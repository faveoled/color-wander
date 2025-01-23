package app

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.Element
import org.scalajs.dom.Event
import org.scalajs.dom.HTMLCanvasElement
import org.scalajs.dom.HTMLElement
import org.scalajs.dom.Image
import org.scalajs.dom.MouseEvent

import typings.wcagContrast.mod.hex as contrasthex

import typings.fastclick.mod.FastClick

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import js.JSConverters.*

var canvas: HTMLCanvasElement = null
var isIOS: Boolean = false
var loop: Engine = null
var background: Image = null
var context: CanvasRenderingContext2D = null
var seedContainer: HTMLElement  = null
var seedText: HTMLElement = null

@main
def App(): Unit =
  dom.document.addEventListener("DOMContentLoaded", (_) => {
    FastClick.attach(dom.document.body)
  }, false); 
  canvas = dom.document.querySelector("#canvas").asInstanceOf[HTMLCanvasElement]
  background = dom.document.createElement("img").asInstanceOf[Image]
  context = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  loop = RafLoop()
  seedContainer = dom.document.querySelector(".seed-container").asInstanceOf[HTMLElement]
  seedText = dom.document.querySelector(".seed-text").asInstanceOf[HTMLElement]

  val userAgentLowerCase = dom.window.navigator.userAgent.toLowerCase()
  isIOS = userAgentLowerCase.contains("ipad") || 
    userAgentLowerCase.contains("iphone") ||
    userAgentLowerCase.contains("ipod")


  if (isIOS) { // iOS bugs with full screen ...
    val fixScroll = () => {
      dom.window.setTimeout(() => {
        dom.window.scrollTo(0, 1)
      }, 500)
      ()
    }

    fixScroll()
    dom.window.addEventListener("orientationchange", (_) => {
      fixScroll()
    }, false)
  }

  dom.window.addEventListener("resize", (_) => resize())
  dom.document.body.style.margin = "0"
  dom.document.body.style.overflow = "hidden"
  canvas.style.position = "absolute"

  def randomize0(): Unit = {
    reload(Config.createConfig(None))
  }

  def randomize1(ev: Event): Unit = {
    ev.preventDefault()
    randomize0()
  }

  randomize0()
  resize()


  val addEvents = (element: Element) => {
    element.addEventListener("mousedown", (ev: MouseEvent) => {
      if (ev.button == 0) {
        randomize1(ev)
      }
    })
    element.addEventListener("touchstart", (ev) => randomize1(ev))
  }

  val targets = Array(dom.document.querySelector("#fill"), canvas)
  for (target <- targets) {
    addEvents(target)
  }


end App

def resize(): Unit = {
  letterbox(canvas, Array(dom.window.innerWidth, dom.window.innerHeight))
}

def letterbox (element: HTMLCanvasElement, parent: Array[Double]) = {
  var aspect = element.width / element.height
  var pwidth = parent(0)
  var pheight = parent(1)

  var width = pwidth
  var height = js.Math.round(width / aspect)
  var y = js.Math.floor(pheight - height) / 2

  if (isIOS) { // iOS bug with full screen nav bars
    width += 1
    height += 1
  }

  element.style.top = y + "px"
  element.style.width = width + "px"
  element.style.height = height + "px"
}

def reload(config: OptRenderer): Unit = {
  loop.removeAllListeners("tick")
  loop.stop()

  val opts = OptRenderer()
  opts.withConfig(config)
  opts.backgroundImage = Some(background)
  opts.context = Some(context)

  val pixelRatio = config.pixelRatio.getOrElse(1)
  canvas.width = opts.width.get * pixelRatio
  canvas.height = opts.height.get * pixelRatio

  dom.document.body.style.background = opts.palette.get(0)
  seedContainer.style.color = getBestContrast(opts.palette.get(0), opts.palette.get.tail)
  seedText.textContent = opts.seedName.get

  background.onload = (ev) => {
    val renderer = Renderer(opts)
    if (opts.debugLuma.get) {
      renderer.debugLuma()
    } else {
      renderer.clear()
      var stepCount = 0
      loop.on("tick", () => {
        renderer.step(opts.interval.get)
        stepCount = stepCount + 1
        if (!opts.endlessBrowser.get && stepCount > opts.steps.get) {
          loop.stop()
        }
      })
      loop.start()
    }    
  }
  background.src = config.backgroundSrc.get
}


def getBestContrast(background: String, colors: Seq[String]): String = {
  val bestitem  =
    colors
      .zipWithIndex
      .map { case (color, index) => (index, contrasthex(background, color)) }
      .maxBy(_._2)
  colors(bestitem._1)
}

package app

import scala.scalajs.js

import org.scalajs.dom.HTMLImageElement
import org.scalajs.dom.HTMLCanvasElement
import org.scalajs.dom.CanvasRenderingContext2D

object DrawImageCover {


    def drawImageCover (ctx: CanvasRenderingContext2D, image: HTMLImageElement,
                        parentArg: Option[HTMLCanvasElement], scaleArg: Option[Int]): Unit =
    {
        val scale = scaleArg.getOrElse(1)
        val parent = parentArg.getOrElse(ctx.canvas)

        val tAspect = image.width / image.height
        val pWidth = parent.width
        val pHeight = parent.height

        val pAspect = pWidth / pHeight

        var width = -1
        var height = -1
        if (tAspect > pAspect) {
            height = pHeight
            width = height * tAspect
        } else {
            width = pWidth
            height = width / tAspect
        }


        width *= scale
        height *= scale
        val x = (pWidth - width) / 2
        val y = (pHeight - height) / 2
        ctx.drawImage(image, x, y, width, height)
    }
}

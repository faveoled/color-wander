package app

import scala.scalajs.js

import scala.scalajs.js.typedarray.Uint8ClampedArray

import org.scalajs.dom.ImageData
import org.scalajs.dom.HTMLImageElement
import org.scalajs.dom.HTMLCanvasElement
import org.scalajs.dom.CanvasRenderingContext2D

object GetLumaPixels {

    case class Options(
        var scale: Option[Int] = None,
        var fillStyle: Option[String] = None,
        var threshold: Option[Array[Double]] = None,
    )


    def getLumaPixels(ctx: CanvasRenderingContext2D, img: HTMLImageElement, opt: Options): ImageData = {
        val canvas = ctx.canvas
        val scale = opt.scale.getOrElse(1)
        val threshold = opt.threshold

        ctx.fillStyle = opt.fillStyle.getOrElse("black")
        ctx.fillRect(0, 0, canvas.width, canvas.height)
        DrawImageCover.drawImageCover(ctx, img, Some(canvas), Some(scale))

        val imageData = ctx.getImageData(0, 0, canvas.width, canvas.height)
        val rgba: Uint8ClampedArray = imageData.data

        for (i <- 0 until canvas.width * canvas.height) {
            val r = rgba(i * 4 + 0)
            val g = rgba(i * 4 + 1)
            val b = rgba(i * 4 + 2)

            // grayscale
            var L = ColorLuminance.rec601(r, g, b)

            // optional threshold
            if (threshold.isDefined) {
                L = js.Math.floor(Smoothstep.smoothstep(threshold.get(0), threshold.get(1), L / 255) * 255)
            }

            // replace RGBA
            rgba(i * 4 + 0) = L.toInt
            rgba(i * 4 + 1) = L.toInt
            rgba(i * 4 + 2) = L.toInt
        }
        imageData

    }


}

package app

import scala.scalajs.js

object Smoothstep {

    def smoothstep(min: Double, max: Double, value: Double): Double = {
        val x = js.Math.max(0, js.Math.min(1, (value - min) / (max - min)))
        x * x * (3 - (2 * x))
    }
}

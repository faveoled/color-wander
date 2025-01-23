package app

import scala.scalajs.js

object Clamp {

    def clampInt(value: Int, min: Int, max: Int): Int = {
        if (min < max) {
            if (value < min) min
            else if (value > max) max
            else value
        } else {
            if (value < max) max
            else if (value > min) min
            else value
        }
    }

    def clamp(value: Double, min: Double, max: Double): Double = {
        if (min < max) {
            if (value < min) min
            else if (value > max) max
            else value
        } else {
            if (value < max) max
            else if (value > min) min
            else value
        }
    }
}

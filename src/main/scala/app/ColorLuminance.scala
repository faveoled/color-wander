package app

import scala.scalajs.js

object ColorLuminance {

    def rec601(r: Double, g: Double, b: Double): Double = {
        r * 0.299 + g * 0.587 + b * 0.114
    }
}

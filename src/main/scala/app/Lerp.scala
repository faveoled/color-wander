package app

import scala.scalajs.js

object Lerp {

    def lerp(v0: Double, v1: Double, t: Double): Double = {
        v0 * (1 - t) + v1 * t
    }

}

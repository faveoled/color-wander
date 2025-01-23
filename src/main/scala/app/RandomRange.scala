package app

import scala.scalajs.js

object RandomRange {

  
  def random(randFunc: (() => Double), min: Option[Double], max: Option[Double]): Double = {
    var adjustedMin = min.getOrElse(1.0)
    var adjustedMax: Option[Double] = None

    if (max.isEmpty) {
      adjustedMax = Some(adjustedMin)
      adjustedMin = 0
    } else {
      adjustedMax = Some(max.get)
    }

    randFunc() * (adjustedMax.get - adjustedMin) + adjustedMin
  }

}

package app

import scala.scalajs.js

import js.JSConverters._

import typings.glVec2.addMod as vec2add
import typings.glVec2.normalizeMod as vec2normalize
import typings.glVec2.scaleMod as vec2scale

object Vec2 {

    def add(a: Array[Double], b: Array[Double]): Array[Double] = {
        val out = new js.Array[Double]()
        vec2add(out, a.toJSArray, b.toJSArray)
        Array(out(0), out(1))
    }

    def normalize(target: Array[Double]): Array[Double] = {
        val out = new js.Array[Double]()
        vec2normalize(out, target.toJSArray)
        Array(out(0), out(1))
    }

    def scale(target: Array[Double], a: Double): Array[Double] = {
        val out = new js.Array[Double]()
        vec2scale(out, target.toJSArray, a)
        Array(out(0), out(1))
    }

}

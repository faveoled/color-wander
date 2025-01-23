package app

import scala.scalajs.js

import org.scalajs.dom.Image
import org.scalajs.dom.CanvasRenderingContext2D


case class OptRenderer(

        var random: Option[() => Double] = None,
        var seedName: Option[String] = None,
        var pointilism: Option[Double] = None,
        var noiseScalar: Option[Array[Double]] = None,
        var globalAlpha: Option[Double] = None,
        var startArea: Option[Double] = None,
        var maxRadius: Option[Double] = None,
        var lineStyle: Option[String] = None,
        var interval: Option[Double] = None,
        var count: Option[Int] = None,
        var steps: Option[Int] = None,
        var endlessBrowser: Option[Boolean] = None,
        
        var debugLuma: Option[Boolean] = None,
        var backgroundScale: Option[Int] = None,
        var backgroundFill: Option[String] = None,
        var backgroundSrc: Option[String] = None,

        var pixelRatio: Option[Int] = None,
        var width: Option[Int] = None,
        var height: Option[Int] = None,
        var palette: Option[Array[String]] = None,

        var asVideoFrames: Option[Boolean] = None,
        var filename: Option[String] = None,
        var outputDir: Option[String] = None,
        var backgroundImage: Option[Image] = None,
        var context: Option[CanvasRenderingContext2D] = None


) {

  def withConfig(another: OptRenderer): Unit = {
    this.random = another.random 
    this.seedName = another.seedName 
    this.pointilism = another.pointilism 
    this.noiseScalar = another.noiseScalar 
    this.globalAlpha = another.globalAlpha 
    this.startArea = another.startArea 
    this.maxRadius = another.maxRadius 
    this.lineStyle = another.lineStyle 
    this.interval = another.interval 
    this.count = another.count 
    this.steps = another.steps 
    this.endlessBrowser = another.endlessBrowser      
    this.debugLuma = another.debugLuma 
    this.backgroundScale = another.backgroundScale 
    this.backgroundFill = another.backgroundFill 
    this.backgroundSrc = another.backgroundSrc 
    this.pixelRatio = another.pixelRatio 
    this.width = another.width 
    this.height = another.height 
    this.palette = another.palette 
    this.asVideoFrames = another.asVideoFrames 
    this.filename = another.filename 
    this.outputDir = another.outputDir 
    this.backgroundImage = another.backgroundImage 
    this.context = another.context            
  }
}

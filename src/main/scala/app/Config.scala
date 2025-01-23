package app

import scala.scalajs.js

import typings.seedRandom.mod as seedRandom
import scala.collection.mutable.ArrayBuffer

object Config {

    var random: (Option[Double], Option[Double]) => Double = null

    def createConfig(seedArg: Option[Int]): OptRenderer = {
        var seed = ""
        if (seedArg.isDefined) {
            seed = seedArg.get.toString
        } else {
            seed = js.Math.floor(js.Math.random() * 1000000).toInt.toString
        }

        print(s"Seed: ${seed}")

        val randomFunc = () => seedRandom(seed)()
        random = RandomRange.random(randomFunc, _ : Option[Double], _: Option[Double])

        def random1(a: Double): Double = {
            random(Some(a), None)
        }

        def random2(a: Double, b: Double): Double = {
            random(Some(a), Some(b))
        }

        val maps = List(
            "sym6.jpg", "sym3.jpg",
            "scifi.jpg", "nature1.jpg",
            "map7.jpg", "geo5.jpg", "geo4.jpg",
            "geo3.jpg", "geo1.jpg", "fractal2.jpg",
            "fractal1.jpg", "eye.jpg", "city5.jpg",
            "city2.jpg", "church2.jpg", "architecture.jpg",
            "pat1.jpg"
        ).map(p => s"maps/$p")

        var mapSrc = maps(js.Math.floor(random(Some(maps.length), None)).toInt)       
        
        val opts = OptRenderer()

        // rendering options
        opts.random = Some(randomFunc)
        opts.seedName = Some(seed)
        opts.pointilism = Some(random2(0, 0.1))
        opts.noiseScalar = Some(Array(random2(0.000001, 0.000001), random2(0.0002, 0.004)))
        opts.globalAlpha = Some(0.5)
        opts.startArea = Some(random2(0.0, 1.5))
        opts.maxRadius = Some(random2(5, 100))
        opts.lineStyle = Some(if random(None, None) > 0.5 then "round" else "square")
        opts.interval = Some(random2(0.001, 0.01))
        opts.count = Some(js.Math.floor(random2(50, 2000)).toInt)
        opts.steps = Some(js.Math.floor(random2(100, 1000)).toInt)
        // Whether to endlessly step in browser
        opts.endlessBrowser = Some(false)

        // background image that drives the algorithm
        opts.debugLuma = Some(false)
        opts.backgroundScale = Some(1)
        opts.backgroundFill = Some("black")
        opts.backgroundSrc = Some(mapSrc)
        
        // browser/node options
        opts.pixelRatio = Some(1)
        opts.width = Some(1280 * 2)
        opts.height = Some(720 * 2)
        opts.palette = Some(getPalette())
        
        // node only options        
        opts.asVideoFrames = Some(false)
        opts.filename = Some("render")
        opts.outputDir = Some("output")

        opts

    }

    def getPalette(): Array[String] = {
        val paletteColors = ColorPalettes.PALETTES(js.Math.floor(random(None, None) * ColorPalettes.PALETTES.length).toInt)
        return arrayShuffle(paletteColors);
    }

    def arrayShuffle(arr: Array[String]): Array[String] = {
        var rand: Int = 0
        var tmp: String = null
        var len = arr.length
        var ret = arr.to(ArrayBuffer)
        
        while (len > 0) {
            rand = js.Math.floor(random(None, None) * len).toInt
            len -= 1
            tmp = ret(len)
            ret(len) = ret(rand)
            ret(rand) = tmp
        }
        
        ret.to(Array)
    }
}

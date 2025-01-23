package app

import app.simplex.distEsmSimplexNoiseMod.SimplexNoise

import scala.scalajs.js

import org.scalajs.dom.HTMLCanvasElement
import org.scalajs.dom.CanvasRenderingContext2D
import scala.scalajs.js.typedarray.Uint8ClampedArray
import org.scalajs.dom.ImageData

class Renderer(opt: OptRenderer) {

    private var randFunc: () => Double = opt.random.getOrElse(() => js.Math.random())

    private val simplex = new SimplexNoise(opt.random.get)
    private var ctx: CanvasRenderingContext2D = opt.context.get
    private val dpr = opt.pixelRatio.getOrElse(1)
    private var canvas: HTMLCanvasElement = ctx.canvas
    private var width: Int = canvas.width
    private var height: Int = canvas.height
    private var count: Int = opt.count.getOrElse(0)
    private var palette: Array[String] = opt.palette.getOrElse(Array("#fff'", "#000"))
    private var backgroundImage = opt.backgroundImage.get

    private var maxRadius: Double = opt.maxRadius.getOrElse(10)
    private var startArea: Double = opt.startArea.getOrElse(0.5)
    private var pointilism: Double = Lerp.lerp(0.000001, 0.5, opt.pointilism.get)
    private var noiseScalar: Array[Double] = opt.noiseScalar.getOrElse(Array(0.00001, 0.0001))
    private var globalAlpha: Double = opt.globalAlpha.getOrElse(1)

    private var time: Double = 0

    var particles: Array[Particle] = newParticleArray()
    var heightMapImage: ImageData = getHeightMapImage()
    var heightMap: Uint8ClampedArray = heightMapImage.data

    def getHeightMapImage(): ImageData = {
        val lumaOpts = GetLumaPixels.Options()
        lumaOpts.scale = opt.backgroundScale
        lumaOpts.fillStyle = opt.backgroundFill
        GetLumaPixels.getLumaPixels(ctx, backgroundImage, lumaOpts)

    } 

    def newParticleArray(): Array[Particle] = {
        Array.fill(count)(0)
            .map(i => resetParticle(None))
    }

    def random(arg1: Double): Double = {
        RandomRange.random(randFunc, Some(arg1), None)
    }

    def random(arg1: Double, arg2: Double): Double = {
        RandomRange.random(randFunc, Some(arg1), Some(arg2))
    }

    def clear(): Unit = {
        ctx.fillStyle = palette(0);
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    def step(dt: Double): Unit = {
        time += dt
        for (p <- particles) {
            val x = p.position(0)
            val y = p.position(1)

            val fx = Clamp.clampInt(js.Math.round(x).toInt, 0, canvas.width - 1)
            val fy = Clamp.clampInt(js.Math.round(y).toInt, 0, canvas.height - 1)
            val heightIndex = fx + (fy * canvas.width)
            val heightValue = heightMap(heightIndex * 4) / 255

            val pS = Lerp.lerp(noiseScalar(0), noiseScalar(1), heightValue)
            val n = simplex.noise3D(fx * pS, fy * pS, p.duration + time)
            val angle = n * js.Math.PI * 2
            val speed = p.speed + Lerp.lerp(0.0, 2, 1 - heightValue)
            p.velocity = Vec2.add(p.velocity, Array(js.Math.cos(angle), js.Math.sin(angle)))
            p.velocity = Vec2.normalize(p.velocity)
            val move = Vec2.scale(p.velocity, speed)
            p.position = Vec2.add(p.position, move)

            val s2 = pointilism
            var r = p.radius * simplex.noise3D(x * s2, y * s2, p.duration + time)
            r *=  Lerp.lerp(0.01, 1.0, heightValue)
            ctx.beginPath()
            ctx.lineTo(x, y)
            ctx.lineTo(p.position(0), p.position(1))
            ctx.lineWidth = r * (p.time / p.duration)
            ctx.lineCap = opt.lineStyle.getOrElse("square")
            ctx.lineJoin = opt.lineStyle.getOrElse("square")
            ctx.strokeStyle = p.color

            ctx.globalAlpha = globalAlpha
            ctx.stroke()

            p.time += dt
            if (p.time > p.duration) {
                resetParticle(Some(p))
            }
        }
    }

    case class Particle(
        var position: Array[Double] = Array.fill(2)(0.0),
        var radius: Double = 0.0,
        var duration: Double = 0.0,
        var time: Double = 0.0,
        var velocity: Array[Double] = Array.fill(2)(0.0),
        var speed: Double = 0.0,
        var color: String = ""
    )

    def resetParticle(pArg: Option[Particle]): Particle = {
        val p = pArg.getOrElse(Particle())

        val scale = js.Math.min(width, height) / 2.0
        val position = randomSphere(Some(random(0, scale * startArea)))
        val newPosition = Array(
            position(0) + ( width / 2.0 ),
            position(1) + ( height / 2.0 )
        )
        p.position = newPosition


        p.radius = random(0.01, maxRadius)
        p.duration = random(1, 500)
        p.time = random(0, p.duration)
        p.velocity = Array(random(-1, 1), random(-1, 1))
        p.speed = random(0.5, 2) * dpr

        // Note: We actually include the background color here.
        // This means some strokes may seem to "erase" the other
        // colours, which can add a nice effect.
        p.color = palette(js.Math.floor(random(palette.length)).toInt)
        p
    }

    def randomSphere(scaleArg: Option[Double]): Array[Double] = {
        val scale = scaleArg.getOrElse(1.0)
        val r = randFunc() * 2.0 * js.Math.PI
        val el0 = js.Math.cos(r) * scale
        val el1 = js.Math.sin(r) * scale
        Array(el0, el1)
    }

    def debugLuma(): Unit = {
        ctx.putImageData(heightMapImage, 0, 0)
    }

}

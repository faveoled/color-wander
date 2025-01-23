package app.simplex

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

/**
  * Copy-pasted from ScalablyTyped converter with fixed JSImports paths.
  */
object distEsmSimplexNoiseMod {
  
  // @JSImport("simplex-noise/dist/esm/simplex-noise", JSImport.Namespace)
  @JSImport("simplex-noise", JSImport.Namespace)
  @js.native
  val ^ : js.Any = js.native
  
  // @JSImport("simplex-noise/dist/esm/simplex-noise", JSImport.Default)
  @JSImport("simplex-noise", JSImport.Default)
  @js.native
  /**
    * Creates a new `SimplexNoise` instance.
    * This involves some setup. You can save a few cpu cycles by reusing the same instance.
    * @param randomOrSeed A random number generator or a seed (string|number).
    * Defaults to Math.random (random irreproducible initialization).
    */
  open class default () extends SimplexNoise {
    def this(randomOrSeed: String) = this()
    def this(randomOrSeed: Double) = this()
    def this(randomOrSeed: RandomFn) = this()
  }
  
  // @JSImport("simplex-noise/dist/esm/simplex-noise", "SimplexNoise")
  @JSImport("simplex-noise", "SimplexNoise")
  @js.native
  /**
    * Creates a new `SimplexNoise` instance.
    * This involves some setup. You can save a few cpu cycles by reusing the same instance.
    * @param randomOrSeed A random number generator or a seed (string|number).
    * Defaults to Math.random (random irreproducible initialization).
    */
  open class SimplexNoise () extends StObject {
    def this(randomOrSeed: String) = this()
    def this(randomOrSeed: Double) = this()
    def this(randomOrSeed: RandomFn) = this()
    
    /**
      * Samples the noise field in 2 dimensions
      * @param x
      * @param y
      * @returns a number in the interval [-1, 1]
      */
    def noise2D(x: Double, y: Double): Double = js.native
    
    /**
      * Samples the noise field in 3 dimensions
      * @param x
      * @param y
      * @param z
      * @returns a number in the interval [-1, 1]
      */
    def noise3D(x: Double, y: Double, z: Double): Double = js.native
    
    /**
      * Samples the noise field in 4 dimensions
      * @param x
      * @param y
      * @param z
      * @returns a number in the interval [-1, 1]
      */
    def noise4D(x: Double, y: Double, z: Double, w: Double): Double = js.native
    
    /* private */ var p: Any = js.native
    
    /* private */ var perm: Any = js.native
    
    /* private */ var permMod12: Any = js.native
  }
  
  inline def buildPermutationTable(random: RandomFn): js.typedarray.Uint8Array = ^.asInstanceOf[js.Dynamic].applyDynamic("buildPermutationTable")(random.asInstanceOf[js.Any]).asInstanceOf[js.typedarray.Uint8Array]
  
  type RandomFn = js.Function0[Double]
}

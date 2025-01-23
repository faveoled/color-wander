package app

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("raf-loop", JSImport.Namespace)
class Engine extends js.Object {
  def stop(): Unit = js.native
  def start(): Unit = js.native
  def removeAllListeners(_type: String): Unit = js.native
  def on(ev: String, fn: js.Function0[Unit]): Unit = js.native
}

@js.native
@JSImport("raf-loop", JSImport.Namespace)
object RafLoop extends js.Object {

  def apply(): Engine = js.native

}



package tutorial.webapp

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.jquery.jQuery

object TutorialApp extends JSApp {

  val $ = jQuery

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  @JSExport
  def addClickedMessage(): Unit = {
    appendPar(document.body, "You clicked the button")
  }

  def main(): Unit = {
    println("Hello world!")
    appendPar(document.body, "Hello World")
    
    $(setupUI _)
  }
  
  def setupUI(): Unit = {
    $("#click-me-button2").click(addClickedMessage _)
    $("body").append("<p>[Hello World]</p>")
  }

}

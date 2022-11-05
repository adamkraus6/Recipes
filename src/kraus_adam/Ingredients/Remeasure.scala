package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper

import scala.collection.mutable.ListBuffer
import scala.xml.*

class Remeasure(name: String, quantity: Double) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {

    }

    def writeXML(): Elem = {
        XMLHelper.makeNode(Remeasure.TAG)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}Remeasure to ${quantity} cups\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n")
    }
}

object Remeasure {
    val TAG = "remeasure"

    def apply(quantity: Double): Remeasure = {
        new Remeasure("", quantity)
    }
}
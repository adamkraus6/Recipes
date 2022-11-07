package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.collection.mutable

import scala.collection.mutable.ListBuffer
import scala.xml.*

class Remeasure(name: String, quantity: Double) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {

    }

    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("quantity", quantity.toString))
        val child = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Remeasure.TAG, attr, child)
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
package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.xml.*

class Baked(name: String, expansionFactor: Double) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        XMLHelper.makeNode(Baked.TAG)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}${name} (${expansionFactor})\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n")
    }
}

object Baked {
    val TAG = "baked"

    def apply(name: String, expansionFactor: Double): Baked = {
        new Baked(name, expansionFactor)
    }
}
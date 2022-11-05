package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.xml.*

class Single(name: String, calories: Double, volume: Double) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        XMLHelper.makeNode(Single.TAG)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}______${name}______\n" +
          s"${spaces * depth}Cups: ${format.format(volume.round)}\n" +
          s"${spaces * depth}Calories: ${format.format(calories.round)}\n"
    }
}

object Single {
    val TAG = "single"

    def apply(name: String, calories: Double, volume: Double): Single = {
        new Single(name, calories, volume)
    }
}
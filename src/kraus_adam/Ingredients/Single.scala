package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.collection.mutable
import scala.xml.*

class Single(name: String, calories: Double, cups: Double) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("calories", calories.toString), ("cups", cups.toString))
        val text = Text(name)
        XMLHelper.makeNode(Single.TAG, attr, text)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}______${name.capitalize}______\n" +
          s"${spaces * depth}Cups: ${format.format(cups.round)}\n" +
          s"${spaces * depth}Calories: ${format.format(calories.round)}\n"
    }
}

object Single {
    val TAG = "single"

    def apply(name: String, calories: Double, volume: Double): Single = {
        new Single(name, calories, volume)
    }
}
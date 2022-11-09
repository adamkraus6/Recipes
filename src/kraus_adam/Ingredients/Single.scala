package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite
import scala.collection.mutable
import scala.xml.*

/*
Basic single ingredient with calories and volume
*/
class Single(name: String, calories: Double, cups: Double) extends Ingredient(name: String) with XMLReadWrite {
    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        // not used, Single has no child nodes  
    }

    /*
    Writes class info into XML
    return: XML Element
    */
    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("calories", calories.toString), ("cups", cups.toString))
        val text = Text(name)
        XMLHelper.makeNode(Single.TAG, attr, text)
    }

    /*
    Searches subIngredient(s) for ingredient
    return: true if found
    */
    def findIngredient(name: String): Boolean = {
        if(this.name == name)
            return true

        false
    }

    /*
    Gets ingredient calories
    return: calories
    */
    def getCal: Double = {
        calories
    }

    /*
    Gets ingredient volume
    return: volume
    */
    def getVol: Double = {
        cups
    }

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
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
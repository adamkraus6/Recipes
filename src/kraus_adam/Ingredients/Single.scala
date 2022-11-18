package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite

import java.text.DecimalFormat
import scala.collection.mutable
import scala.io.StdIn
import scala.xml.*

/*
Basic single ingredient with calories and volume
*/
class Single() extends Ingredient() with XMLReadWrite {
    private var calories: Double = 0
    private var cups: Double = 0
    private val format = new DecimalFormat("0.##")

    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        name = node.text
        calories = node.attribute("calories").getOrElse("100").toString.toDouble
        cups = node.attribute("cups").getOrElse("1").toString.toDouble
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
    Prompts for class information
    */
    def addIngredient(): Unit = {
        print("Name:> ")
        name = StdIn.readLine().toLowerCase
        print("Calories:> ")
        calories = StdIn.readDouble()
        print("Cups:> ")
        cups = StdIn.readDouble()
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
        s"${spaces * depth}______${name}______\n" +
          s"${spaces * depth}Cups: ${format.format(cups)}\n" +
          s"${spaces * depth}Calories: ${format.format(calories)}\n"
    }
}

object Single {
    val TAG = "single"

    def apply(): Single = {
        new Single()
    }
}
package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.xml.*
import scala.io.StdIn


/*
Remeasures sub ingredients
*/
class Remeasure() extends Ingredient() with XMLReadWrite {
    protected var quantity: Double = 0
    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        quantity = node.attribute("quantity").getOrElse("1").toString.toDouble

        val children = node.child
        for (child <- children) {
            val tag = child.label
            tag match {
                case Mix.TAG =>
                    val mix = Mix()
                    mix.loadXML(child)
                    subIngredients += mix
                case Baked.TAG =>
                    val baked = Baked()
                    baked.loadXML(child)
                    subIngredients += baked
                case Remeasure.TAG =>
                    val remeasure = Remeasure()
                    remeasure.loadXML(child)
                    subIngredients += remeasure
                case Single.TAG =>
                    val single = Single()
                    single.loadXML(child)
                    subIngredients += single
                case _ =>
            }
        }
    }

    /*
    Writes class info into XML
    return: XML Element
    */
    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("quantity", quantity.toString))
        val child = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Remeasure.TAG, attr, child)
    }

    def addIngredient(): Unit = {
        print("New Quantity:> ")
        quantity = StdIn.readLine().toDouble

        print("What ingredient (mix, baked, remeasure, single):> ")
        val ingType = StdIn.readLine().toLowerCase

        if (ingType == "mix" || ingType == "m") {
            val mix = Mix()
            mix.addIngredient()
            println("Added mix")
            subIngredients += mix
        } else if (ingType == "baked" || ingType == "b") {
            val baked = Baked()
            baked.addIngredient()
            println("Added baked")
            subIngredients += baked
        } else if (ingType == "remeasure" || ingType == "r") {
            val remeasure = Remeasure()
            remeasure.addIngredient()
            println("Added remeasure")
            subIngredients += remeasure
        } else if (ingType == "single" || ingType == "s") {
            val single = Single()
            single.addIngredient()
            println("Added single")
            subIngredients += single
        } else {
            println("Ingredient format not found")
        }
    }

    /*
    Searches subIngredient(s) for ingredient
    return: true if found
    */
    def findIngredient(name: String): Boolean = {
        if (this.name == name)
            return true

        if (subIngredients(0).findIngredient(name))
            return true

        false
    }

    /*
    Gets ingredient calories
    return: calories
    */
    def getCal: Double = {
        quantity * subIngredients(0).getCal
    }

    /*
    Gets ingredient volume
    return: volume
    */
    def getVol: Double = {
        quantity * subIngredients(0).getVol
    }

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
    def getInfo(depth: Int): String = {
        s"${spaces * depth}Remeasure to ${quantity} cups\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n")
    }
}

object Remeasure {
    val TAG = "remeasure"

    def apply(): Remeasure = {
        new Remeasure()
    }
}
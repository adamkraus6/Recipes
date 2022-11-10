/*

*/
package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite
import scala.collection.mutable
import scala.io.StdIn
import scala.xml.*

/*
Baked ingredient that expands sub ingredient by a certain factor
*/
class Baked() extends Ingredient() with XMLReadWrite {
    private var expansionFactor: Double = 0

    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        name = node.attribute("name").getOrElse("").toString
        expansionFactor = node.attribute("expansion").getOrElse("1").toString.toDouble

        val children = node.child
        for(child <- children) {
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
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("expansion", expansionFactor.toString))
        if(!name.isEmpty) {
            attr.addOne(("name", name))
        }
        val child = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Baked.TAG, attr, child)
    }

    /*
    Prompts for class information and then subingredient(s)
    */
    def addIngredient(): Unit = {
        print("Name:> ")
        name = StdIn.readLine()
        print("Expansion Factor:> ")
        expansionFactor = StdIn.readLine().toDouble

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
        if (this.name.isEmpty) {
            val subName = subIngredients(0).getName
            if(name == ("baked " + subName))
                return true
        } else if(this.name == name) {
            return true
        }

        if (subIngredients(0).findIngredient(name))
            return true

        false
    }

    /*
    Gets ingredient calories
    return: calories
    */
    def getCal: Double = {
        subIngredients(0).getCal
    }

    /*
    Gets ingredient volume
    return: volume
    */
    def getVol: Double = {
        expansionFactor * subIngredients(0).getVol
    }

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
    def getInfo(depth: Int): String = {
        if(name.isEmpty) {
            s"${spaces * depth}baked ${subIngredients(0).getName} (${expansionFactor})\n" +
              subIngredients.map(x => x.getInfo(depth + 1)).mkString("\n")
        } else {
            s"${spaces * depth}${name.capitalize} (${expansionFactor})\n" +
              subIngredients.map(x => x.getInfo(depth + 1)).mkString("\n")
        }
    }
}

object Baked {
    val TAG = "baked"

    def apply(): Baked = {
        new Baked()
    }
}
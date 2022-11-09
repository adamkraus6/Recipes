/*

*/
package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite
import scala.collection.mutable
import scala.xml.*

/*
Baked ingredient that expands sub ingredient by a certain factor
*/
class Baked(name: String, expansionFactor: Double) extends Ingredient(name: String) with XMLReadWrite {
    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        val children = node.child
        for(child <- children) {
            val tag = child.label
            tag match {
                case Mix.TAG =>
                    val name = child.attribute("name").getOrElse("").toString
                    val mix = Mix(name)
                    mix.loadXML(child)
                    subIngredients += mix
                case Baked.TAG =>
                    val name = child.attribute("name").getOrElse("").toString
                    val expFac = child.attribute("expansion").getOrElse("1").toString.toDouble
                    val baked = Baked(name, expFac)
                    baked.loadXML(child)
                    subIngredients += baked
                case Remeasure.TAG =>
                    val quantity = child.attribute("quantity").getOrElse("1").toString.toDouble
                    val remeasure = Remeasure(quantity)
                    remeasure.loadXML(child)
                    subIngredients += remeasure
                case Single.TAG =>
                    val name = child.text
                    val cups = child.attribute("cups").getOrElse("1").toString.toDouble
                    val calories = child.attribute("calories").getOrElse("100").toString.toDouble
                    val single = Single(name, calories, cups)
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
    Searches subIngredient(s) for ingredient
    return: true if found
    */
    def findIngredient(name: String): Boolean = {
        if (this.name.isEmpty) {
            val subName = subIngredients(0).name
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
            s"${spaces * depth}baked ${subIngredients(0).name} (${expansionFactor})\n" +
              subIngredients.map(x => x.getInfo(depth + 1)).mkString("\n")
        } else {
            s"${spaces * depth}${name.capitalize} (${expansionFactor})\n" +
              subIngredients.map(x => x.getInfo(depth + 1)).mkString("\n")
        }
    }
}

object Baked {
    val TAG = "baked"

    def apply(name: String, expansionFactor: Double): Baked = {
        new Baked(name, expansionFactor)
    }
}
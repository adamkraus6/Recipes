package kraus_adam.Ingredients

import kraus_adam.XMLHelper
import kraus_adam.XMLReadWrite
import scala.collection.mutable
import scala.collection.parallel.CollectionConverters.*
import scala.xml.*
import scala.io.StdIn


/*
Mix of many sub ingredients
*/
class Mix() extends Ingredient() with XMLReadWrite {
    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        name = node.attribute("name").get.toString

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
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
        val children = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Mix.TAG, attr, children)
    }

    def addIngredient(): Unit = {
        print("Name:> ")
        name = StdIn.readLine()

        var more = ""
        while (more != "n") {
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
            print("Add another ingredient (y/n):> ")
            more = StdIn.readLine().toLowerCase
        }
    }

    def findIngredient(name: String): Boolean = {
        if (this.name == name)
            return true

        for (ing <- subIngredients)
            if (ing.findIngredient(name))
                return true

        false
    }

    /*
    Gets ingredient calories
    return: calories
    */
    def getCal: Double = {
        // GRADING: PARALLEL
        val parallelList = subIngredients.par
        parallelList.map(i => i.getCal).sum
    }

    /*
    Gets ingredient volume
    return: volume
    */
    def getVol: Double = {
        // Parallel here as well
        val parallelList = subIngredients.par
        parallelList.map(i => i.getVol).sum
    }

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
    def getInfo(depth: Int): String = {
        s"${spaces * depth}${name.capitalize}\n" +
          s"${spaces * depth}*****************************\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n") +
          s"${spaces * depth}*****************************\n"
    }
}

object Mix {
    val TAG = "mix"

    def apply(): Mix = {
        new Mix()
    }
}
package kraus_adam

import kraus_adam.Ingredients.*
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.xml.*

/*
Recipe class that holds ingredients
*/
class Recipe(private var name: String) extends XMLReadWrite {
    private var ingredient: Ingredient = _

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
                    val mix = Mix()
                    mix.loadXML(child)
                    ingredient = mix
                case Baked.TAG =>
                    val baked = Baked()
                    baked.loadXML(child)
                    ingredient = baked
                case Remeasure.TAG =>
                    val remeasure = Remeasure()
                    remeasure.loadXML(child)
                    ingredient = remeasure
                case Single.TAG =>
                    val single = Single()
                    single.loadXML(child)
                    ingredient = single
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
        val child = ingredient.writeXML()
        XMLHelper.makeNode(Recipe.TAG, attr, child)
    }

    /*
    Prompts for ingredient type and adds sub ingredient(s)
    */
    def addIngredient(): Unit = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        val ingType = StdIn.readLine().toLowerCase

        if (ingType == "mix" || ingType == "m") {
            val mix = Mix()
            mix.addIngredient()
            println("Added mix")
            ingredient = mix
        } else if (ingType == "baked" || ingType == "b") {
            val baked = Baked()
            baked.addIngredient()
            println("Added baked")
            ingredient = baked
        } else if (ingType == "remeasure" || ingType == "r") {
            val remeasure = Remeasure()
            remeasure.addIngredient()
            println("Added remeasure")
            ingredient = remeasure
        } else if (ingType == "single" || ingType == "s") {
            val single = Single()
            single.addIngredient()
            println("Added single")
            ingredient = single
        } else {
            println("Ingredient format not found")
        }
    }

    def findIngredient(name: String): Boolean = {
        if(this.name == name) {
            return true
        }

        ingredient.findIngredient(name)
    }
    
    def getName: String = {
        name
    }

    def calcCal(): Double = {
        ingredient.getCal
    }
    
    def calcVol(): Double = {
        ingredient.getVol
    }

    def calcDensity(): Double = {
        calcCal() / calcVol()
    }

    override def toString: String = {
        s"""
          |Recipe: ${name}
          |==================================
          |${ingredient.getInfo(1)}\n
          """.stripMargin
    }
}

object Recipe {
    val TAG = "recipe"

    def apply(name: String): Recipe = {
        new Recipe(name)
    }
}
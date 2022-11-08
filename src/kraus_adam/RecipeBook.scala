package kraus_adam

import java.text.DecimalFormat
import scala.xml.*
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.*

class RecipeBook() extends XMLReadWrite {
    private var recipes: ListBuffer[Recipe] = ListBuffer[Recipe]()

    def loadXML(node: Node): Unit = {
        val children = node.child
        for(child <- children) {
            val tag = child.label
            tag match {
                case Recipe.TAG =>
                    val recipe = Recipe(child.attribute("name").get.toString)
                    recipe.loadXML(child)
                    recipes += recipe
                case _ =>
            }
        }
    }

    def writeXML(): Elem = {
        val children = recipes.map(r => r.writeXML())
        XMLHelper.makeNode(RecipeBook.TAG, null, children)
    }

    def addRecipe(recipe: Recipe) = {
        recipes += recipe
    }

    def findRecipe(name: String): Boolean = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return true
            }
        }

        false
    }

    def findIngredient(name: String): Unit = {
        for(recipe <- recipes) {
            if(recipe.findIngredient(name)) {
                println(name + " found in " + recipe.getName)
                return
            }
        }

        println(name + " not found")
    }

    def calcCal(name: String): Double = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return recipe.calcCal()
            }
        }

        0
    }

    def calcVol(name: String): Double = {
        for (recipe <- recipes) {
            if (recipe.getName == name) {
                return recipe.calcVol()
            }
        }

        0
    }

    def remove(name: String): Boolean = {
        for (i <- 0 to recipes.length-1) {
            if (recipes(i).getName == name) {
                recipes.remove(i)
                return true
            }
        }
        false
    }

    def printDensity(): Unit = {
        val format = new DecimalFormat("0.0")
        for(recipe <- recipes) {
            println(recipe.getName + ": " + format.format(recipe.calcDensity()))
        }
    }

    override def toString: String = {
        var str = ""
        for(recipe <- recipes) {
            str += recipe.toString
        }
        str
    }
}

object RecipeBook {
    val TAG = "recipebook"

    def apply(): RecipeBook = {
        new RecipeBook()
    }
}
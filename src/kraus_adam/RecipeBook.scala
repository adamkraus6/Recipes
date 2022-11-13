package kraus_adam

import java.text.DecimalFormat
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.*
import scala.xml.*

/*
Recipe collection classes
*/
class RecipeBook() extends XMLReadWrite {
    private var recipes: ListBuffer[Recipe] = ListBuffer[Recipe]()

    /*
    Loads information from an XML node into class
    param node: XML node
    */
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

    /*
    Writes class info into XML
    return: XML Element
    */
    def writeXML(): Elem = {
        val children = recipes.map(r => r.writeXML())
        XMLHelper.makeNode(RecipeBook.TAG, null, children)
    }

    /*
    Adds recipe to list
    */
    def addRecipe(recipe: Recipe) = {
        recipes += recipe
    }

    /*
    Finds recipe in list
    param name: name of recipe to find
    return: true if found
    */
    def findRecipe(name: String): Boolean = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return true
            }
        }

        false
    }

    /*
    Searches recipe name and ingredient(s) for ingredient
    param name: name/ingredient to fine
    */
    def findIngredient(name: String): Unit = {
        for(recipe <- recipes) {
            if(recipe.findIngredient(name)) {
                println(name + " found in " + recipe.getName)
                return
            }
        }

        println(name + " not found")
    }

    /*
    Gets the calories for a recipe
    param name: name of recipe
    return: calories in recipe
    */
    def calcCal(name: String): Double = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return recipe.calcCal()
            }
        }

        0
    }

    /*
    Gets the volume of a recipe
    param name: name of recipe
    return: volume in cups of recipe
    */
    def calcVol(name: String): Double = {
        for (recipe <- recipes) {
            if (recipe.getName == name) {
                return recipe.calcVol()
            }
        }

        0
    }

    /*
    Removes a recipe from list
    param name: name of recipe
    return: true if found/removed
    */
    def remove(name: String): Boolean = {
        for (i <- 0 to recipes.length-1) {
            if (recipes(i).getName == name) {
                recipes.remove(i)
                return true
            }
        }
        false
    }

    /*
    Prints calorie density of recipes
    */
    def printDensity(): Unit = {
        val format = new DecimalFormat("0.0")
        for(recipe <- recipes) {
            println(recipe.getName + ": " + format.format(recipe.calcDensity()))
        }
    }

    /*
    Prints data for all recipes
    */
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
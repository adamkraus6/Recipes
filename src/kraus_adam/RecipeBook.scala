package kraus_adam

import scala.xml.*
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._

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

    def find(name: String): Boolean = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return true
            }
        }

        false
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
package kraus_adam

import kraus_adam.Ingredients.*

import java.io.{FileNotFoundException, FileWriter}
import java.text.DecimalFormat
import scala.io.StdIn
import scala.xml.{PrettyPrinter, XML}

object MainStarter {
    def main(args: Array[String]): Unit = {
        val format = new DecimalFormat("0.#")
        val menu: String =
            """
              |1) Add Data
              |2) Display Data
              |3) Remove Recipe
              |4) Load XML
              |5) Write XML
              |6) Find Ingredient in Recipe
              |7) Calculate Calories
              |8) Calculate Volume
              |9) Calculate Calorie Density
              |0) Quit
              |
              |Choice:> """.stripMargin
        var choice : Any = -1
        var temp = ""

        val recipes = RecipeBook()

        while (choice != "0") {
            print(menu)

            //something to strip out empty lines
            temp = StdIn.readLine()
            while (temp.isEmpty)
                temp = StdIn.readLine()

            choice = temp

            if(choice == "1") {
                // Add Data
                print("What recipe:> ")
                val name = StdIn.readLine().toLowerCase
                if(!recipes.find(name)) {
                    val recipe = Recipe(name)
                    // GRADING: ADD
                    recipe.addIngredient()
                    recipes.addRecipe(recipe)
                } else {
                    println(s"${name} is already in the book")
                }
            } else if(choice == "2") {
                // Display Data
                print(recipes.toString)
            } else if(choice == "3") {
                // Remove Recipe
                print("What recipe:> ")
                val name = StdIn.readLine().toLowerCase
                val removed = recipes.remove(name)
                if(removed) {
                    println("removed " + name)
                } else {
                    println("recipe not found")
                }
            } else if(choice == "4") {
                // Load XML
                // print("File name:> ")
                // val fileName = StdIn.readLine()
                // try {
                //     val topNode = XML.loadFile(fileName)
                //     if (topNode.label != RecipeBook.TAG) {
                //         print("Invalid XML file. Needs to be a recipe XML file")
                //     } else {
                //         recipes.loadXML(topNode)
                //     }
                // } catch {
                //     case e: FileNotFoundException => println(s"Could not open file: ${e.getMessage}")
                // }
            } else if(choice == "5") {
                // Write XML
                print("File name:> ")
                val fileName = StdIn.readLine().toLowerCase
                val recipesXML = recipes.writeXML()
                val prettyPrinter = new PrettyPrinter(80, 3)
                val prettyXML = prettyPrinter.format(recipesXML)
                val write = new FileWriter(fileName)
                write.write(prettyXML)
                write.close()
            } else if(choice == "6") {
                // Find Ingredient in Recipe
            } else if(choice == "7") {
                // Calculate Calories
            } else if(choice == "8") {
                // Calculate Volume
            } else if(choice == "9") {
                // Calculate Calorie Density
            }
        }
    }
}


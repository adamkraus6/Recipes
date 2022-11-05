package kraus_adam

import kraus_adam.Ingredients.*

import java.io.{FileNotFoundException, FileWriter}
import java.text.DecimalFormat
import scala.io.StdIn
import scala.xml.XML

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
                val name = StdIn.readLine().capitalize
                if(!recipes.find(name)) {
                    val recipe = Recipe(name)
                    recipe.addIngredient(addIngredient())
                    recipes.addRecipe(recipe)
                } else {
                    println(s"${name} is already in the book")
                }
            } else if(choice == "2") {
                // Display Data
                print(recipes.toString)
            } else if(choice == "3") {
                // Remove Recipe
            } else if(choice == "4") {
                // Load XML
                print("File name: ")
                val fileName = StdIn.readLine()
                try {
                    val topNode = XML.loadFile(fileName)
                    if (topNode.label != RecipeBook.TAG) {
                        print("Invalid XML file. Needs to be a recipe XML file")
                    } else {
                        recipes.loadXML(topNode)
                    }
                } catch {
                    case e: FileNotFoundException => println(s"Could not open file: ${e.getMessage}")
                }
            } else if(choice == "5") {
                // Write XML
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

    def addIngredient(): Ingredient = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        var ingType = StdIn.readLine()
        ingType = ingType.toLowerCase

        if(ingType == "mix" || ingType == "m") {
            print("Name:> ")
            val name = StdIn.readLine().capitalize
            val mix = Mix(name)
            var more = ""
            while(more != "n") {
                val child = addIngredient()
                mix.addIngredient(child)
                print("Add another ingredient (y/n):> ")
                more = StdIn.readLine()
                more = more.toLowerCase
            } 
            println("Added mix")
            mix
        } else if(ingType == "baked" || ingType == "b") {
            print("Name:> ")
            val name = StdIn.readLine().capitalize
            print("Expansion Factor:> ")
            val expFac = StdIn.readLine().toDouble
            val baked = Baked(name, expFac)
            val child = addIngredient()
            baked.addIngredient(child)
            println("Added baked")
            baked
        } else if(ingType == "remeasure" || ingType == "r") {
            print("New Quantity:> ")
            val quantity = StdIn.readLine().toDouble
            val remeasure = Remeasure(quantity)
            val child = addIngredient()
            remeasure.addIngredient(child)
            println("Added remeasure")
            remeasure
        } else if(ingType == "single" || ingType == "s") {
            print("Name:> ")
            val name = StdIn.readLine().capitalize
            print("Calories:> ")
            val calories = StdIn.readLine().toDouble
            print("Cups:> ")
            var volume = StdIn.readLine().toDouble
            println("Added single")
            Single(name, calories, volume)
        } else {
            println("Ingredient format not found")
            null
        }
    }
}


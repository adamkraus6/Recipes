/*
0. Got it running						                        DONE
1.	Add + Display*	36                                          TAGGED
Prompts correct 						                        DONE
Adds each item 						 	                        DONE
Above displays correctly formatted 		                        DONE

2A) Remove + Display*	10                                      TAGGED
Prompts correct							                        DONE
Removes and displays correctly 			                        DONE

2B) Add + XML save*	14                                          TAGGED
Console added items saved correctly 		                    DONE
Console added multiples is saved correctly 	                    DONE

2C) XML load + XML save*	14                                  TAGGED
1 XML file loaded and saved correctly 	                        DONE
2+ XML file loaded and saved correctly	                        DONE

2D) XML load + Display*	12                                      TAGGED
1 XML file loaded and displays correctly 	                    DONE
2+ XML file loaded and displays correctly	                    DONE

2E) XML+ Display with bad file handing	10
All errors handled 							                    DONE

3.	Stress test for above*	12                                  TAGGED
Loads in file, adds data, and displays/saves correctly			DONE
Appends a file and displays/saves correctly 					DONE
Removes ingredient after edits, and displays/saves correctly    DONE

4. Find ingredient*	16                                          TAGGED
CoR format at least there						                DONE
First item found and output formatted correctly	                DONE
Handles “not found case”						                DONE

5A.	Calculate calories*	7
Correct with no remeasuring		                                ______
Correct with remeasuring		                                ______
Parallelized* 					                                ______

5B.  Calculate volume 7						                    ______
Correct with no remeasuring or baking		                    ______
Correct with remeasuring and baking 		                    ______
Parallelized* 								                    ______

6. Calculate density count 6				                    ______

Every Line with a * has its grading tag:                        ______

*/
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
                if(!recipes.findRecipe(name)) {
                    val recipe = Recipe(name)
                    // GRADING: ADD
                    recipe.addIngredient()
                    recipes.addRecipe(recipe)
                } else {
                    println(s"${name} is already in the book")
                }
            } else if(choice == "2") {
                // Display Data
                // GRADING: PRINT
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
                print("File name:> ")
                val fileName = StdIn.readLine()
                try {
                    val topNode = XML.loadFile(fileName)
                    if (topNode.label != RecipeBook.TAG) {
                        println("Invalid XML file. Needs to be an recipebook XML file")
                    } else {
                        // GRADING: READ
                        recipes.loadXML(topNode)
                    }
                } catch {
                    case e: FileNotFoundException => println(s"Could not open file: ${e.getMessage}")
                }
            } else if(choice == "5") {
                // Write XML
                print("File name:> ")
                val fileName = StdIn.readLine()
                // GRADING: WRITE
                val recipesXML = recipes.writeXML()
                val prettyPrinter = new PrettyPrinter(80, 3)
                val prettyXML = prettyPrinter.format(recipesXML)
                val write = new FileWriter(fileName)
                write.write(prettyXML)
                write.close()
            } else if(choice == "6") {
                // Find Ingredient in Recipe
                print("Recipe:> ")
                val name = StdIn.readLine().toLowerCase
                // GRADING: FIND
                recipes.findIngredient(name)
            } else if(choice == "7") {
                // Calculate Calories
                print("What recipe:> ")
                val name = StdIn.readLine().toLowerCase
                val calories = recipes.calcCal(name)
                println("Calorie Count: " + format.format(calories))
            } else if(choice == "8") {
                // Calculate Volume
                print("What recipe:> ")
                val name = StdIn.readLine().toLowerCase
                val cups = recipes.calcVol(name)
                println("Volume in cups: " + format.format(cups))
            } else if(choice == "9") {
                // Calculate Calorie Density
            }
        }
    }
}


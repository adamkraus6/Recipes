package kraus_adam

import java.io.FileWriter
import java.text.DecimalFormat
import scala.io.StdIn
import scala.xml.XML


object MainStarter {
    def main(args: Array[String]) = {
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

        while (choice != "0") {
            print(menu)

            //something to strip out empty lines
            temp = StdIn.readLine()
            while (temp.isEmpty)
                temp = StdIn.readLine()

            choice = temp

            if(choice == "1") {
                // Add Data
            } else if(choice == "2") {
                // Display Data
            } else if(choice == "3") {
                // Remove Recipe
            } else if(choice == "4") {
                // Load XML
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
}


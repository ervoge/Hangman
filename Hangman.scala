/* package Hangman */
/* NAME: Emily Vogelsperger
*  DATE: 3/11/19
*  ASSIGNMENT: Hangman Game
*  DECRIPTION: Hangman game requires a .txt file in the directory of Hangman.scala. This file
*  must contain multiple words all separated by newlines.  Hangman.scala takes this file, reads
*  in the amount of lines dictionaryBIG.txt contains, then selects a random number between 0 and
*  the last line number - 1 to pick a random word. That line is then read into a string var as
*  'word.' As the player guesses characters, game() will check that 'word' contains the guessed
*  char. If so, it will loop through 'word' and for each instance of guessed char (answer) it
*  will replace the value of emptyWord (which holds the current progress of the player) at
*  the specified index. Once emptyWord contains no more '_' characters, the game is over and
*  the player has one. If the player gets to 6 wrong guesses before then, the game is over and
*  the player has lost. The program keeps track of the player's record if the player plays
*  multiple times in one run.*/
import java.io.FileWriter
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Hangman {
  val r = new scala.util.Random()

  def main(args: Array[String]): Unit = {
    var score = 0
    var amntOfGames = 0
    println("Welcome to the game of Hangman! What would you like to do?")
    println()

    var menu = true
    while (menu) {
      println(
        """GAME MENU
          |- play hangman: PLAY
          |- add a word to the dictionary: ADD
          |- close game: QUIT
        """.stripMargin)
      println()

      /* prints the player's current score breakdown */
      print("You're current score is ")
      print(score)
      print(" wins out of ")
      print(amntOfGames)
      println(" games.")
      println()

      print("Please input your answer: ")
      val answer = scala.io.StdIn.readLine().toLowerCase()
      println()

      if (answer.equalsIgnoreCase("PLAY")) {
        amntOfGames = amntOfGames + 1
        val result = game()
        if (result == 1) {
          score = score + 1
        }
      }
      else if (answer.equalsIgnoreCase("ADD")) {
        println("You chose to add a word!")
        print("Please input the word you would like to add to the dictionary: ")
        val word = scala.io.StdIn.readLine()
        val writer = new FileWriter("dictionaryBIG.txt", true)
        writer.write("\n" + word)
        writer.close()
        println("Thank you for your contribution! Your word has been added.")
      }
      else if (answer.equalsIgnoreCase("QUIT")){
        println("Thanks for playing!")
        menu = false
        sys.exit(0)
      }
    }
  }/*end of main*/

  def game (): Int = {
    val word = generateRandWord().toLowerCase() /* generates the word for the game */
    val guesses: ArrayBuffer[String] = ArrayBuffer() /* holds the list of chars guessed */
    val emptyWord: ArrayBuffer[String] = ArrayBuffer.fill(word.length)("_") /* holds the current state of guessed word */
    var wrongGuesses = 0 /* counts the number of incorrect guesses the player has made */
    var gameOver = false /* value determines whether the game has been won or not */

    while(!gameOver) {
      println("Let's play Hangman!")
      drawHangman(wrongGuesses)
      println(emptyWord.mkString(" "))
      println()
      print("Current Guessed Letters: " )
      println(guesses.sorted.mkString(", "))
      print("What do you guess?: ")

      val answer = scala.io.StdIn.readLine().toLowerCase()
      println()

      /* if answer is bigger than 1, assume the user is guessing the word in full */
      if (answer.length != 1) {
        if (word == answer) {
          gameOver = true
          println()
          println(
            """--------
              |       |
              |
              |              (o o)
              |               \|/
              |                |
              |               / \
            """.stripMargin)
          println("Congrats, you won!")
          print("The word was '")
          print(word)
          println("'")
          println()
          println("Would you like to play again?")
          println()
          return 1 /* winning results in one point */
        }
        else {
          print(answer)
          println(" is not the word.")
          println()
          wrongGuesses = wrongGuesses + 1
          guesses += answer
        }
      } /* end of full word guess case */

      if (guesses contains answer) {
        println("You already guessed that letter!  Try again.")
        println()
      }
      else {
        /* is the letter guessed part of the word */
        if (word contains answer) {
          guesses += answer
          /* runs through the word and finds each instance of answer
           * then replaces the same index of emptyWord with answer. */
          for (index <- 0 to word.length - 1) {
            if (word(index).toString == answer) {
              emptyWord(index) = answer /* replacing the answer char in the empty word */
            }
          }
          /* checking for win condition */
          if (!(emptyWord contains "_")) {
            gameOver = true
            println()
            println(
              """--------
                |       |
                |
                |              (o o)
                |               \|/
                |                |
                |               / \
              """.stripMargin)
            println("Congrats, you won!")
            print("The word was '")
            print(word)
            println("'")
            println()
            println("Would you like to play again?")
            println()
            return 1 /* winning results in one point */
          }
        } /* end of cases where guess is correct */
        /* if guess isn't correct, it is automatically wrong */
        else {
          wrongGuesses = wrongGuesses + 1
          guesses += answer
        }
        /* checking for losing condition */
        if (wrongGuesses == 6) {
          gameOver = true
          drawHangman(wrongGuesses)
          println(
            """YOU'VE BEEN HUNG
              |I'm sorry, you're out of guesses.
            """.stripMargin)
          print("The word was '")
          print(word)
          println("'")
          println("G A M E  O V E R")
          println()
          println("Would you like to play again?")
          println()
          return 0 /* losing results in zero points */
        }
      } /* end of incorrect guess cases */
    } /* end of while */

    return 0 /* default */
  }/* end of game */

  def generateRandWord (): String = {
    var count = 0
    /* grabbing the number of lines/words in file for random
    *  number generater */
    for (line <- Source.fromFile("dictionary.txt").getLines()){
      count = count + 1
    }

    /* set random number and reset count to traverse the file again */
    var randLine = r.nextInt(count)
    count = 0

    for (line <- Source.fromFile("dictionary.txt").getLines) {
      count = count + 1
      if (count == randLine) {
        return line
      }
    }

    /* buffer takes the .txt file, turns it to a stream, and drops the first rand - 1
    *  lines so that the immediate next line is line rand
    var buffer = Source.fromFile("dictionary.tx").getLines().toStream.drop(r.nextInt(count - 1))
    var word = buffer.next()

    need to figure out which is worse...two for loops or one for loop and a stream, how
    much memory does a stream actually use? */

    return ""

  }/*end of generateRandWord()*/


  /* takes the number of wrong guesses as an int
  *  and prints the corresponding hangman picture*/
  def drawHangman (wrongGuesses: Int): Unit = {
    wrongGuesses match {
      case 0 => println(
        """--------
          |       |
        """.stripMargin)
      case 1 => println(
        """--------
          |       |
          |      ( )
        """.stripMargin)
      case 2 => println(
        """--------
          |       |
          |      ( )
          |       |
          |       |
        """.stripMargin)
      case 3 => println(
        """--------
          |       |
          |      ( )
          |       |/
          |       |
        """.stripMargin)
      case 4 => println(
        """--------
          |       |
          |      ( )
          |      \|/
          |       |
        """.stripMargin)
      case 5 => println(
        """--------
          |       |
          |      ( )
          |      \|/
          |       |
          |      /
        """.stripMargin)
      case 6 => println(
        """--------
          |       |
          |     (x x)
          |      \|/
          |       |
          |      / \
        """.stripMargin)
    }

  }/* end of drawHangman */

}/* end of Hangman object */
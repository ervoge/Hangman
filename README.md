# Hangman
This is a simple hangman game implemented in Scala. The game requires a file named `dictionary.txt` to be in the same directory as `Hangman.scala` in order to work.

As you play, the game will keep track of your record--can you stay above a 50-50 record?

If the dictionary is not big enough for you, you can add words to the game!

## Example Gameplay

```Welcome to the game of Hangman! What would you like to do?

GAME MENU
- play hangman: PLAY
- add a word to the dictionary: ADD
- close game: QUIT
        

You're current score is 0 wins out of 0 games.

Please input your answer:
```

```--------
       |
      ( )
        
_ e _ e _ _ _

Current Guessed Letters: d, e
What do you guess?: 
```

```--------
       |
     (x x)
      \|/
       |
      / \
        
YOU'VE BEEN HUNG
I'm sorry, you're out of guesses.
            
The word was 'memento'
G A M E  O V E R

Would you like to play again?

GAME MENU
- play hangman: PLAY
- add a word to the dictionary: ADD
- close game: QUIT
        

You're current score is 0 wins out of 1 games.

Please input your answer: 
```

## Run Game

`scala Hangman.scala`


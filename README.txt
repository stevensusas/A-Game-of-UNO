=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 84368726
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O

  My game include a saving and loading function that allows players to temporarily save their game's state, and then
  load that game state whenever they wish. Note that saving a new game state will cover the last saved state, however.
  This function uses File I/O to save the game state to a csv file, and read from that file when loading the game state.

  2. Collections and Maps

  A significant portion of my game's model is aided by the data structure LinkedList. This data structure makes creating
  the game model very convenient because it is an unsorted data structure that can resize very conveniently. Core functionalities
  of the game are implemented with LinkedLists, and I used LinkedLists to represent important data such as the deck of
  the player, the AI, and the drawing deck, as well as past cards, etc.

  The TreeMap data structure is also used in the construction of the heuristic function of the AI that plays against
  the player. This data structure is chosen because the heuristic function maps a certain integer to certain cards, and
  the sorted nature of this data structure makes searching through it very convenient.

  3. JUnit Testable Components

  The implementation of this game follows the Model-view-controller design pattern, and the game model is JUnit testable
 and is not dependent on the components of the GUI. Test cases are added in the GameModelTest class under the folder tests.

  4. Subtyping/Inheritance

  Dynamic dispatch also proved to be very useful in the construction of the game model. There are several card types in this game,
  including the addition cards, Wild cards, Wild addition cards, and common number cards, etc. In this game model, these type
  of cards all implement the Card interface. They have distinct method implementation in several methods. The most important method
  in which every single card type has a different implementation is the checkPlayable() method, which checks if a certain card can
  be played against another certain card. In fact, the core logic of the entire UNO game is dependent on the playability of cards against
  cards.

  5. AI

  In order for the player to be able to play against someone, an AI is also designed in the game model. This AI applies a heuristic
  function that looks multiple steps ahead and tries to play its deck in a way optimized to defeat the player. Here is a description
  of its heuristics:

  Whenever it is the AI's turn to play a card, it takes a look at the current card, and see how many cards it has in its deck that can be
  used to play against this current card. Then, for each of the playable cards, it takes a look at all the played/discarded cards so far as well as its own deck, and count how many of these
  discarded cards can be played against each of the playable cards it currently has in its deck against the current cards. The higher this number,
  the better, because this parameter being larger means the finite drawing deck as well as the Player's deck contains less playable cards
  against the card this parameter is mapped to. Then, the AI simply plays the card that has the largest of this parameter.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

   GameModel.class: contains the essential Game model class with fields documenting important information such as the
   cards of the Player's deck, the AI's deck, the current card, the discarded cards, etc.

   Interface Card: Supertype of the classes NumberCard, AddTwoCard, WildCard, WildAddFourCard. Specifies methods that
   needs to be implemented by all kinds of cards.

   NumberCard.class: Class representing the type of cards that has a specified color and number.

   WildCard.class: Class representing the type of cards that does not have a specified color and number. Can be played
   against any other card other than the adding cards.

   AddTwoCard.class: Classes representing the type of cards that has a specified color, and if not responded with another
   adding card from the opponent, result in the opponent having to draw two cards.

   WildAddFourCard.class: Classes representing the type of cards that does not have a specified color, and if not responded with another
   adding card from the opponent, result in the opponent having to draw four cards. Can be played against any other type of cards.

   Initialize.class: Contains several inner classes that are represent the customized widget components that are in the GUI, including
    the Player's deck, the AI's deck, and the current card, as well as the announcer of winner/game's end state.

   Game.class: run this file to run the game. Puts the widget component together to complete the final layout of the GUI,
    adds action listeners to the buttons and widgets in the layout.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

   One stumbling block was to figure out how to add customized widgets with customized action listeners. I did not want to make
   the action of playing cards dependent on the default JButtons. Instead, I wanted to make the player able to simply click on the card
   they want to play and play them. This turned out to actually be quite difficult, as I had to figure out the corresponding repaint()
   method that would make the process correct and user-friendly.

   Another stumbling rock was simply as the program develops and becomes more complicated, debugging becomes much more difficult too,
   as it takes a lot of time and effort to find out specifically which component of the program contains the error/bug.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

    It is not a bad separation of functionality, I would say! The private state is encapsulated pretty well, although there were some
    methods that were intentionally left not encapsulated because I needed the calling of these methods to modify the inner state of the game
    model.

    A certain component I would refactor is definitely the code blocks that add action listeners to the widget components
    in the GUI layout. Many of the action listeners of each widget component were added through highly similar code, so if I have
    time to I would definitely go back and optimize these highly similar code through helper methods or other methods.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

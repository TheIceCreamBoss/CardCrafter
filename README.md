# **CPSC 210 Project**

## Flashcard Alternative: Studying Flashcards with Mini-games

### Proposal:

- ***What will the application do?***

The application would take in sets of inputs and flashcards.
The user would input terms and definitions and the application would store it.
Next, it would take in the flashcard inputs and use them as questions in a mini-game.
The faster you type out the terms, the more points you score.

- ***Who will use it?***

Students who need flashcards for studying can use this application to further improve their studying habits.
It is aimed to make studying more enjoyable, and hopefully be a more effective way to digest material and get it to stick in people's mind.

- ***Why is this project of interest to you?***

I personally use online flashcards as a way to study, but I found them tedious and repetitive.
It is difficult to retain the information solely by repetition. 
I feel like adding an engaging way or an alternative way to learn would improve my own ability to learn for other courses.

### User Stories:

- As a user, I want to be able to add multiple flashcards to the set.
- As a user, I want to be able to remove a flashcard from the set.
- As a user, I want to be able to view preexisting flashcards in the set.
- As a user, I want to be able to edit a preexisting flashcard in the set.
- As a user, I want to be able to rename a preexisting set.
- As a user, I want to be able to add multiple sets to a set collection.
- As a user, I want to be able to save my Sets, if I choose to.
- As a user, I want to be able to be able to load my Sets, if I choose to.
- As a user, I want to be able to use a set of flashcards and play a speed mini-game.
- As a user, I want to be able to see by previous scores in the mini-game.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking new set and entering a new to
generate a new set.
- You can generate the second required action related to adding Xs to a Y by clicking edit set, and choosing a set, and
shuffle cards in that set.
- You can generate the third required action related to adding Xs to a Y by clicking edit set, and edit card, and pick a
card you wish to edit, and enter the new desired text.
- You can locate my visual component by clicking the credits button in the main menu.
- You can save the state of my application by clicking the save button in the main menu.
- You can reload the state of my application by clicking the load button in the main menu.
- 
# Phase 4: Task 2

Sun Apr 09 17:05:58 PDT 2023

New Card Constructed.

Sun Apr 09 17:05:58 PDT 2023

New Card added to Set "Set1".

Sun Apr 09 17:06:02 PDT 2023

Card removed from Set "Set1".

Sun Apr 09 17:06:03 PDT 2023

Set "Set1" shuffled.

Sun Apr 09 17:06:43 PDT 2023

Card Difficulty Edited to "0".

Sun Apr 09 17:06:43 PDT 2023

Card Term Edited to "Term1".

# Phase 4: Task 3

Overall, the classes I've created makes sense, but there are many places I could refactor the code.
One thing that I could have implemented is making both my Set and SetCollection iterable. We learned this later in the
course, and this design pattern perfectly matches the behaviour of my classes. Making Set and SetCollection iterable
would have gotten rid of a lot of duplicate code, and made by program more cohesive. One other part of refactoring I
could have done was to improve the GUI panels to have their own classes. Right now, the FlashCardApp class has high
coupling, with several panel functions in the same class. It would make more sense to bring each panel into their
own class, and reduce that coupling.
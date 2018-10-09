# Regex to NFA

Created by: [Tyler Higgins](https://github.com/tylerhiggins)
  E-mail: tyler.higgins@wsu.edu

## Description

This program converts a regular expression from a text file (.txt) to an NFA.  In this implementation, the NFA will be represented as a list of transitions.

## How to build
1) Use the commmand `make` to build the java classes in Higgins_Regex directory.
2) To run, use the command `make run`.


## Files:
### -Regex.java
The main java file which reads the text file and creates the NFA.
### -NFA.java
Object that holds the start and final states, as well as all of the NFA's transitions.
### -Transition.java
Object that holds a single transition, which is the state1, state2 and the symbol.
### -README.md
File with description and instructions on how to build the program.
### -README.txt
text file version of README.md

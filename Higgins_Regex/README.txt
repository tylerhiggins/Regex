# Regex to NFA

Created by: Tyler Higgins
E-mail: tyler.higgins@wsu.edu

This program converts a regular expression from a text file (.txt) to an NFA using postfix form.  In this implementation, the NFA will be represented as a list of transitions.

How to build:
- Install the java compiler libraries: sudo apt install openjdk-8-jdk-headless
- Use the make file provided by typing "make" (without quotes) in the terminal to build the java classes.
- Use the command "make run" to run the java program.



Files:
	- Regex.java
		The main java file which reads the text file and creates the NFA.
	-NFA.java
		Object that holds the start and final states, as well as all of the NFA's transitions.
	-Transition.java
		Object that holds a single transition, which is the state1, state2 and the symbol.	
	-README.txt
		File with description and instructions on how to build the program.
	-regexpressions.txt
		File containing all of the sample regular expressions in postfix form based on the project description.

.SUFFIXES: .java .class
JC = javac
EXEC = Regex
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	 Regex.java \
	Transition.java \
	NFA.java

default: classes

run:
	java $(EXEC)

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

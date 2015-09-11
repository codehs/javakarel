# Makefile for jkarel directory
# Last modified on Sat Apr  4 15:14:03 2009 by eroberts
# -----------------------------------------------------
# This Makefile creates the karel.jar archive used to
# implement the Java-based version of Karel the Robot.

JAR = karel.jar
JAVAC = javac -d ../../../classes -classpath ../../../acm.jar


# ***************************************************************
# Main entries

all: $(JAR)
	@true


# ***************************************************************
# Java compilations

karel.jar: always
	@mkdir -p classes
	@echo "[Compiling source files]"
	@(cd src/stanford/karel ; $(JAVAC) *.java)
	@echo "[Creating karel.jar]"
	@(cd classes ; jar xf ../acm.jar)
	@(cd classes ; jar cf ../karel.jar */*/*.class)

always:

# ***************************************************************
# Standard entries to remove files from the directories
#    tidy  -- eliminate unwanted files
#    clean -- delete derived files in preparation for rebuild

tidy:
	rm -f -r *~ .,* core a.out

clean scratch: tidy
	rm -f -r $(JAR) classes

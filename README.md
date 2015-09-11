## CodeHS Karel the Dog in Java -- Fork of ACM.jar and Stanford Karel

This is a modified fork of the Stanford Karel and acm.jar packages

Learn more about ACM Java Task Force here: http://cs.stanford.edu/people/eroberts/jtf/

This is the origin ACM library licence: http://cs.stanford.edu/people/eroberts/jtf/documents/License.pdf

## Quickstart

To test out an example open the root directory in your terminal. Then navigate to SimpleKarel

    $ cd examples/SimpleKarel/
    $ javac -cp src:../../karel.jar src/BlankKarel.java
    $ java -cp src:../../karel.jar BlankKarel

Then compile and run the program. You'll need to add the src directory as well as the karel.jar directory
in the root to the classpath.

## Karel Commands

Karel's basic set of commands are:

    move();
    turnLeft();
    putBall();
    takeBall();

## Build Instructions

This directory rebuilds the karel.jar archive used to
implement the Java-based version of Karel the Dog.

To rebuild the karel.jar file from the command line on
Mac OS X or a Unix-based system, just type

    make all

The Makefile always recompiles all the source files and
doesn't try to be clever about dependencies, which is
relatively difficult for Java packages.

The directory includes a copy of acm.jar, which is
needed for the compilation process.  If you check out this
package, you should check to see whether there is a new
version of acm.jar and update it to the most recent version.
The contents of the acm.jar file are merged into karel.jar
so that students can work with only a single jar file.

/*
 * File: SuperKarel.java
 * ---------------------
 * This class implements an extension of the basic Karel class that
 * supports more operations.
 */

package stanford.karel;

import acm.util.*;
import java.awt.*;

/* Class: SuperKarel */
/**
 * Extended Karel class that supports the following features:
 *
 * <p><ul>
 * <li>Built-in implementations of the <code>turnRight</code>
 *     and <code>turnAround</code> methods.
 * <p>
 * <li>New methods to support adding color to corners (the
 *     command <code>paintCorner</code> and the predicate
 *     <code>cornerColorIs</code>).
 * <p>
 * <li>A predicate method <code>random(p)</code> which returns
 *     <code>true</code> with probability <code>p</code>.  If
 *     <code>p</code> is omitted, <code>random</code> returns
 *     <code>true</code> 50 percent of the time.
 * <p>
 * <li>A method <code>pause(ms)</code>, which delays processing
 *     for the specified number of milliseconds.  This method
 *     makes it possible to control animation much more
 *     effectively.
 * </ul>
 */

public class SuperKarel extends Karel {

	public static final Color BLACK = Color.BLACK;
	public static final Color BLUE = Color.BLUE;
	public static final Color CYAN = Color.CYAN;
	public static final Color DARK_GRAY = Color.DARK_GRAY;
	public static final Color GRAY = Color.GRAY;
	public static final Color GREEN = Color.GREEN;
	public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
	public static final Color MAGENTA = Color.MAGENTA;
	public static final Color ORANGE = Color.ORANGE;
	public static final Color PINK = Color.PINK;
	public static final Color RED = Color.RED;
	public static final Color WHITE = Color.WHITE;
	public static final Color YELLOW = Color.YELLOW;

/**
 * Creates a new <code>SuperKarel</code> object.  You will
 * not ordinarily need to call this method explicitly; it is
 * the default constructor and will be executed automatically
 * if you write a <code>SuperKarel</code> program.
 */
	public SuperKarel() {
		/* Empty */
	}

/**
 * Specifies the code for this <code>SuperKarel</code> program.
 * You need to provide a new implementation of this method
 * whenever you extend <code>SuperKarel</code>.
 */
	public void run() {
		/* Empty */
	}

/**
 * Causes Karel to turn right without moving from the current
 * corner.  This version of <code>turnRight</code> is implemented
 * as a built-in primitive and does not actually turn left three
 * times as is necessary if you write this method yourself.
 */
	public void turnRight() {
		checkWorld("turnRight");
		setDirection(KarelWorld.rightFrom(getDirection()));
		getWorld().trace();
	}

/**
 * Causes Karel to turn around without moving from the current
 * corner.  This version of <code>turnRight</code> is implemented
 * as a built-in primitive and does not actually turn left twice
 * as is necessary if you write this method yourself.
 */
	public void turnAround() {
		checkWorld("turnAround");
		setDirection(KarelWorld.oppositeDirection(getDirection()));
		getWorld().trace();
	}

/**
 * Paints the current corner in the specified color, which should
 * be one of the constants defined in the <code>SuperKarel</code>:
 * <code>BLACK</code>, <code>BLUE</code>, <code>CYAN</code>
 * <code>DARK_GRAY</code>, <code>GRAY</code>, <code>GREEN</code>
 * <code>LIGHT_GRAY</code>, <code>MAGENTA</code>, <code>ORANGE</code>
 * <code>PINK</code>, <code>RED</code>, <code>WHITE</code>,
 * <code>YELLOW</code>.
 *
 * @param The new color for the corner.
 */
	public void paintCorner(Color color) {
		KarelWorld world = getWorld();
		Point pt = getLocation();
		checkWorld("paintCorner");
		world.setCornerColor(pt.x, pt.y, color);
		world.trace();
	}

/**
 * Returns <code>true</code> if the current corner is painted
 * in the specified color.
 *
 * @param color The color you're testing for
 * @return <code>true</code> if the corner has that color
 */
	public boolean cornerColorIs(Color color) {
		KarelWorld world = getWorld();
		Point pt = getLocation();
		checkWorld("cornerColorIs");
		if (color == null) {
			return world.getCornerColor(pt.x, pt.y) == null;
		} else {
			return (color.equals(world.getCornerColor(pt.x, pt.y)));
		}
	}

/**
 * Returns <code>true</code> roughly 50 percent of the time,
 * as if Karel flips a coin.
 *
 * @return <code>true</code> with probablity 0.5
 */
	public boolean random() {
		checkWorld("random");
		return random(0.5);
	}

/**
 * Returns <code>true</code> with probability <code>p</code>,
 * where <code>p</code> is a number between 0 (never) and
 * 1 (always).
 *
 * @param p The probability of returning <code>true</code>
 * @return <code>true</code> with probablity <code>p</code>
 */
	public boolean random(double p) {
		checkWorld("random");
		return Math.random() < p;
	}

/**
 * Pauses Karel for the specified number of milliseconds.
 * This method makes it possible to write much better Karel
 * animations.
 *
 * @param ms The number of milliseconds to pause
 */
	public void pause(double ms) {
		checkWorld("pause");
		getWorld().trace();
		JTFTools.pause(ms);
	}

}

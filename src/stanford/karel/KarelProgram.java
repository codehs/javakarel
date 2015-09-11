/*
 * File: KarelProgram.java
 * -----------------------
 * This file implements the KarelProgram class.
 */

package stanford.karel;

import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class is a subclass of <code><a href="Program.html">Program</a></code>
 * that runs a turtle program.
 */
public class KarelProgram extends Program {

	public static final int NORTH = KarelWorld.NORTH;
	public static final int EAST = KarelWorld.EAST;
	public static final int SOUTH = KarelWorld.SOUTH;
	public static final int WEST = KarelWorld.WEST;

	public static final int INFINITE = KarelWorld.INFINITE;

	public static final int SIMPLE = KarelWorld.SIMPLE;
	public static final int FANCY = KarelWorld.FANCY;

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

/* Constructor: KarelProgram() */
/**
 * Creates a new turtle program.
 *
 * @usage KarelProgram program = new KarelProgram();
 */
	public KarelProgram() {
		world = createWorld();
		world.setRepaintFlag(false);
		world.setDisplayFlag(false);
		world.init(10, 10);
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		panel.add(BorderLayout.CENTER, world);
		controlPanel = new KarelControlPanel(this);
		world.setMonitor(controlPanel);
		panel.add(BorderLayout.WEST, controlPanel);
		add(panel);
		validate();
	}

/* Method: main() */
/**
 * Contains the code to be executed for each specific program subclass.  If
 * you are defining your own <code>KarelProgram</code> class, you need to
 * override the definition of <code>main</code> so that it contains the code
 * for your application.
 */
	public void main() {
		/* Empty */
	}

/* Method: getWorld() */
/**
 * Returns the <code>KarelWorld</code> object in which Karel lives.
 *
 * @usage KarelWorld world = getWorld();
 * @return The <code>KarelWorld</code> object in which Karel lives
 */
	public KarelWorld getWorld() {
		return world;
	}

/* Static method: getWorldDirectory() */
/**
 * Returns the default directory in which Karel's worlds live.
 *
 * @usage String path = KarelProgram.getWorldDirectory();
 * @return The directory in which Karel's worlds lives
 */
	public static String getWorldDirectory() {
		String dir = System.getProperty("user.dir");
		if (new File(dir, "worlds").isDirectory()) {
			dir += "/worlds";
		}
		return dir;
	}

/* Factory method: createWorld() */
/**
 * Creates the <code>KarelWorld</code> in which Karel lives.  Subclasses
 * can override this method to create their own <code>KarelWorld</code> types.
 *
 * @usage KarelWorld world = program.createWorld();
 * @return The <code>World</code> object in which Karel lives
 * @noshow student
 */
	protected KarelWorld createWorld() {
		return new KarelWorld();
	}

/* Protected method: isStarted() */
/**
 * Checks to see whether this program has started.
 * @noshow student
 */
	protected boolean isStarted() {
		if (world == null || !super.isStarted()) return false;
		Dimension size = world.getSize();
		if ((size == null) || (size.width == 0) || (size.height == 0)) return false;
		return true;
	}
	
	protected Karel getKarel() {
		return world.getKarel();
	}
	
	public void add(Karel karel) {
		add(karel, 1, 1, KarelWorld.EAST, KarelWorld.INFINITE);
	}
	
	public void add(Karel karel, int x, int y, int dir, int nBeepers) {
		karel.setLocation(x, y);
		karel.setDirection(dir);
		karel.setBeepersInBag(nBeepers);
		world.add(karel);
	}

	public void start(String[] args) {
		super.start(args);
	}

	protected void setStartupObject(Object obj) {
		super.setStartupObject(obj);
	}

	/** Returns the outermost class that implements stanford.karel.Karel
	 *
	 *  @return the outermost class implementing stanford.karel.Karel or null 
	 */
	protected Class findKarelClass() {
		// The security manager can get the current stack trace.
		// However, the method is protected, so we subclass anonymously
		// and define a public method that does the same thing.
		Class[] currentContext = new SecurityManager() {
			public Class[] publicClassContext() {
				return this.getClassContext();
			}
		}.publicClassContext();
		for (int i = currentContext.length - 1; i >= 0; i--) {
			Class currentClass = currentContext[i];
      System.out.print("Examining: ");
			while (currentClass != null) {
        System.out.print("->" + currentClass.getName());
				if ("stanford.karel.Karel".equals(currentClass.getName())) {
					break;
				}
        currentClass = currentClass.getSuperclass();
			}
      System.out.println();
			if (currentClass != null) {
				return currentContext[i];
			}
		}
		return null;
	}
	
	protected void startRun() {
		Karel karel = null;
		String karelClass = getParameter("karel");
		if (karelClass == null) {
      if (getStartupObject() instanceof Karel) {
			  karel = (Karel) getStartupObject();
      }
			if (karel == null) {
				try {
					karel = (Karel) findKarelClass().newInstance();
				} catch (Exception ex) {
				}
			}
			karelClass = karel.getClass().getName();
			karelClass = karelClass.substring(
					karelClass.lastIndexOf(".") + 1);
		} else {
			try {
				karel = (Karel) Class.forName(karelClass).newInstance();
			} catch (Exception ex) {
				System.out.println("Exception: " + ex);
			}
		}
		if (karel != null) {
			world.add(karel);
			setTitle(karelClass);
			String worldName = getParameter("world");
			if (worldName == null) worldName = karelClass;
			try {
			  URL url = new URL(getCodeBase(), "Worlds/" + worldName + ".w");
			  URLConnection connection = url.openConnection();
			  world.load(new InputStreamReader(connection.getInputStream()));
			} catch (Exception ex) {
        try {
          System.out.println("World: " + worldName);
          InputStream resourceStream 
              = this.getClass().getResourceAsStream(
                  "/worlds/" + worldName + ".w");
          world.load(new InputStreamReader(
            resourceStream
              ));
        } catch (Exception ex1) {
			    System.out.println("Exceptions: ");
          ex.printStackTrace();
          ex1.printStackTrace();
        }
			}
		}
		world.setRepaintFlag(true);
		world.setDisplayFlag(true);
		world.repaint();
		while (true) {
			started = false;
			synchronized (this) {
				while (!started) {
					try {
						wait();
					} catch (InterruptedException ex) {
						/* Empty */
					}
				}
			}
			try {
				if (karel == null) {
					main();
				} else {
					karel.run();
				}
			} catch (Exception ex) {
				if (errorDialog == null) errorDialog = new KarelErrorDialog(this);
				errorDialog.error(ex.getMessage());
			}
		}
	}
	
	void signalStarted() {
		synchronized (this) {
			started = true;
			notifyAll();
		}
	}

/* Private state */

	private KarelWorld world;
	private KarelControlPanel controlPanel;
	private KarelErrorDialog errorDialog;
	private boolean started;
}

/* Private class: KarelErrorDialog */
/**
 * The <code>KarelErrorDialog</code> class is used to display
 * error messages in Karel's world. 
 */
class KarelErrorDialog extends Dialog implements WindowListener, ActionListener {

	public KarelErrorDialog(KarelProgram program) {
		super(JTFTools.getEnclosingFrame(program.getWorld()), true);
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		setFont(DIALOG_FONT);
		setBackground(DIALOG_BGCOLOR);
		setResizable(false);
		addWindowListener(this);
		setLayout(new BorderLayout());
		HPanel hbox = new HPanel();
		VPanel vbox = new VPanel();
		bugIcon = new KarelBugIcon();
		okButton = new Button("OK");
		okButton.addActionListener(this);
		errorDisplay = new KarelErrorCanvas();
		errorDisplay.setFont(DIALOG_FONT);
		hbox.add("/width:" + LOGO_WIDTH + "/height:" + LOGO_HEIGHT, bugIcon);
		hbox.add("/stretch:both", errorDisplay);
		vbox.add("/stretch:both", hbox);
		vbox.add("/top:3/bottom:3/width:" + BUTTON_WIDTH + "/center", okButton);
		add(BorderLayout.CENTER, vbox);
		validate();
	}
	
	public void error(String msg) {
		errorDisplay.setText(msg);
		setVisible(true);
	}

/* WindowListener interface */

	public void windowClosing(WindowEvent e) {
		setVisible(false);
	}

	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }

/* ActionListener interface */

	public void actionPerformed(ActionEvent e) {
		Component source = (Component) e.getSource();
		if (source == okButton) windowClosing(null);
	}

/* Private constants */

	private static final int DIALOG_WIDTH = 330;
	private static final int DIALOG_HEIGHT = 170;
	private static final int LOGO_WIDTH = 100;
	private static final int LOGO_HEIGHT = 100;
	private static final int BUTTON_WIDTH = 60;
	private static final Font DIALOG_FONT = new Font("Dialog", Font.PLAIN, 12);
	private static final Color DIALOG_BGCOLOR = Color.RED;

/* Private state */

	private Canvas bugIcon;
	private Button okButton;
	private KarelErrorCanvas errorDisplay;

}

class KarelErrorCanvas extends Canvas {

	public KarelErrorCanvas() {
		/* Empty */
	}
	
	public void setText(String msg) {
		errorText = msg;
		repaint();
	}
	
	public void paint(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = LEFT_MARGIN;
		int y = TOP_MARGIN + fm.getAscent();
		int height = fm.getHeight();
		int limit = getSize().width - RIGHT_MARGIN;
		StringTokenizer tokenizer = new StringTokenizer(errorText, " ", true);
		while (tokenizer.hasMoreTokens()) {
			String word = tokenizer.nextToken();
			int width = fm.stringWidth(word);
			if (x + width > limit) {
				if (x > LEFT_MARGIN && !word.equals(" ")) {
					x = LEFT_MARGIN;
					y += height;
				}
			}
			g.drawString(word, x, y);
			x += width;
		}
	}

/* Private constants */

	private static final int LEFT_MARGIN = 5;
	private static final int RIGHT_MARGIN = 5;
	private static final int TOP_MARGIN = 40;

/* Private state */

	private String errorText;

}

class KarelBugIcon extends Canvas {
	public KarelBugIcon() {
		image = MediaTools.createImage(KAREL_BUG_IMAGE);
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}

	private String[] KAREL_BUG_IMAGE = {
		"47494638396146005400910000FFFFFFFFCBFF77777700000021F90401000001002C000000004600",
		"54004102FF8C8F19C3ED0FA374AADA3B80DE5BF82F70E2388220305CEA9195E62782C8DB76E198AE",
		"4ACD79839778093DB61B6993D32178C5A19308781D4549A5A106D3540DCEE8B07522552790690FDA",
		"0C6BBA6BE838B2334F6F1010230B3369AD15A6FC0F88C337B82241788868416698D817F808B8D5E8",
		"67E302A422544975888527C0259546D366244848E975E2F904846A3AE8C7CA4A9A97257668CB41F6",
		"71F7F5C386B4B4D810588A1663B4EA89220C1107F9578A5C14DCC8028D7D24696D35ECCCBD942DCE",
		"0C7E3D7EBE7D3A67FCC1A7A749CEB9FE4E6A31045F0D4BED951A9A0A6AA293B47C7C6285F8055096",
		"AD5FC75E153CE22480427A72D2A9A03451E18240FF163140CCA8A65FAE5BEACEF850304BE1948E8A",
		"526D4249EF1EC39004BD35FC085254A63D2CBCB93A63925D4652F116C049F0B3075012B6D07479F3",
		"2DDC39A254F1A181FAC0D154683759EA48BAD58CD772488F923DDBC72CDAB5577C36606BCDEDDBB3",
		"60C3F2A46BF7D1D86E7921ED2D6486A2383D7F5B7EECC54BEECC778591AE43766985BF5C8DAFCC53",
		"D3CEDE4E7E2FF531DDDC0FE6CE9145F91255594FE2A87D9D1FD26AA3DA9FC8D52E497AD67409E428",
		"D2BAE44D43981075BF8ABE21BF08CE86B743D3963281AC5B5A09C677BA3F6D8C543C68F5D91CB36B",
		"CF49EBE672E99FAB8798B5D2BB09D1F7D2E06BCD7CB8A427E17D88D4E69B7285D1E0E14B8F279D4E",
		"185D00D35B4F8B28579E3298D157934F80C4D41E836160B5C883C7C8321B757434334C3126258821",
		"3F14CE2555344B594295730771488163D888D7063CED8DC8C0336135E5143B0D4665CE39170EC459",
		"8C7719C5A3657D05C65A74F11DE9826D8840C7E49089407964658A288625237061A0D6966469E9E5",
		"975D86C90D96649EA2D899B0A4A966376CAA50000021FF0B4D414347436F6E200403103900000001",
		"5772697474656E20627920474946436F6E76657274657220322E342E33206F66204D6F6E6461792C",
		"204D61792032352C2031393938003B"
	};
	
	private Image image;

  public void exit() {
    System.out.println("Exit");
    // Don't do anything
  }
}

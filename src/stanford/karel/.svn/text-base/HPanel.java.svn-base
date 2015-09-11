/*
 * File: HPanel.java
 * -----------------
 * This file implements a CSPanel subclass that is useful for
 * creating a horizontal assembly of components.  The details
 * of its operation are described in the HVLayout manager,
 * which is common to both HPanels and VPanels.
 */

package stanford.karel;

import java.awt.*;

class HPanel extends Panel {
	public HPanel() {
		setLayout(new HVLayout(HVLayout.HORIZONTAL));
	}

	public Component add(String constraint) {
		return add(constraint, null);
	}

	public Component add(Component comp) {
		return add("", comp);
	}

	public Component add(String constraint, Component comp) {
		if (comp == null) comp = new EmptyCanvas();
		return super.add(constraint, comp);
	}

}

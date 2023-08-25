package autopilot;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SimFrame extends JFrame {
	
	public SimFrame() {
		this.add(new SimPanel());
		this.setTitle("Tesla Autopilot Simulator");
		this.setBackground(Color.gray);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}

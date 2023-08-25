package autopilot;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Cars extends Rectangle {
	
	int yVelocity;
	Random random;
	
	public Cars(int x, int y, int width, int height) {
		super(x, y, width, height);
		random = new Random();
		yVelocity = random.nextInt(-1, 2);
	}
	
	public Cars(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		yVelocity = speed;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
		y -= yVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public void setYVelocity(int speed) {
		yVelocity = speed;
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == e.VK_DOWN) {
			yVelocity++;
		}
		if (e.getKeyCode() == e.VK_UP) {
			yVelocity--;
		}
	}
}

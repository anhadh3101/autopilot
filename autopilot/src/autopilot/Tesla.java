package autopilot;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Tesla extends Rectangle {
	
	int xVelocity = 0;
	int moved = 0;
	boolean leftBlinker = false;
	boolean rightBlinker = false;
	
	public Tesla(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
		x += xVelocity;
		if (leftBlinker || rightBlinker) {
			moved++;
		}
		if (moved == 200) {
			xVelocity = 0;
			moved = 0;
			leftBlinker = false;
			rightBlinker = false;
		}
	}
	
	public void turnRight() {
		rightBlinker = true;
		xVelocity++;
	}
	
	public void turnLeft() {
		leftBlinker = true;
		xVelocity--;
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == e.VK_LEFT) {
			xVelocity--;
			leftBlinker = true;
		}
		if (e.getKeyCode() == e.VK_RIGHT) {
			xVelocity++;
			rightBlinker = true;
		}
	}
}

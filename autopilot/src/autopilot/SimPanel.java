package autopilot;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SimPanel extends JPanel implements Runnable{
	
	public static final int SIM_WIDTH = 1000;
	public static final int SIM_HEIGHT = 1000;
	public static final int CAR_WIDTH = 50;
	Thread myThread;
	Image image;
	Graphics graphics;
	Random random = new Random();
	Tesla dikro;
	Cars[] cars;
	boolean disable = false;
	
	
	public SimPanel() {
		newTesla();
		newCars();
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(new Dimension(SIM_WIDTH, SIM_HEIGHT));
		myThread = new Thread(this);
		myThread.start();
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		for (int i = 1; i <= 4; i++) {
			g.drawLine(i * (SIM_WIDTH / 5), 0, i * (SIM_WIDTH / 5), SIM_HEIGHT);
		}
		dikro.draw(g);
		for (int i = 0; i < cars.length; i++) {
			if (cars[i] != null) {
				cars[i].draw(g);
			}
		}
		
	}
	
	public void newTesla() {
		dikro = new Tesla((SIM_WIDTH / 2) - (CAR_WIDTH / 2), (SIM_HEIGHT / 2) - (CAR_WIDTH / 2), CAR_WIDTH, CAR_WIDTH);
	}
	
	public void newCar(int index) {
		int i = random.nextInt(0, 4);
		cars[index] = new Cars(i * (SIM_WIDTH / 5) - 5 * CAR_WIDTH / 2, SIM_HEIGHT, CAR_WIDTH, CAR_WIDTH, 3);
	}
	
	public void newCars() {
		cars = new Cars[5];
		for (int i = 0; i < cars.length; i++) {
			int show = random.nextInt(0, 3);
			int ycoord = random.nextInt(0, 350);
			if (show != 0) {
				if (show == 1) {
					ycoord = random.nextInt(0, 350);
					cars[i] = new Cars(i * (SIM_WIDTH / 5) - 5 * CAR_WIDTH / 2, ycoord, CAR_WIDTH, CAR_WIDTH);
				}
				else {
					ycoord = random.nextInt(650, 1000);
					cars[i] = new Cars(i * (SIM_WIDTH / 5) - 5 * CAR_WIDTH / 2, ycoord, CAR_WIDTH, CAR_WIDTH);
				}
			}
		}
	}
	
	public void move() {
		for (int i = 0; i < cars.length; i++) {
			if (cars[i] != null) {
				cars[i].move();
			}
		}
		dikro.move();
	}
	
	public void autopilot() {
		// Maintain distance
		for (int i = 0; i < cars.length; i++) {
			if (cars[i] != null) {
				if (cars[i].getCenterX() == dikro.getCenterX() && Math.abs(cars[i].getCenterY() - dikro.getCenterY()) <= 150 && !disable) {
					changeLane(cars[i]);
				}
			}
		}
	}
	
	public void proximity() {
		for (int i = 0; i < cars.length; i++) {
			if (cars[i] != null && Math.abs(cars[i].getCenterX() - dikro.getCenterX()) <= 250 && Math.abs(cars[i].getCenterY() - dikro.getCenterY()) <= 100) {
				disable = true;
			}
			else {
				disable = false;
			}
		}
	}
	
	public void changeLane(Cars car) {
		if (car.getCenterY() < dikro.getCenterY()) {
			dikro.turnLeft();
			car.setYVelocity(-1);
		}
		else {
			dikro.turnRight();
			car.setYVelocity(1);
		}
		dikro.move();
	}
	
	public void checkCollision() {
		for (int i = 0; i < cars.length; i++) {
			if (cars[i] != null) {
				if (cars[i].getCenterY() <= -50 || cars[i].getCenterY() >= 1050) {
					newCar(i);
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				//proximity();
				autopilot();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	
	public class ActionListener extends KeyAdapter {
		
		public void keyReleased(KeyEvent e) {
			for (int i = 0; i < cars.length; i++) {
				if (cars[i] != null) {
					cars[i].keyReleased(e);
				}
			}
			dikro.keyReleased(e);
		}
	}
}

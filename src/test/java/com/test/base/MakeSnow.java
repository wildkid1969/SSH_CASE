package com.test.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MakeSnow {
	public static void main(String[] args) {
		JFrame w = new JFrame();
		w.setSize(700, 700);
		w.setLocation(100, 10);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyPanel panel = new MyPanel();
		panel.setBackground(new Color(125, 190, 255));
		w.add(panel);
		w.setVisible(true);
		
		Thread t = new Thread(panel);
		t.start();
		
	}
}

class MyPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = -2869477696663828149L;
	
	int[] x = new int[400];
	int[] y = new int[400];

	MyPanel() {
		for (int i = 0; i < 400; i++) {
			x[i] = (int) (Math.random() * 1240);
			y[i] = (int) (Math.random() * 700);
		}
	}

	private static int[] size = new int[] { 20, 30, 40 };
	private static int[] snow = new int[400];

	public void paint(Graphics g) {
		Random r = new Random();
		super.paint(g);
		g.setColor(Color.WHITE);
		for (int i = 0; i < 400; i++) {
			int s = snow[i];
			if (s < 10) {
				Font f = new Font(null, Font.ROMAN_BASELINE, size[r.nextInt(3)]);
				g.setFont(f);
				snow[i] = f.getSize();
			} else {
				Font f = new Font(null, Font.ROMAN_BASELINE, s);
				g.setFont(f);
			}
			g.drawString("*", x[i], y[i]);
		}
	}

	public void run() {
		Random r = new Random();
		int flag = 0;
		while (true) {
			for (int i = 0; i < 400; i++) {
				y[i]++;
				flag = r.nextInt(2);
				if (flag > 0) {
					x[i]++;
				}
				if (x[i] > 1240) {
					x[i] = 0;
				}
				if (y[i] > 700) {
					y[i] = 0;
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}

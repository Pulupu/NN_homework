package nn;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel_2d_rbfn extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int scale = 50;
	int x1 = 10;
	int y1 = 0;
	int x2 = -10;
	int y2 = 0;
	ArrayList<ArrayList<String>> data;
	double[] weights;

	public Panel_2d_rbfn(double[] weights, ArrayList<ArrayList<String>> data) {
		this.data = data;
		this.weights = weights;
		this.setSize(700, 700);
		this.setBackground(Color.BLACK);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.translate(this.getWidth() / 2, this.getHeight() / 2);
		g.setColor(Color.WHITE);
		g.drawLine(0, -500, 0, 500);
		g.drawLine(-500 , 0, 500, 0);

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).get(data.get(i).size() - 1).equals("1")) {
				g.setColor(Color.BLUE);
				g.fillOval((int) (-2 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (-2 + -(Double.valueOf(data.get(i).get(2)) * scale)), 4, 4);
			} else if (data.get(i).get(data.get(i).size() - 1).equals("2")) {
				g.setColor(Color.RED);
				g.fillOval((int) (-2 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (-2 + -(Double.valueOf(data.get(i).get(2)) * scale)), 4, 4);
			} else {
				g.setColor(Color.YELLOW);
				g.fillOval((int) (-2 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (-2 + -(Double.valueOf(data.get(i).get(2)) * scale)), 4, 4);
			}

		}

		g.setColor(Color.ORANGE);
		y1 = (int) (((-weights[1]) * x1 + weights[0]) / weights[2]) * scale;
		y2 = (int) (((-weights[1]) * x2 + weights[0]) / weights[2]) * scale;

		//g.drawLine(x1 * scale, -y1, x2 * scale, -y2);

	}

	public void updateGraphics(double[] weights) {
		this.weights = weights;
		this.repaint();
	}

}

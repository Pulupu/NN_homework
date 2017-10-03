package nn;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel_2d extends JPanel {
	int scale = 50;
	int x1 = 10;
	int y1 = 0;
	int x2 = -10;
	int y2 = 0;
	int ss = 5;
	ArrayList<ArrayList<String>> data;
	double[] weights;

	public Panel_2d(double[] weights, ArrayList<ArrayList<String>> data) {
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
		g.setColor(Color.WHITE);
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).get(data.get(i).size() - 1).equals("1")) {
				g.setColor(Color.BLUE);
				g.fillOval((int) (this.getWidth() / 2 - 3 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (this.getHeight() / 2 - 3 + -(Double.valueOf(data.get(i).get(2)) * scale)), 6, 6);
			} else if (data.get(i).get(data.get(i).size() - 1).equals("2")){
				g.setColor(Color.RED);
				g.fillOval((int) (this.getWidth() / 2 - 3 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (this.getHeight() / 2 - 3 + -(Double.valueOf(data.get(i).get(2)) * scale)), 6, 6);
			}else{
				g.setColor(Color.YELLOW);
				g.fillOval((int) (this.getWidth() / 2 - 3 + (Double.valueOf(data.get(i).get(1)) * scale)),
						(int) (this.getHeight() / 2 - 3 + -(Double.valueOf(data.get(i).get(2)) * scale)), 6, 6);
			}

		}

		g.setColor(Color.ORANGE);
		y1 = (int) (((-weights[1]) * x1 + weights[0]) / weights[2]) * scale;
		y2 = (int) (((-weights[1]) * x2 + weights[0]) / weights[2]) * scale;
		// g.drawLine(x1 * scale + this.getWidth() / 2, y1 + this.getHeight() / 2, x2 *
		// scale + this.getWidth() / 2,
		// y2 + this.getHeight() / 2);

		g.drawLine(x1 * scale + this.getWidth() / 2, -y1 + this.getHeight() / 2, x2 * scale + this.getWidth() / 2,
				-y2 + this.getHeight() / 2);

	}

	public void updateGraphics(double[] weights) {
		this.weights = weights;
		this.repaint();
	}

}

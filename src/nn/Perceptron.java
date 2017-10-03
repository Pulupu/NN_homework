package nn;

import java.util.ArrayList;

public class Perceptron {

	double recognition_rate = 0;
	double current_recognition_rate = 0;
	double correct = 0;
	boolean reach_recognition = false;
	public Perceptron(int times, double learning_rate,double recognition_rate, double threshold, double[] weights,
			ArrayList<ArrayList<String>> data) {
		this.recognition_rate = recognition_rate;
		calculate(learning_rate,threshold,weights,data);
	}

	private void calculate(double learning_rate, double threshold, double[] weights,
			ArrayList<ArrayList<String>> data) {		
		for (int i = 0; i < data.size(); i++) {
			double y = 0; // 向量內積總值
			for (int j = 0; j < weights.length; j++) {
				y += weights[j] * Double.valueOf(data.get(i).get(j));
			}
			if (y > 0 && data.get(i).get(data.get(0).size() - 1).equals("1")) {
				for (int k = 0; k < weights.length; k++) {
					weights[k] -= learning_rate * Double.valueOf(data.get(i).get(k));
					//System.out.println(weights[k]);
				}
			} else if (y < 0 && data.get(i).get(data.get(0).size() - 1).equals("2")) {
				for (int k = 0; k < weights.length; k++) {
					weights[k] += learning_rate * Double.valueOf(data.get(i).get(k));
					//System.out.println(weights[k]);
				}
			}else {
				correct++;
			}
			//System.out.println("sgn : " + y + " " + i);
		}
		current_recognition_rate = correct / data.size() * 100;
	}
	public boolean reach_recognition_rate() {
		if(current_recognition_rate>=recognition_rate) {
			reach_recognition = true;
		}else {
			reach_recognition = false;
		}
		return reach_recognition;
	}

}

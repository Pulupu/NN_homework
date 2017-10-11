package nn;

import java.util.ArrayList;

public class Perceptron {

	double recognition_rate = 0;
	double current_recognition_rate = 0;
	double correct = 0;
	double current_recognition_rate_test = 0;
	double correct_test = 0;
	boolean reach_recognition = false;
	int training_data_size = 0;

	public Perceptron(int times, double learning_rate, double recognition_rate, double threshold, double[] weights,
			ArrayList<ArrayList<String>> data) {
		this.recognition_rate = recognition_rate;
		this.training_data_size = data.size() / 3 * 2;
		calculate(learning_rate, threshold, weights, data);
	}

	private void calculate(double learning_rate, double threshold, double[] weights,
			ArrayList<ArrayList<String>> data) {
		correct = 0;
		for (int i = 0; i < training_data_size; i++) {
			double y = 0; // 向量內積總值
			for (int j = 0; j < weights.length; j++) {
				y += weights[j] * Double.valueOf(data.get(i).get(j));
			}
			if (y > 0 && data.get(i).get(data.get(0).size() - 1).equals("1")) {
				for (int k = 0; k < weights.length; k++) {
					weights[k] -= learning_rate * Double.valueOf(data.get(i).get(k));
					// System.out.println(weights[k]);
				}
			} else if (y < 0 && data.get(i).get(data.get(0).size() - 1).equals("2")) {
				for (int k = 0; k < weights.length; k++) {
					weights[k] += learning_rate * Double.valueOf(data.get(i).get(k));
					// System.out.println(weights[k]);
				}
			} else if (y < 0 && data.get(i).get(data.get(0).size() - 1).equals("0")) {
				for (int k = 0; k < weights.length; k++) {
					weights[k] += learning_rate * Double.valueOf(data.get(i).get(k));
					// System.out.println(weights[k]);
				}
			} else {
				correct++;
			}
		}
		current_recognition_rate = correct / training_data_size * 100;
	}

	public double testing(double[] weights, ArrayList<ArrayList<String>> data) {
		correct_test = 0;
		for (int i = training_data_size; i < data.size(); i++) {
			double y = 0; // 向量內積總值
			for (int j = 0; j < weights.length; j++) {
				y += weights[j] * Double.valueOf(data.get(i).get(j));
			}
			if (y > 0 && data.get(i).get(data.get(0).size() - 1).equals("1")) {

			} else if (y < 0 && data.get(i).get(data.get(0).size() - 1).equals("2")) {

			} else if (y < 0 && data.get(i).get(data.get(0).size() - 1).equals("0")) {

			} else {
				correct_test++;
			}

		}
		current_recognition_rate_test = correct_test / (data.size() - training_data_size) * 100;
		return current_recognition_rate_test;
	}

	public boolean reach_recognition_rate() {
		if (current_recognition_rate >= recognition_rate) {
			reach_recognition = true;
		} else {
			reach_recognition = false;
		}
		return reach_recognition;
	}

}

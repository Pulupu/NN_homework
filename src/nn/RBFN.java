package nn;

import java.util.ArrayList;

public class RBFN {
	ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> cluster_center = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> cluster1 = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> cluster2 = new ArrayList<ArrayList<String>>();
	ArrayList<String> category = new ArrayList<String>();
	ArrayList<Double> E = new ArrayList<Double>();
	ArrayList<Double> basis_function = new ArrayList<Double>();
	ArrayList<Double> cluster_sigma = new ArrayList<Double>();
	double[] weights;
	double learning_rate;
	double Eav = 0;

	public RBFN(double learning_rate, double threshold, double[] weights, ArrayList<ArrayList<String>> data) {
		this.data = data;
		this.weights = weights;
		this.learning_rate = learning_rate;
		k_means(2);
		int asd = 0;
		while(asd<1000) {
			training();
			training_recognition();
			asd++;
		}		
		System.out.println(this.weights[1]);
		System.out.println("Eav " + Eav);
	}

	private void training() {
		
		for (int i = 0; i < data.size(); i++) {
			double En = 0;
			double Fn = 0;
			Fn += weights[0];//theta
			for (int j = 0; j < category.size(); j++) {
				Fn += basis_function(i,j)*weights[j+1];
			}
			En = (Math.pow(Double.valueOf(data.get(i).get(3))-Fn, 2))/2;
			weights[0] = weights[0]+learning_rate*(Double.valueOf(data.get(i).get(3))-Fn);
			for (int j = 0; j < category.size(); j++) {
				weights[j+1] = weights[j+1]+learning_rate*(Double.valueOf(data.get(i).get(3))-Fn)*basis_function(i,j);
			}
			//System.out.println("En " + En);
			Eav += En;
		}
		Eav = Eav/data.size();
	}
	
	private void training_recognition() {
		double correct_amount = 0;
		double deviation = 0.2;
		double recognition_rate = 0;
		for (int i = 0; i < data.size(); i++) {
			double En = 0;
			double Fn = 0;
			Fn += weights[0];//theta
			for (int j = 0; j < category.size(); j++) {
				Fn += basis_function(i,j)*weights[j+1];
			}
			En = (Math.pow(Double.valueOf(data.get(i).get(3))-Fn, 2))/2;
			System.out.println("En  "+En);
			if(En<deviation) {
				correct_amount++;
			}
		}
		recognition_rate = correct_amount/data.size();
		System.out.println("correct_amount  "+correct_amount);
		System.out.println("recognition_rate  "+recognition_rate);
	}

	@SuppressWarnings("unchecked")
	private void k_means(int k) {
		for (int i = 0; i < data.size(); i++) {
			if (!category.contains(data.get(i).get(data.get(i).size() - 1))) {
				if (category.size() == k) {
					break;
				}
				cluster_sigma.add(1.0);// cluster amount
				category.add(data.get(i).get(data.get(i).size() - 1));
				cluster_center.add((ArrayList<String>) data.get(i).clone());
			}
		}
		for (int i = 0; i < 5; i++) {
			cluster_sorting();
			center_calculate(cluster1, 0);
			center_calculate(cluster2, 1);
			//System.out.println(cluster_center);
		}
	}

	private void cluster_sorting() {
		for (int i = 0; i < data.size(); i++) {
			double cluster1_dist, cluster2_dist;
			// first cluster distance
			cluster1_dist = Math.sqrt(Math
					.pow(Double.valueOf(data.get(i).get(1)) - Double.valueOf(cluster_center.get(0).get(1)), 2)
					+ Math.pow(Double.valueOf(data.get(i).get(2)) - Double.valueOf(cluster_center.get(0).get(2)), 2));
			// second cluster distance
			cluster2_dist = Math.sqrt(Math
					.pow(Double.valueOf(data.get(i).get(1)) - Double.valueOf(cluster_center.get(1).get(1)), 2)
					+ Math.pow(Double.valueOf(data.get(i).get(2)) - Double.valueOf(cluster_center.get(1).get(2)), 2));
			if (cluster1_dist < cluster2_dist) {
				cluster1.add(data.get(i));
			} else {
				cluster2.add(data.get(i));
			}
		}
	}

	private void center_calculate(ArrayList<ArrayList<String>> cluster, int cluster_num) {
		double x = 0;
		double y = 0;
		double cluster_dist = 0;

		for (int i = 0; i < cluster.size(); i++) {
			x += Double.valueOf(cluster.get(i).get(1));
			y += Double.valueOf(cluster.get(i).get(2));
		}
		x = x / cluster.size();
		y = y / cluster.size();

		for (int i = 0; i < cluster.size(); i++) {
			cluster_dist += Math.pow(
					Double.valueOf(cluster.get(i).get(1)) - Double.valueOf(cluster_center.get(cluster_num).get(1)), 2)
					+ Math.pow(Double.valueOf(cluster.get(i).get(2))
							- Double.valueOf(cluster_center.get(cluster_num).get(2)), 2);
		}
		cluster_dist = Math.sqrt(cluster_dist / cluster.size());

		cluster_sigma.set(cluster_num, cluster_dist);
		//System.out.println(cluster_sigma);
		cluster_center.get(cluster_num).set(1, String.valueOf(x));
		cluster_center.get(cluster_num).set(2, String.valueOf(y));
		cluster.clear();
	}

	private double basis_function(int data_num, int cluster_num) {
		double[] x = new double[2];
		x[0] = Math.pow(
				Double.valueOf(data.get(data_num).get(1)) - Double.valueOf(cluster_center.get(cluster_num).get(1)), 2);
		x[1] = Math.pow(
				Double.valueOf(data.get(data_num).get(2)) - Double.valueOf(cluster_center.get(cluster_num).get(2)), 2);
		return Math.exp((-(x[0] + x[1]) / (2 * cluster_sigma.get(cluster_num))));
	}
}

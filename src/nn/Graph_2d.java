package nn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Graph_2d extends JFrame implements ActionListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton file_bt = new JButton("Choose a file");
	JButton learning_bt = new JButton("Confirm");
	private JLabel file_name = new JLabel("File");
	private JLabel learning_rate_label = new JLabel("LearningRate");
	private JLabel recognition_rate_label = new JLabel("RecognitionRate");
	private JLabel threshold_label = new JLabel("Threshlod");
	private JLabel weights_label = new JLabel("Weights");
	private JLabel times_label = new JLabel("Times :");
	private JLabel after_label = new JLabel("After Training :");
	private JLabel test_label = new JLabel("Testing :");
	private JLabel after_weights_label = new JLabel("After Weights :");
	private JLabel after_recognition_label = new JLabel("After Recognition :");
	private JLabel test_recognition_label = new JLabel("Test Recognition :");
	JSlider learning_rate_slider = new JSlider();
	JSlider recognition_rate_slider = new JSlider();
	JTextField times_filed = new JTextField();

	Panel_2d p;
	// data
	int times = 100;
	double learning_rate = 0.1;
	double threshold = -1;
	double condition = 0.5;
	double[] weights = { threshold, 0, 1 };
	double[] weight = { threshold, 0, 1 };
	double recognition_rate = 50;
	double testing_rate = 0;
	ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	// data

	public Graph_2d() {
		this.setSize(1280, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		file_name.setBounds(800, 250, 150, 30);
		times_label.setBounds(800, 200, 50, 30);
		times_filed.setBounds(850, 200, 200, 30);
		file_bt.setBounds(800, 280, 150, 50);
		file_bt.addActionListener(this);
		learning_bt.setBounds(800, 550, 150, 50);
		learning_bt.addActionListener(this);
		learning_rate_label.setBounds(800, 125, 150, 30);
		recognition_rate_label.setBounds(1000, 125, 150, 30);
		threshold_label.setBounds(800, 50, 150, 50);
		threshold_label.setText("Treshold : " + threshold);
		weights_label.setBounds(1000, 50, 200, 50);
		weights_label.setText("Synaptic Weights : (" + weights[1] + "," + weights[2] + ")");
		after_label.setBounds(800, 350, 150, 50);
		after_weights_label.setBounds(800, 400, 200, 50);
		after_recognition_label.setBounds(800, 450, 150, 50);
		test_label.setBounds(1000, 350, 150, 50);
		test_recognition_label.setBounds(1000, 400, 150, 50);

		learning_rate_slider.setBounds(793, 150, 200, 50);
		learning_rate_slider.addChangeListener(this);
		learning_rate_slider.setValue(0);
		learning_rate_slider.setName("learning_rate_slider");
		recognition_rate_slider.setBounds(992, 150, 200, 50);
		recognition_rate_slider.addChangeListener(this);
		recognition_rate_slider.setValue(60);
		recognition_rate_slider.setName("recognition_rate_slider");
		this.add(file_bt);
		this.add(learning_bt);
		this.add(file_name);
		this.add(times_label);
		this.add(learning_rate_label);
		this.add(learning_rate_slider);
		this.add(threshold_label);
		this.add(weights_label);
		this.add(recognition_rate_slider);
		this.add(recognition_rate_label);
		this.add(times_filed);
		this.add(after_label);
		this.add(after_weights_label);
		this.add(after_recognition_label);
		this.add(test_label);
		this.add(test_recognition_label);
		p = new Panel_2d(weights, data);
		this.add(p).setLocation(0, 0);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (file_bt.getActionCommand().equals(e.getActionCommand())) {
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (chooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION) {
				file_name.setText(chooser.getSelectedFile().getName());
				after_weights_label.setText("After Weights :");
				after_recognition_label.setText("After Recognition :");
				try {
					ReadData(chooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} else if (learning_bt.getActionCommand().equals(e.getActionCommand())) {
			times = Integer.valueOf(times_filed.getText());
			Thread train = new Thread(new Runnable() {
				double learning_rate_run;
				double recognition_rate_run;
				double test_recognition_rate;
				Perceptron pc;

				// double training_rate_run = 0;
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = 0;
					double w0,w1,w2,test;
					setData();
					learning_bt.setEnabled(false);
					times_filed.setEditable(false);
					while (num < times) {
						pc = new Perceptron(times, learning_rate_run, recognition_rate_run, threshold, weights, data);
						if (pc.reach_recognition_rate()) {
							p.updateGraphics(weights);
							break;
						}
						p.updateGraphics(weights);
						num++;
						try {
							Thread.currentThread().sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					w0 = new BigDecimal(weights[0]).setScale(2,RoundingMode.HALF_UP).doubleValue();
					w1 = new BigDecimal(weights[1]).setScale(2,RoundingMode.HALF_UP).doubleValue();
					w2 = new BigDecimal(weights[2]).setScale(2,RoundingMode.HALF_UP).doubleValue();
					after_weights_label
							.setText("Weights : (" + w0 + "," + w1 + "," + w2 + ")");
					after_recognition_label.setText("RecognitionRate : " + pc.current_recognition_rate + " %");
					test_recognition_rate = pc.testing(weights, data);
					test = new BigDecimal(test_recognition_rate).setScale(2,RoundingMode.HALF_UP).doubleValue();
					test_recognition_label.setText("Test Recognition :" + test + "%");
					learning_bt.setEnabled(true);
					times_filed.setEditable(true);
				}

				public void setData() {
					learning_rate_run = learning_rate;
					recognition_rate_run = recognition_rate;
				}
			});
			train.start();
		}

	}

	public void initial() {
		this.times = 50;
		this.threshold = -1;
		this.condition = 0.5;
		this.weights = weight.clone();
		this.testing_rate = 0;
		this.data.clear();
	}

	@SuppressWarnings("resource")
	public void ReadData(String data_path) throws IOException {
		initial();
		FileReader fr = new FileReader(data_path);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String[] sp = br.readLine().split(" ");
			ArrayList<String> one_data = new ArrayList<String>();
			one_data.add(threshold + "");// first element is fixed as threshold
			for (int i = 0; i < sp.length; i++) {
				one_data.add(sp[i]);
			}
			data.add(one_data);
		}
		// System.out.println(weights[1]);
		System.out.println(data);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		if (learning_rate_slider == arg0.getSource()) {
			this.learning_rate = ((double) this.learning_rate_slider.getValue()) / 100;
			learning_rate_label.setText("LearningRate : " + this.learning_rate);
		}
		if (recognition_rate_slider == arg0.getSource()) {
			this.recognition_rate = this.recognition_rate_slider.getValue();
			recognition_rate_label.setText("RecognitionRate : " + this.recognition_rate + " %");
		}

	}

}

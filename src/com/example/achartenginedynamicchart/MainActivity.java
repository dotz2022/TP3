package com.example.achartenginedynamicchart;

import android.view.ViewGroup.LayoutParams;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.lang.Math;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import android.os.Handler;

import org.apache.commons.lang.ArrayUtils;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

//import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private Handler handler;

	static int Pr_L = 256;
	static int M = 64;
	static double TWO_PI = 2 * Math.PI;
	static int fs = 125;

	private GraphicalView mChart;

	private GraphicalView mChartView;

	private Double[] duration = new Double[] {

	1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 9.0

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final File a = (File)getIntent().getSerializableExtra("FILECONTENT");
		 
		 startTestThread();
		
		 
		 MainActivity.this.runOnUiThread(new Runnable() {
			   
			   
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			   /* public void run() {
			     try {
					openChart(a);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }*/
			});
		 
	}
	
	   protected void startTestThread() {
	        Thread t = new Thread() {
	            public void run() {
	                Log.d("Inchoo tutorial", "My thread is running");
	             
	               // mHandler.post(mUpdateResults);
	            }
	        };
	        t.start();
	    }

	@SuppressLint("NewApi")
	private void openChart(File file) throws FileNotFoundException {
		
	//	InputStream is = this.getResources().openRawResource(R.raw.sample);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String readLine = null;

		ArrayList<Double> datas = new ArrayList<Double>();

		try {
			// While the BufferedReader readLine is not null
			while ((readLine = br.readLine()) != null) {

				datas.add(Double.parseDouble(readLine));

			}

			// Close the InputStream and BufferedReader
			//is.close();
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8 };

		double a = 1.025641025641026e-4;
		double lol = 0.8;
		// Creating an XYSeries for Income
		XYSeries incomeSeries = new XYSeries("ECG Signal");

		// Adding data to Income and Expense Series

		/*
		 * for(int i=0;i<datas.size();i++){
		 * 
		 * incomeSeries.add(lol, datas.get(i)); // incomeSeries.
		 * 
		 * lol = lol + 1.025641025641026e-4; }
		 */

		XYSeries peak = new XYSeries("ECG Peaks");

		double testz = 0;
		int count = 0;

		ArrayList<Double> newList = datas;

	//	for (int start = 3; start < data.size(); start++) {

		//	newList.add(data.get(start));

		//}

		for (int k = 0; k < newList.size() -1; k++) {

			// System.out.println("NEW DATA IS " + newList.get(k));
			/*
			 * if ((datas.get(k) > datas.get(k - 1)) && (datas.get(k) >
			 * datas.get(k + 1)) && (datas.get(k) > 1.35)) { testz =
			 * datas.get(k); // peak.add(lol, datas.get(k - 2));
			 * 
			 * peak.add(lol + 1.025641025641026e-4 + 1.025641025641026e-4,
			 * datas.get(k));
			 * 
			 * } else {
			 */

			incomeSeries.add(lol, newList.get(k));

			// System.out.println("Added " + datas.get(k - 2));

			// }
			lol = lol + 1.025641025641026e-4;
		}

		// Frequency is 1.6Hz

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(incomeSeries);

		dataset.addSeries(peak);

		XYSeriesRenderer renderer2 = new XYSeriesRenderer();

		renderer2.setColor(Color.BLACK);
		renderer2.setPointStyle(PointStyle.CIRCLE);
		renderer2.setFillPoints(true);
		renderer2.setLineWidth(0);
		renderer2.setDisplayChartValues(true);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.BLUE);
		incomeRenderer.setPointStyle(PointStyle.POINT);
		incomeRenderer.setFillPoints(true);
		// incomeRenderer.setLineWidth(2);
		incomeRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("ECG Before Filtering");
		multiRenderer.setXTitle("Time Domain");
		multiRenderer.setYTitle("Amplitude");
		multiRenderer.setZoomButtonsVisible(true);
		multiRenderer.setShowGrid(true);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("m");
		for (int i = 0; i < x.length; i++) {

			// multiRenderer.addXTextLabel(i+1, mMonth[i]);
			long timestamp = ((Number) duration[i]).longValue() * 60 * 1000; // convert
																				// to
																				// seconds
			Date date = new Date(timestamp);
			String z = dateFormat1.format(date);
			// System.out.println(z);
			multiRenderer.addXTextLabel(i, z);

		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(renderer2);

		multiRenderer.setXLabelsColor(Color.BLACK);
		multiRenderer.setYLabelsColor(0, Color.BLACK);
		multiRenderer.setLabelsColor(Color.BLACK);
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		multiRenderer.setPanEnabled(true, false);
		multiRenderer.setZoomEnabled(true, false);
		// double[] pan = {0.00, 1.00};
		// multiRenderer.setPanLimits(pan);
		// multiRenderer.setMargins(new int[] { 2, 0, 0, 6 });
		mChartView = ChartFactory.getTimeChartView(this, dataset,
				multiRenderer, "m");

		// Getting a reference to LinearLayout of the MainActivity Layout
		LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

		// Getting a reference to LinearLayout of the MainActivity Layout
		LinearLayout chartContainer2 = (LinearLayout) findViewById(R.id.chart_container2);

		// Adding the Line Chart to the LinearLayout
		chartContainer.addView(mChartView);
		// Creating a Line Char

		/*-------------------------------ECG FILTERED GRAPH ---------------*/

		int M = newList.size(); // signal length

		double h[] = { 0.0002, -0.0004, 0.0000, 0.0007, 0.0000, -0.0015,
				-0.0017, -0.0004, -0.0002, -0.0026, -0.0044, -0.0022, 0.0009,
				-0.0007, -0.0046, -0.0027, 0.0050, 0.0078, 0.0017, -0.0009,
				0.0092, 0.0193, 0.0128, -0.0004, 0.0036, 0.0206, 0.0191,
				-0.0076, -0.0226, -0.0035, 0.0113, -0.0211, -0.0678, -0.0591,
				-0.0084, -0.0164, -0.1128, -0.1639, -0.0254, 0.2343, 0.3676,
				0.2343, -0.0254, -0.1639, -0.1128, -0.0164, -0.0084, -0.0591,
				-0.0678, -0.0211, 0.0113, -0.0035, -0.0226, -0.0076, 0.0191,
				0.0206, 0.0036, -0.0004, 0.0128, 0.0193, 0.0092, -0.0009,
				0.0017, 0.0078, 0.0050, -0.0027, -0.0046, -0.0007, 0.0009,
				-0.0022, -0.0044, -0.0026, -0.0002, -0.0004, -0.0017, -0.0015,
				0.0000, 0.0007, 0.0000, -0.0004, 0.0002 };

		Double[] ds = newList.toArray(new Double[newList.size()]);

		double[] dss = ArrayUtils.toPrimitive(ds);

		double[] newData = calculateMean(dss);

		double lolz = 0.0;
		FirFilter firFilter = new FirFilter(h);
		double y[] = new double[M]; // output signal
		for (int i1 = 0; i1 < M; i1++) {

			lolz = firFilter.filter(newData[i1]);

			y[i1] = lolz;

		}

		// Creating an XYSeries for Income
		XYSeries frequencyspec = new XYSeries("Filtered ECG WAVE");

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer filteredRenderer = new XYSeriesRenderer();
		filteredRenderer.setColor(Color.BLUE);
		filteredRenderer.setPointStyle(PointStyle.POINT);
		filteredRenderer.setFillPoints(true);
		// incomeRenderer.setLineWidth(2);
		filteredRenderer.setDisplayChartValues(false);

		for (int k = 0; k < y.length; k++) {

			frequencyspec.add(lol, y[k]);

			lol = lol + 1.025641025641026e-4;
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset frequencydataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		frequencydataset.addSeries(frequencyspec);

		// // Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer freqMultiRenderer = new XYMultipleSeriesRenderer();
		freqMultiRenderer.setXLabels(0);
		freqMultiRenderer.setChartTitle("ECG FILTERED GRAPH");
		freqMultiRenderer.setXTitle("Time Domain");
		freqMultiRenderer.setYTitle("Amplitude");
		freqMultiRenderer.setZoomButtonsVisible(true);
		System.out.println("-----------------------------");
		freqMultiRenderer.setDisplayValues(false);
		freqMultiRenderer.setShowGrid(true);
		freqMultiRenderer.setPanEnabled(true, false);
		freqMultiRenderer.setZoomEnabled(true, false);

		// freqMultiRenderer.setXAxisMax(2.5);
		// multiRenderer.setZoomEnabled(true, false);
		// multiRenderer.setMargins(new int[] {500, 0, 0, 1000});
		freqMultiRenderer.setPanEnabled(true, false);
		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same

		for (int i = 0; i < x.length; i++) {

			// multiRenderer.addXTextLabel(i+1, mMonth[i]);
			long timestamp = ((Number) duration[i]).longValue() * 60 * 1000; // convert
																				// to
																				// seconds
			Date date = new Date(timestamp);
			String z = dateFormat1.format(date);
			// System.out.println(z);
			freqMultiRenderer.addXTextLabel(i, z);

		}

		freqMultiRenderer.addSeriesRenderer(filteredRenderer);

		freqMultiRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
		// freqMultiRenderer.setBackgroundColor(Color.YELLOW);

		freqMultiRenderer.setXLabelsColor(Color.BLACK);
		freqMultiRenderer.setLabelsColor(Color.BLACK);

		mChart = ChartFactory.getTimeChartView(this, frequencydataset,
				freqMultiRenderer, "m");
		chartContainer2.addView(mChart);

		y = calculateMean(y);
		List<Double> b = new ArrayList<Double>();

		DecimalFormat df = new DecimalFormat("0.####");
		df.setRoundingMode(RoundingMode.DOWN);

		for (int i = 0; i < 125 * 4; i++) {

			// System.out.println("125 data is" + df.format(y[i]));

			b.add(Double.parseDouble(df.format(y[i])));
		}

		double maxValue = Collections.max(b);

		Double sample[] = new Double[b.size()];
		// double finaldata[] = new double[b.size()];

		System.out.println("final data length is" + y.length);

		// finaldata = ArrayUtils.toPrimitive(b.toArray(sample));

		double upper_thr = maxValue + 0.4 * maxValue;
		double lower_thr = maxValue - 0.35 * maxValue;

		double zeroArray[] = new double[3];
		zeroArray[0] = 0;
		zeroArray[1] = 0;
		zeroArray[2] = 0;

		int j = 0;

		int rows = 3;
		int cols = 3;
		double array[][] = new double[0][3];

		System.out.println("Row size= " + array.length);
		// System.out.println("Column size = " + array[1].length);

		List<double[][]> rowList = new ArrayList<double[][]>();

		for (int i = 0; i < y.length - 2; i++) {

			// System.out.println(y[i + 1]);
			if ((y[i + 1] < upper_thr && y[i + 1] > lower_thr)
					&& (y[i + 1] - y[i]) > 0 && (y[i + 2] - y[i + 1]) < 0) {

				double tempMatrix[][] = { { 0, i + 2, 0 } };

				// rowList.add(tempMatrix);

				array = append(array, tempMatrix);

				// System.out.println(Arrays.deepToString(array));
				j = j + 1;

			}
		}
		// System.out.println(Arrays.deepToString(array));
		// System.out.println("ARRAY IS " + array[306][1]);

		double[][] qrs = new double[0][3];

		int i = 0;
		j = 0;
		// System.out.println("Array length :" + array.length);

		while (i <= array.length - 2) {

			if (array[i + 1][1] - array[i][1] != 1) {
				double tempMatrix[][] = { { 0, array[i][1], 0 } };

				qrs = append(qrs, tempMatrix);

				i = i + 1;
			} else if (array[i + 1][1] - array[i][1] == 1) {
				double tempMatrix[][] = { { 0, array[i][1] + 0.5, 0 } };

				qrs = append(qrs, tempMatrix);
				i = i + 2;

			}
			j = j + 1;
		}

		// System.out.println("LENGTH OF QRS IS " + array[306][1]);

		double tempMatrix[][] = { { 0, array[j][1], 0 } };

		qrs = append(qrs, tempMatrix);
		double[] l_range = null;
		double[] r_range = null;
		double left = Math.floor(125 / 10);
		double right = Math.floor(125 / 10);
		double r = 0;
		double start;
		double finish = 0;
		// System.out.println(qrs.toString());

		/*--------------------------------START OF FIND_QRS---------------------*/

		for (i = 0; i < qrs.length; i++) {

			r = Math.floor(qrs[i][1]);

			// System.out.println("r is " + r);
			if ((r - left) <= 1) {

				start = 1;
			} else {
				start = r - left;
			}

			l_range = new double[(int) (r - start + 1)];

			// System.out.println("length of l_range is "+ l_range.length);
			int sp1 = (int) start;

			int counter = 1;
			for (int sp = sp1 - 1; counter - 1 < (r - start + 1); sp++, counter++) {

				l_range[counter - 1] = y[sp];
				// System.out.println("l_rangeeeee is " + y[sp]);
			}// done

			if ((r + right) >= y.length) {

				finish = y.length;
				// System.out.println(finish);
			} else {

				finish = r + right;
				// System.out.println(finish);
			}

			int templength = (int) (finish - (r + 1) + 1);
			sp1 = (int) r + 1 - 1;

			r_range = new double[(int) (templength)];
			// System.out.println("r_range length is " + r_range.length); //done
			// length is 12

			counter = 1;
			for (int sp = sp1; counter - 1 < r_range.length; sp++, counter++) {
				r_range[counter - 1] = y[sp];
				// System.out.println("rr_range is " + r_range[counter -1]);
				// //done

			}

			/*--------------------------------START OF TEMP_Q---------------------*/
			double[][] temp_q = new double[0][4];

			double[][] finalMatrix = { { 0, 0, 0, 0 } };
			double tempMatrix1[][] = { { 0, 0, 0, 0 } };
			int k = 0;

			for (j = 0; j < l_range.length - 2; j++) {
				int checker = 0;

				if ((l_range[j + 1] - l_range[j]) <= 0
						&& (l_range[j + 2] - l_range[j + 1] > 0)) {
					checker = 1;
					if (k == 0) {
						tempMatrix1[0][0] = (j + 2);

					} else if (k == 1) {

						tempMatrix1[0][1] = (j + 2);

					} else if (k == 2) {

						tempMatrix1[0][2] = (j + 2);

					} else if (k == 3) {

						tempMatrix1[0][3] = (j + 2);

					}

					k = k + 1;

				} else if (j == (l_range.length - 3)) {
					checker = 2;
					if (k == 0) {
						double tempMatrix2[][] = { { 1, 0, 0, 0 } };

						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix[rr][c] = tempMatrix1[rr][c]
										+ tempMatrix2[rr][c];
							}
						}

					} else if (k == 1) {

						double tempMatrix2[][] = { { 0, 1, 0, 0 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix[rr][c] = tempMatrix1[rr][c]
										+ tempMatrix2[rr][c];
							}

						}

					} else if (k == 2) {

						double tempMatrix2[][] = { { 0, 0, 1, 0 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix[rr][c] = tempMatrix1[rr][c]
										+ tempMatrix2[rr][c];
							}
						}

						// System.out.println(Arrays.deepToString(finalMatrix));
					} else if (k == 3) {
						double tempMatrix2[][] = { { 0, 0, 0, 1 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix[rr][c] = tempMatrix1[rr][c]
										+ tempMatrix2[rr][c];

							}

						}

					}
				}

				if (checker == 2) {
					temp_q = append(temp_q, finalMatrix);

				} else if (checker == 1) {

					temp_q = append(temp_q, tempMatrix1);

				} else {

				}
			}

			int max = max(temp_q);
			// System.out.println("MAX iS " + (max + start -1));
			/*--------------------------------END OF TEMP_Q---------------------*/

			/*--------------------------------START OF TEMP_S---------------------*/

			double[][] temp_s = new double[0][4];
			double[][] finalMatrix1 = { { 0, 0, 0, 0 } };
			double[][] tempMatrix2 = { { 0, 0, 0, 0 } };
			k = 0;
			for (j = 0; j < r_range.length - 2; j++) {
				int checker = 0;

				if ((r_range[j + 1] - r_range[j]) <= 0
						&& (r_range[j + 2] - r_range[j + 1] > 0)) {
					checker = 1;
					if (k == 0) {
						tempMatrix2[0][0] = (j + 2);
						// System.out.println(Arrays.deepToString(tempMatrix2));

					} else if (k == 1) {

						tempMatrix2[0][1] = (j + 2);
						// System.out.println(Arrays.deepToString(tempMatrix2));
					} else if (k == 2) {

						tempMatrix2[0][2] = (j + 2);
						// System.out.println(Arrays.deepToString(tempMatrix2));
					} else if (k == 3) {

						tempMatrix2[0][3] = (j + 2);
						// System.out.println(Arrays.deepToString(tempMatrix2));
					}

					k = k + 1;

				} else if (j == (r_range.length - 3)) {
					checker = 2;
					if (k == 0) {
						double temp[][] = { { r_range.length, 0, 0, 0 } };

						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix1[rr][c] = temp[rr][c]
										+ tempMatrix2[rr][c];
							}
						}

						// System.out.println(finalMatrix1[0][0]);
					} else if (k == 1) {

						double temp[][] = { { 0, r_range.length, 0, 0 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix1[rr][c] = temp[rr][c]
										+ tempMatrix2[rr][c];
							}
						}
						// System.out.println(finalMatrix1[0][1]);
					} else if (k == 2) {

						double temp[][] = { { 0, 0, r_range.length, 0 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix1[rr][c] = temp[rr][c]
										+ tempMatrix2[rr][c];
							}
						}
						// System.out.println(finalMatrix1[0][2]);
					} else if (k == 3) {
						double temp[][] = { { 0, 0, r_range.length, 0 } };
						for (int rr = 0; rr < 1; rr++) {
							for (int c = 0; c < 4; c++) {
								finalMatrix1[rr][c] = temp[rr][c]
										+ tempMatrix2[rr][c];
							}
						}
						// System.out.println(finalMatrix1[0][3]);
					}

				}

				if (checker == 2) {
					temp_s = append(temp_s, finalMatrix1);

				} else if (checker == 1) {

					temp_s = append(temp_s, tempMatrix2);

				} else {

				}
			}

			int min;
			min = min(temp_s);
			// System.out.println("MIN for temp_s iS " + (min));

			/*--------------------------------END OF TEMP_S---------------------*/

			qrs[i][0] = max(temp_q) + start - 1;
			qrs[i][2] = min(temp_s) + r;

			/*--------------------------------END OF FIND_QRS---------------------*/

			// System.out.println(Arrays.deepToString(rri_seq_bef));

		}
		/*--------------------------------START OF FIND_RRI---------------------*/
		double rri_seq_bef[][] = find_rri(qrs);
		// System.out.println("DATA IS " + Arrays.deepToString(rri_seq_bef));
		/*--------------------------------END OF FIND_RRI---------------------*/

		/*---------------------------------FIND ECTOPICS------------------------*/

		double[][] ectopic_qrs = find_ectopic(qrs, y);

		/*--------------------------------END OF FIND ECTOPICS---------------------*/

		/*---------------------------------REMOVE ECTOPIC-----------------------*/
		// remove ectopic peaks from detected QRS
		double[][] clean_qrs = null;

		// System.out.println(Arrays.deepToString(qrs));
		// System.out.println(Arrays.deepToString(qrs));

		clean_qrs = rem_ectopic(qrs, ectopic_qrs);

		/*--------------------------------END OF REMOVE ECTOPICS---------------------*/

		// to find the RRI sequence
		double[][] rri_seq;
		rri_seq = find_rri(clean_qrs);

		/*---------------------------------FIND BAD RRI-----------------------*/
		// identify and remove RR intervals that are too short or too long
		double[] bad_rri = find_bad_rri(rri_seq);

		// System.out.println(Arrays.deepToString(rri_seq));

		double sinus_rri[][] = rem_bad_rr(bad_rri, rri_seq);
		/*---------------------------------CALCULATE HRV PARAMS-----------------------*/

		HRV_params2(sinus_rri);

	}

	public void HRV_params2(double[][] rri_seq) {

		double[] temp = new double[rri_seq.length];
		// aRR, STD, avg HR, std HR
		for (int i = 0; i < rri_seq.length; i++) {

			for (int a = 0; a < 2; a++) {

				if (a == 1) {

					temp[i] = rri_seq[i][1];
					System.out.println(temp[i]);
				}
			}

		}

		double arr = mean(temp); // 0.98881
		double std_rri = sd(temp); // standard deviation is correct
		System.out.println("arr is " + arr);
		System.out.println("stdrri is " + std_rri);
	}

	public double[][] rem_bad_rr(double[] bad_rr, double[][] rri_seq) {

		int k = 1, m = 0;
		double[][] sinus_rri = new double[0][1];

		for (double lz : bad_rr) {
			// System.out.println(lz); //no problem
		}

		for (int i = 0; i < rri_seq.length; i++) {
			double[][] temp1 = { { 0, 0 } };
			double[][] temp2 = { { 0, 0 } };

			// System.out.println(k);

			if (k > bad_rr.length) {
				for (int a = 0; a < 2; a++) {

					if (a == 0) {
						temp1[0][a] = rri_seq[i][a];

					} else if (a == 1) {
						temp2[0][a] = rri_seq[i][a];

					}

				}
				double[][] finalMatrix = { { 0, 0 } };
				for (int rr = 0; rr < 1; rr++) {
					for (int c = 0; c < 2; c++) {
						finalMatrix[0][c] = temp1[0][c] + temp2[0][c];
						if (c == 1) {
							// System.out.println(temp2[0][c]);
						}
						// System.out.println(Arrays.deepToString(finalMatrix));
					}
				}

				// System.out.println(rri_seq[i][1]);
				sinus_rri = append(sinus_rri, finalMatrix);

			} else {
				if ((i + 1 == bad_rr[k - 1]) && k <= bad_rr.length) {
					k = k + 1;
				} else {
					for (int a = 0; a < 2; a++) {

						if (a == 0) {
							temp1[0][a] = rri_seq[i][a];

						} else if (a == 1) {
							temp2[0][a] = rri_seq[i][a];

						}

					}
					double[][] finalMatrix = { { 0, 0 } };
					for (int rr = 0; rr < 1; rr++) {
						for (int c = 0; c < 2; c++) {
							finalMatrix[0][c] = temp1[0][c] + temp2[0][c];
							if (c == 1) {
								// System.out.println(temp2[0][c]);
							}
							// System.out.println(Arrays.deepToString(finalMatrix));
						}
					}

					// System.out.println(rri_seq[i][1]);
					sinus_rri = append(sinus_rri, finalMatrix);
				}

			}

		}

		// System.out.println(Arrays.deepToString(sinus_rri));
		// System.out.println("Length is " + sinus_rri.length);
		return sinus_rri;
	}

	public double[] find_bad_rri(double[][] rri_seq) {
		// System.out.println(Arrays.deepToString(rri_seq));
		// set heart rate limits in bpm
		double max_hr = 150;
		double min_hr = 40;

		// calculate corresponding RRI limits
		double min_rr = 1 / (max_hr / 60);
		double max_rr = 1 / (min_hr / 60);

		double temp[] = new double[rri_seq.length];

		for (int i = 0; i < rri_seq.length; i++) {

			for (int a = 0; a < 3; a++) {

				if (a == 1) {

					temp[i] = rri_seq[i][a];
					// System.out.println(rri_seq[i][a]);
				}
			}
		}
		double mean = mean(temp);
		// System.out.println("mean 1 is " + mean);
		double med_rri = median(temp);
		// System.out.println("median is " + med_rri);
		double std_rri = sd(temp); // standard deviation is correct

		// tolerance value included because one standard deviation only works
		// with
		// long data and lots of noise.

		System.out.println("median is " + med_rri); // 0.9920
		System.out.println("std is " + std_rri);
		double tol;
		if (std_rri < 0.1) {
			tol = 5;

		} else if (std_rri > 0.1 && std_rri < 0.15) {
			tol = 4;
		} else {
			tol = 1;
		}

		// identify RR intervals that are too short or too long

		ArrayList<Double> bad_rr_al = new ArrayList<Double>();

		System.out.println("tol is " + tol);
		for (int i = 0; i < rri_seq.length; i++) {
			if ((rri_seq[i][1] > (med_rri + tol * std_rri))
					|| (rri_seq[i][1] < (med_rri - tol * std_rri))
					|| rri_seq[i][1] < min_rr || rri_seq[i][1] > max_rr) {

				bad_rr_al.add((double) i + 1);

			}
		}

		for (double a : bad_rr_al) {
			// System.out.println(a);
		}

		Double[] tempbad_rr = bad_rr_al.toArray(new Double[bad_rr_al.size()]);

		double[] bad_rr = ArrayUtils.toPrimitive(tempbad_rr);

		return bad_rr;

	}

	public double sum(double[] a) {
		if (a.length > 0) {
			double sum = 0;

			for (double i : a) {
				sum += i;
			}
			return sum;
		}
		return 0;
	}

	public double mean(double[] a) {
		double sum = sum(a);

		double mean = 0;
		mean = sum / (a.length);

		return mean;
	}

	public double median(double[] a) {
		double middle = a.length / 2;

		Arrays.sort(a);

		if (a.length % 2 == 1) {
			return a[(int) middle];
		} else {
			return (a[(int) (middle - 1)] + a[(int) middle]) / 2.0;
		}

	}

	public double sd(double[] a) {

		double sum = 0;
		double mean = mean(a); // correct
		double temp;
		double variance;
		for (int i = 0; i < a.length; i++) {

			temp = a[i] - mean;
			sum += Math.pow(temp, 2);

		}

		variance = sum / (a.length - 1);
		// System.out.println(Math.sqrt(variance));

		return Math.sqrt(variance);

	}

	public double[][] rem_ectopic(double[][] qrs, double[][] ectopic_qrs) {

		double[][] clean_qrs = new double[0][3];

		int k = 0, m = 0;
		for (int i = 0; i < qrs.length; i++) {

			if (ectopic_qrs.length > 0) {
				if (k <= ectopic_qrs.length && qrs[i][1] == ectopic_qrs[k][1]) {
					k = k + 1;

				} else {
					double[][] tempMatrix = { { 0, 0, 0 } };
					tempMatrix[0][0] = qrs[i][0];
					tempMatrix[0][1] = qrs[i][1];
					tempMatrix[0][2] = qrs[i][2];

					clean_qrs = append(clean_qrs, tempMatrix);

					m = m + 1;
					// System.out.println(m);
				}
			} else {
				double[][] tempMatrix = { { 0, 0, 0 } };
				tempMatrix[0][0] = qrs[i][0];
				tempMatrix[0][1] = qrs[i][1];
				tempMatrix[0][2] = qrs[i][2];

				clean_qrs = append(clean_qrs, tempMatrix);

				m = m + 1;

			}

		}
		// System.out.println(Arrays.deepToString(clean_qrs));
		// System.out.println(clean_qrs.length);
		return clean_qrs;

	}

	public double[][] find_ectopic(double[][] qrs, double[] chosen_seg) {

		double[] qrs_dur = new double[qrs.length];
		for (int i = 0; i < qrs.length; i++) {

			qrs_dur[i] = qrs[i][2] - qrs[i][0];
			// System.out.println(qrs_dur[i]);

		}

		// to find ectopics
		double ectopic_qrs[][] = new double[0][3];
		double thr_ect = 0.12 * 125; // the threshold to detect an ectopic in
										// terms of number of samples
		int j = 0;
		for (int i = 0; i < qrs_dur.length; i++) {

			// System.out.println(qrs_dur[i]);
			// System.out.println(thr_ect);
			if (qrs_dur[i] >= thr_ect) {
				double[][] tempMatrix = { { 0, 0, 0 } };
				tempMatrix[0][0] = qrs[i][0];
				tempMatrix[0][1] = qrs[i][1];
				tempMatrix[0][2] = qrs[i][2];

				ectopic_qrs = append(ectopic_qrs, tempMatrix);

				j = j + 1;
				// System.out.println(Arrays.deepToString(ectopic_qrs));
			}

		}

		// System.out.println(ectopic_qrs.length);

		return ectopic_qrs;
		// if there is ectopics, point it out in the graph.

	}

	public double[][] find_rri(double[][] qrs) {

		double[][] rri_seq = new double[0][1];
		int j = 0;

		for (int i = 0; i < (qrs.length - 1); i++) {
			double[][] finalMatrix = { { 0, 0 } };
			double[][] temp = { { 0, 0 } };
			double[][] temp1 = { { 0, 0 } };
			temp[0][0] = qrs[i + 1][1] / 125;

			// rri_seq = append(rri_seq, temp);

			// System.out.println(Arrays.deepToString(temp));

			temp1[0][1] = ((qrs[i + 1][1]) - qrs[i][1]) / 125;
			// System.out.println(Arrays.deepToString(temp1));

			// System.out.println(Arrays.deepToString(temp1));

			for (int rr = 0; rr < 1; rr++) {
				for (int c = 0; c < 2; c++) {
					finalMatrix[rr][c] = temp[rr][c] + temp1[rr][c];
					// System.out.println(finalMatrix[rr][c]);
					// System.out.println(Arrays.deepToString(finalMatrix));
				}
			}

			rri_seq = append(rri_seq, finalMatrix);

			// finalMatrix
			// System.out.println(qrs[i+1][1]/125);
			// System.out.println(qrs[i+1][1] - (qrs[i][2]/125));
			// rri_seq[j][0] = qrs[i+1][1] /125;
			// rri_seq[j][1] = (qrs[i+1][1] - qrs[i][2])/125;
			j = j + 1;
		}

		return rri_seq;

	}

	public int min(double[][] matrix) {
		int min = 10;
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {

				if (min > matrix[col][row] && matrix[col][row] != 0) {
					if ((int) matrix[col][row] != 0) {
						min = (int) matrix[col][row];
					}

				}
			}
		}

		return min;
	}

	public int max(double[][] matrix) {
		int max = (int) matrix[0][0];
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {
				if (max < matrix[col][row]) {
					max = (int) matrix[col][row];
				}
			}
		}
		return max;
	}

	public static double roundDown5(double d) {
		return (long) (d * 1e5) / 1e5;
	}

	public static double[][] append(double[][] a, double[][] b) {
		// System.out.println("A length is" + a.length);
		// System.out.println("B Length is" + b.length);

		double[][] result = new double[a.length + b.length][];
		// System.out.println("result length is" + result.length);

		System.arraycopy(a, 0, result, 0, a.length);

		System.arraycopy(b, 0, result, a.length, b.length);

		// System.out.println(Arrays.deepToString(result));

		return result;
	}

	public double[] calculateMean(double[] data) {
		CalculateMean[] threads;
		double[] newData = null;
		threads = new CalculateMean[1];
		for (int v = 0; v < 1; v++) {

			threads[v] = new CalculateMean(data);

			threads[v].start();
		}

		for (int v = 0; v < threads.length; v++) {
			try {

				threads[v].join();
				newData = threads[v].getData();
			} catch (Exception e) {

			}

		}

		return newData;
	}

	public short[] HanningWindow(short[] signal_in, int pos, int size) {
		for (int i = pos; i < pos + size; i++) {
			int j = i - pos; // j = index into Hann window function
			signal_in[i] = (short) (signal_in[i] * 0.5 * (1.0 - Math.cos(2.0
					* Math.PI * j / size)));
		}
		return signal_in;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

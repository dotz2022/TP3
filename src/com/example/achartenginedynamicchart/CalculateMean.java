package com.example.achartenginedynamicchart;

import android.annotation.SuppressLint;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculateMean extends Thread {
	
	
	public CalculateMean() {
		
	}
	
	 Thread t;
	 double[] data;
	 double[] newData; 
	   CalculateMean(double[] data) {
		
	      // Create a new, second thread
	      t = new Thread(this, "Mean Thread"); 
	      this.data = data;
	      newData = new double[this.data.length];
	      t.start(); // Start the thread
	   }
	@SuppressLint("NewApi")
	public void run() {
		
		double sum = 0.00;
		double meanValue = 0.0000;
		  DecimalFormat df = new DecimalFormat("0.####");
          df.setRoundingMode(RoundingMode.DOWN);
		
		for (int i =0; i < this.data.length; i++) {
			
			sum += this.data[i];
	
		}

		meanValue = sum / data.length;
		
		for (int i =0; i < data.length; i++) {
		
			
			newData[i] =  Double.parseDouble(df.format(data[i] - meanValue));
	  //  System.out.println(meanValue);
		}

		
	}
	
	public double[] getData() {
		
		return this.newData;
	}

}

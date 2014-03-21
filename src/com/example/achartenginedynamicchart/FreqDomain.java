package com.example.achartenginedynamicchart;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.apache.commons.lang.ArrayUtils;

import android.graphics.Color;

public class FreqDomain {
/*
 *     double step = TWO_PI / datas.size();
      double start = 0;
      double end = TWO_PI - TWO_PI / (datas.size());

      System.out.println("ending segment is " + end);
      
      
      double[] input = new double[]{
   		   1.11, 1.107,  1.101,1.09,  1.089, 1.097, 1.092,
   	       1.094, 1.091,1.067, 1.079, 1.152, 1.067,1.079,1.152,1.382, 1.14,  1.144,  1.14, 1.189, 1.172,
   	       1.146,  1.15, 1.157, 1.133, 1.164, 1.145, 1.127, 1.139, 1.15, 1.167, 1.191, 1.172, 1.172, 1.165,  1.184, 1.187,
   	       1.168, 1.185,1.174, 1.155, 1.159, 1.103, 1.071, 1.106,1.083, 1.061,1.069
        };
      
      DoubleFFT_1D fftDo = new DoubleFFT_1D(newList.size());
      
      //System.out.println("input size is" + input.length);
      //System.out.println("NEwList size is " + newList.size());
      Double[] ds = newList.toArray(new Double[newList.size()]);
     
      double[] dss = ArrayUtils.toPrimitive(ds);
      double[] fft = new double[newList.size() * 2];
     
      System.arraycopy(dss, 0, fft, 0, dss.length);
      fftDo.realForwardFull(fft);
     
      // Creating an  XYSeries for Income
         XYSeries frequencyspec = new XYSeries("Freq Wave");
         double z = 0.20;
         
      //   w=[0:2*pi/length(current_data):2*pi-2*pi/length(current_data)]';
        
         double stepfreqX = TWO_PI / newList.size();
         System.out.println("STEP FREQX IS " + stepfreqX);
      
         double freqXPair = stepfreqX;
         
         int i = 0;
         for (double X: fft) {
        	 
        //	 System.out.println("FFT IS " + X);
         }
         
         for (int b =0; b < fft.length; b+=2) {
        	 
        	 double real = fft[b];
      		double imag = fft[b + 1];
      		//System.out.println("REAL IS " + real + " IMAG IS " + imag);
          double result = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));

         	frequencyspec.add(freqXPair, result);
     		freqXPair += stepfreqX;
     //		System.out.println("MAGNITUDE IS " + result);
         }
         

         
         
        
       // Creating a dataset to hold each series
      XYMultipleSeriesDataset frequencydataset = new XYMultipleSeriesDataset();
       // Adding Income Series to the dataset
      frequencydataset.addSeries(frequencyspec);
       

      
  //    // Creating a XYMultipleSeriesRenderer to customize the whole chart
      XYMultipleSeriesRenderer freqMultiRenderer = new XYMultipleSeriesRenderer();
     freqMultiRenderer.setXLabels(0);
      freqMultiRenderer.setChartTitle("Frequency Spectrum");
      freqMultiRenderer.setXTitle("Frequency Domain");
      freqMultiRenderer.setYTitle("Magniture (DB)");
     freqMultiRenderer.setZoomButtonsVisible(true);
     System.out.println("-----------------------------");
   
     freqMultiRenderer.setShowGrid(true);
     freqMultiRenderer.setPanEnabled(true, false);
     freqMultiRenderer.setZoomEnabled(true, false);
     // freqMultiRenderer.setXAxisMax(2.5);
     // multiRenderer.setZoomEnabled(true, false);
    //  multiRenderer.setMargins(new int[] {500, 0, 0, 1000});
      freqMultiRenderer.setPanEnabled(true, false);
      // Adding incomeRenderer and expenseRenderer to multipleRenderer
      // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
      // should be same


      
      for(int b=0;b<7;b++){
      	
    	  
        
       	freqMultiRenderer.addXTextLabel(b, Double.toString(b));
   
       }
     
      
      
      freqMultiRenderer.addSeriesRenderer(incomeRenderer);
   
      freqMultiRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
      //freqMultiRenderer.setBackgroundColor(Color.YELLOW);
     
      freqMultiRenderer.setXLabelsColor(Color.BLACK);
      freqMultiRenderer.setLabelsColor(Color.BLACK);
      
     mChart = ChartFactory.getTimeChartView(this, frequencydataset, freqMultiRenderer,
              "m");
      chartContainer2.addView(mChart);

 */
}

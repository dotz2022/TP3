package com.example.achartenginedynamicchart;

public class FirFilter{
    
    private int length;
    private double delayLine[];
    private double[] impulseResponse;
    private int count = 0;
   
    public FirFilter(double coefs[]){
         this.impulseResponse = coefs;
         this.length = coefs.length;            
         this.delayLine = new double[length];                                      
    }

    public double filter(double newSample){       
    	 delayLine[count] = newSample;
    	 	        double result = 0.0;
    	 	        int index = count;
    		        for (int i=0; i<length; i++) {
    	 	            result += impulseResponse[i] * delayLine[index--];
    	 	            if (index < 0) index = length-1;
    		        }
    	 	        if (++count >= length) count = 0;
    	 	        return result;
    	 	    }
}

package com.example.achartenginedynamicchart;

public class filter {



	public filter() {
		
		int M = 512;       //signal length
        double Fs = 250.0;  //sample frequency
        double dt = 1.0/Fs; //sample period in time domain           
        double t[] = new double[M];  //time vector
        for(int i=0; i<M; i++){
        t[i] = dt*i;
        }                              
        double x[]= new double[M]; //input signal
        x[0] = 1.0;                         
                         
        double h[] = {0.2, 0.2, 0.2, 0.2, 0.2};
        FirFilter firFilter = new FirFilter(h);                             
        double y[] = new double[M];  //output signal           
        for(int i=0; i<M; i++){
        y[i] = firFilter.filter(x[i]);
        }                            
        
        
       
	}
	
}

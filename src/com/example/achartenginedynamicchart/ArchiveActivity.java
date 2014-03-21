package com.example.achartenginedynamicchart;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ArchiveActivity extends Activity implements
OnItemClickListener{
	
	// Defined Array values to show in ListView

	 ArrayList<String> values = new ArrayList<String>();
	 HashMap<String, File> fileRecord = new HashMap<String, File>();
		TextView mTextStatus;
		ScrollView mScrollView;
	 final Context context = this;
	 public static ListView listView ;
	Button button1;
	List<RowItem> rowItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		
		  File sdcard = Environment.getExternalStorageDirectory();
          walkdir(sdcard);
      
          generateAdapter();
          
	}
	
	
	public void generateAdapter() {
		
		  
        final DatabaseHandler db = new DatabaseHandler(this);
        
      
   // Get ListView object from xml
      listView = (ListView) findViewById(R.id.list);
  
      // Define a new Adapter
      // First parameter - Context
      // Second parameter - Layout for the row
      // Third parameter - ID of the TextView to which the data is written
      // Forth - the Array of data

      rowItems = new ArrayList<RowItem>();
      for (int i = 0; i < values.size(); i++) {
      	
      	//get filepath check with database check exist anot
      	
      	RowItem item;
      	
      	db.checkECGFilesExist(fileRecord.get(values.get(i)).getAbsolutePath());
      	
      	if (db.checkECGFilesExist(fileRecord.get(values.get(i)).getAbsolutePath())) {
      
      		 item = new RowItem(R.drawable.textfileicon, values.get(i), "Already in database");
      	} else {
      		 item = new RowItem(R.drawable.textfileicon, values.get(i), "Not in database yet");
      	}

          rowItems.add(item);
      }
      
      CustomListViewAdapter adapter = new CustomListViewAdapter(this,
        R.layout.listview_each_item, rowItems);

     
      // Assign adapter to ListView
     listView.setAdapter(adapter); 
     listView.setOnItemClickListener(this);
     
	}
	
	
	/*
	 * ImageButton imgbtn = (ImageButton) listView.findViewById(R.id.icon);
    
    imgbtn.setOnClickListener(new OnClickListener() {
 	   @Override
 	   public void onClick(View v) {
 	    // TODO Auto-generated method stub
 	   
 	    
 	    Toast.makeText(context, "HELsLO",
       	      Toast.LENGTH_LONG).show();
 	   }
 	 
 	   
 	  });*/
	 
	
	
	public void viewECG(View view) throws FileNotFoundException {

	    TextView txtView = (TextView) view.getTag();
	
		File file =fileRecord.get(txtView.getText());
		
		Intent intent = new Intent(this, MainActivity.class);
		
		ArrayList<Double> fileContent = null;
 	/*	try {
			fileContent = getFileContent(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		intent.putExtra("FILECONTENT", file);
		
		final Runnable r = new Runnable()
		{
		    public void run() 
		    {
		    	//startActivity(intent);
		        //handler.postDelayed(this, 1000);
		    }
		};
		startActivity(intent);
  	   }
	
	
	
	public ArrayList<Double> getFileContent(File file) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String readLine = null;

		ArrayList<Double> datas = new ArrayList<Double>();

		int i =0;
		try {
			// While the BufferedReader readLine is not null
			while ((readLine = br.readLine()) != null) {
				i++;
				if (i > 2) {
				datas.add(Double.parseDouble(readLine));
				}
			}

			// Close the InputStream and BufferedReader
			
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
		
	}
	
	
	
	public void haha(View view) throws FileNotFoundException {

	
	    TextView txtView = (TextView) view.getTag();
	    
	    
	    final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.file_content);
		dialog.setTitle("Content of file");
	    
		mTextStatus = (TextView) dialog.findViewById(R.id.TEXT_STATUS_ID);
		mScrollView = (ScrollView) dialog.findViewById(R.id.SCROLLER_ID);

		
		File file =fileRecord.get(txtView.getText());
		
		int count = 1;
	    StringBuilder text = new StringBuilder();
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String line;
	        while ((line = br.readLine()) != null) {
	            text.append(count + ".\t");
	        	text.append(line);
	            
	            text.append('\n');
	            count++;
	        }
	    }
	    catch (IOException e) {
	        Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
	        e.printStackTrace();
	    }
	 
	   scrollToBottom();
	   mTextStatus.setText(text);
	   
	   
	   mTextStatus.post(new Runnable() {

		    @Override
		    public void run() {

		        int lineCount    = mTextStatus.getLineCount();

		        Log.v("LINE_NUMBERS", lineCount+"");
		    }
		});
	   
		dialog.show();
		
		
		
	    Toast.makeText(context, txtView.getText(),
        	     Toast.LENGTH_LONG).show();
  	   }
	
	private void scrollToBottom()
	{
	    mScrollView.post(new Runnable()
	    { 
	        public void run()
	        { 
	            mScrollView.smoothScrollTo(0, mTextStatus.getBottom());
	        } 
	    });
	}
	
	
	 @SuppressLint("NewApi")
	@Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {
	
		 ImageButton imageButton;
		    final DatabaseHandler db = new DatabaseHandler(this);
		
            	         // custom dialog
            				final Dialog dialog = new Dialog(context);
            				dialog.setContentView(R.layout.custom);
            				dialog.setTitle("Add file to archive");
            	
            				final TextView listText = (TextView) view.findViewById(R.id.title);
            				final TextView lblDB = (TextView) view.findViewById(R.id.lblDB);
            				// set the custom dialog components - text, image and button
            		
            				final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
            				
            				//text.setText("Android custom dialog example!");
            				//ImageView image = (ImageView) dialog.findViewById(R.id.image);
            				//image.setImageResource(R.drawable.ic_launcher);
            				
            				//System.out.println(listText.getText());
            			
            				
            				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            				// if button is clicked, close the custom dialog
            				dialogButton.setOnClickListener(new OnClickListener() {
            					public void onClick(View v) {
            						
            						if (lblDB.getText().equals("Already in database")){
            							  Toast toast = Toast.makeText(getApplicationContext(),
            							           "File exist in database!!",
            							            Toast.LENGTH_SHORT);
            						        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            							      toast.show();
            							
            						} else {
            						
            						File file =fileRecord.get(listText.getText());
            						
            						db.addECGFiles(editText.getText().toString(), file.getAbsolutePath());
            						
            						}
            						generateAdapter();
            						dialog.dismiss();
            					}
            				});
            	 
            				dialog.show();
            	       
	 }
	 
	 /*
	   Toast toast = Toast.makeText(getApplicationContext(),
	            "Item " + (position + 1) + ": " + txtTechCharacteristic.getText(),
	            Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
	      toast.show();
	*/
	
            	         // popup.show();//showing popup menu  
    
	   /*  
    // ListView Clicked item index
    int itemPosition     = position;
    
    // ListView Clicked item value
    String  itemValue    = (String) listView.getItemAtPosition(position);
       
     // Show Alert 
     Toast.makeText(getApplicationContext(),
       "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
       .show();
  */

	public void walkdir(File dir) {

        File[] listFile;
        listFile = dir.listFiles();
        
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                  if (listFile[i].getName().toLowerCase().endsWith(".cnt")){
                  System.out.println(listFile[i].getName());
                  values.add(listFile[i].getName());
                  fileRecord.put(listFile[i].getName(),listFile[i]);
                  ////  files_list.add(listFile[i]);
                  
                  }
                }
            }
        }    
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
	}

}

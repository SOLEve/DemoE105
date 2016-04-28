package com.example.demopose105;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

public class Intro extends Activity
{
	Intent 			myIntent;
	ProgressBar 	progress;
	int 			progressStatus = 0;
	Handler			handler = new Handler();
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_intro_banesco);
        //setContentView(R.layout.activity_intro_sofitasa); 
        setContentView(R.layout.activity_intro_mercantil);
        
        
        myIntent 	= 	new Intent(this,Index.class);
        progress 	= 	(ProgressBar)findViewById(R.id.progressBarIntro);
        
        SQLiteDatabase 	database;
        database = openOrCreateDatabase("DBSQL",MODE_PRIVATE, null);
        
        database.execSQL("CREATE TABLE IF NOT EXISTS Facturas (" +
		"IdFactura INTEGER PRIMARY KEY," +
		"CedulaCliente VARCHAR," +
		"Fecha VARCHAR," +
		"Estatus VARCHAR," +
		"Monto DOUBLE(10)," +
		"PAN VARCHAR," +
		"Hora VARCHAR);");
        
        //database.execSQL("DELETE FROM Facturas");
        //database.execSQL("DROP TABLE Facturas");
        
        database.close();
     }
  
	 @Override
	public void onResume()
	 {
		 super.onResume();
		 
		 progressStatus = 0;
		 dialog1();
		 
		 new Handler().postDelayed(new Runnable() 
		 {
			 @Override
			public void run() 
			 {            
				 dialog2();
			 }
		 }, 5000);	 
	 }

	public void dialog1()
	{          	
		new Thread(new Runnable() 
        {
        
        	@Override
			public void run() 
        	{
	            while (progressStatus < 100) 
	            {
	                  progressStatus += 05;
	           
	                  handler.post(new Runnable() 
	                  {
	                	  @Override
						public void run() 
	                	  {
	                		  progress.setProgress(progressStatus);
	                	  }
	                  });
	                  
	               try {
	            	   Thread.sleep(200);
	               	} catch (InterruptedException e) {e.printStackTrace();}
	            }
        	}
         }).start();  
	}
		
	public void dialog2()
	{  
    	finish();
		myIntent.putExtra("TelfMerchant",	"04247128151");
		myIntent.putExtra("DireccionIP",	"172.17.1.42");
		myIntent.putExtra("Puerto",			"3042");
		this.startActivity(myIntent);	
	}


}

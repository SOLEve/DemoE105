package com.example.demopose105;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

public class Pantalla_4 extends Activity
{
	InputStream 			inputStream;
	DataOutputStream		dataOutputstream;
	ByteArrayOutputStream	Bytearrayoutputstream;
	byte[] 					response1 = new byte[ 512 ];
	byte[] 					response2 = new byte[ 512 ];
	int 					bytesRead = -1;  
	ProgressDialog 			progress,progress1;
	Intent 					myIntent ;
	Bundle 					bundle;
	Thread 					m_objThreadClient;
	Socket 					clientSocket	= new Socket();	
	String 					direccionIP;
	int 					puerto;
	
	byte [] request = 
	{(byte)(0x00),(byte)(0x89),
	(byte)(0x02),(byte)(0x00),(byte)(0x20),(byte)(0x00),(byte)(0x00),
	(byte)(0x00),(byte)(0x00),(byte)(0x00),(byte)(0x00),
	(byte)(0x00),(byte)(0x00),(byte)(0x00),(byte)(0x00)};
	String line;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_4_banesco);
		//setContentView(R.layout.activity_pantalla_4_sofitasa);
		setContentView(R.layout.activity_pantalla_4_mercantil);
		
		bundle=getIntent().getExtras();	
    	
		myIntent = new Intent(this, Pantalla_5.class);
		myIntent.putExtra("Cedula", 		bundle.getString("Cedula")			);
    	myIntent.putExtra("TelfCliente",	bundle.getString("TelfCliente")		);		
		myIntent.putExtra("Monto",			bundle.getString("Monto")			);
		myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant")	);
		myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP")		);
		myIntent.putExtra("Puerto",			bundle.getString("Puerto")			);
		myIntent.putExtra("Estatus",		bundle.getString("Estatus")			);
		myIntent.putExtra("PAN",			bundle.getString("PAN")				);
		myIntent.putExtra("Extraccion",		bundle.getString("Extraccion")		);
		
		direccionIP = 	bundle.getString("DireccionIP");
		puerto		=	Integer.parseInt(bundle.getString("Puerto"));		
		Start();
	}
	
    @Override
    public void onResume()
    {
        super.onResume();
        dialog1();
        dialog2();
        
    }
    
	public void dialog1()
	{          	
	    	progress = new ProgressDialog(this);
	        runOnUiThread(new Runnable()
	        {
	            @Override
				public void run()
	            {            	
	            	progress.setTitle("Transmitiendo...");
	            	progress.setMessage("Enviando Informacion       -->");
	            	progress.show();
	            }
	        });
	 }
	
	public void dialog2()
    {
    	new Handler().postDelayed(new Runnable() 
        {
            @Override
			public void run() 
            {            
        		int longitud	=	response1[1];
        		response2		=	Arrays.copyOf(response1, longitud+2);        		
        		Log.i("Linkit", "Respuesta 1 SimHost: " +toHex(response2)); 
        		
            	progress.dismiss();
    	        dialog3();
    	        dialog4();
            }
        }, 2000); 
    }
	
	public void dialog3()
	 {          	
	    	progress1 = new ProgressDialog(this);
	        runOnUiThread(new Runnable()
	        {
	            @Override
				public void run()
	            {            	
	            	progress1.setTitle("Transmitiendo...");
	            	progress1.setMessage("<--       Recibiendo Informacion");
	            	progress1.show();
	            }
	        });
	 }

	public void dialog4()
    {   
		if(response1[1]	!=	1)
        {
	        new Handler().postDelayed(new Runnable() 
	        {
	            @Override
				public void run() 
	            {                
	            	progress1.dismiss();
	            	finish();
	    	        startActivity(myIntent);    	       
	            }
	        }, 4000); 
        }
		else
	    {
	        /*Toast.makeText(this, "FALLA DE COMUNICACION..", Toast.LENGTH_LONG).show();
	        Intent myIntent = new Intent(this, Index.class);
			myIntent.putExtra("email", 		bundle.getString("email"));
	    	myIntent.putExtra("telefono",	bundle.getString("telefono"));
	    	myIntent.putExtra("direccionIP",bundle.getString("direccionIP"));
			myIntent.putExtra("puerto",		bundle.getString("puerto"));
	        this.startActivity(myIntent);*/
	    }
    }
	
	public void Start()
	{
		m_objThreadClient=new Thread(new Runnable() 
		{
		   @Override
		public void run()
	       {
	          try 
	           {
	        	  clientSocket.connect(new InetSocketAddress(direccionIP, puerto), 15000);
        	  
	        	 if(clientSocket.isConnected())
	        	 {
	        		 Log.i("*******","conecto");
	        		 //DataOutputStream
	        		 dataOutputstream = new DataOutputStream(clientSocket.getOutputStream());
	        		 dataOutputstream.write(request);				 
	        		 dataOutputstream.flush();
	
	        		 //InputStream
	        		 inputStream = clientSocket.getInputStream();  
	
	        		 // Bytearrayoutputstream
	        		 Bytearrayoutputstream = new ByteArrayOutputStream();  
	  
	        		 //leemos el stream
	        		 while( ( bytesRead = inputStream.read( response1 ) ) != -1 ) 
	        		 {  
	        			 Bytearrayoutputstream.write( response1, 0, bytesRead );							
	        		 }			
		                
	        		 inputStream.close();
	        		 dataOutputstream.close();							                 
	        	 }
	        	 else
	        	 {
	        		 Log.i("*******","no conecto");
	        		 response1[0]	=	(byte)(0x20);
	        		 response1[1]	=	(byte)(0x20);
	        	 }
	        	 	clientSocket.close();
			   }catch (Exception e){e.printStackTrace();}				 
	         }
		});	 
		m_objThreadClient.start();
	}

	public static String toHex(byte[] data)
	  {
	    StringBuffer retValue = new StringBuffer();
	    
	    byte[] arrayOfByte = data;
	    int j = data.length;
	   
	    for (int i = 0; i < j; i++)
	    {
	      byte value = arrayOfByte[i];
	      
	      if (retValue.length() > 0) 
	      {
	        retValue.append(", ");
	      }
	      retValue.append(String.format("0x%02X", new Object[] { Byte.valueOf(value) }));
	      
	    }
	    return retValue.toString();
	  }
	    
}
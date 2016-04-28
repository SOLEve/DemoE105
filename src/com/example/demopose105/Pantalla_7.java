package com.example.demopose105;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import api.LibE105Linkit;

public class Pantalla_7 extends Activity implements OnClickListener
{
	@SuppressWarnings("rawtypes")
	ArrayList 	Objarray1;
	String 		msj;
	TextView 	texto1,texto2,texto3,texto4,texto5;
	Button 		botoninicio;
	Bundle		bundle;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_pantalla_7_banesco);
        //setContentView(R.layout.activity_pantalla_7_sofitasa);
        setContentView(R.layout.activity_pantalla_7_mercantil);
		
		texto1  = (TextView) findViewById(R.id.TxtVersionLibreria);
		texto2  = (TextView) findViewById(R.id.TxtVersionKernel);
		texto3  = (TextView) findViewById(R.id.TxtNombreTerminal);
		texto4  = (TextView) findViewById(R.id.TxtMarcaTerminal);
		texto5  = (TextView) findViewById(R.id.TxtSerialTerminal);
		
		botoninicio = (Button) findViewById(R.id.binicioP7);
		botoninicio.setOnClickListener(this);
		
		bundle=getIntent().getExtras();
	}
	
	 @Override
	public void onResume()
	 {
		 super.onResume();
		
		 LibE105Linkit.vdGetInformationPinPad(this);
		
		 WaitResponse();		 
	 }
	 
	public void WaitResponse()
	{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistInformationPinPad();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistInformationPinPad();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir();             
	        	}
	        };hilo.start();
		
	}
	
	public void imprimir()
	{
			runOnUiThread(new Runnable() 
			{
			    @Override
				public void run()
			    {
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus operacion: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus: "	+Objarray1.get(1).toString());
				    	Log.i("Principal","N° Serial: "				+Objarray1.get(2).toString());
				    	Log.i("Principal","Versión S.O: "			+Objarray1.get(3).toString());
				    	Log.i("Principal","Versión Libreria: "		+Objarray1.get(4).toString());
				    	Log.i("Principal","Versión Kernel: "		+Objarray1.get(5).toString());
				    	Log.i("Principal","Marca Terminal: "		+Objarray1.get(6).toString());
				    	Log.i("Principal","Modelo Terminal: "		+Objarray1.get(7).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	
				    	texto1.setText(Objarray1.get(4).toString());
						texto2.setText(Objarray1.get(5).toString());
						texto3.setText(Objarray1.get(7).toString());
						texto4.setText(Objarray1.get(6).toString());
						texto5.setText(Objarray1.get(2).toString());

			    }
			});		
	}

	@Override
	public void onClick(View v) 
	{
		if(v.getId()==findViewById(R.id.binicioP7).getId())
		{
			finish();
			Intent myIntent = new Intent(this, Index.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}		
	}

}

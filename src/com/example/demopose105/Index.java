package com.example.demopose105;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Index extends Activity implements OnClickListener
{
	ImageView setup,venta,salida,informe,informacion,ayuda;
	Bundle bundle;
	Intent myIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_index_banesco);
        //setContentView(R.layout.activity_index_sofitasa);
        setContentView(R.layout.activity_index_mercantil);
		
		venta 		= 	(ImageView) findViewById(R.id.BotonVenta);
		setup 		= 	(ImageView) findViewById(R.id.BotonPanel);
		salida		=	(ImageView) findViewById(R.id.BotonSalida);
		informe		=	(ImageView) findViewById(R.id.BotonInforme);
		informacion	=	(ImageView) findViewById(R.id.BotonInformacion);
		ayuda		=	(ImageView) findViewById(R.id.BotonAyuda);
		
		venta.setOnClickListener(this);
		setup.setOnClickListener(this);
		salida.setOnClickListener(this);
		informe.setOnClickListener(this);
		informacion.setOnClickListener(this);
		ayuda.setOnClickListener(this);
		
		bundle=getIntent().getExtras();		
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==findViewById(R.id.BotonVenta).getId())
		{
			myIntent = new Intent(this, Pantalla_1.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}		

		if(v.getId()==findViewById(R.id.BotonPanel).getId())
		{
	    	myIntent = new Intent(this, Setup.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.BotonInforme).getId())
		{
	    	myIntent = new Intent(this, Pantalla_6.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.BotonInformacion).getId())
		{
	    	myIntent = new Intent(this, Pantalla_7.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.BotonAyuda).getId())
		{
	    	myIntent = new Intent(this, Pantalla_8.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.BotonSalida).getId())
		{

			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Salir")
			.setMessage("¿Desea salir de la aplicación?")
			.setNegativeButton("NO", null)
			.setPositiveButton("SI", new DialogInterface.OnClickListener() 
        {

            @Override
            public void onClick(DialogInterface dialog, int which) 
            {
            	finish();               
            	Intent intent = new Intent(Intent.ACTION_MAIN);
            	intent.addCategory(Intent.CATEGORY_HOME);
            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	startActivity(intent);
            }

        })		        
        .show();
		}
	}

	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	 {
			switch(keyCode)
			{
				case KeyEvent.KEYCODE_BACK:
					new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("Salir")
		        .setMessage("¿Desea salir de la aplicación?")
		        .setNegativeButton("NO", null)
		        .setPositiveButton("SI", new DialogInterface.OnClickListener() 
		        {

		            @Override
		            public void onClick(DialogInterface dialog, int which) 
		            {
		            	finish();               
		            	Intent intent = new Intent(Intent.ACTION_MAIN);
		            	intent.addCategory(Intent.CATEGORY_HOME);
		            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            	startActivity(intent);
		            }

		        })		        
		        .show();
				return true;
			}
			return super.onKeyDown(keyCode, event);
	}

}
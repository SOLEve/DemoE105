package com.example.demopose105;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Pantalla_2 extends Activity implements OnClickListener
{
	EditText	etcedula,etCelCliente;
	Button 		boton1;
	Bundle 		bundle;
	String 		telfcliente;
	Spinner		operadora;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_2_banesco);
		//setContentView(R.layout.activity_pantalla_2_sofitasa);
		setContentView(R.layout.activity_pantalla_2_mercantil);
		
		etcedula		= (EditText) findViewById(R.id.etcedulaP2);
		etCelCliente	= (EditText) findViewById(R.id.etcelclienteP2);
		operadora		= (Spinner) findViewById(R.id.spOperadora);
		
		boton1 = (Button) findViewById(R.id.botonPantalla1_1);
		boton1.setOnClickListener(this);		 
		bundle=getIntent().getExtras();
	}
	
	
	@Override
	public void onClick(View v) 
	{
		if(TextUtils.isEmpty(etcedula.getText().toString()) || TextUtils.isEmpty(etCelCliente.getText().toString()))
		{
			Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show();
		}else
		if(etCelCliente.getText().toString().length()<7)
		{
			Toast.makeText(this, "Campo Celular incompleto", Toast.LENGTH_LONG).show();
		}else
		if(etcedula.getText().toString().length()<7)
		{
			Toast.makeText(this, "Campo Cedula incompleto", Toast.LENGTH_LONG).show();
		}else
		{
        	finish();
        	
        	telfcliente = String.valueOf(operadora.getSelectedItem())+etCelCliente.getText().toString();
        	
			Intent myIntent = new Intent(this, Pantalla_3.class);
	    	myIntent.putExtra("Monto",			bundle.getString("Monto")		);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));	
	    	myIntent.putExtra("Cedula",			etcedula.getText().toString()	);
	    	myIntent.putExtra("TelfCliente",	telfcliente);
	        this.startActivity(myIntent);
		}
	}
	
}

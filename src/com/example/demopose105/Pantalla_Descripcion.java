package com.example.demopose105;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Pantalla_Descripcion extends Activity 
{
	Bundle bundle;
	TextView texto1,texto2,texto3,texto4,texto5,texto6,texto7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_desc_banesco);
        //setContentView(R.layout.activity_desc_sofitasa);
        setContentView(R.layout.activity_desc_mercantil);
		
		texto1= 	(TextView) findViewById(R.id.txtfactura);
		texto2= 	(TextView) findViewById(R.id.txtPAN);
		texto3= 	(TextView) findViewById(R.id.txtEstatus);
		texto4= 	(TextView) findViewById(R.id.txtMonto);
		texto5= 	(TextView) findViewById(R.id.txtFecha);
		texto6= 	(TextView) findViewById(R.id.txtTipo);
		texto7= 	(TextView) findViewById(R.id.txtCedula);
		
		
		bundle=getIntent().getExtras();
		
		texto1.setText(bundle.getString("id"));
		texto2.setText(bundle.getString("PAN"));
		texto3.setText(bundle.getString("estatus"));
		texto4.setText(bundle.getString("monto"));
		texto5.setText(bundle.getString("fecha"));
		texto6.setText(bundle.getString("tipo"));
		texto7.setText(bundle.getString("cedula"));

	}
	
	
	
	
	
	
	
}

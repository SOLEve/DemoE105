package com.example.demopose105;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pantalla_1 extends Activity implements OnClickListener
{
	Button 		boton1;
	EditText	etmonto;
	Bundle 		bundle;
	AlertDialog dlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_1_banesco);
		//setContentView(R.layout.activity_pantalla_1_sofitasa);
		setContentView(R.layout.activity_pantalla_1_mercantil);
		
		etmonto	= (EditText) findViewById(R.id.etmontoP1);		 
		boton1 = (Button) findViewById(R.id.bcontinuarP1);
		boton1.setOnClickListener(this);		
		bundle=getIntent().getExtras();	
	}
	
	@Override
	public void onClick(View v) 
	{
		if(TextUtils.isEmpty(etmonto.getText().toString()))
		{
			Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show();
		}else
		{
        	finish();
			Intent myIntent = new Intent(this, Pantalla_2.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));			
			myIntent.putExtra("Monto",			etmonto.getText().toString());
	        this.startActivity(myIntent);
		}
	}
	
}

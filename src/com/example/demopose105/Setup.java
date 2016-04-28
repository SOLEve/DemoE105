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
import android.widget.Toast;

public class Setup extends Activity implements OnClickListener
{
	Button 		boton1;
	EditText 	telf,socket,puerto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_setup_banesco);
        //setContentView(R.layout.activity_setup_sofitasa);
        setContentView(R.layout.activity_setup_mercantil);
		
		boton1 = (Button) findViewById(R.id.biniciosetup);
		boton1.setOnClickListener(this);
		 
		telf= (EditText) findViewById(R.id.etcelmerchantsetup);
		socket= (EditText) findViewById(R.id.etdireccionsetup);
		puerto= (EditText) findViewById(R.id.etpuertosetup);		
	}
	
	@Override
	public void onClick(View v) 
	{
		if(
				TextUtils.isEmpty(telf.getText().toString()) ||
				TextUtils.isEmpty(socket.getText().toString()) ||
				TextUtils.isEmpty(puerto.getText().toString())
		)
		{
			Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show();
		}
		else
		{
			finish();
			Intent myIntent = new Intent(this, Index.class);			
	    	myIntent.putExtra("TelfMerchant",	telf.getText().toString());
	    	myIntent.putExtra("DireccionIP",	socket.getText().toString());
	    	myIntent.putExtra("Puerto",			puerto.getText().toString());	
	        this.startActivity(myIntent);
		}
	}
}


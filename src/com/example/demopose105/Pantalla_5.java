package com.example.demopose105;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Pantalla_5 extends Activity implements OnClickListener
{
	Button 			botoninicio,botonsms;
	Bundle			bundle;
	SQLiteDatabase 	database;	
	TextView 		etcedula,etmonto,etfecha,ethora,etestatus,etcuenta;	
	String 			estatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_5_banesco);
		//setContentView(R.layout.activity_pantalla_5_sofitasa);
		setContentView(R.layout.activity_pantalla_5_mercantil);
				
		botoninicio = (Button) findViewById(R.id.bInicioP5);
		botoninicio.setOnClickListener(this);
		 
		botonsms = (Button) findViewById(R.id.bSMSP5);
		botonsms.setOnClickListener(this);
		
		bundle=getIntent().getExtras();
		
		etcedula= 	(TextView) findViewById(R.id.twcedulaP5);
		etmonto= 	(TextView) findViewById(R.id.twmontoP5);
		etfecha= 	(TextView) findViewById(R.id.twfechaP5);
		ethora= 	(TextView) findViewById(R.id.twhoraP5);
		etestatus= 	(TextView) findViewById(R.id.twestatusP5);
		etcuenta= 	(TextView) findViewById(R.id.twPANP5);
		
		Random r = new Random();
		int i1 = r.nextInt(999999 - 111111) + 111111;
		String nroestatus="00000"+i1;
		
		etcedula.setText(bundle.getString("Cedula"));
		etmonto.setText(bundle.getString("Monto"));
		etcuenta.setText(bundle.getString("PAN"));
		
		
		if(Double.parseDouble(bundle.getString("Monto"))>1000)
		{
			estatus = "RECHAZADO";
			etestatus.setText("RECHAZADO: "+nroestatus);
		}
		else
		{
			estatus = "APROBADO";
			etestatus.setText("APROBADO: "+nroestatus);
		}
		
		etfecha.setText(get_DatePhone());
		ethora.setText(getTimePhone());
		
		enviarSms();
		
		almacenarDatos(
				bundle.getString("Cedula"), 
				get_DatePhone(), 
				estatus, 
				Double.parseDouble(bundle.getString("Monto")),
				bundle.getString("PAN"),
				getTimePhone()
				);
	}

	@Override
	public void onClick(View v) 
	{
		if(v.getId()==findViewById(R.id.bInicioP5).getId())
		{
			botoninicio.setVisibility(View.INVISIBLE);		
			finish();
			Intent myIntent = new Intent(this, Index.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant")	);
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP")		);
			myIntent.putExtra("Puerto",			bundle.getString("Puerto")			);
	        this.startActivity(myIntent);
		}		

		if(v.getId()==findViewById(R.id.bSMSP5).getId())
		{
			enviarSms();
			Toast.makeText(this, "Mensaje Enviado..", Toast.LENGTH_LONG).show();
		}
	}
	
	@SuppressLint("UnlocalizedSms") 
	public void enviarSms()
    {
		SmsManager sms = SmsManager.getDefault();    	
		sms.sendTextMessage
		(bundle.getString("TelfMerchant"), 
		null, 
		"COMPRA REALIZADA POR LA TARJETA NUMERO :"+bundle.getString("PAN")
    	+" EL DIA: "+
		get_DatePhone()+
		" A LA HORA: "+getTimePhone()+
		" POR EL MONTO: "+bundle.getString("Monto")+" BS. ",
		null, 
		null);
		
		sms.sendTextMessage
		(bundle.getString("TelfCliente"), 
		null, 
		"COMPRA REALIZADA POR LA TARJETA NUMERO :"+bundle.getString("PAN")
    	+" EL DIA: "+
		get_DatePhone()+
		" A LA HORA: "+getTimePhone()+
		" POR EL MONTO: "+bundle.getString("Monto")+" BS. ",
		null, 
		null);
    }

    @SuppressLint("SimpleDateFormat") 
    private String get_DatePhone()
    {    	
        Calendar cal = new GregorianCalendar();

        java.util.Date date =  cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String formatteDate = df.format(date);

        return formatteDate;

    }

    @SuppressLint("SimpleDateFormat") 
    private String getTimePhone()
    {    	
        Calendar cal = new GregorianCalendar();

        java.util.Date date =  cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        String formatteDate = df.format(date);

        return formatteDate;

    }
   
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_BACK:
				finish();
				Intent myIntent = new Intent(this, Index.class);
				myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant")	);
				myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP")		);
				myIntent.putExtra("Puerto",			bundle.getString("Puerto")			);
		        this.startActivity(myIntent);
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    public void almacenarDatos(String cedula,String fecha,String estatus,Double monto,String PAN, String Hora)
    {
    	
        database = openOrCreateDatabase("DBSQL",MODE_PRIVATE, null);
        
        Log.i("SQLite","INSERT INTO Facturas VALUES(null,'"+cedula+"','"+fecha+"','"+estatus+"',"+monto+",'"+PAN+"','"+Hora+"')");
        
        database.execSQL("INSERT INTO Facturas VALUES(null,'"+cedula+"','"+fecha+"','"+estatus+"',"+monto+",'"+PAN+"','"+Hora+"')");        
       
        Cursor cursor = database.rawQuery("SELECT * FROM Facturas", null);
        cursor.moveToFirst();
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("IdFactura")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("CedulaCliente")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("Fecha")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("Estatus")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("Monto")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("PAN")));
        Log.i("SQLite", cursor.getString(cursor.getColumnIndex("Hora")));
        Log.i("SQLite", ""+cursor.getInt(0));
        database.close();
    }

    
}

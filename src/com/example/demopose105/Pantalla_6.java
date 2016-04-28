package com.example.demopose105;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class Pantalla_6 extends Activity implements OnClickListener
{
	Button 		boton1,boton2;
	ImageView 	botonInicio,botonFin;
	Calendar 	cal;
	int 		day,month,year,band=0;	
	TextView 	fechaInicio,fechaFin,TransRealizadas,TransAprobadas,TransRechazadas,MontoAprobado;
	String		fechainicio,fechafin;
	Cursor 		cursor;
	Bundle 		bundle;
	SQLiteDatabase 	database;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_6_banesco);
		//setContentView(R.layout.activity_pantalla_6_sofitasa);
		setContentView(R.layout.activity_pantalla_6_mercantil);
		
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		  
		botonInicio 	= 	(ImageView) findViewById(R.id.BotonCalendarInicio);		
		botonInicio.setOnClickListener(this);
		
		botonFin 	= 	(ImageView) findViewById(R.id.BotonCalendarFin);		
		botonFin.setOnClickListener(this);
		
		fechaInicio		= 	(TextView) findViewById(R.id.textView1);
		fechaFin		= 	(TextView) findViewById(R.id.textView2);
		TransRealizadas	= 	(TextView) findViewById(R.id.txtTranRealizadas);
		TransAprobadas	= 	(TextView) findViewById(R.id.txtTranAprobadas);
		TransRechazadas	= 	(TextView) findViewById(R.id.txtTranRechazadas);
		MontoAprobado	= 	(TextView) findViewById(R.id.txtMontoAprobados);
		
		boton1	=	(Button) findViewById(R.id.bconsultarP6);
		boton1.setOnClickListener(this);
		
		boton2 = (Button) findViewById(R.id.binicioP6);
		boton2.setOnClickListener(this);
		
		bundle	=	getIntent().getExtras();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==findViewById(R.id.BotonCalendarInicio).getId())
		{
			band=1;
			showDialog(0);
		}
		if(v.getId()==findViewById(R.id.BotonCalendarFin).getId())
		{
			band=2;
			showDialog(0);
		}
		if(v.getId()==findViewById(R.id.binicioP6).getId())
		{
			finish();
			Intent myIntent = new Intent(this, Index.class);
			myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant"));
			myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP"));
			myIntent.putExtra("Puerto",			bundle.getString("Puerto"));
	        this.startActivity(myIntent);
		}
		
		if(v.getId()==findViewById(R.id.bconsultarP6).getId())
		{

			database = openOrCreateDatabase("DBSQL",MODE_PRIVATE, null);
			
			cursor = database.rawQuery
		        		(
		        		"SELECT COUNT(*) FROM Facturas " +
		        		"WHERE Fecha>='"+fechainicio+"' AND Fecha <='"+fechafin+"'", null);		
	        cursor.moveToFirst();
	        Log.i("SQLite", ""+cursor.getInt(0));
	        
	        TransRealizadas.setText(""+cursor.getInt(0));
	        
	        cursor = database.rawQuery
	        		(
	        		"SELECT COUNT(*) FROM Facturas " +
	        		"WHERE Fecha>='"+fechainicio+"' " +
	        		"AND Fecha <='"+fechafin+"'" +
	        		"AND Estatus='APROBADO'", null);		
	        cursor.moveToFirst();
	        Log.i("SQLite", ""+cursor.getInt(0));
        
	        TransAprobadas.setText(""+cursor.getInt(0));
	        
	        cursor = database.rawQuery
	        		(
	        		"SELECT COUNT(*) FROM Facturas " +
	        		"WHERE Fecha>='"+fechainicio+"' " +
	        		"AND Fecha <='"+fechafin+"'" +
	        		"AND Estatus='RECHAZADO'", null);		
	        cursor.moveToFirst();
	        Log.i("SQLite", ""+cursor.getInt(0));
        
	        TransRechazadas.setText(""+cursor.getInt(0));
	        
	        cursor = database.rawQuery
	        		(
	        		"SELECT SUM(Monto) FROM Facturas " +
	        		"WHERE Fecha>='"+fechainicio+"' " +
	        		"AND Fecha <='"+fechafin+"'" +
	        		"AND Estatus='APROBADO'", null);		
	        cursor.moveToFirst();
	        Log.i("SQLite", ""+cursor.getInt(0));
        
	        MontoAprobado.setText(""+cursor.getInt(0)+" Bs");
	        
	        
	        
	        database.close();
		}
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) 
	{
	  return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() 
	{
		@Override
		public void onDateSet(DatePicker view, int selectedYear,  int selectedMonth, int selectedDay) 
		{
			if(band==1)
			{
				String diaescogido = null,mesescogido = null;
				if(selectedDay<10)
				{
					diaescogido = "0"+selectedDay;
				}
				else{diaescogido = ""+selectedDay;}
				
				if((selectedMonth+1)<10)
				{
					mesescogido = "0"+(selectedMonth+1);
				}else{mesescogido = ""+(selectedMonth+1);}
				
				fechainicio = diaescogido+"-"+mesescogido+"-"+selectedYear;
								
				fechaInicio.setText(fechainicio);
			}
			if(band==2)
			{
				String diaescogido = null,mesescogido = null;
				if(selectedDay<10)
				{
					diaescogido = "0"+selectedDay;
				}
				else{diaescogido = ""+selectedDay;}
				
				if((selectedMonth+1)<10)
				{
					mesescogido = "0"+(selectedMonth+1);
				}else{mesescogido = ""+(selectedMonth+1);}
				
				fechafin = diaescogido+"-"+mesescogido+"-"+selectedYear;
				
				fechaFin.setText(fechafin);				
			}
		}

	};
		 
}

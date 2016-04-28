package com.example.demopose105;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Pantalla_8 extends Activity 
{

	private ListView lista;
	Cursor cursor;
	SQLiteDatabase 	database;
	Intent myIntent;
	Bundle bundle;
	ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_list__adap_banesco);
        //setContentView(R.layout.activity_list__adap_sofitasa);
        setContentView(R.layout.activity_list__adap_mercantil);

		bundle=getIntent().getExtras();	
		
		cargarDatos();		
       
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_Adaptador(this, R.layout.activity_lista__entrada_banesco, datos)
        {
			@Override
			public void onEntrada(Object entrada, View view) 
			{
		        if (entrada != null) 
		        {
		            TextView texto_transaccion = (TextView) view.findViewById(R.id.textView_transaccion); 
		            if (texto_transaccion != null) 
		            	texto_transaccion.setText(((Lista_Entrada) entrada).get_textotransaccion()); 

		            TextView texto_Estatus = (TextView) view.findViewById(R.id.textView_Estatus); 
		            if (texto_Estatus != null)
		            	texto_Estatus.setText(((Lista_Entrada) entrada).get_textoestatus());
		            
		            TextView texto_Tipo = (TextView) view.findViewById(R.id.textView_Tipo); 
		            if (texto_Tipo != null)
		            	texto_Tipo.setText(((Lista_Entrada) entrada).get_textotipo());
		            
		            TextView texto_Monto = (TextView) view.findViewById(R.id.textView_Monto); 
		            if (texto_Monto != null)
		            	texto_Monto.setText(((Lista_Entrada) entrada).get_textoMonto());
		        }
			}
		});

        lista.setOnItemClickListener(new OnItemClickListener() 
        { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) 
			{
				Lista_Entrada elegido = (Lista_Entrada) pariente.getItemAtPosition(posicion); 

				AbrirDescripcion(
						elegido.get_textoPAN(),
						elegido.get_textoestatus(),
						elegido.get_textoMonto(),
						elegido.get_textoFecha(),
						elegido.get_textotipo(),
						elegido.get_textoCedula(),
						""+(posicion+1));
			}
        });
	}
	
	public void cargarDatos()
	{
		database = openOrCreateDatabase("DBSQL",MODE_PRIVATE, null);
		
		cursor = database.rawQuery("SELECT COUNT(*) FROM Facturas", null);
		cursor.moveToFirst();
		int cantidad = cursor.getInt(0);
		
		cursor = database.rawQuery("SELECT * FROM Facturas", null);
		cursor.moveToFirst();
		
		for(int i=0;i<cantidad;i++)
		{
	
			datos.add(new Lista_Entrada
			(
					cursor.getString(cursor.getColumnIndex("IdFactura")),					
					cursor.getString(cursor.getColumnIndex("Estatus")),
					"VENTA",
					cursor.getString(cursor.getColumnIndex("Monto"))+" Bs",
					cursor.getString(cursor.getColumnIndex("CedulaCliente")),
					cursor.getString(cursor.getColumnIndex("Fecha")),
					cursor.getString(cursor.getColumnIndex("PAN")))
			);
			
			cursor.moveToNext();
		}
		
		database.close();
	}
	
	
	public void AbrirDescripcion(String PAN, String estatus, String monto, String fecha, String tipo, String cedula,String id)
	{
		myIntent = new Intent(this, Pantalla_Descripcion.class);
		myIntent.putExtra("PAN",		PAN);
		myIntent.putExtra("estatus",	estatus);
		myIntent.putExtra("monto",		monto);
		myIntent.putExtra("fecha",		fecha);
		myIntent.putExtra("tipo",		tipo);
		myIntent.putExtra("cedula",		cedula);
		myIntent.putExtra("id",			id);
	    this.startActivity(myIntent);
	}
}

package com.example.demopose105;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import api.LibE105Linkit;

@SuppressWarnings("rawtypes")
public class Pantalla_3 extends Activity 
{

	String 		msj;	
	ArrayList	Objarray1,Objarray3;
	Bundle 		bundle;
	AlertDialog dlg;
	AlertDialog alerta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.activity_pantalla_3_banesco);
		//setContentView(R.layout.activity_pantalla_3_sofitasa);
		setContentView(R.layout.activity_pantalla_3_mercantil);		
		bundle=getIntent().getExtras();	
		
		informNoButtonOrTitleOnUIThread();
	}
	
	@Override
	public void onResume(){
	    super.onResume();

	    LibE105Linkit.vdStartEMVTransactionE105(
				this,
				"FF",							//timeoutCardDetectLoop
				"Ingrese o Deslice Tarjeta...",	//textoCardDetectLoop
				"FF",							//timeoutMsrRead
				"Deslice Tarjeta...",			//textoMsrRead
				"FF",							//timeoutSmartCard
				"Inserte Chip: ",				//textoSmartCardEnable
				"60",							//timeoutSeleccApp
				"Seleccione Aplicacion",		//textoSeleccApp
				"Cancelar",						//textoCancelarSeleccApp
				"60",							//timeoutPIN					
				"Ingrese PIN: ",				//texto1 PIN
				"Re-Ingrese PIN: ",				//texto2 PIN
	    "Re-Ingrese PIN 'Ultima Oportunidad': ",//texto3 PIN
				"PIN INCORRECTO",				//titulo PIN incorrecto
				"Resta 2 Oportunidades",		//texto1 PIN incorrecto
				"Resta 1 Oportunidad",			//texto2 PIN incorrecto
				"TARJETA BLOQUEADA",			//titulo PIN bloqueado
				"Contacte a su banco",			//texto  tarjeta bloqueada					
				"FALLBACK!",					//tituloFallback
				"Presione Ok para continuar...",//textoFallback
				"000000001000",					//montoTransaccion monto = 15.50
				"000000000100",					//montoTransaccion monto = 15.50
				"00");
		
	    WaitResponse();

	}
	
	public void WaitResponse()
	{
		Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistStartEMVTransactionE105();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistStartEMVTransactionE105();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir();             
	        	}
	        };hilo.start();	

		}
	
	public void imprimir()
	{
		Log.i("Principal","*****************************************************");
    	Log.i("Principal","*****************************************************");
    	Log.i("Principal","Estatus Operacion: "			+Objarray1.get(0).toString());
    	Log.i("Principal","Mensaje del Estatus: "		+Objarray1.get(1).toString());
    	Log.i("Principal","RESPUESTA E0B: "				+Objarray1.get(2).toString());
    	Log.i("Principal","OBFUSCATED PAN: "			+Objarray1.get(3).toString());
    	Log.i("Principal","CARDHOLDER NAME: "			+Objarray1.get(4).toString());
    	Log.i("Principal","ENCRYPTED TRACK 2 DATA: "	+Objarray1.get(5).toString());
    	Log.i("Principal","KSN FOR ENCRYPTED TRACK 2: "	+Objarray1.get(6).toString());
    	Log.i("Principal","SERVICE CODE: "				+Objarray1.get(7).toString());
    	Log.i("Principal","PIN BLOCK ENCRIPTADO: "		+Objarray1.get(8).toString());
    	Log.i("Principal","KSN PIN BLOCK: "				+Objarray1.get(9).toString());
    	Log.i("Principal","Modo de Extraccion de Datos: "+Objarray1.get(11).toString());
    	Log.i("Principal","*****************************************************");
    	Log.i("Principal","*****************************************************");

    	finish();
    	alerta.dismiss();
		Intent myIntent = new Intent(this, Pantalla_4.class);
		myIntent.putExtra("Cedula", 		bundle.getString("Cedula")			);
    	myIntent.putExtra("TelfCliente",	bundle.getString("TelfCliente")		);		
		myIntent.putExtra("Monto",			bundle.getString("Monto")			);
		myIntent.putExtra("TelfMerchant",	bundle.getString("TelfMerchant")	);
		myIntent.putExtra("DireccionIP",	bundle.getString("DireccionIP")		);
		myIntent.putExtra("Puerto",			bundle.getString("Puerto")			);
		myIntent.putExtra("Estatus",		Objarray1.get(10).toString()		);
		myIntent.putExtra("PAN",			Objarray1.get(3).toString()			);
		myIntent.putExtra("Extraccion",		Objarray1.get(11).toString()		);
		this.startActivity(myIntent);
	}

	public void informNoButtonOrTitleOnUIThread()
	{
		final AlertDialog.Builder AlertDBuilder = new AlertDialog.Builder(this);
	        
	    this.runOnUiThread(new Runnable()
	    {
	    	@Override
			public void run()
	        {
	    		AlertDBuilder.setMessage("Comunicando con el E105 Por favor espere...")
	            .setCancelable(false);
	            alerta = AlertDBuilder.create();
	            
	            WindowManager.LayoutParams wmlp = alerta.getWindow().getAttributes();	            
	            wmlp.gravity = Gravity.TOP | Gravity.LEFT;
	            wmlp.x = 800;   //x position
	            wmlp.y = 800; 
	            
	            alerta.show();
	            passBackDialogRef(alerta);
	        }
	    });
	 }
	
	public void passBackDialogRef(AlertDialog dlg)
	{
		 this.dlg=dlg;
	}
	
	
}

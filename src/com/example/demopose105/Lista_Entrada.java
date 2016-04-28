package com.example.demopose105;

import android.annotation.SuppressLint;


@SuppressLint("Instantiatable") 
public class Lista_Entrada 
{

	String textotransaccion; 
	String textoestatus; 
	String textotipo; 
	String textoMonto;
	String textoCedula;
	String textoFecha;
	String textoPAN;

	public Lista_Entrada (String textotransaccion, String textoestatus,String textotipo, String textoMonto,String textoCedula,String textoFecha,String textoPAN) 
	{ 
	    this.textotransaccion 	= 	textotransaccion; 
	    this.textoestatus 		= 	textoestatus; 
	    this.textotipo			= 	textotipo;
	    this.textoMonto			=	textoMonto;
	    this.textoCedula		=	textoCedula;
	    this.textoFecha			=	textoFecha;
	    this.textoPAN			=	textoPAN;
	}

	public String get_textotransaccion() { 
	    return textotransaccion; 
	}

	public String get_textoestatus() { 
	    return textoestatus; 
	}

	public String get_textotipo() {
	    return textotipo; 
	} 
	
	public String get_textoMonto() {
	    return textoMonto; 
	} 
	
	public String get_textoCedula() {
	    return textoCedula; 
	} 
	
	public String get_textoFecha() {
	    return textoFecha;
	} 
	
	public String get_textoPAN() {
	    return textoPAN;
	} 
}

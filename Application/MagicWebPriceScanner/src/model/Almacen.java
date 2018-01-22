package model;

import java.io.IOException;

import controller.ReadFromWeb;

public class Almacen {
	private String url;		// URL donde está la información de la carta
	private String expansion;
	private double tendenciaPrecio;
	private double minimoPrecio;
	private double foilPrecio;
	// Posible variable para edición de la carta
	
	public Almacen(){}
	public Almacen(String url) throws IOException{ 
		setUrl(url);
		ReadFromWeb.readFromWeb(this);
	}
	public void setUrl(String url){ this.url = url; }
	public void setTendenciaPrecio(double tendenciaPrecio){ this.tendenciaPrecio = tendenciaPrecio; }
	public void setMinimoPrecio(double minimoPrecio){ this.minimoPrecio = minimoPrecio; }
	public void setFoilPrecio(double foilPrecio){ this.foilPrecio = foilPrecio; }
	public void setExpansion(String expansion){ this.expansion = expansion; }
	public String getUrl(){ return url; }
	public double getTendenciaPrecio(){ return tendenciaPrecio; }
	public double getMinimoPrecio(){ return minimoPrecio; }
	public double getFoilPrecio(){ return foilPrecio; }
	public String getExpansion(){ return expansion; }
}

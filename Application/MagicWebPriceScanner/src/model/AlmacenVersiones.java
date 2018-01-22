package model;

import java.io.IOException;
import java.util.ArrayList;

import controller.ReadVersionsFromWeb;

public class AlmacenVersiones {
	private String nombreCarta;
	private String generalUrl;
	private int numVersiones = 0;
	private ArrayList<Almacen> versiones = new ArrayList<Almacen>();

	public AlmacenVersiones(){};
	public AlmacenVersiones(String url, String name) throws IOException{ 
		setNombreCarta(name);
		setGeneralUrl(url);
		ReadVersionsFromWeb.readVersionsFW(this);
	};
	public ArrayList<Almacen> getVersiones() { return versiones; }
	public String getGeneralUrl() { return generalUrl; }
	public String getNombreCarta() { return nombreCarta; }
	public int getNumVersiones() { return numVersiones; }
	public void setVersiones(ArrayList<Almacen> versiones) { this.versiones = versiones; }
	public void setGeneralUrl(String generalUrl) { this.generalUrl = generalUrl; }
	public void setNumVersiones(int numVersiones) { this.numVersiones = numVersiones; }
	public void setNombreCarta(String nombreCarta) { this.nombreCarta = nombreCarta; }
}

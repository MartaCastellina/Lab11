package it.polito.tdp.model;

public class Gruppo {

	private int numPersone;
	private double tolleranza;
	private int tavoloAssegnato=-1;
	public Gruppo(int numPersone, double tolleranzaGruppoNext) {
		super();
		this.numPersone = numPersone;
		this.tolleranza = tolleranzaGruppoNext;
	}
	public int getNumPersone() {
		return numPersone;
	}
	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}
	public double getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}
	
	public int getTavoloAssegnato() {
		return tavoloAssegnato;
	}
	public void setTavoloAssegnato(int tavoloAssegnato) {
		this.tavoloAssegnato = tavoloAssegnato;
	}
	
	@Override
	public String toString() {
		return "Gruppo [numPersone=" + numPersone + ", tolleranza=" + tolleranza + "]";
	}
	
	
	
	
}

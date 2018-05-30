package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event implements Comparable<Event>{

	private LocalTime ora;
	private EventType tipo;
	
	Gruppo gruppo;

	
	public Event(LocalTime ora, EventType tipo, Gruppo gruppo) {
		super();
		this.ora = ora;
		this.tipo = tipo;
		this.gruppo = gruppo;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public Gruppo getGruppo() {
		return gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.ora.compareTo(o.ora);
	}
	
	
	
}

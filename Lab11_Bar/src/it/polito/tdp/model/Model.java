package it.polito.tdp.model;

import java.time.LocalTime;
import java.util.PriorityQueue;


public class Model {
	//Parametri d'ingresso
	private int numTavoli10=2;
	private int numTavoli8=4;
	private int numTavoli6=4;
	private int numTavoli4=5;
	
	private int numGruppiTotali=2000;
	private int interarrivoMax=10;
	private int maxGruppo=10;
	private double minimaOccupazioneTavolo=0.5;
	
	private LocalTime T_INIZIO= LocalTime.of(5, 0); //Dalle 5.00 di mattino
	
	
	//Modello del mondo
	private int tArrivoNext;
	private int nPersoneNext;
	private int permanenzaNext;
	private double tolleranzaGruppoNext;
	private PriorityQueue<Integer> tavoli; 
	
	//Coda degli eventi
	private PriorityQueue<Event> attesa;
	
	//Valori di output
	//private int numTotClienti; è somma dei due
	private int numSoddisfatti;
	private int numInsoddisfatti;
	
	//Inizializzazione
	
	public void init(){
		numInsoddisfatti=0;
		numSoddisfatti=0;
		//numTotClienti=0;
		this.attesa=new PriorityQueue<Event>();
		this.tavoli=new PriorityQueue<>();
		LocalTime ora=T_INIZIO;
		permanenzaNext=this.nuovaPermanenza();
		
		for (int j=0;j<numTavoli4;j++) {
			tavoli.add(4);
		}
		for (int j=0;j<numTavoli8;j++) {
			tavoli.add(8);
		}
		for (int j=0;j<numTavoli6;j++) {
			tavoli.add(6);
		}
		for (int j=0;j<numTavoli10;j++) {
			tavoli.add(10);
		}
		
		
		for (int i=0;i<numGruppiTotali;i++) {
			tArrivoNext=this.nuovoTArrivo();
			nPersoneNext=this.nuovoNPersone();
			
			tolleranzaGruppoNext=this.nuovaTolleranza();
			
			Gruppo g=new Gruppo(nPersoneNext,tolleranzaGruppoNext);
			Event e=new Event(ora, EventType.ARRIVA,g);
			ora=ora.plusMinutes(tArrivoNext);
			
			attesa.add(e);
		}
	
	
	}

	public double nuovaTolleranza() {
		double random=0;
		boolean flag=true;
		while (flag) {
			random=Math.random()*1.2;
			if(random<=1) {
				flag=false;
			}
				
		}
		
		return random;
	}

	public int nuovaPermanenza() {
		double random=Math.random();
		random=random*1000;
		random=random%61; //61 sono i numeri tra 60 e 120
		random +=60;
		
		return (int) random;
	}

	public int nuovoNPersone() {
		double random=Math.random();
		random= random*maxGruppo;
		random+=1;
				
		
		return (int) random;
	}

	public int nuovoTArrivo() {
		double random=Math.random();
		random= random*interarrivoMax;
		random+=1;
				
		
		return (int) random;
	}
	
	public void run() {
		Event e;
		while ((e=attesa.poll())!= null) {
	
		processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getTipo()) {
		case ARRIVA:{
			if (this.alTavolo(e)) {
				Event e2=new Event(e.getOra(),EventType.TAVOLO,e.getGruppo());
				attesa.add(e2);
				Integer dimTavolo=this.assegnaTavolo(e.getGruppo().getNumPersone());
				tavoli.remove(dimTavolo);

				e.getGruppo().setTavoloAssegnato(dimTavolo);
			}
			else {
				Event e2=new Event(e.getOra(),EventType.BANCONE,e.getGruppo());
				attesa.add(e2);
				e.getGruppo().setTavoloAssegnato(0);
				
			}
			
			break;	
		}
		
		
		case TAVOLO:{
			Event e2=new Event(e.getOra().plusMinutes(permanenzaNext), EventType.LASCIA, e.getGruppo());
			attesa.add(e2);
			permanenzaNext=this.nuovaPermanenza();
			
			break;
		}
		
		case BANCONE:{
			if (this.accettaBancone(e.getGruppo())) {
			Event e2=new Event(e.getOra().plusMinutes(permanenzaNext), EventType.LASCIA, e.getGruppo());
				attesa.add(e2);
				permanenzaNext=this.nuovaPermanenza();
				
				
			}
			else {
				numInsoddisfatti+=e.getGruppo().getNumPersone();
			}
			break;
		}
		case LASCIA:{
			Integer tavolo=e.getGruppo().getTavoloAssegnato();
			if(tavolo==-1) {
				throw new RuntimeException("Ha lasciato il bar un cliente senza tavolo");
			}
			numSoddisfatti+=e.getGruppo().getNumPersone();
			tavoli.add(tavolo);
		break;
		}
		}
		
	}

	private boolean accettaBancone(Gruppo gruppo) {

		double tolleranza=gruppo.getTolleranza();
		double p=this.nuovaTolleranza(); //è tolleranza generata casualmente qui
		if (p<tolleranza) {
			return true;
		}
		return false;
	}

	private int assegnaTavolo(int numPersone) {
		
		for (Integer i: tavoli) {
			if (numPersone<=i && numPersone>=(i*minimaOccupazioneTavolo)) {
				return i;
			}
		}
		
		throw new RuntimeException("Non possibile assegnare un tavolo");
	}

	private boolean alTavolo(Event e) {
		int persone=e.getGruppo().getNumPersone();
		
		if (persone==1) {
			return false;
		}
		for (Integer i: tavoli) {
			if (persone<=i && persone>=(i/2)) {
				return true;
			}
		}
		
		return false;
	}

	public int getNumSoddisfatti() {
		return numSoddisfatti;
	}

	public int getNumInsoddisfatti() {
		return numInsoddisfatti;
	}
	
	
	
	
	}

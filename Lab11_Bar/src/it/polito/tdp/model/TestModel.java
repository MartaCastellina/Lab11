package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		Model m=new Model();
		int i=0;
		int j=0;
		int k=0;
		int l=0;
//		while (i<9) {
//		System.out.println(m.nuovaTolleranza());
//		i++;
//		}
//		while (j<9) {
//			System.out.println(m.nuovoNPersone());
//			j++;
//			}
//		while (k<9) {
//			System.out.println(m.nuovaPermanenza());
//			k++;
//			}
//		while (l<9) {
//			System.out.println(m.nuovoTArrivo());
//			l++;
//			}
		
		m.init();
		m.run();
		System.out.println("Numero persone soddisfatte "+m.getNumSoddisfatti());

		System.out.println("Numero persone insoddisfatte "+m.getNumInsoddisfatti());
		

		System.out.println("Numero persone totali "+(m.getNumSoddisfatti()+m.getNumInsoddisfatti()));
	}

}

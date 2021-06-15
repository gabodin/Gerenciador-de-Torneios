package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Rodada {
	private Integer id;
	private ArrayList<Partida> rodada;
	
	public Rodada() {
		rodada = new ArrayList<Partida>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<Partida> getPartidas() {
		return rodada;
	}
	
	public void adicionaPartida(Partida p) {
		rodada.add(p);
	}
	
	public int getNumeroPartidas() {
		return rodada.size();
	}
	
	public void deletaPartida(Partida p) {
		int idx = rodada.indexOf(p);
		rodada.remove(idx);
	}
	
	public Partida getPartida(int idx) {
		return rodada.get(idx);
	}
	
	public void reordenaPartidas() {
		Random gerador = new Random();
		int n =  rodada.size();
		for(int i=0; i < rodada.size(); i++) {
			Collections.swap(rodada, gerador.nextInt(n), i);
			n--;
		}
	}
	
	public boolean acabou() {
		for(Partida p: rodada) {
			if(p.acabou()) {
			  continue;	
			} else return false;
		}
		
		return true;
	}
}

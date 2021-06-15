package br.ufrn.imd.modelo;
import java.util.ArrayList;



public class Fase {
	private Integer id;
	private ArrayList<Chave> chaves = null;
	
	
	public Fase(Integer id) {
		this.id = id;
		chaves = new ArrayList<Chave>();
	}

	public Fase() {
		chaves = new ArrayList<Chave>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<Chave> getChaves() {
		return chaves;
	}
	
	public void setChaves(ArrayList<Chave> chaves) {
		this.chaves = chaves; 
	}
	
	public void adicionarChave(Chave chave) {
		chaves.add(chave);		
	}
	
	public void deletaChave(Chave ch) {
		int idx = chaves.indexOf(ch);
		chaves.remove(idx);
	}
	
	public int getNumeroChaves() {
		return this.chaves.size();
	}
	
	public Chave getChave(int idx) {
		return chaves.get(idx);
	}
	
	public void removerChave(int idx) {
		chaves.remove(idx);
	}
	
	public void setChave(int idx, Chave ch) {
		chaves.set(idx, ch);
	}
}

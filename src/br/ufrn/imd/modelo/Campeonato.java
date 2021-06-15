package br.ufrn.imd.modelo;

import java.util.ArrayList;

public class Campeonato implements InterfaceCampeonato {
	protected Integer id;
	protected String nome;
	protected ArrayList<Time> times;
	protected boolean idaEVolta;
	protected String formato;
	protected boolean golFora=false;
	
	
	public Campeonato() {
		this.times = new ArrayList<Time>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isIdaEVolta() {
		return idaEVolta;
	}

	public void setIdaEVolta(boolean idaEVolta) {
		this.idaEVolta = idaEVolta;
	}
	
	public boolean isGolFora() {
		return golFora;
	}
	
	public void setGolFora(boolean golFora) {
		this.golFora = golFora;
	}

	public ArrayList<Time> getTimes() {
		return times;
	}
	
	public boolean adicionarTime(Time time) {
		for(Time t : times) {
			if(t.getNome() == time.getNome()) {
				return false;
			}
		}
		if(time != null) {
			times.add(time);
			return true;
		}
		return false;
	}
	
	public void removerTime(Time time) {
		if(time != null) {
			times.remove(time);
		}
	}
	
	public Time getTime(String nome) {
		for(Time t : times) {
			if(t.getNome() == nome) {
				return t;
			}
		}
		return null;
	}

	@Override
	public void gerarTabela() {
		
	}

	@Override
	public ArrayList<Time> gerarClassificacao() {
		return null;
	}

	@Override
	public void ordenarClassificacao() {
		
	}

	@Override
	public void ordenarAtaque() {
		
	}

	@Override
	public void ordenarDefesa() {
		
		
	}

	@Override
	public ArrayList<Time> listarMelhorAtaque() {
		
		return null;
	}

	@Override
	public ArrayList<Time> listarMelhorDefesa() {
		
		return null;
	}

	@Override
	public ArrayList<Time> listarPiorAtaque() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Time> listarPiorDefesa() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	public String getFormato() {
		return formato;
	}
		
}

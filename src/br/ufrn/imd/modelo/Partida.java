package br.ufrn.imd.modelo;

public class Partida {
	private Time casa=null;
	private Time visitante=null;
	private Integer golsCasa = null;
	private Integer golsFora = null;
	private Integer id;
	private boolean isNull = true;
	
	public Partida() {
		golsCasa = null;
		golsFora = null;
	}
	
	public Time getCasa() {
		return casa;
	}
	public void setCasa(Time casa) {
		this.casa = casa;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;	
	}
	
	public Time getVisitante() {
		return visitante;
	}
	public void setVisitante(Time visitante) {
		this.visitante = visitante;
	}
	public Integer getGolsCasa() {
		return golsCasa;
	}
	
	public boolean isNull() {
		return this.isNull;
	}
	
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	
	public void setResultado(int golsCasa, int golsFora) {
		if(this.golsCasa != null && this.golsFora != null) {
			if(this.golsCasa > this.golsFora) {
				casa.retirarVitoria();
				visitante.retirarDerrota();
				
			}
			else if(this.golsCasa == this.golsFora) {
				casa.retirarEmpate();
				visitante.retirarEmpate();
			}
			else if(this.golsCasa < this.golsFora){
				casa.retirarDerrota();
				visitante.retirarVitoria();
			}
			casa.retirarGolsMarcados(this.golsCasa);
			visitante.retirarGolsSofridos(this.golsCasa);
			visitante.retirarGolsMarcados(this.golsFora);
			casa.retirarGolsSofridos(this.golsFora);
		}
		this.isNull = false;
		this.golsCasa = golsCasa;
		this.golsFora = golsFora;
	}
	public void setGolsCasa(Integer golsCasa) {
		this.golsCasa = golsCasa;	
	}
	public Integer getGolsFora() {
		return golsFora;
	}
	
	public void setGolsFora(Integer golsFora) {
		
		this.golsFora = golsFora;
	}
	
	public void atualizarTimes() {				
		casa.fazerGols(this.golsCasa);
		casa.levarGols(this.golsFora);
		visitante.fazerGols(golsFora);
		visitante.levarGols(golsCasa);
		
		if(this.golsCasa == this.golsFora) {
			casa.empatar();
			visitante.empatar();
		} else if(this.golsCasa > this.golsFora) {
			casa.ganhar();
			visitante.perder();
		} else {
			visitante.ganhar();
			casa.perder();
		}
		
		casa.obterJogos();
		visitante.obterJogos();
		
		casa.obterPontos();
		visitante.obterPontos();
		
		casa.obterSaldo();
		casa.obterAproveitamento();
		
		visitante.obterSaldo();
		visitante.obterAproveitamento();
		
				
	}
	
	public boolean acabou() {
		if(golsCasa != null && golsFora != null) {
			return true;
		} else return false;		
	}
	

	
	
	
}

package br.ufrn.imd.modelo;

import java.util.ArrayList;

import br.ufrn.imd.controle.CampeonatoController;

public class Chave {
	private Integer id;
	private ArrayList<Partida> partidas;
	private Time casa;
	private Time visitante; 
	
	
	public Chave(Integer id) {
		this.id = id;
		partidas = new ArrayList<Partida>();
	}
	
	public Chave() {
		partidas = new ArrayList<Partida>();
	}
	
	public void adicionarPartida(Partida pt) {
		partidas.add(pt);
	}
	
	public void removerPartidas(Partida pt) {
		int idx = partidas.indexOf(pt);
		partidas.remove(idx);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Partida> getPartidas() {
		return partidas;
	}
	
	public void setPartidas(ArrayList<Partida> partidas) {
		this.partidas = partidas;
	}
	
	public Time getCasa() {
		return this.casa;
	}
	
	public void adicionarCasa(Time casa) {
		this.casa = casa;
		
	}
	
	public Time getVisitante() {
		return this.visitante;
	}
	
	public void adicionarVisitante(Time visitante) {
		this.visitante = visitante;
	}
	
	private Campeonato campeonato = CampeonatoController.getInstance("Mata-Mata");
	 
	// Garantir que Time casa é time casa no partidas(0)
	
	public Time classificou() {
		int golsTotaisCasa = 0;
		int golsTotaisVisitante = 0;
		int golsForaCasa = 0;
		int golsForaVisitante = 0;
		
		if(campeonato.isIdaEVolta()) {
			if(partidas.get(0).isNull() || partidas.get(1).isNull()) {
				return null;
			}
		}
		
		if(campeonato.isGolFora()) {			
			if(campeonato.isIdaEVolta()) {
				
				golsTotaisCasa = partidas.get(0).getGolsCasa() + partidas.get(1).getGolsFora();
				golsTotaisVisitante = partidas.get(0).getGolsFora() + partidas.get(1).getGolsCasa();
				golsForaCasa = partidas.get(1).getGolsFora();
				golsForaVisitante = partidas.get(0).getGolsFora();
			
				System.out.println(casa.getNome() + " " + golsTotaisCasa + " x " + golsTotaisVisitante + " " + visitante.getNome());
				
				if(golsTotaisCasa > golsTotaisVisitante) {
					return casa;
				} else if(golsTotaisCasa < golsTotaisVisitante) {
					return visitante;
				}
				else {
					if(golsForaCasa > golsForaVisitante) {
						return casa;
					}
					else if(golsForaCasa < golsForaVisitante) {
						return visitante;
					}
					else {
						return null;
					}
				}
				
			} 
			
			else 
			{
				
				golsTotaisCasa = partidas.get(0).getGolsCasa();
				golsTotaisVisitante = partidas.get(0).getGolsFora();
				golsForaVisitante = partidas.get(0).getGolsFora();
				
				if(golsTotaisCasa > golsTotaisVisitante) {
					return casa;
				} else if(golsTotaisCasa < golsTotaisVisitante) {
					return visitante;
				} else {
					if(partidas.get(0).getGolsFora() > 0) {
						return visitante;
					}
					else return null;
				}
				
			} 
			
		}		
		else {
			
			if(campeonato.isIdaEVolta()) {
				golsTotaisCasa = partidas.get(0).getGolsCasa() + partidas.get(1).getGolsFora();
				golsTotaisVisitante = partidas.get(0).getGolsFora() + partidas.get(1).getGolsCasa();
				System.out.println(golsTotaisCasa + " x " + golsTotaisVisitante);
				if(golsTotaisCasa > golsTotaisVisitante) {
					return casa;
				}
				else if(golsTotaisCasa < golsTotaisVisitante) {
					return visitante;
				}
				else return null;
			}
			else {
				golsTotaisCasa = partidas.get(0).getGolsCasa();
				golsTotaisVisitante = partidas.get(0).getGolsFora();
				
				if(golsTotaisCasa > golsTotaisVisitante) {
						return casa;
				} else if(golsTotaisCasa < golsTotaisVisitante) {
						return visitante;
				} else {
						return null;
				}
			}
		}
	}
	
	public Integer getGolsTotaisCasa() {
		Integer gols = 0;
		
		if(partidas.get(0).getGolsCasa() != null) {
			gols = partidas.get(0).getGolsCasa();
		} 
		
		if(campeonato.isIdaEVolta()) {
			if(partidas.get(1).getGolsFora() != null) {
				gols += partidas.get(1).getGolsFora();
			}
		}
		
		return gols;
		
	}
	
	public Integer getGolsTotaisVisitante() {
		Integer gols = 0;
		
		if(partidas.get(0).getGolsFora() != null) {
			gols = partidas.get(0).getGolsFora();
		} 
		
		if(campeonato.isIdaEVolta()) {
			if(partidas.get(1).getGolsCasa() != null) {
				gols += partidas.get(1).getGolsCasa();
			}
		}
		
		return gols;
		
	}
	
}

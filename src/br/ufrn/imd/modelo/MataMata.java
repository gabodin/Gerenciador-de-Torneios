package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MataMata extends Campeonato implements InterfaceCampeonato {
	
	private ArrayList<Fase> fases;
	
	public MataMata() {
		fases = new ArrayList<Fase>();
	}
	
	@Override
	public void gerarTabela() {
		int numFases = logBaseTwo(this.times.size());
		int numChaves = this.times.size();
		
		for(int i = 0; i < numFases; i++) {
			numChaves = numChaves/2;
			this.adicionarFase(i);
			
			if(i > 0) {
				for(int j = 0; j < numChaves; j++) {
					Chave ch = new Chave(j);
					
					ch.adicionarPartida(new Partida());
					
					if(this.isIdaEVolta()) {
						ch.adicionarPartida(new Partida());
					}
					
					fases.get(i).adicionarChave(ch);
				}
			}
							
		}
		
		Collections.shuffle(this.times);
		
		int aux = times.size() - 1;
		
		for(int counter = 0; counter < (times.size() / 2); counter++) {
			Chave ch = new Chave(counter);
			ch.adicionarCasa(times.get(counter));
			ch.adicionarVisitante(times.get(aux));
			
			Partida pt1 = new Partida();
			pt1.setCasa(ch.getCasa());
			pt1.setVisitante(ch.getVisitante());
			pt1.setId(0);
			ch.adicionarPartida(pt1);
			
			Partida pt2 = new Partida();
			if(this.isIdaEVolta()) {				
				pt2.setVisitante(ch.getCasa());
				pt2.setCasa(ch.getVisitante());
				pt2.setId(1);
				ch.adicionarPartida(pt2);
			}
								
			aux--;
			fases.get(0).adicionarChave(ch);
		}
				
	}
	
	public void addFase(Fase f) {
		fases.add(f);
	}
	
	public Fase getFase(int n) {
		return fases.get(n);
	}
	
	public ArrayList<Fase> getFases() {
		return fases;
	}
	
	public void setFases(ArrayList<Fase> fases) {
		this.fases = fases;
	}
	
	public void adicionarFase(Integer id) {
		Fase f = new Fase(id);
		this.fases.add(f);
	}

	@Override
	public ArrayList<Time> gerarClassificacao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ordenarClassificacao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ordenarAtaque() {
		Comparator<Time> comp = (t1, t2) -> {
			return t1.getGolsMarcados().compareTo(t2.getGolsMarcados());
		};
		times.sort(comp);
		
	}

	@Override
	public void ordenarDefesa() {
		Comparator<Time> comp = (t1, t2) -> {
			return -(t1.getGolsSofridos().compareTo(t2.getGolsSofridos()));
		};
		
		times.sort(comp);
		
	}

	@Override
	public ArrayList<Time> listarMelhorAtaque() {
		this.ordenarAtaque();
		Collections.reverse(times);
		return times;
	}

	@Override
	public ArrayList<Time> listarMelhorDefesa() {
		this.ordenarDefesa();
		Collections.reverse(times);
		return times;
	}

	@Override
	public ArrayList<Time> listarPiorAtaque() {
		this.ordenarAtaque();
		return times;
	}

	@Override
	public ArrayList<Time> listarPiorDefesa() {
		this.ordenarDefesa();
		return times;
	}
	
	public void proximaFase(Time time, int idChave, int idFase) {
		int idChaveProximaFase = idChave / 2;
		int casaOuVisitante = (idChave + 1) / 2;
		
		// System.out.println("BORA");
		
		if(idFase+1 < this.fases.size()) {

			if(idChaveProximaFase != casaOuVisitante) {
				
					if(fases.get(idFase+1).getChave(idChaveProximaFase).getVisitante() != null) {
						Chave ch = new Chave(idChaveProximaFase);
						Partida pt1 = new Partida();
						Partida pt2 = new Partida();
						
						// Atualizando Estatísticas dos Times
						Time visitante = fases.get(idFase+1).getChave(idChaveProximaFase).getVisitante();
						visitante.retirarGolsMarcados(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisVisitante());
						visitante.retirarGolsSofridos(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisCasa());
						// Retirados os gols marcados e sofridos
						
						if(fases.get(idFase+1).getChave(idChaveProximaFase).getCasa() != null) {
							
							Time t = fases.get(idFase+1).getChave(idChaveProximaFase).getCasa();
							
							t.retirarGolsMarcados(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisCasa());
							t.retirarGolsSofridos(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisVisitante());
							
							
	
							ch.adicionarCasa(t);
							pt1.setCasa(t);
							pt2.setVisitante(t);
						}
												
						fases.get(idFase+1).setChave(idChaveProximaFase, ch);
						pt1.setId(0);
						fases.get(idFase+1).getChave(idChaveProximaFase).adicionarPartida(pt1);
						
						if(this.isIdaEVolta()) {
							pt2.setId(1);
							fases.get(idFase+1).getChave(idChaveProximaFase).adicionarPartida(pt2);	
						}
					}
					
					fases.get(idFase+1).getChave(idChaveProximaFase).adicionarVisitante(time);
					fases.get(idFase+1).getChave(idChaveProximaFase).getPartidas().get(0).setVisitante(time);
					
					if(this.isIdaEVolta()) {
						fases.get(idFase+1).getChave(idChaveProximaFase).getPartidas().get(1).setCasa(time);
					}
													
			}
			else {
					if(fases.get(idFase+1).getChave(idChaveProximaFase).getCasa() != null) {
						Chave ch = new Chave(idChaveProximaFase);
						Partida pt1 = new Partida();
						Partida pt2 = new Partida();
						
						// Atualizando Estatísticas dos Times
						Time casa = fases.get(idFase+1).getChave(idChaveProximaFase).getCasa();
						casa.retirarGolsMarcados(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisCasa());
						casa.retirarGolsSofridos(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisVisitante());
						// Retirados os gols marcados e sofridos
						
						
						if(fases.get(idFase+1).getChave(idChaveProximaFase).getVisitante() != null) {
							Time t = fases.get(idFase+1).getChave(idChaveProximaFase).getVisitante();
							
							t.retirarGolsMarcados(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisVisitante());
							t.retirarGolsSofridos(fases.get(idFase+1).getChave(idChaveProximaFase).getGolsTotaisCasa	());
							
							ch.adicionarVisitante(t);
							pt2.setCasa(t);
							pt1.setVisitante(t);
						}
												
						fases.get(idFase+1).setChave(idChaveProximaFase, ch);
						pt1.setId(0);
						fases.get(idFase+1).getChave(idChaveProximaFase).adicionarPartida(pt1);
						
						if(this.isIdaEVolta()) {
							pt2.setId(1);
							fases.get(idFase+1).getChave(idChaveProximaFase).adicionarPartida(pt2);	
						}
						
						
					}
					
					fases.get(idFase+1).getChave(idChaveProximaFase).adicionarCasa(time);
					fases.get(idFase+1).getChave(idChaveProximaFase).getPartidas().get(0).setCasa(time);
					
					if(this.isIdaEVolta()) {
						fases.get(idFase+1).getChave(idChaveProximaFase).getPartidas().get(1).setVisitante(time);
					}
				
			}
		}
				
	}
	
	public static int logBaseTwo(int x) {
    	return (int) (Math.log(x) / Math.log(2));
	}
}

package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;




public class PontosCorridos extends Campeonato implements InterfaceCampeonato {
	
	private ArrayList<Rodada> rodadas;
	
	public PontosCorridos(ArrayList<Rodada> rodadas) {
		super();
		rodadas = new ArrayList<Rodada>();
	}

	public PontosCorridos() {
		rodadas = new ArrayList<Rodada>();
	}
	
	public ArrayList<Rodada> getRodadas() {
		return rodadas;
	}

	@Override
	public void gerarTabela() {
		Collections.shuffle(times);
		gerarIda();
		if(idaEVolta) {
			gerarVolta();
		}
		for(Time t : times) {
			if(t.getNome() == null) {
				times.remove(t);
				break;
			}
		}
	}
	
	public void addRodada(Rodada r) {
		rodadas.add(r);
	}
	
	public Rodada getRodada(int idx) {
		return rodadas.get(idx);
	}
	
	public int getNumRodadas() {
		return rodadas.size();
	}
	
	private void gerarIda() {
		int size = times.size()-1;
		if(size % 2 == 0) size++;
		for(int i=0; i<size; i++) {
			gerarRodada(true);
		}
	}
	
	private void gerarVolta() {
		int size = times.size()-1;
		if(size % 2 == 0) size++;
		for(int i=0; i<size; i++) {
			gerarRodada(false);
		}
	}
	
	private void gerarRodada(boolean ida) {
		Time folga = new Time();
		if(times.size() % 2 != 0) {
			times.add(folga);
		}
		
		
		
		int aux = times.size() - 1;
		Rodada rodada = new Rodada();
		for(int i=0; i < times.size()/2; i++) {
			Partida partida = new Partida();
			if(ida) {
				partida.setCasa(times.get(i));
				partida.setVisitante(times.get(aux));
			}
			else {
				partida.setVisitante(times.get(i));
				partida.setCasa(times.get(aux));
			}
			aux--;
			
			Time casa = partida.getCasa();
			Time visitante = partida.getVisitante();
			
			if(casa.getNome() != null && visitante.getNome() != null) rodada.adicionaPartida(partida);
		}
		rodada.reordenaPartidas();
		this.rodadas.add(rodada);
		rotacionarTimes();
	}
	
	private void rotacionarTimes() {
		Collections.rotate(times, 1);
		Collections.swap(times, 0, times.size()-1);
	}

	@Override
	public ArrayList<Time> gerarClassificacao() {
		this.ordenarClassificacao();
		
		return times;
	}

	@Override
	public void ordenarClassificacao() {
		Collections.sort(times);
		Collections.reverse(times);
		int pos=1;
		for(Time t : times) {
			t.setPosicao(pos);
			pos++;
		}
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
	
	public boolean acabou() {
		for(Rodada r : rodadas) {
			if(r.acabou()) {
				continue;
			} 
			else {
				return false;
			}
		} 
		return true;
	}
	
	public Time vencedor() {
		if(this.acabou()) {
			this.ordenarClassificacao();
			return times.get(0);
		} else return null;
	}
	
	
	
}

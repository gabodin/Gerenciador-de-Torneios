package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.MataMata;
import br.ufrn.imd.modelo.PontosCorridos;

public class CampeonatoController {
	private static Campeonato campeonato;
	

	@SuppressWarnings("exports")
	public static Campeonato getInstance(String formato) {
		if(campeonato == null) {
			if(formato == "Pontos Corridos") {
				campeonato = new PontosCorridos();
			}
			else if(formato == "Mata-Mata") {
				campeonato = new MataMata();
			}
		}
		return campeonato;
	}
	
	public static void limparCampeonato() {
		campeonato = null;
	}
	
	
}

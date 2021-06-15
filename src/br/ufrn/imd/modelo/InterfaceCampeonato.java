package br.ufrn.imd.modelo;


import java.util.ArrayList;

public interface InterfaceCampeonato {
	
	void gerarTabela();
	ArrayList<Time> gerarClassificacao();
	void ordenarClassificacao();
	void ordenarAtaque();
	void ordenarDefesa();
	ArrayList<Time> listarMelhorAtaque();
	ArrayList<Time> listarMelhorDefesa();
	ArrayList<Time> listarPiorAtaque();
	ArrayList<Time> listarPiorDefesa();
	
}

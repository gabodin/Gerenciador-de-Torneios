package br.ufrn.imd.modelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Time implements Comparable<Time> {
	private String nome=null;
	private Integer posicao=1;
	// private String estadio;
	private Integer jogos=0;
	private Integer pontos=0;
	private Integer vitorias=0;
	private Integer empates=0;
	private Integer derrotas=0;
	private Integer golsMarcados=0;
	private Integer golsSofridos=0;
	private Integer saldo=0;
	private Double aproveitamento=0.0;
	
	public Time(Integer posicao, String nome, Integer pontos) {
		this.nome = nome;
		this.posicao = posicao;
		this.pontos = pontos;
		this.jogos = 0;
		this.vitorias = 0;
		this.empates = 0;
		this.derrotas = 0;
		this.golsMarcados = 0;
		this.golsSofridos = 0;
		this.saldo = 0;
		this.aproveitamento = 0.0;
	}

	public Time(Integer posicao, String nome, Integer jogos, Integer pontos, Integer vitorias, Integer empates,
			Integer derrotas, Integer golsMarcados, Integer golsSofridos, Integer saldo, Double aproveitamento) {
		this.nome = nome;
		this.posicao = posicao;
		this.jogos = jogos;
		this.pontos = pontos;
		this.vitorias = vitorias;
		this.empates = empates;
		this.derrotas = derrotas;
		this.golsMarcados = golsMarcados;
		this.golsSofridos = golsSofridos;
		this.saldo = saldo;
		this.aproveitamento = aproveitamento;
	}
	
	public Time() {
		
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getJogos() {
		return jogos;
	} 
	
	public void setJogos(Integer jogos) {
		this.jogos = jogos;
	}
	
	public Integer getPontos() {
		return pontos;
	}
	
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
	public Integer getVitorias() {
		return vitorias;
	}
	
	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}
	
	public Integer getDerrotas() {
		return derrotas;
	}
	
	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}
	
	public Integer getEmpates() {
		return empates;
	}
	
	public void setEmpates(Integer empates) {
		this.empates = empates;
	}
	
	public Integer getGolsSofridos() {
		return golsSofridos;
	}
	
	public void setGolsSofridos(Integer golsSofridos) {
		this.golsSofridos = golsSofridos;
	}
	
	public Integer getGolsMarcados() {
		return golsMarcados;
	}
	
	public void setGolsMarcados(Integer golsMarcados) {
		this.golsMarcados = golsMarcados;
	}
	
	public Integer getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}
	
	public Double getAproveitamento() {
		return aproveitamento;
	}
	
	public void setAproveitamento(Double aproveitamento) {
		this.aproveitamento = aproveitamento;
	}
	
	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public void fazerGols(Integer n) {
		this.golsMarcados += n;
	}
	
	public void levarGols(Integer n) {
		this.golsSofridos += n;
	}
	
	public void ganhar() {
		this.vitorias += 1;
	}
	
	public void empatar() {
		this.empates += 1;
	}
	
	public void perder() {
		this.derrotas += 1;
	}
	
	public void obterAproveitamento() {
		DecimalFormatSymbols separador = new DecimalFormatSymbols();
		separador.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("0.0", separador);
		if(this.jogos > 0) {
			this.aproveitamento = Double.parseDouble(formato.format(((double)
					pontos / (jogos * 3)) * 100.0));
		}
		else this.aproveitamento = 0.0;
		
		// this.aproveitamento = (jogos > 0) ? (this.pontos / (jogos * 3)) : 0.0; 
	}
	
	public void obterPontos() {
		this.pontos = (vitorias * 3) + empates;
	}
	
	public void obterSaldo() {
		this.saldo = golsMarcados - golsSofridos;
	}
	
	public void obterJogos() {
		this.jogos = vitorias + empates + derrotas;
	}
	
	@Override
	public int compareTo(Time other) {
		if(other == null) {
			throw new NullPointerException();
		}
				
		if(this.pontos > other.pontos) {
			return 1;
		}
		else if(this.pontos < other.pontos) {
			return -1;
		}
		
		else {
			if(this.vitorias > other.vitorias) {
				return 1;
			}			
			else if(this.vitorias < other.vitorias) {
				return -1;
			}			
			else {
				if(this.saldo > other.saldo) {
					return 1;
				}				
				else if(this.saldo < other.saldo) {
					return -1;
				}				
				else {
					if(this.golsMarcados > other.golsMarcados) {
						return 1;
					}					
					else if(this.golsMarcados < other.golsMarcados) {
						return -1;
					}					
					if(this.golsMarcados == other.golsMarcados) {						
						return this.nome.compareTo(other.nome);
					}
				}
			}
			
		}
		return 0;
	}
	
	public void retirarVitoria() {
		this.vitorias--;		
	}
	
	public void retirarDerrota() {
		this.derrotas--;
	}
	
	public void retirarEmpate() {
		this.empates--;
	}
	
	public void adicionarJogo() {
		this.jogos++;
	}
	
	public void retirarJogo() {
		this.jogos--;
	}
	
	public void retirarGolsMarcados(Integer golsMarcados) {
		this.golsMarcados -= golsMarcados;
	}
	
	public void retirarGolsSofridos(Integer golsSofridos) {
		this.golsSofridos -= golsSofridos;
	}
	
	
		 
}

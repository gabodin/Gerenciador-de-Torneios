package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import br.ufrn.imd.controle.CampeonatoController;
import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.Chave;
import br.ufrn.imd.modelo.Fase;
import br.ufrn.imd.modelo.MataMata;
import br.ufrn.imd.modelo.Partida;
import br.ufrn.imd.modelo.PontosCorridos;
import br.ufrn.imd.modelo.Rodada;
import br.ufrn.imd.modelo.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CampeonatoDAO {
	
	private BancoDeDados banco;
	private Connection conexao;
	private static CampeonatoDAO campeonatos;
	private Campeonato campeonato;
	
	public CampeonatoDAO() throws SQLException {
		banco = new BancoDeDados();
		conexao = banco.getConexao();
	}
	
	public static CampeonatoDAO getInstance() throws SQLException {
		if(campeonatos == null) {
			campeonatos = new CampeonatoDAO();
		}
		return campeonatos;
	}
	
	public int getIdCampeonato(String nome) throws SQLException {
		Integer id = null;
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM campeonatos WHERE nome = ?");
		pstmt.setString(1, nome);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			id = rs.getInt("id");
		}
		return (int) id;
	}
	
	public String getFormatoCampeonato(String nome) throws SQLException {
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM campeonatos WHERE nome = ?");
		pstmt.setString(1, nome);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString("formato").intern();
		}
		return null;
	}
	
	public void removerCampeonato(int id) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("DELETE FROM timesCampeonatos WHERE idCampeonato = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM partidas WHERE idCampeonato = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM rodadas WHERE idCampeonato = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM chaves WHERE idCampeonato = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM fases WHERE idCampeonato = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM campeonatos WHERE id = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	public void removerTodosCampeonato() throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("DELETE FROM partidas");
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM rodadas");
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM chaves");
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM fases");
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM timesCampeonatos");
		pstmt.executeUpdate();
		pstmt.close();
		
		pstmt = conexao.prepareStatement
				("DELETE FROM campeonatos");
		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	public void addCampeonato(Campeonato c) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("INSERT INTO campeonatos (nome,formato,idaEVolta,golsFora)"
				+ "VALUES(?,?,?,?)");
		
		pstmt.setString(1, c.getNome());
		pstmt.setString(2, c.getFormato());
		pstmt.setBoolean(3, c.isIdaEVolta());
		pstmt.setBoolean(4, c.isGolFora());
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public void addRodadas(int idCampeonato) throws SQLException {
		campeonato = CampeonatoController.getInstance("PontosCorridos");
		campeonato.gerarTabela();
		for(int id=0; id < ((PontosCorridos)campeonato).getNumRodadas(); id++) {
			PreparedStatement pstmt = conexao.prepareStatement
					("INSERT INTO rodadas (id,idCampeonato)"
					+ "VALUES (?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, idCampeonato);
			pstmt.executeUpdate();
			pstmt.close();
			
			Rodada rodada = ((PontosCorridos)campeonato).getRodada(id);
			rodada.setId(id);
			
			for(Partida p : rodada.getPartidas()) {
				pstmt = conexao.prepareStatement
						("INSERT INTO partidas "
						+ "(idCampeonato,idRodada,casa,visitante,"
						+ "golsCasa,golsVisitante,isNula)" + 
						"VALUES (?,?,?,?,?,?,?)");
				pstmt.setInt(1, idCampeonato);
				pstmt.setInt(2, id);
				pstmt.setString(3, p.getCasa().getNome());
				pstmt.setString(4, p.getVisitante().getNome());
				pstmt.setNull(5,Types.INTEGER);
				pstmt.setNull(6, Types.INTEGER);
				pstmt.setBoolean(7, true);
				
				pstmt.executeUpdate();
				
				pstmt.close();
			}
		}
	}
	
	public void addFases(int idCampeonato) throws SQLException {
		campeonato = CampeonatoController.getInstance("Mata-Mata");
		campeonato.gerarTabela();
		
		for(int id=0; id < ((MataMata)campeonato).getFases().size(); id++) {
			PreparedStatement pstmt = conexao.prepareStatement
					("INSERT INTO fases (id,idCampeonato)"
					+ "VALUES (?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, idCampeonato);
			pstmt.executeUpdate();
			pstmt.close();
			
			Fase fase = ((MataMata)campeonato).getFase(id);
			fase.setId(id);
			
			int idChave =0;
			for(Chave c : fase.getChaves()) {
				pstmt = conexao.prepareStatement
						("INSERT INTO chaves (id,idCampeonato, idFase)"
						+ "VALUES (?,?,?)");
				pstmt.setInt(1, idChave);
				pstmt.setInt(2, idCampeonato);
				pstmt.setInt(3, id);
				
				pstmt.executeUpdate();
				pstmt.close();
				
				c.setId(idChave);
				
				int idP=0;
				for(Partida p : c.getPartidas()) {
					pstmt = conexao.prepareStatement
							("INSERT INTO partidas "
							+ "(idCampeonato,idChave,casa,visitante,"
							+ "golsCasa,golsVisitante,isNula,idFase,id)" + 
							"VALUES (?,?,?,?,?,?,?,?,?)");
					pstmt.setInt(1, idCampeonato);
					pstmt.setInt(2, idChave);
					if(p.getCasa() != null) pstmt.setString(3, p.getCasa().getNome());
					if(p.getVisitante() != null) pstmt.setString(4, p.getVisitante().getNome());
					pstmt.setNull(5,Types.INTEGER);
					pstmt.setNull(6, Types.INTEGER);
					pstmt.setBoolean(7, true);
					pstmt.setInt(8, id);
					pstmt.setInt(9, idP);
					
					p.setId(idP);
					
					pstmt.executeUpdate();
					
					
					pstmt.close();
					idP++;
				}
				idChave++;
				
			}
		}
	}
	
	public ObservableList<String> listarCampeonatos() throws SQLException{
		ObservableList<String> lista;
		lista = FXCollections.observableArrayList();
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM campeonatos");
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			lista.add(rs.getString("nome"));
		}
		
		pstmt.close();
		return lista;
	}
	
	public void adicionarTime(Time time, Integer idCamp) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement("INSERT INTO timesCampeonatos " +
				"(idCampeonato, posicao, nome,  jogos,  pontos,  vitorias,  empates, derrotas,  golsMarcados,  golsSofridos,  saldo, aproveitamento)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		pstmt.setInt(1, idCamp);
		pstmt.setInt(2, time.getPosicao());
		pstmt.setString(3, time.getNome());
		pstmt.setInt(4, time.getJogos());
		pstmt.setInt(5, time.getPontos());
		pstmt.setInt(6, time.getVitorias());
		pstmt.setInt(7, time.getEmpates());
		pstmt.setInt(8, time.getDerrotas());
		pstmt.setInt(9, time.getGolsMarcados());
		pstmt.setInt(10, time.getGolsSofridos());
		pstmt.setInt(11, time.getSaldo());
		pstmt.setDouble(12, time.getAproveitamento());
		pstmt.executeUpdate();
		pstmt.close();
		
	} 
	
	public ArrayList<Time> obterTimes(Integer idCampeonato) throws SQLException {
		ArrayList<Time> times = new ArrayList<Time>();
		PreparedStatement pstmt = conexao.prepareStatement(
			"SELECT * from timesCampeonatos"
			+ " WHERE (idCampeonato = ?)"
			);
		pstmt.setInt(1, idCampeonato);
		
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			Time time = new Time();
			time.setNome(rs.getString("nome").intern());
			time.setPosicao(rs.getInt("posicao"));
			time.setPontos(rs.getInt("pontos"));
			time.setJogos(rs.getInt("jogos"));
			time.setVitorias(rs.getInt("vitorias"));
			time.setEmpates(rs.getInt("empates"));
			time.setDerrotas(rs.getInt("derrotas"));
			time.setGolsMarcados(rs.getInt("golsMarcados"));
			time.setGolsSofridos(rs.getInt("golsSofridos"));
			time.setSaldo(rs.getInt("saldo"));
			time.setAproveitamento(rs.getDouble("aproveitamento"));
						
			times.add(time);
		}
		
		return times;
	}
	
	public void carregarRodadas(int id) throws SQLException {
		campeonato = CampeonatoController.getInstance("Pontos Corridos");
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM rodadas WHERE (idCampeonato = ?)");
		
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			Rodada r = new Rodada();
			
			r.setId(rs.getInt("id"));
			((PontosCorridos)campeonato).addRodada(r);
		}
		
		pstmt.close();
	}
	
	public void carregarCampeonato(int id) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM campeonatos WHERE (id = ?)");
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			campeonato = CampeonatoController.getInstance(rs.getString("formato").intern());
			campeonato.setNome(rs.getString("nome").intern());
			campeonato.setIdaEVolta(rs.getBoolean("idaEVolta"));
			campeonato.setGolFora(rs.getBoolean("golsFora"));
			campeonato.setId(rs.getInt("id"));
			campeonato.setFormato(rs.getString("formato").intern());
		}
	}
	
	public void carregarPartidas(int id, String formato) throws SQLException {
		campeonato = CampeonatoController.getInstance(formato);
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM partidas WHERE (idCampeonato = ?)");
		
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			Partida p = new Partida();
			
			if(rs.getString("casa") != null) p.setCasa(campeonato.getTime(rs.getString("casa").intern()));
			if(rs.getString("visitante") != null)p.setVisitante(campeonato.getTime(rs.getString("visitante").intern()));
			
			p.setNull(rs.getBoolean("isNula"));
			if(!p.isNull()) {
				p.setGolsCasa(rs.getInt("golsCasa"));
				p.setGolsFora(rs.getInt("golsVisitante"));
			}
			
			if(formato == "Pontos Corridos") {
				Rodada r = ((PontosCorridos)campeonato).getRodada(rs.getInt("idRodada"));
				r.adicionaPartida(p);
			}
			else if(formato == "Mata-Mata") {
				p.setId(rs.getInt("id"));
				
				Fase f = ((MataMata)campeonato).getFase(rs.getInt("idFase"));
				Chave c = f.getChave(rs.getInt("idChave"));
				
				if(p.getId() == 0) {
					c.adicionarCasa(p.getCasa());
					c.adicionarVisitante(p.getVisitante());
				}
				c.adicionarPartida(p);
			}
		}
		
		pstmt.close();
	}
	
	public void carregarFases(int id) throws SQLException {
		campeonato = CampeonatoController.getInstance("Mata-Mata");
		
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM fases WHERE (idCampeonato = ?) ORDER BY id");
		
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			Fase f = new Fase();
			
			f.setId(rs.getInt("id"));
			((MataMata)campeonato).addFase(f);
		}
		
		pstmt.close();
	}
	
	public void carregarChaves(int id) throws SQLException {
		campeonato = CampeonatoController.getInstance("Mata-Mata");
		PreparedStatement pstmt = conexao.prepareStatement
				("SELECT * FROM chaves WHERE (idCampeonato = ?) ORDER BY id");
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			Chave c = new Chave();
			c.setId(rs.getInt("id"));
			Fase f = ((MataMata)campeonato).getFase(rs.getInt("idFase"));
			f.adicionarChave(c);
		}
		pstmt.close();
	}
	
	public void salvarCampeonato(Integer idCampeonato, String formato) throws SQLException {
		campeonato = CampeonatoController.getInstance(formato);
		PreparedStatement pstmt = null;
		
		pstmt = conexao.prepareStatement
				("UPDATE campeonatos SET idaEVolta = ?, golsFora = ?" + 
				 "WHERE id = ?");
		pstmt.setBoolean(1, campeonato.isIdaEVolta());
		pstmt.setBoolean(2, campeonato.isGolFora());
		pstmt.setInt(3, campeonato.getId());
		
		// UPDATE timesCampeonatos
		
		ArrayList<Time> times = campeonato.getTimes();
		for(Time time: times) {
			pstmt = conexao.prepareStatement(
				"UPDATE timesCampeonatos" 
				+ " SET posicao = ?,"
				+ " pontos = ?, jogos = ?, vitorias = ?, empates = ?, derrotas = ?, golsMarcados = ?, golsSofridos = ?,"
				+ " saldo = ?, aproveitamento = ?"
				+ "WHERE idCampeonato = ? AND nome = ?"
			);
				
			pstmt.setInt(1, time.getPosicao());
			pstmt.setInt(2, time.getPontos());
			pstmt.setInt(3, time.getJogos());
			pstmt.setInt(4, time.getVitorias());
			pstmt.setInt(5, time.getEmpates());
			pstmt.setInt(6, time.getDerrotas());
			pstmt.setInt(7, time.getGolsMarcados());
			pstmt.setInt(8, time.getGolsSofridos());
			pstmt.setInt(9, time.getSaldo());
			pstmt.setDouble(10, time.getAproveitamento());
			pstmt.setInt(11, idCampeonato);
			pstmt.setString(12, time.getNome());
			pstmt.executeUpdate();			
			pstmt.close();
		}
		
		// UPDATE rodadas AND partidas
		
		if(formato == "Pontos Corridos") {
			if(campeonato instanceof PontosCorridos) {
				PontosCorridos camp = (PontosCorridos) campeonato;
				ArrayList<Rodada> rodadas = camp.getRodadas();
				
				for(Rodada rodada : rodadas) {
					ArrayList<Partida> partidas = rodada.getPartidas();
					
					for(Partida partida : partidas) {
						if(partida.isNull()) {
							continue;
						}
						pstmt = conexao.prepareStatement(
							"UPDATE partidas"
							+ " SET golsCasa = ?, golsVisitante = ?, isNula = ?"
							+ " WHERE idCampeonato = ? AND idRodada = ? AND casa = ? AND visitante = ?"
						);
					
						
					
						pstmt.setInt(1, partida.getGolsCasa());
						pstmt.setInt(2, partida.getGolsFora());
						pstmt.setBoolean(3, partida.isNull());
						pstmt.setInt(4, idCampeonato);
						pstmt.setInt(5, rodada.getId());
						pstmt.setString(6, partida.getCasa().getNome());
						pstmt.setString(7, partida.getVisitante().getNome());
						
						pstmt.executeUpdate();
						pstmt.close();
						
					}
					
					
				}
				
				
				
			}
			
		}
		else if(formato == "Mata-Mata") {
			MataMata camp = (MataMata)campeonato;
			
			for(Fase f : camp.getFases()) {
				for(Chave c : f.getChaves()) {
					for(Partida p : c.getPartidas()) {
						
						
						pstmt = conexao.prepareStatement(
								"UPDATE partidas"
								+ " SET golsCasa = ?, golsVisitante = ?, isNula = ?, casa = ?, visitante = ?"
								+ " WHERE idCampeonato = ? AND idFase = ? AND idChave = ? AND id = ?"
							);
						
						
						
							if(p.getGolsCasa() != null) 
								pstmt.setInt(1, p.getGolsCasa());
							if(p.getGolsFora() != null) 
								pstmt.setInt(2, p.getGolsFora());
							pstmt.setBoolean(3, p.isNull());
							if(p.getCasa() != null) 
								pstmt.setString(4, p.getCasa().getNome());
							if(p.getVisitante() != null)
								pstmt.setString(5, p.getVisitante().getNome());
							pstmt.setInt(6, idCampeonato);
							pstmt.setInt(7, f.getId());
							pstmt.setInt(8, c.getId());
							pstmt.setInt(9, p.getId());
							
							pstmt.executeUpdate();
							pstmt.close();
					}
				}
			}
		}
		
		pstmt.close();
	} 
	
	
	
}

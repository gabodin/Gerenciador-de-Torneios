package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufrn.imd.modelo.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimeDAO {
	
	private BancoDeDados banco;
	private static TimeDAO times;
	private Connection conexao;
	
	
	public TimeDAO() throws SQLException {
		banco = new BancoDeDados();
		conexao = banco.getConexao();
	}
	
	public static TimeDAO getInstance() throws SQLException {
		if(times == null) {
			times = new TimeDAO();
		}
		return times;
	}
	
	public void addTime(Time time) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("INSERT INTO times (nome)" + "VALUES (?)");
		pstmt.setString(1, time.getNome());
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public ObservableList<String> listarNomeDosTimes() throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement("SELECT * FROM times");
		ResultSet rs = pstmt.executeQuery();
		ObservableList<String> lista = FXCollections.observableArrayList();
		while(rs.next()) {
			lista.add(rs.getString("nome"));
		}
		pstmt.close();
		return lista;
	}
	
	public void removerTime(String nome) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("DELETE FROM times WHERE nome = ?");
		pstmt.setString(1, nome);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public void removerTodosTimes() throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement
				("DELETE FROM times");
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public void editarTime(String nomeAntigo, String nomeNovo) throws SQLException {
		PreparedStatement pstmt = conexao.prepareStatement(
				"UPDATE times " +
				"SET nome = ?"  +
				"WHERE (nome = ?)"
			);
		pstmt.setString(1, nomeNovo);
		pstmt.setString(2, nomeAntigo);
		pstmt.executeUpdate();
		pstmt.close();
	}
}

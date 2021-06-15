package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {
	private static Connection conexao = null;
	
	public Connection getConexao() throws SQLException{
		if(conexao == null) {
			String url = "jdbc:sqlite:db/database.db/";
			conexao = DriverManager.getConnection(url);
		}
		return conexao;
	}
	
	public void closeConexao() throws SQLException {
		if(conexao != null) {
			conexao.close();
		}
	}
	
	
}

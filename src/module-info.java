module Gerenciador_de_Torneios {
	requires  transitive javafx.graphics;
	requires  transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.base;
	requires transitive java.sql;
	
	exports br.ufrn.imd to javafx.graphics;
	exports br.ufrn.imd.controle to javafx.fxml;
	
	opens br.ufrn.imd.controle;
	opens br.ufrn.imd.modelo to javafx.base;
	
}
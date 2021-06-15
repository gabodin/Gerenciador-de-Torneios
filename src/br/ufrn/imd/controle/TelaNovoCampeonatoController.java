package br.ufrn.imd.controle;


import java.io.IOException;
import java.sql.SQLException;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.modelo.Campeonato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaNovoCampeonatoController {
	
	private Stage novoCampeonatoStage;
	private ObservableList<String> cbFormatoList = FXCollections
				.observableArrayList("Pontos Corridos", "Mata-Mata");

		
	private Campeonato campeonato;
	private CampeonatoDAO dbCampeonato;
	
    @FXML
    private Label labelNome;

    @FXML
    private TextField tfNome;

    @FXML
    private Label labelFormato;

    @FXML
    private ComboBox<String> cbFormato;

    @FXML
    private Button btnProximo;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private Label labelError;
    
    @FXML
    private void initialize() throws SQLException {
    	dbCampeonato = CampeonatoDAO.getInstance();
    	
    	btnProximo.setDisable(true);
    	cbFormato.setValue("Selecione um formato");
    	cbFormato.setItems(cbFormatoList);
    	
    	labelError.setVisible(false);
    }

    @FXML
    void cancelar(ActionEvent event) {
    	novoCampeonatoStage.close();
    }
    
    @FXML 
    void selecionarFormato(ActionEvent event){
    	btnProximo.setDisable(false);
    }

    @FXML
    void proximo(ActionEvent event) throws IOException, SQLException {
    	labelError.setVisible(false);
    	campeonato = CampeonatoController.getInstance(cbFormato.getValue());
    	campeonato.setNome(tfNome.getText());
    	campeonato.setFormato(cbFormato.getValue());
    	
    	try {
    		dbCampeonato.addCampeonato(campeonato);
    	}
    	catch(SQLException e) {
    		labelError.setVisible(true);
    		labelError.setText("Não foi possivel criar esse campeonato!");
    		return;
    	}
    	if(cbFormato.getValue() == "Pontos Corridos") {
    		abrirTelaPC();
    	}
    	else if(cbFormato.getValue() == "Mata-Mata") {
    		abrirTelaMM();
    	}
    }
    
    private void abrirTelaPC() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaNovoCampeonatoPCController.class.getResource("/br/ufrn/imd/visao/TelaNovoCampeonatoPC.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage novoCampeonatoPCStage = new Stage();
    	novoCampeonatoPCStage.setTitle("Novo Campeonato Pontos Corridos");
    	novoCampeonatoPCStage.setResizable(false);
    	Scene scene = new Scene(page);
    	novoCampeonatoPCStage.setScene(scene);
    	
    	TelaNovoCampeonatoPCController controller = loader.getController();
    	controller.setNovoCampeonatoPCStage(novoCampeonatoPCStage);
    	novoCampeonatoPCStage.show();
    	this.novoCampeonatoStage.close();
    }
    
    private void abrirTelaMM() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaNovoCampeonatoMMController.class.getResource("/br/ufrn/imd/visao/TelaNovoCampeonatoMM.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage novoCampeonatoMMStage = new Stage();
    	novoCampeonatoMMStage.setTitle("Novo Campeonato Mata-Mata");
    	novoCampeonatoMMStage.setResizable(false);
    	Scene scene = new Scene(page);
    	novoCampeonatoMMStage.setScene(scene);
    	
    	TelaNovoCampeonatoMMController controller = loader.getController();
    	controller.setNovoCampeonatoMMStage(novoCampeonatoMMStage);
    	novoCampeonatoMMStage.show();
    	this.novoCampeonatoStage.close();
    }
    
    public void setNovoCampeonatoStage(Stage novoCampeonatoStage) {
    	this.novoCampeonatoStage = novoCampeonatoStage;
    }

}

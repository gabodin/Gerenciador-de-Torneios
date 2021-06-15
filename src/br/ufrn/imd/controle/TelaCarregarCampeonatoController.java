package br.ufrn.imd.controle;

import java.io.IOException;
import java.sql.SQLException;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TelaCarregarCampeonatoController {
	
	private Stage carregarCampeonatoStage;
	
	private CampeonatoDAO dbCampeonato;
	private Campeonato campeonato;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCarregar;

    @FXML
    private ComboBox<String> cbCampeonato;
    
    @FXML
    void initialize() throws SQLException {
    	dbCampeonato = CampeonatoDAO.getInstance();
    	btnCarregar.setDisable(true);
    	cbCampeonato.setItems(dbCampeonato.listarCampeonatos());
    }

    @FXML
    void cancelar(ActionEvent event) {
    	this.carregarCampeonatoStage.close();
    }

    @FXML
    void carregar(ActionEvent event) throws SQLException, IOException{
    	
    	int id = dbCampeonato.getIdCampeonato(cbCampeonato.getValue());
    	dbCampeonato.carregarCampeonato(id);
    	
    	campeonato = CampeonatoController.getInstance(dbCampeonato.getFormatoCampeonato(cbCampeonato.getValue()));
    	
    	for(Time t : dbCampeonato.obterTimes(id)) {
    		campeonato.adicionarTime(t);
    	}
    	
    	if(campeonato.getFormato() == "Pontos Corridos") {
    		dbCampeonato.carregarRodadas(campeonato.getId());
        	dbCampeonato.carregarPartidas(campeonato.getId(), campeonato.getFormato());
    		abrirTelaPC();
    	}
    	else if(campeonato.getFormato() == "Mata-Mata") {
    		dbCampeonato.carregarFases(campeonato.getId());
    		dbCampeonato.carregarChaves(campeonato.getId());
    		dbCampeonato.carregarPartidas(campeonato.getId(), campeonato.getFormato());
    		abrirTelaMM();
    	}
    }
    
    private void abrirTelaMM() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaCampeonatoMMController.class.getResource("/br/ufrn/imd/visao/TelaCampeonatoMM.fxml"));
    	TabPane page = (TabPane) loader.load();
    	
    	Stage campeonatoMMStage = new Stage();
    	campeonatoMMStage.setTitle(campeonato.getNome().toUpperCase());
    	campeonatoMMStage.setResizable(false);
    	Scene scene = new Scene(page);
    	campeonatoMMStage.setScene(scene);
    	campeonatoMMStage.setOnCloseRequest(e ->{
			try {
				CampeonatoDAO.getInstance().salvarCampeonato(campeonato.getId(),campeonato.getFormato());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			CampeonatoController.limparCampeonato();
		});
    	
    	TelaCampeonatoMMController controller = loader.getController();
    	controller.setCampeonatoMMStage(campeonatoMMStage);
    	campeonatoMMStage.show();
    	this.carregarCampeonatoStage.close();
    }
    
    private void abrirTelaPC() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaCampeonatoPCController.class.getResource("/br/ufrn/imd/visao/TelaCampeonatoPC.fxml"));
    	HBox page = (HBox) loader.load();
    	
    	Stage campeonatoPCStage = new Stage();
    	campeonatoPCStage.setTitle(campeonato.getNome());
    	campeonatoPCStage.setResizable(false);
    	Scene scene = new Scene(page);
    	campeonatoPCStage.setScene(scene);
    	campeonatoPCStage.setOnCloseRequest(e ->{
			try {
				CampeonatoDAO.getInstance().salvarCampeonato(campeonato.getId(),campeonato.getFormato());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			CampeonatoController.limparCampeonato();
		});
    	
    	TelaCampeonatoPCController controller = loader.getController();
    	controller.setCampeonatoPCStage(campeonatoPCStage);
    	campeonatoPCStage.show();
    	this.carregarCampeonatoStage.close();
    }

    @FXML
    void selecionarCampeonato(ActionEvent event) {
    	btnCarregar.setDisable(false);
    }
    
    public void setCarregarCampeonatoStage(Stage carregarCampeonatoStage) {
    	this.carregarCampeonatoStage = carregarCampeonatoStage;
    }

}

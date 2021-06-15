package br.ufrn.imd.controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipalController {

    @FXML
    private Label labelTitulo;

    @FXML
    private Button btnNovoCampeonato;

    @FXML
    private Button btnNovoTime;

    @FXML
    private Button btnCarregarCampeonato;

    @FXML
    private Button btnConfiguracoes;

    @FXML
    void abrirTelaCarregarCampeonato(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaCarregarCampeonatoController.class.getResource("/br/ufrn/imd/visao/TelaCarregarCampeonato.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage carregarCampeonatoStage = new Stage();
    	carregarCampeonatoStage.setTitle("Carregar Campeonato");
    	carregarCampeonatoStage.setResizable(false);
    	Scene scene = new Scene(page);
    	carregarCampeonatoStage.setScene(scene);
    	
    	TelaCarregarCampeonatoController controller = loader.getController();
    	controller.setCarregarCampeonatoStage(carregarCampeonatoStage);
    	carregarCampeonatoStage.showAndWait();
    }

    @FXML
    void abrirTelaNovoCampeonato(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaNovoCampeonatoController.class.getResource("/br/ufrn/imd/visao/TelaNovoCampeonato.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage novoCampeonatoStage = new Stage();
    	novoCampeonatoStage.setTitle("Novo Campeonato");
    	novoCampeonatoStage.setResizable(false);
    	Scene scene = new Scene(page);
    	novoCampeonatoStage.setScene(scene);
    	
    	TelaNovoCampeonatoController controller = loader.getController();
    	controller.setNovoCampeonatoStage(novoCampeonatoStage);
    	novoCampeonatoStage.showAndWait();
    }

    @FXML
    void abrirTelaNovoTime(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaNovoTimeController.class.getResource("/br/ufrn/imd/visao/TelaNovoTime.fxml"));
    	AnchorPane page = (AnchorPane) loader.load();
    	
    	Stage novoTimeStage = new Stage();
    	novoTimeStage.setTitle("Novo Time");
    	novoTimeStage.setResizable(false);
    	Scene scene = new Scene(page);
    	novoTimeStage.setScene(scene);
    	
    	TelaNovoTimeController controller = loader.getController();
    	controller.setNovoTimeStage(novoTimeStage);
    	novoTimeStage.showAndWait();
    }

    @FXML
    void abrirTelaConfiguracoes(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaConfiguracoesController.class.getResource("/br/ufrn/imd/visao/TelaConfiguracoes.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage configuracoesStage = new Stage();
    	configuracoesStage.setTitle("Configurações");
    	configuracoesStage.setResizable(false);
    	Scene scene = new Scene(page);
    	configuracoesStage.setScene(scene);
    	
    	TelaConfiguracoesController controller = loader.getController();
    	controller.setConfiguracoesStage(configuracoesStage);
    	configuracoesStage.showAndWait();
}

}

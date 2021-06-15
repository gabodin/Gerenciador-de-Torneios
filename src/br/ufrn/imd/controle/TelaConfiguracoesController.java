package br.ufrn.imd.controle;

import java.io.IOException;
import java.sql.SQLException;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.dao.TimeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaConfiguracoesController {
	
	private Stage configuracoesStage;

    @FXML
    private Button btnRemoverTodosTimes;

    @FXML
    private Button btnEditarTime;

    @FXML
    private Button btnRemoverTodosCampeonatos;

    @FXML
    private ComboBox<String> cbCampeonato;

    @FXML
    private Button btnRemoverCampeonato;

    @FXML
    private ComboBox<String> cbTime;

    @FXML
    private Button btnRemoverTime;
    
    private CampeonatoDAO dbCampeonato;
    private TimeDAO dbTime;

    @FXML
    void editarTime(ActionEvent event) throws IOException, SQLException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaEditarTimeController.class.getResource("/br/ufrn/imd/visao/TelaEditarTime.fxml"));
    	BorderPane page = (BorderPane) loader.load();
    	
    	Stage editarTimeStage = new Stage();
    	editarTimeStage.setTitle("Editar Time");
    	editarTimeStage.setResizable(false);
    	Scene scene = new Scene(page);
    	editarTimeStage.setScene(scene);
    	
    	TelaEditarTimeController controller = loader.getController();
    	controller.setEditarTimeStage(editarTimeStage);
    	editarTimeStage.showAndWait();
    	
    	cbTime.setItems(dbTime.listarNomeDosTimes());
    }
    
    @FXML
    void initialize() throws SQLException {
    	dbCampeonato = CampeonatoDAO.getInstance();
    	dbTime = TimeDAO.getInstance();
    	
    	btnRemoverTime.setDisable(true);
    	btnRemoverCampeonato.setDisable(true);
    	
    	cbTime.setItems(dbTime.listarNomeDosTimes());
    	cbCampeonato.setItems(dbCampeonato.listarCampeonatos());
    }

    @FXML
    void removerCampeonato(ActionEvent event) throws SQLException {
    	String nomeCampeonato = cbCampeonato.getValue();
    	int idCampeonato = dbCampeonato.getIdCampeonato(nomeCampeonato);
    	
    	dbCampeonato.removerCampeonato(idCampeonato);
    	
    	cbCampeonato.setItems(dbCampeonato.listarCampeonatos());
    }

    @FXML
    void removerTime(ActionEvent event) throws SQLException {
    	String nomeTime = cbTime.getValue();
    	dbTime.removerTime(nomeTime);
    	cbTime.setItems(dbTime.listarNomeDosTimes());
    }

    @FXML
    void removerTodosCampeonatos(ActionEvent event) throws SQLException {
    	dbCampeonato.removerTodosCampeonato();
    	cbCampeonato.setItems(dbCampeonato.listarCampeonatos());
    }

    @FXML
    void removerTodosTimes(ActionEvent event) throws SQLException {
    	dbTime.removerTodosTimes();
    	cbTime.setItems(dbTime.listarNomeDosTimes());
    }

    @FXML
    void selecionarCampeonato(ActionEvent event) {
    	btnRemoverCampeonato.setDisable(false);
    }

    @FXML
    void selecionarTime(ActionEvent event) {
    	btnRemoverTime.setDisable(false);
    }
    
    public void setConfiguracoesStage(Stage configuracoesStage) {
    	this.configuracoesStage = configuracoesStage;
    }

}

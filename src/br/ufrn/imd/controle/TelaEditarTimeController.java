package br.ufrn.imd.controle;

import java.sql.SQLException;

import br.ufrn.imd.dao.TimeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaEditarTimeController {
	
	private Stage editarTimeStage;

    @FXML
    private Label labelTime;

    @FXML
    private ComboBox<String> cbTime;

    @FXML
    private TextField tfNome;

    @FXML
    private Label labelNome;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;
    
    @FXML
    private Label labelError;
    
    @FXML
    void initialize() throws SQLException {
    	cbTime.setValue("Selecione um time");
    	cbTime.setItems(TimeDAO.getInstance().listarNomeDosTimes());
    }

    @FXML
    void cancelar(ActionEvent event) {
    	this.editarTimeStage.close();
    }

    @FXML
    void salvar(ActionEvent event) throws SQLException {
    	labelError.setVisible(false);
    	boolean erro = false;
    	try {
    		TimeDAO.getInstance().editarTime(cbTime.getValue(), tfNome.getText());
    	}
    	catch(SQLException e) {
    		erro = true;
    		labelError.setVisible(true);
    		labelError.setText("Não foi possivel alterar o nome do time!");
    	}
    	if(!erro) {
    		this.editarTimeStage.close();
    	}
    }
    
    @FXML
    void selecionar(ActionEvent event) {
    	if(cbTime.getValue() != null) {
    		labelNome.setDisable(false);
        	tfNome.setDisable(false);
    	}
    }
    
    public void setEditarTimeStage(Stage editarTimeStage) {
    	this.editarTimeStage = editarTimeStage;
    }

}

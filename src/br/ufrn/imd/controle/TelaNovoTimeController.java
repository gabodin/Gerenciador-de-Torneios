package br.ufrn.imd.controle;

import java.sql.SQLException;

import br.ufrn.imd.dao.TimeDAO;
import br.ufrn.imd.modelo.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaNovoTimeController {
	
	private Stage novoTimeStage;

    @FXML
    private Label labelNome;

    @FXML
    private TextField tfNome;
    
    @FXML
    private Label labelError;

    @FXML
    private Button btnAdicionarOutroTime;

    @FXML
    private Button btnSalvarSair;
    
    @FXML
    void adicionarOutroTime(ActionEvent event) throws SQLException {
    	labelError.setVisible(false);
    	Time t = new Time();
    	t.setNome(tfNome.getText());
    	try {
    		TimeDAO.getInstance().addTime(t);
    	}
    	catch(SQLException e) {
    		labelError.setVisible(true);
    		labelError.setText("Não foi possivel adicionar esse time!");
    	}
    	tfNome.clear();
    }

    @FXML
    void salvarSair(ActionEvent event) throws SQLException {
    	boolean erro = false;
    	Time t = new Time();
    	t.setNome(tfNome.getText());
    	try {
    		TimeDAO.getInstance().addTime(t);
    	}
    	catch(SQLException e) {
    		erro = true;
    		labelError.setVisible(true);
    		labelError.setText("Não foi possivel adicionar esse time!");
    	}
    	tfNome.clear();
    	if(!erro) {
    		this.novoTimeStage.close();
    	}
    }
    
    public void setNovoTimeStage(Stage novoTimeStage) {
    	this.novoTimeStage = novoTimeStage;
    }

}

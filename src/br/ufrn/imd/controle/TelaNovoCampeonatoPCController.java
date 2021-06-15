package br.ufrn.imd.controle;

import java.io.IOException;
import java.sql.SQLException;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.dao.TimeDAO;
import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TelaNovoCampeonatoPCController {
	
	private int numeroTimes;
	private int timesAdicionados=0;
	private CampeonatoDAO dbCampeonato;
	private Campeonato campeonato = CampeonatoController.getInstance("Pontos Corridos");
	private TimeDAO dbTime;
	
	private Stage novoCampeonatoPCStage;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCriar;

    @FXML
    private Label labelNumeroTimes;

    @FXML
    private TextField tfNumeroTimes;

    @FXML
    private ComboBox<String> cbTime;

    @FXML
    private Label labelTime;

    @FXML
    private Button btnAdicionarTime;
    
    @FXML
    private Button btnCriatTime;
    
    @FXML
    private Label labelError;
    
    @FXML
    private Button btnValidar;
    
    @FXML
    private Label labelTimesAdicionados;
    
    @FXML CheckBox checkIdaVolta;
    
    
    @FXML
    void initialize() throws SQLException {
    	dbCampeonato = CampeonatoDAO.getInstance();
    	dbTime = TimeDAO.getInstance();
    	cbTime.setItems(dbTime.listarNomeDosTimes());
    }

    @FXML
    void adicionarTime(ActionEvent event) throws SQLException {
    	labelError.setVisible(false);
    	
    	Time novoTime = new Time();
    	novoTime.setNome(cbTime.getValue());
    	
    	if(campeonato.adicionarTime(novoTime) == false) {
    		labelError.setVisible(true);
    		labelError.setText("Time ja foi adicionado!");
    	}
    	else {
    		campeonato.setId(dbCampeonato.getIdCampeonato(campeonato.getNome()));
    		dbCampeonato.adicionarTime(novoTime, campeonato.getId());
    		timesAdicionados++;
    		labelTimesAdicionados.setText(String.valueOf(timesAdicionados));
    		if(timesAdicionados == numeroTimes) {
    			btnAdicionarTime.setDisable(true);
    			btnCriar.setDisable(false);
    		}
    	}
    }

    @FXML
    void cancelar(ActionEvent event) throws SQLException {
    	String nomeCampeonato = campeonato.getNome();
    	int id = dbCampeonato.getIdCampeonato(nomeCampeonato);
    	dbCampeonato.removerCampeonato(id);
    	this.novoCampeonatoPCStage.close();
    }

    @FXML
    void criar(ActionEvent event) throws IOException, SQLException, InterruptedException {
    	
    	campeonato.setIdaEVolta(checkIdaVolta.isSelected());
    	
    	dbCampeonato.addRodadas(campeonato.getId());
    	
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
    	
    	this.novoCampeonatoPCStage.close();
    }

    @FXML
    void selecionarNumeroTimes(ActionEvent event) {
    	labelError.setVisible(false);
    	numeroTimes = Integer.parseInt(tfNumeroTimes.getText());
    	if(numeroTimes > 0) {
    		cbTime.setDisable(false);
    	}
    	else {
    		cbTime.setDisable(true);
    		labelError.setVisible(true);
    		labelError.setText("Número de times invalido!");
    		tfNumeroTimes.clear();
    		
    	}
    }

    @FXML
    void selecionarTime(ActionEvent event) {
    	if(cbTime.getValue() != null) {
    		btnAdicionarTime.setDisable(false);
    	}
    }
    
    public void setNovoCampeonatoPCStage(Stage novoCampeonatoPCStage) {
    	this.novoCampeonatoPCStage = novoCampeonatoPCStage;
    }
    
    @FXML
    void criarTime(ActionEvent event) throws IOException, SQLException {
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
    	
    	cbTime.setItems(TimeDAO.getInstance().listarNomeDosTimes());
    }
    
    

}

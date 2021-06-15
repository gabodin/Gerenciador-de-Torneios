package br.ufrn.imd.controle;

import java.io.IOException;
import java.sql.SQLException;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.dao.TimeDAO;
import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaNovoCampeonatoMMController {
	
	private Stage novoCampeonatoMMStage;
	private Campeonato campeonato = CampeonatoController.getInstance("Mata-Mata");
	private CampeonatoDAO dbCampeonato;
	
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCriar;

    @FXML
    private ComboBox<Integer> cbNumeroTimes;
    
    private ObservableList<Integer> qtdTimes = FXCollections.observableArrayList(2,4,8,16,32);

    @FXML
    private CheckBox checkIdaEVolta;

    @FXML
    private CheckBox checkGolsFora;

    @FXML
    private Button btnAdicionarTime;

    @FXML
    private Button btnCriarTime;
    
    @FXML
    private ComboBox<String> cbTime;
    
    @FXML
    private Label labelError;
    
    @FXML
    private Label labelTimesAdicionados;
    
    private int numeroTimes;
    private int timesAdicionados=0;
    
    @FXML
    void initialize() throws SQLException {
    	dbCampeonato = CampeonatoDAO.getInstance();
    	
    	btnCriar.setDisable(true);
    	btnAdicionarTime.setDisable(true);
    	
    	cbNumeroTimes.setItems(qtdTimes);
    	
    	labelError.setVisible(false);
    	
    	cbTime.setItems(TimeDAO.getInstance().listarNomeDosTimes());
    	cbTime.setDisable(true);
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
    	this.novoCampeonatoMMStage.close();
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

    @FXML
    void criar(ActionEvent event) throws SQLException, IOException, InterruptedException {
    	campeonato.setIdaEVolta(checkIdaEVolta.isSelected());
    	campeonato.setGolFora(checkGolsFora.isSelected());
    	
    	dbCampeonato.addFases(campeonato.getId());
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaCampeonatoMMController.class.getResource("/br/ufrn/imd/visao/TelaCampeonatoMM.fxml"));
    	TabPane page = (TabPane) loader.load();
    	
    	Stage campeonatoMMStage = new Stage();
    	campeonatoMMStage.setTitle(campeonato.getNome());
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
    	
    	this.novoCampeonatoMMStage.close();
    }

    @FXML
    void selecionarNumeroTimes(ActionEvent event) {
    	numeroTimes = cbNumeroTimes.getValue();
    	
    	cbTime.setDisable(false);
    }
    
    @FXML
    void selecionarTime(ActionEvent event) {
    	btnAdicionarTime.setDisable(false);
    }
    
    public void setNovoCampeonatoMMStage(Stage novoCampeonatoMMStage) {
    	this.novoCampeonatoMMStage = novoCampeonatoMMStage;
    }
    
    

}

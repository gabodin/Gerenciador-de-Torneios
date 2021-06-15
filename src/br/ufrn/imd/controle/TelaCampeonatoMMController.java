package br.ufrn.imd.controle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.ufrn.imd.dao.CampeonatoDAO;
import br.ufrn.imd.modelo.Campeonato;
import br.ufrn.imd.modelo.Chave;
import br.ufrn.imd.modelo.Fase;
import br.ufrn.imd.modelo.MataMata;
import br.ufrn.imd.modelo.Partida;
import br.ufrn.imd.modelo.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TelaCampeonatoMMController implements Initializable {

    @FXML
    private Label labelFase;
    
    @FXML
    private Label labelCampeao;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnProx;

    @FXML
    private GridPane gpPartidas;
    
    @FXML
    private ColumnConstraints gpColCasa;

    @FXML
    private ColumnConstraints gpColTfCasa;

    @FXML
    private ColumnConstraints gpColVs;

    @FXML
    private ColumnConstraints gpColTfVisitante;

    @FXML
    private ColumnConstraints gpColVisitante;

    @FXML
    private ColumnConstraints gpColBtn;

    @FXML
    private TableView<Time> tableMD;

    @FXML
    private TableColumn<Time, String> colNomeMD;

    @FXML
    private TableColumn<Time, Integer> colGolsSofridosMD;

    @FXML
    private TableView<Time> tableMA;

    @FXML
    private TableColumn<Time, String> colNomeMA;

    @FXML
    private TableColumn<Time, Integer> colGolsMarcadosMA;

    @FXML
    private TableView<Time> tablePD;

    @FXML
    private TableColumn<Time, String> colNomePD;

    @FXML
    private TableColumn<Time, Integer> colGolsSofridosPD;

    @FXML
    private TableView<Time> tablePA;

    @FXML
    private TableColumn<Time, String> colNomePA;

	@FXML
    private TableColumn<Time, Integer> colGolsMarcadosPA;
    
    private Campeonato campeonato = CampeonatoController.getInstance("Mata-Mata");
    
    
	private Stage campeonatoMMStage;
    private int faseAtual=0;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	int numFases = ((MataMata) campeonato).getFases().size();
		if(faseAtual+1 == numFases) {
			btnProx.setDisable(true);
		}
    	
    	
    	alterarLabelFase(((MataMata)campeonato).getFase(faseAtual));
    	inicializarFase(faseAtual);

		// INITIALIZE - TABELA MELHOR DEFESA
		
		colNomeMD.setCellValueFactory(
			new PropertyValueFactory<>("nome"));
		colGolsSofridosMD.setCellValueFactory(
			new PropertyValueFactory<>("golsSofridos"));
		
		// INITIALIZE - TABELA MELHOR ATAQUE
		
		colNomeMA.setCellValueFactory(
			new PropertyValueFactory<>("nome"));
		colGolsMarcadosMA.setCellValueFactory(
			new PropertyValueFactory<>("golsMarcados"));
		
		// INITIALIZE - TABELA PIOR DEFESA
		
		colNomePD.setCellValueFactory(
			new PropertyValueFactory<>("nome"));
		colGolsSofridosPD.setCellValueFactory(
			new PropertyValueFactory<>("golsSofridos"));
		
		// INITIALIZE - TABELA PIOR ATAQUE
		colNomePA.setCellValueFactory(
			new PropertyValueFactory<>("nome"));
		colGolsMarcadosPA.setCellValueFactory(
			new PropertyValueFactory<>("golsMarcados"));
			
		buildDataMD();
		buildDataPD();
		buildDataMA();
		buildDataPA();
		
		
		gpColCasa = gpPartidas.getColumnConstraints().get(0);
		gpColTfCasa = gpPartidas.getColumnConstraints().get(1);
		gpColVs = gpPartidas.getColumnConstraints().get(2);
		gpColTfVisitante = gpPartidas.getColumnConstraints().get(3);
		gpColVisitante = gpPartidas.getColumnConstraints().get(4);
		gpColBtn = gpPartidas.getColumnConstraints().get(5);
		
		gpColCasa.setHalignment(HPos.CENTER);
		gpColTfCasa.setHalignment(HPos.CENTER);
		gpColVs.setHalignment(HPos.CENTER);
		gpColTfVisitante.setHalignment(HPos.CENTER);
		gpColVisitante.setHalignment(HPos.CENTER);
		gpColBtn.setHalignment(HPos.CENTER);
		
		
    }	

	private void buildDataMD() {
		ObservableList<Time> data1 = FXCollections.observableArrayList();
		
		tableMD.refresh();
		
		data1.addAll(campeonato.listarMelhorDefesa());
		
		tableMD.setItems(data1);
				
	}
	
	private void buildDataPD() {
		ObservableList<Time> data2 = FXCollections.observableArrayList();
		
		tablePD.refresh();
		
		data2.addAll(campeonato.listarPiorDefesa());
		tablePD.setItems(data2);
				
	}
	
	private void buildDataMA() {
		ObservableList<Time> data3 = FXCollections.observableArrayList();
		
		tableMA.refresh();
		
		data3.addAll(campeonato.listarMelhorAtaque());
		tableMA.setItems(data3);
				
	}
	
	private void buildDataPA() {
		ObservableList<Time> data4 = FXCollections.observableArrayList();
		
		tablePA.refresh();
		
		data4.addAll(campeonato.listarPiorAtaque());
		tablePA.setItems(data4);
				
	}
    
    private void inicializarFase(int n) {
    	gpPartidas.getChildren().clear();
    	
    	int idx=0;
    	Fase fase = ((MataMata)campeonato).getFase(n);
    	for(int i=0; i < fase.getNumeroChaves() ; i++) {
    		Chave chave = fase.getChave(i);
    		
    		for(int j=0; j < chave.getPartidas().size(); j++) {
    			Partida p = chave.getPartidas().get(j);
    			
    			Label labelCasa = new Label();
    			labelCasa.setFont(labelFase.getFont());
    			if(p.getCasa() != null) labelCasa.setText(p.getCasa().getNome());
    			else labelCasa.setText("A DEFINIR");
    			
    			TextField tfCasa = new TextField();
    			if(!p.isNull()) {
    				tfCasa.setText(String.valueOf(p.getGolsCasa()));
    			}
    			
    			Label labelVs = new Label();
    			labelVs.setFont(labelFase.getFont());
    			labelVs.setText("x");
    			
    			TextField tfVisitante = new TextField();
    			if(!p.isNull()) {
    				tfVisitante.setText(String.valueOf(p.getGolsFora()));
    			}
    			
    			Label labelVisitante = new Label();
    			labelVisitante.setFont(labelFase.getFont());
    			if(p.getVisitante() != null) labelVisitante.setText(p.getVisitante().getNome());
    			else labelVisitante.setText("A DEFINIR");
    			
    			Button btnPartida = new Button();
    			btnPartida.setText(">");
    			btnPartida.setStyle("-fx-background-radius: 30;");
    			
    			btnPartida.setOnAction(e->{
    				int golsCasa = Integer.parseInt(tfCasa.getText());
    				int golsVisitante = Integer.parseInt(tfVisitante.getText());
    				if(golsCasa < 0 || golsVisitante < 0) {
    					return;
    				}
    				
    				p.setResultado(golsCasa, golsVisitante);
    				p.atualizarTimes();
    				
    				buildDataMD();
					buildDataPD();
					buildDataMA();
					buildDataPA();
					
					Time time = chave.classificou();
					
					
						
					labelCampeao.setVisible(false);
					if(time != null) {
						MataMata mataMata = (MataMata) campeonato;
						mataMata.proximaFase(time, chave.getId(), fase.getId());
						if(mataMata.getFases().size() == faseAtual+1) {
							labelCampeao.setVisible(true);
							labelCampeao.setText("Vencedor: " + time.getNome());
						}
					}
    			});
    			gpPartidas.addRow(idx, labelCasa, tfCasa, labelVs, tfVisitante, labelVisitante, btnPartida);
    			idx++;
    		}
    	}
    }
    

    @FXML
    void faseAnterior(ActionEvent event) {
    	MataMata camp = ((MataMata)campeonato);
    	btnProx.setDisable(false);
		faseAtual--;
		if(faseAtual == 0) {
			btnPrev.setDisable(true);
		}
		alterarLabelFase(camp.getFase(faseAtual));
		inicializarFase(faseAtual);
    }
    
    private void alterarLabelFase(Fase f) {
    	int chavesFase1 = 16;
    	int chavesOitavas = 8;
    	int chavesQuartas = 4;
    	int chavesSemi = 2;    	
    	
    	if(f.getNumeroChaves() == chavesFase1) {
    		labelFase.setText("FASE 1");
    	}
    	else if(f.getNumeroChaves() == chavesOitavas) {
    		labelFase.setText("OITAVAS DE FINAL");
    	}
    	else if(f.getNumeroChaves() == chavesQuartas) {
    		labelFase.setText("QUARTAS DE FINAL");
    	}
    	else if(f.getNumeroChaves() == chavesSemi) {
    		labelFase.setText("SEMI-FINAL");
    	}
    	else {
    		labelFase.setText("FINAL");
    	}
    	labelFase.setAlignment(Pos.CENTER);
    }

    @FXML
    void proximaFase(ActionEvent event) {
    	MataMata camp = ((MataMata)campeonato);
    	
    	btnPrev.setDisable(false);
		faseAtual++;
		int numFases = ((MataMata) campeonato).getFases().size();
		if(faseAtual+1 == numFases) {
			btnProx.setDisable(true);
		}
		alterarLabelFase(camp.getFase(faseAtual));
		inicializarFase(faseAtual);
    }
    
    public void setCampeonatoMMStage(Stage campeonatoMMStage) {
    	this.campeonatoMMStage = campeonatoMMStage;
    }

}

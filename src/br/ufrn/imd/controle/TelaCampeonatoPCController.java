package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Partida;
import br.ufrn.imd.modelo.PontosCorridos;
import br.ufrn.imd.modelo.Rodada;
import br.ufrn.imd.modelo.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TelaCampeonatoPCController implements Initializable {

	@SuppressWarnings("unused")
	private Stage campeonatoPCStage;
	private PontosCorridos campeonato = (PontosCorridos)CampeonatoController.getInstance("Pontos Corridos");
	
	// TABELA - CLASSIFICAÇÃO
	
    @FXML
    private TableView<Time> tableClassificacao;

    @FXML
    private TableColumn<Time, Integer> colPos;

    @FXML
    private TableColumn<Time, String> colNome;

    @FXML
    private TableColumn<Time, Integer> colPontos;

    @FXML
    private TableColumn<Time, Integer> colVitorias;

    @FXML
    private TableColumn<Time, Integer> colEmpates;

    @FXML
    private TableColumn<Time, Integer> colDerrotas;

    @FXML
    private TableColumn<Time, Integer> colGolsPro;

    @FXML
    private TableColumn<Time, Integer> colGolsContra;

    @FXML
    private TableColumn<Time, Integer> colSaldo;

    @FXML
    private TableColumn<Time, Double> colAproveitamento;

    @FXML
    void ordenaTabela(ActionEvent event) {
		
    }
    
	// TABELA - MELHOR DEFESA

    @FXML
    private TableView<Time> tableMelhorDefesa;

    @FXML
    private TableColumn<Time, String> colNomeMD;

    @FXML
    private TableColumn<Time, Integer> colGolsSofridosMD;
	
	// TABELA - MELHOR ATAQUE
	
    @FXML
    private TableView<Time> tableMelhorAtaque;

    @FXML
    private TableColumn<Time, String> colNomeMA;

    @FXML
    private TableColumn<Time, Integer> colGolsMarcadosMA;
	
	// TABELA - PIOR DEFESA
	
    @FXML
    private TableView<Time> tablePiorDefesa;

    @FXML
    private TableColumn<Time, String> colNomePD;

    @FXML
    private TableColumn<Time, Integer> colGolsSofridosPD;
	
	// TABELA - PIOR ATAQUE
	
    @FXML
    private TableView<Time> tablePiorAtaque;

    @FXML
    private TableColumn<Time, String> colNomePA;

    @FXML
    private TableColumn<Time, Integer> colGolsMarcadosPA;
    
    @FXML
    private TableColumn<Time, Integer> colJogos;

	// TABELA - RODADAS
    
    @FXML
    private Label labelRodada;

    @FXML
    private Label labelNumRodada;
    
    @FXML
    Label labelCampeao;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnProx;

    @FXML
    private GridPane gpPartidas;
    

	// MÉTODOS 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(campeonato.getNumRodadas() == 1) {
			btnProx.setDisable(true);
		}
		inicializarRodada(0);
		
		colPos.setCellValueFactory(
			new PropertyValueFactory<>("posicao"));
		colNome.setCellValueFactory(
			new PropertyValueFactory<>("nome"));
		colPontos.setCellValueFactory(
			new PropertyValueFactory<>("pontos"));
		colVitorias.setCellValueFactory(
			new PropertyValueFactory<>("vitorias"));
		colEmpates.setCellValueFactory(
			new PropertyValueFactory<>("empates"));
		colDerrotas.setCellValueFactory(
			new PropertyValueFactory<>("derrotas"));
		colGolsPro.setCellValueFactory(
			new PropertyValueFactory<>("golsMarcados"));
		colGolsContra.setCellValueFactory(
			new PropertyValueFactory<>("golsSofridos"));
		colSaldo.setCellValueFactory(
			new PropertyValueFactory<>("saldo"));
		colAproveitamento.setCellValueFactory(
			new PropertyValueFactory<>("aproveitamento"));
		colJogos.setCellValueFactory(
			new PropertyValueFactory<>("jogos"));
			
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
			
			buildDataCL();
			buildDataMD();
			buildDataPD();
			buildDataMA();
			buildDataPA();
			
			
	}
	
	private void inicializarRodada(int n) {
		gpPartidas.getChildren().clear();
		Rodada rodada = ((PontosCorridos) campeonato).getRodada(n);
		int idx = 0;
		while(idx < rodada.getNumeroPartidas()) {
			Partida p = rodada.getPartida(idx);
			Label labelCasa = new Label();
			labelCasa.setText(p.getCasa().getNome());
			
			TextField tfCasa = new TextField();
			if(!p.isNull()) {
				tfCasa.setText(String.valueOf(p.getGolsCasa()));
			}
			
			Label labelVs = new Label();
			labelVs.setText("   x");
			
			TextField tfVisitante = new TextField();
			if(!p.isNull()) {
				tfVisitante.setText(String.valueOf(p.getGolsFora()));
			}
			
			Label labelVisitante = new Label();
			labelVisitante.setText("     " + p.getVisitante().getNome());
			
			Button btnPartida = new Button();
			btnPartida.setText(">");
			btnPartida.setStyle("-fx-background-radius: 30;");
			
			btnPartida.setOnAction(e ->{
				int golsCasa = Integer.parseInt(tfCasa.getText());
				int golsVisitante = Integer.parseInt(tfVisitante.getText());
				if(golsCasa < 0 || golsVisitante < 0) {
					return;
				}
				
				p.setResultado(golsCasa, golsVisitante);
				p.atualizarTimes();
				
				
				
				buildDataCL();
				buildDataMD();
				buildDataPD();
				buildDataMA();
				buildDataPA();
				
				if(campeonato.acabou()) {
					labelCampeao.setVisible(true);
					labelCampeao.setText("Vencedor: " + campeonato.vencedor().getNome());
				}
				
				
			});
			
			gpPartidas.addRow(idx, labelCasa, tfCasa, labelVs, tfVisitante, labelVisitante, btnPartida);
			idx++;
		}
	}
	
	private void buildDataCL() {
		ObservableList<Time> data1 = FXCollections.observableArrayList();
		
		tableClassificacao.refresh();
		
		data1.addAll(campeonato.gerarClassificacao());
		tableClassificacao.setItems(data1);
				
	}
	
	private void buildDataMD() {
		ObservableList<Time> data2 = FXCollections.observableArrayList();
		
		tableMelhorDefesa.refresh();
		
		data2.addAll(campeonato.listarMelhorDefesa());
		
		tableMelhorDefesa.setItems(data2);
				
	}
	
	private void buildDataPD() {
		ObservableList<Time> data3 = FXCollections.observableArrayList();
		
		tablePiorDefesa.refresh();
		
		data3.addAll(campeonato.listarPiorDefesa());
		tablePiorDefesa.setItems(data3);
				
	}
	
	private void buildDataMA() {
		ObservableList<Time> data4 = FXCollections.observableArrayList();
		
		tableMelhorAtaque.refresh();
		
		data4.addAll(campeonato.listarMelhorAtaque());
		tableMelhorAtaque.setItems(data4);
				
	}
	
	private void buildDataPA() {
		ObservableList<Time> data5 = FXCollections.observableArrayList();
		
		tablePiorAtaque.refresh();
		
		data5.addAll(campeonato.listarPiorAtaque());
		tablePiorAtaque.setItems(data5);
				
	}
		
	
	
	@FXML
    void proximaRodada(ActionEvent event) {
		btnPrev.setDisable(false);
		int n = Integer.parseInt(labelNumRodada.getText());
		n++;
		int numRodadas = ((PontosCorridos) campeonato).getNumRodadas();
		if(n == numRodadas) {
			btnProx.setDisable(true);
		}
		labelNumRodada.setText(String.valueOf(n));
		inicializarRodada(n-1);
    }

    @FXML
    void rodadaAnterior(ActionEvent event) {
    	btnProx.setDisable(false);
		int n = Integer.parseInt(labelNumRodada.getText());
		n--;
		if(n == 1) {
			btnPrev.setDisable(true);
		}
		labelNumRodada.setText(String.valueOf(n));
		inicializarRodada(n-1);
    }
	
	public void setCampeonatoPCStage(Stage campeonatoPCStage) {
		this.campeonatoPCStage = campeonatoPCStage;
	}

}

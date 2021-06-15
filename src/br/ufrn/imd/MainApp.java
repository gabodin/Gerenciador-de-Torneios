package br.ufrn.imd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("visao/TelaPrincipal.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Gerenciador de Torneios");
		stage.getIcons().add(new Image("file:icon.png"));
			stage.setResizable(false);
				stage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}

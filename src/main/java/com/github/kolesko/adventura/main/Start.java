package com.github.kolesko.adventura.main;

import com.github.kolesko.adventura.model.Game;
import com.github.kolesko.adventura.model.IGame;
import com.github.kolesko.adventura.textui.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Třída slouží ke spuštění adventury.
 * Při spuštění bez parametru konstruuje okno aplikace,
 * s parametrem -text se spouští v textovém režimu
 * 
 * @author Filip Vencovsky
 *
 */
public class Start extends Application {

	/**
	 * Spouštěcí metoda pro aplikaci
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IGame hra = new Game();
                TextUI ui = new TextUI(hra);
                ui.play();
            } else {
                System.out.println("Neplatný parameter");
            }
        }
	}
	/**
	 * Metoda, ve které se konstruuje okno, kontroler a hra,
	 * která se předává kontroleru
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/com/github/kolesko/adventura/textui/Home.fxml"));
		Parent root = loader.load();

		HomeController controller = loader.getController();
		controller.inicializuj(new  Game());
		
        primaryStage.setTitle("Hra so škriatkom");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
	}

}
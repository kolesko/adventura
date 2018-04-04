package com.github.kolesko.adventura.textui;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import com.github.kolesko.adventura.model.IGame;
import com.github.kolesko.adventura.model.Game;
import com.github.kolesko.adventura.model.Location;
import com.github.kolesko.adventura.model.ItemCount;
import com.github.kolesko.adventura.model.Figure;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

/**
 * Kontroler, který zprostředkovává komunikaci mezi grafikou
 * a logikou adventury
 * 
 * @author Filip Vencovsky
 *
 */
public class HomeController extends GridPane implements Observer {
	
	@FXML private ComboBox<String> vstupniText;
	@FXML private TextArea vystup;
	@FXML private ListView<ItemCount> seznamVeciMistnost;
	@FXML private ListView<Location> seznamVychodu;
	@FXML private ImageView uzivatel;
	@FXML private ListView<String> zadaneUlohy;
	@FXML private ComboBox<String> vstupnyPrikaz;
	@FXML private Button odosli;
	
	private IGame hra;
	
	/**
	 * metoda čte příkaz ze vstupního textového pole
	 * a zpracuje ho
	 */
	@FXML public void novaHra() {
		seznamVeciMistnost.getItems().clear();
		seznamVychodu.getItems().clear();
		zadaneUlohy.getItems().clear();
		inicializuj(new Game());
		vstupniText.setDisable(false);
		vstupnyPrikaz.setDisable(false);
		odosli.setDisable(false);
	}
	@FXML public void konecHry() {
		hra.processCommand("konec");
		if(hra.isGameOver()) {
			vystup.appendText("\n----------\nKonec hry\n----------\n");
			vstupniText.setDisable(true);
			vstupnyPrikaz.setDisable(true);
			odosli.setDisable(true);
		}
	}
	
	@FXML public void odesliPrikaz() {
		if (vstupnyPrikaz.getValue() != null && vstupnyPrikaz.getValue() != vstupnyPrikaz.getPromptText()) {
			String vstupniTextS = vstupniText.getValue();
			String vystupPrikazu = "";
			if (vstupniTextS != null && vstupniTextS != vstupniText.getPromptText()) {
				vystupPrikazu = hra.processCommand(vstupnyPrikaz.getValue() + " " + vstupniTextS);
				vystup.appendText("\n----------\n"+vstupnyPrikaz.getValue() + " " + vstupniText.getValue()+"\n----------\n");
				vstupniText.setValue(vstupniText.getPromptText());
				vstupnyPrikaz.setValue(vstupnyPrikaz.getPromptText());
			} else {
				vystupPrikazu = hra.processCommand(vstupnyPrikaz.getValue());
				vystup.appendText("\n----------\n"+vstupnyPrikaz.getValue() +"\n----------\n");
				vstupniText.setValue(vstupniText.getPromptText());
				if (vstupniText.isVisible() == false) vstupnyPrikaz.setValue(vstupnyPrikaz.getPromptText());
			}
			vystup.appendText(vystupPrikazu);
		}
		if(hra.isGameOver()) {
			/*vystup.appendText("\n----------\nKonec hry\n----------\n");*/
			vstupniText.setDisable(true);
			vstupnyPrikaz.setDisable(true);
			odosli.setDisable(true);
		}
	}
	
	/**
	 * Metoda bude soužit pro předání objektu se spuštěnou hrou
	 * kontroleru a zobrazí stav hry v grafice.
	 * @param objekt spuštěné hry
	 */
	public void inicializuj(Game hra) {
		vystup.setText(hra.getProlog());
		vystup.setEditable(false);
		this.hra = hra;
		seznamVeciMistnost.getItems().addAll(hra.getGamePlan().getCurrentLocation().getVeci());
		seznamVychodu.getItems().addAll(hra.getGamePlan().getCurrentLocation().getExitLocations());
		vstupnyPrikaz.getItems().addAll(hra.getListOfCommands().getCommands());
		/*if (hra.getGamePlan().getCurrentLocation().getFigure2() != null && hra.getGamePlan().getCurrentLocation().getFigure2().getCurrentTask() != "") {
			tasks.add(hra.getGamePlan().getCurrentLocation().getFigure2().getCurrentTask());
			obsahBatohu.getItems().addAll(tasks);
		}*/
		uzivatel.setX(hra.getGamePlan().getCurrentLocation().getX());
		uzivatel.setY(hra.getGamePlan().getCurrentLocation().getY());
		/*hra.getGamePlan().addObserver(this);
		hra.getGamePlan().getInventory().addObserver(this);
		hra.getGamePlan().getCurrentLocation().addObserver(this);
		hra.getGamePlan().getCurrentLocation().getFigure2().addObserver(this);*/
		hra.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		seznamVeciMistnost.getItems().clear();
		seznamVychodu.getItems().clear();
		//obsahBatohu.getItems().clear();
		seznamVeciMistnost.getItems().addAll(hra.getGamePlan().getCurrentLocation().getVeci());
		seznamVychodu.getItems().addAll(hra.getGamePlan().getCurrentLocation().getExitLocations());
		Figure figure = hra.getGamePlan().getCurrentLocation().getFigure2();
		if (figure != null && figure.getCurrentTask() != "") {
			if (figure.getCompletedTasks() != 0)
				if (figure.getName() == "carodejnicka" && zadaneUlohy.getItems().size() > 1) {
					zadaneUlohy.getItems().remove(1);
				} else {
					if (figure.getName() != "carodejnicka")
						zadaneUlohy.getItems().remove(0);
				}
			if (zadaneUlohy.getItems().size() == 1 && figure.getName().equals("carodejnicka") && figure.getCompletedTasks() == 0)
				zadaneUlohy.getItems().add(figure.getName() + ": " + figure.getCurrentTask());
			if (zadaneUlohy.getItems().size() < 1) {
				zadaneUlohy.getItems().add(figure.getName() + ": " + figure.getCurrentTask());
			}
		}
		uzivatel.setX(hra.getGamePlan().getCurrentLocation().getX());
		uzivatel.setY(hra.getGamePlan().getCurrentLocation().getY());
		hra.getGamePlan().getCurrentLocation().addObserver(this);
		if (hra.getGamePlan().getCurrentLocation().getFigure2() != null) {
			hra.getGamePlan().getCurrentLocation().getFigure2().addObserver(this);
		}
	}
	@FXML public void comboChanged() {
		String value = vstupnyPrikaz.getValue();
		ArrayList<ItemCount> loc = new ArrayList<ItemCount>(hra.getGamePlan().getCurrentLocation().getVeci());
		ArrayList<ItemCount> inv = new ArrayList<ItemCount>(hra.getGamePlan().getInventory().getVeci());
		ArrayList<Location> exit = new ArrayList<Location>(hra.getGamePlan().getCurrentLocation().getExitLocations());
		vstupniText.setVisible(true);
		vstupniText.getItems().clear();
		vstupniText.show();
		vstupniText.hide();
		switch(value) {
			case "vrat" : {
				for(int i = 0; i < inv.size(); i++)
					vstupniText.getItems().add(inv.get(i).toString());
				break;
				}
			case "vezmi" : {
				for(int i = 0; i < loc.size(); i++)
					vstupniText.getItems().add(loc.get(i).toString());
				break;
			}
			case "pouzi" : {				
				for(int i = 0; i < inv.size(); i++)
					vstupniText.getItems().add(inv.get(i).toString());
				break;
			}
			case "preskumaj" : {
				for(int i = 0; i < loc.size(); i++)
					vstupniText.getItems().add(loc.get(i).toString());
				break;
			}
			case "mluv" : {
				Figure figure = hra.getGamePlan().getCurrentLocation().getFigure2();
				if (figure != null) {
					vstupniText.getItems().add(figure.getName());
				}				
				break;
			}
			case "konec" : {
				vstupniText.setVisible(false);
				break;
			}
			case "napoveda" : {
				vstupniText.setVisible(false);
				break;
			}
			case "jdi" : {
				for(int i = 0; i < exit.size(); i++)
					vstupniText.getItems().add(exit.get(i).toString());
				break;
			}
			case "inventar" : {
				vstupniText.setVisible(false);
				break;
			}
		}
	}
}


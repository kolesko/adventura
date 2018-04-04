package com.github.kolesko.adventura.textui;

import java.util.Observable;
import java.util.Observer;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

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
	@FXML private ImageView plan;
	@FXML private ImageView invimg;
	@FXML private ImageView batohimg;
	@FXML private ImageView jablkoimg;
	@FXML private ImageView kuzelneimg;
	@FXML private ImageView loptaimg;
	@FXML private ImageView prstenimg;
	@FXML private ImageView klucimg;
	@FXML private ImageView bylinkyimg;
	
	private IGame hra;
	private int tro = 0;
	private int tru = 0;
	private Tooltip invTooltip = new Tooltip();
		
	
	/**
	 * metoda čte příkaz ze vstupního textového pole
	 * a zpracuje ho
	 */
	@FXML public void novaHra() {
		seznamVeciMistnost.getItems().clear();
		seznamVychodu.getItems().clear();
		zadaneUlohy.getItems().clear();
		vstupnyPrikaz.getItems().clear();
		inicializuj(new Game());
		vstupniText.setDisable(false);
		vstupnyPrikaz.setDisable(false);
		odosli.setDisable(false);
		plan.setImage(new Image(getClass().getResource("plan1.png").toExternalForm()));
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
	
	@FXML public void odesliPrikaz() throws MalformedURLException {
		if (vstupnyPrikaz.getValue() != null && vstupnyPrikaz.getValue() != vstupnyPrikaz.getPromptText()) {
			String vstupniTextS = vstupniText.getValue();
			String vystupPrikazu = "";
			if (vstupnyPrikaz.getValue() == "napoveda") {
				napoveda();
			} else if (vstupniTextS != null && vstupniTextS != vstupniText.getPromptText()) {
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
			vystup.appendText("\n" + hra.getEpilog());
		}
	}
	
	@FXML public void napoveda() {
		String url = getClass().getResource("napoveda.html").toExternalForm();
		WebView webView = new WebView();
		WebEngine engine = webView.getEngine();
		engine.load(url);
		Scene scene = new Scene(webView, 500, 150);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Nápoveda");
		stage.show();
	}
	
	/**
	 * Metoda bude soužit pro předání objektu se spuštěnou hrou
	 * kontroleru a zobrazí stav hry v grafice.
	 * @param objekt spuštěné hry
	 */
	public void inicializuj(Game hra) {
		invTooltip.setText("Velkost inventara je: " + hra.getGamePlan().getInventory().getStorageSize() + "\nZaplnenost: " + hra.getGamePlan().getInventory().getActualSize()+ "/" + hra.getGamePlan().getInventory().getStorageSize());
		Tooltip.install(invimg, invTooltip);
		vystup.setText(hra.getProlog());
		vystup.setEditable(false);
		this.hra = hra;
		seznamVeciMistnost.getItems().addAll(hra.getGamePlan().getCurrentLocation().getVeci());
		seznamVychodu.getItems().addAll(hra.getGamePlan().getCurrentLocation().getExitLocations());
		vstupnyPrikaz.getItems().addAll(hra.getListOfCommands().getCommands());
		uzivatel.setX(hra.getGamePlan().getCurrentLocation().getX());
		uzivatel.setY(hra.getGamePlan().getCurrentLocation().getY());
		hra.addObserver(this);
		inventory();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (hra.getGamePlan().getInventory().getStorageSize() == 11) {
			invTooltip.setText("Velkost inventara je: " + (hra.getGamePlan().getInventory().getStorageSize() - 1)  + "\nZaplnenost: " + (hra.getGamePlan().getInventory().getActualSize() - 1) + "/" + (hra.getGamePlan().getInventory().getStorageSize() - 1));
		} else invTooltip.setText("Velkost inventara je: " + hra.getGamePlan().getInventory().getStorageSize()  + "\nZaplnenost: " + hra.getGamePlan().getInventory().getActualSize()+ "/" + hra.getGamePlan().getInventory().getStorageSize());
		Tooltip.install(invimg, invTooltip);
		seznamVeciMistnost.getItems().clear();
		seznamVychodu.getItems().clear();
		seznamVeciMistnost.getItems().addAll(hra.getGamePlan().getCurrentLocation().getVeci());
		seznamVychodu.getItems().addAll(hra.getGamePlan().getCurrentLocation().getExitLocations());
		Figure figure = hra.getGamePlan().getCurrentLocation().getFigure2();
		if (figure != null && figure.getCurrentTask() != "") {
			if (figure.getCompletedTasks() != 0 && figure.getCompletedTasks() != figure.getTasksToComplete()) {
				if (zadaneUlohy.getItems().get(0).contains("skriatok") && figure.getName().equals("skriatok")){
					zadaneUlohy.getItems().remove(0); tru = 0;
				} else if (figure.getName().equals("skriatok")) { zadaneUlohy.getItems().remove(1); tru = 0;}
			}
			if (figure.getCompletedTasks() == figure.getTasksToComplete()) {
				if (figure.getName().equals("skriatok")) {
					zadaneUlohy.getItems().clear();
				}
				if (figure.getName() == "carodejnicka" && zadaneUlohy.getItems().get(0).contains("carodejnicka")) {
					zadaneUlohy.getItems().remove(0); plan.setImage(new Image(getClass().getResource("plan2.png").toExternalForm()));
				} else if (figure.getName() == "carodejnicka" && zadaneUlohy.getItems().size() > 1) { zadaneUlohy.getItems().remove(1); plan.setImage(new Image(getClass().getResource("plan2.png").toExternalForm()));}
			}
			if (tro == 0 && figure.getName().equals("carodejnicka")) {
				zadaneUlohy.getItems().add(figure.getName() + ": " + figure.getCurrentTask());
			}
			if (tru == 0 && figure.getName().equals("skriatok")) {
				zadaneUlohy.getItems().add(figure.getName() + ": " + figure.getCurrentTask());
			}
			if (figure.getName().equals("carodejnicka")) {
				tro = 1;
			} else {
				tru = 1;
			}
		}
		uzivatel.setX(hra.getGamePlan().getCurrentLocation().getX());
		uzivatel.setY(hra.getGamePlan().getCurrentLocation().getY());
		hra.getGamePlan().getCurrentLocation().addObserver(this);
		if (hra.getGamePlan().getCurrentLocation().getFigure2() != null) {
			hra.getGamePlan().getCurrentLocation().getFigure2().addObserver(this);
		}
		inventory();
		ArrayList<Location> exit = new ArrayList<Location>(hra.getGamePlan().getCurrentLocation().getExitLocations());
		if (exit.toString().contains("domov")) {
			plan.setImage(new Image(getClass().getResource("plan3.png").toExternalForm()));
		}
	}
	@FXML public void comboChanged(){
		String value = vstupnyPrikaz.getValue();
		ArrayList<ItemCount> loc = new ArrayList<ItemCount>(hra.getGamePlan().getCurrentLocation().getVeci());
		ArrayList<ItemCount> inv = new ArrayList<ItemCount>(hra.getGamePlan().getInventory().getVeci());
		ArrayList<Location> exit = new ArrayList<Location>(hra.getGamePlan().getCurrentLocation().getExitLocations());
		vstupniText.setVisible(true);
		vstupniText.getItems().clear();
		vstupniText.show();
		vstupniText.hide();
		if (value != null) {
		switch(value) {
			case "vrat" : {
				for(int i = 0; i < inv.size(); i++)
					if (inv.get(i).toString().contains("jablko (")) {
						if (inv.get(i).toString().contains("kuzelne")) {
							vstupniText.getItems().add("kuzelne_jablko");
						} else vstupniText.getItems().add("jablko");
					} else vstupniText.getItems().add(inv.get(i).toString());
				break;
				}
			case "vezmi" : {
				for(int i = 0; i < loc.size(); i++)
					if (loc.get(i).toString().contains("jablko (")) {
						vstupniText.getItems().add("jablko");
					} else vstupniText.getItems().add(loc.get(i).toString());
				break;
			}
			case "pouzi" : {				
				for(int i = 0; i < inv.size(); i++)
					if (inv.get(i).toString().contains("jablko (")) {
						if (inv.get(i).toString().contains("kuzelne")) {
							vstupniText.getItems().add("kuzelne_jablko");
						} else vstupniText.getItems().add("jablko");
					} else vstupniText.getItems().add(inv.get(i).toString());
				break;
			}
			case "preskumaj" : {
				for(int i = 0; i < loc.size(); i++)
					if (loc.get(i).toString().contains("jablko (")) {
						vstupniText.getItems().add("jablko");
					} else vstupniText.getItems().add(loc.get(i).toString());
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
	public void inventory() {
		int jablkoo = 0;
		ArrayList<ItemCount> inv = new ArrayList<ItemCount>(hra.getGamePlan().getInventory().getVeci());
		for (int i = 0; i < inv.size(); i++) {
			if (inv.get(i).toString().contains("batoh")) {
				batohimg.setVisible(true);
				batohimg.setTranslateX(35);
				batohimg.setTranslateY(382);
				Tooltip batohTool = new Tooltip();
				batohTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("batoh").getItem().getDesc());
				Tooltip.install(batohimg, batohTool);
			}
			if (inv.get(i).toString().contains("jablko")) {
				if (inv.toString().contains("kuzelne")) {
					kuzelneimg.setVisible(true);
					if (i>1) {
						kuzelneimg.setTranslateX(75 + (i-1)*40);
					} else kuzelneimg.setTranslateX(75 + i*40);
					kuzelneimg.setTranslateY(382);
					Tooltip kuzelneTool = new Tooltip();
					kuzelneTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("kuzelne_jablko").getItem().getDesc() + "\nPocet: " + hra.getGamePlan().getInventory().getContainsItemCount("kuzelne_jablko").getCount());
					Tooltip.install(kuzelneimg, kuzelneTool);
				} else {
					jablkoimg.setVisible(true);
					jablkoimg.setTranslateX(75 + i*40);
					jablkoimg.setTranslateY(382);
					Tooltip jablkoTool = new Tooltip();
					jablkoTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("jablko").getItem().getDesc() + "\nPocet: " + hra.getGamePlan().getInventory().getContainsItemCount("jablko").getCount());
					Tooltip.install(jablkoimg, jablkoTool);
					jablkoo = i;
				}
			}
			if (inv.get(i).toString().contains("lopta")) {
				loptaimg.setVisible(true);
				if (i>1) {
					loptaimg.setTranslateX(75 + (i-1)*40);
				} else loptaimg.setTranslateX(75 + i*40);
				loptaimg.setTranslateY(382);
				Tooltip loptaTool = new Tooltip();
				loptaTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("lopta").getItem().getDesc());
				Tooltip.install(loptaimg, loptaTool);
			}
			if (inv.get(i).toString().contains("bylinky")) {
				bylinkyimg.setVisible(true);
				if (i>1) {
					bylinkyimg.setTranslateX(75 + (i-1)*40);
				} else bylinkyimg.setTranslateX(75 + i*40);
				bylinkyimg.setTranslateY(382);
				Tooltip bylinkyTool = new Tooltip();
				bylinkyTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("bylinky").getItem().getDesc());
				Tooltip.install(bylinkyimg, bylinkyTool);
			}
			if (inv.get(i).toString().contains("prsten")) {
				prstenimg.setVisible(true);
				if (i>1) {
					prstenimg.setTranslateX(75 + (i-1)*40);
				} else prstenimg.setTranslateX(75 + i*40);
				prstenimg.setTranslateY(382);
				Tooltip prstenTool = new Tooltip();
				prstenTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("prsten").getItem().getDesc());
				Tooltip.install(prstenimg, prstenTool);
			}
			if (inv.get(i).toString().contains("kluc")) {
				klucimg.setVisible(true);
				if (i>0) {
					klucimg.setTranslateX(75 + (i-1)*40);
				} else klucimg.setTranslateX(75 + i*40);
				klucimg.setTranslateY(382);
				Tooltip klucTool = new Tooltip();
				klucTool.setText(hra.getGamePlan().getInventory().getContainsItemCount("kluc").getItem().getDesc());
				Tooltip.install(klucimg, klucTool);
			}
		}
		if (inv.size() != 0) {
			if (!inv.get(jablkoo).toString().matches("(^jablko \\(\\d[x]\\)|^jablko)")) {
				jablkoimg.setVisible(false);
			}
		} else jablkoimg.setVisible(false);
		if (!inv.toString().contains("kuzelne_jablko")) {
			kuzelneimg.setVisible(false);
		}
		if (!inv.toString().contains("batoh")) {
			batohimg.setVisible(false);
		}
		if (!inv.toString().contains("lopta")) {
			loptaimg.setVisible(false);
		}
		if (!inv.toString().contains("bylinky")) {
			bylinkyimg.setVisible(false);
		}
		if (!inv.toString().contains("prsten")) {
			prstenimg.setVisible(false);
		}
		if (!inv.toString().contains("kluc")) {
			klucimg.setVisible(false);
		}
	}
}


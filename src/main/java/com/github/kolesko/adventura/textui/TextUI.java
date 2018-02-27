/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package com.github.kolesko.adventura.textui;

import java.util.Scanner;
import java.io.*;

import com.github.kolesko.adventura.model.IGame;

/**
 * Toto je třída uživatelského rozhraní aplikace Adventura. Třída vytváří
 * instanci třídy Game, která představuje logiku aplikace. Čte vstup zadaný
 * uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na
 * konzoli.
 *
 * @author     Michael Kolling, Luboš Pavlíček, Jarmila Pavlíčková, Jan Říha
 * @version    ZS 2017/2018
 */

public class TextUI
{
    private IGame game;

    /**
     * Vytváří nové textové rozhraní pro hru.
     *
     * @param game hra
     */
    public TextUI(IGame game)
    {
        this.game = game;
    }

    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     * příkazu od hráče do konce hry (dokud metoda isGameOver() z logiky nevrátí
     * hodnotu true). Nakonec vypíše text epilogu.
     */
    public void play()
    {
        System.out.println(game.getProlog());

        // Základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!game.isGameOver()) {
            String line = readLine();
            System.out.println(game.processCommand(line));
        }

        System.out.println(game.getEpilog());
    }
    
    public void playFromFile(String fileName) {
        System.out.println("nacitam prikazy ze souboru " + fileName);
        File file = new File(fileName);
        
        System.out.println(game.getProlog());
        
        try{
           Scanner sc = new Scanner(file);
 
           while (sc.hasNextLine() && !game.isGameOver()) {
               String line = sc.nextLine();
               System.out.println("> "+ line);
               System.out.println(game.processCommand(line));
           }
            
           //System.out.println(game.getEpilog());
           if (!game.isGameOver()){
               play();
           } else System.out.println(game.getEpilog());
        }
        catch (FileNotFoundException e) {
            System.out.println("Soubor " + fileName + " nejde nacitat");
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Doslo ku chybe pri nacitani suboru");
            e.printStackTrace();
        }
        /*BufferedReader br = null;
        FileReader fr = null;
       
        try {
           fr = new FileReader(fileName);
           br = new BufferedReader(fr);
            
            String currentLine;
            
            while ((currentLine = br.readLine()) != null) {
                System.out.println("> " + currentLine);
                System.out.println(game.processCommand(currentLine));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Soubor " + fileName + " nejde nacitat");
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Doslo ku chybe pri nacitani suboru");
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) 
                    br.close();
                if (fr != null)
                    fr.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
        
        
        //System.out.println(game.getEpilog());
    }

    /**
     * Metoda přečte příkaz z příkazového řádku
     *
     * @return vrací přečtený příkaz jako instanci třídy String
     */
    private String readLine()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}

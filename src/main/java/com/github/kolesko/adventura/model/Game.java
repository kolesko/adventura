/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;

/**
 * Toto je hlavní třída logiky aplikace. Třída vytváří instanci třídy
 * GamePlan, která inicializuje lokace hry a vytváří seznam platných
 * příkazů a instance tříd provádějící jednotlivé příkazy.
 *
 * Vypisuje uvítací a ukončovací text hry. Také vyhodnocuje jednotlivé
 * příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Luboš Pavlíček, Jarmila Pavlíčková, Jan Říha
 * @version    ZS 2017/2018
 */

public class Game implements IGame
{
    private ListOfCommands listOfCommands;
    private GamePlan gamePlan;
    private boolean gameOver = false;

    /**
     * Vytváří hru a inicializuje lokace (prostřednictvím třídy GamePlan)
     * a seznam platných příkazů.
     */
    public Game()
    {
        gamePlan = new GamePlan();
        listOfCommands = new ListOfCommands();
        listOfCommands.insertCommand(new CommandHelp(listOfCommands));
        listOfCommands.insertCommand(new CommandMove(gamePlan));
        listOfCommands.insertCommand(new CommandTerminate(this));
        listOfCommands.insertCommand(new CommandPick(gamePlan));
        listOfCommands.insertCommand(new CommandDrop(gamePlan));
        listOfCommands.insertCommand(new CommandInspect(gamePlan));
        listOfCommands.insertCommand(new CommandTalk(gamePlan));
        listOfCommands.insertCommand(new CommandUse(gamePlan));
        listOfCommands.insertCommand(new CommandInventory(gamePlan));
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     *
     * @return úvodní zprávu pro hráče
     */
    @Override
    public String getProlog()
    {
        return "Vítejte!\n" +
               "Toto je příběh o Červené Karkulce, babičce a vlkovi.\n" +
               "Napište 'napoveda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               gamePlan.getCurrentLocation().getFullDescription() + "\n" + "Tvoj zivot: " + gamePlan.getStatLife();
    }
    
    /**
     * Vrátí závěrečnou zprávu pro hráče.
     *
     * @return závěrečnou zprávu pro hráče
     */
    @Override
    public String getEpilog()
    {
        String navratovytext = "";
        if (gamePlan.getLife().getCurrentLife() == 0) {
            navratovytext = "Zomrel si, dakujem ze si si zahral ale prehral si. Ahoj.";
        } else {
            if (gamePlan.getCurrentLocation().getName().equals(gamePlan.getFinalLocationName())) {
                navratovytext = "Vyhral si. Dakujem ze si si zahral. Ahoj.";
            } else {
                navratovytext = "Dík, že jste si zahráli.  Ahoj.";
            }
        }
        return navratovytext;
    }

    /**
     * Vrací true, pokud hra skončila.
     *
     * @return true, pokud hra již skončila; jinak false
     */
    @Override
    public boolean isGameOver()
    {
        if (gamePlan.getLife().getCurrentLife() == 0) {
            gameOver = true;
        }
        return gameOver;
    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo
     * příkazu a další parametry. Pak otestuje zda příkaz je klíčovým slovem,
     * např. jdi. Pokud ano spustí samotné provádění příkazu.
     *
     * @param line  text, který zadal uživatel jako příkaz do hry
     * @return řetězec, který se má vypsat na obrazovku
     */
    @Override
    public String processCommand(String line)
    {
        String[] words = line.split("[ \t]+");
        String cmdWord = words[0];
        String[] parameters = new String[words.length - 1];

        for (int i = 0; i < parameters.length; i++) {
            parameters[i]= words[i+1];
        }

        String result = null;
        if (listOfCommands.checkCommand(cmdWord)) {
            ICommand command = listOfCommands.getCommand(cmdWord);
            result = command.process(parameters);
            if (!cmdWord.equals("konec")) {
                gameOver = gamePlan.playerWon();
            }
        } else {
            result = "Nevím, co tím myslíš. Tento příkaz neznám.";
        }
        
        return result;
    }

    /**
     * Nastaví, že nastal konec hry, metodu využívá třída CommandTerminate,
     * mohou ji použít i další implementace rozhraní ICommand.
     *
     * @param příznak, zda hra již skončila
     */
    public void setGameOver(boolean konecHry)
    {
        this.gameOver = konecHry;
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní lokace hry.
     *
     * @return herní plán
     */
    @Override
    public GamePlan getGamePlan()
    {
        return gamePlan;
    }
}

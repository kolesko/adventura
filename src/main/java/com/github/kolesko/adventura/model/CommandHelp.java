/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Říha
 * @version    LS 2016/2017
 */
public class CommandHelp implements ICommand {

    private static final String NAME = "napoveda";
    private ListOfCommands listOfCommands;

   /**
    * Konstruktor třídy
    *
    * @param    platnePrikazy seznam příkazů, které je možné ve hře použít, aby je nápověda mohla zobrazit uživateli.
    */    
    public CommandHelp(ListOfCommands platnePrikazy) {
        this.listOfCommands = platnePrikazy;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return    napoveda ke hre
     */
    @Override
    public String process(String... parametry) {
        return "Tvojou úlohou je dostať sa do svojho domu.\n"
        + "Ak sa tam chceš dostať musíš splniť škriatkové úlohy.\n"
        + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + listOfCommands.getCommandNames();
    }

     /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *  
     * @return    nazev prikazu
     */
    @Override
      public String getName() {
        return NAME;
     }
    @Override
    public String toString() {
    	return getName();
    }
}

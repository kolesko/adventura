/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;

/**
 * Třída PrikazKonec implementuje pro hru příkaz konec.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Jan Riha
 * @version    LS 2016/2017
 *
 */

public class CommandTerminate implements ICommand {

    private static final String NAME = "konec";

    private Game game;

    /**
     * Konstruktor třídy
     *
     * @param    hra odkaz na hru, která má být příkazem konec ukončena
     */    
    public CommandTerminate(Game hra) {
        this.game = hra;
    }

    /**
     * V případě, že příkaz má jen jedno slovo "konec" hra končí(volá se metoda setKonecHry(true))
     * jinak pokračuje např. při zadání "konec a".
     *
     * @return zpráva, kterou vypíše hra hráči
     */

    @Override
    public String process(String... parametry) {
        if (parametry.length > 0) {
            return "Ukončit co? Nechápu, proč jste zadal druhé slovo.";
        }
        else {
            game.setGameOver(true);
            return "hra ukončena příkazem konec";
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     * @return    nazev prikazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}

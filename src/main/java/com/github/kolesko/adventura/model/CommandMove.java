/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Říha
 * @version    LS 2016/2017
 */
public class CommandMove implements ICommand {
    private static final String NAME = "jdi";
    private GamePlan plan;

   /**
    * Konstruktor třídy
    *
    * @param    plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public CommandMove(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadané lokace. Pokud lokace
     * existuje, vstoupí se do nového lokace. Pokud zadaná sousední lokace
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr obsahuje jméno lokace (východu), do kterého se má jít.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousední lokace
        Location sousedniLokace = plan.getCurrentLocation().getExitLocation(smer);

        if (sousedniLokace == null) {
            return "Tam se odsud jít nedá!";
        }
        else {
            plan.setCurrentLocation(sousedniLokace);
            plan.getLife().decCurrentLife(); 
            return sousedniLokace.getFullDescription() + "\n" + "Tvoj zivot: " + plan.getStatLife();
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

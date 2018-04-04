package com.github.kolesko.adventura.model;



public class CommandInventory implements ICommand
{
    private static final String NAME = "inventar";
    private GamePlan plan;
    
    /**
     * Konstruktor třídy
     *
     * @param    plan odkaz na plan, z ktoreho si zoberie odkaz na inventar hraca
     */   
    public CommandInventory(GamePlan plan) {
        this.plan = plan;
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
    /**
     * Provádí příkaz "inventar". Ak je vyvolany tento prikaz tak sa vypise obsah inventaru hraca
     *
     * @param     parametry tento prikaz nepotrebuje.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return plan.getInventory().getInventoryItems();
        }
        else {
            return "Neviem spracovat tento vyraz.";
        }
        
    }
    @Override
    public String toString() {
    	return getName();
    }
}

package com.github.kolesko.adventura.model;



public class CommandDrop implements ICommand
{
    private static final String NAME = "vrat";
    private GamePlan plan;
    private Inventory inventory;
    
    /**
     * Konstruktor třídy
     *
     * @param    plan odkaz na plan, z ktoreho si zoberie odkaz na inventar hraca
     */   
    public CommandDrop(GamePlan plan) {
        this.plan = plan;
        inventory = plan.getInventory();
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
     * Provádí příkaz "vrat". Zkouší vratit predmet ktory sa nachadza v inventari. Ak
     * predmet je v inventari tak sa vymaze z inventara a pripise sa ku lokacii v ktorej sa nachadza. 
     * Pokud predmet nieje v inventari, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr nazov predmetu, ktoroy chce vratit.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Co mám vratit? Musíš zadat jméno predmetu";
        }
        
        String predmet = parametry[0];
        Item hladanyPredmet = inventory.getContainsItem(predmet);
        if (hladanyPredmet == null) {
            return "Predmet ktory chces vratit nieje v inventari!";
        } else {
            if (predmet.equals("batoh")) {
                String[] items = inventory.getInventoryItems2().split(" ");
                for (String item : items) {
                    plan.getCurrentLocation().addItem(inventory.removeItems(item));
                }
                return "Vratil si batoh a s nim aj vsetky predmety v nom";
            }
                plan.getCurrentLocation().addItem(inventory.removeItem(predmet));
            return "Vratil si predmet: " + predmet + ".";
        }
        
    }
}

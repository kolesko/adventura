package com.github.kolesko.adventura.model;



public class CommandPick implements ICommand
{
    private static final String NAME = "vezmi";
    private GamePlan plan;
    private Inventory inventory;
    private boolean first = true;
    
    /**
    * Konstruktor třídy
    *
    * @param    plan herní plán, z ktoreho vybera predmety
    */    
    public CommandPick(GamePlan plan) {
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
     * Provádí příkaz "vezmi". Zkouší zobrat predmet. Ak predmet existuje,
     * inventar nieje plny a da sa preniest tak predmet zoberie. 
     * Ak predmet neexistuje alebo je plny inventar alebo sa neda zobrat, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr obsahuje nazov predmetu, ktory chce zobrat.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Co mám vzít? Musíš zadat jméno predmetu";
        }
        
        if (parametry.length > 1) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Neviem zobrat viac predmetov naraz";
        }

        String predmet = parametry[0];
        Item hladanyPredmet = null;
        // zkoušíme přejít do sousední lokace
        hladanyPredmet = plan.getCurrentLocation().getContainsItem(predmet);
        if (hladanyPredmet == null) {
            return "Taky predmet tu nieje!";
        }
        if (hladanyPredmet != null && hladanyPredmet.isMoveable())
        {
            if (inventory.getStorageSize() == inventory.getActualSize()){
              return "Inventar je plny, predmet sa neda zobrat!";   
            }
            if (predmet.equals("kuzelne_jablko")) {
                inventory.addItem(new ItemCount(hladanyPredmet,1));
            } else {
                inventory.addItem(plan.getCurrentLocation().removeItem(predmet));
            }
            String returnForm = inventory.getInventoryItems(); 
            if (inventory.containsItem("batoh")) {
                inventory.setStorageSize(11);
                if (first) {
                    returnForm = returnForm + ", velkost inventara sa zvacsila na 10";
                    first = false;
                }
            }
            return returnForm;
        } else {
            return "Predmet sa neda zobrat"; 
        }
    }
}

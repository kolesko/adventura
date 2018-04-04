package com.github.kolesko.adventura.model;



public class CommandUse implements ICommand
{
    private static final String NAME = "pouzi";
    private GamePlan plan;
    private Inventory inventory;
    
    /**
     * Konstruktor třídy
     *
     * @param    plan odkaz na plan, z ktoreho si zoberie odkaz na inventar hraca
     */    
    public CommandUse(GamePlan plan) {
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
     * Provádí příkaz "pouzi". Zkouší pouzit predmet ktory sa nachadza v inventari. Ak
     * predmet je v inventari spravi sa to co ma urobit po pouziti a vymaze sa z inventara. 
     * Pokud predmet nieje v inventari, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr nazov predmetu, ktoroy chce pouzit.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Co chces pouzit? Musíš zadat jméno predmetu";
        }
        
        String predmet = parametry[0];
        Item hladanyPredmet = null;
        // zkoušíme přejít do sousední lokace
      
        hladanyPredmet = inventory.getContainsItem(predmet);
        
        
        if (hladanyPredmet == null) {
            return "Predmet ktory chces pouzit nieje v inventari!";
        } else {
            if (predmet.equals("kuzelne_jablko")) {
                if (plan.getCurrentLocation().getName().equals("kuzelna_jablon")) {
                    plan.getLife().incbynumberCurrentLife(4);
                    if (plan.getLife().getCurrentLife() > plan.getLife().getMaxLife()) {
                        plan.getLife().setCurrentLife(plan.getLife().getMaxLife());
                    }
                    inventory.removeItem(predmet);
                    return "Pouzil si predmet: " + predmet + " a tvoj novy zivot je: " + plan.getStatLife();
                    } else {
                        return "kuzelne jablko sa neda pouzit mimo lokacie kuzelna_jablon";
                    }
                } else {
                    if (predmet.equals("jablko")) {
                    plan.getLife().incbynumberCurrentLife(1);
                    if (plan.getLife().getCurrentLife() > plan.getLife().getMaxLife()) {
                       plan.getLife().setCurrentLife(plan.getLife().getMaxLife());
                    }
                    inventory.removeItem(predmet);
                    return "Pouzil si predmet: " + predmet + " a tvoj novy zivot je: " + plan.getStatLife();
                }
            }
                
             
            if (predmet.equals("kluc") && plan.getCurrentLocation().getName().equals("dvor")) {
                    plan.getCurrentLocation().addExit(plan.getLocation(plan.getFinalLocationName()));
                    inventory.removeItem(predmet);
                    return "Pouzil si predmet: " + predmet + " a odomkol si si cestu " + plan.getFinalLocationName() +".";
            } 
            return "Tento predmet sa neda pouzit.";
            }
            
        }
    @Override
    public String toString() {
    	return getName();
    }
    }
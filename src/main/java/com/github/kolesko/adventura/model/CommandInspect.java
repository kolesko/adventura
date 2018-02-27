package com.github.kolesko.adventura.model;



public class CommandInspect implements ICommand
{
    private static final String NAME = "preskumaj";
    private GamePlan plan;
    
    /**
     * Konstruktor třídy
     *
     * @param    plan odkaz na plan(neskor lokaciu), ktory predmet chce pouzit
     */   
    public CommandInspect(GamePlan plan) {
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
     * Provádí příkaz "preskumaj". Zkouší preskumat predmet. Ak sa predmet nachadza 
     * v lokacii v ktorej je hrac, tak sa vypise description predmetu
     * Ak predmet neexistuje(nenachadza sa v danej lokacii), vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr obsahuje nazov predmetu, ktory chce preskumat.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Co mám preskumat? Musíš zadat jméno predmetu";
        }
        String predmet = parametry[0];
        Item skumanyPredmet = plan.getCurrentLocation().getContainsItem(predmet);
        
        if (skumanyPredmet == null) {
            return "Taky predmet tu nieje!";
        } else {
            return skumanyPredmet.getDesc();
        }
        
    }
}

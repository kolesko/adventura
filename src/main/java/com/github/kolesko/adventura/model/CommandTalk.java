/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Třída PrikazKonec implementuje pro hru příkaz konec.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Jan Riha
 * @version    LS 2016/2017
 *
 */

public class CommandTalk implements ICommand {

    private static final String NAME = "mluv";
    private GamePlan plan;

    /**
     * Konstruktor třídy
     *
     * @param    plan odkaz na plan, kde zistuje ci existuje postava
     */    
    public CommandTalk(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "mluv". Zkouší rozpravat zo zadanou osobou. Pokud osoba
     * existuje, vypise svoju voice line. Pokud osoba neexistuje, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr nazov osoby, s ktorou chce rozpravat.
     * @return    zpráva, kterou vypíše hra hráči
     */ 

    @Override
    public String process(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Skym mam rozpravat? Musíš zadat jméno postavy";
        }
        
        String postava = parametry[0];
        Figure figure = plan.getCurrentLocation().getFigure(postava);
        if (figure != null) {
            figure.incTalkTimes();
            String navratovytext = "";
            int tasknumber;
            Set<String> taskt = figure.getTasks().keySet();
            String [] tasks = taskt.toArray(new String[0]);
            if (figure.getTalkTimes() == 1) {
                tasknumber = ThreadLocalRandom.current().nextInt(0, tasks.length);
                figure.setCurrentTask(tasks[tasknumber]);
                navratovytext += figure.getText() + "\n" + figure.getVoiceLines().get("task" + String.valueOf(figure.getTalkTimes())) + figure.getCurrentTask() + 
                "\n" + "Splnené úlohy : " + String.valueOf(figure.getCompletedTasks()) + "/" + String.valueOf(figure.getTasksToComplete());
                return navratovytext;
            } else {
                Item wantedItem = (Item)figure.getTasks().get(figure.getCurrentTask());
                if (figure.getTalkTimes() > figure.getTasksToComplete() + 1 && figure.getCompletedTasks() == figure.getTasksToComplete()) {
                    return "Uz nemam pre teba ulohy, vsetky si splnil";
                }
                if (plan.getCurrentLocation().containsItem(wantedItem.getName())) {
                    plan.getCurrentLocation().removeItem(wantedItem.getName());
                    figure.incCompletedTasks();
                    if (figure.getCompletedTasks() == figure.getTasksToComplete()) {
                        navratovytext += figure.getAnswer();
                        if (figure.getName().equals("carodejnicka")) {
                            plan.getLocation("carodejnickyn_dom").addExit(plan.getLocation("jaskyna"));
                            plan.getLocation("les").addExit(plan.getLocation("jaskyna"));
                            plan.getLocation("jaskyna").addExit(plan.getLocation("carodejnickyn_dom"));
                            plan.getLocation("jaskyna").addExit(plan.getLocation("les"));
                            plan.getLocation("carodejnickyn_dom").addExit(plan.getLocation("kuzelna_jablon"));
                            plan.getLocation("kuzelna_jablon").addExit(plan.getLocation("carodejnickyn_dom"));
                        } else {
                            Item kluc = new Item("kluc", "kluc od tvojho domu", true);
                            plan.getLocation("dvor").addItem(new ItemCount(kluc,1));
                        }
                    } else {
                        figure.getTasks().remove(figure.getCurrentTask());
                        taskt = figure.getTasks().keySet();
                        tasks = taskt.toArray(new String[0]);
                        tasknumber = figure.getTasksToComplete() - figure.getCompletedTasks() - 1;
                        figure.setCurrentTask(tasks[tasknumber]);
                        navratovytext += figure.getVoiceLines().get("task" + String.valueOf(figure.getTalkTimes())) + figure.getCurrentTask() + 
                            "\n" + "Splnené úlohy : " + String.valueOf(figure.getCompletedTasks()) + "/" + String.valueOf(figure.getTasksToComplete());
                    }
                    return navratovytext;
                } else { 
                    figure.decTalkTimes();
                    return "To co chcem sa tu nenachadza, ulohu si este nesplnil";
                }
            }
        } else {
            return "Takato postava sa tu nenachadza.";
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
    @Override
    public String toString() {
    	return getName();
    }
}
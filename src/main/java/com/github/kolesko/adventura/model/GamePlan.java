/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;
import java.util.*;
/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny lokace, propojuje je vzájemně pomocí východů 
 * a pamatuje si aktuální lokaci, ve které se hráč právě nachází.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha
 * @version    LS 2016/2017
 */
public class GamePlan extends Observable{
    private static final String FINAL_LOCATION_NAME = "domov";
    private Location currentLocation;
    private Inventory inventory;
    private Life life;
    private Map<String, Location> locations;
    
    /**
     * Konstruktor který vytváří jednotlivé lokace a propojuje je pomocí východů.
     */
    public GamePlan() {
        prepareWorldMap();
    }

    /**
     * Vytváří jednotlivé lokace a propojuje je pomocí východů.
     * Jako výchozí aktuální lokaci nastaví domeček.
     */
    private void prepareWorldMap() {
        // vytvářejí se jednotlivé lokace
        inventory = new Inventory();
        life = new Life();
        Location dvor = new Location("dvor","Tvoj dvor, z ktoreho sa vies dostat domov po splneni uloh",118.0,89.0);
        Location domov = new Location(FINAL_LOCATION_NAME, "domov, kde byvas, zaroven ciel",116.0,70.0);
        Location jaskyna = new Location("jaskyna","stará plesnivá jeskyně",104.0,185.0);
        Location les = new Location("les","les s jahodami, malinami a pramenem vody",158.0,161.0);
        Location hlbokyLes = new Location("hlboky_les","temný les",155.0,220.0);
        Location jazero = new Location("jazero","jazero pri ktorom byva carodejnicka",23.0,173.0);
        Location carodejnickyn_dom = new Location("carodejnickyn_dom", "dom, v ktorom byva kuzelnik",95.0,235.0);
        Location kuzelna_jablon = new Location("kuzelna_jablon", "jablon, z jej jablk si vies doplnit zivot",91.0,262.0);
        Location luka = new Location("luka", "luka, na ktorej sa nachadzaju rastliny",203.0,274.0);
        Location rozcestnik = new Location("rozcestnik", "rozcestnik",115.0,134.0);
        Figure kuzelnik = new Figure("carodejnicka", "Ahoj, ja som čarodejníčka.","Dakujem, vchod do jaskyne je hned za mojim domom, a v lese.\nTaktiez som ti dal pristup ku kuzelnej jabloni.");
        Figure skriatok = new Figure ("skriatok", "HAHAHAHA, tvoj kluc od domu mam ja a ked mi nesplnis moje ulohy tak ho nedostanes.", "Splnil si vsetky ulohy, tu mas kluc mozes ist domov.");
        Item jablko = new Item("jablko", "obycajne jablko ktore doplna 1 zivot", true);
        Item kuzelne_jablko = new Item("kuzelne_jablko", "kuzelne jablko ktore doplna 4 zivoty", true);
        Item lopta = new Item("lopta", "lopta ktoru skriatok stratil v hlbokom lese",true);
        Item prsten = new Item("prsten", "zlaty prsten",true);
        Item batoh = new Item("batoh", "Batoh aby si mohol mat viac ako dva predmety pri sebe (10)", true);
        Item bylinky = new Item("bylinky", "Bylinky, z ktorych vyraba kuzelnik svoje elixiry", true);
        Item stol = new Item("stol", "stol pri ktorom sedi skriatok");
        
        locations = new HashMap<>();
        locations.put(dvor.getName(), dvor);
        locations.put(domov.getName(), domov);
        locations.put(jaskyna.getName(), jaskyna);
        locations.put(les.getName(), les);
        locations.put(hlbokyLes.getName(), hlbokyLes);
        locations.put(jazero.getName(), jazero);
        locations.put(carodejnickyn_dom.getName(), carodejnickyn_dom);
        locations.put(kuzelna_jablon.getName(), kuzelna_jablon);
        locations.put(luka.getName(), luka);
        locations.put(rozcestnik.getName(), rozcestnik);
        // přiřazují se průchody mezi lokacemi (sousedící lokace)
        
        dvor.addExit(rozcestnik);
        rozcestnik.addExit(jazero);
        rozcestnik.addExit(les);
        rozcestnik.addExit(dvor);
        les.addExit(rozcestnik);
        jazero.addExit(carodejnickyn_dom);
        carodejnickyn_dom.addExit(hlbokyLes);
        carodejnickyn_dom.addExit(jazero);
        jazero.addExit(rozcestnik);
        hlbokyLes.addExit(luka);
        hlbokyLes.addExit(les);
        hlbokyLes.addExit(carodejnickyn_dom);
        les.addExit(hlbokyLes);
        luka.addExit(hlbokyLes);
        
        // priradzuju sa itemy k lokacii
        dvor.addItem(new ItemCount(batoh,1));
        dvor.addItem(new ItemCount(stol,1));
        jazero.addItem(new ItemCount(jablko,3));
        les.addItem(new ItemCount(jablko,2));
        hlbokyLes.addItem(new ItemCount(jablko,5));
        hlbokyLes.addItem(new ItemCount(lopta,1));
        jaskyna.addItem(new ItemCount(prsten,1));
        luka.addItem(new ItemCount(bylinky,1));
        kuzelna_jablon.addItem(new ItemCount(kuzelne_jablko,1));
        
        // Skriatkove ulohy
        
        skriatok.addTask("V hlbokom lese mi najdi loptu a dones mi ju", lopta);
        skriatok.addTask("Dones mi jedno kuzelne jablko\nKu kuzelnej jabloni sa dostanes ked splnis carodejnickynu ulohu", kuzelne_jablko);
        skriatok.addTask("Z jaskyne mi dones prsten ktory som tam stratil\nDo jaskyne sa dostanes ked splnis carodejnickynu ulohu", prsten);
        
        // Skriatkove voice liny
        
        skriatok.addVoiceLine("task1","Tvoja prva uloha je: ");
        skriatok.addVoiceLine("task2","Prvu ulohu si splnil, tvojou druhou ulohou je: ");
        skriatok.addVoiceLine("task3","Druhu ulohu si splnil, tvojou poslednou ulohou uloha je: ");

        dvor.addFigure(skriatok);
        
        //kuzelnikova uloha;
        
        kuzelnik.addTask("Ulohou je doniest mi bylinky z luky.", bylinky);
        kuzelnik.addVoiceLine("task1", "Ak chces vediet cestu ku jaskyni a pristup ku jabloni musis splnit moju ulohu. ");
        carodejnickyn_dom.addFigure(kuzelnik);
        
        currentLocation = dvor;  // hra začíná na dvore  
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return    aktuální lokace
     */
    
    public Location getCurrentLocation() {
        return currentLocation;
    }
    /**
     * Metoda vrací odkaz na inventar hraca.
     *
     * @return    inventar
     */
    public Inventory getInventory() {
        return inventory;
    }
    /**
     * Metoda zistuje ze ci sa hrac nachadza vo finalnej lokacii.
     *
     * @return    true/false
     */
    public boolean playerWon(){
        return currentLocation.getName().equals(FINAL_LOCATION_NAME);
    }
    /**
     * Metoda ziskava lokaciu pomocou jej nazvu.
     *
     * @return    location;
     */
    public Location getLocation(String name) {
        return locations.get(name);
    }
    /**
     * Metoda zistuje stav zivota.
     *
     * @return    vracia string o stave zivota;
     */
    public String getStatLife(){
        return life.getCurrentLife() + "/" + life.getMaxLife();
    }
    /**
     * Metoda ziskava instanci zivota.
     *
     * @return    Life;
     */
    public Life getLife(){
        return life;
    }
    /**
     * Metoda zistuje nazov vyhernej lokacie.
     *
     * @return    string nazov lokacie;
     */
    public String getFinalLocationName() {
        return FINAL_LOCATION_NAME;
    }
    /**
     * Metoda nastaví aktuální lokaci, používá se nejčastěji při přechodu mezi lokacemi
     *
     * @param    location nová aktuální lokace
     */
    public void setCurrentLocation(Location location) {
       currentLocation = location;
       setChanged();
       notifyObservers();
    }


}

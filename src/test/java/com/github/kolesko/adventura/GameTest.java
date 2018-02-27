package com.github.kolesko.adventura;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.kolesko.adventura.model.*;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra
 *
 * @author     Jarmila Pavlíčková, Jan Říha
 * @version    LS 2016/2017
 */
public class GameTest {
    private Game hra1;

    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Game();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče.
     * 
     */
    @Test
    public void testPrubehHry() {
        assertEquals("dvor", hra1.getGamePlan().getCurrentLocation().getName());
        hra1.processCommand("jdi rozcestnik");
        assertEquals(false, hra1.isGameOver());
        assertEquals("rozcestnik", hra1.getGamePlan().getCurrentLocation().getName());
        hra1.processCommand("jdi les");
        assertEquals(false, hra1.isGameOver());
        assertEquals("les", hra1.getGamePlan().getCurrentLocation().getName());
        hra1.processCommand("jdi hlboky_les");
        assertEquals(false, hra1.isGameOver());
        assertEquals("hlboky_les", hra1.getGamePlan().getCurrentLocation().getName());
        hra1.processCommand("konec");
        assertEquals(true, hra1.isGameOver());
    }
    @Test
    public void testInventory() {
        /*test inventara, ze nemoze mat pri sebe viac veci ako 2 bez batohu a s nim viac ako 10 + batoh*/
        assertEquals(2,hra1.getGamePlan().getInventory().getStorageSize());
        Item jablko = new Item("jablko","pridava zivot",true);
        hra1.getGamePlan().getCurrentLocation().addItem(new ItemCount(jablko,5));
        hra1.processCommand("vezmi jablko");
        hra1.processCommand("vezmi jablko");
        assertEquals(2,hra1.getGamePlan().getInventory().getActualSize());
        hra1.processCommand("vezmi jablko");
        assertEquals(2,hra1.getGamePlan().getInventory().getActualSize());
        hra1.processCommand("vrat jablko");
        assertEquals(1,hra1.getGamePlan().getInventory().getActualSize());
        hra1.processCommand("vezmi batoh");
        assertEquals(11,hra1.getGamePlan().getInventory().getStorageSize());        
        hra1.processCommand("jdi rozcestnik");
    }
    @Test
    public void testPick() {
        /*test inventara, ze nemoze mat pri sebe viac veci ako 2 bez batohu a s nim viac ako 10 + batoh*/
        assertEquals(2,hra1.getGamePlan().getInventory().getStorageSize());
        assertEquals(0,hra1.getGamePlan().getInventory().getActualSize());
        hra1.processCommand("vezmi batoh");
        assertEquals(1,hra1.getGamePlan().getInventory().getActualSize());
        assertEquals("Predmet sa neda zobrat",hra1.processCommand("vezmi stol"));
        assertEquals(1,hra1.getGamePlan().getInventory().getActualSize());
        assertEquals(11,hra1.getGamePlan().getInventory().getStorageSize());        
        hra1.processCommand("jdi rozcestnik");
    }
    @Test
    public void testLifeUse() {
        /*pri presune miestnostami ma odobrat zivot a nasledne vratit spat pouzitim jablka*/
        /*pri pouziti predmetu sa vymaze z inventara*/
        assertEquals(hra1.getGamePlan().getLife().getCurrentLife(),hra1.getGamePlan().getLife().getMaxLife());
        hra1.processCommand("jdi rozcestnik");
        assertEquals(9,hra1.getGamePlan().getLife().getCurrentLife());
        Item jablko = new Item("jablko","pridava zivot",true);
        hra1.getGamePlan().getCurrentLocation().addItem(new ItemCount(jablko,5));
        hra1.processCommand("vezmi jablko");
        assertEquals(1,hra1.getGamePlan().getInventory().getActualSize());
        hra1.processCommand("pouzi jablko");
        assertEquals(0,hra1.getGamePlan().getInventory().getActualSize());
        assertEquals(10,hra1.getGamePlan().getLife().getCurrentLife());
        hra1.processCommand("vezmi jablko");
        hra1.processCommand("pouzi jablko");
        assertEquals(10,hra1.getGamePlan().getLife().getCurrentLife());
    }
    @Test
    public void testEndOfGamePlayerWon() {
        /*ukoncenie hry pri vycerpani zivota (prehra pri nejedeni jablk po 10 presunoch), a ked sa dostane do finalneho priestoru (vyhra)*/
        assertEquals(false, hra1.isGameOver());
        assertEquals(false, hra1.getGamePlan().playerWon());
        hra1.processCommand("jdi rozcestnik");
        hra1.processCommand("jdi dvor");
        hra1.processCommand("jdi rozcestnik");
        hra1.processCommand("jdi dvor");
        hra1.processCommand("jdi rozcestnik");
        hra1.processCommand("jdi dvor");
        hra1.processCommand("jdi rozcestnik");
        hra1.processCommand("jdi dvor");
        hra1.processCommand("jdi rozcestnik");
        assertEquals(false, hra1.isGameOver());
        hra1.processCommand("jdi dvor");
        assertEquals(true, hra1.isGameOver());
        assertEquals(false, hra1.getGamePlan().playerWon());
        /*vyhra*/
        hra1.getGamePlan().getLife().incbynumberCurrentLife(10);
        hra1.setGameOver(false);
        hra1.getGamePlan().getCurrentLocation().addExit(hra1.getGamePlan().getLocation(hra1.getGamePlan().getFinalLocationName()));
        hra1.processCommand("jdi domov");
        assertEquals(hra1.getGamePlan().getFinalLocationName(), hra1.getGamePlan().getCurrentLocation().getName());
        assertEquals(true, hra1.getGamePlan().playerWon());
        assertEquals(true, hra1.isGameOver());
    }
}

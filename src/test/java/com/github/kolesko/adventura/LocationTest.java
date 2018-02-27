package com.github.kolesko.adventura;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.kolesko.adventura.model.*;

/*******************************************************************************
 * Testovací třída LokaceTest slouží ke komplexnímu otestování
 * třídy Lokace
 *
 * @author     Jarmila Pavlíčková, Jan Říha
 * @version    LS 2016/2017
 */
public class LocationTest
{
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
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {		
        Location lokace1 = new Location("hala", "vstupní hala budovy VŠE na Jižním městě");
        Location lokace2 = new Location("bufet", "bufet, kam si můžete zajít na svačinku");
        lokace1.addExit(lokace2);
        lokace2.addExit(lokace1);
        assertEquals(lokace2, lokace1.getExitLocation("bufet"));
        assertEquals(null, lokace2.getExitLocation("pokoj"));
    }

}

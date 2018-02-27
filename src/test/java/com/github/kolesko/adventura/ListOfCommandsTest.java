package com.github.kolesko.adventura;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.kolesko.adventura.model.*;

/*******************************************************************************
 * Testovací třída SeznamPrikazuTest slouží ke komplexnímu otestování třídy  
 * SeznamPrikazu
 * 
 * @author     Luboš Pavlíček, Jan Říha
 * @version    LS 2016/2017
 */
public class ListOfCommandsTest
{
    private Game hra;
    private CommandTerminate prKonec;
    private CommandMove prJdi;
    
    @Before
    public void setUp() {
        hra = new Game();
        prKonec = new CommandTerminate(hra);
        prJdi = new CommandMove(hra.getGamePlan());
    }

    @Test
    public void testVlozeniVybrani() {
        ListOfCommands seznPrikazu = new ListOfCommands();
        seznPrikazu.insertCommand(prKonec);
        seznPrikazu.insertCommand(prJdi);
        assertEquals(prKonec, seznPrikazu.getCommand("konec"));
        assertEquals(prJdi, seznPrikazu.getCommand("jdi"));
        assertEquals(null, seznPrikazu.getCommand("napoveda"));
    }
    @Test
    public void testJePlatnyPrikaz() {
        ListOfCommands seznPrikazu = new ListOfCommands();
        seznPrikazu.insertCommand(prKonec);
        seznPrikazu.insertCommand(prJdi);
        assertEquals(true, seznPrikazu.checkCommand("konec"));
        assertEquals(true, seznPrikazu.checkCommand("jdi"));
        assertEquals(false, seznPrikazu.checkCommand("napoveda"));
        assertEquals(false, seznPrikazu.checkCommand("Konec"));
    }
    
    @Test
    public void testNazvyPrikazu() {
        ListOfCommands seznPrikazu = new ListOfCommands();
        seznPrikazu.insertCommand(prKonec);
        seznPrikazu.insertCommand(prJdi);
        String nazvy = seznPrikazu.getCommandNames();
        assertEquals(true, nazvy.contains("konec"));
        assertEquals(true, nazvy.contains("jdi"));
        assertEquals(false, nazvy.contains("napoveda"));
        assertEquals(false, nazvy.contains("Konec"));
    }
    
}

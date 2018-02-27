package com.github.kolesko.adventura.model;


/**
 * Write a description of class Life here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Life
{
    // instance variables - replace the example below with your own
    private int maxLife;
    private int currentLife;

    /**
     * konstruktor vytvara na zaciatku zivot 10/10
     */
    public Life()
    {
        this.maxLife = 10;
        this.currentLife = 10;
    }

    /**
     * vracia maximalny zivot aky moze hrac mat
     *
     * 
     * @return    maxlife
     */
    public int getMaxLife()
    {
        return maxLife;
    }
    /**
     * vracia aktualny zivot hraca
     */
    public int getCurrentLife(){
        return currentLife;
    }
    /**
     * dekrementuje zivot hraca
     */
    public void decCurrentLife(){
        this.currentLife -= 1;
    }
    /**
     * inkrementuje zivot hraca o cislo
     * @param int o kolko chcem pridat ziot
     */
    public void incbynumberCurrentLife(int number) {
        this.currentLife += number;
    }
    /**
     * nastavenie zivota aky chcem mat
     * @param int cislo
     */
    public void setCurrentLife(int number) {
        this.currentLife = number;
    }
}

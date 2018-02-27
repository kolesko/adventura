package com.github.kolesko.adventura.model;



public class Item
{
    String name;
    String desc;
    boolean moveable;
    /**
     * konstruktor na vytvorenie predmetu
     * @param string meno
     * @param string popis
     * @param boolean ci s predmetom mozeme hybat
     */
    public Item(String name, String desc, boolean moveable){
        this.name = name;
        this.desc = desc;
        this.moveable = moveable;
    }
    /**
     * to iste ako prvy ale ked nenastavime hodnotu moveamble tak je automaticky false
     */
    public Item(String name, String desc){
        this.name = name;
        this.desc = desc;
        this.moveable = false;
    }
    /**
     * vracia nazov predmetu
     */
    public String getName() {
        return name;
    }
    /**
     * vracia popis predmetu
     */
    public String getDesc() {
        return desc;
    }
    /**
     * vracia ci sa s predmetom da hybat
     */
    public boolean isMoveable() {
        return moveable;
    }
    /**
     * nastavuje hodnotu moveable
     * @param boolean true/false
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
    
}

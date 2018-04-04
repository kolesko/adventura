/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kodovani: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;



/**
 * Instance třídy ItemCount představují ...
 *
 * @author (jméno autora)
 * @version (číslo verze nebo datum)
 */
public class ItemCount
{
    // == Datové atributy (statické i instancí) =====================
    private int count;
    private Item item;
    // == Konstruktory a tovární metody =============================

    public ItemCount(Item item,int count)
    {
        this.item = item;
        this.count = count;
    }

    public int getCount(){
        return count;
    }
    public Item getItem(){
        return item;
    }
    public void incCount() {
        count++;
    }
    public void decCount() {
        count--;
    }
    @Override
    	public String toString() {
    		if(count == 1) {
    			return item.getName();
    		} else return item.getName() + " (" + count + "x)"; 
    	}
    // == Nesoukromé metody (instancí i třídy) ======================

    // == Soukromé metody (instancí i třídy) ========================

}

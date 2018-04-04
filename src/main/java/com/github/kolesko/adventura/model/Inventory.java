package com.github.kolesko.adventura.model;
import java.util.Map;
import java.util.Observable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;



public class Inventory extends Observable
{
    private Map<String, ItemCount> items;
    private int storageSize;
    /**
     * konstruktor na vytvorenie inventara
     * nastavi sa zakladna velkost a vytvori polia pre predmety
     */
    public Inventory(){
        items = new HashMap<>();
        storageSize = 2;
    }
    /**
     * pridavanie predmetu do inventara
     * @param item, ktory chcem pridat
     */
    public void addItem(ItemCount item) {
        if (item != null) {
            if (!items.containsKey(item.getItem().getName())) {
                items.put(item.getItem().getName(),new ItemCount(item.getItem(),1));
            } else {
                items.get(item.getItem().getName()).incCount();
            } 
    }
        setChanged();
        notifyObservers();
    }
    /**
     * vymazavanie predmetu z inventara na zaklade mena(kluca)
     * @param string nazov predmetu
     * @return item ktory sa vymazal
     */
    public ItemCount removeItem(String itemName){
        if (items.get(itemName).getCount() > 1) {
                items.get(itemName).decCount();
                setChanged();
                notifyObservers();
                return items.get(itemName);
            } else {
            	ItemCount item = items.remove(itemName);
                setChanged();
                notifyObservers();
            	return item;
            }
    }
    public ItemCount removeItems(String itemName) {
    	 ItemCount item = items.remove(itemName);
         return item;
    }
    /**
     * zistuje ci sa nachadza predmet v inventari pomocou nazvu(kluca)
     * @return boolean hodnota ano/nie
     */
    public boolean containsItem(String itemName){
        return items.containsKey(itemName);
    }
    /**
     * vracia predmet ktory sa nachadza v inventari /pri prikaze vrat/
     * @return item ktory zistuje
     */
    public Item getContainsItem(String itemName) {
        if (items.get(itemName) != null) {
            return items.get(itemName).getItem();
        }
        return null;
    }
    public ItemCount getContainsItemCount(String itemName) {
        if (items.get(itemName) != null) {
            return items.get(itemName);
        }
        return null;
    }
    /**
     * vracia retazec so vsetkymi predmetami ktore sa nachadzaju v inventari
     */
    public String getInventoryItems() {
        String vracenyText = "predmety v inventari:";
        for (String item : items.keySet()) {
            if(items.get(item).getCount() > 1) {
                vracenyText += " " + item + "(" + items.get(item).getCount() + "x)";
            } else vracenyText += " " + item;
        }
        return vracenyText;
    }
    /**
     * vracia to iste co metoda nad ale bez komentara "predmety v inventari:"
     */
    public String getInventoryItems2() {
       String vracenyText = "";
       boolean first = true;
       for (String item : items.keySet()) {
            if (first) {
                vracenyText += item;
                first = false;
            } else vracenyText += " " + item;
       }
       return vracenyText; 
    }
    /**
     * vracia velkost inventara zakladna je 2 po zobrati batohu 11 (10 + batoh)
     */
    public int getStorageSize(){
        return storageSize;
    }
    /**
     * vracia pocet predmetov ktore sa nachadzaju v inventari
     */
    public int getActualSize() {
        int size = 0;
        for (String item : items.keySet()) {
            size += items.get(item).getCount();
        }
        return size;
    }
    /**
     * nastavuje hodnotu velkosti inventara
     */
    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }
    
    public Collection<ItemCount> getVeci() {
    	return Collections.unmodifiableCollection(items.values());
    }
}

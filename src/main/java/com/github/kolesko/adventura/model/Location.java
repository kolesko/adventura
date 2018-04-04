/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.kolesko.adventura.model;

import java.util.Collection;
import java.util.Observable;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


import java.util.Map;
import java.util.HashMap;

/**
 * Trida Lokace - popisuje jednotlivé lokace (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Lokace" reprezentuje jedno místo (místnost, prostor, ...) ve scénáři hry.
 * Lokace může mít sousední lokace připojené přes východy. Pro každý východ
 * si lokace ukládá odkaz na sousedící lokace.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha
 * @version    LS 2016/2017
 */
public class Location extends Observable{

    private String name;
    private String description;
    private Figure figure;
    private Set<Location> exits;   // obsahuje sousední lokace
    private Map<String, ItemCount> items;
    private double x;
	private double y;
    /**
     * Vytvoření lokace se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param    name nazev lokace, jednoznačný identifikátor, jedno slovo nebo víceslovný název bez mezer
     * @param    description Popis lokace
     */
    public Location(String name, String description, double x, double y) {
        this.name = name;
        this.description = description;
        exits = new HashSet<>();
        items = new HashMap<>();
        figure = null;
        this.x = x;
        this.y = y;
    }

    /**
     * Definuje východ z lokace (sousední/vedlejsi lokace). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední lokace uvedena
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední lokace).
     * Druhé zadání stejné lokace tiše přepíše předchozí zadání (neobjeví
     * se žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param    location lokace, která sousedi s aktualní lokací.
     *
     */
    public void addExit(Location location) {
        exits.add(location);
    }
    /**
     * Definuje postavu lokace. Vzhledem k tomu,
     * ze kazda lokacia moze mat len jednu postavu tak po pridani znova novej postavy sa stara prepise
     *
     * @param    figure postava, ktoru chceme priradit k lokacii.
     *
     */
    public void addFigure(Figure figure) {
        this.figure = figure;
    }
    /**
     * Pridava predmety do lokacie. Vzhledem k tomu,
     * ze pouzivame mapu tak kazdy predmet moze byt pridany len raz
     * (kluce sa nemozu opakovat)
     *
     * @param    item predmet, ktory chceme priradit k lokacii.
     *
     */
    public void addItem(ItemCount item) {
        if (item != null) {
            String itemName = item.getItem().getName();
            if (!items.containsKey(item.getItem().getName())) {
                items.put(itemName,item);
            } else {
            	items.get(item.getItem().getName()).incCount();
            }
        }
        setChanged();
        notifyObservers();
    }
    /**
     * Vymazuje predmet z lokacie ked sa tam nachadza
     *
     *
     * @param    string item, nazov predmetu ktory chceme vymazat z lokacie.
     * @return   vracia odkaz na predmet ktory vymazal
     */
    public ItemCount removeItem(String itemName){
        if (items.get(itemName).getCount() > 1) {
                items.get(itemName).decCount();
                return items.get(itemName);
            } else return items.remove(itemName);
    }
    /**
     * Zistuje ci sa predmet nachadza v lokacii
     *
     *
     * @param    string item, nazov predmetu ktoreho chceme zistit dostupnost.
     * @return   vracia true/false podla toho ci sa predmet nachadza
     */
    public boolean containsItem(String itemName){
        return items.containsKey(itemName);
    }
     /**
     * Zistuje ci sa predmet nachadza v lokacii
     *
     *
     * @param    string item, nazov predmetu ktoreho chceme zistit dostupnost.
     * @return   vracia odkaz na predmet ked sa tam nachadza
     */
    public Item getContainsItem(String itemName) {
        if (items.get(itemName) != null) {
            return items.get(itemName).getItem();
        }
        return null;
    }
    /**
     * zistuje kolko predmetov dokopy s jablkami sa nachadza v lokacii
     * @return   vracia pocet predmetov
     */
    public int getItemsSize() {
        return items.size();
    }
    /**
     * zistuje ci sa osoba nachadza v lokacii
     * @return   ak ano vrati odkaz na osobu
     */
    public Figure getFigure(String figureName) {
        if (figure.getName().equals(figureName)) {
            return figure;
        }
        return null;
    }
    
    public Figure getFigure2() {
    	return figure;
    }
    /**
     * Metoda equals pro porovnání dvou lokací. Překrývá se metoda equals ze
     * třídy Object. Dvě lokace jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    hodnotu true, pokud má zadaná lokace stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Location)) {
            return false;    // pokud parametr není typu Lokace, vrátíme false
        }
        // přetypujeme parametr na typ Lokace 
        Location druha = (Location) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.name, druha.name));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object.
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.name);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název lokace (byl zadán při vytváření lokace jako parametr
     * konstruktoru)
     *
     * @return    název lokace
     */
    public String getName() {
        return name;       
    }

    /**
     * Vrací "dlouhý" popis lokace, který může vypadat následovně: Jsi v
     * mistnosti/lokaci vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return    dlouhý popis lokace
     */
    public String getFullDescription() {
        if (items.isEmpty() && figure == null) {
            return "Jsi v mistnosti/lokaci " + description + ".\n"
                    + getExitNames();
        } 
        if(!items.isEmpty() && figure == null) 
                return "Jsi v mistnosti/lokaci " + description + ".\n"
                + getExitNames() + ".\n"
                + getItemNames();
        if (items.isEmpty() && figure != null) 
                return "Jsi v mistnosti/lokaci " + description + ".\n"
                + getExitNames() + ".\n"
                + getFigure();
        if (!items.isEmpty() && figure != null) 
                return "Jsi v mistnosti/lokaci " + description + ".\n"
                + getExitNames() + ".\n"
                + getItemNames() + ".\n"
                + getFigure();
                return "Jsi v mistnosti/lokaci " + description + ".\n"
                    + getExitNames() + ".\n"
                    + getItemNames() + ".\n"
                    + getFigure() +".";
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return    popis východů - názvů sousedních lokací
     */
    private String getExitNames() {
        String vracenyText = "vychody:";
        for (Location sousedni : exits) {
            vracenyText += " " + sousedni.getName();
        }
        return vracenyText;
    }
    /**
     * Vrací textový řetězec, který popisuje predmety v lokacii, například:
     * "predmety: kos ".
     *
     * @return    popis predmetov - názvů predmetu v lokací
     */
    private String getItemNames() {
        String vracenyText = "predmety:";
        for (String item : items.keySet()) {
            if(items.get(item).getCount() > 1) {
                vracenyText += " " + item + "(" + items.get(item).getCount() + "x)";
            } else vracenyText += " " + item;
        }
        return vracenyText;
    }
    /**
     * Vrací textový řetězec, který popisuje postavu v lokacii, například:
     * "postava: kuzelnik ".
     *
     * @return    popis postavy v lokacii
     */
    private String getFigure() {
        String vracenyText = "postava: ";
        vracenyText += figure.getName();
        return vracenyText;
    }
 
    /**
     * Vrací lokaci, která sousedí s aktuální lokací a jejíž název je zadán
     * jako parametr. Pokud lokace s udaným jménem nesousedí s aktuální
     * lokací, vrací se hodnota null.
     *
     * @param     locationName Jméno sousední lokace (východu)
     * @return    lokace, která se nachází za příslušným východem, nebo hodnota null, pokud lokace zadaného jména není sousedem.
     */
    public Location getExitLocation(String locationName) {
        List<Location>hledaneLokace = 
            exits.stream()
                   .filter(sousedni -> sousedni.getName().equals(locationName))
                   .collect(Collectors.toList());
        if(hledaneLokace.isEmpty()){
            return null;
        }
        else {
            return hledaneLokace.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující lokace, se kterými ta lokace sousedí.
     * Takto získaný seznam sousedních lokací nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Lokace.
     *
     * @return    nemodifikovatelná kolekce lokací (východů), se kterými tato lokace sousedí.
     */
    public Collection<Location> getExitLocations() {
        return Collections.unmodifiableCollection(exits);
    }
    
    public Collection<ItemCount> getVeci() {
    	return Collections.unmodifiableCollection(items.values());
    }
    
    public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return name;
	}
}

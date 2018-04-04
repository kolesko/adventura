package com.github.kolesko.adventura.model;
import java.util.*;

    
public class Figure extends Observable
{
    private String name;
    private String text;
    private String answer;
    private Map<String, Item> tasks;
    private Map<String, String> voicelines;
    private int tasksToComplete;
    private int talkTimes;
    private int completedTasks;
    private String currentTask;
    /**
     * konstruktor postavy vytvara novu postavu
     * 
     * @param string name - nazov postavy
     * @param string text - vychodzi text postavy
     * @param string answer - odpoved po splneni uloh
     */
    public Figure(String name, String text, String answer){
        this.name = name;
        this.text = text;
        this.answer = answer;
        this.tasks = new HashMap<>();
        this.voicelines = new HashMap<>();
        this.tasksToComplete = 0;
        this.talkTimes = 0;
        this.completedTasks = 0;
        this.currentTask = "";
    }
 
    /**
     * Vracia meno postavy
     * 
     */
    public String getName() {
        return name;
    }
    /**
     * vracia hlavny text postavy
     */
    public String getText() {
        return text;
    }
    /**
     * vracia odpoved postavy 
     */
    public String getAnswer() {
        return answer;
    }
    /**
     * vracia odkaz na ulohy postavy
     */
    public Map<String, Item> getTasks() {
        return tasks;
    }
    /**
     * vracia odkaz na text ktory hovori postava pred zadanim ulohy
     */
    public Map<String, String> getVoiceLines() {
        return voicelines;
    }
    /**
     * vracia pocet uloh ktore ma hrac splnit
     */
    public int getTasksToComplete() {
        return tasksToComplete;
    }
    /**
     * vracia pocet uloh ktore uz hrac splnil
     */
    public int getCompletedTasks() {
        return completedTasks;
    }
    /**
     * vracia pocet kolko krat hrac rozpraval s postavou
     */
    public int getTalkTimes() {
        return talkTimes;
    }
    /**
     * vracia ulohu ktoru zadal hracovi ako poslednu
     */
    public String getCurrentTask() {
    	/*setChanged();
        notifyObservers();*/
        return currentTask;
    }
    /**
     * nastavuje ulohu ktoru zadal ako poslednu
     * @param task
     */
    public void setCurrentTask(String task) {
        this.currentTask = task;
        setChanged();
        notifyObservers();
    }
    /**
     * inkrementuje pocet kolko krat hrac hovoril s postavou
     */
    public void incTalkTimes() {
        this.talkTimes += 1;
    }
    /**
     * 
     * ak to bolo nezmyselne tak to spat decrementuje /vyuziva sa pri zistovani ci ulohu uz splnil/
     */
    public void decTalkTimes() {
        this.talkTimes -= 1;
    }
    /**
     * incrementuje pocet splnenych uloh
     */
    public void incCompletedTasks() {
        this.completedTasks += 1;
        setChanged();
        notifyObservers();
    }
    /**
     * pridanie novej ulohy ku postave
     * @param string text ulohy
     * @param item predmet ktory chce pri tej ulohe
     */
    public void addTask(String text, Item item) {
        this.tasks.put(text, item);
        this.tasksToComplete += 1;
    }
    /**
     * pridanie textu ktory hovori
     * @param string kluc (task1,task2,atp..) na lahsie pracovanie s ulohami
     * @param string value text ulohy
     */
    public void addVoiceLine(String key, String value) {
        this.voicelines.put(key, value);
    }
}
package model.board;

import java.util.ArrayList;
import model.gem.*;

public abstract class Cell {
    private int location;
    private int numberOfGems;
    private ArrayList<Gem> gemList = new ArrayList<Gem>();

    public Cell(int location, int numberOfGems) {
        this.location = location;
        this.numberOfGems = numberOfGems;
    }

    public int getLocation() {
        return location;
    }

    public int getNumberOfGems() {
        return numberOfGems;
    }

    public ArrayList<Gem> getGemList() {
        return gemList;
    }

    public void addGem(Gem gem) {  // polymorphism for add or declare
        this.gemList.add(gem);
    }

    public boolean isEmpty() {
        return this.getGemList().size() == 0;
    }

    public void setEmpty(){
        this.gemList.clear();
    }

}
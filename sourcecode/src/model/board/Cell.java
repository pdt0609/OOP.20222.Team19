package model.board;


import java.util.ArrayList;
import java.util.List;

import model.gem.*;

public abstract class Cell {
    private int location;
    private int numberOfGems;
    private List<Gem> gemList = new ArrayList<Gem>();

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

    public List<Gem> getGemList() {
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

    public int getNumberOfBigGems(){
        int numberOfBigGems = 0;
        for (Gem gem : this.gemList){
            if (gem instanceof BigGem){
                numberOfBigGems++;
            }
        }
        return numberOfBigGems;
    }
    public int getNumberOfSmallGems(){
        int numberOfSmallGems = 0;
        for (Gem gem : this.gemList){
            if (gem instanceof SmallGem){
                numberOfSmallGems++;
            }
        }
        return numberOfSmallGems;
    }



}
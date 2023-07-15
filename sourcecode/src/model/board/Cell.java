package model.board;

import java.util.ArrayList;


public abstract class Cell {
    private int location;
    private int numberOfGems;

    public Cell(int location, int numberOfGems) {
        this.location = location;
        this.numberOfGems = numberOfGems;
    }

    public Cell(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public int getNumberOfGems() {
        return numberOfGems;
    }

    public void addGem(Gem gem) {
        this.gemList.add(gem);
    }

    public boolean isEmpty() {
        return this.getGemList().size() == 0;
    }

    public void setEmpty(){
        this.gemList.clear();
    }
    public String seeDetails() {
        StringBuffer gemDetails = new StringBuffer();
        for(Gem gem:this.gemList) {
            gemDetails.append(gem);
        }
        return(
                 "-"+"Cell " + this.getClass().getSimpleName() +
                        ", number of gems: " + this.getGemList().size() +
                        ", gem list: " + gemDetails+"\n"
                );
    }

}
package model.board;


import java.util.ArrayList;
import java.util.List;

import model.gem.*;

public abstract class Cell implements Cloneable{
    private int location;
    private int numberOfGems;
    private List<Gem> gemList = new ArrayList<Gem>();
    private boolean isEarnedCell = false;

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

    public Cell copyCell(){
        // Cell newCell = new Cell(cell.getLocation(), cell.getNumberOfGems()); -> Error because Cell is abstract
        // Cell newCell; can not do because newCell is not initialized from square, halfcircle, circle
        Cell newCell=null;
        
        if (this instanceof Square){
            newCell = new Square(this.getLocation(), this.getNumberOfGems());
              //upcasting
            for (int i=0; i<this.getNumberOfSmallGems();i++){
            newCell.addGem(new SmallGem());
            }
            return newCell;
        }
        else if (this instanceof HalfCircle){
            newCell = new HalfCircle(this.getLocation(), this.getNumberOfGems());
            for (int i=0; i<this.getNumberOfSmallGems();i++){
                newCell.addGem(new SmallGem());
            }
            for (int i=0; i<this.getNumberOfBigGems();i++){
                newCell.addGem(new BigGem());
            }
            return newCell;

        }
        return null;
    }

    public void setEarnedCell(){
        this.isEarnedCell = true;
    }

    public boolean isEarnedCell(){
        return this.isEarnedCell;
    }

   



}
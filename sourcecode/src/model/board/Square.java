package model.board;
public class Square extends Cell implements Pickable {
    public Square(int location) {
        super(location);
    }

    @Override
    public boolean pickable() { // force to implement this pickable method, and square only need one method, so I would like define pickable
        return true;
    }

}
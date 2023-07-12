package model.gem;

public abstract class Gem {
    public abstract int getValue();
}

public class BigGem extends Gem {
    private final int value = 5;

    public int getValue() {
        return value;
    }
}

public class SmallGem extends Gem {
    private final int value = 1;

    public int getValue() {
        return value;
    }
}

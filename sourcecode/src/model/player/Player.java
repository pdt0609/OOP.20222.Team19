package model.player;


public class Player {
    private int score;
    private String name;
    // private Board board; //association
    

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void computeScore(int earnedScore) {
        this.score += earnedScore;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return this.name.equals(player.name);
        }
        return false;
    }

}

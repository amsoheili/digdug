package ir.ac.kntu;

public class Player {
    private int x;
    private int y;
    private int hp;
    private int currentScore;
    private int maxScore;
    private GunType gunType;

    public Player(){
    }

    public Player(int x,int y,int hp,GunType gunType){
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.gunType = gunType;
        this.currentScore = 0;
    }




}

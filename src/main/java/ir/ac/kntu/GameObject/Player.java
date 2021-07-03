package ir.ac.kntu.GameObject;

public class Player extends GameObject{
    private int hp;
    private int currentScore;
    private int maxScore;
    private GunType gunType;

    public Player(){
    }

    public Player(int x,int y,int hp,GunType gunType){
        super(x,y);
        this.hp = hp;
        this.gunType = gunType;
        this.currentScore = 0;
    }




}

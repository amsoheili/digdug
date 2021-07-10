package ir.ac.kntu;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class PlayerInfo implements Serializable,Comparable<PlayerInfo> {
    private String name;
    private int numOfGames;
    private int highScore;
    private int lastScore;

    public PlayerInfo(String name){
        this.name = name;
        numOfGames = 0;
        highScore = 0;
    }

    public void win(int score){
        if (score > highScore){
            highScore = score;
        }
        numOfGames++;
    }

    public void lose(int score){
        if (score > highScore){
            highScore = score;
        }
        numOfGames++;
    }

    public int getLastScore(){
        return lastScore;
    }

    public int getHighScore(){
        return highScore;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Name: " + name + "  " +
                "Number of Games : "+ numOfGames + "  " +
                "HighScore : " + highScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        PlayerInfo that = (PlayerInfo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public int compareTo(@NotNull PlayerInfo o) {
        return lastScore-o.lastScore;
    }

}

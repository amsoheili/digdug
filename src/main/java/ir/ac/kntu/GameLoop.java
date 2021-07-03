package ir.ac.kntu;

import ir.ac.kntu.GameObject.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {
    private GridPane root;
    private Scene scene;
    private Stage stage;
    private ArrayList<PlayerInfo> players;
    private AnimationTimer animationTimer;
    private List<GameObject> gameObjects;

    public GameLoop(GridPane root,Scene scene,Stage stage,ArrayList<PlayerInfo> result){
        this.root = MapBuilder.createMap(root);
        this.scene = scene;
        this.stage = stage;
        players = result;
        gameObjects = new ArrayList<>();
        initGameObjects();
        setTimerAnimation();
    }

    public void initGameObjects(){
        int[][] mapData = MapBuilder.readMap().getMAP();
        for (int i=0;i<mapData.length;i++){
            for (int j=0;j<mapData[i].length;j++){
                if (mapData[i][j] == MapData.DESTRUCTIBLE_ROCK){
                    gameObjects.add(new DestructibleRock(i,j));
                }
                if (mapData[i][j] == MapData.INDESTRUCTIBLE_ROCK){
                    gameObjects.add(new IndestructibleRock(i,j));
                }
                if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.ORDINARY_BALLOON_AREA)
                        || (mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.ORDINARY_BALLOON_AREA)){
                    gameObjects.add(new Balloon(i,j,BalloonType.ORDINARY));
                }
                if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.DRAGON_BALLOON_AREA)
                        || (mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.DRAGON_BALLOON_AREA)){
                    gameObjects.add(new Balloon(i,j,BalloonType.DRAGON));
                }
            }
        }
    }
    public void setTimerAnimation(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkCollide();
            }
        };
    }

    private void checkCollide(){
        for (int i=0;i<gameObjects.size();i++){
            for(int j=i+1;j<gameObjects.size();j++){
                if(gameObjects.get(i).isColliding(gameObjects.get(j))){
                    gameObjects.get(i).collide(gameObjects.get(j));
                    gameObjects.get(j).collide(gameObjects.get(i));
                }
            }
        }
    }

    public void startGame(){

    }
}

package ir.ac.kntu;

import ir.ac.kntu.GameObject.*;
import ir.ac.kntu.KeyBoard.KeyListener;
import ir.ac.kntu.KeyBoard.KeyLogger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    private KeyLogger keyLogger;

    public GameLoop(GridPane root,Scene scene,Stage stage,ArrayList<PlayerInfo> result){
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        players = result;
        gameObjects = new ArrayList<>();
        keyLogger = new KeyLogger(scene,gameObjects);
        initBackground();
        initGameObjects();
        setTimerAnimation();
    }

    public void initBackground(){
        Background background = new Background(new BackgroundFill(Color.rgb(48,100,144), CornerRadii.EMPTY, Insets.EMPTY));
        root.setBackground(background);
    }

    public void initGameObjects(){
        int[][] mapData = MapBuilder.readMap().getMAP();
        for (int i=0;i<mapData.length;i++){
            for (int j=0;j<mapData[i].length;j++){
                if (mapData[i][j] == MapData.PLAYER){
                    gameObjects.add(new Player(i,j,3,ObjectDirection.RIGHT,PlayerState.STANDING,1,1));
                }
                if (mapData[i][j] == MapData.DESTRUCTIBLE_ROCK){
                    gameObjects.add(new DestructibleRock(i,j));
                }
                if (mapData[i][j] == MapData.INDESTRUCTIBLE_ROCK){
                    gameObjects.add(new IndestructibleRock(i,j));
                }
                if (mapData[i][j] == MapData.ORDINARY_BALLOON_AREA){
                    if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.ORDINARY_BALLOON_AREA)){
                        gameObjects.add(new Balloon(i,j,BalloonType.ORDINARY,ObjectDirection.DOWN,BalloonState.MOVING,1,0));
                    }
                    if((mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.ORDINARY_BALLOON_AREA)){
                        gameObjects.add(new Balloon(i,j,BalloonType.ORDINARY,ObjectDirection.RIGHT,BalloonState.MOVING,0,1));
                    }
                }
                if (mapData[i][j] == MapData.DRAGON_BALLOON_AREA){
                    if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.DRAGON_BALLOON_AREA)){
                        gameObjects.add(new Balloon(i,j,BalloonType.DRAGON,ObjectDirection.DOWN,BalloonState.MOVING,1,0));
                    }
                    if ((mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.DRAGON_BALLOON_AREA)){
                        gameObjects.add(new Balloon(i,j,BalloonType.DRAGON,ObjectDirection.RIGHT,BalloonState.MOVING,0,1));
                    }
                }
            }
        }
    }

    public void setTimerAnimation(){
        animationTimer = new AnimationTimer() {
            private int count=0;
            @Override
            public void handle(long l) {
//                if(count<5){
//                    root.getChildren().clear();
//                    count++;
//                    TextField textField=new TextField((String.valueOf(count)));
//                    textField.setId("textField");
//                    root.add(textField,0,0);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return;
//                }
                try{
                   Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                checkCollide();
                checkAndFall();
                clean();
                moveBalloons();
                addGameObjectsToRoot();
            }
        };
    }

    public void checkAndFall(){
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof IndestructibleRock && isEmptyBeneath(gameObjects.get(i))){
                ((IndestructibleRock)(gameObjects.get(i))).fall();
            }
        }
    }

    public boolean isEmptyBeneath(GameObject gameObject){
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof DestructibleRock){
                if ((gameObjects.get(i).getRowIndex() == gameObject.getRowIndex()+1)
                        && (gameObjects.get(i).getColumnIndex() == gameObject.getColumnIndex())){
                    return false;
                }
            }
        }
        return true;
    }

    public void clean(){
        for (int i=0 ;i< gameObjects.size();i++){
            if (!gameObjects.get(i).isAlive()){
                gameObjects.remove(i);
            }
        }
    }

    public void moveBalloons(){
        for (int i=0;i<gameObjects.size();i++){
            gameObjects.get(i).move();
        }
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

    public void addGameObjectsToRoot(){
        root.getChildren().clear();
        //System.out.println("A");
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof KeyListener){
                keyLogger.addListener((KeyListener)gameObjects.get(i));
            }
            if(!gameObjects.get(i).isVisible()){
                continue;
            }
            root.add(gameObjects.get(i).getImage(),gameObjects.get(i).getColumnIndex(),gameObjects.get(i).getRowIndex());
        }
    }

    public void startGame(){
        animationTimer.start();
    }
}

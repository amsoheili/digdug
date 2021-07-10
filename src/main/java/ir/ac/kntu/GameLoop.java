package ir.ac.kntu;

import ir.ac.kntu.GameObject.*;
import ir.ac.kntu.GameObject.Timer;
import ir.ac.kntu.KeyBoard.KeyListener;
import ir.ac.kntu.KeyBoard.KeyLogger;
import ir.ac.kntu.Map.MapBuilder;
import ir.ac.kntu.Map.MapData;
import ir.ac.kntu.PlayerDAO.BinaryPlayerDAO;
import ir.ac.kntu.PlayerDAO.PlayerDAO;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLoop {
    private GridPane root;
    private Scene scene;
    private Stage stage;
    private ArrayList<PlayerInfo> players;
    private AnimationTimer animationTimer;
    private List<GameObject> gameObjects;
    private KeyLogger keyLogger;
    private long startTime;
    private Timer timer;
    private ScheduledExecutorService timerThread;
    private int numOfPlayers;
    private int numOfEnemies;
    private RandomObjects randomObjects;
    private boolean end;

    public GameLoop(GridPane root,Scene scene,Stage stage,ArrayList<PlayerInfo> players){
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        this.players = players;
        gameObjects = new ArrayList<>();
        initBackground();
        initGameObjects();
        keyLogger = new KeyLogger(scene,gameObjects);
        setTimerAnimation();
        setNumOfPlayersAndEnemies();
        randomObjects = new RandomObjects(gameObjects);
    }

    public void setNumOfPlayersAndEnemies(){
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof Balloon){
                numOfEnemies++;
            }
            if (gameObjects.get(i) instanceof Player){
                numOfPlayers++;
            }
        }
    }

    public void initBackground(){
        Background background = new Background(new BackgroundFill(Color.rgb(48,100,144), CornerRadii.EMPTY, Insets.EMPTY));
        root.setBackground(background);
        root.setAlignment(Pos.CENTER_LEFT);
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
                if (getNumOfPlayers() <= 0){
                    System.out.println("all players died");
                    endGame();
                    return;
                }
                if (numOfEnemies <= 0){
                    System.out.println("all enemies died");
                    endGame();
                    return;
                }
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
        for (int i=0;i< gameObjects.size();i++){
            if (!gameObjects.get(i).isAlive()){
                if (gameObjects.get(i) instanceof Player){
                    numOfPlayers--;
                }
                if (gameObjects.get(i) instanceof Balloon){
                    numOfEnemies--;
                }
                gameObjects.remove(i);
            }
        }
    }

    public void moveBalloons(){
        Random rd = new Random();
        for (int i=0;i<gameObjects.size();i++){
            gameObjects.get(i).move();
            if (gameObjects.get(i) instanceof Balloon){
                if (((Balloon)gameObjects.get(i)).getType() == BalloonType.DRAGON){
                    if (rd.nextBoolean()){
                        ((Balloon)gameObjects.get(i)).fire(gameObjects);
                    }
                }
            }
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
        setStopAndResume();
        Label label = new Label(getTimer().toString());
        label.setMinWidth(150);
        label.setStyle("-fx-font-size:12;");
        label.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        root.add(label,MapData.GRID_SIZE_X+1,1);
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof KeyListener){
                keyLogger.addListener((KeyListener)gameObjects.get(i));
            }
            if(!gameObjects.get(i).isVisible()){
                continue;
            }
            if (gameObjects.get(i) instanceof Player){
                Player player = (Player)gameObjects.get(i);
                for (int j=1;j<=player.getHp();j++){
                    removeRock(0,MapData.GRID_SIZE_X-j);
                    Flower flower = new Flower(0,MapData.GRID_SIZE_X-j);
                    root.add(flower.getImage(), flower.getColumnIndex(),flower.getRowIndex());
                }
            }
            root.add(gameObjects.get(i).getImage(),gameObjects.get(i).getColumnIndex(),gameObjects.get(i).getRowIndex());
        }
    }

    public void removeRock(int rowIndex,int columnIndex){
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i).getRowIndex() == rowIndex &&
                gameObjects.get(i).getColumnIndex() == columnIndex){
                gameObjects.remove(i);
            }
        }
    }

    public void setStopAndResume(){
        Label stop = new Label("STOP GAME");
        stop.setOnMouseClicked(e->{
            animationTimer.stop();
        });
        root.add(stop,MapData.GRID_SIZE_X+1,2);
        Label resume = new Label("Resume GAME");
        resume.setOnMouseClicked(e->{
            animationTimer.start();
        });
        root.add(resume,MapData.GRID_SIZE_X+1,3);
    }

    public void startGame(){
        animationTimer.start();
        setTimer();
        randomObjects.start();
    }

    public void endGame(){
        animationTimer.stop();
        timerThread.shutdown();
        keyLogger.removeAllListeners();
        randomObjects.stop();
        List<Player> players = getPlayers();
        root.getChildren().clear();
        if (players.size() != 0){
            //players.get(0).win();
        }
        PlayerDAO playerDAO = new BinaryPlayerDAO();
        playerDAO.saveAllPlayers(this.players);
        showRanks();
    }

    private void showRanks() {
        root.getChildren().clear();
        Collections.sort(players);
        ListView listView=new ListView();
        listView.setPrefWidth(500);
        listView.setPrefHeight(600);
        listView.getStyleClass().add("listView");
        int i=0;
        for (PlayerInfo player : players){
            i++;
            listView.getItems().add(i+". "+player.getName()+"             "+player.getLastScore());
        }
        root.add(listView,0,0);
        Button button=new Button("BACK");
        button.setOnAction(e->{
            Menu menu=new Menu(stage,scene,root);
        });
        root.add(button,1,0);
    }



    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        for (int i=0;i< gameObjects.size();i++){
            if (gameObjects.get(i) instanceof Player){
                players.add((Player) gameObjects.get(i));
            }
        }
        return players;
    }

    public void setTimer(){
        timerThread = Executors.newScheduledThreadPool(1);
        this.timer = new Timer();
        timerThread.scheduleAtFixedRate(timer,1,1, TimeUnit.SECONDS);
    }

    public Timer getTimer(){
        return timer;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }
}

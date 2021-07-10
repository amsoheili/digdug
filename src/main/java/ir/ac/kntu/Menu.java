package ir.ac.kntu;

import ir.ac.kntu.PlayerDAO.BinaryPlayerDAO;
import ir.ac.kntu.PlayerDAO.PlayerDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menu {
    private PlayerDAO playerDAO;
    private GridPane root;
    private Scene scene;
    private Stage stage;

    public Menu(Stage stage, Scene scene, GridPane root){
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        playerDAO = new BinaryPlayerDAO();
        drawMenu();
    }

    public void drawMenu(){
        Button newGame = new Button("NEW GAME");
        Button exit = new Button("EXIT");
        Background background =  getBackGround(new File("src/main/resources/Background1.png"));
        root.setBackground(background);
        editMenu(stage,scene,root,newGame,exit);
    }

    public Background getBackGround(File file){
        BackgroundFill backgroundFill= null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

    public void editMenu(Stage stage, Scene scene, GridPane root,Button newGame,Button exit){
        newGame.setOnAction(e-> {
            setPlayer();
        });
        exit.setOnAction(e->{
            stage.close();
        });
        root.setAlignment(Pos.CENTER);
        root.getChildren().clear();
        newGame.setMaxWidth(200);
        newGame.setStyle("-fx-font-size:20;");
        exit.setMaxWidth(200);
        exit.setStyle("-fx-font-size:20;");
        root.add(newGame,0,0);
        root.add(exit,0,1);
    }

    public void setPlayer(){
        root.getChildren().clear();
        ArrayList<PlayerInfo> players = playerDAO.getAllPlayers();
//        players.add(new PlayerInfo("player1"));
//        players.add(new PlayerInfo("Player2"));
        ListView listView=new ListView();
        listView.getItems().addAll(players);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ArrayList<PlayerInfo> result=new ArrayList<>();
        listView.setOnMouseClicked(e->{
            if(result.size()==2) {
                result.remove(result.size() - 1);
            }
            System.out.println("clicked");
            PlayerInfo playerInfo=(PlayerInfo) listView.getSelectionModel().getSelectedItem();
            if(!result.contains(playerInfo)&&playerInfo!=null){
                result.add(playerInfo);
            }
        });
        listView.setPrefHeight(300);
        listView.setPrefWidth(300);
        Button submit = new Button("SUBMIT");
        Button addNewPlayer = new Button("ADD NEW PLAYER");
        submit.setOnAction(e->{
            System.out.println("Hal");
            GameLoop game = new GameLoop(root,scene,stage,result);
            game.startGame();
        });
        addNewPlayer.setOnAction(e->{
            addNewPlayer(listView);
        });

        root.add(listView,0,0);
        root.add(addNewPlayer,0,1);
        root.add(submit,0,2);
        System.out.println(players);
    }

    public void addNewPlayer(ListView listView){
        Stage stage1=new Stage();
        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Label label=new Label("Name");
        TextField name=new TextField();
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        Button button=new Button("Save");
        button.setOnAction(e->{
            if(!name.getText().isEmpty()) {
                listView.getItems().add(new PlayerInfo(name.getText()));
                stage1.close();
            }
        });
        gridPane.add(label,0,0);
        gridPane.add(name,0,1);
        gridPane.add(button,0,2);
        stage1.setTitle("add new player");
        stage1.setScene(new Scene(gridPane,300,300,Color.BLUE));
        stage1.show();
    }
}

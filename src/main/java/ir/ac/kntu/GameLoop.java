package ir.ac.kntu;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameLoop {
    private GridPane root;
    private Scene scene;
    private Stage stage;
    private ArrayList<PlayerInfo> players;

    public GameLoop(GridPane root,Scene scene,Stage stage,ArrayList<PlayerInfo> result){
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        players = result;
    }

    public void startGame(){

    }
}

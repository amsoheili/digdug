package ir.ac.kntu.KeyBoard;

import ir.ac.kntu.GameObject.GameObject;
import javafx.scene.input.KeyEvent;

import java.util.List;

public interface KeyListener {
    public void notify(KeyEvent keyEvent, List<GameObject> gameObjects);
}


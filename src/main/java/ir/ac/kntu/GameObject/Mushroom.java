package ir.ac.kntu.GameObject;

import javafx.scene.image.ImageView;

public class Mushroom extends GameObject{

    public Mushroom(int rowIndex,int columnIndex){
        super(rowIndex,columnIndex,0,0);
        setImage(setImageHelper("assets/mushroom.png"));
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Player){
            setAlive(false);
        }
    }
}

package ir.ac.kntu.GameObject;

import ir.ac.kntu.GameLoop;
import javafx.scene.image.ImageView;

public class IndestructibleRock extends GameObject {

    public IndestructibleRock(int x,int y){
        super(x,y,0,0);
        setImage(setImageHelper("Cropped_Images/Indestructible_Rock.png"));
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Player){
            gameObject.setAlive(false);
        }
    }

    public void fall(){
        setRowIndex(getRowIndex()+1);
    }

}

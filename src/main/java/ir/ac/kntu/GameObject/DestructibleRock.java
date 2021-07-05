package ir.ac.kntu.GameObject;

import javafx.scene.image.ImageView;

public class DestructibleRock extends GameObject{

    public DestructibleRock(int x,int y){
        super(x,y,0,0);
        setImage(setImageHelper("Cropped_Images/Destructible_Rock.png"));
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Player){
            setAlive(false);
        }
    }

}

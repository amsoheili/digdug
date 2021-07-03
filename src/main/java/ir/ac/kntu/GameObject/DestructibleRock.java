package ir.ac.kntu.GameObject;

import javafx.scene.image.ImageView;

public class DestructibleRock extends GameObject{

    public DestructibleRock(int x,int y){
        super(x,y);
        setImage(setImageHelper("Cropped_Images/Destructible_Rock.png"));
    }


}

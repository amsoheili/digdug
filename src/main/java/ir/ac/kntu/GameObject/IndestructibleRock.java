package ir.ac.kntu.GameObject;

import ir.ac.kntu.GameLoop;
import javafx.scene.image.ImageView;

public class IndestructibleRock extends GameObject {

    public IndestructibleRock(int x,int y){
        super(x,y);
        setImage(setImageHelper("Cropped_Images/Indestructible_Rock.png"));
    }

}
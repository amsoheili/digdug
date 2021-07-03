package ir.ac.kntu.GameObject;

import ir.ac.kntu.MapData;
import javafx.scene.image.ImageView;

public class GameObject {
    private ImageView image;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private boolean isAlive;
    private boolean isVisible;

    public GameObject(){

    }

    public GameObject(int x,int y){
        this.x = x;
        this.y = y;
        isAlive = true;
        isVisible = true;
    }

    public void collide(GameObject gameObject){

    }

    public void setImage(ImageView image){
        this.image = image;
    }

    public ImageView getImage(){
        return image;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isColliding(GameObject gameObject){
        return (getX()==gameObject.getX())&&(getY()==gameObject.getY());
    }


    public ImageView setImageHelper(String path){
        ImageView temp = new ImageView(path);
        temp.setFitHeight(MapData.GRID_SCALE);
        temp.setFitWidth(MapData.GRID_SCALE);
        return temp;
    }
}

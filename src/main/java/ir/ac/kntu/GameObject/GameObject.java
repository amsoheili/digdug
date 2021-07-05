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

    public GameObject(int x,int y,int xSpeed,int ySpeed){
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void move(){
        setX(getX()+getXSpeed());
        setY(getY()+getYSpeed());
    }

    public boolean isColliding(GameObject gameObject){
        return (getX()==gameObject.getX())&&(getY()==gameObject.getY());
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public ImageView setImageHelper(String path){
        ImageView temp = new ImageView(path);
        temp.setFitHeight(MapData.GRID_SCALE);
        temp.setFitWidth(MapData.GRID_SCALE);
        return temp;
    }
}

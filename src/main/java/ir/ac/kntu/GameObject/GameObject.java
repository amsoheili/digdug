package ir.ac.kntu.GameObject;

import ir.ac.kntu.Map.MapData;
import javafx.scene.image.ImageView;

public class GameObject {
    private ImageView image;
    private int rowIndex;
    private int columnIndex;
    private int xSpeed;
    private int ySpeed;
    private boolean isAlive;
    private boolean isVisible;

    public GameObject(){

    }

    public GameObject(int rowIndex,int columnIndex,int xSpeed,int ySpeed){
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
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

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
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
        setRowIndex(getRowIndex()+getXSpeed());
        setColumnIndex(getColumnIndex()+getYSpeed());
    }

    public boolean isColliding(GameObject gameObject){
        return (getRowIndex()==gameObject.getRowIndex())&&(getColumnIndex()==gameObject.getColumnIndex());
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

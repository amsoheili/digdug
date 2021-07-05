package ir.ac.kntu.GameObject;

import ir.ac.kntu.MapData;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Bullet extends GameObject{
    private ObjectDirection direction;
    private int bulletType;  // 1-> AirGun   2->Sniper

    public Bullet(int x,int y,ObjectDirection direction){
        super(x,y,0,0);
        bulletType = 1;
        this.direction = direction;
        setImage();
        setLifeTime();
    }

    public void setLifeTime(){
        Runnable x = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(250);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                setAlive(false);
            }
        };
        Thread thread1 = new Thread(x);
        thread1.start();
    }

    public void setImage(){
        ImageView image;
        if (direction == ObjectDirection.RIGHT && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            setImage(image);
        }
//        if (direction == ObjectDirection.RIGHT && bulletType == 2){
//            image = new ImageView("Cropped_Images/Bullet_Right.png");
//            image.setFitWidth(5*MapData.GRID_SCALE);
//            setImage(image);
//        }
        if (direction == ObjectDirection.LEFT && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(180);
            setImage(image);
        }
//        if (direction == ObjectDirection.LEFT && bulletType == 2){
//            image = new ImageView("Cropped_Images/Bullet_Right.png");
//            image.setFitHeight(5*MapData.GRID_SCALE);
//            image.setRotate(180);
//            setImage(image);
//        }
        if (direction == ObjectDirection.UP && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(-90);
            setImage(image);
        }
//        if (direction == ObjectDirection.UP && bulletType == 2){
//            image = new ImageView("Cropped_Images/Bullet_Right.png");
//            image.setFitHeight(5*MapData.GRID_SCALE);
//            image.setRotate(-90);
//            setImage(image);
//        }
        if (direction == ObjectDirection.DOWN && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(90);
            setImage(image);
        }
//        if (direction == ObjectDirection.DOWN && bulletType == 2){
//            image = new ImageView("Cropped_Images/Bullet_Right.png");
//            image.setFitHeight(5*MapData.GRID_SCALE);
//            image.setRotate(90);
//            setImage(image);
//        }
    }
}

package ir.ac.kntu.GameObject;

import javafx.scene.image.ImageView;

public class Flame extends GameObject{

    private ObjectDirection direction;

    public Flame(int rowIndex,int columnIndex,ObjectDirection direction){
        super(rowIndex,columnIndex,0,0);
        this.direction = direction;
        setImage();
        setLifeTime();
    }

    public void setLifeTime(){
        Runnable x = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(50);
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
        if (direction == ObjectDirection.RIGHT){
            setImage(setImageHelper("Cropped_Images/Dragon_Flame_Right.png"));
        }
        if (direction == ObjectDirection.LEFT){
            setImage(setImageHelper("Cropped_Images/Dragon_Flame_Left.png"));
        }
        if (direction == ObjectDirection.UP){
            image = setImageHelper("Cropped_Images/Dragon_Flame_Right.png");
            image.setRotate(-90);
            setImage(image);
        }
        if (direction == ObjectDirection.DOWN){
            image = setImageHelper("Cropped_Images/Dragon_Flame_Right.png");
            image.setRotate(90);
            setImage(image);
        }

    }
}

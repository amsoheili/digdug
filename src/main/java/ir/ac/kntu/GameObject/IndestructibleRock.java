package ir.ac.kntu.GameObject;

import ir.ac.kntu.GameLoop;
import javafx.scene.image.ImageView;

public class IndestructibleRock extends GameObject {
    private int lastRowIndex;

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
        //setLastRowIndex();
        setLastRowIndex(getRowIndex());
        setRowIndex(getRowIndex()+1);
    }

    public int getLastRowIndex() {
        return lastRowIndex;
    }

    public void setLastRowIndex(int lastRowIndex){
        this.lastRowIndex = lastRowIndex;
    }

    public void setLastRowIndex(){
        Runnable lastIndexSetter = new Runnable() {
            @Override
            public void run() {
                setLastRowIndex(getRowIndex());
                //System.out.println(getRowIndex());
                try{
                    Thread.sleep(400);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //System.out.println("B");
                //setLastRowIndex(-1);
            }
        };
        Thread thread = new Thread(lastIndexSetter);
        thread.start();
    }
}

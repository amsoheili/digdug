package ir.ac.kntu.GameObject;

import javafx.scene.image.ImageView;

public class Bullet extends GameObject{
    private ObjectDirection direction;
    private int bulletType;  // 1-> AirGun   2->Sniper
    private boolean hitOrdinaryBalloon;
    private boolean hitDragonBalloon;

    public Bullet(){
        super();
    }

    public Bullet(int x,int y,ObjectDirection direction){
        super(x,y,0,0);
        bulletType = 1;
        this.direction = direction;
        hitDragonBalloon = false;
        hitOrdinaryBalloon = false;
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
        if (direction == ObjectDirection.LEFT && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(180);
            setImage(image);
        }
        if (direction == ObjectDirection.UP && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(-90);
            setImage(image);
        }
        if (direction == ObjectDirection.DOWN && bulletType == 1){
            image = setImageHelper("Cropped_Images/Bullet_Right.png");
            image.setRotate(90);
            setImage(image);
        }
    }

    public boolean isHitOrdinaryBalloon() {
        return hitOrdinaryBalloon;
    }

    public boolean isHitDragonBalloon() {
        return hitDragonBalloon;
    }

    public void setHitOrdinaryBalloon(boolean hitOrdinaryBalloon){
        this.hitOrdinaryBalloon = hitOrdinaryBalloon;
    }

    public void setHitDragonBalloon(boolean hitDragonBalloon){
        this.hitDragonBalloon = hitDragonBalloon;
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Balloon){
            System.out.println("collide bullet - balloon");
            if (((Balloon)gameObject).getType() == BalloonType.ORDINARY){
                System.out.println("collide bullet - balloon2");
                setHitOrdinaryBalloon(true);
            }else {
                setHitDragonBalloon(true);
            }
        }
    }
}

package ir.ac.kntu.GameObject;

import ir.ac.kntu.MapData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Balloon extends GameObject{
    private BalloonType type;
    private ObjectDirection direction;
    private BalloonState balloonState;

    public Balloon(int x,int y,BalloonType type,ObjectDirection direction,BalloonState balloonState,
                   int xSpeed,int ySpeed){
        super(x,y,xSpeed,ySpeed);
        this.type = type;
        this.direction = direction;
        this.balloonState = balloonState;
        setImage();
    }

    public void setImage(){
        ImageView image;
        if (type == BalloonType.ORDINARY && (direction == ObjectDirection.UP) &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/Ordinary_Balloon1.png");
            image.setRotate(90);
            super.setImage(image);
        }
        if (type == BalloonType.ORDINARY && (direction == ObjectDirection.RIGHT) &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/Ordinary_Balloon1.png");
            super.setImage(image);
        }
        if (type == BalloonType.ORDINARY && direction == ObjectDirection.DOWN &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/Ordinary_Balloon1.png");
            image.setRotate(-90);
            super.setImage(image);
        }
        if (type == BalloonType.ORDINARY && direction == ObjectDirection.LEFT &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/Ordinary_Balloon0.png");
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.RIGHT &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.UP &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            image.setRotate(-90);
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.DOWN &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            image.setRotate(90);
            super.setImage(image);
        }

        if (type == BalloonType.DRAGON && direction == ObjectDirection.LEFT &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon3.png");
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.RIGHT &&
                balloonState == BalloonState.FIRING){
            image = setImageHelper("Cropped_Images/DragonBalloon6.png");
            image.setFitWidth(2*MapData.GRID_SCALE);
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.UP &&
                balloonState == BalloonState.FIRING){
            image = setImageHelper("Cropped_Images/DragonBalloon6.png");
            image.setFitHeight(2*MapData.GRID_SCALE);
            image.setRotate(90);
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.DOWN &&
                balloonState == BalloonState.FIRING){
            image = setImageHelper("Cropped_Images/DragonBalloon5.png");
            image.setFitHeight(2*MapData.GRID_SCALE);
            image.setRotate(-90);
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.LEFT &&
                balloonState == BalloonState.FIRING){
            image = setImageHelper("Cropped_Images/DragonBalloon5.png");
            image.setFitWidth(2*MapData.GRID_SCALE);
            super.setImage(image);
        }

    }

    public ImageView setImageHelper(String path){
        ImageView temp = new ImageView(path);
        temp.setFitHeight(MapData.GRID_SCALE);
        temp.setFitWidth(MapData.GRID_SCALE);
        return temp;
    }

    public void setType(BalloonType type){
        this.type = type;
    }

    public void setDirection(ObjectDirection direction){
        this.direction = direction;
    }

    public void setBalloonState(BalloonState balloonState){
        this.balloonState = balloonState;
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof DestructibleRock){
            setXSpeed(-1*getXSpeed());
            setYSpeed(-1*getYSpeed());
            if (direction == ObjectDirection.RIGHT){
                setDirection(ObjectDirection.LEFT);
            }else if (direction == ObjectDirection.LEFT){
                setDirection(ObjectDirection.RIGHT);
            }
            if (direction == ObjectDirection.UP){
                setDirection(ObjectDirection.DOWN);
            }else if (direction == ObjectDirection.DOWN){
                setDirection(ObjectDirection.UP);
            }
        }
        if (gameObject instanceof IndestructibleRock){
            if (  ((IndestructibleRock)gameObject).getLastRowIndex() + 1 == getRowIndex()){
                System.out.println("A");
                setBalloonState(BalloonState.EXPLODING);
                explode();
            }
        }

        if (gameObject instanceof Bullet){
            setBalloonState(BalloonState.EXPLODING);
            explode();
        }
        if (gameObject instanceof Player && (getBalloonState() != BalloonState.EXPLODING)){
            gameObject.setAlive(false);
            gameObject.setVisible(false);
        }
    }

    public void explode(){
        if (type == BalloonType.ORDINARY && balloonState == BalloonState.EXPLODING) {
            explodeOrdinaryBalloon();
        }else if (type == BalloonType.DRAGON && balloonState == BalloonState.EXPLODING){
            explodeDragonBalloon();
        }
    }

    public void explodeDragonBalloon(){
        setXSpeed(0);
        setYSpeed(0);
        Runnable exploding = new Runnable() {
            int step=1;
            @Override
            public void run() {
                if (step == 1){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon0.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 2){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon1.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 3){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon7.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 4){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon2.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 5){
                    setAlive(false);
                    setVisible(false);
                }
            }
        };
        Thread thread = new Thread(exploding);
        thread.start();
    }

    public void explodeOrdinaryBalloon(){
        setXSpeed(0);
        setYSpeed(0);
        Runnable exploding = new Runnable() {
            int step=1;
            @Override
            public void run() {
                if (step == 1){
                    setImage(setImageHelper("Cropped_Images/Ordinary_Balloon4.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 2){
                    setImage(setImageHelper("Cropped_Images/Ordinary_Balloon5.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 3){
                    setImage(setImageHelper("Cropped_Images/Ordinary_Balloon2.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 4){
                    setImage(setImageHelper("Cropped_Images/Ordinary_Balloon3.png"));
                    try{
                        Thread.sleep(800);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    step++;
                }
                if (step == 5){
                    setAlive(false);
                    setVisible(false);
                }
            }
        };
        Thread thread = new Thread(exploding);
        thread.start();
    }
    @Override
    public void move(){
        setRowIndex(getRowIndex()+getXSpeed());
        setColumnIndex(getColumnIndex()+getYSpeed());
        setImage();
    }

    public BalloonState getBalloonState() {
        return balloonState;
    }
}

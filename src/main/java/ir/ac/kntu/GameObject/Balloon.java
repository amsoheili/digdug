package ir.ac.kntu.GameObject;

import ir.ac.kntu.Map.MapData;
import javafx.scene.image.ImageView;

import java.util.List;

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
        ImageView image = new ImageView();
        setImageOrdinaryBalloon(image);
        setImageDragonBalloon(image);
    }

    public void setImageOrdinaryBalloon(ImageView image){
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
    }

    public void setImageDragonBalloon(ImageView image){
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
    }

    public BalloonType getType(){
        return type;
    }

    public ObjectDirection getDirection(){
        return direction;
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
    }

    public void explode(){
        if (type == BalloonType.ORDINARY && balloonState == BalloonState.EXPLODING) {
            setXSpeed(0);
            setYSpeed(0);
            explodeOrdinaryBalloon();
        }else if (type == BalloonType.DRAGON && balloonState == BalloonState.EXPLODING){
            explodeDragonBalloon();
        }
    }

    public void explodeDragonBalloon(){
        setXSpeed(0);
        setYSpeed(0);
        Runnable exploding = new Runnable() {
            private int step=1;
            @Override
            public void run() {
                if (step == 1){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon0.png"));
                    threadSleeper(Thread.currentThread());
                    step++;
                }
                if (step == 2){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon1.png"));
                    threadSleeper(Thread.currentThread());
                    step++;
                }
                if (step == 3){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon7.png"));
                    threadSleeper(Thread.currentThread());
                    step++;
                }
                if (step == 4){
                    setImage(setImageHelper("Cropped_Images/DragonBalloon2.png"));
                    threadSleeper(Thread.currentThread());
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

    public void threadSleeper(Thread thread){
        try{
            Thread.sleep(800);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void explodeOrdinaryBalloon(){
        Runnable exploding = new Runnable() {
            private int step=1;
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

    public void fire(List<GameObject> gameObjects){
        if (getDirection() == ObjectDirection.RIGHT && balloonState != BalloonState.EXPLODING){
            gameObjects.add(new Flame(getRowIndex(),getColumnIndex()+1,ObjectDirection.RIGHT));
        }
        if (getDirection() == ObjectDirection.LEFT && balloonState != BalloonState.EXPLODING){
            gameObjects.add(new Flame(getRowIndex(),getColumnIndex()-1,ObjectDirection.LEFT));
        }
        if (getDirection() == ObjectDirection.UP && balloonState != BalloonState.EXPLODING){
            gameObjects.add(new Flame(getRowIndex()-1,getColumnIndex(),ObjectDirection.UP));
        }
        if (getDirection() == ObjectDirection.DOWN && balloonState != BalloonState.EXPLODING){
            gameObjects.add(new Flame(getRowIndex()+1,getColumnIndex(),ObjectDirection.DOWN));
        }
    }
}

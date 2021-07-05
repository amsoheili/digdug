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
            setBalloonState(BalloonState.EXPLODING);
            explode();
        }
    }

    public void explode(){
        if (type == BalloonType.ORDINARY && balloonState == BalloonState.EXPLODING){
            Timeline timeline = new Timeline();
            timeline.setCycleCount(3);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                int step=1;
                @Override
                public void handle(ActionEvent event) {
                    ImageView image1;
                    if (step == 1){
                        setImage(setImageHelper("Cropped_Images/Ordinary_Balloon2.png"));
                        step++;
                    }
                    if (step == 2){
                        setImage(setImageHelper("Cropped_Images/Ordinary_Balloon3.png"));
                        step++;
                    }
                    if (step == 3){
                        setAlive(false);
                        setVisible(false);
                    }
                }
            });
        }
    }

    @Override
    public void move(){
        setX(getX()+getXSpeed());
        setY(getY()+getYSpeed());
        setImage();
    }

}

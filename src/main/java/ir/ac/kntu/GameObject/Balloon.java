package ir.ac.kntu.GameObject;

import ir.ac.kntu.MapData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Balloon extends GameObject {
    private BalloonType type; // 1-> Ordinary    2-> Dragon
    private ObjectDirection direction;
    private BalloonState balloonState;

    public Balloon(int x,int y,BalloonType type){
        super(x,y);
        this.type = type;
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
//        if (type == BalloonType.ORDINARY && balloonState == BalloonState.EXPLODING){
//            Timeline timeline = new Timeline();
//            timeline.setCycleCount(3);
//            KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
//                int step=1;
//                @Override
//                public void handle(ActionEvent event) {
//                    ImageView image1;
//                    if (step == 1){
//                        image1 = setImageHelper("Cropped_Images/Ordinary_Balloon3.png");
//                        super.se
//                        step++;
//                    }
//                    if (step == 2){
//                        image = new ImageView("Cropped_Images/Ordinary_Balloon3.png");
//                        step++;
//                    }
//                    if (step == 3){
//                        getChildren().clear();
//                    }
//                }
//            });
//        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.RIGHT &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.UP &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            image.setRotate(90);
            super.setImage(image);
        }
        if (type == BalloonType.DRAGON && direction == ObjectDirection.DOWN &&
                balloonState == BalloonState.MOVING){
            image = setImageHelper("Cropped_Images/DragonBalloon4.png");
            image.setRotate(-90);
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

}

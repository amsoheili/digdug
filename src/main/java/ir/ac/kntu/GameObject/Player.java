package ir.ac.kntu.GameObject;

import ir.ac.kntu.KeyBoard.KeyListener;
import ir.ac.kntu.MapData;
import ir.ac.kntu.PlayerInfo;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class Player extends GameObject implements KeyListener {
    private int hp;
    private int currentScore;
    private int maxScore;
    private ObjectDirection direction;
    private PlayerState playerState;
    private KeyEvent keyEvent;
    private PlayerInfo playerInfo;

    public Player(){
    }

    public Player(int x,int y,int hp,ObjectDirection direction,PlayerState playerState,int xSpeed,int ySpeed){
        super(x,y,xSpeed,ySpeed);
        this.hp = hp;
        this.currentScore = 0;
        this.direction = direction;
        this.playerState = playerState;
        setImage();
    }

    public void setImage(){
        ImageView image;
        if (direction == ObjectDirection.RIGHT && playerState == PlayerState.MOVING){
            image = setImageHelper("Cropped_Images/Player_Move_Right.png");
            setImage(image);
        }
        if (direction == ObjectDirection.LEFT && playerState == PlayerState.MOVING){
            image = setImageHelper("Cropped_Images/Player_Move_Left.png");
            setImage(image);
        }
        if (direction == ObjectDirection.UP && playerState == PlayerState.MOVING){
            image = setImageHelper("Cropped_Images/Player_Move_Right.png");
            image.setRotate(-90);
            setImage(image);
        }
        if (direction == ObjectDirection.DOWN && playerState == PlayerState.MOVING){
            image = setImageHelper("Cropped_Images/Player_Move_Down.png");
            setImage(image);
        }
        if (direction == ObjectDirection.RIGHT && playerState == PlayerState.STANDING){
            image = setImageHelper("Cropped_Images/Player_Right.png");
            setImage(image);
        }
        if (direction == ObjectDirection.LEFT && playerState == PlayerState.STANDING){
            image = setImageHelper("Cropped_Images/Player_Left.png");
            setImage(image);
        }
        if (direction == ObjectDirection.UP && playerState == PlayerState.STANDING){
            image = setImageHelper("Cropped_Images/Player_Right.png");
            image.setRotate(-90);
            setImage(image);
        }
        if (direction == ObjectDirection.DOWN && playerState == PlayerState.STANDING){
            image = setImageHelper("Cropped_Images/Player_Down.png");
            setImage(image);
        }

//        if (direction == ObjectDirection.RIGHT && playerState == PlayerState.FIRING){
//            image = new ImageView("Cropped_Images/Player_Strike_Right.png");
//            image.setFitWidth();
//            setImage(image);
//        }

    }


    @Override
    public void move() {
        //do nothing
    }

    @Override
    public void notify(KeyEvent keyEvent, List<GameObject> gameObjects) {
        if(!isAlive()){
            return;
        }
        this.keyEvent = keyEvent;
        if(keyEvent.getEventType()==KeyEvent.KEY_RELEASED){
            setImage();
            return;
        }
        notifyPlayer(keyEvent,gameObjects);
        setImage();
    }

    public void notifyPlayer(KeyEvent keyEvent, List<GameObject> gameObjects) {
        switch (keyEvent.getCode()) {
            case UP:
                if (getX() > 1) {
                    setX(getX() - 1);
                }
                this.direction = ObjectDirection.UP;
                break;
            case DOWN:
                if (getX() < MapData.GRID_SIZE_X) {
                    setX(getX() + 1);
                }
                this.direction = ObjectDirection.DOWN;
                break;
            case RIGHT:
                if (getY() < MapData.GRID_SIZE_X) {
                    setY(getY() + 1);
                }
                this.direction = ObjectDirection.RIGHT;
                break;
            case LEFT:
                if (getY() > 1) {
                    setY(getY() - 1);
                }
                this.direction = ObjectDirection.LEFT;
                break;
            case ENTER:
                Bullet bullet = setBullet();
                gameObjects.add(bullet);
            default:
                break;
        }
        gameObjects.remove(this);
        gameObjects.add(this);
    }

    public Bullet setBullet(){
        if (direction == ObjectDirection.RIGHT){
            return new Bullet(getX()+1,getY(),ObjectDirection.RIGHT);
        }else if (direction == ObjectDirection.LEFT){
            return new Bullet(getX()-1,getY(),ObjectDirection.LEFT);
        }else if (direction == ObjectDirection.UP){
            return new Bullet(getX(),getY()-1,ObjectDirection.UP);
        }else{
            return new Bullet(getX(),getY()+1,ObjectDirection.DOWN);
        }
    }
}

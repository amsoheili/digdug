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
    private int bulletType;

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
                if (getRowIndex() > 1) {
                    setRowIndex(getRowIndex() - 1);
                }
                this.direction = ObjectDirection.UP;
                break;
            case DOWN:
                if (getRowIndex() < MapData.GRID_SIZE_X) {
                    setRowIndex(getRowIndex() + 1);
                }
                this.direction = ObjectDirection.DOWN;
                break;
            case RIGHT:
                if (getColumnIndex() < MapData.GRID_SIZE_X) {
                    setColumnIndex(getColumnIndex() + 1);
                }
                this.direction = ObjectDirection.RIGHT;
                break;
            case LEFT:
                if (getColumnIndex() > 1) {
                    setColumnIndex(getColumnIndex() - 1);
                }
                this.direction = ObjectDirection.LEFT;
                break;
            case ENTER:
                setBullet(gameObjects);
            default:
                break;
        }
        gameObjects.remove(this);
        gameObjects.add(this);
    }

    public void setBullet(List<GameObject> gameObjects){
        this.bulletType = 2;
        if (direction == ObjectDirection.RIGHT && bulletType == 1){
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+1,ObjectDirection.RIGHT));
        }else if (direction == ObjectDirection.LEFT && bulletType == 1){
             gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-1,ObjectDirection.LEFT));
        }else if (direction == ObjectDirection.UP && bulletType == 1){
             gameObjects.add(new Bullet(getRowIndex()-1, getColumnIndex(),ObjectDirection.UP));
        }else if (direction == ObjectDirection.DOWN && bulletType == 1){
             gameObjects.add(new Bullet(getRowIndex()+1, getColumnIndex(),ObjectDirection.DOWN));
        }

        if (direction == ObjectDirection.RIGHT && bulletType == 2){
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+1,ObjectDirection.RIGHT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+2,ObjectDirection.RIGHT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+3,ObjectDirection.RIGHT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+4,ObjectDirection.RIGHT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+5,ObjectDirection.RIGHT));
        }else if (direction == ObjectDirection.LEFT && bulletType == 2){
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-1,ObjectDirection.LEFT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-2,ObjectDirection.LEFT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-3,ObjectDirection.LEFT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-4,ObjectDirection.LEFT));
            gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-5,ObjectDirection.LEFT));
        }else if (direction == ObjectDirection.UP && bulletType == 2){
            gameObjects.add(new Bullet(getRowIndex()-1, getColumnIndex(),ObjectDirection.UP));
            gameObjects.add(new Bullet(getRowIndex()-2, getColumnIndex(),ObjectDirection.UP));
            gameObjects.add(new Bullet(getRowIndex()-3, getColumnIndex(),ObjectDirection.UP));
            gameObjects.add(new Bullet(getRowIndex()-4, getColumnIndex(),ObjectDirection.UP));
            gameObjects.add(new Bullet(getRowIndex()-5, getColumnIndex(),ObjectDirection.UP));
        }else if (direction == ObjectDirection.DOWN && bulletType == 2){
            gameObjects.add(new Bullet(getRowIndex()+1, getColumnIndex(),ObjectDirection.DOWN));
            gameObjects.add(new Bullet(getRowIndex()+2, getColumnIndex(),ObjectDirection.DOWN));
            gameObjects.add(new Bullet(getRowIndex()+3, getColumnIndex(),ObjectDirection.DOWN));
            gameObjects.add(new Bullet(getRowIndex()+4, getColumnIndex(),ObjectDirection.DOWN));
            gameObjects.add(new Bullet(getRowIndex()+5, getColumnIndex(),ObjectDirection.DOWN));
        }
    }
}

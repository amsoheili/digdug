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
            notifyPlayerReleasedKey(keyEvent,gameObjects);
            setImage();
            return;
        }
        notifyPlayer(keyEvent,gameObjects);
        setImage();
    }
    public void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    public void notifyPlayerReleasedKey(KeyEvent keyEvent, List<GameObject> gameObjects){
        setPlayerState(PlayerState.STANDING);
        switch (keyEvent.getCode()) {
            case UP:
                this.direction = ObjectDirection.UP;
                break;
            case DOWN:
                this.direction = ObjectDirection.DOWN;
                break;
            case RIGHT:
                this.direction = ObjectDirection.RIGHT;
                break;
            case LEFT:
                this.direction = ObjectDirection.LEFT;
                break;
            default:
                break;
        }
        gameObjects.remove(this);
        gameObjects.add(this);
    }
    public void notifyPlayer(KeyEvent keyEvent, List<GameObject> gameObjects) {
        setPlayerState(PlayerState.MOVING);
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
            setBulletHelper(gameObjects,0,3,MathOperation.PLUS,ObjectDirection.RIGHT);
        }else if (direction == ObjectDirection.LEFT && bulletType == 1){
            setBulletHelper(gameObjects,0,3,MathOperation.MINUS,ObjectDirection.LEFT);
        }else if (direction == ObjectDirection.UP && bulletType == 1){
            setBulletHelper(gameObjects,3,0,MathOperation.MINUS,ObjectDirection.UP);
        }else if (direction == ObjectDirection.DOWN && bulletType == 1){
            setBulletHelper(gameObjects,3,0,MathOperation.PLUS,ObjectDirection.DOWN);
        }

        if (direction == ObjectDirection.RIGHT && bulletType == 2){
            setBulletHelper(gameObjects,0,5,MathOperation.PLUS,ObjectDirection.RIGHT);
        }else if (direction == ObjectDirection.LEFT && bulletType == 2){
            setBulletHelper(gameObjects,0,5,MathOperation.MINUS,ObjectDirection.LEFT);
        }else if (direction == ObjectDirection.UP && bulletType == 2){
            setBulletHelper(gameObjects,5,0,MathOperation.MINUS,ObjectDirection.UP);
        }else if (direction == ObjectDirection.DOWN && bulletType == 2){
            setBulletHelper(gameObjects,5,0,MathOperation.PLUS,ObjectDirection.DOWN);
        }
    }

    public void setBulletHelper(List<GameObject> gameObjects,int x,int y,MathOperation operation,ObjectDirection direction){
        if (x==0){
            if (operation == MathOperation.PLUS){
                for (int i=1;i<=y;i++){
                    if (getColumnIndex()+i < MapData.GRID_SIZE_X){
                        gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()+i, direction));
                    }
                }
            }else if (operation == MathOperation.MINUS){
                for (int i=1;i<=y;i++){
                    if (getColumnIndex()-i >= 0){
                        gameObjects.add(new Bullet(getRowIndex(), getColumnIndex()-i, direction));
                    }
                }
            }
        }else if (y == 0){
            if (operation == MathOperation.PLUS){
                for (int i=1;i<=x;i++){
                    if (getRowIndex()+i < MapData.GRID_SIZE_X){
                        gameObjects.add(new Bullet(getRowIndex()+i, getColumnIndex(), direction));
                    }
                }
            }else if (operation == MathOperation.MINUS){
                for (int i=1;i<=x;i++){
                    if (getRowIndex()-i >= 0){
                        gameObjects.add(new Bullet(getRowIndex()-i, getColumnIndex(), direction));
                    }
                }
            }
        }
    }
}

package ir.ac.kntu.GameObject;

import ir.ac.kntu.KeyBoard.KeyListener;
import ir.ac.kntu.Map.MapData;
import ir.ac.kntu.PlayerInfo;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class Player extends GameObject implements KeyListener {
    private int hp;
    private int currentScore;
    private int highScore;
    private ObjectDirection direction;
    private PlayerState playerState;
    private KeyEvent keyEvent;
    private PlayerInfo playerInfo;
    private int bulletType;
    private int lastRowIndex;
    private int lastColumnIndex;

    public Player(){
    }

    public Player(int x,int y,PlayerInfo playerInfo,int hp,ObjectDirection direction,PlayerState playerState,int xSpeed,int ySpeed){
        super(x,y,xSpeed,ySpeed);
        this.hp = hp;
        this.highScore = 0;
        this.currentScore = 0;
        this.direction = direction;
        this.playerState = playerState;
        this.playerInfo = playerInfo;
        this.bulletType = 1;
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
        this.lastColumnIndex = getColumnIndex();
        this.lastRowIndex = getRowIndex();
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
                moveUp();
                this.direction = ObjectDirection.UP;
                break;
            case DOWN:
                moveDown();
                this.direction = ObjectDirection.DOWN;
                break;
            case RIGHT:
                moveRight();
                this.direction = ObjectDirection.RIGHT;
                break;
            case LEFT:
                moveLeft();
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

    public void moveUp(){
        if (getRowIndex() >= getYSpeed()) {
            if (getYSpeed() == 2){
                Runnable up2 = new Runnable() {
                    @Override
                    public void run() {
                        setRowIndex(getRowIndex() - 1);
                        try{
                            Thread.sleep(250);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        setRowIndex(getRowIndex() - 1);
                    }
                };
                Thread thread = new Thread(up2);
                thread.start();
            }else {
                setRowIndex(getRowIndex() - getYSpeed());
            }
        }
    }

    public void moveDown(){
        if (getRowIndex() < MapData.GRID_SIZE_X - getYSpeed()) {
            if (getYSpeed() == 2){
                Runnable down2 = new Runnable() {
                    @Override
                    public void run() {
                        setRowIndex(getRowIndex() + 1);
                        try{
                            Thread.sleep(250);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        setRowIndex(getRowIndex() + 1);
                    }
                };
                Thread thread = new Thread(down2);
                thread.start();
            }else {
                setRowIndex(getRowIndex() + getYSpeed());
            }
        }
    }

    public void moveRight(){
        if (getColumnIndex() < MapData.GRID_SIZE_X - getXSpeed()) {
            if (getXSpeed() == 2){
                Runnable right2 = new Runnable() {
                    @Override
                    public void run() {
                        setColumnIndex(getColumnIndex() + 1);
                        try{
                            Thread.sleep(250);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        setColumnIndex(getColumnIndex() + 1);
                    }
                };
                Thread thread = new Thread(right2);
                thread.start();
            }else {
                setColumnIndex(getColumnIndex() + getXSpeed());
            }
        }
    }

    public void moveLeft(){
        if (getColumnIndex() >= getXSpeed()) {
            if (getXSpeed() == 2){
                Runnable left2 = new Runnable() {
                    @Override
                    public void run() {
                        setColumnIndex(getColumnIndex() - 1);
                        try{
                            Thread.sleep(250);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        setColumnIndex(getColumnIndex() - 1);
                    }
                };
                Thread thread = new Thread(left2);
                thread.start();
            }else{
                setColumnIndex(getColumnIndex() - getXSpeed());
            }
        }
    }

    public void setBullet(List<GameObject> gameObjects){
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
        Bullet bullet = new Bullet();
        if (x==0){
            if (operation == MathOperation.PLUS){
                for (int i=1;i<=y;i++){
                    if (getColumnIndex()+i < MapData.GRID_SIZE_X){
                        bullet = new Bullet(getRowIndex(), getColumnIndex()+i, direction);
                        gameObjects.add(bullet);
                        checkHit(bullet);
                    }
                }
            }else if (operation == MathOperation.MINUS){
                for (int i=1;i<=y;i++){
                    if (getColumnIndex()-i >= 0){
                        bullet = new Bullet(getRowIndex(), getColumnIndex()-i, direction);
                        gameObjects.add(bullet);
                        checkHit(bullet);
                    }
                }
            }
        }else if (y == 0){
            if (operation == MathOperation.PLUS){
                for (int i=1;i<=x;i++){
                    if (getRowIndex()+i < MapData.GRID_SIZE_X){
                        bullet = new Bullet(getRowIndex()+i, getColumnIndex(), direction);
                        gameObjects.add(bullet);
                        checkHit(bullet);
                    }
                }
            }else if (operation == MathOperation.MINUS){
                for (int i=1;i<=x;i++){
                    if (getRowIndex()-i >= 0){
                        bullet = new Bullet(getRowIndex()-i, getColumnIndex(), direction);
                        gameObjects.add(bullet);
                        checkHit(bullet);
                    }
                }
            }
        }
        //checkHit(bullet);
    }

    public void checkHit(Bullet bullet){
        Runnable checkHit = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(220);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("checkHit1");
                if (bullet.isHitOrdinaryBalloon()){
                    System.out.println("checkHit2");
                    setCurrentScore(getCurrentScore() + 2);
                }
                try{
                    Thread.sleep(220);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (bullet.isHitDragonBalloon()){
                    System.out.println("checkHit3");
                    setCurrentScore(getCurrentScore() + 4);
                }
            }
        };
        Thread thread = new Thread(checkHit);
        thread.start();
//        System.out.println("checkHit1");
//        System.out.println(bullet.isHitOrdinaryBalloon());
//        if (bullet.isHitOrdinaryBalloon()){
//            System.out.println("checkHit2");
//            setCurrentScore(getCurrentScore() + 2);
//        }
//        if (bullet.isHitDragonBalloon()){
//            System.out.println("checkHit3");
//            setCurrentScore(getCurrentScore() + 4);
//        }
    }

    public void setCurrentScore(int currentScore){
        if (currentScore > getHighScore()){
            setHighScore(currentScore);
        }
        this.currentScore = currentScore;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public int getHighScore(){
        return highScore;
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof IndestructibleRock){
            if (((IndestructibleRock)gameObject).getLastRowIndex() == getRowIndex()-1 &&
                    (((gameObject).getColumnIndex() == getLastColumnIndex()+1) ||
                    ((gameObject).getColumnIndex() == getLastColumnIndex()-1)) ){
                System.out.println("You died");
                setAlive(false);
            }else {
                goBack();
            }
        }
        if (gameObject instanceof Balloon && (((Balloon)gameObject).getBalloonState() != BalloonState.EXPLODING) ){
            getsDamageOrDie();
        }
        if (gameObject instanceof Flame){
            getsDamageOrDie();
        }
        if (gameObject instanceof Sniper){
            setBulletType(2);
        }
        if (gameObject instanceof Heart){
            if (getHp()<3){
                setHp(getHp()+1);
            }
        }
        if (gameObject instanceof Mushroom){
            setXSpeed(2);
            setYSpeed(2);
        }
    }

    public void getsDamageOrDie(){
        if (getHp() == 0){
            setAlive(false);
        }else{
            setHp(getHp()-1);
        }
    }

    public void win(){
        playerInfo.win(getCurrentScore());
    }

    public void lose(){
        System.out.println(getCurrentScore());
        playerInfo.lose(getCurrentScore());
    }

    public void setBulletType(int bulletType){
        this.bulletType = bulletType;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public void goBack(){
        setRowIndex(getLastRowIndex());
        setColumnIndex(getLastColumnIndex());
    }

    public int getLastRowIndex() {
        return lastRowIndex;
    }

    public int getLastColumnIndex() {
        return lastColumnIndex;
    }

    public void setLastColumnIndex(int lastColumnIndex) {
        this.lastColumnIndex = lastColumnIndex;
    }

    public void setLastRowIndex(int lastRowIndex) {
        this.lastRowIndex = lastRowIndex;
    }

    public int getHp(){
        return this.hp;
    }
}

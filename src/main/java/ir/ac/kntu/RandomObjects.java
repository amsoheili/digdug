package ir.ac.kntu;

import ir.ac.kntu.GameObject.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RandomObjects extends TimerTask {
    private List<GameObject> gameObjects;
    private static final SecureRandom GENERATOR = new SecureRandom();
    private Timer timer;

    public RandomObjects(List<GameObject> gameObjects){
        this.gameObjects=gameObjects;
        this.timer=new Timer();
    }


    @Override
    public void run() {
        clean();
        addRandomObjects();
    }

    public void start(){
        this.timer = new Timer();
        timer.schedule(this,15000,15000);
    }

    public void stop(){
        timer.cancel();
    }

    public void clean(){
        for (int i=0;i<gameObjects.size();i++){
            if (gameObjects.get(i) instanceof Heart ||
                gameObjects.get(i) instanceof Mushroom ||
                gameObjects.get(i) instanceof Sniper){
                gameObjects.add(new DestructibleRock(gameObjects.get(i).getRowIndex(),gameObjects.get(i).getColumnIndex()));
                gameObjects.remove(i);
            }
        }
    }

    public void addRandomObjects(){
        int random = GENERATOR.nextInt(3);
        if (random == 0){
            randomSniper();
        }else if (random == 1){
            randomHeart();
        }else {
            randomMushroom();
        }
    }

    public void randomSniper(){
        int random;
        for (int i=0;i< gameObjects.size();i++){
            random = GENERATOR.nextInt(gameObjects.size());
            if (gameObjects.get(random) instanceof DestructibleRock){
                gameObjects.add(new Sniper(gameObjects.get(random).getRowIndex(),gameObjects.get(random).getColumnIndex()));
                gameObjects.remove(random);
                return;
            }
        }
    }

    public void randomHeart(){
        int random;
        for (int i=0;i< gameObjects.size();i++){
            random = GENERATOR.nextInt(gameObjects.size());
            if (gameObjects.get(random) instanceof DestructibleRock){
                gameObjects.add(new Heart(gameObjects.get(random).getRowIndex(),gameObjects.get(random).getColumnIndex()));
                gameObjects.remove(random);
                return;
            }
        }
    }

    public void randomMushroom(){
        int random;
        for (int i=0;i< gameObjects.size();i++){
            random = GENERATOR.nextInt(gameObjects.size());
            if (gameObjects.get(random) instanceof DestructibleRock){
                gameObjects.add(new Mushroom(gameObjects.get(random).getRowIndex(),gameObjects.get(random).getColumnIndex()));
                gameObjects.remove(random);
                return;
            }
        }
    }
}

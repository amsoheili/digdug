package ir.ac.kntu.GameObject;

public class Sniper extends GameObject{

    public Sniper(int rowIndex,int columnIndex){
        super(rowIndex,columnIndex,0,0);
        setImage(setImageHelper("assets/gun.png"));
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Player){
            setAlive(false);
        }
    }
}

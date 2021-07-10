package ir.ac.kntu.GameObject;

public class Heart extends GameObject{
    public Heart(int rowIndex,int columnIndex){
        super(rowIndex,columnIndex,0,0);
        setImage(setImageHelper("assets/heart.png"));
    }

    @Override
    public void collide(GameObject gameObject){
        if (gameObject instanceof Player){
            setAlive(false);
        }
    }
}

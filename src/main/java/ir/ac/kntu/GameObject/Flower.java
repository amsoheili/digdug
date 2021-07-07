package ir.ac.kntu.GameObject;

public class Flower extends GameObject{

    public Flower(int rowIndex,int columnIndex){
        super(rowIndex,columnIndex,0,0);
        setImage(setImageHelper("Cropped_Images/HP_Flower.png"));
    }
}

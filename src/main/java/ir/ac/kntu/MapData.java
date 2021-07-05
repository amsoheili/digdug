package ir.ac.kntu;

public class MapData {
    public static final int EMPTY = 0;
    public static final int DESTRUCTIBLE_ROCK = 1;
    public static final int INDESTRUCTIBLE_ROCK = 2;
    public static final int ORDINARY_BALLOON_AREA = 3;
    public static final int DRAGON_BALLOON_AREA = 4;
    public static final int PLAYER = 5;
    public static final int GRID_SCALE = 30;
    public static final int GRID_SIZE_X = 20;
    public static final int GRID_SIZE_Y = 20;

    private int[][] MAP_DATA;

    public MapData(int[][] mapArray){
        MAP_DATA = mapArray;
    }

    public int[][] getMAP(){
        return MAP_DATA;
    }

}

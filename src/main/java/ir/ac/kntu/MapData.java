package ir.ac.kntu;

public class MapData {
    private static final int EMPTY = 0;
    private static final int DESTRUCTIBLE_ROCK = 1;
    private static final int INDESTRUCTIBLE_ROCK = 2;
    private static final int GRID_SCALE = 20;
    private static final int GRID_SIZE_X = 30;
    private static final int GRID_SIZE_Y = 30;

    private int[][] MAP_DATA;

    public MapData(int[][] mapArray){
        MAP_DATA = mapArray;
    }


}

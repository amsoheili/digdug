package ir.ac.kntu;

import ir.ac.kntu.GameObject.Balloon;
import ir.ac.kntu.GameObject.BalloonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.Scanner;

public class MapBuilder {
    public static GridPane createMap(GridPane root){
        root.getChildren().clear();
        MapData map = readMap();
        createBackGround(root,map);
        //createBalloon(root,map);
        return root;
    }

    public static MapData readMap(){
        int[][] mapData = new int[MapData.GRID_SIZE_X][MapData.GRID_SIZE_X];
        File file = new File("src/main/resources/MapData.txt");
        try(Scanner input = new Scanner(file)){
            for (int i=0;i<MapData.GRID_SIZE_X;i++){
                for (int j=0;j<MapData.GRID_SIZE_Y;j++){
                    mapData[i][j] = input.nextInt();
                }
            }
        }catch (IOException e){
            e.getStackTrace();
        }
        printArray(mapData);
        MapData mapData1 = new MapData(mapData);
        return mapData1;
    }

    public static void createBackGround(GridPane root,MapData map){
        int[][] mapData = map.getMAP();
        for (int i=0;i< mapData.length;i++){
            for (int j=0;j< mapData[i].length;j++){
                switch (mapData[i][j]){
//                    case 0:
//                        Rectangle rec0 = new Rectangle(MapData.GRID_SCALE,MapData.GRID_SCALE);
//                        rec0.setFill(Color.BLACK);
//                        root.add(rec0,j,i);
//                        break;
                    case 1:
                        Rectangle rec1 = new Rectangle(MapData.GRID_SCALE,MapData.GRID_SCALE);
                        rec1.setFill(Color.PINK);
                        root.add(rec1,j,i);
                        break;
                    default:
                        Rectangle rec0 = new Rectangle(MapData.GRID_SCALE,MapData.GRID_SCALE);
                        rec0.setFill(Color.BLACK);
                        root.add(rec0,j,i);
                        break;
                }
            }
        }
    }

    public static void printArray(int[][] array){
        for (int i=0;i< array.length;i++){
            for (int j=0;j<array[i].length;j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

//    private static void createBalloon(GridPane root,MapData map){
//        int[][] mapData = map.getMAP();
//        for (int i=0;i< mapData.length;i++){
//            for (int j=0;j< mapData[i].length;j++){
//                switch (mapData[i][j]){
//                    case 3:
//                        if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.ORDINARY_BALLOON_AREA)
//                                || (mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.ORDINARY_BALLOON_AREA)){
//                            root.add(new Balloon(i,j, BalloonType.ORDINARY).getImage(),j,i);
//                        }
//                        break;
//                    case 4:
//                        if ((mapData[i-1][j] == MapData.DESTRUCTIBLE_ROCK && mapData[i+1][j] == MapData.DRAGON_BALLOON_AREA)
//                                || (mapData[i][j-1] == MapData.DESTRUCTIBLE_ROCK && mapData[i][j+1] == MapData.DRAGON_BALLOON_AREA)){
//                            root.add(new Balloon(i,j,BalloonType.DRAGON).getImage(),j,i);
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    }
}

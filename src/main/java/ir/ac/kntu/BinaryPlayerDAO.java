package ir.ac.kntu;

import java.io.*;
import java.util.ArrayList;

public class BinaryPlayerDAO implements PlayerDAO{
    @Override
    public ArrayList<PlayerInfo> getAllPlayers() {
        ArrayList<PlayerInfo> playersInfo = new ArrayList<>();
        File file = new File("src/main/resources/PlayersInfo.txt");
        try(FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fileInputStream)){
            while (true){
                try {
                    playersInfo.add((PlayerInfo) input.readObject());
                }catch (EOFException e){
                    break;
                }
            }
        }catch (FileNotFoundException e){
            e.getStackTrace();
        }catch (ClassNotFoundException | IOException e){
            System.out.println(e.getMessage());
        }
        return playersInfo;
    }

    @Override
    public void saveAllPlayers(ArrayList<PlayerInfo> list) {
        ArrayList<PlayerInfo> players = new ArrayList<>();
        players = getAllPlayers();
        for (int i=0;i<players.size();i++){
            for(int j=0;j< list.size();j++){
                if(list.get(j).equals(players.get(i))){
                    players.remove(i);
                    players.add(list.get(j));
                }else{
                    if(!players.contains(list.get(j))){
                        players.add(list.get(j));
                    }
                }
            }
        }
        if(players.size()==0){
            players.addAll(list);
        }
        File file = new File("src/main/resources/PlayersInfo.txt");
        try(FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);){
            for (int i=0;i< players.size();i++){
                output.writeObject(players.get(i));
            }
        }catch (FileNotFoundException e){
            System.out.println("There is no file to save");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

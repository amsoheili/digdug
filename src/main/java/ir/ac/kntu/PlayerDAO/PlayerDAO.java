package ir.ac.kntu.PlayerDAO;

import ir.ac.kntu.PlayerInfo;

import java.util.ArrayList;

public interface PlayerDAO {

    public ArrayList<PlayerInfo> getAllPlayers();

    public void saveAllPlayers(ArrayList<PlayerInfo> players);
}

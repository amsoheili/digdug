package ir.ac.kntu;

import java.util.ArrayList;

public interface PlayerDAO {

    public ArrayList<PlayerInfo> getAllPlayers();

    public void saveAllPlayers(ArrayList<PlayerInfo> players);
}

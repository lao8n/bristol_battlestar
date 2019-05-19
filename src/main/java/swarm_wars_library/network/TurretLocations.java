package swarm_wars_library.network;

import swarm_wars_library.map.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurretLocations {

    private int turretCounts = 5;

    private int[] versions = new int[turretCounts];

    private double[][] locations = new double[turretCounts][2];

    private int map_width = Map.getInstance().getMapWidth();

    private int map_height = Map.getInstance().getMapHeight();

    public static TurretLocations turretLocations = new TurretLocations();

    public static TurretLocations getInstance() {
        return turretLocations;
    }

    private TurretLocations() {
        for (int i = 0; i < turretCounts; i++) {
            versions[i] = 0;
            locations[i][0] = map_width * Math.random();
            locations[i][1] = map_height * Math.random();
        }
    }

    public boolean updateTurretLocation(int id, int version) {
        if (version < versions[id]) {
            return false;
        }
        if (version >= versions[id]) {
            locations[id][0] = map_width * Math.random();
            locations[id][1] = map_height * Math.random();
            versions[id] = versions[id] + 1;
            try {
                Logger.getInstance().log("Updated turret id: " + id +
                                ". New location at (" + locations[id][0] + "," + locations[id][1] + ")",
                        "Server");
            }catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public ArrayList getTurretLocationWithId(int id) {
        ArrayList<Double> l = new ArrayList<>();
        l.add(locations[id][0]);
        l.add(locations[id][1]);
        return l;
    }

    public int getTurretVersionWithId(int id) {
        return versions[id];
    }

    public ArrayList getStartLocations() {
        ArrayList<Double> l = new ArrayList<>();
        for (int i = 0; i < turretCounts; i++){
            l.add(locations[i][0]);
            l.add(locations[i][1]);
        }
        return l;
    }
}

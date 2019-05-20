package swarm_wars_library.network;

import swarm_wars_library.map.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurretLocations {

    private int turretCounts = 5;

    private int healthPackCounts = 3;

    private int[] versions = new int[turretCounts];

    private double[][] locations = new double[turretCounts][2];

    private int map_width = Map.getInstance().getMapWidth();

    private int map_height = Map.getInstance().getMapHeight();

    public static TurretLocations turretLocations = new TurretLocations();

    private int[] hpVersions = new int[healthPackCounts];

    private double[][] hpLocations = new double[healthPackCounts][2];

    public static TurretLocations getInstance() {
        return turretLocations;
    }

    private TurretLocations() {
        for (int i = 0; i < turretCounts; i++) {
            versions[i] = 0;
            locations[i][0] = map_width * Math.random();
            locations[i][1] = map_height * Math.random();
        }
        for (int i = 0; i < healthPackCounts; i++) {
            hpVersions[i] = 0;
            hpLocations[i][0] = map_width * Math.random();
            hpLocations[i][1] = map_height * Math.random();
        }
    }

    public void refreshTurrets() {
        for (int i = 0; i < turretCounts; i++) {
            versions[i] = 0;
            locations[i][0] = map_width * Math.random();
            locations[i][1] = map_height * Math.random();
        }
        for (int i = 0; i < healthPackCounts; i++) {
            hpVersions[i] = 0;
            hpLocations[i][0] = map_width * Math.random();
            hpLocations[i][1] = map_height * Math.random();
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

    public boolean updateHPLocation(int id, int version) {
        if (version < hpVersions[id]) {
            return false;
        }
        if (version >= hpVersions[id]) {
            hpLocations[id][0] = map_width * Math.random();
            hpLocations[id][1] = map_height * Math.random();
            hpVersions[id] = hpVersions[id] + 1;
            try {
                Logger.getInstance().log("Updated health pack id: " + id +
                                ". New location at (" + hpLocations[id][0] + "," + hpLocations[id][1] + ")",
                        "Server");
            }catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public ArrayList<Double> getTurretLocationWithId(int id) {
        ArrayList<Double> l = new ArrayList<>();
        l.add(locations[id][0]);
        l.add(locations[id][1]);
        return l;
    }

    public ArrayList<Double> getHealthPackLocationWithId(int id) {
        ArrayList<Double> l = new ArrayList<>();
        l.add(hpLocations[id][0]);
        l.add(hpLocations[id][1]);
        return l;
    }

    public int getTurretVersionWithId(int id) {
        return versions[id];
    }

    public int getHealthPackVersionWithId(int id) {
        return hpVersions[id];
    }

    public ArrayList<Double> getStartLocations() {
        ArrayList<Double> l = new ArrayList<>();
        for (int i = 0; i < turretCounts; i++){
            l.add(locations[i][0]);
            l.add(locations[i][1]);
        }
        return l;
    }

    public ArrayList<Double> getHealthPackStartLocations() {
        ArrayList<Double> l = new ArrayList<>();
        for (int i = 0; i < healthPackCounts; i++){
            l.add(hpLocations[i][0]);
            l.add(hpLocations[i][1]);
        }
        return l;
    }
}

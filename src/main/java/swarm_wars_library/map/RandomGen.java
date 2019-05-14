package swarm_wars_library.map;

import java.util.Date;
import java.util.Random;

public class RandomGen {

    private static RandomGen instance = new RandomGen();

    private static Random r;

    private static long seed;

    private RandomGen(){
    }

    public static void setSeed(int seed){
        r = new Random(seed);
    }

    public static int generateSeed(){
        return (int) Math.round(Math.random() * 10000);
    }

    public static double getRand() {
        return r.nextDouble();
    }

}

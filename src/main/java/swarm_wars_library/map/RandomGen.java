package swarm_wars_library.map;

import java.util.Date;
import java.util.Random;

public class RandomGen {

    private static RandomGen instance = new RandomGen();

    private static Random r;

    private static long seed = 10;

    private RandomGen(){
        r = new Random(seed);
    }

    public static void setSeed(int seed){
        r = new Random(seed);
    }

    public static int generateSeed(){
        return (int) Math.round(RandomGen.getRand() * 10000);
    }

    public static double getRand() {
        return r.nextDouble();
    }

}

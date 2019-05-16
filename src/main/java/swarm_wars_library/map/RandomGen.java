package swarm_wars_library.map;

import java.util.Random;

public class RandomGen {

    private static RandomGen instance = new RandomGen();

    private static Random r;

    private static long seed = 10;

    private static boolean  seedSet = false;

    private RandomGen(){
        r = new Random();
        r.setSeed(seed);
    }

    public static void setSeed(int newSeed){
        if(seedSet) return;
        seed = newSeed;
        r.setSeed(seed);
        seedSet = true;
    }

    public static void resetSeed(){
        r.setSeed(seed);
    }


    public static int generateSeed(){
        return (int) Math.round(RandomGen.getRand() * 10000);
    }

    public static double getRand() {
        double rand = r.nextDouble();
        return rand;
    }

    public static int getInt(int bound){
        int r = (int) Math.round(getRand() * bound);
        if (r == bound) r -= 1;
        if (r < 0) r = 0;
        return r;
    }

    public static void printSeed() {
        System.out.print("Random seed ");
        System.out.println(seed);

    }

}

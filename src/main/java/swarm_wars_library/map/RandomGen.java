package swarm_wars_library.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// double R = RandomGen.getRand();

public class RandomGen {

    private static RandomGen instance = new RandomGen();

    private static Random r;

    private static int seed = 10;

    private static boolean  seedSet = false;

    private static List<Integer> seeds;

    private static int currSeed = 0;

    private RandomGen(){
        seeds = new ArrayList<>();
        r = new Random();
        seeds.add(seed);
        resetSeed();
    }

    public static void setSeed(int newSeed){
        if(seedSet) return;
        populateSeeds(newSeed);
        seed = seeds.get(currSeed++);
        r.setSeed(seed);
        seedSet = true;
    }

    public static void populateSeeds(int seed){
        Random seedGen = new Random();
        seedGen.setSeed(seed);
        for(int i = 0; i < 10; i++) {
            seeds.add(seedGen.nextInt());
        }
    }

    public static void resetSeed(){
        seed = seeds.get((currSeed++) % seeds.size());
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

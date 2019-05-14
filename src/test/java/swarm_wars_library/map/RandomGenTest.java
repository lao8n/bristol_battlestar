package swarm_wars_library.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomGenTest {

    @Test
    void randomIntegrationTest() {

        int seed = RandomGen.generateSeed();
        RandomGen.setSeed(seed);
        double rand1 = RandomGen.getRand();
        assertTrue(rand1 >= 0.0 && rand1 <= 1.0);
        double rand2 = RandomGen.getRand();
        assertTrue(rand2 >= 0.0 && rand2 <= 1.0);
        double rand3 = RandomGen.getRand();
        assertTrue(rand3 >= 0.0 && rand3 <= 1.0);

        RandomGen.setSeed(seed);
        assertEquals(rand1, RandomGen.getRand());
        assertEquals(rand2, RandomGen.getRand());
        assertEquals(rand3, RandomGen.getRand());
    }
}
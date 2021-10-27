package ex0.algo;

import ex0.Tests.Test_Building;
import ex0.Tests.Test_CallForElevator;

import static org.junit.jupiter.api.Assertions.*;

class ShabatElev3AlgoTest {

    @org.junit.jupiter.api.Test
    void allocateAnElevator() {
        Test_Building b = new Test_Building(-5,100,5,1,2,3);
        ShabatElev3Algo algo = new ShabatElev3Algo(b);

        assertEquals(algo.allocateAnElevator(new Test_CallForElevator(5,10)),0);
        b.getTestElevetor(1).setPosition(50);
        assertEquals(algo.allocateAnElevator(new Test_CallForElevator(99,10)),1);
    }

    @org.junit.jupiter.api.Test
    void cmdElevator() {
    }
}
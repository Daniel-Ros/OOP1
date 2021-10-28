package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;
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
        Test_Building b = new Test_Building(-5,100,1,1,2,3);
        ShabatElev3Algo algo = new ShabatElev3Algo(b);

        Test_CallForElevator c1 = new Test_CallForElevator(5,99);
        Test_CallForElevator c2 = new Test_CallForElevator(70,10);
        assertEquals(algo.allocateAnElevator(c1),0);
        assertEquals(algo.allocateAnElevator(c2),0);

        algo.cmdElevator(0);
        assertEquals(b.getTestElevetor(0).getLastGoto(),5);
        c1.setState(CallForElevator.GOIND2DEST);
        b.getTestElevetor(0).setPosition(5);
        algo.cmdElevator(0);
        assertEquals(b.getTestElevetor(0).getLastGoto(),99);
        b.getTestElevetor(0).setState(Elevator.UP);
        b.getTestElevetor(0).setPosition(6);
        algo.cmdElevator(0);
        assertEquals(b.getTestElevetor(0).getLastGoto(),70);
        c2.setState(CallForElevator.GOIND2DEST);
        b.getTestElevetor(0).setPosition(70);
        b.getTestElevetor(0).setState(Elevator.LEVEL);
        algo.cmdElevator(0);
        assertEquals(b.getTestElevetor(0).getLastGoto(),99);
        c1.setState(CallForElevator.DONE);
        b.getTestElevetor(0).setPosition(99);
        algo.cmdElevator(0);
        assertEquals(b.getTestElevetor(0).getLastGoto(),10);
    }
}
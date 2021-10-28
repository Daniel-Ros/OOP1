package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;
import ex0.Tests.Test_Building;
import ex0.Tests.Test_CallForElevator;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

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

    @org.junit.jupiter.api.Test
    void systemCheck(){
        try {
            Simulator_A.setCodeOwner("0000,0000");
            int stage = 0; // any case in [0,9].
            // String callFile = "data/Ex0_stage_2__.csv"; //
            // init the simulator data: {building, calls}.
            Simulator_A.initData(stage, null);

            ElevatorAlgo ex0_alg = new ShabatElev3Algo(Simulator_A.getBuilding());
            Simulator_A.initAlgo(ex0_alg); // init the algorithm to be used by the simulator

            Simulator_A.runSim(); // run the simulation - should NOT take more than few seconds.

            long time = System.currentTimeMillis();
            String report_name = "out/Ex0_report_case_" + stage + "_" + time + "_ID_.log";
            Simulator_A.report(report_name); // print the algorithm results in the given case, and save the log to a file.
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }
}
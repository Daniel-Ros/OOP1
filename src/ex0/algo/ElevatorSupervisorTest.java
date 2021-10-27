package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;
import ex0.Tests.Test_CallForElevator;

import static org.junit.jupiter.api.Assertions.*;

import ex0.Tests.Test_Elevator;
import org.junit.jupiter.api.Test;

import java.util.Vector;

class ElevatorSupervisorTest {

    @org.junit.jupiter.api.Test
    void bid() {
        Test_Elevator e = new Test_Elevator(0, 1, 2, 3);
        ElevatorSupreviser supervisor = new ElevatorSupreviser();
        assertEquals(supervisor.bid(e, new Test_CallForElevator(0, -1)), 11);
        e.setPosition(-2);
        assertEquals(supervisor.bid(e, new Test_CallForElevator(0, -1)), 23);
        supervisor.add(new Test_CallForElevator(0, -1));
        assertEquals(supervisor.bid(e, new Test_CallForElevator(0, -1)), 23);
        supervisor.poll();
        e.setPosition(4);
        assertEquals(supervisor.bid(e, new Test_CallForElevator(3, 0)), 24);
        supervisor.add(new Test_CallForElevator(0, -1));
        assertEquals(supervisor.bid(e, new Test_CallForElevator(3, 0)), 35);
    }

    @org.junit.jupiter.api.Test
    void add() {
        ElevatorSupreviser supervisor = new ElevatorSupreviser();
        supervisor.add(new Test_CallForElevator(5, 10));
        supervisor.add(new Test_CallForElevator(2, 3));
        supervisor.add(new Test_CallForElevator(5, 1));

        assertEquals(supervisor.peek().getSrc(), 5);
        assertEquals(supervisor.poll().getDest(), 10);
        assertEquals(supervisor.peek().getSrc(), 2);
        assertEquals(supervisor.poll().getDest(), 3);
        assertEquals(supervisor.peek().getSrc(), 5);
        assertEquals(supervisor.poll().getDest(), 1);
    }

    @org.junit.jupiter.api.Test
    void getStops() {
        ElevatorSupreviser supervisor = new ElevatorSupreviser();
        Test_CallForElevator c1 = new Test_CallForElevator(1, 10);
        Test_CallForElevator c2 = new Test_CallForElevator(2, 3);
        Test_CallForElevator c3 = new Test_CallForElevator(5, 1);

        supervisor.add(c1);
        supervisor.add(c2);
        supervisor.add(c3);

        Vector<Integer> stops = supervisor.getStops(1, 10, CallForElevator.UP);


        assertEquals(stops.get(0), 5);
        assertEquals(stops.get(1), 2);
        assertEquals(stops.size(), 3);
        assertEquals(supervisor.getStops(1, 10, CallForElevator.UP).size(), 0);

        c2.setState(2);       // simulate the evecator stoped at the second floor
        supervisor.getStop(); // just to clean the internal cache
        assertEquals(supervisor.getStops(1, 10, CallForElevator.UP).get(1), 3);
        supervisor.poll();
        supervisor.poll();
        supervisor.poll();

        c1 = new Test_CallForElevator(0, -1);
        c2 = new Test_CallForElevator(3, 0);

        supervisor.add(c1);
        supervisor.add(c2);
        supervisor.getStop();
        stops = supervisor.getStops(4, -1, CallForElevator.DOWN);
        assertEquals(stops.get(0), 0);
        assertEquals(stops.get(1), 3);
        assertEquals(stops.size(), 2);
        c2.setState(2);
        supervisor.getStop();
        stops = supervisor.getStops(3, -1, CallForElevator.DOWN);
        assertEquals(stops.get(0), 0);
        assertEquals(stops.size(), 1);

    }

    @org.junit.jupiter.api.Test
    void getStop() {
        ElevatorSupreviser supervisor = new ElevatorSupreviser();
        Test_CallForElevator c1 = new Test_CallForElevator(5, 10);
        Test_CallForElevator c2 = new Test_CallForElevator(2, 3);
        Test_CallForElevator c3 = new Test_CallForElevator(5, 1);
        supervisor.add(c1);
        supervisor.add(c2);
        supervisor.add(c3);

        assertEquals(supervisor.getStop(), 5);
        c1.setState(2);
        c3.setState(2);
        assertEquals(supervisor.getStop(), 10);
        supervisor.poll();
        assertEquals(supervisor.getStop(), 2);
        c2.setState(2);
        assertEquals(supervisor.getStop(), 3);
        supervisor.poll();
        assertEquals(supervisor.getStop(), 1);
        supervisor.poll();


    }

}
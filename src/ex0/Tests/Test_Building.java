package ex0.Tests;

import ex0.Building;
import ex0.Elevator;

public class Test_Building implements Building {
    String name;
    int min,max,numOfElevators;
    Test_Elevator [] elevators;

    public Test_Building (int min,int max,int numOfElevators,double speed, double openCloseSpeed, double startStopSpeed){
        this.name="B_Test";
        this.min=min;
        this.max=max;
        this.numOfElevators=numOfElevators;
        elevators = new Test_Elevator [numOfElevators];
        for (int i = 0; i < numOfElevators; i++) {
            elevators[i] = new Test_Elevator(min,speed ,openCloseSpeed,startStopSpeed);
        }
    }

    @Override
    public String getBuildingName() {
        return this.name;
    }

    @Override
    public int minFloor() {
        return this.min;
    }

    @Override
    public int maxFloor() {
        return this.max;
    }

    @Override
    public int numberOfElevetors() {
        return this.numOfElevators;
    }

    @Override
    public Elevator getElevetor(int i) {
        return elevators[i];
    }

    public Test_Elevator getTestElevetor(int i) {
        return elevators[i];
    }

}

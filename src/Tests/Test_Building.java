package Tests;

import ex0.Building;
import ex0.Elevator;

public class Test_Building implements Building {
    String name;
    int min,max,numOfElevators;
    Elevator []elevators;

    public Test_Building (String name,int min,int max,int numOfElevators){
        this.name=name;
        this.min=min;
        this.max=max;
        this.numOfElevators=numOfElevators;
        elevators=new Test_Elevator [numOfElevators]();
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

}

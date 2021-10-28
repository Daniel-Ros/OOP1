## Ex0 OOP class - ariel CS

In this assigment we were given the following interfaces:

| Interface       | Description |
| -----------     | ----------- |
| Elevator        | This is the interface of a Simple Elevator in time - which has a range of floors it can visit, and has a goto and stop functionality.The Elevator uses the "System" (simulated) time. e.g., for computing its current position.       |
| Building        | This interface represents a building with several floors and elevators. The interface is part of Ex0 - OOP (Ariel U.) */        |
| Building | This interface represents a call for an elevator - with a dedicated destination (aka smart Elevators).The call has few states: {Init, Going2SRC, Going2Dest, Done}, each state has a time stamp (in seconds).        |
| ElevatorAlgo     | This interface represents the main Algorithms for the "load-balancing" the Calls for elevators in a building (aka OOP Ex0).It assumes online algorith        |

and a `Sim.jar` file that ran the simulation on the scenario.
In this assgiment we were only required to implement an `ElevatorAlgo` class and write our online elevator algorithm there.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
  - `src/ex0`: the folder where the `Elevator`,`Building`,`Building` interfaces exsit, as well as the main of the program
  - `src/ex0/algo`: the folder where out algorithm and tests exists
  - `src/ex0/test`: implementation for `Elevator`,`Building`,`Building` just for testing purposes 
- `lib`: the folder to maintain dependencies
- `out`: the folder to maintain log and output files.

## Testing
this project was test uing the JUINT5 library. the tests can be run by an IDE of choice.
the test classes are `ElevatorSupervisorTest`,`ShabatElev3AlgoTest`

## Running the simulation
This simulation can be run using the OpenJDK17 (Not tested on any other jdk).This project is configured in the Intellij IDE, but any IDE can be used as long as use add the `Ex0_Simulator_V0.0.jar` to your project.

##  Results

These are the final results that I was able to get
> There were some stages that I got a bit more, but really hurted other stages, soI had to balance it 

|Case | Uncompleted calls | average waiting time per call|
|---|-------------------|------------------------------|
|0|0|23.19897426188186|
|1|4|34.79897426188186|
|2|5|51.09792822120195|
|3|2|42.5963457108327|
|4|2|42.07291073728415|
|5|17|72.77212115705177|
|6|15|55.12788209694845|
|7|47|224.5331211570514|
|8|23|131.51088209694842|
|9|5|46.30134007431241|


## Assigment Instructions
[part 1](https://docs.google.com/document/d/1KMsfQpuENR16Kfofs30KCxE95hSp7xnXXze5xyj-Y6c/edit?usp=sharing)

[part 2](https://docs.google.com/document/d/1_lXbS2P2m_jZ2m7h-9oRC8FiJIOTpoIBJWo6lwJ6Pv8/edit?usp=sharing)

Github: [project files](https://github.com/benmoshe/OOP_2021/tree/main/Assignments/Ex0)

Google Form: [here](https://docs.google.com/forms/d/e/1FAIpQLScng8inlQ59tiwVIKk_froGoE7lX5Ft7CDS5JfRLTnG_Z1m-w/viewform?usp=sf_link)

Reported results (by students) : [here](https://docs.google.com/spreadsheets/d/1_7ZUAyWS0o45TRvDfdiFeZ_b3gnCDJOnC0fkAK7TlGw/edit?usp=sharing)
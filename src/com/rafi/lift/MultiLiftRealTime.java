package com.rafi.lift;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.rafi.lift.Elevator;
import com.rafi.lift.MultiLiftScheduling;

class Elevator {
    int currentFloor;
    String direction;
    Queue<Integer> requests;

    public Elevator() {
        currentFloor = 0;
        direction = "idle";
        requests = new LinkedList<>();
    }
    
    public void setCurrentFloor(int floor) {
    	this.currentFloor = floor;
    }

    public void addRequest(int source, int destination) {
        if (requests.isEmpty()) {
            requests.add(source);
        }
        requests.add(destination);
        setCurrentFloor(destination);
    }

    public String getStatus() {
        return "[Current Floor: " + currentFloor + ", Direction: " + direction + ", Requests: " + requests + "]";
    }
}

class MultiLiftScheduling {
    Elevator[] elevators;

    public MultiLiftScheduling(int numElevators) {
        elevators = new Elevator[numElevators];
        for (int i = 0; i < numElevators; i++) {
            elevators[i] = new Elevator();
        }
    }

    public void addRequest(int source, int destination) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.currentFloor - source);
            if (distance < minDistance && (elevator.direction.equals("idle") || 
                (elevator.direction.equals("up") && source >= elevator.currentFloor) || 
                (elevator.direction.equals("down") && source <= elevator.currentFloor))) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }

        if (bestElevator != null) {
            bestElevator.addRequest(source, destination);
            System.out.println("Request from Floor " + source + " to Floor " + destination + " assigned to Elevator " + (Arrays.asList(elevators).indexOf(bestElevator) + 1));
            
        } else {
            System.out.println("No suitable elevator found for request from Floor " + source + " to Floor " + destination);
        }
    }

    public void step() {
            System.out.println("seems like customer is healthy and taking steps...!!");
    }

    public void displayStatus() {
        for (int i = 0; i < elevators.length; i++) {
            System.out.println("Elevator " + (i + 1) + " " + elevators[i].getStatus());
        }
    }
}

public class MultiLiftRealTime {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MultiLiftScheduling scheduler = new MultiLiftScheduling(2);

        while (true) {
            System.out.println("\n1. Add Request");
            System.out.println("2. Step");
            System.out.println("3. Display Status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            

            switch (choice) {
                case 1:
                    System.out.print("Enter current floor: ");
                    int currentFloor = scanner.nextInt();
                    System.out.print("Enter destination floor: ");
                    int destinationFloor = scanner.nextInt();
                    scheduler.addRequest(currentFloor, destinationFloor);
                    break;

                case 2:
                    scheduler.step();
                    break;

                case 3:
                    scheduler.displayStatus();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    exitLift(scanner);
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    public static void exitLift(Scanner scanner) {
    	System.out.println("Thank you for using lift...!");
    	scanner.close();
    }
}
/**
 * ============================================================
 * ABSTRACT CLASS – Room
 * ============================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * Represents a generic hotel room.
 *
 * @version 2.1
 */

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public abstract void displayRoomDetails();
}


/**
 * ============================================================
 * CLASS – SingleRoom
 * ============================================================
 * @version 2.0
 */
class SingleRoom extends Room {

    private static int availableRooms = 5;

    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    public void displayRoomDetails() {
        System.out.println("Single Room:");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + availableRooms);
        System.out.println();
    }
}


/**
 * ============================================================
 * CLASS – DoubleRoom
 * ============================================================
 * @version 2.0
 */
class DoubleRoom extends Room {

    private static int availableRooms = 3;

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    public void displayRoomDetails() {
        System.out.println("Double Room:");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + availableRooms);
        System.out.println();
    }
}


/**
 * ============================================================
 * CLASS – SuiteRoom
 * ============================================================
 * @version 2.0
 */
class SuiteRoom extends Room {

    private static int availableRooms = 2;

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    public void displayRoomDetails() {
        System.out.println("Suite Room:");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + availableRooms);
        System.out.println();
    }
}


/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Hotel Room Initialization\n");

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        single.displayRoomDetails();
        dbl.displayRoomDetails();
        suite.displayRoomDetails();
    }
}
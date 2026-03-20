import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================================
 * CLASS - Room
 * =============================================================================
 * Holds the characteristics of a specific room type.
 */
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}

/**
 * =============================================================================
 * CLASS - RoomInventory
 * =============================================================================
 * * Use Case 3: Centralized Room Inventory Management
 * * @version 4.0
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        this.roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        // Mapping keys to match the SearchService logic ("Single", "Double", "Suite")
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/**
 * =============================================================================
 * CLASS - RoomSearchService
 * =============================================================================
 * * Use Case 4: Room Search & Availability Check
 * * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 * * It reads room availability from inventory
 * and room details from Room objects.
 * * No inventory mutation or booking logic
 * is performed in this class.
 * * @version 4.0
 */
class RoomSearchService {

    /**
     * Displays available rooms along with 
     * their details and pricing.
     * * This method performs read-only access
     * to inventory and room data.
     * * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search");
        System.out.println();

        // Check and display Single Room availability
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds: " + singleRoom.getBeds());
            System.out.println("Size: " + singleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + singleRoom.getPrice());
            System.out.println("Available: " + availability.get("Single"));
            System.out.println();
        }

        // Check and display Double Room availability
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds: " + doubleRoom.getBeds());
            System.out.println("Size: " + doubleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + doubleRoom.getPrice());
            System.out.println("Available: " + availability.get("Double"));
            System.out.println();
        }

        // Check and display Suite Room availability
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds: " + suiteRoom.getBeds());
            System.out.println("Size: " + suiteRoom.getSize() + " sqft");
            System.out.println("Price per night: " + suiteRoom.getPrice());
            System.out.println("Available: " + availability.get("Suite"));
            System.out.println();
        }
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =============================================================================
 * * Use Case 4: Room Search & Availability Check
 * * Description:
 * This class demonstrates how guests
 * can view available rooms without
 * modifying inventory data.
 * * The system enforces read-only access
 * by design and usage discipline.
 * * @version 4.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Setup initial data
        RoomInventory inventory = new RoomInventory();
        
        // Setup room definitions
        Room singleRoom = new Room("Single", 1, 250, 1500.0);
        Room doubleRoom = new Room("Double", 2, 400, 2500.0);
        Room suiteRoom = new Room("Suite", 3, 750, 5000.0);

        // Initialize Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Execute search
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}
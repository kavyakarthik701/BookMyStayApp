import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================================
 * CLASS - FilePersistenceService
 * =========================================================================
 * * Use Case 12: Data Persistence & System Recovery
 *
 * * Description:
 * This class handles saving and loading of
 * room inventory data using a plain text file.
 *
 * * Features:
 * - Saves inventory state to file
 * - Loads inventory state on startup
 * - Handles missing/corrupt file scenarios
 *
 * * File Format:
 * Each line follows:
 * roomType=availableCount
 *
 * Example:
 * Single=5
 * Double=3
 * Suite=2
 *
 * * @version 12.1
 */
class FilePersistenceService {

    /**
     * Saves current room inventory to file.
     *
     * @param inventory Central inventory object
     * @param filePath  File path for persistence
     */
    public void saveInventory(RoomInventory inventory, String filePath) {

        /*
         * Try-with-resources ensures file is
         * automatically closed after writing.
         */
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            /*
             * Iterate through inventory map
             * and write each entry to file.
             */
            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {

            /*
             * Handles file writing issues such as:
             * - Permission denied
             * - Disk errors
             */
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory from file.
     *
     * @param inventory Central inventory object
     * @param filePath  File path for persistence
     */
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        /*
         * If file does not exist,
         * system starts with default values.
         */
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        /*
         * Read file line-by-line and reconstruct inventory.
         */
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                /*
                 * Split line into key-value pair.
                 */
                String[] parts = line.split("=");

                /*
                 * Validate correct format before parsing.
                 */
                if (parts.length == 2) {

                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    /*
                     * Update inventory with loaded values.
                     */
                    inventory.updateRoom(roomType, count);
                }
            }

            System.out.println("Inventory loaded successfully.");

        } catch (IOException | NumberFormatException e) {

            /*
             * Handles:
             * - File read errors
             * - Corrupt data (non-integer values)
             */
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

/**
 * =========================================================================
 * CLASS - RoomInventory
 * =========================================================================
 * * Description:
 * Represents centralized storage of room availability.
 *
 * * Responsibilities:
 * - Maintain room counts
 * - Provide update functionality
 * - Display inventory state
 *
 * * @version 12.1
 */
class RoomInventory {

    /**
     * Map storing room type and availability count.
     */
    private Map<String, Integer> rooms = new HashMap<>();

    /**
     * Constructor initializes default inventory.
     * Used when no saved data exists.
     */
    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    /**
     * Updates room count.
     *
     * @param type  Room type
     * @param count Available rooms
     */
    public void updateRoom(String type, int count) {
        rooms.put(type, count);
    }

    /**
     * Returns inventory map.
     *
     * @return Map of room data
     */
    public Map<String, Integer> getRooms() {
        return rooms;
    }

    /**
     * Displays current inventory to console.
     */
    public void display() {
        System.out.println("\nCurrent Inventory:");

        rooms.forEach((type, count) ->
                System.out.println(type + ": " + count)
        );
    }
}

/**
 * =========================================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================================
 * * Use Case 12: Data Persistence & System Recovery
 *
 * * Description:
 * Demonstrates how application restores
 * inventory data after restart.
 *
 * * Flow:
 * 1. Load inventory from file
 * 2. Display current state
 * 3. Save inventory back to file
 *
 * * @version 12.1
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("=== System Recovery ===");

        /*
         * File used for persistence storage.
         */
        String filePath = "inventory_state.txt";

        /*
         * Initialize core components.
         */
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        /*
         * Step 1: Load existing inventory (if available).
         */
        persistenceService.loadInventory(inventory, filePath);

        /*
         * Step 2: Display inventory after recovery.
         */
        inventory.display();

        /*
         * Step 3: Save current state for next run.
         */
        persistenceService.saveInventory(inventory, filePath);
    }
}
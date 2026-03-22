import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================================
 * CLASS - CancellationService
 * =========================================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class is responsible for handling booking cancellations.
 *
 * It ensures that:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 *
 * A stack is used to model rollback behavior.
 *
 * @version 10.0
 */
class CancellationService {

    /** Stack that stores recently released room IDs. */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type. */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes the cancellation service. */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking for potential cancellation.
     *
     * @param reservationId reservation ID
     * @param roomType type of room booked
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking and restores inventory safely.
     *
     * @param reservationId reservation ID
     * @param inventory centralized inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        String roomType = reservationRoomTypeMap.get(reservationId);

        if (roomType != null) {
            // Restore inventory
            inventory.updateAvailability(roomType, 1);
            // Track released room in stack
            releasedRoomIds.push(reservationId);
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Error: Reservation ID not found.");
        }
    }

    /**
     * Displays recently cancelled reservations (Rollback History).
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No recent cancellations.");
        } else {
            // Stack provides LIFO (Last-In, First-Out) order
            for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
            }
        }
    }
}

/**
 * Supporting RoomInventory class (as required by method parameters)
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
    }

    public void updateAvailability(String type, int delta) {
        inventory.put(type, inventory.getOrDefault(type, 0) + delta);
    }
    
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase10CancellationRollback
 * =========================================================================
 *
 * Description:
 * This class demonstrates how bookings are cancelled
 * and inventory is restored using a Stack.
 *
 * @version 10.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        CancellationService cancelService = new CancellationService();

        // Simulate a booking record
        String resId = "Single-1";
        cancelService.registerBooking(resId, "Single");

        // Perform cancellation (matches terminal output)
        cancelService.cancelBooking(resId, inventory);

        // Display rollback history
        cancelService.showRollbackHistory();
        
        System.out.println("\nUpdated Inventory: " + inventory.getAvailability("Single"));
    }
}
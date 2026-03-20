import java.util.*;

/**
 * =============================================================================
 * CLASS - RoomAllocationService
 * =============================================================================
 * * Use Case 6: Reservation Confirmation & Room Allocation
 * * Description:
 * This class is responsible for confirming
 * booking requests and assigning rooms.
 * * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 * * @version 6.0
 */
class RoomAllocationService {

    /** Stores all allocated room IDs to prevent duplicate assignments. */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs by room type. */
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     * * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        int currentCount = inventory.getRoomAvailability().getOrDefault(type, 0);

        if (currentCount > 0) {
            String roomId = generateRoomId(type);
            
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
            
            inventory.updateAvailability(type, currentCount - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + 
                               ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() + 
                               ". No " + type + " rooms available.");
        }
    }

    /**
     * Generates a unique room ID 
     * for the given room type.
     * * @param roomType type of room
     * @return unique room ID
     */
    private String generateRoomId(String roomType) {
        int nextNumber = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + nextNumber;
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.offer(new Reservation("Abhi", "Single"));
        bookingQueue.offer(new Reservation("Subha", "Single"));
        bookingQueue.offer(new Reservation("Vanmathi", "Suite"));

        while (!bookingQueue.isEmpty()) {
            allocationService.allocateRoom(bookingQueue.poll(), inventory);
        }
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();
    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }
    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
    public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
}

class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String name, String type) { this.guestName = name; this.roomType = type; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}
/**
 * =========================================================================
 * CLASS - ConcurrentBookingProcessor
 * =========================================================================
 * * Use Case 11: Concurrent Booking Simulation
 * * Description:
 * This class represents a booking processor
 * that can be executed by multiple threads.
 * * It demonstrates how shared resources
 * such as booking queues and inventory
 * must be accessed in a thread-safe manner.
 * * @version 11.0
 */
class ConcurrentBookingProcessor implements Runnable {

    /**
     * Shared booking request queue.
     */
    private BookingRequestQueue bookingQueue;

    /**
     * Shared room inventory.
     */
    private RoomInventory inventory;

    /**
     * Shared room allocation service.
     */
    private RoomAllocationService allocationService;

    /**
     * Creates a new booking processor.
     * * @param bookingQueue shared booking queue
     * @param inventory shared inventory
     * @param allocationService shared allocation service
     */
    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    /**
     * Executes booking processing logic.
     * * This method is called when the thread starts.
     */
    @Override
    public void run() {

        while (true) {
            Reservation reservation;

            /*
             * Synchronize on the booking queue to ensure
             * that only one thread can retrieve a request
             * at a time.
             */
            synchronized (bookingQueue) {
                reservation = bookingQueue.poll();
                if (reservation == null) break;
            }

            /*
             * Allocation also mutates shared inventory.
             * Synchronization ensures atomic allocation.
             */
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * =========================================================================
 * * Use Case 11: Concurrent Booking Simulation
 * * Description:
 * This class simulates multiple users
 * attempting to book rooms at the same time.
 * * It highlights race conditions and
 * demonstrates how synchronization
 * prevents inconsistent allocations.
 * * @version 11.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // ... (Initialization of bookingQueue, inventory, and allocationService)

        // Create booking processor tasks
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }
    }
}
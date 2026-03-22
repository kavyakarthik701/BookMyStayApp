import java.util.*;

/**
 * =========================================================================
 * CLASS - Service
 * =========================================================================
 * * Use Case 7: Add-On Service Selection
 * * Description:
 * This class represents an optional service
 * that can be added to a confirmed reservation.
 * * Examples:
 * - Breakfast
 * - Spa
 * - Airport Pickup
 * * @version 7.0
 */
class Service {
    /**
     * Name of the service.
     */
    private String serviceName;

    /**
     * Cost of the service.
     */
    private double cost;

    /**
     * Creates a new add-on service.
     * * @param serviceName name of the service
     * @param cost cost of the service
     */
    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    /**
     * @return service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return service cost
     */
    public double getCost() {
        return cost;
    }
}

public class BookMyStayApp {

    /**
     * Maps reservation ID to selected services.
     * * Key   -> Reservation ID
     * Value -> List of selected services
     */
    private Map<String, List<Service>> servicesByReservation;

    /**
     * Initializes the service manager.
     */
    public BookMyStayApp() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     * * @param reservationId confirmed reservation ID
     * @param service add-on service
     */
    public void addService(String reservationId, Service service) {
        // If the reservation doesn't exist in the map, create a new list
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        // Add the service to the list associated with the ID
        servicesByReservation.get(reservationId).add(service);
    }

    /**
     * Calculates total add-on cost
     * for a reservation.
     * * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        List<Service> services = servicesByReservation.get(reservationId);
        
        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }

    /**
     * Main method to demonstrate the functionality based on the provided output image.
     */
    public static void main(String[] args) {
        BookMyStayApp manager = new BookMyStayApp();
        String resId = "Single-1";

        // Example data to reach the 1500.0 total shown in your image
        manager.addService(resId, new Service("Spa", 1000.0));
        manager.addService(resId, new Service("Breakfast", 500.0));

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + resId);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(resId));
    }
}
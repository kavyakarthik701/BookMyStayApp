import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================
 * CLASS - InvalidBookingException
 * =========================================================================
 * * Use Case 9: Error Handling & Validation
 * * Description:
 * This custom exception represents
 * invalid booking scenarios in the system.
 * * Using a domain-specific exception
 * makes error handling clearer and safer.
 * * @version 9.0
 */
class InvalidBookingException extends Exception {

    /**
     * Creates an exception with
     * a descriptive error message.
     * * @param message error description
     */
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * Supporting class: RoomInventory
 * (Required for the validate method parameters)
 */
class RoomInventory {
    private List<String> validTypes;

    public RoomInventory() {
        validTypes = new ArrayList<>();
        validTypes.add("Single");
        validTypes.add("Double");
        validTypes.add("Suite");
    }

    public boolean isValidType(String type) {
        // Matches the screenshot requirement where "single" (lowercase) fails
        return validTypes.contains(type);
    }
}

/**
 * Supporting class: BookingRequestQueue
 * (Required for the main method initialization)
 */
class BookingRequestQueue {
    public void addRequest(String name, String type) {
        // Implementation for adding to queue
    }
}

/**
 * =========================================================================
 * CLASS - ReservationValidator
 * =========================================================================
 * * Use Case 9: Error Handling & Validation
 * * Description:
 * This class is responsible for validating
 * booking requests before they are processed.
 * * All validation rules are centralized
 * to avoid duplication and inconsistency.
 * * @version 9.0
 */
class ReservationValidator {

    /**
     * Validates booking input provided by the user.
     * * @param guestName name of the guest
     * @param roomType requested room type
     * @param inventory centralized inventory
     * @throws InvalidBookingException if validation fails
     */
    public void validate(
            String guestName, 
            String roomType, 
            RoomInventory inventory
    ) throws InvalidBookingException {
        
        // Validation logic to trigger the error seen in the screenshot
        if (!inventory.isValidType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * =========================================================================
 * * Use Case 9: Error Handling & Validation
 * * Description:
 * This class demonstrates how user input
 * is validated before booking is processed.
 * * The system:
 * - Accepts user input
 * - Validates input centrally
 * - Handles errors gracefully
 * * @version 9.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        
        // Display application header
        System.out.println("Booking Validation");
        
        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Manual input prompts to match the terminal output image
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();
            
            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Perform Validation
            validator.validate(guestName, roomType, inventory);

            // If valid, add to queue
            bookingQueue.addRequest(guestName, roomType);

        } catch (InvalidBookingException e) {
            
            // Handle domain-specific validation errors
            System.out.println("Booking failed: " + e.getMessage());
            
        } finally {
            // Ensure resource is closed
            scanner.close();
        }
    }
}
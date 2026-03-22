import java.util.*;

/**
 * =========================================================================
 * CLASS - BookingHistory
 * =========================================================================
 * Use Case 8: Booking History & Reporting
 * Description: This class maintains a record of confirmed reservations.
 * It provides ordered storage for historical and reporting purposes.
 */
class BookingHistory {
    /** List that stores confirmed reservations. */
    private List<Reservation> confirmedReservations;

    /** Initializes an empty booking history. */
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    /** Adds a confirmed reservation to booking history. */
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /** Returns all confirmed reservations. */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * =========================================================================
 * CLASS - BookingReportService
 * =========================================================================
 * Description: This class generates reports from booking history data.
 * Reporting logic is separated from data storage.
 */
class BookingReportService {
    /** Displays a summary report of all confirmed bookings. */
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        for (Reservation res : history.getConfirmedReservations()) {
            System.out.println("Guest: " + res.getGuestName() + ", Room Type: " + res.getRoomType());
        }
    }
}

/**
 * Supporting class based on the reporting requirements.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * =========================================================================
 * Description: This class demonstrates how confirmed bookings are stored and reported.
 * The system maintains an ordered audit trail of reservations.
 */
public class BookMyStayApp {
    /** Application entry point. */
    public static void main(String[] args) {
        System.out.println("Booking History and Reporting\n");

        // Initialize history and report service
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Adding data to match the provided output image
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate the report
        reportService.generateReport(history);
    }
}
package lab_5.Comparator;

import lab_5.Booking;

import java.util.Comparator;

/**
 * Клас, що містить компаратори для порівняння об'єктів Booking.
 */
public class BookingComparator {
    public static Comparator<Booking> byStartDate() {
        return Comparator.comparing(Booking::getStartDate);
    }

    public static Comparator<Booking> byEndDate() {
        return Comparator.comparing(Booking::getEndDate);
    }

    public static Comparator<Booking> byCustomerName() {
        return Comparator.comparing(booking -> booking.getCustomer().getLastName());
    }
}

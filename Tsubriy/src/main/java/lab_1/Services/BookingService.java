package lab_1.Services;

import lab_1.Room;
import lab_1.Customer;
import lab_1.Booking;
import lab_1.Comparator.RoomComparator;
import lab_1.Comparator.BookingComparator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервіс для роботи з бронюванням, номерами та замовниками.
 */
public class BookingService {

    /**
     * Повертає список доступних кімнат (не зайнятих у вказані дати).
     * @param rooms список всіх кімнат
     * @param bookings список бронювань
     * @return список доступних кімнат
     */
    public List<Room> getAvailableRooms(List<Room> rooms, List<Booking> bookings) {
        return rooms.stream()
                .filter(room -> bookings.stream()
                        .noneMatch(booking -> booking.getRoom().equals(room)))
                .collect(Collectors.toList());
    }

    /**
     * Повертає список бронювань відсортованих за датою початку.
     * @param bookings список бронювань
     * @return відсортований список бронювань
     */
    public List<Booking> sortBookingsByStartDate(List<Booking> bookings) {
        return bookings.stream()
                .sorted(BookingComparator.byStartDate())
                .collect(Collectors.toList());
    }

    /**
     * Повертає список номерів, відсортованих за кількістю ліжок.
     * @param rooms список номерів
     * @return відсортований список номерів
     */
    public List<Room> sortRoomsByBeds(List<Room> rooms) {
        return rooms.stream()
                .sorted(RoomComparator.byNumberOfBeds())
                .collect(Collectors.toList());
    }

    /**
     * Повертає список бронювань, здійснених конкретним замовником.
     * @param bookings список всіх бронювань
     * @param customer замовник
     * @return список бронювань, здійснених замовником
     */
    public List<Booking> getBookingsByCustomer(List<Booking> bookings, Customer customer) {
        return bookings.stream()
                .filter(booking -> booking.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    /**
     * Повертає список бронювань, які були здійснені після вказаної дати.
     * @param bookings список всіх бронювань
     * @param date дата для фільтрації
     * @return список бронювань
     */
    public List<Booking> getBookingsAfterDate(List<Booking> bookings, Date date) {
        return bookings.stream()
                .filter(booking -> booking.getStartDate().after(date))
                .collect(Collectors.toList());
    }
}

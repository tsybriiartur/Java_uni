package lab_5;

import lab_5.DAO.DataSource;
import lab_5.DAO.RoomDao;
import lab_5.DAO.CustomerDao;
import lab_5.DAO.BookingDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Initialize database and create tables
        DataSource.createTablesStructure();

        // Create and add Room
        Room room = Room.builder()
                .setRoomType("Deluxe")
                .setNumberOfBeds(2)
                .setRoomNumber(101)
                .setAmenities("Wi-Fi, AC, TV")
                .build();
        RoomDao roomDao = new RoomDao();
        roomDao.add(room);

        // Create and add Customer
        Customer customer = new Customer.Builder()
                .setLastName("Doe")
                .setFirstName("John")
                .setIdDocument("ID123456")
                .setBirthDate(LocalDate.of(1985, 6, 15))
                .build();
        CustomerDao customerDao = new CustomerDao();
        customerDao.add(customer);

        // Create and add Booking
        Booking booking = Booking.builder()
                .room(room)
                .customer(customer)
                .startDate(LocalDate.of(2023, 7, 20))
                .endDate(LocalDate.of(2023, 7, 25))
                .isPaid(true)
                .build();
        BookingDao bookingDao = new BookingDao();
        bookingDao.add(booking);

        // Retrieve and print all Rooms
        List<Room> allRooms = roomDao.getAll();
        System.out.println("All Rooms:");
        for (Room r : allRooms) {
            System.out.println(r);
        }

        // Retrieve and print all Customers
        List<Customer> allCustomers = customerDao.getAll();
        System.out.println("\nAll Customers:");
        for (Customer c : allCustomers) {
            System.out.println(c);
        }

        // Retrieve and print all Bookings
        List<Booking> allBookings = bookingDao.getAll();
        System.out.println("\nAll Bookings:");
        for (Booking b : allBookings) {
            System.out.println(b);
        }
    }
}

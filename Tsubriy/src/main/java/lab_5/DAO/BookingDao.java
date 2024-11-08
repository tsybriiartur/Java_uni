package lab_5.DAO;

import lab_5.Booking;
import lab_5.Room;
import lab_5.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private final Connection connection;
    static final String TABLE_NAME = "Booking";

    public BookingDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking add(Booking booking) throws SQLException {
        String insertBooking = String.format("INSERT INTO %s (room_number, customer_id, start_date, end_date, is_paid) VALUES (?, ?, ?, ?, ?);", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(insertBooking)) {
            ps.setInt(1, booking.getRoom().getRoomNumber());
            ps.setString(2, booking.getCustomer().getIdDocument());
            ps.setDate(3, Date.valueOf(booking.getStartDate()));
            ps.setDate(4, Date.valueOf(booking.getEndDate()));
            ps.setBoolean(5, booking.isPaid());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return booking;
            } else {
                throw new IllegalArgumentException("Error while creating booking " + booking);
            }
        }
    }

    public List<Booking> getAll() throws SQLException {
        String getAll = """
                        SELECT room.room_type, room.number_of_beds, room.room_number, room.amenities,
                               customer.last_name, customer.first_name, customer.id_document, customer.birth_date,
                               booking.start_date, booking.end_date, booking.is_paid
                        FROM booking
                        JOIN room ON booking.room_number = room.room_number
                        JOIN customer ON booking.customer_id = customer.id_document;
                    """;

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        List<Booking> bookings = new ArrayList<>();

        while (rs.next()) {
            bookings.add(fromResultSet(rs));
        }
        return bookings;
    }

    private Booking fromResultSet(ResultSet rs) throws SQLException {
        return Booking.builder()
                .room(new Room(
                        rs.getString("room_type"),
                        rs.getInt("number_of_beds"),
                        rs.getInt("room_number"),
                        rs.getString("amenities")
                ))
                .customer(new Customer(
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("id_document"),
                        rs.getDate("birth_date").toLocalDate()
                ))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .isPaid(rs.getBoolean("is_paid"))
                .build();
    }

}

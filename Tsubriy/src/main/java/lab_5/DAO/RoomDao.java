package lab_5.DAO;

import lab_5.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private final Connection connection;
    static final String TABLE_NAME = "Room";

    public RoomDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Room add(Room room) throws SQLException {
        String insertRoom = String.format("INSERT INTO %s (room_type, number_of_beds, room_number, amenities) VALUES (?, ?, ?, ?);", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(insertRoom)) {
            ps.setString(1, room.getRoomType());
            ps.setInt(2, room.getNumberOfBeds());
            ps.setInt(3, room.getRoomNumber());
            ps.setString(4, room.getAmenities());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return room;
            } else {
                throw new IllegalArgumentException("Error while creating room " + room);
            }
        }
    }

    public List<Room> getAll() throws SQLException {
        String getAll = String.format("SELECT * FROM %s;", TABLE_NAME);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        List<Room> rooms = new ArrayList<>();

        while (rs.next()) {
            rooms.add(fromResultSet(rs));
        }
        return rooms;
    }

    private Room fromResultSet(ResultSet rs) throws SQLException {
        return Room.builder()
                .setRoomType(rs.getString("room_type"))
                .setNumberOfBeds(rs.getInt("number_of_beds"))
                .setRoomNumber(rs.getInt("room_number"))
                .setAmenities(rs.getString("amenities"))
                .build();
    }
}

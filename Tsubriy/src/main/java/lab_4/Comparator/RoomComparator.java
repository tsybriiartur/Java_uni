package lab_4.Comparator;

import lab_4.Room;

import java.util.Comparator;

/**
 * Клас, що містить компаратори для порівняння об'єктів Room.
 */
public class RoomComparator {
    public static Comparator<Room> byRoomNumber() {
        return Comparator.comparingInt(Room::getRoomNumber);
    }

    public static Comparator<Room> byNumberOfBeds() {
        return Comparator.comparingInt(Room::getNumberOfBeds);
    }

    public static Comparator<Room> byRoomType() {
        return Comparator.comparing(Room::getRoomType);
    }
}

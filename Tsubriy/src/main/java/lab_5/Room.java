package lab_5;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;
/**
 * Клас, що представляє номер готелю.
 */
@Getter
@Data
@Builder
public class Room {
    private String roomType;
    private int numberOfBeds;
    private int roomNumber;
    private String amenities;

    // Приватний конструктор, що використовується Builder
    public Room(RoomBuilder builder) {
        this.roomType = builder.roomType;
        this.numberOfBeds = builder.numberOfBeds;
        this.roomNumber = builder.roomNumber;
        this.amenities = builder.amenities;
    }

    public Room(String roomType, int numberOfBeds, int roomNumber, String amenities) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomNumber = roomNumber;
        this.amenities = amenities;
    }

    /**
     * Метод для зручного відображення інформації про номер.
     * @return Строкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Room [Type: " + roomType + ", Number of Beds: " + numberOfBeds +
                ", Room Number: " + roomNumber + ", Amenities: " + amenities + "]";
    }

    /**
     * Перевизначення методу equals для порівняння об'єктів.
     * @param o Об'єкт для порівняння.
     * @return true якщо об'єкти рівні, інакше false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return numberOfBeds == room.numberOfBeds &&
                roomNumber == room.roomNumber &&
                Objects.equals(roomType, room.roomType) &&
                Objects.equals(amenities, room.amenities);
    }

    /**
     * Перевизначення методу hashCode для генерації хеш-коду.
     * @return хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomType, numberOfBeds, roomNumber, amenities);
    }

    /**
     * Патерн Builder для класу Room.
     */
    public static class RoomBuilder {
        private String roomType;
        private int numberOfBeds;
        private int roomNumber;
        private String amenities;

        public RoomBuilder setRoomType(String roomType) {
            this.roomType = roomType;
            return this;
        }

        public RoomBuilder setNumberOfBeds(int numberOfBeds) {
            this.numberOfBeds = numberOfBeds;
            return this;
        }

        public RoomBuilder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public RoomBuilder setAmenities(String amenities) {
            this.amenities = amenities;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}

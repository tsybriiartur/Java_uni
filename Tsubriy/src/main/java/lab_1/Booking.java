package lab_1;

import java.util.Objects;
import java.util.Date;
import lombok.Getter;

/**
 * Клас, що представляє бронювання номеру.
 */
@Getter
public class Booking {
    private Room room;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private boolean isPaid;

    public Booking(Room room, Customer customer, Date startDate, Date endDate, boolean isPaid) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = isPaid;
    }

    /**
     * Метод для зручного відображення інформації про бронювання.
     * @return Строкове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Booking [Room: " + room + ", Customer: " + customer +
                ", Start Date: " + startDate + ", End Date: " + endDate +
                ", Is Paid: " + isPaid + "]";
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
        Booking booking = (Booking) o;
        return isPaid == booking.isPaid &&
                Objects.equals(room, booking.room) &&
                Objects.equals(customer, booking.customer) &&
                Objects.equals(startDate, booking.startDate) &&
                Objects.equals(endDate, booking.endDate);
    }

    /**
     * Перевизначення методу hashCode для генерації хеш-коду.
     * @return хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(room, customer, startDate, endDate, isPaid);
    }
}

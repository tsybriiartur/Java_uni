package lab_5.DAO;

import lab_5.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private final Connection connection;
    static final String TABLE_NAME = "Customer";

    public CustomerDao() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer add(Customer customer) throws SQLException {
        String insertCustomer = String.format("INSERT INTO %s (last_name, first_name, id_document, birth_date) VALUES (?, ?, ?, ?);", TABLE_NAME);
        try (PreparedStatement ps = connection.prepareStatement(insertCustomer)) {
            ps.setString(1, customer.getLastName());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getIdDocument());
            ps.setDate(4, Date.valueOf(customer.getBirthDate()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return customer;
            } else {
                throw new IllegalArgumentException("Error while creating customer " + customer);
            }
        }
    }

    public List<Customer> getAll() throws SQLException {
        String getAll = String.format("SELECT * FROM %s;", TABLE_NAME);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(getAll);
        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            customers.add(fromResultSet(rs));
        }
        return customers;
    }

    private Customer fromResultSet(ResultSet rs) throws SQLException {
        return new Customer.Builder()
                .setLastName(rs.getString("last_name"))
                .setFirstName(rs.getString("first_name"))
                .setIdDocument(rs.getString("id_document"))
                .setBirthDate(rs.getDate("birth_date").toLocalDate())
                .build();

    }
}

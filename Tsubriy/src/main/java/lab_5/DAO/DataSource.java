package lab_5.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataSource {

    private static HikariConfig config;
    private static HikariDataSource ds;

    static {
        try (InputStream input = DataSource.class.getClassLoader().getResourceAsStream("datasource.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find datasource.properties");
            }
            prop.load(input);
            config = new HikariConfig(prop);
            ds = new HikariDataSource(config);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void createTablesStructure() throws SQLException {
        String createRoomTable = """
                CREATE TABLE IF NOT EXISTS room (
                    room_type TEXT,
                    number_of_beds INTEGER,
                    room_number INTEGER PRIMARY KEY,
                    amenities TEXT
                );
                """;

        String createCustomerTable = """
                CREATE TABLE IF NOT EXISTS customer (
                    id_document TEXT PRIMARY KEY,
                    last_name TEXT,
                    first_name TEXT,
                    birth_date DATE
                );
                """;

        String createBookingTable = """
                CREATE TABLE IF NOT EXISTS booking (
                    id SERIAL PRIMARY KEY,
                    room_number INTEGER REFERENCES room(room_number),
                    customer_id TEXT REFERENCES customer(id_document),
                    start_date DATE,
                    end_date DATE,
                    is_paid BOOLEAN
                );
                """;

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.execute(createRoomTable);
                st.execute(createCustomerTable);
                st.execute(createBookingTable);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}

package sk.posam.fsa.jdbc;

import java.sql.*;
import java.util.Objects;

public class JdbcApplication {

    private static final String URL = "jdbc:postgresql://localhost:5432/dvdrental";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";  // Treba zmenit na vase heslo :)

    public static void main(String[] args) {
        pocetHercov();
        zoznamHercov();
        Actor actor = najdiHercaPodlaId(123);
        if(!Objects.isNull(actor)) System.out.println(actor);
    }

    /**
     * Metoda vypise pocet hercov v DB
     */
    private static void pocetHercov() {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM actor");
        ) {
            resultSet.next();
            int countOfActors = resultSet.getInt(1);

            System.out.println(String.format("Pocet hercov v DB je %d", countOfActors));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda vypise meno a priezvisko kazdeho herca ulozeneho v DB
     */
    private static void zoznamHercov() {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT actor_id, CONCAT(first_name, ' ' , last_name) AS full_name FROM actor"
                );
        ) {
            while (resultSet.next()) {
                long actorId = resultSet.getInt("actor_id");
                String actorFullName = resultSet.getString("full_name");

                System.out.println(String.format("[%d] %s", actorId, actorFullName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda najde v DB herca so zadanym ID a vrati ho ako navratovu hodnotu
     */
    private static Actor najdiHercaPodlaId(int actorId) {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE actor_id = ?";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, actorId);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Actor(
                            resultSet.getInt("actor_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Metoda vytvori v DB noveho herca a vrati true, ak sa ho podarilo vlozit
     */
    private static boolean vlozHerca(String meno, String priezvisko) {
        // Nestihal som, ospravedlnujem sa :(
        return false;
    }
}

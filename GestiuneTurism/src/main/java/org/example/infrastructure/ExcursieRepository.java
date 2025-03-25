package org.example.infrastructure;

import org.example.domain.Excursie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repo.IExcursieRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExcursieRepository implements IExcursieRepository {
    private static final Logger LOG = LogManager.getLogger(ExcursieRepository.class);
    private DbConnection dbConnection;

    public ExcursieRepository(DbConnection dbConnection) {

        this.dbConnection = dbConnection;
    }

    @Override
    public Optional<Excursie> delete(Excursie entity) {
        LOG.info("Excursie - delete: {}", entity);

        String query = "DELETE FROM \"Excursii\" WHERE \"excursieId\" = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Excursie> findById(Integer integer) {
        LOG.info("Excursie - findById: {}", integer);

        String query = "SELECT * FROM \"Excursii\" WHERE \"excursieId\" = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Excursie(
                        resultSet.getInt("excursieId"),
                        resultSet.getString("obiectiv"),
                        resultSet.getInt("nrLocuri"),
                        resultSet.getInt("pret"),
                        resultSet.getString("firmaTransport"),
                        resultSet.getTimestamp("dataOraPlecare").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Excursie> findAll() {
        LOG.info("Excursie - findAll");
        String query = "SELECT * FROM \"Excursii\"";
        List<Excursie> excursii = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                excursii.add(new Excursie(
                        resultSet.getInt("excursieId"),
                        resultSet.getString("obiectiv"),
                        resultSet.getInt("nrLocuri"),
                        resultSet.getInt("pret"),
                        resultSet.getString("firmaTransport"),
                        resultSet.getTimestamp("dataOraPlecare").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return excursii;
    }

    @Override
    public Optional<Excursie> save(Excursie entity) {
        LOG.info("Excursie - save: {}", entity);

        String query = "INSERT INTO \"Excursii\"(obiectiv, \"firmaTransport\", \"nrLocuri\", \"dataOraPlecare\") VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getObiectiv());
            preparedStatement.setString(2, entity.getFirmaTransport());
            preparedStatement.setInt(3, entity.getNrLocuri());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getDataOraPlcare()));

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creation failed, ID not obtainable!");
            }

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Excursie> update(Excursie entity) {
        LOG.info("Excursie - update: {}", entity);

        String query = "UPDATE \"Excursii\" SET obiectiv=?, \"firmaTransport\"=?, \"nrLocuri\"=?, \"dataOraPlecare\"=? WHERE \"excursieId\"=?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);

            preparedStatement.setString(1, entity.getObiectiv());
            preparedStatement.setString(2, entity.getFirmaTransport());
            preparedStatement.setInt(3, entity.getNrLocuri());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getDataOraPlcare()));
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public int remaingSeats(Excursie entity) throws SQLException {
        LOG.info("Checking number of places for {}", entity);

        String query = "SELECT SUM(\"nrLocuriRezervate\") AS locuri_ramase FROM \"Rezervari\" WHERE \"excursieId\" = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int nr_locuri_coupate = resultSet.getInt("locuri_ramase");
                System.out.println(" " + nr_locuri_coupate + " - " + entity.getNrLocuri());
                return entity.getNrLocuri() - nr_locuri_coupate;
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        throw new SQLException("Excursia nu exista!");
    }
}

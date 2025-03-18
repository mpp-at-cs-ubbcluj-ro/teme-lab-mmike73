package org.example.infrastructure;

import org.example.domain.AgentieTurism;
import org.example.domain.Angajat;
import org.example.repo.AbstractDbRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AngajatRepository extends AbstractDbRepository<Integer, Angajat> {
    private final Logger LOG = LogManager.getLogger(this.getClass());

    public AngajatRepository(DbConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    public Optional<Angajat> delete(Angajat entity) {
        LOG.info("Angajat - delete {}", entity);

        String query = "DELETE FROM \"Angajati\" WHERE \"angajatId\" = ?";

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
    public Optional<Angajat> findById(Integer integer) {
        LOG.info("Finding angajat {}", integer);

        String query = "SELECT * FROM \"Angajati\" WHERE \"angajatId\" = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Angajat angajat = new Angajat(
                        resultSet.getInt("angajatId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        null
                );

                Integer agentieId = resultSet.getInt("agentieId");
                AgentieTurism agentieTurism = null;

                String queryAgentieTurism = "SELECT * FROM \"AgentiiTurism\" WHERE \"agentieId\" = ?";
                PreparedStatement preparedStatementAgentieTurism = dbConnection.getConn().prepareStatement(queryAgentieTurism);
                preparedStatementAgentieTurism.setInt(1, agentieId);
                ResultSet resultSetAgentieTurism = preparedStatementAgentieTurism.executeQuery();
                if (resultSetAgentieTurism.next()) {
                    agentieTurism = new AgentieTurism(
                        resultSetAgentieTurism.getInt("agentieId"),
                        resultSetAgentieTurism.getString("numeAgentie")
                    );
                }
                angajat.setAgentieTurism(agentieTurism);

                return Optional.of(angajat);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Angajat> findAll() {
        LOG.info("Angajati - findAll");

        String query = "SELECT * FROM \"Angajati\"";
        List<Angajat> angajati = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Angajat angajat = new Angajat(
                        resultSet.getInt("angajatId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        null
                );

                Integer agentieId = resultSet.getInt("agentieId");
                AgentieTurism agentieTurism = null;

                String queryAgentieTurism = "SELECT * FROM \"AgentieTurism\" WHERE \"agentieId\" = ?";
                PreparedStatement preparedStatementAgentieTurism = dbConnection.getConn().prepareStatement(queryAgentieTurism);
                preparedStatementAgentieTurism.setInt(1, agentieId);
                ResultSet resultSetAgentieTurism = preparedStatementAgentieTurism.executeQuery();
                if (resultSetAgentieTurism.next()) {
                    agentieTurism = new AgentieTurism(
                            resultSetAgentieTurism.getInt("agentieId"),
                            resultSetAgentieTurism.getString("numeAgentie")
                    );
                }
                angajat.setAgentieTurism(agentieTurism);

                angajati.add(angajat);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return angajati;
    }

    @Override
    public Optional<Angajat> save(Angajat entity) {
        LOG.info("Angajati - save: {}", entity);

        String query = "INSERT INTO \"Angajati\" (username, password, \"agentieId\") VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getAgentieTurism().getId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
            } else {
                throw new SQLException("Creating agency failed, no ID obtained.");
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Angajat> update(Angajat entity) {
        LOG.info("Angajat - update: {}", entity);

        String query = "UPDATE \"Angajati\" SET password=?, \"agentieId\"=? WHERE \"angajatId\"=?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);

            preparedStatement.setString(1, entity.getPassword());
            preparedStatement.setInt(2, entity.getAgentieTurism().getId());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeQuery();

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

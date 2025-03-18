package org.example.infrastructure;

import org.example.domain.AgentieTurism;
import org.example.domain.Angajat;
import org.example.domain.Excursie;
import org.example.domain.Rezervare;
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

public class AgentieRepository extends AbstractDbRepository<Integer, AgentieTurism> {
    private static final Logger LOG = LogManager.getLogger(AgentieRepository.class);

    public AgentieRepository(DbConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    public Optional<AgentieTurism> delete(AgentieTurism entity) {
        LOG.info("AgentieTurism - delete: {}", entity);

        String query = "DELETE FROM \"AgentieTurism\" WHERE \"agentieId\" = ?";

        try {
             PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
             preparedStatement.setInt(1, entity.getId());
             preparedStatement.executeUpdate();

             return Optional.of(entity);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<AgentieTurism> findById(Integer integer) {
        LOG.info("AgentieTurism - findById: {}", integer);

        String query = "SELECT * FROM \"AgentiiTurism\" WHERE \"agentieId\" = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new AgentieTurism(integer, resultSet.getString("numeAgentie")));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<AgentieTurism> findAll() {
        LOG.info("AgentieTurism - findAll");


        String query = "SELECT * FROM \"AgentieTurism\"";
        List<AgentieTurism> agentii = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                agentii.add(new AgentieTurism(
                        resultSet.getInt("agentieId"),
                        resultSet.getString("numeAgentie")
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return agentii;
    }

    @Override
    public Optional<AgentieTurism> save(AgentieTurism entity) {
        LOG.info("AgentieTurism - saved: {}" , entity.toString());


        String query = "INSERT INTO \"AgentiiTurism\" (\"numeAgentie\") VALUES (?)";
        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getNumeAgentie());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
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
    public Optional<AgentieTurism> update(AgentieTurism entity) {

        return Optional.empty();
    }
}

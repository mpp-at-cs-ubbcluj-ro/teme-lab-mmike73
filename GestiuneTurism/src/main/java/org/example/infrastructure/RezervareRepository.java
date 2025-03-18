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

public class RezervareRepository extends AbstractDbRepository<Integer, Rezervare> {
    private static final Logger LOG = LogManager.getLogger(RezervareRepository.class);

    public RezervareRepository(DbConnection dbConnection) {

        super(dbConnection);

    }

    @Override
    public Optional<Rezervare> delete(Rezervare entity) {
        LOG.info("Rezervare - delete: {}", entity);

        String query = "DELETE FROM Rezervari WHERE rezervareId = ?";

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
    public Optional<Rezervare> findById(Integer integer) {
        LOG.info("Rezervare - findById: {}", integer);

        String query = "SELECT * FROM Rezervari WHERE rezervareId = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Rezervare rezervare = new Rezervare(
                        resultSet.getInt("rezervareId"),
                        resultSet.getString("numeClient"),
                        resultSet.getInt("nrLocuriRezervate"),
                        resultSet.getString("telefon"),
                        null,
                        null
                );

                int idExcursie = resultSet.getInt("excursieId");
                String queryExcursie = "SELECT * FROM Excursii WHERE excursieId = ?";
                PreparedStatement preparedStatementExcursie = dbConnection.getConn().prepareStatement(queryExcursie);
                preparedStatementExcursie.setInt(1, idExcursie);
                ResultSet resultSetExcursie = preparedStatementExcursie.executeQuery();
                if (resultSetExcursie.next()) {
                    rezervare.setExcursie(new Excursie(
                            resultSetExcursie.getInt("excursieId"),
                            resultSetExcursie.getString("obiectiv"),
                            resultSetExcursie.getInt("nrLocuri"),
                            resultSetExcursie.getInt("pret"),
                            resultSetExcursie.getString("firmaTransport"),
                            resultSetExcursie.getTimestamp("dataOraPlecare").toLocalDateTime()
                    ));
                }

                int idAgentieTurism = resultSet.getInt("agentieId");
                String queryAgentieTurism = "SELECT * FROM AgentiiTurism WHERE agentieId = ?";
                PreparedStatement preparedStatementAgentie = dbConnection.getConn().prepareStatement(queryAgentieTurism);
                preparedStatementAgentie.setInt(1, idAgentieTurism);
                ResultSet resultSetAgentieTurism = preparedStatementAgentie.executeQuery();
                if (resultSetAgentieTurism.next()) {
                    rezervare.setAgentieTurism(new AgentieTurism(
                            idAgentieTurism,
                            resultSetAgentieTurism.getString("numeAgentie")
                    ));
                }

            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Rezervare> findAll() {
        LOG.info("Rezervare - findAll");

        String query = "SELECT * FROM Rezervari";
        List<Rezervare> rezervari = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Rezervare rezervare = new Rezervare(
                        resultSet.getInt("rezervareId"),
                        resultSet.getString("numeClient"),
                        resultSet.getInt("nrLocuriRezervate"),
                        resultSet.getString("telefon"),
                        null,
                        null
                );

                int idExcursie = resultSet.getInt("excursieId");
                String queryExcursie = "SELECT * FROM Excursii WHERE excursieId = ?";
                PreparedStatement preparedStatementExcursie = dbConnection.getConn().prepareStatement(queryExcursie);
                preparedStatementExcursie.setInt(1, idExcursie);
                ResultSet resultSetExcursie = preparedStatementExcursie.executeQuery();
                if (resultSetExcursie.next()) {
                    rezervare.setExcursie(new Excursie(
                            resultSetExcursie.getInt("excursieId"),
                            resultSetExcursie.getString("obiectiv"),
                            resultSetExcursie.getInt("nrLocuri"),
                            resultSetExcursie.getInt("pret"),
                            resultSetExcursie.getString("firmaTransport"),
                            resultSetExcursie.getTimestamp("dataOraPlecare").toLocalDateTime()
                    ));
                }

                int idAgentieTurism = resultSet.getInt("agentieId");
                String queryAgentieTurism = "SELECT * FROM AgentiiTurism WHERE agentieId = ?";
                PreparedStatement preparedStatementAgentie = dbConnection.getConn().prepareStatement(queryAgentieTurism);
                preparedStatementAgentie.setInt(1, idAgentieTurism);
                ResultSet resultSetAgentieTurism = preparedStatementAgentie.executeQuery();
                if (resultSetAgentieTurism.next()) {
                    rezervare.setAgentieTurism(new AgentieTurism(
                            idAgentieTurism,
                            resultSetAgentieTurism.getString("numeAgentie")
                    ));
                }

                rezervari.add(rezervare);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return rezervari;
    }

    @Override
    public Optional<Rezervare> save(Rezervare entity) {
        LOG.info("Rezervare - save: {}", entity);

        String query = "INSERT INTO \"Rezervari\"(\"numeClient\", \"agentieId\", \"excursieId\", telefon, \"nrLocuriRezervate\") VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getNumeClient());
            preparedStatement.setInt(2, entity.getAgentieTurism().getId());
            preparedStatement.setInt(3, entity.getExcursie().getId());
            preparedStatement.setString(4, entity.getNumarTelefon());
            preparedStatement.setInt(5, entity.getNrLocuriRezervate());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
            } else {
                throw new SQLException("Adding failed. No ID obtained.");
            }

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Rezervare> update(Rezervare entity) {
        LOG.info("Rezervare - update: {}", entity);

        String query = "UPDATE \"Rezervari\" SET \"numeClient\" = ?, \"agentieId\" = ?, \"excursieId\" = ?, telefon = ?, \"nrLocuriRezervate\" = ?";

        try {
            PreparedStatement preparedStatement = dbConnection.getConn().prepareStatement(query);
            preparedStatement.setString(1, entity.getNumeClient());
            preparedStatement.setInt(2, entity.getAgentieTurism().getId());
            preparedStatement.setInt(3, entity.getExcursie().getId());
            preparedStatement.setString(4, entity.getNumarTelefon());
            preparedStatement.setInt(5, entity.getNrLocuriRezervate());

            preparedStatement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }
}

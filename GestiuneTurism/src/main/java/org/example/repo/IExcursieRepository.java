package org.example.repo;

import org.example.domain.Excursie;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IExcursieRepository extends Repository<Integer, Excursie>{
    @Override
    Optional<Excursie> delete(Excursie entity);

    @Override
    List<Excursie> findAll();

    @Override
    Optional<Excursie> findById(Integer integer);

    @Override
    Optional<Excursie> save(Excursie entity);

    @Override
    Optional<Excursie> update(Excursie entity);

    int remaingSeats(Excursie entity) throws SQLException;

}

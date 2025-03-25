package org.example.repo;

import org.example.domain.Rezervare;

import java.util.List;
import java.util.Optional;

public interface IRezervareRepository extends Repository<Integer, Rezervare> {
    @Override
    Optional<Rezervare> delete(Rezervare entity);

    @Override
    List<Rezervare> findAll();

    @Override
    Optional<Rezervare> findById(Integer integer);

    @Override
    Optional<Rezervare> save(Rezervare entity);

    @Override
    Optional<Rezervare> update(Rezervare entity);
}

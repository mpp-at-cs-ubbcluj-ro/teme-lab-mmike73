package org.example.repo;

import org.example.domain.Angajat;

import java.util.List;
import java.util.Optional;

public interface IAngajatRepository extends Repository<Integer, Angajat> {
    @Override
    default Optional<Angajat> delete(Angajat entity) {
        return Optional.empty();
    }

    @Override
    default Optional<Angajat> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    default List<Angajat> findAll() {
        return List.of();
    }

    @Override
    default Optional<Angajat> save(Angajat entity) {
        return Optional.empty();
    }

    @Override
    default Optional<Angajat> update(Angajat entity) {
        return Optional.empty();
    }
}

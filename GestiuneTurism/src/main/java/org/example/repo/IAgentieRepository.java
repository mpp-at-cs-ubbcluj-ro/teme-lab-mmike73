package org.example.repo;

import org.example.domain.AgentieTurism;

import java.util.List;
import java.util.Optional;

public interface IAgentieRepository extends Repository<Integer, AgentieTurism> {
    @Override
    Optional<AgentieTurism> delete(AgentieTurism entity);

    @Override
    List<AgentieTurism> findAll();

    @Override
    Optional<AgentieTurism> findById(Integer integer);

    @Override
    Optional<AgentieTurism> save(AgentieTurism entity);

    @Override
    Optional<AgentieTurism> update(AgentieTurism entity);
}

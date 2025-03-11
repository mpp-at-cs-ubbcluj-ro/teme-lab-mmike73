package org.example.repo;

import org.example.domain.Entity;
import org.example.infrastructure.DbConnection;

public abstract class AbstractDbRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    DbConnection dbConnection;

    public AbstractDbRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}

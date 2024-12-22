package com.codeforall.online.javabank.persistence.daos.jpa;

import com.codeforall.online.javabank.model.Establishment;
import com.codeforall.online.javabank.persistence.daos.EstablishmentDao;
import org.springframework.stereotype.Repository;

/**
 * A JPA {@link EstablishmentDao} implementation
 */
@Repository
public class JpaEstablishmentDao extends JpaGenericDao<Establishment> implements EstablishmentDao {

    /**
     * @see JpaGenericDao#JpaGenericDao(Class)
     */
    public JpaEstablishmentDao() {
        super(Establishment.class);
    }
}

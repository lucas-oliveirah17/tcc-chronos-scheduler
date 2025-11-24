package br.com.barberscheduler.backend.service;

import org.hibernate.Session;
import jakarta.persistence.EntityManager;

public abstract class BaseService {

    private final EntityManager entityManager;

    protected BaseService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected void enableFilter(String filterName) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter(filterName).setParameter("ativo", true);
    }

    protected void disableFilter(String filterName) {
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter(filterName) != null) {
            session.disableFilter(filterName);
        }
    }
}

package ch.drshit.domain.services;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Created by timo on 16.12.16.
 */
public abstract class GenericService<T> {

    @PersistenceContext(unitName = "boxenmietenPU")
    protected EntityManager em;

    private CriteriaBuilder criteriaBuilder;

    private Class<T> clazz;

    private Root<T> root;

    public GenericService(Class clazz) {
        this.clazz = clazz;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = em.getCriteriaBuilder();
        root = getBasicQuery().from(clazz);
    }

    public void persist(T user) {
        em.persist(user);
    }

    public T merge(T user) {
        return em.merge(user);
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public List<T> getAll() {
        TypedQuery<T> query = em.createQuery(getBasicQuery());
        return query.getResultList();
    }

    public Optional<T> getById(int id) {
        CriteriaQuery<T> query = getBasicQuery();
        query.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<T> typedQuery = em.createQuery(query);
        return getSingleResult(typedQuery);
    }

    public Optional<T> getByName(String name) {
        CriteriaQuery<T> query = getBasicQuery();
        query.where(criteriaBuilder.equal(root.get("name"), name));
        TypedQuery<T> typedQuery = em.createQuery(query);
        return getSingleResult(typedQuery);
    }

    private Optional<T> getSingleResult(TypedQuery<T> typedQuery) {
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private CriteriaQuery<T> getBasicQuery() {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        return query;
    }
}

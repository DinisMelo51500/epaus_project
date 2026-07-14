package isel.sisinf.jpa.repo;

import isel.sisinf.jpa.Dal;
import isel.sisinf.jpa.entity.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    public Optional<Cliente> findByNif(String nif) {

        EntityManager em = Dal.getEntityManager();
        Cliente cliente = em.find(Cliente.class, nif);

        return Optional.ofNullable(cliente);
    }

    public List<Cliente> findAll() {
        
        EntityManager em = Dal.getEntityManager();
        em.getTransaction().begin();

        return em.createQuery(
                "select c from Cliente c",
                Cliente.class
        ).getResultList();
    }

    public void save(Cliente cliente) {
        EntityManager em = Dal.getEntityManager();
        em.persist(cliente);
    }

    public void delete(String nif) {

        EntityManager em = Dal.getEntityManager();

        Cliente cliente = em.find(Cliente.class, nif);

        if (cliente != null) {
            em.remove(cliente);
        }
    }
}
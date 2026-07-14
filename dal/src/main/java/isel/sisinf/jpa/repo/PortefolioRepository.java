package isel.sisinf.jpa.repo;

import isel.sisinf.jpa.entity.Portefolio;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PortefolioRepository {

    private final EntityManager em;

    public PortefolioRepository(EntityManager em) {
        this.em = em;
    }

    public List<Portefolio> findByCliente(String nif) {

        return em.createQuery("""
                select p
                from Portefolio p
                where p.cliente.nif = :nif
                """, Portefolio.class)
                .setParameter("nif", nif)
                .getResultList();
    }

    public Optional<Portefolio> findById(Long id) {

        Portefolio p = em.find(Portefolio.class, id);

        return Optional.ofNullable(p);
    }

    public Portefolio save(Portefolio p) {

        return em.merge(p);
    }

    public void updateValorTotal(Long id) {

        Portefolio p = em.find(Portefolio.class, id);

        if (p == null) return;

        // calcula via query (mais correto do que em Java)
        BigDecimal total = em.createQuery("""
                select sum(v.valorTotal)
                from ValorPosicao v
                where v.id.portefolio = :id
                """, BigDecimal.class)
                .setParameter("id", id)
                .getSingleResult();

        if (total == null) {
            total = BigDecimal.ZERO;
        }

        p.setValorTotal(total);
    }
}


package isel.sisinf.jpa.repo;

import isel.sisinf.jpa.entity.Posicao;
import isel.sisinf.jpa.entity.PosicaoId;
import isel.sisinf.jpa.entity.ValorPosicao;
import isel.sisinf.jpa.Dal;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PosicaoRepository {

    public List<ValorPosicao> findByClienteNif(String nif) {

        EntityManager em = Dal.getEntityManager();

        return em.createQuery("""
            SELECT vp
            FROM ValorPosicao vp
            JOIN Portefolio p
                ON p.id = vp.id.portefolio
            JOIN p.cliente c
            WHERE c.nif = :nif
        """, ValorPosicao.class)
        .setParameter("nif", nif)
        .getResultList();
    }
    public List<Posicao> findByPortefolio(Long portefolioId) {

        EntityManager em = Dal.getEntityManager();

        return em.createQuery("""
                select p
                from Posicao p
                where p.portefolio.id = :id
                """, Posicao.class)
                .setParameter("id", portefolioId)
                .getResultList();
    }

    public void addPosicao(Posicao posicao) {

        EntityManager em = Dal.getEntityManager();

        em.persist(posicao);
    }

    public void updateQuantidade(Long portefolioId,
                                 String isin,
                                 BigDecimal novaQuantidade) {

        EntityManager em = Dal.getEntityManager();

        PosicaoId id = new PosicaoId(portefolioId, isin);

        Posicao posicao = em.find(Posicao.class, id);

        if (posicao != null) {
            posicao.setQuantidade(novaQuantidade);
        }
    }

    public void remove(Long portefolioId, String isin) {

        EntityManager em = Dal.getEntityManager();

        PosicaoId id = new PosicaoId(portefolioId, isin);

        Posicao posicao = em.find(Posicao.class, id);

        if (posicao != null) {
            em.remove(posicao);
        }
    }

    public List<ValorPosicao> obterValorPosicoes(Long portefolioId) {

        EntityManager em = Dal.getEntityManager();

        return em.createQuery("""
                select v
                from ValorPosicao v
                where v.id.portefolio = :id
                """, ValorPosicao.class)
                .setParameter("id", portefolioId)
                .getResultList();
    }
}
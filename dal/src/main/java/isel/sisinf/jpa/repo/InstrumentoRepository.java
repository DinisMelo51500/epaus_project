package isel.sisinf.jpa.repo;

import isel.sisinf.jpa.Dal;
import isel.sisinf.jpa.entity.Instrumento;
import isel.sisinf.jpa.entity.ValorInstrumentoDiario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class InstrumentoRepository {


    public Optional<Instrumento> findByIsin(String isin) {

        EntityManager em = Dal.getEntityManager();

        Instrumento instrumento =
                em.find(Instrumento.class, isin);

        return Optional.ofNullable(instrumento);
    }

    public List<Instrumento> findByMercado(String mercadoId) {

        EntityManager em = Dal.getEntityManager();

        return em.createQuery("""
                select i
                from Instrumento i
                where i.mercado.id = :mercado
                """, Instrumento.class)
                .setParameter("mercado", mercadoId)
                .getResultList();
    }

    public List<ValorInstrumentoDiario> historico(String isin) {

        EntityManager em = Dal.getEntityManager();

        return em.createQuery("""
                select v
                from ValorInstrumentoDiario v
                where v.instrumento.id = :isin
                order by v.id.data
                """, ValorInstrumentoDiario.class)
                .setParameter("isin", isin)
                .getResultList();
    }
}
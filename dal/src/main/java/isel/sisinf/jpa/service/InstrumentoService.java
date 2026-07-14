package isel.sisinf.jpa.service;

import isel.sisinf.jpa.repo.InstrumentoRepository;
import isel.sisinf.jpa.Dal;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.math.BigDecimal;

public class InstrumentoService{

    private static final InstrumentoRepository repo = new InstrumentoRepository(); 

    public void actualizarValorDiario( String isin, LocalDate dataHora, BigDecimal valor) {

        EntityManager em = Dal.getEntityManager();

        try {

            em.getTransaction().begin();

            em.createNativeQuery(
                "CALL p_actualizaValorDiario(?1, ?2, ?3)"
            )
            .setParameter(1, isin)
            .setParameter(2, dataHora)
            .setParameter(3, valor)
            .executeUpdate();

            em.getTransaction().commit();

        } catch(Exception e) {

            if(em.getTransaction().isActive())
                em.getTransaction().rollback();

            throw e;
        }
    }
}
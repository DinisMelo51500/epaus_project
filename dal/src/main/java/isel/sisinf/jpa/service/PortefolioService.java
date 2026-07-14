package isel.sisinf.jpa.service;

import isel.sisinf.jpa.entity.Portefolio;
import isel.sisinf.jpa.entity.Cliente;
import isel.sisinf.jpa.repo.PortefolioRepository;
import isel.sisinf.jpa.Dal;
import jakarta.persistence.*;


import java.math.BigDecimal;

public class PortefolioService {

    public void criarPortefolio(String name, Cliente cliente){
        EntityManager em = Dal.getEntityManager();

        try{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();

            Portefolio portefolio = new Portefolio();

            portefolio.setCliente(cliente);
            portefolio.setNome(name);
            portefolio.setValorTotal(BigDecimal.ZERO);

            em.persist(portefolio);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }
    };

    public void adicionarInstrumento(){

    };

    public void comprarInstrumento(){

    };

    public void venderInstrumento(){

    };

    public void calcularValorTotal(){
        
    };

}

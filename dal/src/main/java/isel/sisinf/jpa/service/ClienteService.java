package isel.sisinf.jpa.service;

import isel.sisinf.jpa.entity.ContactoCliente;
import isel.sisinf.jpa.entity.ContactoEmail;
import isel.sisinf.jpa.entity.ContactoTelefone;
import isel.sisinf.jpa.entity.Cliente;
import isel.sisinf.jpa.repo.ClienteRepository;
import isel.sisinf.jpa.Dal;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private static final ClienteRepository repo = new ClienteRepository(); 

    public void criarCliente(String name, String nif, String cartCidadao, String contactType, String contact, String descricao){
        EntityManager em = Dal.getEntityManager();

        try{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.getTransaction().begin();

            ContactoCliente cliente = new ContactoCliente(
                nif,cartCidadao, name, contactType, contact, descricao);
            
            em.persist(cliente);

            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }

    };

    public boolean existsByTaxNumberOrCC(String cartCidadao, String nif) {

        List<Cliente> clientes = repo.findAll();
        for (Cliente cliente : clientes) {
            if(cliente.getNif().equals(nif) || cliente.getCartaoCidadao().equals(cartCidadao)){
                return true;
            }
        }
        return false;
    }

    public void atualizarCliente(String nif, String novoNome, String contactType, String oldContact, String newContact, String descricao){
        EntityManager em = Dal.getEntityManager();

        try{
            em.getTransaction().begin();

            Cliente cliente = em.find(Cliente.class, nif, LockModeType.OPTIMISTIC);

            if (cliente == null){
                throw new RuntimeException("Cliente não encontrado");//não deve acontecer porque há verificação disto antes, na app
            }

            em.lock(cliente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            if(contactType.equals("email")) {

                ContactoEmail email = em.createQuery("""
                    SELECT e
                    FROM ContactoEmail e
                    WHERE e.cliente.nif = :nif
                    AND e.email = :oldContact
                """, ContactoEmail.class)
                .setParameter("nif", nif)
                .setParameter("oldContact", oldContact)
                .setLockMode(LockModeType.OPTIMISTIC)
                .getSingleResult();

                email.setEmail(newContact);
                email.setDescricao(descricao);

            }else if(contactType.equals("telefone")) {

                ContactoTelefone tel = em.createQuery("""
                    SELECT t
                    FROM ContactoTelefone t
                    WHERE t.cliente.nif = :nif
                    AND t.telefone = :oldContact
                """, ContactoTelefone.class)
                .setParameter("nif", nif)
                .setParameter("contact", oldContact)
                .setLockMode(LockModeType.OPTIMISTIC)
                .getSingleResult();

                tel.setTelefone(newContact);
                tel.setDescricao(descricao);
            }
            cliente.setNome(novoNome);
            
            em.getTransaction().commit();
        }catch(OptimisticLockException e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }

            throw new RuntimeException("O Cliente foi alterado por outro utilizador");
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Optional<Cliente> findByNif(String nif) {
        return repo.findByNif(nif);
    }

}


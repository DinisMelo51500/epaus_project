package isel.sisinf;

import isel.sisinf.jpa.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testApp() {
        assertTrue(true);
    }

    public void testOptimisticLocking() {

        // Duas factories separadas -> dois connection pools separados
        // -> conexões físicas distintas -> commits visíveis entre sessões
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("epaus-project");
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("epaus-project");

        EntityManager em1 = emf1.createEntityManager();
        EntityManager em2 = emf2.createEntityManager();

        String nif = "123456789";

        try {

            System.out.println("=== INICIO TESTE ===");

            em1.getTransaction().begin();
            Cliente c1 = em1.find(Cliente.class, nif);
            System.out.println("c1 = " + c1);
            assertNotNull(c1);

            em2.getTransaction().begin();
            Cliente c2 = em2.find(Cliente.class, nif);
            System.out.println("c2 = " + c2);
            assertNotNull(c2);

            System.out.println("Versao c1 antes = " + c1.getVersao());
            System.out.println("Versao c2 antes = " + c2.getVersao());

            // Sessao 1 atualiza e faz commit -> versao incrementa na BD
            System.out.println("Sessao 1 vai atualizar");
            c1.setNome("AAA_" + System.nanoTime());
            em1.getTransaction().commit();
            System.out.println("Commit sessao 1 OK");

            em1.refresh(c1);
            System.out.println("Versao c1 depois = " + c1.getVersao());

            // Sessao 2 tenta commitar com a versao antiga -> deve falhar
            System.out.println("Sessao 2 vai atualizar");
            c2.setNome("BBB_" + System.nanoTime());

            try {
                System.out.println("A fazer commit da sessao 2...");
                em2.getTransaction().commit();

                System.out.println("Commit sessao 2 OK");
                fail("Era esperada uma OptimisticLockException");

            } catch (OptimisticLockException e) {
                System.out.println("OptimisticLockException apanhada corretamente!");
                if (em2.getTransaction().isActive()) {
                    em2.getTransaction().rollback();
                }

            } catch (Exception e) {
                // EclipseLink por vezes envolve a OptimisticLockException noutra exceção
                Throwable cause = e;
                boolean isOptimistic = false;
                while (cause != null) {
                    System.out.println("Cause: " + cause.getClass().getName());
                    if (cause instanceof OptimisticLockException) {
                        isOptimistic = true;
                        break;
                    }
                    cause = cause.getCause();
                }

                if (em2.getTransaction().isActive()) {
                    em2.getTransaction().rollback();
                }

                if (!isOptimistic) {
                    fail("Era esperada uma OptimisticLockException mas foi: " + e.getClass().getName());
                }

                System.out.println("OptimisticLockException apanhada corretamente (dentro de outra exceção)!");
            }

        } finally {
            System.out.println("fim teste");

            if (em1.getTransaction().isActive())
                em1.getTransaction().rollback();
            if (em2.getTransaction().isActive())
                em2.getTransaction().rollback();

            em1.close();
            em2.close();
            emf1.close();
            emf2.close();
        }
    }
}
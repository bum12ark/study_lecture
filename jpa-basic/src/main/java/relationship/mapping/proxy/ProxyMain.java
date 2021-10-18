package relationship.mapping.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProxyMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapping_proxy");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.close();
        emf.close();
    }
}

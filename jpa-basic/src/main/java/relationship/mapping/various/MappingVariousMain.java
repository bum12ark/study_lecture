package relationship.mapping.various;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MappingVariousMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapping_various");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.close();
        emf.close();
    }

}

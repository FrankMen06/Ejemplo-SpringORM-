import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.HibernateException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.metamodel.EntityType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
   private static final SessionFactory ourSessionFactory;

   static {
       try {
           Configuration configuration = new Configuration();
           configuration.configure();

           ourSessionFactory = configuration.buildSessionFactory();
       } catch (Throwable ex) {
           throw new ExceptionInInitializerError(ex);
       }
   }

   public static Session getSession() throws HibernateException {
       return ourSessionFactory.openSession();
   }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default"); // Nombre de tu unidad de persistencia
        EntityManager em = emf.createEntityManager();

        try {
            Metamodel metamodel = em.getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                String entityName = entityType.getName();
                List<?> resultList = em.createQuery("from " + entityName).getResultList();
                for (Object entity : resultList) {
                    System.out.println(entity);
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
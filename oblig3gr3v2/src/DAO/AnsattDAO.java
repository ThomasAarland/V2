package DAO;

import java.util.List;

import entity.Ansatt;
import entity.Avdeling;
import entity.Deltager;
import entity.Prosjekt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class AnsattDAO {

    private static EntityManagerFactory emf;

    public AnsattDAO() {
        emf = Persistence.createEntityManagerFactory("oblig3");
    }

    
    public Ansatt finnAnsattMedId(int ansatt_id) {

        EntityManager em = emf.createEntityManager();

        Ansatt ansatt = null;
        try {
            ansatt = em.find(Ansatt.class, ansatt_id);
        } finally {
            em.close();
        }
        return ansatt;
    }

    public Ansatt finnAnsattMedBrukernavn(String brukernavn){
        EntityManager em = emf.createEntityManager();
    
        Ansatt ansatt = null;
    
        try {
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn", Ansatt.class);
            query.setParameter("brukernavn", brukernavn);
            ansatt = query.getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("Ingen ansatt funnet med brukernavn: " + brukernavn);
        } finally {
            em.close();
        }
        return ansatt;
    }
    

    public static List<Ansatt> hentAlleAnsatte(){
        EntityManager em = emf.createEntityManager();

        try {
            String q = "select a from Ansatt as a";
            TypedQuery<Ansatt> query = em.createQuery(q, Ansatt.class);
            return query.getResultList();
        }   finally {
            em.close();
        }
    }
    
    public void oppdaterAnsattStilling(int ansatt_id, String stilling){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();

            Ansatt nyStilling = em.find(Ansatt.class, ansatt_id);

            nyStilling.setStilling(stilling);

            tx.commit();
        }
        catch(Throwable e){
        e.printStackTrace();
        if(tx.isActive()){
            tx.rollback();
        }
        }   finally {
            em.close();
        }
    }

    public void oppdaterAnsattMLonn(int ansatt_id, double mlonn){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();

            Ansatt nyMLonn = em.find(Ansatt.class, ansatt_id);

            nyMLonn.setMlonn(mlonn);

            tx.commit();
        }
        catch(Throwable e){
        e.printStackTrace();
        if(tx.isActive()){
            tx.rollback();
        }
        }   finally {
            em.close();
        }
    }
    public void oppdaterStillingOgLonn(int ansatt_id, String stilling, Double mlonn) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Ansatt a = em.find(Ansatt.class, ansatt_id);
            tx.begin();
            if (a != null) {
                a.setStilling(stilling);
                a.setMlonn(mlonn);
                em.merge(a);
            }
            tx.commit();

        } catch (Throwable e) {
            e.printStackTrace();
            tx.rollback();

        } finally {
            em.close();
        }
    }
    public void leggInnEnNyAnsatt(Ansatt a, int avdelings_id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();  //oppretter en ny rad i databasen

            Avdeling avdeling = em.find(Avdeling.class, avdelings_id);
            if (avdeling != null) {
                a.setAvdeling(avdeling);
            }

            
            em.persist(a);
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }


    public void registrerProsjektdeltagelse(int ansattId, int prosjektId) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            Prosjekt p = em.find(Prosjekt.class, prosjektId);
           
            Deltager de = new Deltager(a, p, null, 0);

            em.persist(de);
            
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
        
    }

    public void slettProsjektdeltagelse(int ansattId, int prosjektId) {
    	
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            Prosjekt p = em.find(Prosjekt.class, prosjektId);

            Deltager de = new Deltager(a, p, null, 0);
            em.remove(de);
            
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unused")
	private Deltager finndeltagere(int ansattId, int prosjektId) {
        
        String queryString = "SELECT de FROM Deltager de " 
                + "WHERE pd.Ansatt.ansatt_id = :ansattId AND pd.Prosjekt.prosjekt_id = :prosjektId";

        EntityManager em = emf.createEntityManager();

        Deltager de = null;
        try {
            TypedQuery<Deltager> query 
                    = em.createQuery(queryString, Deltager.class);
            query.setParameter("ansatt_id", ansattId);
            query.setParameter("prosjektId", prosjektId);
            de = query.getSingleResult();
            
        } catch (NoResultException e) {
             e.printStackTrace();
        } finally {
            em.close();
        }
        return de;
    }
    
}

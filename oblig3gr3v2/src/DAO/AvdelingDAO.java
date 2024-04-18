package DAO;

import java.util.List;

import entity.Ansatt;
import entity.Avdeling;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


public class AvdelingDAO {
    private EntityManagerFactory emf;

    public AvdelingDAO(){
        emf = Persistence.createEntityManagerFactory("oblig3");
    }

    public Avdeling finnAvdelingMedAvdelingsID(int avdelings_id){
        EntityManager em = emf.createEntityManager();

        Avdeling avdeling = null;
        try{
            avdeling = em.find(Avdeling.class, avdelings_id);
        } finally {
            em.close();
        }
        return avdeling;
    }

    public Avdeling finnAvdelingMedAvdelingsNavn( String avdeling_navn){
        EntityManager em = emf.createEntityManager();

        Avdeling avdeling = null;

        try {
            TypedQuery<Avdeling> query = em.createQuery("SELECT av FROM Avdeling av WHERE av.avdeling_navn = :avdeling_navn", Avdeling.class);
            query.setParameter("avdeling_navn", avdeling_navn);
            avdeling = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Ingen avdeling funnet med navn: " + avdeling_navn);
        }   finally {
            em.close();
        }
        return avdeling;
    }

    
    // usikker på om denne er riktig.
    // skal hente og skrive ut alle avdelingene
    // usikker på om den henter alle ansatte i avdeling...
    public List<Avdeling> hentAlleAvdelinger(){
        EntityManager em = emf.createEntityManager();

        try {
            String q = "select av from Avdeling as av";
            TypedQuery<Avdeling> query = em.createQuery(q, Avdeling.class);
            return query.getResultList();
            
        } finally {
           em.close(); 
        }
    }

    public Ansatt avdelingssjef(int avdelings_id){
        EntityManager em = emf.createEntityManager();
        Avdeling avdeling1 = em.find(Avdeling.class, avdelings_id);
        return avdeling1.getAvdeling_sjef();
        
    }

    // skal hente alle ansatte i en avdeling.
    public List<Ansatt> hentAlleAnsatteIAvdeling(int avdelings_id) {
        EntityManager em = emf.createEntityManager();
        Avdeling avdeling = em.find(Avdeling.class, avdelings_id);
    
        try {
            String q = "select a from Ansatt a where a.avdeling = :avdeling";
            TypedQuery<Ansatt> query = em.createQuery(q, Ansatt.class);
            query.setParameter("avdeling", avdeling);
            return query.getResultList();
    
        } finally {
            em.close();
        }
    }

    // uskikker om om denne er riktig
    // metode som endrer avdelingen til en ansatt
    public void endreAvdeling(int ansatt_id, int avdeling_id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Ansatt ansatt = em.find(Ansatt.class, ansatt_id);
        try {
            tx.begin();
            ansatt.setAvdelings_id(avdeling_id);
            tx.commit();
        } finally {
            em.close();
        }
    }
}

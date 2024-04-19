package DAO;

import java.util.List;

import entity.Prosjekt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class ProsjektDAO {

    private static EntityManagerFactory emf;

    public ProsjektDAO() {
        emf = Persistence.createEntityManagerFactory("oblig3");
    }

    public Prosjekt finnProsjektMedId(int prosjekt_id) {

        EntityManager em =  emf.createEntityManager();

        Prosjekt prosjekt = null;

        try{
            prosjekt = em.find(Prosjekt.class, prosjekt_id);
        }   finally {
            em.close();
        }
        return prosjekt;
    }

    public void leggInnEtNyttProsjekt(Prosjekt p) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();  //oppretter en ny rad i databasen
            em.persist(p);
            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public static List<Prosjekt> hentAlleProsjekt(){
        EntityManager em = emf.createEntityManager();

        try {
            String q = "select p from Prosjekt as p";
            TypedQuery<Prosjekt> query = em.createQuery(q, Prosjekt.class);
            return query.getResultList();
        }   finally {
            em.close();
        }
    }

    public void oppdaterProsjektBeskrivelse(int prosjekt_id, String prosjekt_beskrivelse){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Prosjekt nyBeskrivelse = em.find(Prosjekt.class, prosjekt_id);

            nyBeskrivelse.setProsjektBeskrivelse(prosjekt_beskrivelse);
            tx.commit(); // usikker på om den er riktig plassert
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }   finally {
            em.close();
        }
    }

    public void oppdaterProsjektNavn(int prosjekt_id, String navn){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Prosjekt Navn = em.find(Prosjekt.class, prosjekt_id);

            Navn.setProsjektNavn(navn);
            tx.commit(); // usikker på om den er riktig plassert
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }   finally {
            em.close();
        }
    }
}

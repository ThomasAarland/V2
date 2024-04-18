package entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(schema = "oblig3")
public class Ansatt {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int ansatt_id;

    @Column(unique =  true, nullable=false)
    private String brukernavn;

    private String fornavn;
    private String etternavn;
    private LocalDate ansettelsesDato;
    private String stilling;
    private double mlonn;
    //private int avdelings_id;

    public Ansatt(){

    }

    public Ansatt(String fornavn, String etternavn, LocalDate ansettelsesDato, String stilling, double mlonn){
        this.fornavn = fornavn; 
        this.etternavn = etternavn; 
        this.ansettelsesDato = ansettelsesDato; 
        this.stilling = stilling; 
        this.mlonn = mlonn;
    }

    @ManyToOne
    @JoinColumn(name = "avdelings_id")
    private Avdeling avdeling;

    @OneToMany(mappedBy = "ansatt")
    private List<Deltager> deltagere;
    
    @OneToOne(mappedBy = "avdeling_sjef")
    private Avdeling avdeling_sjef;

    public void skrivUt(String innrykk) {
        System.out.printf("%sAnsatt nr %d: %s %s, %s, %s, %.2f%n", innrykk, ansatt_id, fornavn, etternavn, ansettelsesDato, stilling, mlonn);
    }

    public void skrivUtMedProsjekter() {
        System.out.println();
        skrivUt("");
        deltagere.forEach(p -> p.skrivUt("\n   "));
    }

    public void leggTilDeltager(Deltager prosjektdeltager) {
        deltagere.add(prosjektdeltager);
    }

    public void fjernDeltager(Deltager prosjektdeltager) {
        deltagere.remove(prosjektdeltager);
    }


    public int getAnsatt_id(){
        return  ansatt_id;
    }


    public String  getBrukernavn() {
        return brukernavn;
    }

    public String getFornavn(){
        return fornavn;
    }

    public String getEtternavn(){
        return etternavn;
    }

    public LocalDate getAnsettelsesDato(){
        return ansettelsesDato;
    }

    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public double getMlonn() {
        return mlonn;
    }

    public void setMlonn(double mlonn) {
        this.mlonn = mlonn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public void setEtternavn (String etternavn) {
        this.etternavn = etternavn;
    }

    public void setAnsatt_id (int ansattId) {
        
    }

    public List<Deltager> getDeltagere(){
        return deltagere;
    }

    public void setAnsettelsesDato(LocalDate ansettelsesDato) {
        this.ansettelsesDato = ansettelsesDato;
    }
 
    public Avdeling getAvdeling() {
        return avdeling;
    }

    public void setAvdeling(Avdeling avdeling){
        this.avdeling = avdeling;
    }


}

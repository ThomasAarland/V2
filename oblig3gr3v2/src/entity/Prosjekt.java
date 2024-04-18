package entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "oblig3")
public class Prosjekt {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int prosjekt_id;

    private String prosjekt_navn;
    private String prosjekt_beskrivelse;


    @OneToMany(mappedBy="prosjekt", fetch = FetchType.EAGER)
    private List<Deltager> deltagere;


    public void skrivUt(String innrykk) {
        System.out.printf("%sProsjekt nr %d: %s, %s", innrykk, prosjekt_id, prosjekt_navn, prosjekt_beskrivelse);
    }

    public void skrivUtMedAnsatte() {
        System.out.println();
        skrivUt("");
        deltagere.forEach(a -> a.skrivUt("\n   "));
    }

    public void leggTilDeltager(Deltager prosjektdeltager) {
        deltagere.add(prosjektdeltager);
    }

    public void fjernDeltager(Deltager prosjektdeltager) {
        deltagere.remove(prosjektdeltager);
    }

    public int getProsjektID() {
        return prosjekt_id;
    }

    public String getNavn() {
        return prosjekt_navn;
    }

    public void setProsjektNavn(String navn) {
        this.prosjekt_navn = navn;
    }

    public String getProsjekt_beskrivelse() {
        return prosjekt_beskrivelse;
    }

    public void setProsjektBeskrivelse(String prosjekt_beskrivelse) {
        this.prosjekt_beskrivelse = prosjekt_beskrivelse;
    }


}

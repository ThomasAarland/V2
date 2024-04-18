package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(schema = "oblig3")
public class Deltager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deltager_id;


    // The value of the field Deltager.rolle is not usedJava(570425421)
    //??
    private String rolle; 
    private int arbeidstimer;

    @ManyToOne
    @JoinColumn(name = "ansatt_id")
    private Ansatt ansatt;

    @ManyToOne
    @JoinColumn(name = "prosjekt_id")
    private Prosjekt prosjekt;

    public Deltager(){}

    public Deltager(Ansatt ansatt, Prosjekt prosjekt, String rolle, int arbeidstimer) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.rolle = rolle;
        this.arbeidstimer = arbeidstimer;
        ansatt.leggTilDeltager(this);
        prosjekt.leggTilDeltager(this);
        ansatt.fjernDeltager(null);
        prosjekt.fjernDeltager(null);
    }

    public void skrivUt(String inntrykk){
        System.out.printf("%sDeltagelse: %s %s, %s, %s, %d timer", inntrykk,
                ansatt.getFornavn(), ansatt.getEtternavn(), prosjekt.getNavn(), rolle, arbeidstimer);        
    }   
}

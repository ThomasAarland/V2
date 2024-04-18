package entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "oblig3")
public class Avdeling {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int avdelings_id;
    private String avdeling_navn;

    @OneToOne
    @JoinColumn(name = "avdeling_sjef", referencedColumnName = "ansatt_id")
    private Ansatt avdeling_sjef;
    
    @OneToMany(mappedBy = "avdeling", fetch = FetchType.EAGER)
    private List<Ansatt> ansatt;

    public void skrivUt(String innrykk) {
        System.out.printf("%sAnsatt nr %d: %s ", innrykk, avdelings_id, avdeling_navn);
    }

    public void skrivUtMedAnsatte() {
        System.out.println();
        skrivUt("");
        ansatt.forEach(a -> a.skrivUt("\n   "));
    }

    public void setAvdelings_id(int avdelings_id){
        this.avdelings_id = avdelings_id;
    }
    

    public int getAvdelings_id() {
        return avdelings_id;
    }
    
    public String getAvdeling_navn() {
        return avdeling_navn;
    }

    public Ansatt getAvdeling_sjef() {
        return avdeling_sjef;
    }
}

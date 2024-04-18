package run;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import DAO.AnsattDAO;
import DAO.AvdelingDAO;
import entity.Ansatt;


public class UI {
    public static void main(String[] args) {
        AnsattDAO ansatt = new AnsattDAO();
        AvdelingDAO avdeling = new AvdelingDAO();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nVelkommen til ansattadministrasjonssystemet!");
            System.out.println("1. Søk etter ansatt på ansatt-id");
            System.out.println("2. Søk etter ansatt på brukernavn");
            System.out.println("3. Utlisting av alle ansatte");
            System.out.println("4. Oppdater en ansatts stilling og/eller lønn");
            System.out.println("5. Legg til en ny ansatt");
            System.out.println("6. Utlisting av ansatte i en avdeling");
            System.out.println("7. Avslutt");



            System.out.println("\nVelg et alternativ:");
            int valg = scanner.nextInt();
            scanner.nextLine(); 

            switch (valg) {
                case 1:
                    System.out.println("Skriv inn ansatt id: ");
                    int id = scanner.nextInt();
                    Ansatt a = ansatt.finnAnsattMedId(id);
                    if (a!= null) {
                        a.skrivUt("");
                    } else {
                        System.out.println("Ansatt ikke funnet med dette id-nummeret");
                    }
                    break;

                case 2:
                    System.out.println("Skriv inn brukernavn: ");
                    String brukernavn = scanner.nextLine();
                    Ansatt a4 = ansatt.finnAnsattMedBrukernavn(brukernavn);
                    if (a4!= null) {
                        a4.skrivUt("");
                    } else {
                        System.out.println("Ansatt ikke funnet med dette brukernavnet");
                    }
                    break;

                case 3:
                    List<Ansatt> ansatte = AnsattDAO.hentAlleAnsatte();
                    if (!ansatte.isEmpty()) {
                        System.out.println("Alle ansatte:");
                        for (Ansatt a1 : ansatte) {
                            a1.skrivUt("");
                        }
                    } else {
                        System.out.println("Ingen ansatte funnet");
                    }
                    break;
            
                case 4:
                    System.out.println("Skriv inn ansatt id: ");
                    int id1 = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("For oppdatering av stilling skriv: 1 \n For oppdatering av mnd Lønn skriv: 2 \n For oppdaterinig av begge skriv: 3");
                    int valg1 = scanner.nextInt();
                    scanner.nextLine();

                    switch(valg1) {
                        case 1:
                            System.out.println("Skriv inn den nye ansattes stilling: ");
                            String stilling = scanner.nextLine();
                            ansatt.oppdaterAnsattStilling(id1, stilling);
                            System.out.println("oppdattering gjennomført");
                            break;

                        case 2:
                            System.out.println("Skriv inn den nye ansattes måndes lønn: ");
                            double mndlonn= scanner.nextDouble();
                            ansatt.oppdaterAnsattMLonn(id1, mndlonn);
                            System.out.println("oppdattering gjennomført");
                            break;

                        case 3:
                            System.out.println("Skriv inn den nye ansattes stilling: ");
                            String stilling1 = scanner.nextLine();
                            System.out.println("Skriv inn den nye ansattes måndes lønn: ");
                            double mndlonn1= scanner.nextDouble();
                            ansatt.oppdaterStillingOgLonn(id1, stilling1, mndlonn1);
                            System.out.println("oppdattering gjennomført");
                            break;
                    }
                    break;


                case 5:

                    System.out.println("Skriv inn ny ansatt brukernavn: ");
                    String brukernavn3 = scanner.nextLine();
                    System.out.println("Skriv inn ny ansatt fornavn: ");
                    String fornavn3 = scanner.nextLine();
                    System.out.println("Skriv inn ny ansatt etternavn: ");
                    String ettenavn3 = scanner.nextLine();
                    System.out.println("Skriv inn ny ansatt ansettelsesDato: ");
                    LocalDate ansettelsesDato3 = LocalDate.parse(scanner.nextLine());
                    System.out.println("Skriv inn ny ansatt stilling: ");
                    String stilling3 = scanner.nextLine();
                    System.out.println("Skriv inn ny ansatt måndes lønn: ");
                    double mndlonn3 = scanner.nextDouble();


                    Ansatt a3 = new Ansatt();
                    // a.setAnsatt_id(id3);
                    a3.setBrukernavn(brukernavn3);
                    a3.setFornavn(fornavn3);
                    a3.setEtternavn(ettenavn3);
                    a3.setAnsettelsesDato(ansettelsesDato3);
                    a3.setStilling(stilling3);
                    a3.setMlonn(mndlonn3);

                    ansatt.leggInnEnNyAnsatt(a3);
                    break;

                case 6:
                    System.out.println("Skriv inn ønsket avdelings id: ");
                    int x = scanner.nextInt();
                    List<Ansatt> ansatte1 = avdeling.hentAlleAnsatteIAvdeling(x);
                    Ansatt xy = ansatte1.get(1);
                    Ansatt avdelingssjef = avdeling.avdelingssjef(xy.getAvdelings_id());

                    if (ansatte1.isEmpty()) {
                        System.out.println("Ingen ansatte funnet");
                    } else {
                        System.out.println("Alle ansatte i ønsket avdeling:");
                        for (Ansatt a6 : ansatte1) {
                            a6.skrivUt("");
                        }
                        System.out.println("Avdelingssjef i ønsket avdeling:");
                        avdelingssjef.skrivUt("");
                    }
                    break;

                case 7:
                    running = false;
                    System.out.println("Systemet avsluttet");
                    break;
                
            }
        }
    }
}
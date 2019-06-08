package com.example.pickgroup.services;


import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;

import java.util.ArrayList;

public class InitializeData {

    public ArrayList<StudentModel> initializeStudents(){
        ArrayList<StudentModel> studenti = new ArrayList<>();

        //Setto gli esami del primo studente
        ArrayList<String> esami = new ArrayList<>();
        esami.add("Matematica Discreta");
        esami.add("Ricerca Operativa");
        esami.add("Sicurezza");
        esami.add("Programmazione Avanzata");
        esami.add("Fisica 1");
        esami.add("Calcolo Scientifico");

        //Setto le lingue del primo studente
        ArrayList<String> lingue = new ArrayList<>();
        lingue.add("Italiano");
        lingue.add("Inglese");

        //Creo il primo studente Vincenzo
        studenti.add(new StudentModel("Vincenzo", "Pepe", "0512106666", "v.pio66",
                "v.pio66@studenti.unisa.it", "v.pio66", "pepe",
                StudentModel.TipoStudente.CORSO, esami, lingue));

        //Setto gli esami del secondo studente
        ArrayList<String> esami1 = new ArrayList<>();
        esami1.add("Matematica Discreta");
        esami1.add("Interazione Uomo Macchina");
        esami1.add("Sicurezza");
        esami1.add("Programmazione Avanzata");
        esami1.add("Fisica 1");
        esami1.add("Sistemi Operativi");

        //Setto le lingue del secondo studente
        ArrayList<String> lingue1 = new ArrayList<>();
        lingue1.add("Italiano");
        lingue1.add("Inglese");
        lingue1.add("Tedesco");

        //Creo il secondo studente Davide
        studenti.add(new StudentModel("Davide", "Senatore", "0512107777", "d.sen77",
                "d.sen77@studenti.unisa.it", "d.sen77", "sen",
                StudentModel.TipoStudente.FUORICORSO, esami1, lingue1));

        //Setto gli esami del terzo studente
        ArrayList<String> esami2 = new ArrayList<>();
        esami2.add("Ingegneria Del Software");
        esami2.add("Mobile Computing");
        esami2.add("Sicurezza");
        esami2.add("Programmazione Avanzata");
        esami2.add("Sistemi Operativi");
        esami2.add("Calcolo Scientifico");

        //Setto le lingue del terzo studente
        ArrayList<String> lingue2 = new ArrayList<>();
        lingue2.add("Tedesco");
        lingue2.add("Inglese");

        //Creo il terzo studente Loris
        studenti.add(new StudentModel("Loris", "Proto", "0512108888", "l.pro88",
                "l.pro88@studenti.unisa.it", "l.pro88", "proto",
                StudentModel.TipoStudente.ERASMUS, esami2, lingue2));

        return studenti;
    }

    public ArrayList<GroupModel> initializeGroup(ArrayList<StudentModel> studenti){
        TakeData take = new TakeData(studenti, null, null);
        ArrayList<GroupModel> gruppi = new ArrayList<>();

        //Creo gli studenti per il primo gruppo di PROGRAMMAZIONE AVANZATA
        ArrayList<StudentModel> partecipanti1 = new ArrayList<>();
        partecipanti1.add(take.getUser("v.pio66", "v.pio66"));
        partecipanti1.add(take.getUser("d.sen77", "d.sen77"));
        partecipanti1.add(take.getUser("l.pro88", "l.pro88"));

        GroupModel group1 = new GroupModel(partecipanti1, "Gruppo PA", "Programmazione Avanzata");
        gruppi.add(group1);

        //Creo gli studenti per il secondo gruppo di FISICA 1
        ArrayList<StudentModel> partecipanti2 = new ArrayList<>();
        partecipanti2.add(take.getUser("v.pio66", "v.pio66"));
        partecipanti2.add(take.getUser("d.sen77", "d.sen77"));

        GroupModel group2 = new GroupModel(partecipanti2, "Gruppo F1", "Fisica 1");
        gruppi.add(group2);

        //Creo gli studenti per il terzo gruppo di CALCOLO SCIENTIFICO
        ArrayList<StudentModel> partecipanti3 = new ArrayList<>();
        partecipanti3.add(take.getUser("v.pio66", "v.pio66"));
        partecipanti3.add(take.getUser("l.pro88", "l.pro88"));

        GroupModel group3 = new GroupModel(partecipanti3, "Gruppo CS", "Calcolo Scientifico");
        gruppi.add(group3);

        //Creo gli studenti per il quarto gruppo di SISTEMI OPERATIVI
        ArrayList<StudentModel> partecipanti4 = new ArrayList<>();
        partecipanti4.add(take.getUser("d.sen77", "d.sen77"));
        partecipanti4.add(take.getUser("l.pro88", "l.pro88"));

        GroupModel group4 = new GroupModel(partecipanti4, "Gruppo SO", "Sistemi Operativi");
        gruppi.add(group4);

        return gruppi;
    }

    public ArrayList<GroupRequestsModel> initializeGroupRequests(ArrayList<StudentModel> studenti, ArrayList<GroupModel> gruppi){
        TakeData take = new TakeData(studenti, gruppi, null);
        ArrayList<GroupRequestsModel> richieste = new ArrayList<>();
        StudentModel mittente = null;
        StudentModel destinatario = null;
        String esame;
        GroupRequestsModel richiestaInviata = null;

        //Richieste di gruppo inviate da v.pio66

        //Setto la prima richiesta
        mittente = take.getUser("v.pio66", "v.pio66");
        destinatario = take.getUser("l.pro88", "l.pro88");
        esame = "Programmazione Avanzata";
        richiestaInviata = new GroupRequestsModel(mittente, destinatario, esame);
        richieste.add(richiestaInviata);

        //Setto la seconda richiesta
        mittente = take.getUser("v.pio66", "v.pio66");
        destinatario = take.getUser("d.sen77", "d.sen77");
        esame = "Fisica 1";
        richiestaInviata = new GroupRequestsModel(mittente, destinatario, esame);
        richieste.add(richiestaInviata);

        //Setto la terza richiesta
        mittente = take.getUser("l.pro88", "l.pro88");
        destinatario = take.getUser("v.pio66", "v.pio66");
        esame = "Sicurezza";
        richiestaInviata = new GroupRequestsModel(mittente, destinatario, esame);
        richieste.add(richiestaInviata);

        //Setto la quarta richiesta
        mittente = take.getUser("d.sen77", "d.sen77");
        destinatario = take.getUser("l.pro88", "l.pro88");
        esame = "Sistemi Operativi";
        richiestaInviata = new GroupRequestsModel(mittente, destinatario, esame);
        richieste.add(richiestaInviata);

        //Setto la quinta richiesta
        mittente = take.getUser("d.sen77", "d.sen77");
        destinatario = take.getUser("v.pio66", "v.pio66");
        esame = "Matematica Discreta";
        richiestaInviata = new GroupRequestsModel(mittente, destinatario, esame);
        richieste.add(richiestaInviata);

        return richieste;
    }

}

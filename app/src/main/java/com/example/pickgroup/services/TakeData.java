package com.example.pickgroup.services;

import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;

import java.security.acl.Group;
import java.util.ArrayList;

public class TakeData {

    private ArrayList<StudentModel> studenti;
    private ArrayList<GroupModel> gruppi;
    private ArrayList<GroupRequestsModel> richieste;

    public TakeData(ArrayList<StudentModel> studenti, ArrayList<GroupModel> gruppi, ArrayList<GroupRequestsModel> richieste){
        this.studenti = studenti;
        this.gruppi = gruppi;
        this.richieste = richieste;
    }

    public StudentModel getUser(String username, String password){

        for(StudentModel st : studenti){
            if(st.getUsername().equals(username) && st.getPassword().equals(password))
                return st;
        }

        return null;
    }

    public ArrayList<GroupModel> getGroupsForStudent(String username, String password){
        StudentModel studente = getUser(username, password);
        ArrayList<GroupModel> gruppiStudente = new ArrayList<>();

        for(GroupModel gruppo : gruppi){
            for(StudentModel s : gruppo.getPartecipanti()){
                if(studente.equals(s))
                    gruppiStudente.add(gruppo);
            }
        }

        return gruppiStudente;
    }

    public ArrayList<StudentModel> getStudentsForExam(String esame, StudentModel studenteLog, String lingua){
        ArrayList<StudentModel> studentiExam = new ArrayList<>();

        if(lingua == null) {
            for (StudentModel studente : studenti) {
                for (String exam : studente.getEsami())
                    if (exam.equals(esame) && !studenteLog.getUsername().equals(studente.getUsername()) && !studenteLog.getPassword().equals(studente.getPassword()))
                        studentiExam.add(studente);
            }
        }else{
            for (StudentModel studente : studenti) {
                for (String exam : studente.getEsami())
                    if (exam.equals(esame) && !studenteLog.getUsername().equals(studente.getUsername()) && !studenteLog.getPassword().equals(studente.getPassword()))
                        for(String ling : studente.getLingue())
                            if(ling.equals(lingua))
                                studentiExam.add(studente);
            }
        }

        return studentiExam;
    }

    public ArrayList<GroupModel> getGroupForExame(String esame, StudentModel studenteLog){
        ArrayList<GroupModel> gruppiExm = new ArrayList<>();

        for(GroupModel gruppo : gruppi)
            for(StudentModel partecipante : gruppo.getPartecipanti())
                if(!partecipante.getUsername().equals(studenteLog.getUsername()) && !partecipante.getPassword().equals(studenteLog.getPassword()) && gruppo.getEsame().equals(esame))
                    gruppiExm.add(gruppo);

        return null;
    }

    public ArrayList<GroupRequestsModel> getAllGroupRequestForStudent(StudentModel studenteLog){
        ArrayList<GroupRequestsModel> richiesteGruppoInviate = new ArrayList<>();

        if(richieste != null){
            for(GroupRequestsModel request: richieste)
                if(request.getStudenteMittente().equals(studenteLog) || request.getStudenteDestinatario().equals(studenteLog))
                    richiesteGruppoInviate.add(request);
            return richiesteGruppoInviate;
        }

        return richiesteGruppoInviate;
    }

    public ArrayList<GroupRequestsModel> getSentGroupRequestForStudent(StudentModel studenteLog){
        ArrayList<GroupRequestsModel> richiesteGruppoInviate = new ArrayList<>();

        if(richieste != null){
            for(GroupRequestsModel request: richieste)
                if(request.getStudenteMittente().equals(studenteLog))
                    richiesteGruppoInviate.add(request);
        }

        return richiesteGruppoInviate;
    }

    public ArrayList<GroupRequestsModel> getReceiveGroupRequestForStudent(StudentModel studenteLog){
        ArrayList<GroupRequestsModel> richiesteGruppoRicevute = new ArrayList<>();

        if(richieste != null){
            for(GroupRequestsModel request: richieste)
                if(request.getStudenteDestinatario().equals(studenteLog))
                    richiesteGruppoRicevute.add(request);
        }

        return richiesteGruppoRicevute;
    }

    public String compositeGroupName(String nomeEsame){
        int i = 0;
        char l = nomeEsame.charAt(i);
        String name = "Gruppo"+" "+l;
        for(i = 1; i < nomeEsame.length(); i++){
            if(nomeEsame.charAt(i) == ' ') {
                name = name + nomeEsame.charAt(i+1);
            }
        }

        return name;
    }

    public ArrayList<GroupModel> getGroupForExam(String esame){
        ArrayList<GroupModel> gruppiExam = new ArrayList<>();

        for(GroupModel gruppo : gruppi){
            if(gruppo.getEsame().equals(esame))
                gruppiExam.add(gruppo);
        }

        return gruppiExam;
    }

}

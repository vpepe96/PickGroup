package com.example.pickgroup.model;

import java.io.Serializable;

public class GroupRequestsModel implements Serializable {

    private StudentModel studenteMittente;
    private StudentModel studenteDestinatario;
    private String esame;

    public GroupRequestsModel(){}

    public GroupRequestsModel(StudentModel studenteMittente, StudentModel studenteDestinatario, String esame){
        this.studenteMittente = studenteMittente;
        this.studenteDestinatario = studenteDestinatario;
        this.esame = esame;
    }

    public StudentModel getStudenteMittente() {
        return studenteMittente;
    }

    public StudentModel getStudenteDestinatario() {
        return studenteDestinatario;
    }

    public String getEsame(){
        return esame;
    }

    public void setStudenteMittente(StudentModel studenteMittente) {
        this.studenteMittente = studenteMittente;
    }

    public void setStudenteDestinatario(StudentModel studenteDestinatario) {
        this.studenteDestinatario = studenteDestinatario;
    }

    public void setEsame(String esame){
        this.esame = esame;
    }

}

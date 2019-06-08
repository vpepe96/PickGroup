package com.example.pickgroup.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupModel implements Serializable {

    private ArrayList<StudentModel> partecipanti;
    private String nome;
    private String esame;

    public GroupModel(){}

    public GroupModel(ArrayList<StudentModel> partecipanti, String nome, String esame){
        this.partecipanti = partecipanti;
        this.nome = nome;
        this.esame = esame;
    }

    public ArrayList<StudentModel> getPartecipanti() {
        return partecipanti;
    }

    public String getNome() {
        return nome;
    }

    public String getEsame() {
        return esame;
    }

    public void setPartecipanti(ArrayList<StudentModel> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEsame(String esame) {
        this.esame = esame;
    }

    public void addPartecipante(StudentModel partecipante){
        this.partecipanti.add(partecipante);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        GroupModel other = (GroupModel) otherObject;

        return partecipanti.equals(other.partecipanti) &&
                nome.equals(other.nome) &&
                esame.equals(other.esame);
    }

}

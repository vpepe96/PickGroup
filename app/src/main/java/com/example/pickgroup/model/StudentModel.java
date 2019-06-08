package com.example.pickgroup.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentModel implements Serializable {

    public enum TipoStudente {CORSO, FUORICORSO, ERASMUS};

    private String nome;
    private String cognome;
    private String matricola;
    private String username;
    private String email;
    private String password;
    private String foto;
    private TipoStudente tipo;
    private ArrayList<String> esami;
    private ArrayList<String> lingue;

    public StudentModel(){}

    public StudentModel(String nome, String cognome, String matricola, String username, String email, String password, String foto, TipoStudente tipo, ArrayList<String> esami, ArrayList<String> lingue){
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.username = username;
        this.email = email;
        this.password = password;
        this.foto = foto;
        this.tipo = tipo;
        this.esami = esami;
        this.lingue = lingue;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFoto(){
        return foto;
    }

    public TipoStudente getTipo(){
        return tipo;
    }

    public ArrayList<String> getEsami() {
        return esami;
    }

    public ArrayList<String> getLingue() {
        return lingue;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }

    public void setTipo(TipoStudente tipo){
        this.tipo = tipo;
    }

    public void setEsami(ArrayList<String> esami) {
        this.esami = esami;
    }

    public void setLingue(ArrayList<String> lingue) {
        this.lingue = lingue;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        StudentModel other = (StudentModel) otherObject;

        return nome.equals(other.nome) &&
                cognome.equals(other.cognome) &&
                matricola.equals(other.matricola) &&
                username.equals(other.username) &&
                email.equals(other.email) &&
                password.equals(other.password) &&
                foto.equals(other.foto) &&
                tipo == other.tipo;
    }
}

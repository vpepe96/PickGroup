package com.example.pickgroup.services;

import com.example.pickgroup.model.StudentModel;

import java.util.ArrayList;

public class CheckLogin {

    private ArrayList<StudentModel> studenti;

    public CheckLogin(){}

    public CheckLogin(ArrayList<StudentModel> studenti){
        this.studenti = studenti;
    }

    public boolean checkLogin(String username, String password){

        for(StudentModel st : studenti){
            if(st.getUsername().equals(username) && st.getPassword().equals(password))
                return true;
        }

        return false;
    }

}

package com.example.pickgroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;
import com.example.pickgroup.services.InitializeData;
import com.example.pickgroup.services.TakeData;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private StudentModel studenteLog;
    private ArrayList<StudentModel> studentiDate;
    private ArrayList<GroupModel> gruppiDate;
    private ArrayList<GroupRequestsModel> richiesteDate;
    private String esameSelezionato;
    private String linguaSelezionata;
    private StudentModel studenteSelezionato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        studenteLog = (StudentModel) bundle.getSerializable("studenteLoggato");
        studentiDate = (ArrayList<StudentModel>) bundle.getSerializable("studentiDate");
        gruppiDate = (ArrayList<GroupModel>) bundle.getSerializable("gruppiDate");
        richiesteDate = (ArrayList<GroupRequestsModel>) bundle.getSerializable("richiesteDate");
        esameSelezionato = bundle.getString("esameScelto");
        linguaSelezionata = bundle.getString("linguaScelta");
        studenteSelezionato = (StudentModel) bundle.getSerializable("studenteSelezionato");

        //Setto l'immagine del profilo dello studente
        ImageView imgProfilo = (ImageView) findViewById(R.id.imageViewImgProfile);
        String command = "@drawable/"+studenteLog.getFoto();
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(command, "string", packageName);
        imgProfilo.setImageResource(resId);

        //Setto il nome e il cognome dello studente
        TextView nameSurnameProfilo = (TextView) findViewById(R.id.textViewNameSurnameProfile);
        nameSurnameProfilo.setText(studenteLog.getNome()+" "+studenteLog.getCognome());

        //Setto i gruppi di studio dello studente loggato
        InitializeData init = new InitializeData();
        ArrayList<GroupModel> gruppi = new ArrayList<>();
        ArrayList<StudentModel> studenti = new ArrayList<>();
        ArrayList<GroupRequestsModel> richieste = new ArrayList<>();
        TakeData take = null;
        if(studentiDate == null) {
            studenti = init.initializeStudents();
        }else{
            studenti = studentiDate;
        }
        if(gruppiDate == null) {
            gruppi = init.initializeGroup(studentiDate);
        }else{
            gruppi = gruppiDate;
        }
        if(richiesteDate == null) {
            richieste = init.initializeGroupRequests(studenti, gruppi);
        }else{
            richieste = richiesteDate;
        }
        take = new TakeData(studenti, gruppi, richieste);
        ArrayList<GroupModel> gruppiLog = take.getGroupsForStudent(studenteLog.getUsername(), studenteLog.getPassword());
        LinearLayout gruppiNameProfilo = (LinearLayout) findViewById(R.id.linearLayoutNameGroupProfile);
        LinearLayout gruppiEsameProfilo = (LinearLayout) findViewById(R.id.linearLayoutExamGroupProfile);
        for(GroupModel group : gruppiLog) {
            TextView gruppoNameView = new TextView(this);
            gruppoNameView.setText(group.getNome());
            gruppoNameView.setHeight(110);
            gruppoNameView.setWidth(gruppiNameProfilo.getWidth());
            gruppoNameView.setPadding(40,15,0,0);
            gruppoNameView.setTextColor(Color.BLACK);
            gruppoNameView.setTextSize(18);
            gruppiNameProfilo.addView(gruppoNameView);

            TextView gruppoExameView = new TextView(this);
            gruppoExameView.setText(group.getEsame());
            gruppoExameView.setHeight(110);
            gruppoExameView.setWidth(gruppiEsameProfilo.getWidth());
            gruppoExameView.setPadding(40,15,0,0);
            gruppoExameView.setTextColor(Color.BLACK);
            gruppoExameView.setTextSize(18);
            gruppiEsameProfilo.addView(gruppoExameView);
        }

        //Setto i listener per i bottoni relativi alle richieste
        //Setto il link per le richieste inviate
        TextView textSendRequest = (TextView) findViewById(R.id.textViewSendRequest);
        textSendRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent openSentRequest = new Intent(ProfileActivity.this, SentRequestsActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("studenteLoggato", studenteLog);
                extras.putSerializable("studentiDate", studentiDate);
                extras.putSerializable("gruppiDate", gruppiDate);
                extras.putSerializable("richiesteDate", richiesteDate);
                extras.putString("esameScelto", esameSelezionato);
                extras.putString("linguaScelta", linguaSelezionata);
                extras.putSerializable("studenteScelto", studenteSelezionato);
                openSentRequest.putExtras(extras);
                startActivity(openSentRequest);
            }
        });

        //Setto il link per le richieste ricevute
        TextView textReceiveRequest = (TextView) findViewById(R.id.textViewReceiveRequest);
        textReceiveRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent openReceiveRequest = new Intent(ProfileActivity.this, ReceiveRequestsActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("studenteLoggato", studenteLog);
                extras.putSerializable("studentiDate", studentiDate);
                extras.putSerializable("gruppiDate", gruppiDate);
                extras.putSerializable("richiesteDate", richiesteDate);
                extras.putString("esameScelto", esameSelezionato);
                extras.putString("linguaScelta", linguaSelezionata);
                extras.putSerializable("studenteScelto", studenteSelezionato);
                openReceiveRequest.putExtras(extras);
                startActivity(openReceiveRequest);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //Setto i valori del menu laterale

        Bundle bundle = getIntent().getExtras();
        studenteLog = (StudentModel) bundle.getSerializable("studenteLoggato");

        //Setto l'immagine del profilo nel menu laterale dello studente loggato
        ImageView imgProfiloMenu = (ImageView) findViewById(R.id.imageViewProfiloGeneral);
        String command = "@drawable/"+studenteLog.getFoto();
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(command, "string", packageName);
        imgProfiloMenu.setImageResource(resId);


        //Setto nome e cognome nel menu laterale dello studente loggato
        TextView textNameSurnameMenu = (TextView) findViewById(R.id.textViewNameSurnameGeneral);
        textNameSurnameMenu.setText(studenteLog.getNome()+" "+studenteLog.getCognome());

        //Setto matricola nel menu laterale dello studente loggato
        TextView textMatricolaMenu = (TextView) findViewById(R.id.textViewMatricolaGeneral);
        textMatricolaMenu.setText("MATRICOLA - "+studenteLog.getMatricola());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile_profile) {
            Intent openProfile = new Intent( ProfileActivity.this, ProfileActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("studenteLoggato", studenteLog);
            extras.putSerializable("studentiDate", studentiDate);
            extras.putSerializable("gruppiDate", gruppiDate);
            extras.putSerializable("richiesteDate", richiesteDate);
            extras.putString("esameScelto", esameSelezionato);
            extras.putString("linguaScelta", linguaSelezionata);
            extras.putSerializable("studenteScelto", studenteSelezionato);
            openProfile.putExtras(extras);
            this.startActivity(openProfile);
            return true;
        } else if (id == R.id.nav_profile_search_exams) {
            Intent openExamList = new Intent(this, ExamListActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("studenteLoggato", studenteLog);
            extras.putSerializable("studentiDate", studentiDate);
            extras.putSerializable("gruppiDate", gruppiDate);
            extras.putSerializable("richiesteDate", richiesteDate);
            extras.putString("esameScelto", esameSelezionato);
            extras.putString("linguaScelta", linguaSelezionata);
            extras.putSerializable("studenteScelto", studenteSelezionato);
            openExamList.putExtras(extras);
            this.startActivity(openExamList);
        } else if (id == R.id.nav_profile_search_group) {
            Intent openSearchGroup = new Intent(this, SearchGroupStartActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("studenteLoggato", studenteLog);
            extras.putSerializable("studentiDate", studentiDate);
            extras.putSerializable("gruppiDate", gruppiDate);
            extras.putSerializable("richiesteDate", richiesteDate);
            extras.putString("esameScelto", esameSelezionato);
            extras.putString("linguaScelta", linguaSelezionata);
            extras.putSerializable("studenteScelto", studenteSelezionato);
            openSearchGroup.putExtras(extras);
            this.startActivity(openSearchGroup);
        } else if (id == R.id.nav_profile_search_student) {
            Intent openSearchStudent = new Intent(this, SearchStudentStartActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("studenteLoggato", studenteLog);
            extras.putSerializable("studentiDate", studentiDate);
            extras.putSerializable("gruppiDate", gruppiDate);
            extras.putSerializable("richiesteDate", richiesteDate);
            extras.putString("esameScelto", esameSelezionato);
            extras.putString("linguaScelta", linguaSelezionata);
            extras.putSerializable("studenteScelto", studenteSelezionato);
            openSearchStudent.putExtras(extras);
            this.startActivity(openSearchStudent);
            return true;
        } else if (id == R.id.nav_profile_settings) {
            /*
            Intent intent = new Intent(this, SettingActivity.class);
            this.startActivity(intent);
            return true;
            */
        } else if(id == R.id.nav_profile_logout){
            Intent openLogin = new Intent(this, LoginActivity.class);
            openLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(openLogin);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

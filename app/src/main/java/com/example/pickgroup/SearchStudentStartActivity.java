package com.example.pickgroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;

import java.util.ArrayList;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class SearchStudentStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private StudentModel studenteLog;
    private ArrayList<StudentModel> studentiDate;
    private ArrayList<GroupModel> gruppiDate;
    private ArrayList<GroupRequestsModel> richiesteDate;
    private String esameSelezionato;
    private String linguaSelezionata;
    private StudentModel studenteSelezionato;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student_start);
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

        //Setto gli esami dello studente loggato
        LinearLayout esamiLayout = (LinearLayout) findViewById(R.id.linearLayoutExameChooseList);
        ArrayList<String> esami = studenteLog.getEsami();
        for(String esame : esami) {
            final Switch exameSwitch = new Switch(this);
            exameSwitch.setText(esame);
            exameSwitch.setHeight(110);
            exameSwitch.setWidth(esamiLayout.getWidth());
            exameSwitch.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            exameSwitch.setTextColor(Color.BLACK);
            exameSwitch.setTextSize(18);
            esamiLayout.addView(exameSwitch);
            exameSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    esameSelezionato = (String) exameSwitch.getText();
                }
            });
        }

        if(studenteLog.getTipo()==StudentModel.TipoStudente.ERASMUS){

            //Setto le lingue selezionabili dallo studente loggato
            LinearLayout lingueLayout = (LinearLayout) findViewById(R.id.linearLayoutLanguagesChooseList);
            ArrayList<String> lingue = studenteLog.getLingue();
            for(String lingua : lingue) {
                final Switch linguaSwitch = new Switch(this);
                linguaSwitch.setText(lingua);
                linguaSwitch.setHeight(110);
                linguaSwitch.setWidth(lingueLayout.getWidth());
                linguaSwitch.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                linguaSwitch.setTextColor(Color.BLACK);
                linguaSwitch.setTextSize(18);
                lingueLayout.addView(linguaSwitch);
                linguaSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        linguaSelezionata = (String) linguaSwitch.getText();
                    }
                });
            }
        }else{
            TextView titleLanguage = (TextView) findViewById(R.id.textViewTitleLanguage);
            titleLanguage.setVisibility(View.INVISIBLE);
            LinearLayout lingueLayout = (LinearLayout) findViewById(R.id.linearLayoutLanguagesChooseList);
            lingueLayout.setVisibility(View.INVISIBLE);

        }

        Button buttonHome = (Button) findViewById(R.id.buttonHomeSearchStudent);
        buttonHome.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent openHome = new Intent(SearchStudentStartActivity.this, ProfileActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("studenteLoggato", studenteLog);
                extras.putSerializable("studentiDate", studentiDate);
                extras.putSerializable("gruppiDate", gruppiDate);
                extras.putSerializable("richiesteDate", richiesteDate);
                extras.putString("esameScelto", esameSelezionato);
                extras.putString("linguaScelta", linguaSelezionata);
                extras.putSerializable("studenteScelto", studenteSelezionato);
                openHome.putExtras(extras);
                startActivity(openHome);
            }
        });

        Button buttonContinua = (Button) findViewById(R.id.buttonContinuaSearchStudent);
        buttonContinua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent openSelectStudent = new Intent(SearchStudentStartActivity.this, SearchStudentEndActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("studenteLoggato", studenteLog);
                extras.putSerializable("studentiDate", studentiDate);
                extras.putSerializable("gruppiDate", gruppiDate);
                extras.putSerializable("richiesteDate", richiesteDate);
                extras.putString("esameScelto", esameSelezionato);
                extras.putString("linguaScelta", linguaSelezionata);
                extras.putSerializable("studenteScelto", studenteSelezionato);
                openSelectStudent.putExtras(extras);
                startActivity(openSelectStudent);
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
        ImageView imgProfiloMenu = (ImageView) findViewById(R.id.imageViewProfiloSearchStudent);
        String command = "@drawable/"+studenteLog.getFoto();
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(command, "string", packageName);
        imgProfiloMenu.setImageResource(resId);


        //Setto nome e cognome nel menu laterale dello studente loggato
        TextView textNameSurnameMenu = (TextView) findViewById(R.id.textViewNameSurnameSearchStudent);
        textNameSurnameMenu.setText(studenteLog.getNome()+" "+studenteLog.getCognome());

        //Setto matricola nel menu laterale dello studente loggato
        TextView textMatricolaMenu = (TextView) findViewById(R.id.textViewMatricolaSearchStudent);
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

        if (id == R.id.nav_search_student_start_profile) {
            Intent openProfile = new Intent( this, ProfileActivity.class);
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
        } else if (id == R.id.nav_search_student_start_search_exams) {
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
        } else if (id == R.id.nav_search_student_start_search_group) {
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
        } else if (id == R.id.nav_search_student_start_search_student) {
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
        } else if (id == R.id.nav_search_student_start_settings) {
            /*
            Intent intent = new Intent(this, SettingActivity.class);
            this.startActivity(intent);
            return true;
            */
        } else if(id == R.id.nav_search_student_start_logout){
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

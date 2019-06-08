package com.example.pickgroup;

import android.app.Dialog;
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
import com.example.pickgroup.services.TakeData;

import java.util.ArrayList;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class SearchStudentEndActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_search_student_end);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linguaSelezionata = null;

        Bundle bundle = getIntent().getExtras();
        studenteLog = (StudentModel) bundle.getSerializable("studenteLoggato");
        studentiDate = (ArrayList<StudentModel>) bundle.getSerializable("studentiDate");
        gruppiDate = (ArrayList<GroupModel>) bundle.getSerializable("gruppiDate");
        richiesteDate = (ArrayList<GroupRequestsModel>) bundle.getSerializable("richiesteDate");
        esameSelezionato = bundle.getString("esameScelto");
        linguaSelezionata = bundle.getString("linguaScelta");
        studenteSelezionato = (StudentModel) bundle.getSerializable("studenteSelezionato");

        //Setto i gruppi di studio dello studente loggato
        LinearLayout studentiLayout = (LinearLayout) findViewById(R.id.linearLayoutStudentChooseList);
        final TakeData take = new TakeData(studentiDate, gruppiDate, null);
        final ArrayList<StudentModel> studenti = take.getStudentsForExam(esameSelezionato, studenteLog, linguaSelezionata);
        for(StudentModel studente : studenti) {
            final Switch studentSwitch = new Switch(this);
            studentSwitch.setText(studente.getNome()+" "+studente.getCognome());
            studentSwitch.setHeight(110);
            studentSwitch.setWidth(studentiLayout.getWidth());
            studentSwitch.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            studentSwitch.setTextColor(Color.BLACK);
            studentSwitch.setTextSize(18);
            studentiLayout.addView(studentSwitch);
            final StudentModel studenteApp = take.getUser(studente.getUsername(), studente.getPassword());
            studentSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    studenteSelezionato =  studenteApp;
                }
            });
        }

        Button buttonHome = (Button) findViewById(R.id.buttonHomeSelectStudent);
        buttonHome.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent openHome = new Intent(SearchStudentEndActivity.this, ProfileActivity.class);
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

        Button buttonInviaRichiesta = (Button) findViewById(R.id.buttonContinuaSelectStudent);
        buttonInviaRichiesta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                //CREARE NOTIFICA DIALOG DI RIUSCITA E DI ERRORE E CREARE LA RICHIESTA VERA E PROPRIA
                final ArrayList<GroupRequestsModel> richiesteStudente = take.getAllGroupRequestForStudent(studenteLog);
                final GroupRequestsModel richiesta = new GroupRequestsModel(studenteLog, studenteSelezionato, esameSelezionato);
                if(richiesta == null){
                    Dialog d = new Dialog(SearchStudentEndActivity.this);
                    d.setTitle("Errore");
                    d.setCancelable(false);
                    d.setContentView(R.layout.activity_error_request);
                    d.show();

                    Button errorButton = (Button) d.findViewById(R.id.buttonErrorRequest);
                    errorButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            // definisco l'intenzione
                            Intent openLogin = new Intent(SearchStudentEndActivity.this, LoginActivity.class);
                            // passo all'attivazione dell'activity Pagina.java
                            startActivity(openLogin);
                        }
                    });
                }else {
                    Dialog d = new Dialog(SearchStudentEndActivity.this);
                    d.setTitle("Success");
                    d.setCancelable(false);
                    d.setContentView(R.layout.activity_success_request);
                    d.show();

                    Button errorButton = (Button) d.findViewById(R.id.buttonSuccessRequest);
                    errorButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            richiesteDate.add(richiesta);
                            Intent openThis = new Intent(SearchStudentEndActivity.this, ProfileActivity.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable("studenteLoggato", studenteLog);
                            extras.putSerializable("studentiDate", studentiDate);
                            extras.putSerializable("gruppiDate", gruppiDate);
                            extras.putString("esameScelto", esameSelezionato);
                            extras.putString("linguaScelta", linguaSelezionata);
                            extras.putSerializable("studenteScelto", studenteSelezionato);
                            extras.putSerializable("richiesteDate", richiesteDate);
                            openThis.putExtras(extras);
                            startActivity(openThis);
                        }
                    });
                }
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
        ImageView imgProfiloMenu = (ImageView) findViewById(R.id.imageViewProfiloSelectStudent);
        String command = "@drawable/"+studenteLog.getFoto();
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(command, "string", packageName);
        imgProfiloMenu.setImageResource(resId);

        //Setto nome e cognome nel menu laterale dello studente loggato
        TextView textNameSurnameMenu = (TextView) findViewById(R.id.textViewNameSurnameSelectStudent);
        textNameSurnameMenu.setText("MATRICOLA - "+studenteLog.getNome()+" "+studenteLog.getCognome());

        //Setto matricola nel menu laterale dello studente loggato
        TextView textMatricolaMenu = (TextView) findViewById(R.id.textViewMatricolaSelectStudent);
        textMatricolaMenu.setText(studenteLog.getMatricola());

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

        if (id == R.id.nav_search_student_end_profile) {
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
        } else if (id == R.id.nav_search_student_end_search_exams) {
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
        } else if (id == R.id.nav_search_student_end_search_group) {
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
        } else if (id == R.id.nav_search_student_end_search_student) {
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
        } else if (id == R.id.nav_search_student_end_settings) {
            /*
            Intent intent = new Intent(this, SettingActivity.class);
            this.startActivity(intent);
            return true;
            */
        } else if(id == R.id.nav_search_student_end_logout){
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

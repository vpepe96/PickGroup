package com.example.pickgroup;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;
import com.example.pickgroup.services.CheckLogin;
import com.example.pickgroup.services.TakeData;

import java.util.ArrayList;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class ReceiveRequestsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private StudentModel studenteLog;
    private ArrayList<StudentModel> studentiDate;
    private ArrayList<GroupModel> gruppiDate;
    private ArrayList<GroupRequestsModel> richiesteDate;
    private String esameSelezionato;
    private String linguaSelezionata;
    private StudentModel studenteSelezionato;
    private GroupRequestsModel richiestaSelezionata;

    private Button homeButton;
    private Button accettaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_requests);
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

        //Setto le richieste inviate dello studente loggato
        TakeData take = new TakeData(studentiDate, gruppiDate, richiesteDate);
        ArrayList<GroupRequestsModel> receiveRequest = take.getReceiveGroupRequestForStudent(studenteLog);
        LinearLayout requestsExam = (LinearLayout) findViewById(R.id.linearLayoutExamReceiveRequestGroup);
        LinearLayout requestsDestinatario = (LinearLayout) findViewById(R.id.linearLayoutMittenteReceiveRequestGroup);
        for(final GroupRequestsModel request : receiveRequest) {
            TextView richiestaExamView = new TextView(this);
            richiestaExamView.setText(request.getEsame());
            richiestaExamView.setHeight(110);
            richiestaExamView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            richiestaExamView.setPadding(40,15,0,0);
            richiestaExamView.setTextColor(Color.BLACK);
            richiestaExamView.setTextSize(18);
            requestsExam.addView(richiestaExamView);

            final Switch richiestaMittenteSwitch = new Switch(this);
            richiestaMittenteSwitch.setText(request.getStudenteMittente().getNome()+" "+request.getStudenteMittente().getCognome());
            richiestaMittenteSwitch.setHeight(110);
            richiestaMittenteSwitch.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            richiestaMittenteSwitch.setTextColor(Color.BLACK);
            richiestaMittenteSwitch.setTextSize(18);
            requestsDestinatario.addView(richiestaMittenteSwitch);
            richiestaMittenteSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    richiestaSelezionata =  request;
                }
            });
        }

        homeButton = (Button)findViewById(R.id.buttonHomeReceiveRequest);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // definisco l'intenzione
                Intent openHome = new Intent(ReceiveRequestsActivity.this, ProfileActivity.class);
                //Setto lo studente loggato
                Bundle extras = new Bundle();
                extras.putSerializable("studenteLoggato", studenteLog);
                extras.putSerializable("studentiDate", studentiDate);
                extras.putSerializable("gruppiDate", gruppiDate);
                extras.putSerializable("richiesteDate", richiesteDate);
                extras.putString("esameScelto", esameSelezionato);
                extras.putString("linguaScelta", linguaSelezionata);
                extras.putSerializable("studenteScelto", studenteSelezionato);
                openHome.putExtras(extras);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openHome);
            }
        });

        accettaButton = (Button) findViewById(R.id.buttonAcceptReceiveRequest);
        accettaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if(richiestaSelezionata == null){
                    Dialog d = new Dialog(ReceiveRequestsActivity.this);
                    d.setTitle("Errore");
                    d.setCancelable(false);
                    d.setContentView(R.layout.activity_error_receive_request);
                    d.show();

                    Button errorButton = (Button) d.findViewById(R.id.buttonErrorReceiveRequest);
                    errorButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            // definisco l'intenzione
                            Intent openThis = new Intent(ReceiveRequestsActivity.this, ProfileActivity.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable("studenteLoggato", studenteLog);
                            extras.putSerializable("studentiDate", studentiDate);
                            extras.putSerializable("gruppiDate", gruppiDate);
                            extras.putSerializable("richiesteDate", richiesteDate);
                            extras.putString("esameScelto", esameSelezionato);
                            extras.putString("linguaScelta", linguaSelezionata);
                            extras.putSerializable("studenteScelto", studenteSelezionato);
                            openThis.putExtras(extras);
                            startActivity(openThis);
                        }
                    });
                }
                else{
                    TakeData take = new TakeData(studentiDate, gruppiDate, richiesteDate);
                    ArrayList<StudentModel> partecipanti = new ArrayList<>();
                    partecipanti.add(richiestaSelezionata.getStudenteMittente());
                    partecipanti.add(richiestaSelezionata.getStudenteDestinatario());
                    String nomeGruppo = take.compositeGroupName(richiestaSelezionata.getEsame());
                    GroupModel nuovoGruppo = new GroupModel(partecipanti, nomeGruppo, richiestaSelezionata.getEsame());
                    gruppiDate.add(nuovoGruppo);
                    richiesteDate.remove(richiestaSelezionata);

                    Dialog d = new Dialog(ReceiveRequestsActivity.this);
                    d.setTitle("Success");
                    d.setCancelable(false);
                    d.setContentView(R.layout.activity_success_receive_request);
                    d.show();

                    Button errorButton = (Button) d.findViewById(R.id.buttonSuccessReceiveRequests);
                    errorButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            Intent openThis = new Intent(ReceiveRequestsActivity.this, ProfileActivity.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable("studenteLoggato", studenteLog);
                            extras.putSerializable("studentiDate", studentiDate);
                            extras.putSerializable("gruppiDate", gruppiDate);
                            extras.putSerializable("richiesteDate", richiesteDate);
                            extras.putString("esameScelto", esameSelezionato);
                            extras.putString("linguaScelta", linguaSelezionata);
                            extras.putSerializable("studenteScelto", studenteSelezionato);
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

        Bundle bundle = getIntent().getExtras();
        studenteLog = (StudentModel) bundle.getSerializable("studenteLoggato");

        //Setto l'immagine del profilo nel menu laterale dello studente loggato
        ImageView imgProfiloMenu = (ImageView) findViewById(R.id.imageViewProfiloReceiveRequests);
        String command = "@drawable/"+studenteLog.getFoto();
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(command, "string", packageName);
        imgProfiloMenu.setImageResource(resId);


        //Setto nome e cognome nel menu laterale dello studente loggato
        TextView textNameSurnameMenu = (TextView) findViewById(R.id.textViewNameSurnameReceiveRequests);
        textNameSurnameMenu.setText(studenteLog.getNome()+" "+studenteLog.getCognome());

        //Setto matricola nel menu laterale dello studente loggato
        TextView textMatricolaMenu = (TextView) findViewById(R.id.textViewMatricolaReceiveRequests);
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

        if (id == R.id.nav_receive_request_profile) {
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
        } else if (id == R.id.nav_receive_request_search_exams) {
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
        } else if (id == R.id.nav_sent_request_search_group) {
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
        } else if (id == R.id.nav_receive_request_search_student) {
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
        } else if (id == R.id.nav_receive_request_settings) {
            /*
            Intent intent = new Intent(this, SettingActivity.class);
            this.startActivity(intent);
            return true;
            */
        } else if(id == R.id.nav_receive_request_logout){
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

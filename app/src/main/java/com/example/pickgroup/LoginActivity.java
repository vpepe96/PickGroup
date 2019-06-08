package com.example.pickgroup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;

import com.example.pickgroup.model.GroupModel;
import com.example.pickgroup.model.GroupRequestsModel;
import com.example.pickgroup.model.StudentModel;
import com.example.pickgroup.services.*;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private StudentModel studenteLog;
    private ArrayList<StudentModel> studentiDate;
    private ArrayList<GroupModel> gruppiDate;
    private ArrayList<GroupRequestsModel> richiesteDate;


    private Button loginButton;
    private EditText usernameEditText;
    private EditText passowrdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitializeData init = new InitializeData();
        studentiDate = init.initializeStudents();
        gruppiDate = init.initializeGroup(studentiDate);
        richiesteDate = init.initializeGroupRequests(studentiDate, gruppiDate);

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passowrdEditText = (EditText) findViewById(R.id.editTextPassword);

        loginButton=(Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("username", usernameEditText.getText().toString());
                bundle.putString("password", passowrdEditText.getText().toString());

                CheckLogin check = new CheckLogin(studentiDate);

                if(check.checkLogin(bundle.getString("username"), bundle.getString("password"))){

                    TakeData take = new TakeData(studentiDate, gruppiDate, richiesteDate);
                    studenteLog = take.getUser(bundle.getString("username"), bundle.getString("password"));

                    // definisco l'intenzione
                    Intent openHome = new Intent(LoginActivity.this, ProfileActivity.class);
                    //Setto lo studente loggato
                    Bundle extras = new Bundle();
                    extras.putSerializable("studenteLoggato", studenteLog);
                    extras.putSerializable("studentiDate", studentiDate);
                    extras.putSerializable("gruppiDate", gruppiDate);
                    extras.putSerializable("richiesteDate", richiesteDate);
                    openHome.putExtras(extras);
                    // passo all'attivazione dell'activity Pagina.java
                    startActivity(openHome);
                }else{
                    Dialog d = new Dialog(LoginActivity.this);
                    d.setTitle("Errore");
                    d.setCancelable(false);
                    d.setContentView(R.layout.activity_error_login);
                    d.show();

                    Button errorButton = (Button) d.findViewById(R.id.buttonError);
                    errorButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View arg0) {
                            // definisco l'intenzione
                            Intent openLogin = new Intent(LoginActivity.this, LoginActivity.class);
                            // passo all'attivazione dell'activity Pagina.java
                            startActivity(openLogin);
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
        return false;
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

        if (id == R.id.nav_login_login) {
           Intent intent = new Intent(this, LoginActivity.class);
           this.startActivity(intent);
           return true;
        } else if (id == R.id.nav_login_registration) {
            /*
            Intent intent = new Intent(this, RegistrationActivity.class);
            this.startActivity(intent);
            return true;
            */
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

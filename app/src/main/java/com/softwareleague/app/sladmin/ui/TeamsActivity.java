package com.softwareleague.app.sladmin.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.model.Team;
import com.softwareleague.app.sladmin.ui.adapter.TeamAdapter;
import com.softwareleague.app.sladmin.ui.fragment.TeamsFragment;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data;
    private RecyclerView recyclerView;
    private TeamAdapter teamAdapter;
    private ArrayList<Team> teamDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            try {
                actionBar.setDisplayHomeAsUpEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        teamDataset = new ArrayList<>();
        loadTeam();
        //RecyclerView para listar los campeonatos
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        data = firebaseDatabase.getReference("equipo");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teamDataset.clear();
                Team team;
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    team = dt.getValue(Team.class);
                    Log.i("Datos", dt.getValue().toString());
                    teamDataset.add(team);
                }
                teamAdapter = new TeamAdapter(TeamsActivity.this, teamDataset);
                recyclerView.setAdapter(teamAdapter);
                loadTeam();
                teamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }

        });

        //Acciones de Boton Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Se inicia actividad de creación de equipo",
                        Snackbar.LENGTH_LONG).setAction("Action",null).show();
                switch (view.getId()) {
                    case R.id.fab:
                        showCreateTeamDialog();
                        break;
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //TODO: Pedir al servidor informacion reciente
                loadTeam();
            }
        });
    }

    private void loadTeam() {
        showLoadingIndicator(true);
        if (!teamDataset.isEmpty()) {
            showLoadingIndicator(false);
        }
    }

    private void showLoadingIndicator(final boolean show) {
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(show);
            }
        });
    }

    private void showCreateTeamDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        TeamsFragment newFragment = new TeamsFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alerta = new AlertDialog.Builder(TeamsActivity.this);

        alerta
                .setIcon(R.drawable.ic_nav_teams)
                .setTitle("Salir")
                .setMessage("Atención: \nEstás a punto de abandonar la aplicación, ¿estás seguro?.\n\nAlgunos datos se perderán.")
                .setPositiveButton("Si, salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        TeamsActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Quiero quedarme", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acciones a realizar cuando salgamos. Siempre llamar al método súper.
                        Intent intent = new Intent(TeamsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog dialogoAlerta = alerta.show();

    }

}

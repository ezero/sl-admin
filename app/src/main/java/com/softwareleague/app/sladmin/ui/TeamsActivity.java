package com.softwareleague.app.sladmin.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.model.Team;
import com.softwareleague.app.sladmin.ui.adapter.TeamAdapter;
import com.softwareleague.app.sladmin.ui.fragment.TeamsFragment;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {

    private RecyclerView mTeamList;
    private View mEmptyStateContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RecyclerView para listar los campeonatos
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //llamar a los CardView para listar
        ArrayList<Team> teamDataset = new ArrayList<>();
        teamDataset.add(new Team("1","Thekne","Varones",7));
        teamDataset.add(new Team("1","AssureSoft","Varones",9));
        teamDataset.add(new Team("1","Thekne","Damas",8));
        teamDataset.add(new Team("1","VIVA","Damas",15));
        TeamAdapter teamAdapter = new TeamAdapter(teamDataset);
        recyclerView.setAdapter(teamAdapter);

        //Acciones de Boton Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Se inicia actividad de creación de equipo",
                        Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //TODO: Pedir al servidor informacion reciente
            }
        });
    }

    private void showCreateTeamDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TeamsFragment newFragment = new TeamsFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content,newFragment).addToBackStack(null).commit();
    }
}

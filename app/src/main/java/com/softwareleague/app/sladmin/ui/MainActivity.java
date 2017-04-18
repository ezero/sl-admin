package com.softwareleague.app.sladmin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.model.League;
import com.softwareleague.app.sladmin.data.prefs.SessionPrefs;
import com.softwareleague.app.sladmin.ui.adapter.LeagueAdapter;
import com.softwareleague.app.sladmin.ui.fragment.LeagueDialogFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data;
    private RecyclerView mRecyclerView;
    private View mEmptyStateContainer;
    private LeagueAdapter leagueAdapter;
    private ArrayList<League> leagueDataset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirección al Login
        if (!SessionPrefs.get(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Remover titulo del action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //RecyclerView para listar los campeonatos
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        data = firebaseDatabase.getReference("campeonato");

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                leagueDataset = new ArrayList<>();
                leagueDataset.clear();
                loadLeague();
                League league;
                for (DataSnapshot dt: dataSnapshot.getChildren()){
                    league = dt.getValue(League.class);
                    Log.i("Datos",dt.getValue().toString());
                    leagueDataset.add(league);
                }
                leagueAdapter = new LeagueAdapter(MainActivity.this,leagueDataset);
                //Crear Evento en este lugar
                mRecyclerView.setAdapter(leagueAdapter);
                loadLeague();
                leagueAdapter.notifyDataSetChanged();
                mEmptyStateContainer = findViewById(R.id.empty_state_container);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error",databaseError.getMessage());
            }
        });

        //llamar a los CardView para listar
        //ArrayList<League> leagueDataset = new ArrayList<>();
        //League(String lid, String leagueCatFemale, String leagueCatMale, String leagueEstado, String leagueDateCamIni, String leagueDateCamFin, String leagueDateInsIni, String leagueDateInsFin, String leagueMarca, String leagueTitle, String leagueUser)
        //leagueDataset.add(new League("2016","Si","Si","Activo","01/01/2016","31/01/2016","01/02/2016","30/09/2016","0","Campeonato Software League 2016","admin"));
        //LeagueAdapter leagueAdapter = new LeagueAdapter(leagueDataset);
        //mRecyclerView.setAdapter(leagueAdapter);

        //Boton flotante
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.fab:
                        showCreateLeagueDialog();
                        break;
                }
            }
        });
        //Ocultar Boton flotante al hacer scroll
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0 || dy < 0 && fab.isShown())
                {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView,newState);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SwipeRefreshLayout swipeRereshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRereshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLeague();
            }
        });

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;

        } else if (id == R.id.nav_teams) {
            intent = new Intent(MainActivity.this,TeamsActivity.class);
            startActivity(intent);
            finish();
            return true;

        } else if (id == R.id.nav_damas) {
            Toast.makeText(this,"Función de Proceso de distribución de roles de partidos por primera vez",Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this,LadiesActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.nav_varones) {
            Toast.makeText(this,"Función de Proceso de distribución de roles de partidos por primera vez",Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this,MaleActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.nav_datos) {
            intent = new Intent(MainActivity.this,PendingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showCreateLeagueDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LeagueDialogFragment newFragment = new LeagueDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content,newFragment).addToBackStack(null).commit();
    }

    private void loadLeague(){
        showLoadingIndicator(true);
        if (!leagueDataset.isEmpty()){
            showLoadingIndicator(false);
            //showNoLeague();
        }
    }
    private void showLoadingIndicator(final boolean show) {
        final SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(show);
            }
        });
    }

    private void showNoLeague(){
        mRecyclerView.setVisibility(View.GONE);
        mEmptyStateContainer.setVisibility(View.VISIBLE);
    }

}

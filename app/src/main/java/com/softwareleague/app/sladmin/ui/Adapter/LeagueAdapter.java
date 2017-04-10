package com.softwareleague.app.sladmin.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.api.model.League;

import java.util.ArrayList;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder> {
    private ArrayList<League> mDataset;
    //Clase Interna para el ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView leagueTitle;
        TextView leagueCatDamas;
        TextView leagueCatVarones;
        TextView leagueDateIni;
        TextView leagueDateFin;
        TextView leagueStat;

        ViewHolder(View v) {
            super(v);
            leagueTitle = (TextView) v.findViewById(R.id.leagueTitle);
            leagueCatDamas = (TextView) v.findViewById(R.id.leagueCatDamas);
            leagueCatVarones = (TextView) v.findViewById(R.id.leagueCatMen);
            leagueDateIni = (TextView) v.findViewById(R.id.leagueDateIni);
            leagueDateFin = (TextView) v.findViewById(R.id.leagueDateFin);
            leagueStat = (TextView) v.findViewById(R.id.leagueStat);
        }
    }

    public LeagueAdapter(ArrayList<League> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public LeagueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_league, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.leagueTitle.setText(mDataset.get(position).getLeagueTitle());
        holder.leagueCatDamas.setText(mDataset.get(position).getLeagueCatDamas());
        holder.leagueCatVarones.setText(mDataset.get(position).getLeagueCatVarones());
        holder.leagueDateIni.setText(mDataset.get(position).getLeagueDateIni());
        holder.leagueDateFin.setText(mDataset.get(position).getLeagueDateFin());
        holder.leagueStat.setText(mDataset.get(position).getLeagueStat());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

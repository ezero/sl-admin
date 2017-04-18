package com.softwareleague.app.sladmin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.model.League;

import java.util.ArrayList;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder> {

    private ArrayList<League> mDataset;
    private Context mContext;

    //Clase Interna para el ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eTitle;
        public TextView eFemale;
        public TextView eMale;
        public TextView eStatus;
        public TextView eUser;
        public TextView eDateA;
        public TextView eDateB;
        public TextView eDateIni;
        public TextView eDateFin;
        public Button eButton;
        public View statusIndicator;

        ViewHolder(View v) {
            super(v);
            eTitle = (TextView)v.findViewById(R.id.league_title);
            eFemale = (TextView)v.findViewById(R.id.league_cat_female);
            eMale = (TextView)v.findViewById(R.id.league_cat_male);
            eStatus = (TextView)v.findViewById(R.id.league_status);
            eUser = (TextView)v.findViewById(R.id.league_user);
            eDateA = (TextView)v.findViewById(R.id.league_date_ins_ini);
            eDateB = (TextView)v.findViewById(R.id.league_date_ins_fin);
            eDateIni = (TextView)v.findViewById(R.id.league_date_cam_ini);
            eDateFin = (TextView)v.findViewById(R.id.league_date_cam_fin);
            eButton = (Button)v.findViewById(R.id.button_update_league);
            statusIndicator = v.findViewById(R.id.indicator_league_status);

            eButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Toast.makeText(mContext,"Se establecera nuevos parametros a las fechas del Campeonato",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public LeagueAdapter(Context context, ArrayList<League> myDataset) {
        mContext = context;
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
        View statusIndicator = holder.statusIndicator;

        switch (mDataset.get(position).getEstado()){
            case "Activo":
                holder.eButton.setVisibility(View.VISIBLE);
                holder.eStatus.setTextColor(holder.eStatus.getContext()
                                                          .getResources()
                                                          .getColor(R.color.activeStatus));
                statusIndicator.setBackgroundResource(R.color.activeStatus);
                break;
            case "Concluido":
                holder.eButton.setVisibility(View.GONE);
                holder.eStatus.setTextColor(holder.eStatus.getContext()
                                                          .getResources()
                                                          .getColor(R.color.completedStatus));
                statusIndicator.setBackgroundResource(R.color.completedStatus);
                break;
            case "Cancelado":
                holder.eButton.setVisibility(View.GONE);
                holder.eStatus.setTextColor(holder.eStatus.getContext()
                                                          .getResources()
                                                          .getColor(R.color.cancelledStatus));
                statusIndicator.setBackgroundResource(R.color.cancelledStatus);
                break;
        }

        holder.eTitle.setText(mDataset.get(position).getNombre());
        holder.eFemale.setText(mDataset.get(position).getCat_damas());
        holder.eMale.setText(mDataset.get(position).getCat_varones());
        holder.eStatus.setText(mDataset.get(position).getEstado());
        holder.eUser.setText(mDataset.get(position).getUser());
        holder.eDateA.setText(mDataset.get(position).getFecha_ins_ini());
        holder.eDateB.setText(mDataset.get(position).getFecha_ins_fin());
        holder.eDateIni.setText(mDataset.get(position).getFecha_cam_ini());
        holder.eDateFin.setText(mDataset.get(position).getFecha_cam_fin());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

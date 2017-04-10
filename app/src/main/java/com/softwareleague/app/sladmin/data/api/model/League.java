package com.softwareleague.app.sladmin.data.api.model;

import java.util.Date;

public class League {
    private String leagueTitle;
    private String leagueCatDamas;
    private String leagueCatVarones;
    private String leagueDateIni;
    private String leagueDateFin;
    private String leagueStat;

    public League(String leagueTitle, String leagueCatDamas, String leagueCatVarones, String leagueDateIni, String leagueDateFin, String leagueStat) {
        this.leagueTitle = leagueTitle;
        this.leagueCatDamas = leagueCatDamas;
        this.leagueCatVarones = leagueCatVarones;
        this.leagueDateIni = leagueDateIni;
        this.leagueDateFin = leagueDateFin;
        this.leagueStat = leagueStat;
    }

    public String getLeagueTitle() {
        return leagueTitle;
    }

    public void setLeagueTitle(String leagueTitle) {
        this.leagueTitle = leagueTitle;
    }

    public String getLeagueCatDamas() {
        return leagueCatDamas;
    }

    public void setLeagueCatDamas(String leagueCatDamas) {
        this.leagueCatDamas = leagueCatDamas;
    }

    public String getLeagueCatVarones() {
        return leagueCatVarones;
    }

    public void setLeagueCatVarones(String leagueCatVarones) {
        this.leagueCatVarones = leagueCatVarones;
    }

    public String getLeagueDateIni() {
        return leagueDateIni;
    }

    public void setLeagueDateIni(String leagueDateIni) {
        this.leagueDateIni = leagueDateIni;
    }

    public String getLeagueDateFin() {
        return leagueDateFin;
    }

    public void setLeagueDateFin(String leagueDateFin) {
        this.leagueDateFin = leagueDateFin;
    }

    public String getLeagueStat() {
        return leagueStat;
    }

    public void setLeagueStat(String leagueStat) {
        this.leagueStat = leagueStat;
    }
}

package com.softwareleague.app.sladmin.data.model;

public class League {
    private String lid;
    private String cat_damas;
    private String cat_varones;
    private String estado;
    private String fecha_cam_ini;
    private String fecha_cam_fin;
    private String fecha_ins_ini;
    private String fecha_ins_fin;
    private String mrcb;
    private String nombre;
    private String user;

    public League() { }

    public League(String lid, String cat_damas, String cat_varones, String estado, String fecha_cam_ini, String fecha_cam_fin, String fecha_ins_ini, String fecha_ins_fin, String mrcb, String nombre, String user) {
        this.lid = lid;
        this.cat_damas = cat_damas;
        this.cat_varones = cat_varones;
        this.estado = estado;
        this.fecha_cam_ini = fecha_cam_ini;
        this.fecha_cam_fin = fecha_cam_fin;
        this.fecha_ins_ini = fecha_ins_ini;
        this.fecha_ins_fin = fecha_ins_fin;
        this.mrcb = mrcb;
        this.nombre = nombre;
        this.user = user;
    }



    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getCat_damas() {
        return cat_damas;
    }

    public void setCat_damas(String cat_damas) {
        this.cat_damas = cat_damas;
    }

    public String getCat_varones() {
        return cat_varones;
    }

    public void setCat_varones(String cat_varones) {
        this.cat_varones = cat_varones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_cam_ini() {
        return fecha_cam_ini;
    }

    public void setFecha_cam_ini(String fecha_cam_ini) {
        this.fecha_cam_ini = fecha_cam_ini;
    }

    public String getFecha_cam_fin() {
        return fecha_cam_fin;
    }

    public void setFecha_cam_fin(String fecha_cam_fin) {
        this.fecha_cam_fin = fecha_cam_fin;
    }

    public String getFecha_ins_ini() {
        return fecha_ins_ini;
    }

    public void setFecha_ins_ini(String fecha_ins_ini) {
        this.fecha_ins_ini = fecha_ins_ini;
    }

    public String getFecha_ins_fin() {
        return fecha_ins_fin;
    }

    public void setFecha_ins_fin(String fecha_ins_fin) {
        this.fecha_ins_fin = fecha_ins_fin;
    }

    public String getMrcb() {
        return mrcb;
    }

    public void setMrcb(String mrcb) {
        this.mrcb = mrcb;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

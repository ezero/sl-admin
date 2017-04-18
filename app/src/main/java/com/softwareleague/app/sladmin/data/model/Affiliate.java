package com.softwareleague.app.sladmin.data.model;

/**
 * Objeto plano Java para representar afiliados
 */
public class Affiliate {

    private String id;
    private String nombre;
    private String tipo;
    private String login;
    private String clave;
    private String estado;
    private String fechaCreacion;
    private String fechaModificacion;
    private String token;

    public Affiliate(String id, String nombre, String tipo, String login, String clave, String estado, String fechaCreacion, String fechaModificacion, String token) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.login = login;
        this.clave = clave;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

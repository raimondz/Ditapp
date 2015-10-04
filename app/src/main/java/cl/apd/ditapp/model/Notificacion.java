package cl.apd.ditapp.model;

import java.util.Date;

import io.realm.RealmObject;

public class Notificacion extends RealmObject {

    private String titulo;
    private String descripcion;
    private Date fecha;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

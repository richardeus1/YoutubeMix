package com.dominio;

import java.util.ArrayList;

/**
 * La clase Lista
 * @author Natali Balón
 */
public class Lista {
    private String nombre; //El nombre de la lista
    private String aliasUsuario;
    private String descripcion;
    private ArrayList<Cancion> conjuntoDeCanciones;

    /**
     * Creadora por defecto
     */
    public Lista(){}

    /**
     * Creadora
     * @param nombre titulo de la lista
     * @param aliasUsuario usuario de la lista
     * @param desc descripción de la lista
     */
    public Lista(String nombre, String aliasUsuario, String desc) {
        this.nombre = nombre;
        this.aliasUsuario = aliasUsuario;
        this.descripcion = desc;
        this.conjuntoDeCanciones = new ArrayList<Cancion>();
    }

    /**
     * Creadora
     * @param nombre nombre de la lista
     * @param aliasUsuario usuario de la lista
     * @param descripcion descripción de la lista
     * @param c conjunto de canciones de la lista
     */
    public Lista(String nombre, String aliasUsuario, String descripcion, ArrayList<Cancion> c) {
        this.nombre = nombre;
        this.aliasUsuario = aliasUsuario;
        this.descripcion = descripcion;
        this.conjuntoDeCanciones = c;
    }

    /**
     *
     * @return nombre de la lista
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return usuario de la lista
     */
    public String getAliasUsuario() {
        return aliasUsuario;
    }

    /**
     *
     * @return descripción de la lista
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica
     * @param nombre nombre nuevo de la lista
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Modifica
     * @param descripcion descripción nueva de la lista
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return conjunto de canciones de la lista
     */
    public ArrayList<Cancion> getConjuntoDeCanciones() {
        return conjuntoDeCanciones;
    }

    /**
     * Modifica el conjunto de canciones
     * @param conjuntoDeCanciones conjunto nuevo de canciones
     */
    public void setConjuntoDeCanciones(ArrayList<Cancion> conjuntoDeCanciones) {
        this.conjuntoDeCanciones = conjuntoDeCanciones;
    }

    /**
     * Añade una canción a la lista
     * @param c canción que se desea añadir
     */
    public void addCancionALista(Cancion c){
        conjuntoDeCanciones.add(c);
    }


    /**
     * Elimina una canción a la lista
     * @param c canción que se desea eliminar
     */
    public Boolean removeCancionDeLista(Cancion c){
        return conjuntoDeCanciones.remove(c);
    }
}

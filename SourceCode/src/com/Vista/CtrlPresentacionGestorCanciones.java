package com.Vista;

import com.dominio.Cancion;
import com.dominio.CtrlConjuntoCanciones;
import com.dominio.CtrlDominio;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Andreu Conesa
 */
public class CtrlPresentacionGestorCanciones {

    private CtrlDominio ctrlDominio;
    private CtrlVistas ctrlVista;
    private CtrlConjuntoCanciones cc;
    private PresentacionGestorCanciones vista;

    /**
     * CtrlPresentacionGestorCanciones() constructor con un CtrlVistas como parametro
     * @param crVista con el que se comunica
     */
    public CtrlPresentacionGestorCanciones(CtrlVistas crVista)
    {
        ctrlVista = crVista;
        ctrlDominio = crVista.getDominio();
        cc = ctrlDominio.getCtrlCanciones();
    }

    /**
     * Inicializa un nuevo PresentacionGestorCanciones
     * y llama a su programa principal
     */
    public void ejecutar() {
        vista = new PresentacionGestorCanciones(this);
        vista.main();
    }

    /**
     *
     * @return el controlador de dominio
     */
    public CtrlDominio getCtrlDominio() {
        return ctrlDominio;
    }

    /**
     * @param titulo de la cancion a anyadir
     * @param autor de la cancion a anyadir
     * @param album de la cancion a anyadir
     * @param genero de la cancion a anyadir
     * @param numRep de la cancion a anyadir
     * @param fechaInsercion de la cancion a anyadir
     * @param creador de la cancion a anyadir
     * @param anyo de la cancion a anyadir
     * @return true si la cancion se ha anyadido, false sino
     */
    public Boolean anadirCancion(String titulo, String autor, String album, String genero, Integer numRep, String fechaInsercion, String creador, Integer anyo){
        Boolean b = ctrlDominio.anadirCancion(titulo, autor, album, genero, numRep, fechaInsercion, creador, anyo);
        return b;
    }

    /**
     * @param path del archivo que contiene informacion de canciones a anyadir
     * @return true si todas las canciones se han podido anyadir, false sino
     */
    public Boolean anadirCancionesDesdeArchivo(String path){
        Boolean b = ctrlDominio.anadirCancionesDesdeArchivo(path);
        return b;
    }

    /**
     * @param id de la cancion a borrar titulo;autor
     * @return true si la cancion identificada por id se ha podido borrar, false sino
     */
    public Boolean borrarCancion(String id) {
        Boolean b = ctrlDominio.borrarCancion(id);
        return b;
    }

    /**
     * @param path del archivo con ids de canciones que se quieren borrar
     * @return true si todas las canciones se han podido borrar, false sino
     */
    public Boolean borrarCancionesDesdeArchivo(String path){
        Boolean b = ctrlDominio.borrarCancionesDesdeArchivo(path);
        return b;
    }

    /**
     * @param t es el titulo de la cancion a modificar
     * @param a es el autor de la cancion a modificar
     * @param album es el nuevo album de la cancion
     * @param genero es el nuevo genero de la cancion
     * @param anyo es el nuevo anyo de la cancion
     * @return true si la cancion se ha modificado, false sino
     */
    public Boolean modificarCancion(String t, String a, String album, String genero, Integer anyo) {
        Boolean b;
        b = ctrlDominio.modificarCancion(t, a, album, genero, anyo);
        return b;
    }

    /**
     * @param path del archivo con la informacion necesaria para modificar canciones
     * @return true si todas las canciones se han podido modificar, false sino
     */
    public Boolean modificarCancionesDesdeArchivo(String path){
        Boolean b = ctrlDominio.modificarCancionesDesdeArchivo(path);
        return b;
    }

    /**
     * @param id de la cancion a buscar titulo;autor
     * @return un ArrayList<String> con los datos de la cancion si existe, o vacio sino
     */
    public ArrayList<String> buscarCancion(String id) {
        ArrayList<String> c1 = ctrlDominio.buscarCancion(id);
        return c1;
    }

    /**
     * @return un ArrayList<String> con los identificadores (titulo y autor) de las canciones del sistema
     */
    public ArrayList<String> getNombreCanciones() {
        ArrayList<String> res = cc.getNombreCanciones();
        return res;
    }

    /**
     * @return un ArrayList<String> con toda la informacion de las canciones del sistema
     */
    public ArrayList<String> getCanciones() {
        //obtengo todas las canciones
        ArrayList<String> res = ctrlDominio.getCanciones();
        return res;
    }

    /**
     * @return un ArrayList<String> con titulo,autor,album,genero,anyo,reproducciones
     * de todas las canciones del sistema si el usuario actual es el Admin, o de las que el usuario es el creador sino.
     */
    public ArrayList<String> getCancionesAdmin() {
        // obtengo segun sea admin o no
        ArrayList<String> res = ctrlDominio.getCancionesAdmin();
        return res;
    }

    /**
     * @return un ArrayList<String> con toda la informacion de las canciones
     * de las cuales el usuario actual es el creador
     */
    public ArrayList<String> getCancionesCreador(){
        // obtengo por creador
        ArrayList<String> res = ctrlDominio.getCancionesCreador();
        return res;
    }

    /**
     * llama a la vista principal
     */
    public void getMenuPrincipal(){
        ctrlVista.getMenuPrincipal();
    }
}

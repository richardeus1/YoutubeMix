package com.dominio;

/**
 * @author Andreu Conesa
 */
import com.CtrlDatos.CtrlDatosCanciones;

import java.util.*;


public class CtrlConjuntoCanciones {

    ConjuntoCanciones conjuntoCanciones;
    CtrlDatosCanciones ccc;

    /**
     * CtrlConjuntoCanciones() constructora que crea un nuevo ConjuntoCanciones y un CtrlDatosCanciones
     */
    public CtrlConjuntoCanciones() {
        ccc = new CtrlDatosCanciones();
        ArrayList<ArrayList<String> > l2 = ccc.cargarCancionesDesdeArchivo();
        List<Cancion> l1 = transformar(l2);
        this.conjuntoCanciones = new ConjuntoCanciones(l1);
    }

    /**
     * @return conjuntoCanciones
     */
    public ConjuntoCanciones getConjuntoDeCanciones() {
        return conjuntoCanciones;
    }

    /**
     * llama a la funcion anadirCancion de conjuntoCanciones
     * @param titulo de la cancion a anyadir
     * @param autor de la cancion a anyadir
     * @param album de la cancion a anyadir
     * @param genero de la cancion a anyadir
     * @param numRep de la cancion a anyadir
     * @param fechaInsercion de la cancion a anyadir
     * @param creador de la cancion a anyadir
     * @param anyo de la cancion a anyadir
     * @return true si la cancion se ha anyadido, false si no se ha podido anyadir
     */
    public Boolean anadirCancion(String titulo, String autor, String album, String genero, Integer numRep, String fechaInsercion, String creador, Integer anyo) {
        Boolean b;
        b = conjuntoCanciones.anadirCancion(titulo, autor, album, genero, numRep, fechaInsercion, creador, anyo);
        if (b) cargarCancionesHaciaArchivo();
        return b;
    }

    /**
     * llama a la funcion borrarCancion de ConjuntoCanciones
     * @param id de la cancion a borrar
     * @param alias del usuario que desea borrar la cancion
     * @return true si la cancion se ha borrado, false si no se ha podido borrar
     */
    public Boolean borrarCancion(String id, String alias) {
        Boolean b;
        b = conjuntoCanciones.borrarCancion(id, alias);
        if (b) cargarCancionesHaciaArchivo();
        return b;
    }

    /**
     * llama a la funcion modificarCancion de ConjuntoCanciones
     * @param t titulo de la cancion a modificar
     * @param a autor de la cancion a modificar
     * @param album nuevo album
     * @param genero nuevo genero
     * @param anyo nuevo anyo
     * @param alias del usuario que desea modificar la cancion
     * @return true si la cancion se ha modificado, false si no se ha podido modificar
     */
    public Boolean modificarCancion(String t, String a, String album, String genero, Integer anyo, String alias) {
        Boolean b;
        b = conjuntoCanciones.modificarCancion(t, a, album, genero, anyo, alias);
        if (b) cargarCancionesHaciaArchivo();
        return b;
    }

    /**
     * llama a la funcion buscarCancion de ConjuntoCanciones
     * @param id de la cancion que se desea buscar
     * @return una cancion con sus atributos o una cancion vacia
     */
    public Cancion buscarCancion(String id) {
        Cancion c1 = conjuntoCanciones.buscarCancion(id);
        return c1;
    }

    /**
     * llama a la funcion getNombreCanciones de ConjuntoCanciones
     * @return los ids de la canciones del conjunto
     */
    public ArrayList<String> getNombreCanciones() {
        return conjuntoCanciones.getNombreCanciones();
    }

    /**
     * llama a la funcion getCanciones de ConjuntoCanciones
     * @return las canciones del conjunto
     */
    public List<Cancion> getCanciones() {
        return conjuntoCanciones.getCanciones();
    }

    /**
     * llama a la funcion modificarCreadorCancion de ConjuntoCanciones
     * @param alias del usuario que desea realizar la operacion
     */
    public void modificarCreadorCancion(String alias) {
        conjuntoCanciones.modificarCreadorCancion(alias);
        cargarCancionesHaciaArchivo();
    }

    /**
     * llama a la funcion getCancionesCreador de ConjuntoCanciones
     * @param alias del usuario que desea realizar la operacion
     * @return las canciones cuyo creador sea alias
     */
    public List<Cancion> getCancionesCreador(String alias){
        return conjuntoCanciones.getCancionesCreador(alias);
    }

    /**
     * llama a la funcion cargarCancionesHaciaArchivo de CtrlDatosCanciones
     */
    public void cargarCancionesHaciaArchivo(){
        List<Cancion> aux = getCanciones();
        ArrayList<String> aux2 = new ArrayList<>();
        for (int i = 0; i < aux.size(); ++i){
            aux2.add(aux.get(i).toString());
        }
        ccc.cargarCancionesHaciaArchivo(aux2);
    }

    /**
     * llama a la funcion modificarNumeroReproducciones de ConjuntoCanciones
     * @param IdCancion de la cancion cuyo numRep quiera modificarse
     * @param rep incremento o decremento que se aplica al numRep de la cancion identificada por IdCancion
     * @param aumentar true indica que rep implica incremento, false implica decremento
     */
    public void modificarNumeroReproducciones(String IdCancion, Integer rep, Boolean aumentar) {
        conjuntoCanciones.modificarNumeroReproducciones(IdCancion, rep, aumentar);
        cargarCancionesHaciaArchivo();
    }

    /**
     * llama a la funcion anadirCancionesDesdeArchivo de CtrlDatosCanciones
     * @param path del archivo desde donde se quieren anyadir las canciones
     * @param alias del usuario que desea realizar la operacion
     * @return la lista de canciones que se quieren anyadir
     */
    public ArrayList<Cancion> anadirCancionesDesdeArchivo(String path, String alias) {
        ArrayList<ArrayList<String> > l2 = ccc.anadirCancionesDesdeArchivo(path, alias);
        ArrayList<Cancion> l1 = transformar(l2);
        return l1;
    }

    /**
     * llama a la funcion borrarCancionesDesdeArchivo de CtrlDatosCanciones
     * @param path del archivo desde donde se quieren borrar las canciones
     * @return la lista de identificadores de las canciones que se quieren borrar
     */
    public ArrayList<ArrayList<String> > borrarCancionesDesdeArchivo(String path) {
        ArrayList<ArrayList<String> > l2 = ccc.borrarCancionesDesdeArchivo(path);
        return l2;
    }

    /**
     * llama a la funcion modificarCancionesDesdeArchivo de CtrlDatosCanciones
     * @param path del archivo desde donde se quieren modificar las canciones
     * @return la lista de identificadores y nuevos datos de las canciones que se quieren modificar
     */
    public ArrayList<ArrayList<String> > modificarCancionesDesdeArchivo(String path) {
        ArrayList<ArrayList<String> > l2 = ccc.modificarCancionesDesdeArchivo(path);
        return l2;
    }

    /**
     * transforma una lista de listas de caracteres en una lista de canciones
     * @param l es la lista a transformar
     * @return una lista de canciones
     */
    private ArrayList<Cancion> transformar(ArrayList<ArrayList<String> > l){
        ArrayList<Cancion> res = new ArrayList<>();
        for (int i = 0; i < l.size(); ++i){
            Cancion ca = new Cancion(l.get(i).get(0),l.get(i).get(1),l.get(i).get(2),l.get(i).get(3),Integer.parseInt(l.get(i).get(4)),l.get(i).get(5),l.get(i).get(6),Integer.parseInt(l.get(i).get(7)));
            res.add(ca);
        }
        return res;
    }

}
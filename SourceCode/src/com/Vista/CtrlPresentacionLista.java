package com.Vista;

import com.Vista.PresentacionLista;
import com.dominio.CtrlDominio;

import java.util.ArrayList;

/**
 * @author Natali Balón
 */
public class CtrlPresentacionLista {
    private static CtrlDominio ctrlDominio;
    private CtrlVistas ctrlVista;
    private PresentacionLista vista;

    /**
     * Creadora por defecto
     */
    public CtrlPresentacionLista(){
        this.ctrlDominio = new CtrlDominio();
    }

    /**
     * Creadora
     * @param crVista El controlador de Vistas
     */
    public CtrlPresentacionLista(CtrlVistas crVista) {
        ctrlVista = crVista;
        ctrlDominio = crVista.getDominio();
    }

    /**
     * Permite añadir una lista
     * @param l nombre de la lista
     * @param desc descripción de la lista
     * @return true si se ha añadido la lista, false en caso contrario.
     */
    public static Boolean anadirLista(String l, String desc){
        if (l.isEmpty()) return false;
        return ctrlDominio.getCtrlConjuntoLista().addLista(l, desc);
    }

    /**
     *
     * @return devuelve los nombres de las listas que tiene el usuario actual.
     */
    public ArrayList<String> getNombresListas(){
        return ctrlDominio.getCtrlConjuntoLista().getNombresListas();
    }

    /**
     * Permite eliminar una lista
     * @param noml identificación de la lista.
     */
    public void eliminarLista(String noml){
        ctrlDominio.getCtrlConjuntoLista().removeLista(noml);
    }

    /**
     * Permite guardar las modificaciones de las reproducciones.
     */
    public static void guardaModificacionesReproduciones() {
        ctrlDominio.getCtrlReproducidas().guardarDatos();
    }

    /**
     * Permite guardar las modificaciones de las reproducciones de una canción (global)
     */
    public static void guardaModificacionesReproducionesCanciones() {
        ctrlDominio.getCtrlCanciones().cargarCancionesHaciaArchivo();
    }

    /**
     *
     * @param noml nombre de una lista
     * @return devuelve la información de la lista.
     */
    public static ArrayList<String> getDatosLista(String noml){
        return ctrlDominio.getCtrlConjuntoLista().getDatosLista(noml);
    }

    /**
     *
     * @param noml nombre de una lista
     * @param nomNuevo nombre nuevo de la lista
     * @param descNueva descripción nueva de la lista.
     * @return true si se ha modificado la lista, false en caso contrario.
     */
    public Boolean modificarLista(String noml, String nomNuevo, String descNueva){
        return ctrlDominio.getCtrlConjuntoLista().setAtributosDeLista(noml, nomNuevo, descNueva);
    }

    /**
     *
     * @param noml nombre de una lista
     * @return devuelve las canciones de una lista (noml).
     */
    public ArrayList<String> getNombresCanciones(String noml){
        return ctrlDominio.getCtrlConjuntoLista().getNombresCancionesListas(noml);
    }

    /**
     *
     * @param noml nombre de una lista
     * @return devuelve las canciones que no pertenecen a la lista "noml".
     */
    public ArrayList<String> getNombresNoCanciones(String noml){
        return ctrlDominio.getCtrlConjuntoLista().getNombresCancionesNoListas(noml);
    }

    /**
     * Permite añadir una cancion a la lista.
     * @param listaActual nombre de la lista
     * @param IdCancion identificación de la canción.
     */
    public static void anadirCancionALista(String listaActual, String IdCancion){
        ctrlDominio.getCtrlConjuntoLista().addCancionALista(listaActual, IdCancion);
    }

    /**
     * Permite eliminar una canción de la lista.
     * @param listaActual nombre de la lista
     * @param IdCancion identificación de la canción.
     */
    public static void eliminarCancionDeLista(String listaActual, String IdCancion){
        ctrlDominio.getCtrlConjuntoLista().removeCancionDeLista(listaActual, IdCancion);
    }

    /**
     *
     * @return devuelve todos los nombres de las canciones que existen en el sistema.
     */
    public ArrayList<String> getTodosNombresCanciones(){
        return ctrlDominio.getCtrlCanciones().getNombreCanciones();
    }

    /**
     * Permite modificar el numero de reproducciones de una canción
     * @param IdCancion identificador de una canción.
     * @param numero numero de reproduccion a añadir o a quitar.
     * @param aumentar indicación de lo que se desea realizar.
     * @return true si se ha modificado las reproducciones, false en caso contrario.
     */
    public static Boolean modificarNumeroReproduciones(String IdCancion, Integer numero, Boolean aumentar){
        Boolean b = ctrlDominio.getCtrlReproducidas().modificarReproducida(IdCancion, numero, aumentar);
        return b;

    }

    /**
     * Permite exportar una lista
     * @param listaActual nombre de la lista
     * @param ruta ruta del fichero donde se guardará la información de la lista.
     */
    public static void exportarLista(String listaActual, String ruta){
        ctrlDominio.getCtrlConjuntoLista().exportarLista(listaActual, ruta);
    }

    /**
     * Permite modificar la posición de una canción dentro de la lista.
     * @param listaActual nombre de la lista
     * @param IdCancion identificación de una canción.
     * @param subir indicación de lo que se desea realizar
     * @return true si se ha modificado la posición, false en caso contrario.
     */
    public static Boolean modificarIndiceCancion(String listaActual, String IdCancion, Boolean subir) {
        return ctrlDominio.getCtrlConjuntoLista().modificarIndiceCancion(listaActual, IdCancion, subir);
    }

    /**
     * Función para ejecutar la vista de Lista
     */
    public void ejecutar() {
            //Como ahora el ctrl del conjunto gestiona la vista que se muestra y del view de la presentación es el controlador el que lo llama
            //Por tanto también el que se encarga de procesar cuando se pulsa un botón.
            vista = new PresentacionLista(this);
        vista.main();
    }

    /**
     * Función para ejecutar la vista de Reproducidas
     */
    public void ejecutarRepro() {
        //Como ahora el ctrl del conjunto gestiona la vista que se muestra y del view de la presentación es el controlador el que lo llama
        //Por tanto también el que se encarga de procesar cuando se pulsa un botón.
        vista = new PresentacionLista(this);
        vista.mainRepro();
    }

    /**
     * Función que manda a la vista principal del sistema
     */
    public void getMenuPrincipal(){
        ctrlVista.getMenuPrincipal();
    }

    /**
     * Busca la canción en todas las listas
     * @param textoBusqueda idcanción a buscar
     * @return Devuelve todas las apariciones de la canción en todas las listas.
     */
    public String buscarCancion(String textoBusqueda) {
        return ctrlDominio.getCtrlConjuntoLista().buscarCancion(textoBusqueda);
    }

    /**
     *
     * @param can identificación de una canción.
     * @return devuelve el numero de veces que el usuario ha reproducido esa canción.
     */
    public Integer getNumeroRepro(String can) {
        return ctrlDominio.getCtrlReproducidas().numRepro(can);
    }
}

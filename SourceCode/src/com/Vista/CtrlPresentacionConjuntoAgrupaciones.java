package com.Vista;

import com.Algoritmos.CtrlAlgortimo;
import com.dominio.Cancion;
import com.dominio.CtrlConjuntoAgrupaciones;
import com.dominio.CtrlConjuntoCanciones;
import com.dominio.CtrlDominio;
import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;

import java.util.ArrayList;

/**
 * @author Ricardo Franco
 */
public class CtrlPresentacionConjuntoAgrupaciones {
    private CtrlDominio dominio;
    private CtrlVistas ctrlVista;
    private CtrlConjuntoAgrupaciones conjuntos;
    private ArrayList<String> datosConjuntoVisualizado;
    private PresentacionConjuntosAgrupaciones vista;

    /**
     * Creadora por defecto
     * @param crVista El controlador de Vistas
     */
    public CtrlPresentacionConjuntoAgrupaciones(CtrlVistas crVista) {
        this.ctrlVista = crVista;
        dominio = crVista.getDominio();
        conjuntos = dominio.getCtrlConjuntoAgrupaciones();
    }

    /**
     *
     * @return Devuelve los conjuntos (nombres) que tiene el usuario actual en el sistema
     */
    public ArrayList getConjuntosGuardados(){
        return conjuntos.getConjuntosUsuarioActual();
    }

    /**
     * Borra el conjunto identificado por nombre
     * @param nombre Nombre del conjunto a borrar
     */
    public void borrarConjunto(String nombre){
        conjuntos.borrarConjunto(nombre);
    }

    /**
     * Obtiene los datos del conjunto guardado
     * @param nombreAgrupacion Nombre del conjunto a cargar
     * @return Los datos del conjunto guardado
     */
    public ArrayList<String> getDatosConjunto(String nombreAgrupacion){
        //Primero cargamos el conjunto
        conjuntos.cargarConjunto(nombreAgrupacion);
        //Obtengamos sus datos
        datosConjuntoVisualizado = conjuntos.getDatosConjunto();
        return datosConjuntoVisualizado;
    }

    /**
     *
     * @return Devuelve los datos del conjunto actual
     */
    public ArrayList<String> getDatosConjuntoActual(){
        //Obtengamos sus datos
        datosConjuntoVisualizado = conjuntos.getDatosConjunto();
        return datosConjuntoVisualizado;
    }

    /**
     *
     * @return Devuelve todas las agrupaciones del conjunto actual
     */
    public ArrayList<ArrayList<String>> getAgrupacionesConjunto(){
        ArrayList<ArrayList<String>> procesado = conjuntos.getAgrupacionesConjunto();
        return procesado;
    }


    /**
     * Permite generar un conjunto autogenerado
     * @param datos Datos necesarios para el conjunto
     * @param criteriosFiltrado Criterios para el filtrado
     * @param criteriosAlgoritmo Criterios para el algoritmo
     * @return true si se ha realizado correctamente, false si no
     */
    public Boolean generarConjunto(ArrayList<String> datos,ArrayList<String> criteriosFiltrado,ArrayList<Integer> criteriosAlgoritmo){
       try {
           return dominio.generarConjuntoAgrupaciones(datos, criteriosFiltrado, criteriosAlgoritmo);
       }
       catch (Exception e){
          e.printStackTrace();
       }
        return false;
    }


    /**
     * Función para ejecutar la vista de Conjunto Agrupaciones
     */
    public void ejecutar() {
        //Como ahora el ctrl del conjunto gestiona la vista que se muestra y del view de la presentación es el controlador el que lo llama
        //Por tanto también el que se encarga de procesar cuando se pulsa un botón.
        vista = new PresentacionConjuntosAgrupaciones(this);
        vista.main();
    }

    /**
     *  Guarda el conjunto actual con titulo y descripción
     * @param titulo titulo del conjunto
     * @param desc descripción del conjunto
     * @return true si se ha realizado, false si no porque ya existia un conjunto con ese nombre y no es el cargado
     */
    public Boolean guardarAgrupacion(String titulo, String desc){
        return conjuntos.guardarConjunto(titulo,desc);
    }

    /**
     * Cierra la vista actual
     */
    public void cerrar() {
        vista.close();
        ctrlVista.mostarMenu();
    }

    /**
     * Exporta una agrupación (n) del conjunto actual a una nueva lista con nombre y descripción concretos
     * @param n Número de agrupación a exportar
     * @param nombreListaNueva Nombre de la lista a crear
     * @param descripcionListaNueva Descripción de la lista
     * @return true si se ha exportado bien, false si no y ya existía una lista con ese nombre
     */
    public boolean exportarALista(Integer n, String nombreListaNueva, String descripcionListaNueva){
        return dominio.exportarAgrupacionaLista(n,nombreListaNueva,descripcionListaNueva);
    }

    /**
     *
     * @return Devuelve todas las listas del usuario actual
     */
    public ArrayList<String> getListas() {
        return dominio.getCtrlConjuntoLista().getNombresListas();
    }

    /**
     * Exprota el Conjunto actual a un archivo
     * @param ruta Ruta con el nombre del nuevo archivo
     * @param datos Nombre y descripción actuales (de la vista) de la agrupación
     */
    public void exportarArchivoConjunto(String ruta, ArrayList<String> datos) {
        conjuntos.exportarArhivoConjuntoActual(ruta,datos);
    }

    /**
     * Exporta la agrupación del conjunto actual a un archivo
     * @param numAgrupacion Número de la agrupación a exportar
     * @param ruta Ruta con el nombre del archivo
     */
    public void exportarArchivoAgrupacion(String numAgrupacion, String ruta) {
        conjuntos.exportarArhivoAgrupacion(numAgrupacion, ruta);
    }

    /**
     * Borra la agrupación del sistema
     * @param elemento Número de la agrupación a borrar
     * @return true si se ha borrado correctamente, false si no
     */
    public boolean borrarAgrupacion(String elemento) {
        return conjuntos.borrarAgrupacionActual(Integer.valueOf(elemento));
    }

    /**
     * Fusiona las dos agrupaciones del conjunto acutal
     * @param a1 Num agrupación a fusionar
     * @param a2 Num agrupación a fusionar
     */
    public void fusionarAgrupaciones(String a1, String a2) {
        conjuntos.fusionarAgrupacionesConjunto(Integer.valueOf(a1),Integer.valueOf(a2));
    }

    /**
     * Intercambia el orden de las dos canciones de la agrupación del conjunto actual
     * @param numA num agrupación
     * @param c1 id canción
     * @param c2 id canción
     */
    public void intercambiarOrdenCanciones(String numA, String c1,String c2){
        conjuntos.intercambiarOrdenCanciones(Integer.valueOf(numA), c1, c2);
    }

    /**
     * Borra la canción de la agrupación (elemento) del conjunto actual
     * @param elemento Num agrupación
     * @param c1 identificador de la canción
     */
    public void borrarCancion(String elemento, String c1) {
        conjuntos.borrarCancionenAgrupacion(Integer.valueOf(elemento), c1);
    }

    /**
     * Busca la canción en el conjunto actual
     * @param textoBusqueda idcanción a buscar
     * @return Devuelve todas las apariciones de la canción en el conjunto actual
     */
    public String buscarCancion(String textoBusqueda) {
        ArrayList<Integer> busqueda = conjuntos.buscarCancionConjunto(textoBusqueda);
        if(busqueda.get(0) != -1){
            String resultado = "La canción ha sido encontrada en: \n";
            //Ha encontrado algo por tanto lo recorremos de dos en dos
            for(int i = 0; i<busqueda.size();i+=2){
                resultado = resultado +"Agrupación: " +busqueda.get(i) +", elemento: " +busqueda.get(i+1) +"\n";
            }
            return resultado;
        }
        return null;
    }

    /**
     *
     * @return Devuelve los parámetros utilizados para la generación del conjunto actual
     */
    public ArrayList<Integer> getParametrosActual(){
        ArrayList<String> t = conjuntos.getDatosConjunto();
        String aux = t.get(2);
        ArrayList<Integer> res = new ArrayList<Integer>();
        for(String a : aux.split(";")){
            res.add(Integer.valueOf(a));
        }
        return res;
    }

    /**
     * Cambia la canción de la agrupación nA a la nB del conjunto actual
     * @param nA número agrupación original
     * @param nB número de la agrupación final
     * @param id id de la canción a mover
     * @return true si se ha realizado correctamente
     */
    public boolean cambiarCancionAgrupacion(String nA,String nB,String id){
        return conjuntos.cambiarAgrupacionCancion(Integer.valueOf(nA),Integer.valueOf(nB),id);

    }
}

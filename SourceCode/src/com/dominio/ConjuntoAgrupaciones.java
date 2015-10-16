package com.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Quibus on 01/05/2015.
 * @author Alejandro Quibus
 */
public class ConjuntoAgrupaciones {
    //Parametros privados
    private ArrayList<Agrupacion> agrupaciones;
    private String nombre; //Al autogenerarse tiene un nombre generico
    private String metodo;
    private ArrayList<Integer> parametros;
    private Boolean modificado;
    private String descripcion;
    private String origenDatos;

    //Creadora por defecto

    /**
     * Creadora por defecto
     */
    public ConjuntoAgrupaciones(){
        agrupaciones = new ArrayList<>();
        parametros = new ArrayList<>();
        nombre = null; //No tiene un nombre al crearse, solo cuando se vaya a guardar
        modificado = false;
    }

    /**
     * Creadora con parametros
     * @param agrupaciones Todas las agrupaciones ya inicializadas del Conjunto
     * @param nombre El nombre de la agrupación
     * @param metodo El método usado para generar el conjunto
     * @param parametros Los parámetros que se han utilizado para generar el conjunto
     * @param modificado Booleano que indica si se ha modificado el conjunto (falso al crearse)
     * @param descripcion Descripción del conjunto
     */
    public ConjuntoAgrupaciones(ArrayList<Agrupacion> agrupaciones, String nombre, String metodo, ArrayList<Integer> parametros, Boolean modificado, String descripcion, String origenDatos) {
        this.agrupaciones = agrupaciones;
        this.nombre = nombre;
        this.metodo = metodo;
        this.parametros = parametros;
        this.modificado = modificado;
        this.descripcion = descripcion;
        this.origenDatos = origenDatos;
    }

    /**
     * Nos permite buscar una canción en el conjunto
     * <p>
     *     Al buscar una canción puede ser que la canción aparezca más de una vez, entonces aparecerán n pares de combinaciones de apariciones, si no hay ninguna aparición devuelve un ArrayList con elemento 0 = -1
     * </p>
     * @param c la canción a buscar
     * @return Cada dos elementos consecuitivos del array contienen una aparición de la Canción de ese par el primer elemento es el nº de conjunto y el segundo la posición de la canción en el conjunto. Si no lo encuentra devuelve una lista con un elemento -1
     */
    public ArrayList<Integer> buscarCancion(Cancion c){
        ArrayList<Integer> ret = new ArrayList<>();
        int pa;
        for(int i = 0; i < agrupaciones.size(); i++){
            pa = agrupaciones.get(i).getPosicion(c);
            if(pa>-1){
                ret.add(i);
                ret.add(pa);
            }
        }
        if(ret.isEmpty()) ret.add(-1);
        return ret;
    }

    /**
     * Nos permite funsionar dos Agrupaciones que han de existir del conjunto en un único conjutno a1 contendrá todos los elementos (union) de a2
     * @param a1 posición en el conjunto de la agrupación a fusionar 1, contendrá sus elementso y los de a2
     * @param a2 posición en el conjunto de la agrupación a fusionar 2, será destruido
     */
    public void fusionarAgrupaciones(Integer a1, Integer a2){
        if(a1 >= 0 && a2 >= 0 && a1<agrupaciones.size() && a2 < agrupaciones.size()){
            //Primero obtenemos todas las canciones de a2 y lo eliminamos
            List<Cancion> aux = agrupaciones.get(a2).getCanciones();
//            Agrupacion at = agrupaciones.get(a2); //IMPORTANTE PARA USAR REMOVE HAY QUE PASARLE EL OBJETO ENTERO PARA COMPARAR
            int paux = a2; //Convertimos a int el Integer para poder eleminar usando remove
            agrupaciones.remove(paux); //Importante se eliminará también la agrupación, pero no las canciones (del total de canciones) de la agrupación eleminiada
            //Segundo añadimos todas las canciones que tenía a2 respectando su orden en a1
            for(Cancion c : aux){
                agrupaciones.get(a1).addCancion(c);
            }
        }
    }

    /**
     * Elimina la agrupación en la posición p del conjunto (inclusive el objeto agrupación)
     * @param p La agrupación en la posición p del conjunto es eliminada
     */
    public boolean removeAgrupacion(Integer p){
        if(p>=0 && p < agrupaciones.size()){
            //Para que el remove funcione no se puede pasar un Integer ya que si no lo considera un objeto y no encontrará ese objeto integer en el Arrau, por tanto lo convertimos
            int aux = p;
            agrupaciones.remove(aux);
            return true;
        }
        return false;
    }

    /**
     * Obtienes todas las agrupaciones que contiene el Conjunto de agrupaciones
     * @return ArrayList de todas las agrupaciones
     */
    public ArrayList<Agrupacion> getAgrupaciones() {
        return agrupaciones;
    }

    /**
     * Las agrupaciones del conjunto son reemplazadas por "agrupaciones"
     * @param agrupaciones Arraylist con todas las agrupaciones para el conjunto
     */
    public void setAgrupaciones(ArrayList<Agrupacion> agrupaciones) {
        this.agrupaciones = agrupaciones;
    }

    /**
     *
     * @return Devuelve el nombre del conjunto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Permite cambiar el nombre que tiene el Conjunto
     * @param nombre Nuevo nombre para el conjunto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return El método utilizado para generar el conjunto
     */
    public String getMetodo() {
        return metodo;
    }

    /**
     * Permite cambiar el método utilzado para generar el conjunto
     * @param metodo el nuevo método utilizado para generar el conjunto {Clique,Louvain,Newman}
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     *
     * @return Los parámetros utilizados para generar el conjutno de agrupaciones
     */
    public ArrayList<Integer> getParametros() {
        return parametros;
    }

    /**
     * Permite cambiar los parámetros usados en la generación del algoritmo (es solo un historico)
     * @param parametros
     */
    public void setParametros(ArrayList<Integer> parametros) {
        this.parametros = parametros;
    }

    /**
     *
     * @return True si el conjunto ha sido modificado respecto al autogeenrado original
     */
    public Boolean isModificado() {
        return modificado;
    }

    /**
     * Permite indicar manualmente si el conjunto ha sido modificado
     * @param modificado ture si ha sido modificado el consulto
     */
    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    /**
     *
     * @return La descripción que tiene el conjunto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Permite modificar la descripción del conjunto
     * @param descripcion La nueva descripción del conjunto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return El número de agrupacioens que tiene el Conjunto de Agrupaciones
     */
    public Integer getNumAgrupaciones(){
        return agrupaciones.size();
    }

    /**
     *
     * @return  Devuelva en base a que canciones (el origen) se ha generado el conjunto
     */
    public String getOrigenDatos() {
        return origenDatos;
    }

    /**
     * Permite especificar el origen, de donde se consiguieron los datos
     * @param origenDatos
     */
    public void setOrigenDatos(String origenDatos) {
        this.origenDatos = origenDatos;
    }

    /**
     * Añade una agrupación nueva al conjunto
     * @param nA la nueva agrupación a añadir
     */
    public void addAgrupacion(Agrupacion nA){
        agrupaciones.add(nA);
    }

    /**
     * Devuelve la agrupación que pertenece a la posción i
     * @param i poisición de la agrupación
     * @return La agrupación que pertenece al conjunto en esa posición
     */
    public Agrupacion getAgrupacion(Integer i){
        if(i>= 0 && i < agrupaciones.size()) return agrupaciones.get(i);
        return null;
    }

    //FALTA TODAS LAS OPERACIONES PARA ALTERAR LAS AGRUPACIONES

    /**
     *
     * @return Devuelve todos los campos de datos (exceptuando las agrupaciones) que contiene el conjunto
     */
    public ArrayList<String> getDatosConjunto(){
        ArrayList<String> aux = new ArrayList<>();
        aux.add(nombre);
        aux.add(metodo);
        //Transformamos los parametros a un solo campo
        String par = parametros.get(0)+";"+parametros.get(1)+";"+parametros.get(2) + ";"+parametros.get(3)+";"+parametros.get(4);
        aux.add(par);
        aux.add(modificado.toString());
        aux.add(origenDatos);
        aux.add(descripcion);
        return aux;
    }

    /**
     *
     * @return Devuelve en un string todos los IDs de las canciones de todas las agrupaciones
     */
    public ArrayList<ArrayList<String> > getDatosAgrupaciones(){
        ArrayList<ArrayList<String> > aux = new ArrayList<>();
        //Para cada agrupación obtenemos sus ids de canciones
        for(Agrupacion a : agrupaciones){
            //Cada agrupación necesitamos todas sus canciones
            ArrayList<String> ids = new ArrayList<>();
            for(Cancion c : a.getCanciones()){
                ids.add(c.getId());
            }
            aux.add(ids);
        }
        return aux;
    }

    /**
     * Mueve la Canción (c) de la Agrupación (numAO) a la Agrupación (numAF)
     * @param numAO El número de la agrupación original de la canción
     * @param numAF El número de la agrupación final (a mover)
     * @param c La canción de cambiar de Agrupación
     */
    public void cambiarCancionAgrupacion(int numAO,int numAF,Cancion c){
        agrupaciones.get(numAO).borrarCancion(c);
        agrupaciones.get(numAF).addCancion(c);
        checkAgrupacionNoVacia(numAO);

    }

    /**
     * Borra la canción c de la agrupación numA
     * @param numA El número de la donde está la canción c
     * @param c La canción a borrar
     */
    public void borrarCancion(int numA,Cancion c){
        agrupaciones.get(numA).borrarCancion(c);
        checkAgrupacionNoVacia(numA);
    }

    /**
     * Operación de mantenimiento se asegura que si una Agrupación está vacia (tras operaciones que modifican sus canciones, p.e borrarcancion)
     * sea eliminada del conjunto
     * @param n El número de la agrupación a evaluar
     */
    private void checkAgrupacionNoVacia(int n){
        if(agrupaciones.get(n).getNumCanciones() == 0) agrupaciones.remove(n);
    }



}

package com.dominio;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alejandro Quibus on 01/05/2015.
 * @author Alejandro Quibus
 */
public class Agrupacion {

    private ArrayList<Cancion> canciones;

    /**
     * Creadora por defecto
     */

    public Agrupacion(){
        canciones = new ArrayList<>();

    }

    /**
     * Creadora  pasando un listado de canciones
     * @param  canciones todas las canciones que contiene esta agrupacion
     */
    public Agrupacion(ArrayList<Cancion> canciones) {
       this.canciones = canciones;
    }

    /**
     * Devuvelve todas las canciones que contiene la Agrupacion
     * @return el número de nodos que comparten en común esos dos cliques
     */
    public ArrayList<Cancion> getCanciones(){
        return canciones;
    }

    /**
     * Añadimos la canción a la agrupación, importante que puede estar ya en la agrupación pero se repite
     * @param c La canción a añadir no ha de ser null
     */
    public void addCancion(Cancion c){
        if(c!= null) canciones.add(c); //Nos aseguramos que no vale null
    }
    /**
     * Nos permite saber si la canción "c" pertenece a la agrupación
     * @param c La canción a buscar no ha de ser null
     * @return True si existe la cacnión y false si no en la agrupación
     */
    public Boolean existeCancion(Cancion c){
        return canciones.contains(c);
    }
    /**
     * Nos permite borrar la canción c de la agrupación
     * @param c La canción a buscar no ha de ser null
     */
    public void borrarCancion(Cancion c){
        canciones.remove(c);
    }
    /**
     * Nos permite identeificar la posición de una canción dentro de la agrupación
     * @param c La canción a buscar no ha de ser null
     * @return Devuelve la posición del a canción c en la agrupación, -1 si es null
     */
    public Integer getPosicion(Cancion c){
        return canciones.indexOf(c);
    }

    /**
     * Nos permite cambiar, intercambiar la posición de dos elementos en la agrupación
     * @param pOriginal la posición original del elemento a intercambiar
     * @param pFinal la posición final del elemento
     */
    public void intercambiarPos(Integer pOriginal,Integer pFinal){
        //Miramos primero si son validos los int
        if(pOriginal != pFinal && pOriginal >= 0 && pOriginal < canciones.size() && pFinal >= 0 && pFinal < canciones.size()) {
            Collections.swap(canciones, pOriginal, pFinal);
        }
    }

    /**
     * Dada la posición en la agrupación nos permite obtener su canción asociada
     * @param pos la posición a buscar en la agrupación
     * @return La canción que corresponde a esa posición, null si no existe canción en esa posición
     */
    public Cancion getCancion(int pos){
        if(pos >= canciones.size()) return null;
        return canciones.get(pos);
    }

    /**
     *
     * @return Devuelve el número de canciones en la agrupación
     */
    public Integer getNumCanciones(){
        return canciones.size();
    }



    //Para poder poder comparar agrupaciones
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agrupacion that = (Agrupacion) o;

        return !(canciones != null ? !canciones.equals(that.canciones) : that.canciones != null);

    }

    @Override
    public int hashCode() {
        return canciones != null ? canciones.hashCode() : 0;
    }

}

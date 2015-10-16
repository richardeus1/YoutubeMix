package com.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase ConjuntoLista
 * @author Natali Balón Pérez
 */
public class ConjuntoLista {
    private ArrayList<Lista> conjuntoDeListas;

    /**
     * Creadora por defecto
     */
    public ConjuntoLista(){
        this.conjuntoDeListas = new ArrayList<>();
    }

    /**
     *
     * @return conjunto de listas.
     */
    public ArrayList<Lista> getConjuntoDeListas() {
        return conjuntoDeListas;
    }

    /**
     * Modifica
     * @param conjuntoDeListas nuevo conjunto de listas.
     */
    public void setConjuntoDeListas(ArrayList<Lista> conjuntoDeListas) {
        this.conjuntoDeListas = conjuntoDeListas;
    }

    /**
     * Añade una lista al conjunto
     * @param nomL nombre de la lista (identificador)
     * @param username usuario de la lista (identificador)
     * @param desc descripción de la lista
     * @param canciones conjunto de canciones de la lista(String)
     * @return true si se añade correctamente la lista, false en cualquier otro caso.
     */
    public Boolean addLista(String nomL, String username, String desc, ArrayList<Cancion> canciones){
        if (existeLista(nomL)) {
            return false;

        }
        else {
            Lista l = new Lista(nomL, username, desc, canciones);
            //anado al conjunto de las canciones
            conjuntoDeListas.add(l);
            return true;
        }
    }

    /**
     *
     * @param nomL nombre de la lista (identificador)
     * @param username usuario de la lista (identificador)
     * @param desc descripción de la lista
     * @return  true si se añade correctamente la lista, false en cualquier otro caso.
     */
    public Boolean addLista(String nomL, String username, String desc){
        if (existeLista(nomL)) {
            return false;

        }
        else {
            Lista l = new Lista(nomL, username, desc);
            //anado al conjunto de las canciones
            conjuntoDeListas.add(l);
            return true;
        }
    }

    /**
     * Borra una lista del conjunto
     * @param noml nombre de la lista (identificador) que se desea eliminar existente en el conjunto
     */
    public void removeLista(String noml){
        ArrayList<Lista> tem = new ArrayList<Lista>();
        for(Lista l : conjuntoDeListas){
            if (!(l.getNombre().equals(noml))) {
                tem.add(l);
            }
        }
        conjuntoDeListas = tem;
    }

    /**
     *
     * @param noml nombre de la lista donde se desea añadir la cancion que existe en el sistema
     */
    public void addCancionALista(String noml, Cancion c){
        Integer i = indice(noml);
        conjuntoDeListas.get(i).addCancionALista(c);
    }

    /**
     *
     * @param noml nombre de la lista
     * @return posición de la lista en el conjunto, sino -1.
     */
    private Integer indice(String noml){
        for(int i = 0; i<conjuntoDeListas.size(); i++){
            if(conjuntoDeListas.get(i).getNombre().equals(noml)) return i;
        }
        return -1;
    }

    /**
     * Elimina una canción de una lista
     * @param noml lista existente a la que se desea eliminar la canción
     */
    public void removeCancionDeLista(String noml, Cancion c){
        Integer i = indice(noml);
        conjuntoDeListas.get(i).removeCancionDeLista(c);
    }

    /**
     * Modifica los atributos de una lista
     * @param noml lista existente que se desea modificar
     * @param nomNuevo nuevo nombre de la lista
     * @param descNueva nueva descripción de la lista
     */
    public Boolean setAtributosDeLista(String noml, String nomNuevo, String descNueva) {
        //compruebo que no exista una lista con ese nombre
        if (noml.equals(nomNuevo)){
            Integer in = indice(noml);
            conjuntoDeListas.get(in).setDescripcion(descNueva);

            conjuntoDeListas.get(in).setDescripcion(descNueva);

            return true;
        }
        else {
            Boolean b = existeLista(nomNuevo);
            if (!b) {
                Integer in = indice(noml);
                conjuntoDeListas.get(in).setNombre(nomNuevo);
                conjuntoDeListas.get(in).setDescripcion(descNueva);
                return true;
            }
            return false;
        }
    }

    /**
     *
     * @param l identificación de la lista
     * @param titulo titulo de una canción
     * @param autor autor de una canción
     * @return true si existe la canción (titulo + autor) en la lista, false en caso contrario.
     */
    public Boolean existeCancionEnLista(String l, String titulo, String autor){
        Integer i = indice(l);
        ArrayList<Cancion> cancions = conjuntoDeListas.get(i).getConjuntoDeCanciones();
        for(Cancion canc : cancions){
            if(canc.getTitulo().equals(titulo) && canc.getAutor().equals(autor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detecta una lista
     * @param nomL nombre de una lista
     * @return true si existe la lista l en el sistema, sino false.
     */
    private Boolean existeLista(String nomL){
        for(Lista l : conjuntoDeListas){
            if(l.getNombre().equals(nomL)) return true;
        }
        return false;
    }

    /**
     * Devuelve los nombres de las listas que existen en el sistema
     * @return conjunto de nombres de las listas
     */
    public ArrayList<String> getNombresListas(){
        ArrayList<String> l = new ArrayList<>();
        for(Lista l2 : conjuntoDeListas){
            l.add(l2.getNombre());
        }
        return l;
    }

    /**
     * Devuelve los datos de una lista
     * @param nomL nombre de la lista
     * @return devuelve la lista identificada por nomL, sino devuelve una lista vacia.
     */
    public Lista getLista(String nomL){
        for(Lista l : conjuntoDeListas){
            if(l.getNombre().equals(nomL)) return l;
        }
        return new Lista();
    }

    /**
     *
     * @param noml nombre de la lista
     * @return devuelve los identificadores de todas las canciones que existen en la lista "noml".
     */
    public ArrayList<String> getCancionesNoms(String noml){
        ArrayList<String> canc = new ArrayList<>();
        Integer index = indice(noml);
        ArrayList<Cancion> cancions = conjuntoDeListas.get(index).getConjuntoDeCanciones();
        for (Cancion c : cancions){
            canc.add(c.getId());
        }
        return canc;
    }

    /**
     *
     * @param indiceLista posición de una lista
     * @param IdCancion identificación de una canción
     * @return el índice de la canción
     */
    private Integer indiceCancion(Integer indiceLista, String IdCancion){
        ArrayList<Cancion> cancions = conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones();
        for(int i = 0; i<cancions.size(); i++){
            if(cancions.get(i).getId().equals(IdCancion)) return i;
        }
        return -1;
    }

    /**
     *
     * @param listaActual nombre de una lista
     * @param IdCancion identificación de una canción
     * @param subir indicación de lo que se desea hacer (true subir la canción, false bajar canción)
     * @return true si se ha modificado el indice de la canción, false en cualquier otro caso.
     */
    public Boolean modificarIndiceCancion(String listaActual, String IdCancion, Boolean subir) {
        Integer indiceLista = indice(listaActual);
        Integer indiceCancion = indiceCancion(indiceLista, IdCancion);
        if(subir) {
            if (indiceCancion == 0) return false;
            Cancion l1 = conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().get(indiceCancion);
            Cancion l2 = conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().get(indiceCancion - 1);
            conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().set(indiceCancion - 1, l1);
            conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().set(indiceCancion, l2);
        }
        //quiere decir que queremos bajar
        else {
            if (indiceCancion == conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().size()-1) return false;
            Cancion l1 = conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().get(indiceCancion);
            Cancion l2 = conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().get(indiceCancion + 1);
            conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().set(indiceCancion + 1, l1);
            conjuntoDeListas.get(indiceLista).getConjuntoDeCanciones().set(indiceCancion, l2);

        }
        return true;
    }

    /**
     *
     * @param textoBusqueda identificación de una canción
     * @return devuelve la posiciones de las listas donde se encuentra la cancón con la respectiva posición
     * de la canción dentro de la lista.
     */
    public ArrayList<Integer> buscarCancion(String textoBusqueda) {
        ArrayList<Integer> busqueda = new ArrayList<>();
        for(Integer i = 0; i<conjuntoDeListas.size(); ++i ){
            Integer indice = indiceCancion(i, textoBusqueda);
            if (indice!=-1){
                busqueda.add(i);
                busqueda.add(indice);
            }
        }
        if (busqueda.size()==0) busqueda.add(-1);
        return busqueda;
    }
}

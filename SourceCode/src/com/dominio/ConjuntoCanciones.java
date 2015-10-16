package com.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreu Conesa
 */
public class ConjuntoCanciones {

    private List<Cancion> canciones;

    /**
     * ConjuntoCanciones() constructor vacio
     */
    public ConjuntoCanciones() {
        this.canciones = new ArrayList<Cancion>();
    }

    /**
     * ConjuntoCanciones() constructor con todos los atributos
     * @param canciones pasa a ser las canciones del parametro implicito
     */
    public ConjuntoCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    /**
     * anyade una cancion a la lista de canciones
     * @param titulo es el titulo de la cancion a anyadir
     * @param autor es el autor de la cancion a anyadir
     * @param album es el album de la cancion a anyadir
     * @param genero es el genero de la cancion a anyadir
     * @param numRep es el numero de reproducciones por parte del creador de la cancion a anyadir
     * @param fechaInsercion es la fecha de insercion de la cancion a anyadir
     * @param creador es el creador de la cancion a anyadir
     * @param anyo es el anyo de la cancion a anyadir
     * @return true si la cancion se ha anyadido de forma correcta, false si no se ha podido anyadir porque ya existia
     */
    public Boolean anadirCancion(String titulo, String autor, String album, String genero, Integer numRep, String fechaInsercion, String creador, Integer anyo) {
        Boolean res = false;
        String id = titulo + ";" + autor;
        Cancion c1 = buscarCancion(id);
        if (c1.getTitulo() == null) {
            Cancion c = new Cancion(titulo,autor,album,genero,numRep,fechaInsercion,creador,anyo);
            canciones.add(c);
            res = true;
        }
        return res;
    }

    /**
     * borra una cancion de la lista de canciones
     * @param id es el titulo;autor de una cancion
     * @param alias puede ser el admin o el usuario actual
     * @return true si la cancion se ha borrado/modificado de forma correcta, false si no ha sido asi
     */
    public Boolean borrarCancion(String id, String alias) {
        List<String> tituloYautor = traducirIdentificador(id);
        String t = tituloYautor.get(0);
        String a = tituloYautor.get(1);
        Boolean trobada = false;
        for (int i = 0; i < canciones.size() && !trobada; i++) {
            if (t.equals(canciones.get(i).getTitulo()) && a.equals(canciones.get(i).getAutor())) {
                trobada = true;
                if (alias.equals("Admin")) canciones.remove(i);
                else if (alias.equals(canciones.get(i).getCreador()))canciones.get(i).setCreador("Admin");
            }
        }
        return trobada;
    }

    /**
     * modifica algunos atributos de una cancion de la lista de canciones
     * @param t es el titulo de la cancion a modificar
     * @param a es el autor de la cancion a modificar
     * @param album es el nuevo album de la cancion
     * @param genero es el nuevo genero de la cancion
     * @param anyo es el nuevo anyo de la cancion
     * @return true si la cancion se ha modificado correctamente, false si no se ha podido modificar
     */
    public Boolean modificarCancion(String t, String a, String album, String genero, Integer anyo, String alias) {
        Boolean res = false;
        Boolean found = false;
        for (int i = 0; i < canciones.size() && !found; i++) {
            if (t.equals(canciones.get(i).getTitulo()) && a.equals(canciones.get(i).getAutor())) {
                found = true;
                if (alias.equals("Admin") || alias.equals(canciones.get(i).getCreador())) {
                    canciones.get(i).setAlbum(album);
                    canciones.get(i).setGenero(genero);
                    canciones.get(i).setAnyo(anyo);
                    res = true;
                }
            }
        }
        return res;
    }


    /**
     * separa los campos de un string con formato titulo;autor
     * @param id
     * @return una lista de cadenas con dos posiciones: la primera es el titulo y la segunda el autor
     */
    private List<String> traducirIdentificador(String id) {
        String idArray[] = id.split(";");
        String t = idArray[0];
        String a = idArray[1];
        List<String> res;
        res = new ArrayList<String>();
        res.add(t);
        res.add(a);
        return res;
    }

    /**
     * busca una cancion en la lista de canciones
     * @param id en formato (titulo;autor) de la cancion a buscar
     * @return la cancion con sus atributos si existe en la lista, o una cancion vacia si no existe
     */
    public Cancion buscarCancion(String id) {
        List<String> tituloYautor = traducirIdentificador(id);
        String t = tituloYautor.get(0);
        String a = tituloYautor.get(1);
        Cancion res = new Cancion();
        Boolean found = false;
        for (int i = 0; i < canciones.size() && !found; i++) {
            if (t.equals(canciones.get(i).getTitulo()) && a.equals(canciones.get(i).getAutor())) {
                found = true;
                res = new Cancion(canciones.get(i));
            }
        }
        return res;
    }

    /**
     * @return la lista de canciones
     */
    public List<Cancion> getCanciones() {
        return canciones;
    }

    /**
     * modifica el atributo creador por "Admin" de las canciones cuyo creador sea alias
     * @param alias de las canciones cuyo creador quiera modificarse por "Admin"
     */
    public void modificarCreadorCancion(String alias) {
        for (int i = 0; i < canciones.size(); i++) {
            if (alias.equals(canciones.get(i).getCreador())) {
                canciones.get(i).setCreador("Admin");
            }
        }
    }

    /**
     * @param alias del usuario cuyas canciones se quieran consultar
     * @return la lista de canciones cuyo creador coincida con el parametro alias
     */
    public List<Cancion> getCancionesCreador(String alias){
        List<Cancion> res = new ArrayList<Cancion>();
        for (int i = 0; i < canciones.size(); i++) {
            if (alias.equals(canciones.get(i).getCreador())) {
                res.add(canciones.get(i));
            }
        }
        return res;
    }

    /**
     * @return los identificadores en formato titulo;autor de canciones
     */
    public ArrayList<String> getNombreCanciones(){
        ArrayList<String> nombres = new ArrayList<String>();
        for(Cancion c : canciones){
            nombres.add(c.getId());
        }
        return nombres;
    }

    /**
     * @param IdCancion en formato titulo;autor
     * @return titulo de la cancion identificada con IdCancion
     */
    private String getTituloOK(String IdCancion){
        String str = IdCancion;
        String delimiter = ";";
        String[] temp;
        temp = str.split(delimiter);
        return temp[0];

    }

    /**
     * @param IdCancion en formato titulo;autor
     * @return autor de la cancion identificada con IdCancion
     */
    private String getAutorOK(String IdCancion){
        String str = IdCancion;
        String delimiter = ";";
        String[] temp;
        temp = str.split(delimiter);
        return temp[1];
    }

    /**
     * @param IdCancion en formato titulo;autor de la cancion cuyo numRep se quiera modificar
     * @param rep incremento o decremento que se aplica al numRep de la cancion identificada por IdCancion
     * @param aumentar true indica que rep implica incremento, false implica decremento
     */
    public void modificarNumeroReproducciones(String IdCancion, Integer rep, Boolean aumentar) {
        String titulo = getTituloOK(IdCancion);
        String autor = getAutorOK(IdCancion);
        Boolean found = false;
        for (int i = 0; i < canciones.size() && !found; i++) {
            if (titulo.equals(canciones.get(i).getTitulo()) && autor.equals(canciones.get(i).getAutor())) {
                found = true;
                Integer valor;
                if (aumentar)
                    valor = canciones.get(i).getNumRep()+rep;
                else
                    valor = canciones.get(i).getNumRep()-rep;
                if (valor < 0) valor = 0;
                canciones.get(i).setNumRep(valor);
            }
        }
    }
}

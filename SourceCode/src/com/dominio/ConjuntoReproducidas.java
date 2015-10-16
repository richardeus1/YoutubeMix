package com.dominio;

import java.util.ArrayList;

/**
 * La clase ConjuntoReproducidas
 * @author Natali Balón
 */
public class ConjuntoReproducidas {
    private ArrayList<Reproducida> conjuntoReproducidas;

    /**
     * Creadora por defecto.
     */
    public ConjuntoReproducidas(){
        this.conjuntoReproducidas = new ArrayList<>();
    }

    /**
     *
     * @return conjunto de reproducciones
     */
    public ArrayList<Reproducida> getConjuntoReproducidas() {
        return conjuntoReproducidas;
    }

    /**
     * Modifica el conjunto de reproducciones.
     * @param conjuntoReproducidas nuevo conjunto de reproducidas.
     */
    public void setConjuntoReproducidas(ArrayList<Reproducida> conjuntoReproducidas) {
        this.conjuntoReproducidas = conjuntoReproducidas;
    }

    /**
     * Modifica
     * @param u alias de un usuario
     * @param t titulo de una canción.
     * @param a autor de una canción.
     * @param n numero de reproducciones a aumentar o disminuir
     * @param aumentar indicación a eliminar o aumentar.
     * @return true si se ha modificado el numero de reproduccion, false en caso contrario.
     */
    public Boolean modificarReproducidas(String u, String t, String a, Integer n, Boolean aumentar) {
        Integer numRepActual = 0;
        Boolean found = false;
        Boolean encontradaCancion = false;
        Integer indexCancion = 0;
        Integer index=0;
        Integer index2 = 0;
        Reproducida rep = new Reproducida();
        for (int i = 0; i < conjuntoReproducidas.size() && !found; i++) {
            if (t.equals(conjuntoReproducidas.get(i).getTitulo()) && a.equals(conjuntoReproducidas.get(i).getAutor())) {
                encontradaCancion = true;
                indexCancion = i;
                //obtengo el conjunto de usuarios y sus reproducidas
                ArrayList<UsuarioNumero> userNumRepr = conjuntoReproducidas.get(i).getConjuntoUsuario();
                for (int j = 0; j<userNumRepr.size(); ++j) {
                    UsuarioNumero user = userNumRepr.get(j);
                    if (user.getUser().equals(u)) {
                        found = true;
                        index = i;
                        index2 = j;
                    }

                }
            }
        }
        if (found) {
            String uMoment = conjuntoReproducidas.get(index).getConjuntoUsuario().get(index2).getUser();
            Integer rePro = conjuntoReproducidas.get(index).getConjuntoUsuario().get(index2).getNumRepro();
            //Estoy incrementado
            if (aumentar) {
                rePro+=n;
                UsuarioNumero nuevo = new UsuarioNumero(uMoment, rePro);
                conjuntoReproducidas.get(index).getConjuntoUsuario().set(index2, nuevo);
                return true;
            } else {
                rePro -= n;
                if (!(rePro < 0)) {
                    UsuarioNumero nuevo = new UsuarioNumero(uMoment, rePro);
                    conjuntoReproducidas.get(index).getConjuntoUsuario().set(index2, nuevo);
                    return true;
                }
            }
        }
        else {
            //nunca ha escuchado esta cancion y ahora quiere aumentar
            if (aumentar){
                if (encontradaCancion) {
                    UsuarioNumero nuevo = new UsuarioNumero(u, n);
                    conjuntoReproducidas.get(indexCancion).getConjuntoUsuario().add(nuevo);
                }
                else {
                    ArrayList<UsuarioNumero> lista = new ArrayList<>();
                    UsuarioNumero nuevo = new UsuarioNumero(u, n);
                    lista.add(nuevo);
                    Reproducida reprod = new Reproducida(t, a, lista);
                    conjuntoReproducidas.add(reprod);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Indice de canción reproducida
     * @param titulo titulo de una canción
     * @param autor autor de una canción.
     * @return -1 si no se ha reproducido nunca esa canción (titulo + autor) por cualquier usuario, sino numero
     * positivo (>=0) que indica la posición de la canción en la lista.
     */
    private Integer indiceCancionYaReproducida(String titulo, String autor){
        for (int i = 0; i<conjuntoReproducidas.size(); i++){
            if (conjuntoReproducidas.get(i).getTitulo().equals(titulo) && conjuntoReproducidas.get(i).getAutor().equals(autor)) return i;
        }
        return -1;
    }

    /**
     *
     * @param t titulo de una canción
     * @param a autor de una canción
     * @return null si no se ha reproducido nunca la canción (titulo + autor), sino alias de los usuarios que han reproducido esa canción.
     */
    public ArrayList<String> getNombresUsuario(String t, String a){
        Integer index = indiceCancionYaReproducida(t, a);
        if(index == -1) return null; //Caso de que no se ha encontrado ningún usuario que haya reproducido esa canción
        ArrayList<String> users = new ArrayList<>();
        ArrayList<UsuarioNumero> todos = conjuntoReproducidas.get(index).getConjuntoUsuario();
        for (UsuarioNumero o : todos){
            users.add(o.getUser());
        }
        return users;
    }


    /**
     * Añade una canción reproducida por diferentes usuarios.
     * @param titulo titulo de una canción existente.
     * @param autor autor de una canción existente.
     * @param list conjunto de usuarios que han reproducido la canción con su respectivo numero de reproducción.
     */
    public void addReproducida(String titulo, String autor, ArrayList<UsuarioNumero> list){
        Integer index = indiceCancionYaReproducida(titulo, autor);
        if (index==-1) {
            Reproducida n = new Reproducida(titulo, autor, list);
            conjuntoReproducidas.add(n);
        }
    }

    /**
     *
     * @param IdCancion identificación de una canción
     * @return devuelve el titulo de la canción.
     */
    private String getTituloOK(String IdCancion){
        String str = IdCancion;
        String delimiter = ";";
        String[] temp;
        temp = str.split(delimiter);
        return temp[0];

    }

    /**
     *
     * @param IdCancion identificación de una canción.
     * @return devuelve el autor de la canción.
     */
    private String getAutorOK(String IdCancion){
        String str = IdCancion;
        String delimiter = ";";
        String[] temp;
        temp = str.split(delimiter);
        return temp[1];
    }

    /**
     * Modifica
     * @param u alias de un usuario.
     * @param IdCancion identificación de una canción.
     * @param n numero de reproducciones a aumentar o disminuir
     * @param aumentar indicación a eliminar o aumentar el numero de reproducción.
     * @return true si se ha modificado el numero de reproduccion, false en caso contrario.
     */
    public Boolean modificarReproducidas(String u, String IdCancion, Integer n, Boolean aumentar) {
        String t = getTituloOK(IdCancion);
        String a = getAutorOK(IdCancion);
        Integer numRepActual = 0;
        Boolean found = false;
        Boolean encontradaCancion = false;
        Integer indexCancion = 0;
        Integer index=0;
        Integer index2 = 0;
        Reproducida rep = new Reproducida();
        for (int i = 0; i < conjuntoReproducidas.size() && !found; i++) {
            if (t.equals(conjuntoReproducidas.get(i).getTitulo()) && a.equals(conjuntoReproducidas.get(i).getAutor())) {
                encontradaCancion = true;
                indexCancion = i;
                //obtengo el conjunto de usuarios y sus reproducidas
                ArrayList<UsuarioNumero> userNumRepr = conjuntoReproducidas.get(i).getConjuntoUsuario();
                for (int j = 0; j<userNumRepr.size(); ++j) {
                    UsuarioNumero user = userNumRepr.get(j);
                    if (user.getUser().equals(u)) {
                        found = true;
                        index = i;
                        index2 = j;
                    }

                }
            }
        }
        if (found) {
            String uMoment = conjuntoReproducidas.get(index).getConjuntoUsuario().get(index2).getUser();
            Integer rePro = conjuntoReproducidas.get(index).getConjuntoUsuario().get(index2).getNumRepro();
            //Estoy incrementado
            if (aumentar) {
                rePro+=n;
                UsuarioNumero nuevo = new UsuarioNumero(uMoment, rePro);
                conjuntoReproducidas.get(index).getConjuntoUsuario().set(index2, nuevo);
                return true;
            } else {
                rePro -= n;
                if (!(rePro < 0)) {
                    UsuarioNumero nuevo = new UsuarioNumero(uMoment, rePro);
                    conjuntoReproducidas.get(index).getConjuntoUsuario().set(index2, nuevo);
                    return true;
                }
            }
        }
        else {
            //nunca ha escuchado esta cancion y ahora quiere aumentar
            if (aumentar){
                if (encontradaCancion) {
                    UsuarioNumero nuevo = new UsuarioNumero(u, n);
                    conjuntoReproducidas.get(indexCancion).getConjuntoUsuario().add(nuevo);
                }
                else {
                    ArrayList<UsuarioNumero> lista = new ArrayList<>();
                    UsuarioNumero nuevo = new UsuarioNumero(u, n);
                    lista.add(nuevo);
                    Reproducida reprod = new Reproducida(t, a, lista);
                    conjuntoReproducidas.add(reprod);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Modifica
     * @param user alias de un usuario existente.
     * @return devuelve conjunto de canciones escuchas por el usuario "user" con su respectivo numero de reproducción.
     */
    public ArrayList<UsuarioNumero> eliminarReproducidas(String user) {
        ArrayList<UsuarioNumero> eliminadas = new ArrayList<>();
        for (Reproducida r : conjuntoReproducidas) {
            ArrayList<UsuarioNumero> usuarios = r.getConjuntoUsuario();
            for (UsuarioNumero u : usuarios){
                if (u.getUser().equals(user)) {
                    Integer valor = u.getNumRepro();
                    Boolean b = modificarReproducidas(user, r.getTitulo(), r.getAutor(), valor, false);
                    if (b) {
                        UsuarioNumero n = new UsuarioNumero(r.getTitulo()+";"+r.getAutor(), valor);
                        eliminadas.add(n);
                    }
                }
            }
        }
        return eliminadas;
    }

    /**
     * @param t es el titulo de la cancion cuyo numRep quiere obtenerse
     * @param a es el autor de la cancion cuyo numRep quiere obtenerse
     * @param user es el usuario del que se quiere conocer el numRep que tiene para la cancion con titulo t, autor a
     * @return 0 si el usuario user no ha reproducido la cancion con titulo t y autor a, su numRep si por el contrario la ha reproducido
     */
    public Integer getNumRep(String t, String a, String user){
        Integer numRep = 0;
        Boolean found = false;
        for (int i = 0; i < conjuntoReproducidas.size() && !found; i++) {
            if (t.equals(conjuntoReproducidas.get(i).getTitulo()) && a.equals(conjuntoReproducidas.get(i).getAutor())) {
                ArrayList<UsuarioNumero> cjtUsuarios = conjuntoReproducidas.get(i).getConjuntoUsuario();
                for (int j = 0; j < cjtUsuarios.size() && !found; ++j){
                    if (cjtUsuarios.get(j).getUser().equals(user)){
                        found = true;
                        numRep = cjtUsuarios.get(j).getNumRepro();
                    }
                }
            }
        }
        return numRep;
    }
}


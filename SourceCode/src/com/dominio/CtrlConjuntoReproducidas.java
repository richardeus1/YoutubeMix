package com.dominio;

import com.CtrlDatos.CtrlDatosLista;
import java.util.ArrayList;

/**
 * La clase CtrlConjuntoReproducidas
 * @author Natali Balón
 */
public class CtrlConjuntoReproducidas {
    private ConjuntoReproducidas conjuntoReproducidas;
    private CtrlDatosLista ctrlDatosLista;
    private CtrlConjuntoCanciones ctrlConjuntoCanciones;
    private CtrlConjuntoUsuarios ctrlConjuntoUsuarios;
    private String user;

    /**
     * Creadora
     * @param ctrlConjuntoCanciones controlador de las canciones
     * @param ctrlConjuntoUsuarios controlador de los usuarios
     * @param usuarioActual alias del usuario que ha iniciado sesion.
     */
    public CtrlConjuntoReproducidas(CtrlConjuntoCanciones ctrlConjuntoCanciones, CtrlConjuntoUsuarios ctrlConjuntoUsuarios, String usuarioActual){
        this.user = usuarioActual;
        this.conjuntoReproducidas = new ConjuntoReproducidas();
        this.ctrlDatosLista = new CtrlDatosLista(usuarioActual);
        this.ctrlConjuntoCanciones = ctrlConjuntoCanciones;
        this.ctrlConjuntoUsuarios = ctrlConjuntoUsuarios;
        leerDatos();
    }

    /**
     * Permite tener en memoria los datos de un fichero.
     */
    public void leerDatos(){
        CtrlConjuntoCanciones ctrlConjuntoCanciones;
        ctrlConjuntoCanciones = new CtrlConjuntoCanciones();
        ArrayList<String> listas = ctrlDatosLista.leerReproducidas();
        for(String l : listas) {
            String t[] = l.split(";");
            String titulo = t[0];
            String autor = t[1];
            int i = 2;
            ArrayList<UsuarioNumero> lista = new ArrayList<>();
            String cancion = titulo + ";" + autor;
            if (ctrlConjuntoCanciones.buscarCancion(cancion).getTitulo() != null) {
                while (i<t.length) {
                //existe cancion identificada por String cancion;
                    String str = t[i];
                    String delimiter = ",";
                    String[] temp;
                    temp = str.split(delimiter);
                    String user = temp[0];
                    if (ctrlConjuntoUsuarios.existeUsuario(user)) {
                        Integer numRep = Integer.parseInt(temp[1]);
                        UsuarioNumero nuevo = new UsuarioNumero(user, numRep);
                        lista.add(nuevo);
                    }
                    ++i;
                }
                conjuntoReproducidas.addReproducida(titulo, autor, lista);
            }

        }
    }


    /**
     *
     * @param listas conjunto de listas de reproducidas del sistema.
     * @return información de todas las reproducidas del usuario.
     */
    private ArrayList<String> convertirLista(ArrayList<Reproducida> listas){
        ArrayList<String> datos = new ArrayList<>();
        for(Reproducida l : listas) {
            String line;
            String titulo = l.getTitulo();
            String autor = l.getAutor();
            line = titulo+";"+autor;
            for (UsuarioNumero c : l.getConjuntoUsuario()) {
                String lineMoment = ";"+ c.getUser()+','+c.getNumRepro();
                line = line + lineMoment;
            }
            datos.add(line);
        }
        return datos;
    }

    /**
     * Permite guardar en los ficheros la información actual de las reproducidas.
     */
    public void guardarDatos(){
        ArrayList<Reproducida> listas = conjuntoReproducidas.getConjuntoReproducidas();
        ArrayList<String> datos = convertirLista(listas);
        ctrlDatosLista.guardarReproducidas(datos);
    }

    /**
     *
     * @param t titulo de una canción.
     * @param a autor de una canción.
     * @return alias de los usuarios que han reproducidos la canción identificada por titulo y autor
     */
    public ArrayList<String> usuariosQueEscucha(String t, String a){
        return conjuntoReproducidas.getNombresUsuario(t, a);
    }

    /**
     *
     * @param list1 usuarios que han escuchado una canción.
     * @param list2 usuarios que han escuchado una canción.
     * @return usuarios que han escuchado las dos canciones.
     */
    private ArrayList<String> intersection(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> list = new ArrayList<String>(list1);
        try {
            list.retainAll(list2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param c1 canción.
     * @param c2 canción
     * @return numero de usuarios que han reproducido las dos canciones
     */
    public Integer numRepComun(Cancion c1,Cancion c2){
        ArrayList<String> usuarios1 = usuariosQueEscucha(c1.getTitulo(), c1.getAutor());
        ArrayList<String> usuarios2 = usuariosQueEscucha(c2.getTitulo(), c2.getAutor());
        if(usuarios1 == null || usuarios2 == null) return 0; //Alguna de las dos nunca ha sido reproducida
        ArrayList<String> usuariosEnComun = intersection(usuarios1, usuarios2);
        return usuariosEnComun.size();
    }

    /**
     *
     * @param IdCancion identificador de una canción
     * @param n numero de reproduciones aumentar.
     * @param aumentar si se desea añadir o quitar reproduciones
     * @return true si se ha modificado las reproducciones de la canción, true en cualquier otro caso.
     */
    public Boolean modificarReproducida(String IdCancion, Integer n, Boolean aumentar){
        Boolean b = conjuntoReproducidas.modificarReproducidas(user, IdCancion, n, aumentar);
        if (b) {
            ctrlConjuntoCanciones.modificarNumeroReproducciones(IdCancion, n, aumentar);
            guardarDatos();
        }
        return b;
    }

    /**
     *
     * @param IdCancion identificador de una canción
     * @param n numero de reproduciones aumentar.
     * @param aumentar true si se desea añadir o quitar reproduciones
     * @return true si se ha añadido la canción reproducida, false en cualquier otro caso.
     */
    public Boolean AddAReproducida(String IdCancion, Integer n, Boolean aumentar){
        Boolean b = conjuntoReproducidas.modificarReproducidas(user, IdCancion, n, aumentar);
        if (b) {
            guardarDatos();
        }
        return b;
    }

    /**
     * Eliminación de todas las reproducidas del usuario
     * @return devuelve todas las canciones, y su reproducción, que elimina.
     */
    public ArrayList<UsuarioNumero> eliminarReproducidas() {
        ArrayList<UsuarioNumero> eliminadasRep = conjuntoReproducidas.eliminarReproducidas(user);
        return eliminadasRep;
    }

    /**
     * llama a la funcion getNumRep de ConjuntoReproducidas
     * @param t es el titulo de la cancion cuyo numero de reproducciones se quiere obtener
     * @param a es el autor de la cancion cuyo numero de reproducciones se quiere obtener
     * @return el numero de reproducciones de la cancion con titulo t y autor a por parte del usuario user, o 0 si no la ha reproducido
     */
    public Integer getNumRep(String t, String a){
        Integer nr = conjuntoReproducidas.getNumRep(t, a, user);
        return nr;
    }


    /**
     *
     * @param can identificador de una canción.
     * @return devuelve el numero de reproducción de la canción seleccionada.
     */
    public Integer numRepro(String can) {
        Cancion c = ctrlConjuntoCanciones.buscarCancion(can);
        return conjuntoReproducidas.getNumRep(c.getTitulo(),c.getAutor(), user);
    }
}

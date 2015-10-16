package com.dominio;

import com.CtrlDatos.CtrlDatosLista;

import java.util.*;

/**
 *
 * La clase CtrlConjuntoLista
 * @author Natali Balón
 *
 */
public class CtrlConjuntoLista {
    private ConjuntoLista conjuntoLista;
    private CtrlDatosLista ctrlDatosLista;
    //para tener la cancion:
    private CtrlConjuntoCanciones ctrlConjuntoCanciones;
    private String user;

    /**
     * Creadora por defecto.
     */
    public CtrlConjuntoLista(CtrlConjuntoCanciones ctrlConjuntoCanciones, String usuarioActual) {
        this.ctrlConjuntoCanciones = ctrlConjuntoCanciones;
        this.conjuntoLista = new ConjuntoLista();
        this.ctrlDatosLista = new CtrlDatosLista(usuarioActual);
        this.user = usuarioActual;
        leerDatos(usuarioActual);
    }

    /**
     * Devuelve las listas que existen en el sistema
     * @return conjunto de listas
     */
    public ArrayList<Lista> getConjuntoDeListas() {
        return conjuntoLista.getConjuntoDeListas();
    }

    /**
     * Añade una lista al conjunto
     * @param nomL nombre de la lista (identificador)
     * @param username usuario de la lista (identificador)
     * @param desc descripción de la lista
     * @param canciones conjunto de canciones de la lista(String)
     */
    public Boolean addListaFicheroGlobal(String nomL, String username, String desc, ArrayList<String> canciones){
        ArrayList<Cancion> cancions = getCancions(canciones);
        Boolean b = conjuntoLista.addLista(nomL, username, desc, cancions);
        return b;
    }

    public Boolean addLista(String nomL, String username, String desc, ArrayList<Cancion> canciones){
        Boolean b = conjuntoLista.addLista(nomL, username, desc, canciones);
        if (b) {
            guardarDatos();
        }
        return b;
    }

    public Boolean addLista(String nomL, String desc){
        Boolean b = conjuntoLista.addLista(nomL, user, desc);
        if (b) {
            guardarDatos();
        }
        return b;
    }

    public ArrayList<Cancion> getCancions(ArrayList<String> canciones){
        ArrayList<Cancion> conjunt = new ArrayList<>();
        for(String c : canciones){
            Cancion canc = ctrlConjuntoCanciones.buscarCancion(c);
            conjunt.add(canc);
        }
        return conjunt;
    }

    /**
     * Borra una lista del conjunto
     * @param noml nombre de la lista (identificador) que se desea eliminar existente en el conjunto
     */
    public void removeLista(String noml){
        conjuntoLista.removeLista(noml);
        guardarDatos();
    }

    /**
     * Añade una cancion a una lista existente.
     * @param noml nombre de la lista EXISTENTE donde se desea añadir la cancion
     * @param titulo de la canción (identificación)
     * @param autor de la canción (identificación)
     */
    public void addCancionALista(String noml, String titulo, String autor){
        Cancion c = ctrlConjuntoCanciones.buscarCancion(titulo + ";" + autor);
        if (c!=null) {
            conjuntoLista.addCancionALista(noml, c);
            guardarDatos();
        }
    }

    /**
     * Añade una cancion a una lista existente
     * @param noml nombre de la lista existente donde se desea añadir
     * @param IdCancion identificador de una canción
     */
    public void addCancionALista(String noml, String IdCancion){

        Cancion c = ctrlConjuntoCanciones.buscarCancion(IdCancion);
        if (c!=null) {
            conjuntoLista.addCancionALista(noml, c);
            guardarDatos();
        }
    }

    /**
     * Elimina una cancion de la lista
     * @param noml nombre de la lista existente donde se desea eliminar la canción
     * @param IdCancion identificador de una canción
     */
    public void removeCancionDeLista(String noml, String IdCancion){
        Cancion c = ctrlConjuntoCanciones.buscarCancion(IdCancion);
        if (c!=null) {
            conjuntoLista.removeCancionDeLista(noml, c);
            guardarDatos();
        }
    }

    /**
     * Modifica los atributos de una lista
     * @param noml lista existente que se desea modificar
     * @param nomNuevo nuevo nombre de la lista
     * @param descNueva nueva descripción de la lista
     */
    public Boolean setAtributosDeLista(String noml, String nomNuevo, String descNueva){
        Boolean b = conjuntoLista.setAtributosDeLista(noml, nomNuevo, descNueva);
        if(b) guardarDatos();
        return b;
    }


    /**
     * Devuelve los nombres de las listas que existen en el sistema
     * @return conjunto de nombres de las listas
     */
    public ArrayList<String> getNombresListas(){
        return conjuntoLista.getNombresListas();
    }

    /**
     * Devuelve los datos de una lista
     * @param nomL nombre de la lista
     * @return devuelve la lista identificada por nomL, sino devuelve una lista vacia.
     */
    public Lista getLista(String nomL){
        return conjuntoLista.getLista(nomL);
    }

    /**
     *
     * @param noml identificador de una lista existente
     * @return informacion de una lista
     */
    public ArrayList<String> getDatosLista(String noml){
        ArrayList<String> con = new ArrayList<>();
        Lista l = conjuntoLista.getLista(noml);
        con.add(l.getNombre());
        con.add(l.getDescripcion());
        return con;
    }

    /**
     *
     * @param noml identificador de una lista existente
     * @return información de una lista para exportar
     */
    public ArrayList<String> getDatosListaParaExportar(String noml){
        ArrayList<String> con = new ArrayList<>();
        Lista l = conjuntoLista.getLista(noml);
        con.add(l.getNombre());
        if (l.getDescripcion().isEmpty()) con.add("null");
        else con.add(l.getDescripcion());
        return con;
    }

    /**
     * Lectura de todas las listas del usuario user
     * @param user identificador usuario.
     */
    public void leerDatos(String user){
        ArrayList<String> listas = ctrlDatosLista.leerListas();
        for(String l : listas) {
            String t[] = l.split(";");
            String nomlista = t[0];
            String desc = t[1];
            if(desc.equals("null")) desc = new String();
            ArrayList<String> cancionesTodas = new ArrayList<>();

            int i=2;
            while (i<t.length){
                String cancion = t[i]+";"+t[i+1];
                //existe cancion identificada por String cancion;
                if (ctrlConjuntoCanciones.buscarCancion(cancion).getTitulo()!=null) {
                    cancionesTodas.add(cancion);
                }

                i=i+2;
            }
            addListaFicheroGlobal(nomlista, user, desc, cancionesTodas);
        }
    }

    /**
     * Convertir a información para los Datos
     * @param listas conjunto de listas del usuario.
     * @return conjuntos de informacion de cada lista del usuario.
     */
    private ArrayList<String> convertirLista(ArrayList<Lista> listas){
        ArrayList<String> datos = new ArrayList<>();
        for(Lista l : listas) {
            String line;
            String nomlista = l.getNombre();
            String desc = l.getDescripcion();
            if (desc.isEmpty()) desc = "null";
            line = nomlista + ";" + desc;

            int i=0;
            while (i<l.getConjuntoDeCanciones().size()){
                Cancion c = l.getConjuntoDeCanciones().get(i);
                String nomCa=c.getTitulo();
                String autorCa=c.getAutor();
                String can = ";"+nomCa+";"+autorCa;
                line = line+can;
                ++i;
            }
            datos.add(line);
        }
        return datos;
    }

    /**
     * Permite guardar los datos actual.
     */
    public void guardarDatos(){
        ArrayList<Lista> listas = conjuntoLista.getConjuntoDeListas();
        ArrayList<String> datos = convertirLista(listas);
        ctrlDatosLista.escrbirListas(datos);
    }

    /**
     *
     * @param noml identificador de una lista
     * @param t titulo de una canción
     * @param a autor de una canción
     * @return true si existe la cancion identificada por titulo y autor, false si no existe.
     */
    private Boolean existeCancionEnLista(String noml, String t, String a){
        return conjuntoLista.existeCancionEnLista(noml, t, a);
    }

    /**
     *
     * @param noml identificador de una lista
     * @return identificadores de las canciones de una lista.
     */
    public ArrayList<String> getNombresCancionesListas(String noml){
        return conjuntoLista.getCancionesNoms(noml);
    }

    /**
     *
     * @param noml identificador de una lista
     * @return identificadores de las canciones que no pertenecen a una lista.
     */
    public ArrayList<String> getNombresCancionesNoListas(String noml){
        ArrayList<String> canciones = new ArrayList<>();
        List<Cancion> tipoCanciones = ctrlConjuntoCanciones.getCanciones();
        for(Cancion c : tipoCanciones){
            if(!existeCancionEnLista(noml, c.getTitulo(), c.getAutor())) {
                String s = c.getTitulo()+";"+c.getAutor();
                canciones.add(s);
            }
        }
        return canciones;
    }

    /**
     * Permite guardar en un fichero externo la información de una lista.
     * @param listaActual identificador de una lista
     * @param ruta path de ubicación del archivo
     */
    public void exportarLista(String listaActual, String ruta){
        ArrayList<String> lista = getDatosListaParaExportar(listaActual);
        ArrayList<String> canciones = getCancionesNoms(listaActual);
        if(canciones.size()>0) {
            for (String s : canciones) {
                lista.add(s);
            }
        }
        ctrlDatosLista.guardarExportacion(lista, ruta);

    }

    /**
     *
     * @param listaActual identificador de una lista
     * @return nombre de las canciones de la lista "listaActual"
     */
    private ArrayList<String> getCancionesNoms(String listaActual){
        return conjuntoLista.getCancionesNoms(listaActual);
    }

    /**
     *
     * @param listaActual identificador de una lista
     * @param IdCancion identificador de una canción.
     * @param subir opción de movimiento
     * @return true si se ha modificado el indice de la cancion, false en cualquier otro caso.
     */
    public Boolean modificarIndiceCancion(String listaActual, String IdCancion, Boolean subir) {
        Boolean b = conjuntoLista.modificarIndiceCancion(listaActual, IdCancion, subir);
        if(b) guardarDatos();
        return b;
    }

    /**
     *
     * @param textoBusqueda identificador de una canción.
     * @return información si la canción existe, null si no existe la canción en ninguna lista.
     */
    public String buscarCancion(String textoBusqueda) {
        ArrayList<Integer> busqueda = conjuntoLista.buscarCancion(textoBusqueda);
        if(busqueda.get(0) != -1){
            String resultado = "La canción ha sido encontrada en: \n";
            //Ha encontrado algo por tanto lo recorremos de dos en dos
            for(int i = 0; i<busqueda.size();i+=2){
                resultado = resultado +"Lista: " + conjuntoLista.getConjuntoDeListas().get(busqueda.get(i)).getNombre() +", elemento: " + (busqueda.get(i+1)+1) +"\n";
            }
            return resultado;
        }
        return null;
    }

    /**
     *
     * @param lista nombre de la lista a buscar
     * @param titulo título de la canción
     * @param autor autor de la canción
     * @return true si la canción (titulo,autor) está en la lista (lista)
     */
    public Boolean existeCancionLista(String lista,String titulo,String autor){
        return conjuntoLista.existeCancionEnLista(lista,titulo,autor);
    }
}

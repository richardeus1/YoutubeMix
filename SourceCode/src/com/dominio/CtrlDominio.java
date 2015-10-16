package com.dominio;

import com.Algoritmos.CtrlAlgortimo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Natali on 23/04/15.
 * @author Natali Balon
 * @author Alejandro Quibus
 * @author Andreu Conesa
 */
public class CtrlDominio {
    private CtrlConjuntoCanciones ctrlConjuntoCanciones;
    private CtrlConjuntoUsuarios ctrlConjuntoUsuarios;
    private CtrlConjuntoLista ctrlConjuntoLista;
    private CtrlConjuntoAgrupaciones ctrlConjuntoAgrupaciones;
    private CtrlAlgortimo ctrlAlgortimo;
    private CtrlConjuntoReproducidas ctrlReproducidas;
    private String usuarioActual;

    /**
     * El dominio será el encargado de gestionar el conjunto de agrupaciones ya que la mayoría de sus funciones requieren de otros controladores
     */
    /**
     * Creadora por defecto de la clase CtrlDominio
     */
    public CtrlDominio(){
        ctrlConjuntoUsuarios = new CtrlConjuntoUsuarios();
    }

    public void usuarioIniciado(String usuarioActual) {
        this.usuarioActual = usuarioActual;

        ctrlConjuntoCanciones = new CtrlConjuntoCanciones();

        ctrlConjuntoLista = new CtrlConjuntoLista(ctrlConjuntoCanciones, usuarioActual);

        //Esto es para mi NATALI
        ctrlReproducidas = new CtrlConjuntoReproducidas(ctrlConjuntoCanciones, ctrlConjuntoUsuarios, usuarioActual);
        ctrlAlgortimo = new CtrlAlgortimo(ctrlReproducidas);
        ctrlConjuntoAgrupaciones = new CtrlConjuntoAgrupaciones(ctrlConjuntoCanciones,usuarioActual);
        ctrlConjuntoUsuarios.guardarDatos();

    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    /**
     *
     * @return Devuelve el controlador de canciones que tiene el dominio
     */
    public CtrlConjuntoCanciones getCtrlCanciones() {
        return ctrlConjuntoCanciones;
    }

    public CtrlConjuntoUsuarios getCtrlConjuntoUsuarios() {
        return ctrlConjuntoUsuarios;
    }

    public void setCtrlConjuntoUsuarios(CtrlConjuntoUsuarios ctrlConjuntoUsuarios) {
        this.ctrlConjuntoUsuarios = ctrlConjuntoUsuarios;
    }

    public CtrlConjuntoLista getCtrlConjuntoLista() {
        return ctrlConjuntoLista;
    }

    public CtrlConjuntoReproducidas getCtrlReproducidas() {
        return ctrlReproducidas;
    }

    //<---- FUNCIONES DE ALGORTIMOS Y CONJUNTO DE AGRUPACIONES --->
    //FUNCIONES DESARROLLADAS POR ALEJANDRO QUIBUS

    /**
     * Esta función nos permite filtrar un array de canciones en función de unos parámetros establecidos
     * @param cs La lista de canciones a filtrar
     * @param parms Los parametros que usaremos para filtrar
     * @return Devuelve un array de canciones que solo contiene las canciones que cumplan los parámetros establecidos
     */
    private ArrayList<Cancion> filtrarCanciones(ArrayList<Cancion> cs,ArrayList<String> parms){
        ArrayList<Cancion> filtradas = new ArrayList<>();
        boolean cumple = true;
        for(Cancion c : cs){
            //Miramos que siga cumpliendo la condición de poder ser añadida, y para ello primero ha de ser un parametro a evaluar (!= -1) y evaluamos
            if(cumple && !parms.get(0).equals("-1") && !parms.get(0).equals(c.getAutor())) cumple = false;
            if(cumple && !parms.get(1).equals("-1") && !parms.get(1).equals(c.getAlbum())) cumple = false;
            if(cumple && !parms.get(2).equals("-1") && !parms.get(2).equals(c.getGenero())) cumple = false;
            if(cumple && !parms.get(3).equals("-1") && Integer.valueOf(parms.get(3))>c.getNumRep()) cumple = false; //Buscamos que tenga míninimo el parametro 3, por tanto lo negamos  que tenga menos lo rechazará
            if(cumple && !parms.get(4).equals("-1") && !parms.get(4).equals(c.getFechaInsercion())) cumple = false;
            if(cumple) {
                //Miramos si se busca un número maximo de canciones, si es así (!=-1, miramos que no tengamso ya ese mínimo antes de añadir)
                if(Integer.valueOf(parms.get(5)) == -1 || (filtradas.size() <= (Integer.valueOf(parms.get(5)))-1)) filtradas.add(c);
            }
            else cumple = true;
        }
        return filtradas;
    }

    //Esta función corresponde al caso de uso <b>Autogenerar un conjunto de agrupaciones</b>

    /**
     * Esta funcion Autogenerará el conjunto de agrupaciones en  base a los criterios especificados en el vector datos
     * <p>
     *     -Explicación del ArrayList datos, cada elemento
     *     0: El origen de los datos para la Autogeneración {"All":Todas las canciones,"User":Todas las canciones del usuario,"NombreListaUsuario":Una lista del usuario con NombreListaUsuario}
     *     1: Método para autogenerar {"Louvain","Newmann","Clique"}
     *     2: El parámetro de como de estricto será el algoritmo de 0 a 100 sin decimales
     *     Ej: {All,Clique} Para todas las canciones del sistema (y si criterios != NULL se filtrarán) se autogenerarán en función del método de Clique
     *     -Explicación del ArrayList criterios para el filtrado
     *     0: autor {Si no es -1, se filtrán las canciones por el "Autor"  indicado}
     *     1: album {Si no es -1, se filtrán las canciones por el "Albmun" indicado}
     *     2: genero {Si no es -1, se filtrán las canciones por el "Genero" indicado}
     *     3: numReproduccionesMin {Si no es -1, se filtrán las canciones por el "numReproduccionesMin" indicado, solo se quedarán las canciones que tengan como mínimo numReproduccionesMIN }
     *     4: fechadeinsercion {Si no es -1, se filtrán las canciones por el "fechadeinsercion" indicado}
     *     5: nummaxCanciones {Si no es -1, únicamente se seleccionaran en el filtrado un número máximo de canciones = nummaxcanciones}
     *     Ej: {RIP,NULL,POP,12,NULL,NULL} : Filtraremos las canciones dejando las que sean del artista RIP, de genero POP y con más de 12 reproducciones
     *     Ej: {NULL} : No se hará ningún filtrado
     *     -Explicación del ArrayList criterios para la Autogeneración: Si >0 se evalua la condición, y con una importancia del 1 al 5 (5 le damos más importancia a ese valor)
     *     0:autor común (>0 si se evalua esa condición)
     *     1:albúm común (>0 si se evalua esa condición)
     *     2:género común (>0 si se evalua esa condición)
     *     3:Rerpoducciones comunes (o similar) (>0 si se evalua esa condición,).
     *     4:año de publicación de la canción común (o similar) (>0 si se evalua)
     * </p>
     * @param datos Lista que contiene todos los datos para la Autogeneración
     * @param criteriosFiltrado Criterios (si son necesarios) para filtrar las cancioens antes de autogenerar, en caso de que sea null el parametro no se filtrarán
     * @param criteriosAlgoritmo Criterios para generar el algoritmo a nivel de las canciones, que tienen que tener en común
     */
    public Boolean generarConjuntoAgrupaciones(ArrayList<String> datos, ArrayList<String>criteriosFiltrado, ArrayList<Integer>criteriosAlgoritmo){
        //Primero vamos a conseguir las canciones
        ArrayList<Cancion> cancionesFiltrado;
        switch (datos.get(0)){ //Elegimos la fuente de datos
            case "All":
                //Seleccionaremos todas las canciones del sistema
                cancionesFiltrado = new ArrayList<>(ctrlConjuntoCanciones.getCanciones()); //Seleccionamos todas las canciones del sistema
                break;
            case "Mis canciones":
                Lista aux = ctrlConjuntoLista.getLista("Mis canciones"); //Llamada provisional hasta que no se implemente las otras funciones del dominio
                cancionesFiltrado = aux.getConjuntoDeCanciones();
                break;
            default:
                //El nombre la lista de reproducción que queremos usar está en datos.get(0)
                try {
                    Lista aux2 = ctrlConjuntoLista.getLista(datos.get(0)); //La lista de reproducción del usuario que sea igual al NombreListaPasado, primer campo del array
                    cancionesFiltrado = aux2.getConjuntoDeCanciones();
                }
                catch (Exception e){
                    //SI LA LISTA NO EXISTE UNICAMENTE HASTA QUE NO HAGAMOS LA INTERFAZ QUE LA INTERFAZ YA MIRARÁ SI EXSITE
                    e.printStackTrace();
                    //System.out.println("ERROR LA LISTA POSIBLEMENTE NO EXISTA, abortando operación");
                    return false;
                }
                break;
        }
        //Procedemos a filtrar si es necesario
        if(!criteriosFiltrado.get(0).equals("-1") || !criteriosFiltrado.get(1).equals("-1") || !criteriosFiltrado.get(2).equals("-1")
                || !criteriosFiltrado.get(3).equals("-1") || !criteriosFiltrado.get(4).equals("-1") || !criteriosFiltrado.get(5).equals("-1")){ //Si hay algún campo para filtrar filtramos
            //Procedemos a filtrar
            cancionesFiltrado = filtrarCanciones(cancionesFiltrado,criteriosFiltrado);
        }
        //Procedemos a generar las comunidades
        HashSet<HashSet<String>> comunidades = new HashSet<>();
        if(cancionesFiltrado.size()==0) return false;
        switch (datos.get(1)){
            case "Clique":
                comunidades = ctrlAlgortimo.generarComunidadClique(cancionesFiltrado,criteriosAlgoritmo,Integer.valueOf(datos.get(2)));
                break;
            case "Louvain":
                comunidades = ctrlAlgortimo.generarComunidadLouvain(cancionesFiltrado,criteriosAlgoritmo,Integer.valueOf(datos.get(2)));
                break;
            case "Newmann":
                comunidades = ctrlAlgortimo.generarComunidadNewmann(cancionesFiltrado,criteriosAlgoritmo,Integer.valueOf(datos.get(2)));
                break;
            default:
                break;
        }

        //Procesamos las comunidades (ya que nos devuelve Strings)
        ConjuntoAgrupaciones conjuntoGenerado = procesarAgrupaciones(comunidades);
        //Una vez le añadimos las comunidades, falta rellenar el resto de datos de la clase que se insertan al crearse
        conjuntoGenerado.setMetodo(datos.get(1)); //El método indicado antes
        conjuntoGenerado.setOrigenDatos(datos.get(0));
        conjuntoGenerado.setDescripcion(" //Estricto:"+datos.get(2)+"%");
        conjuntoGenerado.setParametros(criteriosAlgoritmo); //Los parametros usados en el algoritmo
//        ctrlConjuntoAgrupaciones = new CtrlConjuntoAgrupaciones(conjuntoGenerado,ctrlCanciones);
        ctrlConjuntoAgrupaciones.setConjuntoAgrupacionesActual(conjuntoGenerado);
        return true;
    }

    /**
     * Nos permite procesar el resutlado de los algoritmos de Autogeneracion a Agrupaciones del conjutnto autogenerado actual
     * <p>Para ello pasaremos el resultado de la generación que son idscanciones a comunidades de Canciones</p>
     * @param comunidades Las comunidades compuestas por idsCanciones de los algoritmos de autogeneración
     */
    private ConjuntoAgrupaciones procesarAgrupaciones(HashSet<HashSet<String>> comunidades){
        ConjuntoAgrupaciones conjutoProcesado = new ConjuntoAgrupaciones();
        for(HashSet<String> cH : comunidades){
            //cH es una comunidad, así que para cada identificador de canción buscamos su canción correspondiente
            ArrayList<Cancion> cancionesAgrup = new ArrayList<>();
            for(String idC : cH){
                //idC es un idCancion (titulo;autor) de la comunidad cH que
                cancionesAgrup.add(getCtrlCanciones().buscarCancion(idC)); //Añadimos la canción
            }
            //Ya tenemos convertidos los ids en canciones
            Agrupacion aux = new Agrupacion(cancionesAgrup);
            conjutoProcesado.addAgrupacion(aux); //Añadimos esa agrupación con sus canciones al conjunto actual
        }
        return conjutoProcesado;
    }


    //CASO DE USO: EXPORTAR A UNA LISTA DE CANCIONES UNA AGRUPACIÓN DEL CONJUNTO

    /**
     * Permite exportar del conjunto de agrupaciones actual una agrupación como lista de reproducción
     * @param n_agrupacion El nº de la aagrupación a exportar a lista
     * @param nombreLista El nombre que tendrá la nueva lista creada, importante no debe existir anterior mente
     * @return false si ya existe una lista con nombreLista
     */
    public Boolean exportarAgrupacionaLista(Integer n_agrupacion,String nombreLista,String desc){
        //Los parametros de la nueva lista son privisionales
        ConjuntoAgrupaciones actual = ctrlConjuntoAgrupaciones.getConjuntoAgrupacionesActual();
        Agrupacion aux = actual.getAgrupacion(n_agrupacion);
        String descripcion = desc + "//Esta lista ha sido exportada de la agrupación: " +actual.getNombre()
                +" que se ha generado con: " +actual.getMetodo() +" con los datos de " +actual.getOrigenDatos();
        if(ctrlConjuntoLista.addLista(nombreLista, usuarioActual, descripcion, aux.getCanciones())) { //Creamos la lista
            ctrlConjuntoLista.guardarDatos();
            return true;
        }
        return false;
    }

    //CASO DE USO CAMBIAR EL ORDEN DE UNA CANCIÓN EN UNA AGRUPACION

    /**
     *
     * @return El controlador de Cojunto de agrupaciones actual
     */
    public CtrlConjuntoAgrupaciones getCtrlConjuntoAgrupaciones() {
        return ctrlConjuntoAgrupaciones;
    }


    /**
     *
     * @return El conjunto de agrupaciones actual
     */
    public ConjuntoAgrupaciones getConjuntoAgrupacionesActual(){
        return ctrlConjuntoAgrupaciones.getConjuntoAgrupacionesActual();
    }

    //EL RESTO DE CASOS DE USO (funciones) DE AUTOGENERAR/CONJUNTO AGRUPACIONES INVOLUCRAN A LA CAPA DE DATOS Y POR TANTO AUN NO HAN SIDO IMPLEMENTADOS

    /**
     * Si la cancion se ha anyadido, debe asociarse a la lista de Mis canciones del usuario
     * y si el numero de reproducciones es mayor de cero, anyadirse a las reproducidas.
     * Si por otro lado la cancion no se ha anyadido, solo debe asociarse a la lista Mis canciones.
     * @param titulo de la cancion a anyadir
     * @param autor de la cancion a anyadir
     * @param album de la cancion a anyadir
     * @param genero de la cancion a anyadir
     * @param numRep de la cancion a anyadir
     * @param fechaInsercion de la cancion a anyadir
     * @param creador de la cancion a anyadir
     * @param anyo de la cancion a anyadir
     * @return true si la cancion se ha anyadido, false si no
     */
    public Boolean anadirCancion(String titulo, String autor, String album, String genero, Integer numRep, String fechaInsercion, String creador, Integer anyo){
        Boolean b;
        b = ctrlConjuntoCanciones.anadirCancion(titulo, autor, album, genero, numRep, fechaInsercion, creador, anyo);
        if (b){
            if (!ctrlConjuntoLista.existeCancionLista("Mis canciones",titulo,autor)){
                ctrlConjuntoLista.addCancionALista("Mis canciones", titulo, autor);
            }
            if (numRep > 0) ctrlReproducidas.AddAReproducida(titulo + ";" + autor, numRep, true);
        }
        else{
            if (!ctrlConjuntoLista.existeCancionLista("Mis canciones",titulo,autor)){
                ctrlConjuntoLista.addCancionALista("Mis canciones", titulo, autor);
            }
        }
        return b;
    }

    /**
     * Para cada cancion, si se ha anyadido, se asocia a Mis canciones del usuarioActual y si
     * el numRep es mayor de cero, se anyade a reproducidas.
     * Por otro lado, si no se ha podido anyadir la cancion porque ya existia, solo se asocia a la
     * lista Mis canciones del usuarioActual.
     * @param path del archivo del que se quieren anyadir las canciones
     * @return true si todas las canciones del archivo path se han podido anyadir correctamente, false sino
     */
    public Boolean anadirCancionesDesdeArchivo(String path) {
        ArrayList<Cancion> l1 = ctrlConjuntoCanciones.anadirCancionesDesdeArchivo(path, usuarioActual);
        Boolean b = true;
        for (Cancion ca: l1){
            Boolean t = ctrlConjuntoCanciones.anadirCancion(ca.getTitulo(), ca.getAutor(), ca.getAlbum(), ca.getGenero(), ca.getNumRep(), ca.getFechaInsercion(), ca.getCreador(), ca.getAnyo());
            if (t){
                if (!ctrlConjuntoLista.existeCancionLista("Mis canciones",ca.getTitulo(),ca.getAutor())) {
                    ctrlConjuntoLista.addCancionALista("Mis canciones", ca.getTitulo(), ca.getAutor());
                }
                if (ca.getNumRep() > 0) ctrlReproducidas.AddAReproducida(ca.getTitulo()+";"+ca.getAutor(),ca.getNumRep(),true);
            }else{
                if (!ctrlConjuntoLista.existeCancionLista("Mis canciones",ca.getTitulo(),ca.getAutor())) {
                    ctrlConjuntoLista.addCancionALista("Mis canciones", ca.getTitulo(), ca.getAutor());
                }
                b = false;
            }
        }
        return b;
    }

    /**
     *
     * @param id de una cancion con el formato titulo;autor
     * @return true si la cancion se ha borrado correctamente, false sino
     */
    public Boolean borrarCancion(String id) {
        Boolean b;
        b = ctrlConjuntoCanciones.borrarCancion(id, usuarioActual);
        return b;
    }

    /**
     * Con la lista de elementos titulo autor en l2
     * para cada elemento llama a borrarCancion.
     * @param path del archivo que contiene ids de canciones a borrar
     * @return true si todas las canciones se han podido borrar, false sino
     */
    public Boolean borrarCancionesDesdeArchivo(String path) {

        Boolean b = true;
        ArrayList<ArrayList<String>> l2 = ctrlConjuntoCanciones.borrarCancionesDesdeArchivo(path);
        for (int i = 0; i < l2.size(); ++i){
            String id = l2.get(i).get(0) + ";" + l2.get(i).get(1);
            Boolean t = borrarCancion(id);
            if (!t) b = false;
        }
        return b;
    }

    /**
     *
     * @param t titulo de la cancion a modificar
     * @param a autor de la cancion a modificar
     * @param album nuevo de la cancion a modificar
     * @param genero nuevo de la cancion a modificar
     * @param anyo anuevo de la cancion a modificar
     * @return true si la cancion se ha modificado correctamente, false sino
     */
    public Boolean modificarCancion(String t, String a, String album, String genero, Integer anyo) {
        Boolean b;
        b = ctrlConjuntoCanciones.modificarCancion(t, a, album, genero, anyo, usuarioActual);
        return b;
    }

    /**
     * Para cada elemento de l2, llama a modificarCancion pasandole sus datos
     * @param path del archivo que contiene la informacion necesaria para modificar canciones
     * @return true si todas las canciones se han podido modificar, false sino
     */
    public Boolean modificarCancionesDesdeArchivo(String path) {
        ArrayList<ArrayList<String>> l2 = ctrlConjuntoCanciones.modificarCancionesDesdeArchivo(path);
        Boolean b = true;
        for (int i = 0; i < l2.size(); ++i){
            Boolean t = modificarCancion(l2.get(i).get(0),l2.get(i).get(1),l2.get(i).get(2),l2.get(i).get(3),Integer.parseInt(l2.get(i).get(4)));
            if (!t) b = false;
        }
        return b;
    }

    /**
     * @param id de la cancion a buscar en formato titulo;autor
     * @return un ArrayList<String> con los datos de la cancion si existe, o vacio sino
     */
    public ArrayList<String> buscarCancion(String id) {
        Cancion c1 = ctrlConjuntoCanciones.buscarCancion(id);
        ArrayList<String> aux = new ArrayList<String>();
        if (c1.getTitulo() != null) {
            aux.add(c1.getTitulo());
            aux.add(c1.getAutor());
            aux.add(c1.getAlbum());
            aux.add(c1.getGenero());
            aux.add(c1.getAnyo().toString());
            aux.add(c1.getNumRep().toString());
        }
        return aux;
    }

    /**
     * @return un ArrayList<String> con los datos de las canciones del sistema
     */
    public ArrayList<String> getCanciones() {
        List<Cancion> aux = ctrlConjuntoCanciones.getCanciones();
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < aux.size(); ++i){
            res.add(aux.get(i).getTitulo() + " - " + aux.get(i).getAutor() + " - " + aux.get(i).getAlbum() +" - " + aux.get(i).getGenero() + " - " + aux.get(i).getAnyo() + " - " + aux.get(i).getNumRep());
        }
        return res;
    }

    /**
     * Obtiene los datos de las canciones del usuario actual
     * @return un ArrayList<String> con las canciones del usuario actual
     */
    public ArrayList<String> getCancionesCreador(){
        ArrayList<String> res = new ArrayList<>();
        List<Cancion> aux = ctrlConjuntoCanciones.getCancionesCreador(usuarioActual);
        for (int i = 0; i < aux.size(); ++i){
            res.add(aux.get(i).getTitulo() + " - " + aux.get(i).getAutor() + " - " + aux.get(i).getAlbum() + " - " + aux.get(i).getGenero() + " - " + aux.get(i).getAnyo() + " - " + ctrlReproducidas.getNumRep(aux.get(i).getTitulo(),aux.get(i).getAutor()));
        }
        return res;
    }

    /**
     * @return un ArrayList<String> con todas las canciones del sistema si el usuario actual es el Admin
     * o con solo las canciones de las cuales es el creador sino.
     */
    public ArrayList<String> getCancionesAdmin() {
        ArrayList<String> res = new ArrayList<>();
        List<Cancion> aux;
        if (usuarioActual.equals("Admin")) aux = ctrlConjuntoCanciones.getCanciones();
        else aux = ctrlConjuntoCanciones.getCancionesCreador(usuarioActual);
        for (int i = 0; i < aux.size(); ++i){
            res.add(aux.get(i).getTitulo() + " - " + aux.get(i).getAutor() + " - " + aux.get(i).getAlbum() +" - " + aux.get(i).getGenero() + " - " + aux.get(i).getAnyo());
        }
        return res;
    }

    /**
     *
     * @return true si se han eliminado todas las relaciones del usuario actual,false si no
     */
    public Boolean remoteTodosLasRelaciones() {
        if (!usuarioActual.equals("Admin")) {
            ArrayList<UsuarioNumero> eliminadasRep = ctrlReproducidas.eliminarReproducidas();

            //CREO QUE ESTO NO ES NECESARIO YA QUE EL NUMEROREPRO DE UNA CANCION SE DEBE MANTENER AUNQUE EL USUARIO SE HAYA ELIMINADO
            if (!eliminadasRep.isEmpty()) {
                for(UsuarioNumero c : eliminadasRep) {
                    ctrlConjuntoCanciones.modificarNumeroReproducciones(c.getUser(), c.getNumRepro(), false);
                }
            }
            ctrlConjuntoCanciones.modificarCreadorCancion(usuarioActual);
            ctrlReproducidas.guardarDatos();

            //borramos aqui la carpeta
            return ctrlConjuntoUsuarios.eliminarUser();
        }
        else return false;

    }

    /**
     * Permite guardar todos los datos relacionados con el usuarios y las canciones
     */
    public void guardarTodosLosDatos() {
        ctrlConjuntoCanciones.cargarCancionesHaciaArchivo();
        ctrlConjuntoLista.guardarDatos();
        ctrlConjuntoUsuarios.guardarDatos();
        ctrlReproducidas.guardarDatos();
    }

    /**
     * Permite guardar todos los datos relacionados solo con el usuario logueado
     */
    public void guardarDatosGenerales() {
        ctrlConjuntoCanciones.cargarCancionesHaciaArchivo();
        ctrlConjuntoUsuarios.guardarDatos();
        ctrlReproducidas.guardarDatos();
    }

}

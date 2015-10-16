package com.dominio;

import com.CtrlDatos.CtrlDatosConjuntoAgrupaciones;

import java.util.ArrayList;

/**
 * Created by Alejandro Quibus on 17/05/2015.
 * @author Alejandro Quibus
 */
public class CtrlConjuntoAgrupaciones {
    private ConjuntoAgrupaciones conjuntoAgrupacionesActual; //Como únicamente se puede tener cargada un conjunto autogenerado
    private CtrlConjuntoCanciones ctrlCanciones;
    private CtrlDatosConjuntoAgrupaciones gestorDisco;
    /**
     * Aquí se cargar al iniciar el controlador todos los "nombres" de los conjuntos de agrupacioens guardados en memoria del usuario
     */
    private ArrayList<String> conjuntosUsuarioActual;

    /**
     * Creadora por defecto, genera un Ctrl vacio
     */
    public CtrlConjuntoAgrupaciones(CtrlConjuntoCanciones ctrlCanciones,String usuario){
        conjuntoAgrupacionesActual = new ConjuntoAgrupaciones();
        this.ctrlCanciones = ctrlCanciones;
        gestorDisco = new CtrlDatosConjuntoAgrupaciones(usuario);
        conjuntosUsuarioActual = gestorDisco.cargarNombresConjuntos();
    }

    /**
     * Devuelve el nombre de todos los conjutnos autogenerados guardados en disco
     * @return ArrayList con todos los nobmres si null (size == 0) no hay ninguno guardado para ese usuario
     */
    public ArrayList<String> getConjuntosUsuarioActual() {
        return conjuntosUsuarioActual;
    }

    /**
     *
     * @return El conjunto de agrupaciones actual
     */
    public ConjuntoAgrupaciones getConjuntoAgrupacionesActual() {
        return conjuntoAgrupacionesActual;
    }

    /**
     * Set del conjunto de agrupaciones actual
     * @param conjuntoAgrupacionesActual Un conjunto ya autogenerado e inicializado de conjunto de agrupaciones
     */
    public void setConjuntoAgrupacionesActual(ConjuntoAgrupaciones conjuntoAgrupacionesActual) {
        this.conjuntoAgrupacionesActual = conjuntoAgrupacionesActual;
    }

    //CASO DE USO CAMBIAR EL ORDEN DE UNA CANCIÓN EN UNA AGRUPACION
    /**
     * Cambia la posición de la canción en posOri por la del posFinal dentro de la agrupación nAgrupación
     * @param nAgrupacion El número de agrupación
     * @param posOri La posición original de la canción a intercambiar
     * @param posFinal La posición final de la canción a intercambiar
     */
    public void cambiarOrdenCancionenAgrupacion(Integer nAgrupacion, Integer posOri, Integer posFinal){
        Agrupacion aux = conjuntoAgrupacionesActual.getAgrupacion(nAgrupacion);
        aux.intercambiarPos(posOri, posFinal);
    }

    //CASO DE USO BORRAR UNA CANCIÓN EN UNA AGRUPACIÓN DE UN CONJUNTO
    /**
     * Elimina la canción en la posición pos de la agrupación en nAgrupacion del conjunto
     * @param nAgrupacion Posicion de la agrupación en el conjunto
     * @param idCancion El identificador la canción a eliminar
     */
    public void borrarCancionenAgrupacion(Integer nAgrupacion,String idCancion){
        Cancion cancion = ctrlCanciones.buscarCancion(idCancion);
        conjuntoAgrupacionesActual.borrarCancion(nAgrupacion,cancion);
        conjuntoAgrupacionesActual.setModificado(true);
    }

    //CASO DE USO FUSIONAR AGRUPACIONES DE UN CONJUNTO AUTOGENERADO

    /**
     * Nos permite funsionar dos Agrupaciones que han de existir del conjunto en un único conjutno a1 contendrá todos los elementos (union) de a2
     * @param p1 posición en el conjunto de la agrupación a fusionar 1, contendrá sus elementso y los de a2
     * @param p2 posición en el conjunto de la agrupación a fusionar 2, será destruido
     */
    public void fusionarAgrupacionesConjunto(Integer p1, Integer p2){
        conjuntoAgrupacionesActual.fusionarAgrupaciones(p1, p2);
        conjuntoAgrupacionesActual.setModificado(true);
    }

    //CASO DE USO ELIMINAR UNA AGRUPACIÓN DE UN CONJUNTO AUTOGENERADO

    /**
     * Elimina la agrupación en la posición p del conjunto (inclusive el objeto agrupación)
     * @param p La agrupación en la posición p del conjunto es eliminada
     */
    public void eliminarAgrupacionConjunto(Integer p){
        conjuntoAgrupacionesActual.removeAgrupacion(p);
    }


    /**
     * Nos permite buscar una canción en el conjunto
     * <p>
     *     Al buscar una canción puede ser que la canción aparezca más de una vez, entonces aparecerán n pares de combinaciones de apariciones, si no hay ninguna aparición devuelve un el elemento 0 = -1
     * </p>
     * @param idCancion EL id de la canción a buscar
     * @return Cada dos elementos consecuitivos del array contienen una aparición de la Canción de ese par el primer elemento es el nº de conjunto y el segundo la posición de la canción en el conjunto. Si no lo encuentra devuelve una lista con el elemento -1
     */
    public ArrayList<Integer> buscarCancionConjunto(String idCancion){
        Cancion cancion = ctrlCanciones.buscarCancion(idCancion);
        return conjuntoAgrupacionesActual.buscarCancion(cancion);
    }

    /**
     * Guarda el conjunto actual en memoria
     */
    public void guardarConjunto(){
        //Preparamos los datos
        //1) Actualizamos la lista con todos los conjutnos guardados y le añadimos el actual
        conjuntosUsuarioActual.add(conjuntoAgrupacionesActual.getNombre());
        //2) Los datos del conjunto
        ArrayList<String> datosConjunto = conjuntoAgrupacionesActual.getDatosConjunto();
        //3) Las agrupaciones del conjunto
        ArrayList<ArrayList<String> > agrupacionesConjunto = conjuntoAgrupacionesActual.getDatosAgrupaciones();

        //Guardamos
        gestorDisco.guardarConjunto(datosConjunto,agrupacionesConjunto,conjuntosUsuarioActual);
        //Después de guardar vaciar memoria
    }

    /**
     * Carga el conjunto identificado por nombreConjunto de memoria y lo establece como el conjutno de agrupaciones abierto actualmente en el sistema
     * @param nombreConjunto
     */
    public void cargarConjunto(String nombreConjunto){
        ArrayList<String> datos =  gestorDisco.cargarDatosConjunto(nombreConjunto);
        //Vamos a procesar los datos, necesitas transformar el parámetro 2 a un vector de Integers
        ArrayList<Integer> param = new ArrayList<>();
        for(String s : datos.get(2).split(";")){
            param.add(Integer.valueOf(s));
        }
        //Transformamos el parámetro 3 que es un booleano
        Boolean b = true;
        if(datos.get(3).equals("false")) b = false;
        //Ahora obtenemos todas las agrupaciones, pero solo obtenemos su ID
        ArrayList<ArrayList<String>> idsAgrupaciones = gestorDisco.cargarAgrupaciones(nombreConjunto);
        //Transformamos esos ids en canciones
        ArrayList<Agrupacion> agrupaciones = new ArrayList<>();
        for(ArrayList<String> ag : idsAgrupaciones){
            ArrayList<Cancion> temp = obtenerCanciones(ag);
            if(!temp.isEmpty()){
                Agrupacion agTemp = new Agrupacion(temp);
                agrupaciones.add(agTemp);
            }
        }
        conjuntoAgrupacionesActual = new ConjuntoAgrupaciones(agrupaciones,datos.get(0),datos.get(1),param,b,datos.get(5),datos.get(4));
    }

    /**
     * Devuelve un ArrayList de Canciones dado un ArrayList de ids
     * @param idsCanciones El id de las canciones
     * @return Las Canciones
     */
    private ArrayList<Cancion> obtenerCanciones(ArrayList<String> idsCanciones){
        ArrayList<Cancion> canciones = new ArrayList<>();
        for(String id : idsCanciones){
            Cancion aux = ctrlCanciones.buscarCancion(id);
            //Miramos si la canción existe
            if(aux != null && aux.getAutor() != null){
                //Esa canción es válida por tanto añadimos
                canciones.add(aux);
            }
        }
        return canciones;
    }

    /**
     * Borra el conjunto almacenado en memoria
     * @param nombreConjunto
     * @return true si se ha borrado, false si no
     */
    public Boolean borrarConjunto(String nombreConjunto){
        if(gestorDisco.borrarConjunto(nombreConjunto)){
            //Como se ha borrado lo borramos de la lista de conjuntos del usuario
            conjuntosUsuarioActual.remove(nombreConjunto);
            //Actualizamos el indice
            return gestorDisco.actualizarIndiceConjuntos(conjuntosUsuarioActual);
        }
        return false;
    }

    /**
     *
     * @return Devuelve todos los datos del conjunto Actual
     */
    public ArrayList getDatosConjunto(){
        if(conjuntoAgrupacionesActual == null) return null;
        return conjuntoAgrupacionesActual.getDatosConjunto();
    }

    /**
     *
     * @return Devuelve todas las agrupaciones del conjunto Actual únicamente sus ids(titulo;autor)
     */
    public ArrayList<ArrayList<String>> getAgrupacionesConjunto(){
        if(conjuntoAgrupacionesActual == null) return null;
        ArrayList<ArrayList<String>> conjuntoRAW = conjuntoAgrupacionesActual.getDatosAgrupaciones();
        ArrayList<ArrayList<String>> procesado = new ArrayList<>();
        for(ArrayList<String> agrupacion : conjuntoRAW){
            ArrayList<String> ag = new ArrayList<>();
            for(String idCancion : agrupacion){
                //Para cada ID que contiene, vamos a procesarlo en el out que queremos mostrar
                Cancion c = ctrlCanciones.buscarCancion(idCancion);
                if(c != null && !c.getAutor().equals("NULL")){
                    String formateado = c.getTitulo() +", " +c.getAutor() +", " +c.getAlbum() +", " +c.getGenero() +", " +c.getAnyo();
                    ag.add(formateado);
                }
            }
            procesado.add(ag);
        }
        return procesado;
    }

    /**
     * Permite actualizar el título y la descripción del conjunto autogenerado actual
     * @param titulo Nuevo título que se le va a poner al Conjunto
     * @param desc Nueva descripción que se le va a poner al Conjunto
     */
    public void actualizarDatosConjuntoActual(String titulo, String desc) {
        conjuntoAgrupacionesActual.setNombre(titulo);
        conjuntoAgrupacionesActual.setDescripcion(desc);
    }

    /**
     *
     * @param titulo De la agrupación a ver si existe
     * @return true si existe la agrupación
     */
    public Boolean existeAgrupacion(String titulo) {
        return conjuntosUsuarioActual.contains(titulo);
    }

    /**
     *
     * @return True si la agrupación actual es cargada de memoria, False si no es de memoria.
     */
    public Boolean esCargada() {
        return existeAgrupacion(conjuntoAgrupacionesActual.getNombre());
    }

    /**
     * Permite exportar el Conjunto Autogenerado actual a un archivo de texto
     * @param ruta La ruta con el nombre donde se guarará el archivo
     * @param datosV El nombre y descripción actuales para guardar
     */
    public void exportarArhivoConjuntoActual(String ruta, ArrayList<String> datosV) {
        //Vamos a generar el contenido del fichero
        String datos;
        datos = "Nombre conjunto: " +datosV.get(0) +"\n" +"Método y Origen: " +conjuntoAgrupacionesActual.getMetodo() +" ," +conjuntoAgrupacionesActual.getOrigenDatos() +"\n" +"Parametros: " +conjuntoAgrupacionesActual.getParametros() +"\n"
                +"Descripción: " +datosV.get(1)+"\n" +"Modificado: " +conjuntoAgrupacionesActual.isModificado() +"\n"+"\n" +"Agrupaciones: " +"\n"+"\n";
        //Ahora cargamos las agrupaciones
        int i = 0;
        for(Agrupacion a : conjuntoAgrupacionesActual.getAgrupaciones()){
            datos = datos + "Agrupación " +i +":" +"\n";
            for(Cancion c : a.getCanciones()){
                datos = datos +c.getTitulo() +", " +c.getAutor() +", " +c.getAlbum() +", " +c.getGenero() +", " +c.getAnyo() +"\n";
            }
            datos = datos +"\n\n";
            i++;
        }
        gestorDisco.guardarArchivo(datos, ruta);

    }

    /**
     * Permite exportar una agrupación del conjunto actual (numAgrupacion) a un archivo
     * @param numAgrupacion El número de la agrupación a exportar
     * @param ruta La ruta con el nombre del archivo a exportar
     */
    public void exportarArhivoAgrupacion(String numAgrupacion, String ruta) {
        String datos = "";
        for(Cancion c : conjuntoAgrupacionesActual.getAgrupacion(Integer.valueOf(numAgrupacion)).getCanciones()){
            datos = datos +c.getTitulo() +", " +c.getAutor() +", " +c.getAlbum() +", " +c.getGenero() +", " +c.getAnyo() +"\n";
        }
        gestorDisco.guardarArchivo(datos,ruta);
    }

    /**
     * Borra la agrupación elemento del conjunto Actual
     * @param numA Número de la agrupación a borrar
     * @return true si se ha borrado correctamente, false si no
     */
    public Boolean borrarAgrupacionActual(Integer numA) {
        if(conjuntoAgrupacionesActual.removeAgrupacion(numA)){
            conjuntoAgrupacionesActual.setModificado(true);
            return true;
        }
        return false;
    }

    /**
     * Intercambia la posición de la cancion (id1) con el de la canción (id2) de la agrupación (numA) del Conjunto Actual
     * @param posAgrupacion El número de agrupación
     * @param id1 El id de la canción
     * @param id2 El id de la canción
     */
    public void intercambiarOrdenCanciones(Integer posAgrupacion, String id1, String id2) {
        //Vamos a procesar los parametros
        Cancion c1 = ctrlCanciones.buscarCancion(id1);
        Cancion c2 = ctrlCanciones.buscarCancion(id2);
        //Primer obtengamos la posición de las canciones c1, c2 en la agrupación numA
        Agrupacion ag = conjuntoAgrupacionesActual.getAgrupacion(posAgrupacion);
        int p1 = ag.getPosicion(c1);
        int p2 = ag.getPosicion(c2);
        ag.intercambiarPos(p1,p2);
        conjuntoAgrupacionesActual.setModificado(true);
    }

    /**
     * Cambia la canción (idC) de la agrupación nA a la agrupación nB del conjunto actual
     * @param nA Numero de la agrupación origen de la canción
     * @param nB Número de la agrupación destino de la canción
     * @param idC Identificador de la canción a mover
     * @return true si se ha realizado el cambio, false si no
     */
    public Boolean cambiarAgrupacionCancion(int nA,int nB,String idC){
        if(nB > conjuntoAgrupacionesActual.getNumAgrupaciones() || nB<0) return false;
        Cancion c = ctrlCanciones.buscarCancion(idC);
        conjuntoAgrupacionesActual.cambiarCancionAgrupacion(nA, nB, c);
        conjuntoAgrupacionesActual.setModificado(true);
        return true;
    }

    /**
     * Guarda el conjunto actual en mememoria con los datos nombre y descripción indicados
     * @param nombre Nombre del conjunto
     * @param desc Descripción del conjunto
     * @return true si se ha realizado el guardado, false si no porque ya existe un conjunto guardado en memoria con ese nombre que no es el cargado
     */
    public Boolean guardarConjunto(String nombre,String desc){
        if(esCargada())  borrarConjunto(conjuntoAgrupacionesActual.getNombre());
        else{
            //Si no es cargada la canción miramos si ya existe una con ese nombre
            if(existeAgrupacion(nombre)) return false; //Existe una por tanto no podemos proceder
        }
        actualizarDatosConjuntoActual(nombre,desc);
        guardarConjunto();
        return true;
    }
}

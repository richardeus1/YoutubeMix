package com.Vista;

import com.dominio.CtrlDominio;

/**
 * Created by Alex on 21/5/15.
 * @author Alejandro Quibus
 * @author Natali Balon
 * @author Andreu Conesa
 *
 */
public class CtrlVistas {
    private String usuario;
    private inicioPrograma vistaInicio; //El widget de vista inicio
    private CtrlDominio dominio;
    private CtrlPresentacionConjuntoAgrupaciones ctrlVistaAgr;
    private CtrlPresentacionLista ctrlPresentacionLista;
    private CtrlPresentacionGestorCanciones ctrlPresentacionGestorCanciones;
    private CtrlPresentacionUsuarios ctrlPresentacionUsuarios;


    /**
     * Creadora por defecto
     */
    public CtrlVistas() {
        //Creamos nuesto dominio
        dominio = new CtrlDominio();
        vistaInicio = new inicioPrograma(this);
    }

    /**
     *
     * @return Devuvelve la vista inicial de la aplicación
     */
    public inicioPrograma getVistaInicio(){
        return vistaInicio;
    }

    /**
     *
     * @return Devuelve el controlador de dominio actual
     */
    public CtrlDominio getDominio(){
        return dominio;
    }

    /**
     * Carga la vista de inicio
     */
    public void cargarVistaInicio(){
        vistaInicio = new inicioPrograma(this);
        vistaInicio.ocultar();
        ctrlVistaAgr.ejecutar();

    }

    /**
     * Carga y muestra las vistas corresponiente al Conjunto de agrupaciones
     */
    public void cargarVistaAgrupaciones(){
        ctrlVistaAgr = new CtrlPresentacionConjuntoAgrupaciones(this);
        vistaInicio.ocultar();
        ctrlVistaAgr.ejecutar();

    }

    /**
     * Muestra el menú básico de la aplicación
     */
    public void mostarMenu(){
        vistaInicio.mostrar();
    }

    /**
     * Carga la vista de listas
     */
    public void cargarVistaListas(){
        ctrlPresentacionLista = new CtrlPresentacionLista(this);
        vistaInicio.ocultar();
        ctrlPresentacionLista.ejecutar();
    }

    /**
     * Carga la vista de listas de reproducción
     */
    public void cargarVistaListasRepro(){
        ctrlPresentacionLista = new CtrlPresentacionLista(this);
        vistaInicio.ocultar();
        ctrlPresentacionLista.ejecutarRepro();
    }

    /**
     * Carga vista de usuarios
     */
    public void cargarVistaUsuarios(){
        ctrlPresentacionUsuarios = new CtrlPresentacionUsuarios(this);
        vistaInicio.ocultar();
        ctrlPresentacionUsuarios.ejecutar();
    }

    /**
     * Carga vista de canciones
     */
    public void cargarVistaCanciones(){
        ctrlPresentacionGestorCanciones = new CtrlPresentacionGestorCanciones(this);
        vistaInicio.ocultar();
        ctrlPresentacionGestorCanciones.ejecutar();
    }

    /**
     * Inicia los datos para el usuario que ha iniciado sesión
     * @param userIn Alias del usuario que ha iniciado sesión
     */
    public void usuarioCorrecto(String userIn){
        usuario = userIn;
        dominio.usuarioIniciado(usuario);
    }

    /**
     * Inicio de la aplicación se encarga de inicializar y cargar los recursos necesarios y mostrar
     * la vista inicial-
     * @param args
     */
    public static void main(String[] args){
        //El main del programa
        CtrlVistas inicio = new CtrlVistas();
        inicioPrograma iniP = inicio.getVistaInicio();
        iniP.main();
    }

    /**
     * Verifica que la combinación usuario y contraseña es correcta
     * @param user Alias del usuario
     * @param pass Contraseña
     * @return true si son validos los datos, false si no
     */
    public Boolean iniciarSesion(String user, String pass) {
        return dominio.getCtrlConjuntoUsuarios().iniciarSesion(user, pass);
    }

    /**
     * Muestra el menú principal
     */
    public void getMenuPrincipal() {
        vistaInicio.mostrar();
    }


    /**
     * Añade un usuario
     * @param alias alias del usuario
     * @param pass contraseña
     * @param nom nombre
     * @param apel apellidos
     * @param fecha fecha de nacimiento
     * @param genero género
     * @param correo dirección de correo
     * @return true si se ha realizado correctamente, false si no
     */
    public Boolean addUsuario(String alias, String pass, String nom, String apel, String fecha, String genero, String correo) {
        Boolean b = dominio.getCtrlConjuntoUsuarios().addUsuario(alias, pass, nom, apel, fecha, genero, correo);
        if(b) {
            dominio.getCtrlConjuntoUsuarios().crearFicheros(alias);
            dominio.getCtrlConjuntoUsuarios().guardarDatos();
        }
        return b;
    }
}

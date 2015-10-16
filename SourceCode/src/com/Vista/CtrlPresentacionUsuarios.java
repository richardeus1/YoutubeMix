package com.Vista;

import com.dominio.CtrlDominio;
import java.util.ArrayList;

/**
 * @author Natali Balon
 * @author Andreu Conesa
 */
public class CtrlPresentacionUsuarios {
    private static CtrlDominio ctrlDominio;
    private CtrlVistas ctrlVista;
    private PresentacionUsuarios vista;

    /**
     * Creadora por defecto
     */
    public CtrlPresentacionUsuarios(){
        this.ctrlDominio = new CtrlDominio();
    }

    /**
     * Creadora
     * @param crVista El controlador de Vistas
     */
    public CtrlPresentacionUsuarios(CtrlVistas crVista) {
        ctrlVista = crVista;
        ctrlDominio = crVista.getDominio();
    }

    /**
     * Función para ejecutar la vista de Usuario
     */
    public void ejecutar() {
        //Como ahora el ctrl del conjunto gestiona la vista que se muestra y del view de la presentación es el controlador el que lo llama
        //Por tanto también el que se encarga de procesar cuando se pulsa un botón.
        vista = new PresentacionUsuarios(this);
        vista.main();
    }

    /**
     * Función que manda a la vista principal del sistema
     */
    public void getMenuPrincipal(){
        ctrlVista.getMenuPrincipal();
    }

    /**
     *
     * @return devuelve toda la información del usuario que ha iniciado sesión.
     */
    public ArrayList<String> getInformacion() {
        return ctrlDominio.getCtrlConjuntoUsuarios().getInformacion();
    }

    /**
     * Permite modificar la información del usuario.
     * @param nom nombre nuevo del usuario
     * @param apellido apellido nuevo del usuario.
     * @param fecha fecha de nacimiento nueva del usuario.
     * @param genero genero nuevo del usuario
     * @param correo correo nuevo del usuario.
     */
    public void setInformacion(String nom, String apellido, String fecha, String genero, String correo) {
        ctrlDominio.getCtrlConjuntoUsuarios().setInformacion(nom, apellido, fecha, genero, correo);
    }

    /**
     * Permite guardar las modificaciones que afecta solo a los usuarios.
     */
    public static void guardaModificacionesUsuarios() {
        ctrlDominio.getCtrlConjuntoUsuarios().guardarDatos();
    }

    /**
     *
     * @return devuelve la contraseña actual del usuario.
     */
    public String getContrasenya() {
        return ctrlDominio.getCtrlConjuntoUsuarios().getContrasenya();
    }

    /**
     * Permite modificar la contraseña actual del usuario.
     * @param pass contraseña nueva del usuario.
     */
    public void setContrasenya(String pass) {
        ctrlDominio.getCtrlConjuntoUsuarios().setContrasenya(pass);
    }

    /**
     * Permite eliminar todas las relaciones que tiene el usuario actual con las clases.
     * @return true si se ha eliminado absolutamente to_do, false en caso contrario.
     */
    public Boolean removeTodosLasRelaciones() {
        return ctrlDominio.remoteTodosLasRelaciones();
    }

    /**
     * Función que manda a la vista inicial del sistema
     */
    public void getMenuInicioSesion() {
        ctrlVista.getVistaInicio().MenuIniciarSesion();
    }

    /**
     * Permite guardar todos los datos del usuario y todas sus relaciones
     */
    public void guardarTodosLosDatos() {
        ctrlDominio.guardarTodosLosDatos();
    }

    /**
     * Permite guardar todos los datos del usuario y sus basicas relaciones
     */
    public void guardarDatosGenerales() {
        ctrlDominio.guardarDatosGenerales();
    }
}

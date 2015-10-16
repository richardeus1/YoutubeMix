package com.dominio;

import com.CtrlDatos.CtrlDatosUsuarios;

import java.util.ArrayList;

/**
 * La clase CtrlConjuntoUsuarios
 * @author Ricardo
 */
public class CtrlConjuntoUsuarios {
    private ConjuntoUsuario conjuntoUsuario;
    private CtrlDatosUsuarios ctrlDatosUsuarios;
    private String user;

    public CtrlConjuntoUsuarios(){
        this.conjuntoUsuario = new ConjuntoUsuario();
        this.ctrlDatosUsuarios = new CtrlDatosUsuarios();
        leerTodosDatos();
        if(!existeUsuario("Admin")){
            addUsuario("Admin", "Admin", "", "", "", "", "");
            crearFicheros("Admin");
            guardarDatos();
        }
    }

    /**
     *
     * @return conjunto de usuarios existentes en sistema.
     */
    public ConjuntoUsuario getConjuntoUsuario() {
        return conjuntoUsuario;
    }

    /**
     *
     * @return contraseña actual del usuario que ha iniciado sesión
     */
    public String getContrasenya() {
        return conjuntoUsuario.getContrasenya(user);
    }

    /**
     * Modifica
     * @param conjuntoUsuario nuevo conjunto de usuarios.
     */
    public void setConjuntoUsuario(ConjuntoUsuario conjuntoUsuario) {
        this.conjuntoUsuario = conjuntoUsuario;
    }

    /**
     * Modifica
     * @param aliasUsuario alias de un usuario (identificador)
     * @param contrasenya contraseña del usuario.
     * @param nombreCompleto nombre del usuario.
     * @param apellido apellido del usuario.
     * @param fechaNacimiento fecha de nacimiento del usuario.
     * @param genero genero del usuario.
     * @param correoElectronico correo electronico del usuario.
     * @return true si se ha añadido el usuario al conjunto, false en cualquier otro caso
     */
    public Boolean addUsuario(String aliasUsuario, String contrasenya, String nombreCompleto,String apellido, String fechaNacimiento, String genero, String correoElectronico) {
        return conjuntoUsuario.addUsuario(aliasUsuario, contrasenya, nombreCompleto, apellido, fechaNacimiento, genero, correoElectronico);
    }

    /**
     * Añade al conjunto de usuarios, todos los usuarios del fichero que se considera la base de datos.
     */
    public void leerTodosDatos() {
        ArrayList<String> listas = ctrlDatosUsuarios.leerUsuarios();
        for(String l : listas) {
            String t[] = l.split(";");
            String alias = t[0];
            String contra = t[1];
            String nom = t[2];
            if (nom.equals("null")) nom = new String();
            String ape = t[3];
            if (ape.equals("null")) ape = new String();
            String fecha = t[4];
            if (fecha.equals("null")) fecha = new String();
            String gene = t[5];
            if (gene.equals("null")) gene = new String();
            String correo = t[6];
            if (correo.equals("null")) correo = new String();
            conjuntoUsuario.addUsuario(alias, contra, nom, ape, fecha, gene, correo);
        }
    }

    /**
     * Devuelve un conjunto de String para poder escribirse.
     * @param usuarios conjunto de usuarios existentes al sistema.
     * @return conjunto de usuarios (Strings)
     */
    private ArrayList<String> convertirLista(ArrayList<Usuario> usuarios){
        ArrayList<String> datos = new ArrayList<>();
        for(Usuario u : usuarios) {
            String line;
            String a = u.getAliasUsuario();
            String c = u.getContrasenya();
            String n = u.getNombreCompleto();
            if(n.isEmpty()) n = "null";
            String ap = u.getApellido();
            if(ap.isEmpty()) ap = "null";
            String fe = u.getFechaNacimiento();
            if(fe.isEmpty()) fe = "null";
            String g = u.getGenero();
            if(g.isEmpty()) g = "null";
            String correo = u.getCorreoElectronico();
            if(correo.isEmpty()) correo = "null";
            line = a + ";" + c + ";" + n + ";" + ap + ";" + fe + ";" + g + ";" + correo;
            datos.add(line);
        }
        return datos;
    }

    /**
     *
     * @param usuario
     * @param contrasenya
     * @return true si existe el usuario y su contraseña es igual, false en cualquier otro caso.
     */
    public Boolean iniciarSesion(String usuario, String contrasenya) {
        Boolean b = conjuntoUsuario.iniciarSesion(usuario, contrasenya);
        if (b) user = usuario;
        return b;
    }

    /**
     * Convierte los datos, en datos para almacenar.
     */
    public void guardarDatos() {
        ArrayList<Usuario> usuarios = conjuntoUsuario.getUsuarios();
        ArrayList<String> datos = convertirLista(usuarios);
        ctrlDatosUsuarios.escrbirUsuarios(datos);
    }


    /**
     *
     * @return toda la información de un usuario.
     */
    public ArrayList<String> getInformacion() {
        return conjuntoUsuario.getInformacion(user);
    }

    /**
     * Modifica
     * @param nom nuevo nombre del usuario
     * @param apellido nuevo apellido del usuario
     * @param fecha nuevo fecha del usuario
     * @param genero nuevo genero del usuario
     * @param correo nuevo correo del usuario
     */
    public void setInformacion(String nom, String apellido, String fecha, String genero, String correo) {
        conjuntoUsuario.setAtributos(user, nom, apellido, fecha, genero, correo);
    }

    /**
     * Crea directorios y ficheros necesariós cuando se registre.
     * @param alias del usuario.
     */
    public static void crearFicheros(String alias) {
        String l = "Mis canciones;Añadidas al sistema";
        CtrlDatosUsuarios.crearFicheros(alias, l);

    }

    /**
     * Modifica
     * @param pass nueva contraseña
     */
    public void setContrasenya(String pass) {
        conjuntoUsuario.setContrasenya(user, pass);
        guardarDatos();
    }

    /**
     *
     * @return true si se elimina correctamente el usuario, false en cualquier otro caso.
     */
    public Boolean eliminarUser() {
        conjuntoUsuario.removeUser(user);
        guardarDatos();
        return ctrlDatosUsuarios.eliminarFicheros(user);
    }

    /**
     *
     * @param user alias del usuario.
     * @return true si existe el usuario, false en cualquier otro caso.
     */
    public Boolean existeUsuario(String user) {
        return conjuntoUsuario.existeUsuario(user);
    }
}

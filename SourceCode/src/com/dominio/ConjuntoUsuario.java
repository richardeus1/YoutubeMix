package com.dominio;

import java.util.ArrayList;

/**
 * La clase ConjuntoUsuario
 * @author Ricardo
 */
public class ConjuntoUsuario {
    private ArrayList<Usuario> conjuntoUsuarios;

    /**
     * Creadora por defecto
     */
    public ConjuntoUsuario(){
        this.conjuntoUsuarios = new ArrayList<>();
    }

    /**
     *
     * @return conjunto de usuarios existentes en el sistema
     */
    public ArrayList<Usuario> getUsuarios() {
        return conjuntoUsuarios;
    }

    /**
     * Modifica
     * @param usuarios conjunto de usuarios
     */
    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.conjuntoUsuarios = usuarios;
    }

    /**
     *
     * @param aliasUsuario alias de un usuario
     * @return true si existe el usuario identificado por "aliasUsuario", false en cualquier otro caso.
     */
    public Boolean existeUsuario(String aliasUsuario){
        for(Usuario u : conjuntoUsuarios) {
            if(u.getAliasUsuario().equals(aliasUsuario)) return true;
        }
        return false;
    }

    /**
     *
     * @param user alias de un usuario existente.
     * @return contraseña del usuario identificado por "user"
     */
    public String getContrasenya(String user){
        Integer i = getIndiceUsuario(user);
        return conjuntoUsuarios.get(i).getContrasenya();
    }

    /**
     * Añade un nuevo usuario.
     * @param aliasUsuario identificación de un usuario
     * @param contrasenya contraseña de un usuario
     * @param nombreCompleto nombre de un usuario
     * @param apellido apellido de un usuario
     * @param fechaNacimiento fecha de nacimiento de un usuario
     * @param genero genero del usuario
     * @param correoElectronico correo electronico de un usuario.
     * @return true si se ha añadido el nuevo usuario, false en caso que no.
     */
    public Boolean addUsuario(String aliasUsuario, String contrasenya, String nombreCompleto,String apellido, String fechaNacimiento, String genero, String correoElectronico){
        Boolean b = existeUsuario(aliasUsuario);
        if (!b) {
            Usuario u = new Usuario(aliasUsuario, contrasenya, nombreCompleto, apellido, fechaNacimiento, genero, correoElectronico);
            conjuntoUsuarios.add(u);
            return true;
        }
        return false;
    }

    /**
     *
     * @param aliasUsuario identificación de un usuario
     * @param contrasenya contraseña del usuario "aliasUsuario"
     * @return true si existe el usuario "aliasUsuario" con la contraseña "contrasenya", false en caso contrario.
     */
    public Boolean iniciarSesion(String aliasUsuario, String contrasenya){
        Integer i = getIndiceUsuario(aliasUsuario);
        if(i==-1) return false;
        String contraActual = conjuntoUsuarios.get(i).getContrasenya();
        if(contraActual.equals(contrasenya)) return true;
        return false;
    }

    /**
     *
     * @param alias identificación de un usuario.
     * @return -1 si el usuario no existe, sino numero mayor o igual a 0 que será la posicion del usuario en la lista.
     */
    private Integer getIndiceUsuario(String alias) {
        for(int i=0; i<conjuntoUsuarios.size();i++){
            if(conjuntoUsuarios.get(i).getAliasUsuario().equals(alias)) return i;
        }
        return -1;
    }

    /**
     * Modifica
     * @param alias alias de un usuario existente.
     * @param nombreCompleto nuevo nombre del usuario.
     * @param apellido nuevo apellido del usuario.
     * @param fechaNacimiento nueva fecha de nacimiento
     * @param genero nuevo genero del usuario
     * @param correoElectronico nuevo correo electronico.
     */
    public void setAtributos(String alias, String nombreCompleto,String apellido, String fechaNacimiento, String genero, String correoElectronico){
        Integer indice = getIndiceUsuario(alias);
        conjuntoUsuarios.get(indice).setNombreCompleto(nombreCompleto);
        conjuntoUsuarios.get(indice).setApellido(apellido);
        conjuntoUsuarios.get(indice).setFechaNacimiento(fechaNacimiento);
        conjuntoUsuarios.get(indice).setGenero(genero);
        conjuntoUsuarios.get(indice).setCorreoElectronico(correoElectronico);
    }


    /**
     *
     * @param user alias de un usuario existente.
     * @return toda la información del usuario "user".
     */
    public ArrayList<String> getInformacion(String user) {
        ArrayList<String> info = new ArrayList<>();
        Integer i = getIndiceUsuario(user);
        Usuario u = conjuntoUsuarios.get(i);
        info.add(u.getNombreCompleto());
        info.add(u.getApellido());
        info.add(u.getFechaNacimiento());
        info.add(u.getGenero());
        info.add(u.getCorreoElectronico());
        return info;
    }

    /**
     * Modifica
     * @param user alias de un usuario existente.
     * @param pass nueva contraseña del usuario.
     */
    public void setContrasenya(String user, String pass) {
        Integer i = getIndiceUsuario(user);
        conjuntoUsuarios.get(i).setContrasenya(pass);
    }

    /**
     * Eliminación del usuario identificado por "user".
     * @param user alias de un usuario existe.
     */
    public void removeUser(String user) {
        Integer i = getIndiceUsuario(user);
        conjuntoUsuarios.remove(conjuntoUsuarios.get(i));
    }
}

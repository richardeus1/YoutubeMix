package com.dominio;

/**
 * La clase UsuarioNumero
 * @author Natali Balón y Andreu Conesa
 */
public class UsuarioNumero {
    private String user;
    private Integer numRepro;

    /**
     * Creadora por defecto
     */
    public UsuarioNumero() {
    }


    /**
     * Creadora mediante el usuario y el numero de reproduccion
     * @param user alias del usuario.
     * @param numRepro numero de reproduccion.
     */
    public UsuarioNumero(String user, Integer numRepro) {
        this.user = user;
        this.numRepro = numRepro;
    }

    /**
     *
     * @return el alias del usuario
     */
    public String getUser() {
        return user;
    }

    /**
     * Modifica
     * @param user nuevo alias del usuario que ha reproduccido una canción.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return numero de reproducciones de una cancion por el usuario X
     */
    public Integer getNumRepro() {
        return numRepro;
    }

    /**
     * Modifica
     * @param numRepro numero de reproduccion nuevo de una cancion por el usuario X.
     */
    public void setNumRepro(Integer numRepro) {
        this.numRepro = numRepro;
    }
}

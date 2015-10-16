package com.dominio;

import java.util.ArrayList;

/**
 * La clase Reproducida
 * @author Natali Balón
 */
public class Reproducida {
    private String titulo;
    private String autor;
    private ArrayList<UsuarioNumero> conjuntoUsuario;

    /**
     * Creadora por defecto vacia
     */
    public Reproducida(){}

    /**
     * Creadora
     * @param titulo de la canción que se ha reproducido.
     * @param autor de la canción que se ha reproducido.
     * @param conjuntoUsuario conjunto de usuarios que han reproduccido esta canción.
     */
    public Reproducida(String titulo, String autor, ArrayList<UsuarioNumero> conjuntoUsuario) {
        this.titulo = titulo;
        this.autor = autor;
        this.conjuntoUsuario = conjuntoUsuario;
    }

    /**
     *
     * @return titulo de la canción reproducida.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Modifica
     * @param titulo nuevo de la canción reproducida.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return autor de la canción reproducida.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Modifica
     * @param autor nuevo de la canción reproduccida.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     *
     * @return conjuntos de usuarios que han reproduccido al menos una vez una canción.
     */
    public ArrayList<UsuarioNumero> getConjuntoUsuario() {
        return conjuntoUsuario;
    }

    /**
     * Modifica
     * @param conjuntoUsuario nuevo conjuntos de usuarios que han reproducido la canción.
     */
    public void setConjuntoUsuario(ArrayList<UsuarioNumero> conjuntoUsuario) {
        this.conjuntoUsuario = conjuntoUsuario;
    }
}

package com.dominio;

/**
 * @author Andreu Conesa
 */

public class Cancion {
    private String titulo;
    private String autor;
    private String album;
    private String genero;
    private Integer numRep;
    private String fechaInsercion;
    private String creador;
    private Integer anyo;

    /**
     * Cancion() constructor vacio
     */
    public Cancion(){}

    /**
     * Cancion() constructor con titulo y autor
     * @param titulo titulo de la cancion
     * @param autor autor de la cancion
     */
    public Cancion(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    /**
     * Compara dos objetos de tipo Cancion
     * @param o
     * @return true si el objeto o es igual al parametro implicito, false si no lo es
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cancion cancion = (Cancion) o;

        if (getTitulo() != null ? !getTitulo().equals(cancion.getTitulo()) : cancion.getTitulo() != null) return false;
        if (getAutor() != null ? !getAutor().equals(cancion.getAutor()) : cancion.getAutor() != null) return false;
        if (getAlbum() != null ? !getAlbum().equals(cancion.getAlbum()) : cancion.getAlbum() != null) return false;
        if (getGenero() != null ? !getGenero().equals(cancion.getGenero()) : cancion.getGenero() != null) return false;
        if (getNumRep() != null ? !getNumRep().equals(cancion.getNumRep()) : cancion.getNumRep() != null) return false;
        if (getFechaInsercion() != null ? !getFechaInsercion().equals(cancion.getFechaInsercion()) : cancion.getFechaInsercion() != null)
            return false;
        if (getCreador() != null ? !getCreador().equals(cancion.getCreador()) : cancion.getCreador() != null)
            return false;
        return !(getAnyo() != null ? !getAnyo().equals(cancion.getAnyo()) : cancion.getAnyo() != null);

    }

    /**
     * Cancion() constructor con todos los atributos
     * @param titulo titulo de la cancion
     * @param autor autor de la cancion
     * @param album album de la cancion
     * @param genero genero de la cancion
     * @param numRep numero de reproducciones de la cancion
     * @param fechaInsercion fecha de insercion de la cancion
     * @param creador creador de la cancion
     * @param anyo anyo de la cancion
     */
    public Cancion(String titulo, String autor, String album, String genero, Integer numRep, String fechaInsercion, String creador, Integer anyo ) {
        this.titulo = titulo;
        this.autor = autor;
        this.album = album;
        this.genero = genero;
        this.numRep = numRep;

        this.fechaInsercion = fechaInsercion;
        this.creador = creador;
        this.anyo = anyo;
    }

    /**
     * Cancion() constructor por copia
     * @param c2 c2 es la cancion que se copia en el parametro implicito
     */
    public Cancion(Cancion c2){
        this.titulo = c2.getTitulo();
        this.autor = c2.getAutor();
        this.album = c2.getAlbum();
        this.genero = c2.getGenero();
        this.numRep = c2.getNumRep();
        this.fechaInsercion = c2.getFechaInsercion();
        this.creador = c2.getCreador();
        this.anyo = c2.getAnyo();
    }

    /**
     * @return el titulo del parametro implicito
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return el autor del parametro implicito
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param titulo pasa a ser el titulo del parametro implicito
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param autor pasa a ser el autor del parametro implicito
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return el album del parametro implicito
     */
    public String getAlbum() {
        return album;
    }

    /**
     * @param album pasa a ser el album del parametro implicito
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * @return el genero del parametro implicito
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero pasa a ser el genero del parametro implicito
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return el numero de reproducciones del parametro implicito
     */
    public Integer getNumRep() { return numRep; }

    /**
     * @param numRep pasa a ser el numero de reproducciones del parametro implicito
     */
    public void setNumRep(Integer numRep) { this.numRep = numRep; }

    /**
     * @return la fecha de insercion del parametro implicito
     */
    public String getFechaInsercion() { return fechaInsercion; }

    /**
     * @param fechaInsercion pasa a ser la fecha de insercion del parametro implicito
     */
    public void setFechaInsercion(String fechaInsercion) { this.fechaInsercion = fechaInsercion; }

    /**
     * @return el creador del parametro implicito
     */
    public String getCreador() { return creador; }

    /**
     * @param creador pasa a ser el creador del parametro implicito
     */
    public void setCreador(String creador) { this.creador = creador; }

    /**
     * @return el anyo del parametro implicito
     */
    public Integer getAnyo() { return anyo; }

    /**
     * @param anyo pasa a ser el anyo del parametro implicito
     */
    public void setAnyo(Integer anyo) { this.anyo = anyo; }

    /**
     * @return una cadena con los atributos del parametro implicito
     */
    @Override
    public String toString() {
        return  titulo +
                ";" + autor +
                ";" + album +
                ";" + genero +
                ";" + numRep +
                ";" + fechaInsercion +
                ";" + creador +
                ";" + anyo
                ;
    }

    /**
     *
     * @return el identificador de la cancion con el formato titulo;autor
     */
    public String getId(){
        return titulo +";"+ autor;
    }
}

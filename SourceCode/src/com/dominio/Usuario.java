package com.dominio;

/**
 * La clase Usuario
 * @author Ricardo
 */


public class Usuario {
    private String aliasUsuario;
    private String contrasenya;
    private String nombreCompleto;
    private String apellido;
    private String fechaNacimiento;
    private String genero;
    private String correoElectronico;

    /**
     * Creadora por defecto
     */
    public Usuario() {
    }

    /**
     * Creadora mediante el alias y su contraseña
     * @param aliasUsuario identificador del usuario
     * @param contrasenya contraseña del usuario
     */
    public Usuario(String aliasUsuario, String contrasenya){
        this.aliasUsuario=aliasUsuario;
        this.contrasenya=contrasenya;

    }

    /**
     * Creadora
     * @param aliasUsuario identificador del usuario
     * @param contrasenya contraseña del usuario
     * @param nombreCompleto nombre del usuario
     * @param apellido apellido del usuario
     * @param fechaNacimiento fecha de nacimiento del usuario
     * @param genero género del usuario
     * @param correoElectronico correo electrónico del usuario
     */
    public Usuario(String aliasUsuario, String contrasenya, String nombreCompleto,String apellido, String fechaNacimiento, String genero, String correoElectronico){
        this.aliasUsuario=aliasUsuario;
        this.contrasenya=contrasenya;
        this.nombreCompleto=nombreCompleto;
        this.apellido=apellido;
        this.fechaNacimiento=fechaNacimiento;
        this.genero=genero;
        this.correoElectronico=correoElectronico;
    }

    /**
     *
     * @return el alias del usuario (parametro implicito)
     */
    public String getAliasUsuario() {return aliasUsuario;}

    /**
     *
     * @return la contraseña del parametro implicito
     */
    public String getContrasenya() {return contrasenya;}

    /**
     *
     * @return el nombre del parametro implicito
     */
    public String getNombreCompleto() {return nombreCompleto;}

    /**
     *
     * @return el apellido del parametro implicito.
     */
    public String getApellido(){return apellido;}

    /**
     *
     * @return la fecha de nacimiento del parametro implicito.
     */
    public String getFechaNacimiento() {return fechaNacimiento;}

    /**
     *
     * @return el género del parametro implicito.
     */
    public String getGenero() {return genero;}

    /**
     *
     * @return el correo electrónico del parametro implicito.
     */
    public String getCorreoElectronico() {return correoElectronico;}

    /**
     * Modifica
     * @param aliasUsuario nuevo alias del usuario.
     */
    public void setAliasUsuario(String aliasUsuario) {this.aliasUsuario=aliasUsuario;}

    /**
     * Modifica
     * @param contrasenya pasa a ser la nueva contraseña del parametro implicito
     */
    public void setContrasenya(String contrasenya) {this.contrasenya=contrasenya;}

    /**
     * Modifica
     * @param nombreCompleto pasa a ser el nombre completo del parametro implicito
     */
    public void setNombreCompleto(String nombreCompleto) {this.nombreCompleto=nombreCompleto;}

    /**
     * Modifica
     * @param apellido pasa a ser el apellido del parametro implicito
     */
    public void setApellido(String apellido) {this.apellido=apellido;}

    /**
     * Modifica
     * @param fechaNacimiento pasa a ser la fecha de nacimiento del parametro implicito
     */
    public void setFechaNacimiento(String fechaNacimiento) {this.fechaNacimiento=fechaNacimiento;}

    /**
     * Modifica
     * @param genero pasa a ser el género del parametro implicito
     */
    public void setGenero(String genero) {this.genero=genero;}


    /**
     * Modifica
     * @param correoElectronico pasa a ser el correo electrónico del parametro implicito
     */
    public void setCorreoElectronico(String correoElectronico) {this.correoElectronico=correoElectronico;}

}
package com.CtrlDatos;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Natali Balon
 * @author Andreu Conesa
 */
public class CtrlDatosUsuarios {
    private String pathBase;
    //El path al archivo usuarios.txt
    private String pathIndice;
    private static String pathBaseAdd;

    /**
     * Creadora por defecto
     */
    public CtrlDatosUsuarios() {
        //PARA CREAR LA CARPETA BASEDEDATOS POR SI EL SISTEMA NO LA TIENE
        File fD = new File("BaseDeDatos");
        if(!fD.exists()) fD.mkdir();
        pathBase ="BaseDeDatos/usuariosSistema/";
        //Miramos si existe el pathBaseLista
        File f = new File(pathBase);
        if(!f.exists()) f.mkdir(); //Si no existe creamos el directorio
        pathIndice = pathBase+"usuarios.txt";
        f = new File(pathIndice);
        if (!f.exists()) {
            try{
                f.createNewFile();
            }
            catch (Exception e){
                throw new IllegalArgumentException("Error: al cargar los usuarios "+e.getMessage());
            }
        }
        pathBaseAdd = "BaseDeDatos/";

    }

    /**
     * Permite leer todos los usuarios
     * @return array con toda la información de todos los usuarios.
     */
    public ArrayList<String> leerUsuarios(){
        ArrayList<String> conjuntoUsuarios = new ArrayList<>();
        try{
            String archivo = pathIndice;
            Path rPre = Paths.get(archivo);
            for(String line : Files.readAllLines(rPre, Charset.defaultCharset())) {
                conjuntoUsuarios.add(line);
            }

        }
        catch (Exception e){
            throw new IllegalArgumentException("Error: al cargar los usuarios "+e.getMessage());
        }
        return conjuntoUsuarios;
    }

    /**
     * Permite guardar toda la información de todos los usuarios.
     * @param datos array con toda la información de todos los usuarios.
     */
    public void escrbirUsuarios(ArrayList<String> datos) {
        try {
            FileWriter fw;
            BufferedWriter bw;
            File f;


            f = new File(pathBase);

            if (!f.exists()) {
                f.mkdirs();
                f = new File(pathIndice);
                f.createNewFile();
            }
            f = new File(pathIndice);
            fw = new FileWriter(f, false);
            bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            for (int i = 0; i < datos.size(); ++i) {
                out.println(datos.get(i));
            }
            out.close();
            fw.close();
            bw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: "+e.getMessage());
        }
    }

    /**
     * Permite crear todos los ficheros basicos del usuario
     * @param alias alias del usuario.
     * @param l lista basica
     */
    public static void crearFicheros(String alias, String l) {
        try {
            FileWriter fw;
            BufferedWriter bw;
            File f;
            //SE crea la carpeta del usuario
            String rutaCarpeta = pathBaseAdd + alias;
            f = new File(rutaCarpeta);
            f.mkdirs();

            //Se crea el fichero listas.txt
            String rutaFichero = pathBaseAdd+alias+"/listas.txt";
            f = new File(rutaFichero);
            f.createNewFile();

            //guardoListaVacia
            fw = new FileWriter(f, false);
            bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(l);

            out.close();
            fw.close();
            bw.close();


        } catch (IOException e) {
            throw new IllegalArgumentException("Error: "+e.getMessage());
        }
    }

    /**
     * Permite eliminar la carpeta del usuario logueado actual.
     * @param directorio directorio del usuario.
     */
    private static void borrarDirectorio (File directorio){
        File[] ficheros = directorio.listFiles();

        for (int x=0;x<ficheros.length;x++){
            if (ficheros[x].isDirectory()) {
                borrarDirectorio(ficheros[x]);
            }
            ficheros[x].delete();
        }
    }

    /**
     * Permite eliminar todos los ficheros relacionados con el usuario logueado
     * @param user alias del usuario logueado.
     * @return true si se ha eliminado to_do correctamente, false en caso contrario.
     */
    public Boolean eliminarFicheros(String user) {
        String rutaCarpeta = pathBaseAdd+user;
        File f;
        f = new File(rutaCarpeta);
        borrarDirectorio(f);
        Boolean b = f.delete();
        return b;
    }

}

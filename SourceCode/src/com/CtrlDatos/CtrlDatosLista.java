package com.CtrlDatos;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Natali Balon
 */
public class CtrlDatosLista {
    private String pathBaseLista;
    //El path al archivo listas.txt
    private String pathIndiceLista;
    private String pathBaseRepro;
    //El path al archivo reproducidas.txt
    private String pathIndiceRepro;

    /**
     * Creadora por defecto
     * @param user El alias del usuario logeado actualmente en el sistema
     */
    public CtrlDatosLista(String user) {
        pathBaseLista ="BaseDeDatos/"+user;
        //Miramos si existe el pathBaseLista
        File f = new File(pathBaseLista);
        if(!f.exists()) f.mkdirs(); //Si no existe creamos el directorio
        pathIndiceLista = pathBaseLista+"/listas.txt";
        //Miramos si existe el pathbase reproducidas y si no lo creamos
        pathBaseRepro ="BaseDeDatos/cancionesReproducidas/";
        File fb = new File(pathBaseRepro);
        if(!fb.exists()) fb.mkdirs();
        pathIndiceRepro = pathBaseRepro+"/reproducidas.txt";
        File f2 = new File(pathIndiceRepro);
    }

    /**
     * Permite leer todas las lista del usuario logueado
     * @return El array con toda la informacion de cada lista
     */
    public ArrayList<String> leerListas(){
        ArrayList<String> conjuntoListas = new ArrayList<>();
        try{
            FileWriter fw;
            BufferedWriter bw;
            String archivo = pathIndiceLista;
            File f = new File(pathBaseLista);
            if (!f.exists()) {
                f.createNewFile();

                //guardoListaVacia
                fw = new FileWriter(f, false);
                bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println("Mis canciones;Añadidas al sistema");
                out.close();
            }
            Path rPre = Paths.get(archivo);
            for(String line : Files.readAllLines(rPre, Charset.defaultCharset())) {
                conjuntoListas.add(line);
            }

        }
        catch (Exception e){
            throw new IllegalArgumentException("Error al cargar las listas: " + e.getMessage());

        }
        return conjuntoListas;
    }

    /**
     * Permite guardar todas las lista del usuario logueado
     * @param datos array de toda la información de todas las listas.
     */
    public void escrbirListas(ArrayList<String> datos) {
        try {
            FileWriter fw;
            BufferedWriter bw;
            File f;
            String archivoLista = pathIndiceLista;

            f = new File(archivoLista);

            if (!f.exists()) throw new IllegalArgumentException("Error: No tienes ningun archivo abierto.");
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
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    /**
     * Permite leer todas las reproducciones de los usuarios
     * @return array de toda la información de las reproducciones
     */
    public ArrayList<String> leerReproducidas() {
        ArrayList<String> conjuntoListas = new ArrayList<>();
        try{
            String archivo = pathIndiceRepro;
            File f;
            f = new File(archivo);

            if (!f.exists()) {
                f.createNewFile();
            }
            Path rPre = Paths.get(archivo);
            for(String line : Files.readAllLines(rPre, Charset.defaultCharset())) {
                conjuntoListas.add(line);
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Error al cargar las reproducidas: " + e.getMessage());
        }
        return conjuntoListas;
    }

    /**
     * Permite guardar todas las reproducciones de los usuarios.
     * @param datos información de todas las reproducciones.
     */
    public void guardarReproducidas(ArrayList<String> datos) {
        try {
            FileWriter fw;
            BufferedWriter bw;
            File f;
            String archivoLista = pathIndiceRepro;

            f = new File(archivoLista);

            if (!f.exists()) {
                f.createNewFile();
            }
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
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    /**
     * Permite guardar toda la información de una lista del usuario logueado
     * @param datos array con toda la información de la lista
     * @param ruta path del fichero donde se desea guardar la información.
     */
    public void guardarExportacion(ArrayList<String> datos, String ruta) {
        try {
            FileWriter fw;
            BufferedWriter bw;
            File f;
            f = new File(ruta);
            if (!f.exists()) {
                fw = new FileWriter(f);
                bw = new BufferedWriter(fw);
            } else {
                fw = new FileWriter(f, false);
                bw = new BufferedWriter(fw);
            }


            PrintWriter out = new PrintWriter(bw);
            String linea = "Nombre de la lista: \n   "+datos.get(0) + "\nDescripción: \n   " + datos.get(1) + "\nCanciones: ";
            Integer i = 2;
            while (i < datos.size()) {
                linea = linea + "\n   " + datos.get(i);
                ++i;
            }
            out.println(linea);
            out.close();
            fw.close();
            bw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }
}

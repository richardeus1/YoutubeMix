package com.CtrlDatos;

import com.dominio.Cancion;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author Andreu Conesa
 */
public class CtrlDatosCanciones {

    /**
     * Esta funcion carga las canciones del sistema que se encuentran
     * en BaseDeDatos/cancionesSistema/canciones.txt en un ArrayList<ArrayList<String>>
     * @return un ArrayList<ArrayList<String>> con toda la informacion de las canciones del sistema
     */
    public ArrayList<ArrayList<String> > cargarCancionesDesdeArchivo(){
        ArrayList<ArrayList<String>> l1 = new ArrayList<>();
        String archivoCancionesSistema = "BaseDeDatos/cancionesSistema/canciones.txt";
        File f = new File("BaseDeDatos/cancionesSistema");
        if(!f.exists()) f.mkdir();
        FileReader fr = null;
        BufferedReader br = null;
        Boolean res;
        String regex = "(.+);(.+);(.+);(.+);(\\d+);([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2});(.+);(\\d+)";
        try {
            f = new File(archivoCancionesSistema);
            if (!f.exists()) {
                f.createNewFile();
            }
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                res = linea.matches(regex);
                String[] partesDelString = linea.split(";");
                if (res && partesDelString.length == 8) {
                        ArrayList<String> ca = new ArrayList<>();
                        ca.add(0, partesDelString[0]);
                        ca.add(1, partesDelString[1]);
                        ca.add(2, partesDelString[2]);
                        ca.add(3, partesDelString[3]);
                        ca.add(4, partesDelString[4]);
                        ca.add(5, partesDelString[5]);
                        ca.add(6, partesDelString[6]);
                        ca.add(7, partesDelString[7]);
                        l1.add(ca);

                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("Archivo no encontrado");
        } catch (IOException ioe) {
            System.out.println("Fallo o interrupcion en operacion de entrada/salida");
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException ioe) {
                System.out.println("Fallo o interrupcion en operacion de entrada/salida");
            }
        }
        return l1;
    }

    /**
     * Esta funcion carga toda la informacion
     * de las canciones del sistema en BaseDeDatos/cancionesSistema/canciones.txt
     * @param l1 es la lista de canciones del sistema
     */
    public void cargarCancionesHaciaArchivo(ArrayList<String> l1) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("BaseDeDatos/cancionesSistema/canciones.txt"));
            for (int i = 0; i < l1.size(); ++i) {
                pw.println(l1.get(i));
            }
            pw.close();
        }catch (FileNotFoundException fnfe) {
            System.out.println("Archivo no encontrado");
        }
    }

    /**
     * Esta funcion carga las canciones que se encuentran
     * en path en un ArrayList<ArrayList<String>>
     * @param path es la ruta absoluta del archivo que contiene las canciones a anyadir
     * @param alias es el alias del usuario que quiere realizar la operacion
     * @return un ArrayList<ArrayList<String>> con titulo, autor, album, genero, reproducciones, la fecha actual,
     * el alias de usuario y el anyo de aquellas lineas que han cumplido el formato
     * titulo;autor;album;genero;reproducciones;anyo
     */
    public ArrayList<ArrayList<String> > anadirCancionesDesdeArchivo(String path, String alias) {
        Boolean res = true;
        ArrayList<ArrayList<String>> l1 = new ArrayList<>();
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = formatter.format(date);
        File f = null;
        FileReader fr = null;
        BufferedReader br = null;
        String regex = "(.+);(.+);(.+);(.+);(\\d+);(\\d+)";
        try {
            f = new File(path);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                res = linea.matches(regex);
                String[] partesDelString = linea.split(";");
                // titulo;autor;album;genero;reproducciones;anyo
                if (res && partesDelString.length == 6){
                        ArrayList<String> ca = new ArrayList<>();
                        ca.add(0,partesDelString[0]);
                        ca.add(1,partesDelString[1]);
                        ca.add(2,partesDelString[2]);
                        ca.add(3,partesDelString[3]);
                        ca.add(4,partesDelString[4]);
                        ca.add(5,hoy);
                        ca.add(6,alias);
                        ca.add(7,partesDelString[5]);
                        l1.add(ca);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("Archivo no encontrado");
        } catch (IOException ioe) {
            System.out.println("Fallo o interrupcion en operacion de entrada/salida");
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException ioe) {
                System.out.println("Fallo o interrupcion en operacion de entrada/salida");
            }
        }
        return l1;
    }

    /**
     * Esta funcion lee el archivo del path y anyade a la lista que devuelve cada linea que cumple el formato
     * @param path es la ruta absoluta del archivo que contiene los identificadores de las canciones a borrar
     * @return un ArrayList<ArrayList<String>> con titulo y autor de todas las lineas del archivo
     * que han cumplido el formato titulo;autor
     */
    public ArrayList<ArrayList<String>> borrarCancionesDesdeArchivo(String path)
    {
        ArrayList<ArrayList<String>> l1 = new ArrayList<>();
        Boolean res = true;
        File f = null;
        FileReader fr = null;
        BufferedReader br = null;
        String regex = "(.+);(.+)";
        try {
            f = new File(path);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                res = linea.matches(regex);
                String[] partesDelString = linea.split(";");
                // titulo;autor
                if (res && partesDelString.length == 2) {
                        ArrayList<String> ca = new ArrayList<>();
                        ca.add(0, partesDelString[0]);
                        ca.add(1, partesDelString[1]);
                        l1.add(ca);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("Archivo no encontrado");
        } catch (IOException ioe) {
            System.out.println("Fallo o interrupcion en operacion de entrada/salida");
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException ioe) {
                System.out.println("Fallo o interrupcion en operacion de entrada/salida");
            }
        }
        return l1;
    }

    /**
     * Esta funcion lee el archivo del path y anyade a la lista que devuelve cada linea que cumple el formato
     * @param path es la ruta absoluta del archivo que contiene la informacion necesaria para modificar canciones
     * @return un ArrayList<ArrayList<String>> con titulo, autor, album, genero y anyo de todas las lineas
     * del archivo que han cumplido el formato titulo;autor;album;genero;anyo
     */
    public ArrayList<ArrayList<String>> modificarCancionesDesdeArchivo(String path) {
        ArrayList<ArrayList<String>> l1 = new ArrayList<>();
        Boolean res = true;
        File f = null;
        FileReader fr = null;
        BufferedReader br = null;
        String regex = "(.+);(.+);(.+);(.+);(\\d+)";
        try {
            f = new File(path);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                res = linea.matches(regex);
                String[] partesDelString = linea.split(";");
                // titulo;autor;album;genero;anyo
                if (res && partesDelString.length == 5) {
                        ArrayList<String> ca = new ArrayList<>();
                        ca.add(0, partesDelString[0]);
                        ca.add(1, partesDelString[1]);
                        ca.add(2,partesDelString[2]);
                        ca.add(3,partesDelString[3]);
                        ca.add(4,partesDelString[4]);
                        l1.add(ca);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("Archivo no encontrado");
        } catch (IOException ioe) {
            System.out.println("Fallo o interrupcion en operacion de entrada/salida");
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException ioe) {
                System.out.println("Fallo o interrupcion en operacion de entrada/salida");
            }
        }
        return l1;
    }
}

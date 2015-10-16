package com.CtrlDatos;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ricardo
 * @version 2.0
 */
public class CtrlDatosConjuntoAgrupaciones {
    private final String aliasUsuario;
    /**
     * El path base para todos los archivos
     */
    private final String pathBase;
    //El path al archivo indiceConjuntos.txt
    private final String pathIndice;

    /**
     * Creadora por defecto necesitamos saber la ruta
     * @param aliasUsuario El alias del usuario logeado actualmente en el sistema
     */
    public CtrlDatosConjuntoAgrupaciones(String aliasUsuario){
        this.aliasUsuario = aliasUsuario;
        pathBase = "BaseDeDatos/"+aliasUsuario+"/DatosConjuntos/";  //NUEVA RUTA
//        pathBase = "src/com/baseDatos/"+aliasUsuario+"/DatosConjuntos/";
        //Miramos si existe el pathBase
        File f = new File(pathBase);
        if(!f.exists()) f.mkdir(); //Si no existe creamos el directorio
        pathIndice = pathBase+"indiceConjuntos.txt";
    }

    /**
     * Devuelve el nombre de todos los conjuntos autogenerados guardados en memoria
     * @return El array con todos los nombres o null si no existe ninguno
     */
    public ArrayList<String> cargarNombresConjuntos(){
        ArrayList<String> nombres = new ArrayList<>();
        //Tenemos que comprobar primero si hay alguna lista guarda (y por tanto ha de existir el archivo totalConjuntos.txt
        File fr = new File(pathBase);
        String[] conjuntos = fr.list();
        ArrayList<String> res = new ArrayList<String>(Arrays.asList(conjuntos));
        res.remove("indiceConjuntos.txt"); //Para dar compatibilidad con Bases de datos antiguas del programa que usaban este atributo, se mantiene al guardar igualmente para ser retrocompatible
        return res;
        //return nombres;
    }

    /**
     * Guarda los datos de un conjunto en memoria
     * <p>
     *     ->datos: contiene los siguientes elementos
     *     0: nombre conjunto
     *     1: metodo utilizado
     *     2: los parámetros utilizados en un unico string separado por ; cada parámetro
     *     3: si ha sido modificado o no
     *     4: el origen de los datos utilizado
     *     5: la descripción
     *     ->agrupaciones: contiene un arraylist con todos los ids de una agrupación para cada agrupación
     * </p>
     * @param datos Todos los campos del conjunto
     * @param agrupaciones Un arraylist por agrupación que contiene todos los ids de canciones
     * @param nombresConjuntos Contiene el nombre de todos los conjuntos que están en memoria o se van a guardar, incluye también este conjunto a guardar en la última posición
     * @return true si se ha realizado la acción false si no
     */
    public Boolean guardarConjunto(ArrayList<String> datos,ArrayList<ArrayList<String>> agrupaciones,ArrayList<String> nombresConjuntos){
        try{
            //Primero vamos a guardar los nombresConjuntos
            File f = new File(pathIndice);
            f.delete();
            Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"utf-8"));
            for(String st : nombresConjuntos){
                wr.write(st);
                wr.write("\n");
            }
            //Una vez guardado el nombre de conjuntos vamos a crear el directorio y elementos conforme los necesitemos
            //Cerramos y limpiamos
            wr.close(); //CERRAMOS EL BUFFER DE ESCRITURA APLICANDO LOS CAMBIOS

            //Ahorra guardamos los datos del conjunto
            String nombreConjunto = datos.get(0);
            File f2 = new File(pathBase+nombreConjunto);
            //Primero borramos su directoio si ya existe
            borrarDirectorio(f2);
            //Segundo creamos la carpeta directorio para ese conjunto a guardar
            f2.mkdir();
            //Tercero voy a crear el .txt con todos los datos del conjunto (los guardamos en el archivo datos.txt
            File f3 = new File(f2.getPath()+"/datos.txt");
            Writer wr2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f3),"utf-8"));
            for(String st : datos){
                wr2.write(st);
                wr2.write("\n");
            }
            wr2.close();
            //Cuarto creo un directorio agrupaciones donde voy a guardar en un txt cada conjunto del 0 al 1
            File f4 = new File(f2.getPath()+"/Agrupaciones");
            f4.mkdir();
            //Quinto guardo en esa carpeta cada agrupación
            int i = 0;
            for(ArrayList<String> ag : agrupaciones){
                File f5 = new File(f4.getPath()+"/"+i+".txt");
                Writer wr3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f5),"utf-8"));
                for(String st : ag){
                    wr3.write(st);
                    wr3.write("\n");
                }
                wr3.close();
                i++;
            }
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    /**
     * Operación recursiva para borrar todos los elementos de un directorio y el propio directorio
     * @param dir El directorio a eliminar
     * @return True si se ha conseguido borrar el directorio
     */
    private static Boolean borrarDirectorio(File dir)
    {
        if (dir.isDirectory())
        {
            String[] subdirectorios = dir.list();
            for (int i=0; i<subdirectorios.length; i++)
            {
                boolean conseguido = borrarDirectorio(new File(dir, subdirectorios[i]));
                if (!conseguido) return false;
            }
        }
        // Directorio ya está vacio
        return dir.delete();
    }

    /**
     * Permite cargar de memoria los datos del conjunto (no sus agrupaciones)
     * @param idConjunto El identificador del conjunto a cargar sus datos
     * @return El un array con N campos igual que cuando cargas
     * <p>
     *     Estructura del return, contiene los siguientes elementos en las siguientes posiciones
     *     0: nombre conjunto
     *     1: metodo utilizado
     *     2: los parámetros utilizados en un unico string separado por ; cada parámetro
     *     3: si ha sido modificado o no
     *     4: el origen de los datos utilizado
     *     5: la descripción
     * </p>
     */
    public ArrayList<String> cargarDatosConjunto(String idConjunto){
        ArrayList<String> res = new ArrayList<>();
        String pathConjunto = pathBase+idConjunto+"/datos.txt";
        Path p = Paths.get(pathConjunto);
        try{
            for(String t :Files.readAllLines(p,Charset.defaultCharset())){
                res.add(t);
            }
        }
        catch (Exception e){
            return null;
        }
        return res;
    }


    /**
     * Carga todas las agrupaciones del conjunto de agrupaciones identificado por idConjunto
     * @param idConjunto El identificador del conjunto a cargar sus datos
     * @return Un array para agrupación donde están todos los ids
     */
    public ArrayList<ArrayList<String>> cargarAgrupaciones(String idConjunto){
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        String pathAgrupaciones = pathBase+idConjunto+"/Agrupaciones";
        try {
            Path pth = Paths.get(pathAgrupaciones);
            File dir = new File(pathAgrupaciones);
            String[] pathAg = dir.list(); //Obtenemos cada elemento dentro del directorio agrupaciones
            for (String p : pathAg) {
                //Ahjora cargamos los datos de cada uno
                ArrayList<String> idsC = new ArrayList<>();
                pth = Paths.get(pathAgrupaciones+"/"+p);
                for (String t : Files.readAllLines(pth, Charset.defaultCharset())) {
                    //Cada linea de un archivo es un id
                    idsC.add(t);
                }
                res.add(idsC);
            }
        }
        catch (Exception e){
            return null;
        }
        return res;
    }

    public Boolean borrarConjunto(String idConjunto){
        try{
            String pathConjunto = pathBase+idConjunto;
            File f = new File(pathConjunto);
            borrarDirectorio(f);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public Boolean actualizarIndiceConjuntos(ArrayList<String> nombresConjuntos){
        try{
            File f = new File(pathIndice);
            f.delete();
            Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"utf-8"));
            for(String st : nombresConjuntos){
                wr.write(st);
                wr.write("\n");
            }
            wr.close();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public void guardarArchivo(String datos, String ruta) {
        try {
            File f = new File(ruta);
            f.delete();
            Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
            wr.write(datos);
            wr.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

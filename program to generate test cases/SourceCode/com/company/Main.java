package com.company;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa para generar juegos de prueba para la practica de PROP
 * @author Ricardo
 */
public class Main {
    final static String sp = ";";

    //Configura aquí los parámetros puedes añadir nuevos géneros o quitar o bien modficiar los numX
    static String[] gen = {"Pop","Dance","Electro","Latino","Clasica","Country"}; //6 estilo del 0 al 5
    final static int numAutores = 25;
    final static int numAlbum = 45;
    final static int anyoMin = 1980;
    final static int anyoMax = 2015;

    /**
     *
     * @param max El número máximo a generar
     * @param min El número mínimo a generar
     * @return Devuelve un número aleatorio entre max y min incluidos
     */
    public static int aleatorio(int max,int min){
        Random al = new Random();
        int n = al.nextInt(max-min+1) +min;
        return n;
    }

    /**
     * Main para ejecutar el programa
     * @param args NO SE NECESITA
     */
    public static void main(String[] args) {
	// write your code here
        System.out.println("Introduzca el numero de canciones a generar");
        Scanner entrada = new Scanner(System.in);
        int num = entrada.nextInt();
        String dato = "";
        String id = "";
        long before = System.nanoTime();
        for(int i=1;i<=num;i++){
            //En aux guardamos primero el id de la canción titulo;autor para poder luego usarlo para borrar canciones por ejemplo
            String aux = "c"+i+sp+"a"+aleatorio(numAutores,1);
            id = id + aux +"\n";
            dato = dato + aux +sp+"A"+aleatorio(numAlbum,1)+sp+gen[aleatorio(gen.length-1,0)]+sp+aleatorio(15,0)+sp+aleatorio(anyoMax,anyoMin)+"\n";
            //titulocancion;autor;album;GENERO;REP;AÑO
        }
        File f = new File("Canciones.txt");
        File f2 = new File("CancionesID.txt");
        try {
            Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"utf-8"));
            wr.write(dato);
            wr.close();
            Writer wr2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2),"utf-8"));
            wr2.write(id);
            wr2.close();
            long after = System.nanoTime();
            double tex = (double) (after - before)/1000000000;
            System.out.println("Se han generado " +num +" canciones aleatorias en el archivo Canciones.txt, junto a sus ids en CancionesID.txt gracias por usar el supergenerador. Tiempo ejecucion total: " + tex + " segundos");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

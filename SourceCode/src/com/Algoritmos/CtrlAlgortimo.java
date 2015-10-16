package com.Algoritmos;

import com.dominio.Cancion;
import com.dominio.CtrlConjuntoReproducidas;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Ricardo
 */
public class CtrlAlgortimo {
    CtrlConjuntoReproducidas ctrlRep;
    /**
     * Creadora por defecto de la clase
     */
    public CtrlAlgortimo(CtrlConjuntoReproducidas ctrlRep){
        this.ctrlRep = ctrlRep;
    }

    /**
     * Dado una canción c1 y c2 y unos parametros calculamos su peso, segun los parametros que tengan en común
     * @param c1 La canción 1
     * @param c2 La canción 2
     * @param par Los parámetros que se usarán para asignarle un peso, estos parámatros si valen <1 no evaluan la condición, pero si >1 le dan más o menos importancia
     * @return El peso que se le ha dado a la relación entre c1 y c2 en funcion de los parametros par
     */
    private double obtenerPeso(Cancion c1, Cancion c2, ArrayList<Integer> par){
        double pes = 0.0;
        //Miramos el parámetro 0 autor
        if(par.get(0)>0){
            if(c1.getAutor().equals(c2.getAutor())) pes += par.get(0);
        }
        //Miramos el parámetro 1 album
        if(par.get(1)>0){
            if(c1.getAlbum().equals(c2.getAlbum())) pes += par.get(1);
        }
        //Miramos el parámetro 2 genero
        if(par.get(2)>0){
            if(c1.getGenero().equals(c2.getGenero())) pes += par.get(2);
        }
        //Miramos el parámetro 3 nºde reproducciones comun o simular (que han sido escuchadas con usuarios)
        if(par.get(3)>0){
            //LA NUEVA VERSIÓN DONDE SE TIENE EN CUENTA SI LOS USUARIOS LA HAN RERPODUCIDO EN COMÚN
            Integer n = ctrlRep.numRepComun(c1,c2);//Número de usuarios en común que han reproducido esas dos canciones del sistema
            if(n>1){ //>1 implica que la han reproducido dos
                pes += par.get(3); //Ya cumple la condición así que le damos su peso en importancia las han reproducido mínimo dós usuarios en común
                if(n>2 && n<11) pes+= (par.get(3)/4.0); //La han reproducido más de 2 usuarios y max 10 usuarios en común por tanto aumentamos su peso un poco
                if(n>11 && n<21) pes+= (par.get(3)/3.0); //La han reproducido max 21 usuarios en común por tanto aumentamos su peso más
                if(n>22 && n<36) pes+= (par.get(3)/2.0); //La han reproducido 35 usuarios en común por tanto aumentamos su peso mucho más
                if(n>40) pes+= (par.get(3)/1.0); //La han reproducido más de 40 usuarios en común por tanto aumentamos su peso muchísimo
            }
            /* ANTIGUO VERSION ANTIGUA
            int nRepComun = Math.abs(c1.getNumRep()-c2.getNumRep()); //Considero que es un número similar de veces si no excede de 10 la diferencia
            if(nRepComun == 0 && c1.getNumRep()>0) pes += 2.5; //Mismo Numero de veces diferente a cero
            else if(nRepComun < 6) pes += 1.0; //Hay una diferencia de 5 max entre el número de reproducciones por tanto tienen un número similar de reproducciones
            else if(nRepComun < 11) pes += 0.6; //Diferencia superior a 10 así que no han sido reproducias un número similar de veces
            */
        }
        //Miramos el parámetro 4, año similar de las canciones
        if(par.get(4)>0){
//            double aux = par.get(4).doubleValue();
            if(c1.getAnyo().equals(c2.getAnyo())) pes += par.get(4); //Mismo año en la canción
            else if(Math.abs(c1.getAnyo()-c2.getAnyo()) == 1) pes += (par.get(4)/2.0); //No son del mismo año pero son similares por tanto ya que hay muchos años considero que tienen que puntuar un poco
            else if(Math.abs(c1.getAnyo()-c2.getAnyo()) < 4) pes += (par.get(4)/4.0); //No son del mismo año pero son similares por tanto ya que hay muchos años considero que tienen que puntuar un poco
        }

        return pes;
    }


    /**
     * Dada una canción construye su identificador
     * @param c
     * @return El identificador de la cancion titulo;autor
     */
    private String idCancion(Cancion c){
        String codigo = c.getTitulo()+";"+c.getAutor();
        return codigo;
    }

    /**
     * Nos convierte la lista de canciones a una lista de identifcadores respetando el orden
     * @param canciones
     * @return Devuelve un hashset de ids de canciones
     */
    private HashSet<String> setIdsCanciones(ArrayList<Cancion> canciones){
        HashSet<String> t = new HashSet<>();
        for(Cancion c : canciones){
            t.add(idCancion(c));
        }
        return t;
    }

    /**
     * Dado un listado de identificadores de canciones, y unos parámetros para obtener los pesos se generará un grafo.
     * <p>
     *     Esta función es llamada cuando las canciones que serán procesada ya han sido filtradas y los parametros para
     *     autogenerar el grafo (los de la función autogenerar).
     *     Ese arraylist de 4 elementos integer, tiene un formato muy concreto, cada campo equivale a uno de los parámetros de la autogenración,
     *     que a su vez son diferentes atributos de la canción (si no valen null implica que se evaluarán a la hora de generar el grafo),
     *     estos parametros pueden ser:
     *     0:autor común (1 si se evalua esa condición)
     *     1:albúm común (1 si se evalua esa condición)
     *     2:género común (1 si se evalua esa condición)
     *     3:numero de rerpoducciones común (o similar) (>0 si se evalua esa condición, siendo ese número el numero de reproducciones en común).
     *     4:año de publicación de la canción común (o similar) (1 si se evalua)
     * </p>
     * @param Canciones en esta lista tenemos todas las canciones sobre las cuales se aplicará el algoritmo
     * @param parametrosAutoGenerar una lista con los paramaetros que se evaluarán al ponderar dos nodos (canciones) del grafo
     * @return un grafo con pesos que contiene todas las canciones
     */

    private Graf generarGrafo(ArrayList<Cancion> Canciones,ArrayList<Integer> parametrosAutoGenerar){
        double peso;
        HashSet<String> IdsCanciones = setIdsCanciones(Canciones); //Aquí tendremos todas los idsCanciones
        Graf g = new Graf(IdsCanciones); //Creamos un grafo nuevo con todas las nodos = ids canciones que se procesarán
        for(int i = 0; i< Canciones.size();i++){
            for(int j = (i+1); j<Canciones.size();j++){
                peso = obtenerPeso(Canciones.get(i),Canciones.get(j),parametrosAutoGenerar);
                if(peso > 0) g.addAresta(idCancion(Canciones.get(i)),idCancion(Canciones.get(j)),peso); //La añadimos
            }
        }
        return g;
    }

    /**
     * Dado una lista de ids de canciones, los parametros para autogenerar y como de estrico se pide que sea el algoritmo se generaran las comunidades de canciones usando el algoritmo de Clique
     * @param Canciones en esta lista tenemos todas las canciones a procesar.
     * @param parametrosAutoGenerar una lista con los paramaetros que se evaluarán al ponderar dos nodos (canciones) del grafo
     * @param estricto integer de 0 a 100 que nos indica como de estricto será el algoritmo
     * @return conjunto de comunidades con todas las cancioens de idsCacanciones generadas con el algoritmo de clique.
     */
    public HashSet<HashSet<String>> generarComunidadClique(ArrayList<Cancion> Canciones,ArrayList<Integer> parametrosAutoGenerar, Integer estricto){
        Graf g = generarGrafo(Canciones, parametrosAutoGenerar);
        //Forma de llamarlo correcto
        long before = System.nanoTime();
        HashSet<HashSet<String>> res = Clique.executa(g,estricto);
        long after = System.nanoTime();
        double tex = (double) (after - before)/1000000000;
        System.out.println("Tiempo ejecucion total Clique " + tex +" segundos");
        return res;
    }

    /**
     * Dado una lista de ids de canciones, los parametros para autogenerar y como de estrico se pide que sea el algoritmo se generaran las comunidades de canciones usando el algoritmo de Louvain
     * @param Canciones en esta lista tenemos todas las canciones a procesar.
     * @param parametrosAutoGenerar una lista con los paramaetros que se evaluarán al ponderar dos nodos (canciones) del grafo
     * @param estricto integer de 0 a 100 que nos indica como de estricto será el algoritmo
     * @return conjunto de comunidades con todas las cancioens de idsCacanciones generadas con el algoritmo de Louvain.
     */
    public  HashSet<HashSet<String>> generarComunidadLouvain(ArrayList<Cancion> Canciones,ArrayList<Integer> parametrosAutoGenerar, Integer estricto){
        Graf g = generarGrafo(Canciones, parametrosAutoGenerar);
        //Forma de llamarlo correctamente
        long before = System.nanoTime();
        HashSet<HashSet<String>> res = Louvain.executa(g, estricto);
        long after = System.nanoTime();
        double tex = (double) (after - before)/1000000000;
        System.out.println("Tiempo ejecucion total Louvian " + tex +" segundos");
        return res;
    }

    /**
     * Dado una lista de ids de canciones, los parametros para autogenerar y como de estrico se pide que sea el algoritmo se generaran las comunidades de canciones usando el algoritmo de Newmann Girvan
     * @param Canciones en esta lista tenemos todas las canciones a procesar.
     * @param parametrosAutoGenerar una lista con los paramaetros que se evaluarán al ponderar dos nodos (canciones) del grafo
     * @param estricto integer de 0 a 100 que nos indica como de estricto será el algoritmo
     * @return conjunto de comunidades con todas las cancioens de idsCacanciones generadas con el algoritmo de Newmann Girvan.
     */
    public  HashSet<HashSet<String>> generarComunidadNewmann(ArrayList<Cancion> Canciones,ArrayList<Integer> parametrosAutoGenerar, Integer estricto){
        Graf g = generarGrafo(Canciones, parametrosAutoGenerar);
        //FORMA DE LLAMARLO CORRECTAMENTE
        long before = System.nanoTime();
        HashSet<HashSet<String>> res = AlgorismeNewmanGirvan.executa(g, estricto);
        long after = System.nanoTime();
        double tex = (double) (after - before)/1000000000;
        System.out.println("Tiempo ejecucion total NewmanGirvan " + tex +" segundos");
        return res;
    }
}

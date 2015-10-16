package com.Algoritmos;



import java.util.*;

/**
 *
 * La clase Clique Percolation para la detection de comunidades
 * @author Alejandro Q
 * @author Natali Balón
 *
 */
public class Clique {

    private static Graf graph;
    private static double pMax;
    private static double pMin;
    private static ArrayList<HashSet<String>> cliques; //Tendra TODOS los cliques ordenados del grafo "graph"
    private static double mediaK;


    /**
     * Creadora por defecto.
     */
    public Clique()
    {
    }

    /**
     *
     * @return conjuntos de todos los cliques del Graf graph.
     */
    private static ArrayList<HashSet<String>> getAllMaximalCliques()
    {
        cliques = new ArrayList<HashSet<String>>();
        ArrayList<String> potential_clique = new ArrayList<String>();
        ArrayList<String> candidates = new ArrayList<String>();
        ArrayList<String> already_found = new ArrayList<String>();
        candidates.addAll(graph.getNodes());
        findCliques(potential_clique, candidates, already_found);
        return cliques;
    }

    /**
     * Encuentra un clique
     * @param potential_clique conjunto de nodos que pertenecen a un mismo clique.
     * @param already_found conjunto de nodos que pueden pertenecer a un clique
     * @param candidates conjunto de nodos candidatos a ser clique.
     *
     */
    private static void findCliques(List<String> potential_clique, List<String> candidates, List<String> already_found)
    {
        List<String> candidates_array = new ArrayList<String>(candidates);
        if (!end(candidates, already_found)) {

            //para cada candidate_node en candidates hacer
            for(int i=0; i<candidates_array.size(); i++) {
                String candidate = candidates_array.get(i);
                List<String> new_candidates = new ArrayList<String>();
                List<String> new_already_found = new ArrayList<String>();

                // mover nodo "candidate" a potential_clique
                potential_clique.add(candidate);
                candidates.remove(candidate);

                //crear new_candidates eliminando nodos de "candidates" conectado al nodo candidate

                for(int j=0; j<candidates.size(); j++){
                    String new_candidate = candidates.get(j);
                    if (graph.existeixAresta(candidate, new_candidate))
                    {
                        new_candidates.add(new_candidate);
                    }
                }

                //crear new_already_found eliminando nodo de "already_found" conectado al nodo "candidate"
                for(int k=0; k<already_found.size(); k++){
                    String new_found = already_found.get(k);
                    if (graph.existeixAresta(candidate, new_found)) {
                        new_already_found.add(new_found);
                    }
                }

                //si new_candidates y new_already_found estan vacio
                if (new_candidates.isEmpty() && new_already_found.isEmpty()) {
                    //potential_clique es el maximo clique que se ha encontrado
                    cliques.add(new HashSet<String>(potential_clique));
                }
                else {
                    //llamada recursiva
                    findCliques(
                            potential_clique,
                            new_candidates,
                            new_already_found);
                }

                //mover "candidate_node" desde "potential_clique" a "already_found"
                already_found.add(candidate);
                potential_clique.remove(candidate);
            }
        }
    }

    /**
     * @param candidates conjunto de nodos que no pertenecen a ningun clique
     * @param already_found conjunto de nodos que pueden pertenecer a un clique
     * @return true si un nodo de los encontrados esta conectado con todos los nodos candidatos, false en otro caso.
     */
    private static Boolean end(List<String> candidates, List<String> already_found)
    {
        // if a node in already_found is connected to all nodes in candidates
        Boolean end = false;
        int edgecounter;
        for(int i=0; i<already_found.size(); i++){
            String found = already_found.get(i);
            edgecounter = 0;
            for (int j=0; j<candidates.size(); j++){
                String candidate = candidates.get(j);
                if (graph.existeixAresta(found, candidate)) {
                    edgecounter++;
                } // of if
            } // of for
            if (edgecounter == candidates.size()) {
                //end = true;
                return true;
            }
        } // of for
        return false;
        //return end;
    }

    /**
     *
     * @return conjunto de cliques ordenados de mayor a menor
     */
    private static ArrayList<HashSet<String>> getCliquesOrder()
    {
        //tenemos en cliques todos los cliques
        int maximum = 0;
        mediaK = 0.0;
        int n = 0;
        ArrayList<HashSet<String>> order_cliques = new ArrayList<HashSet<String>>();
        for (HashSet<String> clique : cliques) {
            n ++;
            mediaK += clique.size();
            if (maximum < clique.size()) {
                maximum = clique.size();
            }
        }
        for(int i = maximum; i>=0; --i) {
            for (HashSet<String> clique : cliques) {
                if (i == clique.size()) {
                    order_cliques.add(clique);
                }
            }
        }
        mediaK = mediaK/n;
        return order_cliques;
    }

    /**
     * Genera el Graf graph que sera con el cual trabajará la funcion getAllMaximalCliques()
     * @param f Grafo original sin filtrado.
     * @param percentatge indica lo estricto que desea ser.
     */
    private static void     determinarGrafoNuevo(Graf f, Integer percentatge){
        //Primero determinamos pMax y pMin
        //obtengo todos los nodos
        pMax = Double.MIN_VALUE;
        pMin = Double.MAX_VALUE;
        double mediaPesos = 0.0;
        HashSet<String> nodes = f.getNodes();

        //para cada nodo miro sus adjacentes y determino pMax, pMin.
        int c = 0;
        for (String n : nodes) {
            HashSet<String> adj = f.getAdjacents(n);
            for (String nod : adj) {
                double p = f.getPes(n, nod);
                if (p > pMax) pMax = p;
                if (p < pMin) pMin = p;
                mediaPesos += p;
                c++;
            }
        }

        mediaPesos = mediaPesos/c;

        /**
         * Cambios hechos, ahora como partimos de la media (estamos ya aprox sobre el 50% de estricto aumententamos o decrecemos ese 50% según el parámetro de estricto que nos piden
         */
        //Determino la W segun el peso Maximo, el peso Minimo, la media y el porcentaje de estricto.
        double w = calcularMediaEstricta(mediaPesos,pMax,pMin,percentatge);
        //Hago una copia.
        graph = new Graf(f);
        //elimino las aristas que no cumple la W:
        for (String n : nodes){
            HashSet<String> adj = f.getAdjacents(n);
            for (String nod : adj){
                double p = f.getPes(n, nod);
                if (p<w) graph.removeAresta(n, nod);
            }
        }
    }

    /**
     * Dado un grafo con pesos y un porcentaje se obtienen sus comunidades usando Clique
     * @param G grafo original sin filtrado
     * @param percentatge indica lo estricto que desea ser.
     * @return conjunto de comunidades que tiene el grafo G con peso aplicandole una filtrado.
     */
    public static HashSet<HashSet<String>> executa(Graf G, Integer percentatge){

        //GEnero el grafo con la cual va a trabajar para detectar los cliques
        determinarGrafoNuevo(G, percentatge);
        //G = null;
        //ME encuentra todos los cliques que existen en el grafo "GRAPH"
        getAllMaximalCliques();
        //Me ordena los cliques de mayor a menor(K mas alta)
        cliques = getCliquesOrder();
        //Vamos a vaciar memoria
        //Llamamos al garbage collector
        System.gc();
        //PARA OBTENER LAS COMUNIDADES
        HashSet<HashSet<String>> res = obtenerComunidades(cliques,percentatge);
        return res;
    }

    ///FUNCIONES OBTENER COMUNIDAD DESARROLLADAS POR ALEJANDRO:

    /**
     * Obtenemos el número de nodos comunes entre dos cliques
     * @param C1 clique 1.
     * @param  C2 clique 2.
     * @return el número de nodos que comparten en común esos dos cliques
     */
    private static int numNodosComunes(HashSet<String> C1, HashSet<String> C2){
        HashSet<String> CX = new HashSet<String>(C1); //Duplicamos para no machacar los datos
        CX.retainAll(C2); //Intersecci0n de los cliques para ver el numero de nodos en comun
        return CX.size(); //Devolvemos el tamaño
    }

    /**
     * Obtenemos todas las k-comunidades
     * <p>
     *     El algoritmo dado unos n k-cliques obtiene sus k-comunidades. La k de la comunidad es decidida, en fucnión
     *     de la k max y mínima de todos los cliques y el porcentaje de exigencia que piden. En el caso de que un clique
     *     no cumpla la condición de ser comunidad (al ser su k<kComunidad) se fuerza a que sea una comunidad individual.
     * </p>
     * @param cliques una array con todos los cliques que se van a buscar las comunidades
     * @param percentatge como de estricto será el algoritmo al buscar las comunidades en esos cliques
     * @return todas las comunidades encontradas según el percentatge de estricto y los cliques dados
     */
    private static HashSet<HashSet<String>> obtenerComunidades(ArrayList<HashSet<String>> cliques,Integer percentatge){
        int n = cliques.size();
        //Obtenemos nuestra K para las comunidades
        int k = (int) Math.round(calcularMediaEstricta(mediaK,cliques.get(0).size(),cliques.get(n-1).size(),percentatge));
        int overMat[][] = new int[n][n]; //La matriz de NxN cliques inicializada
        //Rellenamos la tabla de overlaping (tenemos el número de nodos en común) y generamos a la vez la binaria (1=forman comunidad los cliques, 0=no)
        for(int i=0;i<n;i++){
            for (int j = i; j < n; j++) { //Miramos solo diagonal superior solo
                if(i==j) { //Obtenemos el número de nodos que tiene el clique, por la matriz M[i][i]=clique[i](intersección)clique[i]=clique[i].size
                    overMat[i][j] = cliques.get(i).size();
                    if(overMat[i][j]>=k) overMat[i][j] = 1;
                    else overMat[i][j] = 0;
                }
                else { //SI es diferente vomparamos los nodos comunues entre esos dos cliques
                    overMat[i][j] = numNodosComunes(cliques.get(i),cliques.get(j));
                    if(overMat[i][j] >= (k-1)) overMat[i][j] = 1;
                    else overMat[i][j] = 0;
                }
            }
        }
        //int checked[] = new int[n]; //0 si no han sido revisada la fila
        List<Set<Integer>> comProv = new ArrayList<Set<Integer>>();
        int checked[] = new int[n]; //0 si no han sido revisados
        Set<Integer> com;
        //Obtenemos las comunidades una por fila
        for(int i =0;i<n;i++){
            //Una fila 1 comunidad provisional si no ha sido ya chekeada/procesada, una fila es chekeada si sus cliques adyacentes han sido procesados
            if(checked[i]==0) {
                com = new HashSet<Integer>(); //La comunidad provisional
                //Ahora para i hemos de obtener todas sus cliques adjacentes, por tanto exploramos
                com.addAll(obtenerComunidades(overMat, i,checked)); //Añadimos todas las comunidades de i ya hemos explorado también todas de las que son adyacente, así tenemos los nº de cliques adyacentes entre ellos
                checked[i] = 1; //Acabamos de procesar i, entonces ya la marcamos
                comProv.add(com);//La añadimos
            }
            //Si ya ha sido procesada no nos interesa examinarla porque ya ha sido añadida junto a otra comunidad bien por la función recursiva o en este mismo bucle
        }
        //en comProv tenemos todas las comunidades, pero solo su número de clique no los nodos
        //Voy a fusionar todos los nodos de las comunidades que tengo en comPro
        HashSet<HashSet<String>> comunidades = new HashSet<>();
        for(int i = 0; i<comProv.size();i++) {
            if (comProv.get(i).size() > 0) {
                HashSet<String> aux = new HashSet<String>();
                for (int x : comProv.get(i)) {
                    aux.addAll(cliques.get(x));
                }
                comunidades.add(aux);
            }
        }
        //Para el caso de todos los cliques que no cumplan la condución de K-Comunidad para el proposito de este ejercicio los transformo en comunidades asiladas
        for(int i=0;i<n;i++){
            if(overMat[i][i]==0){
                //Son los cliques que no cumplen el requisito de K-comunidad por tanto "fuerzo" a que sean comunidades aisladas
                comunidades.add(cliques.get(i)); //Creamos una nueva comunidad para todos los nodos del clique for ever alone
            }
        }
        return comunidades;
    }

    /**
     * Obtenemos todas las comunidades adyacentes a la fila dada.
     * @param m overlaping matrix binaria.
     * @param  i fila que va a ser analizada.
     * @return todos los cliques que son adyacentes (cumpliendo la definición de k-comunidad en su overlaping matrix binaria).
     */
    private static HashSet<Integer> obtenerComunidades(int m[][], int i,int c[]){
        HashSet<Integer> cs = new HashSet<>();
        cs.add(i); //Si hemos llegado aquí m[i][i]=1, ya que existe m[x][i]=1 por tanto [i][i] valdrá 1
        if(c[i]==1) return cs;; //Condición de romper la recursividad si ya ha sido examinada esta fila salimos
        for(int j=i+1; j<m.length;j++){
            if(m[i][j]==1) {
                if(c[j]==1){
                    //Ya han evaluado la fila j pero como tiene 1 en overmat están relacionados
                    cs.add(j);
                }
                if(c[j]==0) {
                    //Como la fila no ha sido evaluada vamso a evaluarla
                    HashSet<Integer> res = obtenerComunidades(m, j, c);
                    cs.addAll(res); //Obtenemos todos las comunidades que comparte j {su fila}, la procesamos integra y acumulamos
                    c[j] = 1; //Hemos obtenido todas las comunidades de j por tanto la marcamos como evaluada la fila
                }
            }
        }
        return cs;
    }

    /**
     * Te permite calcular el valor de la media en función de como de estricto se pida que sea
     * @param media La media de un conjunto de valores C
     * @param max El valor máximo presente en el conjunto C
     * @param min El valor mínimo presente en el conjunto C
     * @param estricto Como de estricto se pida que sea esa "media" (si muy estricto el valor será proximo al double max y si lo contrario proximo a min)
     * @return La media en función de lo estricto que se pida que se sea. Consideramos que estrico = 50% es la propia media
     */
    private static double calcularMediaEstricta(double media, double max, double min, int estricto){
        //Partimos de la base de que la media ya es el 50% de estricto y queremos obtener una nueva media en función de lo estricto que pidan
        if(estricto == 50){
            //Si nos piden el 50% de estricto ya es la media
            return media;
        }
        if(estricto > 50){
            //Si >50, implica que ahora media será el valor más bajo y el máximo max, por tanto aproximammos una nueva media para el estricto
            double porcentaje = (double) 1-((estricto-50)*2/100.00);
            double i = max-((max-media)*porcentaje);
            return i;
        }
        if (estricto < 50){
            //Si <50, implica que ahora media será el valor maximo y el min el mínimo, por tanto aproximammos una nueva media para el estricto
            double porcentaje = (double) 1-((estricto/100.00)*2);
            return media-((media-min)*(porcentaje));
        }
        return 0;
    }

}

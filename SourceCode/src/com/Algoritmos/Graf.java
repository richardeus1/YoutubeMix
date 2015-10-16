package com.Algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * La classe Graf implementa un graf ponderat no dirigit on els nodes són Strings.
 * @author Yoel Cabo
 *
 */

public class Graf {
	protected Map<String,Integer> Diccionari;
	protected Map<Integer,String> DiccionariInvers;
	protected LlistaAdjacencia llista;
	
	protected class Aresta {
		private String node1;
		private String node2;
		private Double pes;
		private Aresta(String node1, String node2, Double pes) {
			this.node1 = node1;
			this.node2 = node2;
			this.pes = pes;
		}
		public String getNode1() {
			return node1;
		}
		private void setNode1(String node1) {
			this.node1 = node1;
		}
		public String getNode2() {
			return node2;
		}
		private void setNode2(String node2) {
			this.node2 = node2;
		}
		public Double getPes() {
			return pes;
		}
		private void setPes(Double pes) {
			this.pes = pes;
		}
	}
	protected class LlistaAdjacencia {
		private ArrayList< HashMap<Integer,Double> > lista;
		/**
		 * Creadora per defecte.
		 */
		public LlistaAdjacencia() {
			lista = new ArrayList< HashMap<Integer,Double> >();
		}
		
		/**
		 * Copiadora de llista de adjacències
		 * @param l llista a copiar
		 */
		public LlistaAdjacencia(LlistaAdjacencia l) {
			lista = new ArrayList< HashMap<Integer,Double> >(l.lista.size());
			for (int i = 0; i < l.lista.size();++i) {
				lista.add(new HashMap<Integer,Double>(l.lista.get(i)));
			}
		}
		
		/**
		 * Comprova si existeix adjacencia entre i y j
		 * @param i
		 * @param j
		 * @return true si existeix, sino false
		 */
		public Boolean exists(Integer i, Integer j) {
			return lista.get(i).containsKey(j);
		}
		
		/**
		 * Consultora del pes entre i i j
		 * @param i
		 * @param j
		 * @return pes entre i i j, si no hi ha aresta és 0
		 */
		public Double get(Integer i, Integer j) {
			if(exists(i,j)) return lista.get(i).get(j); 
			return 0.0;
		}
		
		/**
		 * Modificadora de pes
		 * @param i node
		 * @param j node
		 * @param value pes
		 */
		public void set(Integer i, Integer j, Double value) {
				lista.get(i).put(j, value);
				lista.get(j).put(i, value);
		}
		
		/**
		 * Modificadora de pes dirigit, de i a j
		 * @param i
		 * @param j
		 * @param value
		 */
		public void setDirected(Integer i, Integer j, Double value) {
			lista.get(i).put(j, value);
		}
		
		/**
		 * Elimina el node amb index index
		 * @param index
		 */
		public void remove(Integer index) {
			for (Integer i : lista.get(index).keySet()) {
				lista.get(i).remove(index);
			}
			lista.remove((int) index);
		}
		
		/**
		 * consultora de la mida de la llista
		 * @return mida de la llista
		 */
		public int size() {
			return lista.size();
		}
		
		/**
		 * Afegeix un nou node.
		 * @return index del node
		 */
		public Integer add() {
			lista.add(new HashMap<Integer,Double>());
			return llista.size()-1;
		}
		
		/**
		 * Remove de adyacencia.
		 * Elimina la adyacencia entre i y j.
		 * @param i
		 * @param j
		 */
		public void remove(Integer i, Integer j) {
			lista.get(i).remove(j);
			lista.get(j).remove(i);
		}
		
		/**
		 * Consultora de adyacencia
		 * @param i
		 * @return Parejas de indice del nodo - Peso adyacentes.
		 */
		public HashMap<Integer,Double> adjacents(Integer i) {
			return lista.get(i);
		}
		
	}
	/**
	 * Creadora per defecte.
	 */
	public Graf() {
		Diccionari = new HashMap<String,Integer>();
		DiccionariInvers = new HashMap<Integer,String>();
		llista = new LlistaAdjacencia();
	}
	
	/**
	 * Creadora a partir de HashSet. Crea un Graf que té per nodes el contingut del HashSet.
	 * @param NodesInicials Nodes del Graf que es crea.
	 */
	public Graf(HashSet<String> NodesInicials) {
		this();
		for (String Node : NodesInicials) {
			this.addNode(Node);
		}
	}
	
	/**
	 * Creadora per còpia a partir d'un Graf.
	 * @param G Graf que es copiarà.
	 */
	public Graf(Graf G) {
		Diccionari = new HashMap<String,Integer>(G.Diccionari);
		DiccionariInvers = new HashMap<Integer,String>(G.DiccionariInvers);
		llista = new LlistaAdjacencia(G.llista);
	}
	
	/**
	 * 
	 * @return Nombre de nodes del Graf.
	 */
	public Integer size() {
		return llista.size();
	}
	
	/**
	 * 
	 * @return Conjunt de Nodes del Graf
	 */
	public HashSet<String> getNodes() {
		return new HashSet<String>(Diccionari.keySet());
	}
	
	/**
	 * Afegeix un Node al Graf, sense cap aresta adjacent.
	 * @param id Node a afegir.
	 * @return true si s'ha afegit, false si ja existia.
	 */
	public Boolean addNode(String id) {
		if (existeixNode(id)) return false;
		Integer Posicio = llista.add();
		Diccionari.put(id,Posicio);
		DiccionariInvers.put(Posicio,id);
		return true;
	}
	
	/**
	 * Esborra un Node del Graf i totes les seves Arestes adjacents.
	 * @param id Node a esborrar.
	 * @return false si no existia, true si s'ha esborrat
	 */
	public Boolean removeNode(String id) {
		if (!existeixNode(id)) return false;
		Integer Posicio = Diccionari.get(id);
		Integer Size = llista.size();
		llista.remove(Posicio);
		Diccionari.remove(id);
		for (Integer i = Posicio; i < Size-1; ++i) {//TODO Intentar hacerlo más eficiente
			String iString = DiccionariInvers.get(i+1);
			Diccionari.put(iString,i);
			DiccionariInvers.put(i, iString);
		}
		
		DiccionariInvers.remove(Size-1);
		return true;
	}
	
	/**
	 * 
	 * @param id Node a comprovar la existència.
	 * @return true si existeix, false altrament.
	 */
	public Boolean existeixNode(String id) {
		return Diccionari.containsKey(id);
	}
	
	/**
	 * Afegeix una aresta d'un pes determinat entre dos nodes.
	 * @param a Un dels Nodes que connecta l'aresta.
	 * @param b L'altre dels Nodes que connecta l'aresta.
	 * @param Pes Pes de l'aresta entre a i b. Ha de ser >= 0.
	 * @return false si l'aresta ja existia o no existeix un dels dos nodes o el pes no �s correcte, true altrament.
	 */
	public Boolean addAresta(String a, String b, Double Pes) {
		if (!Diccionari.containsKey(a) || !Diccionari.containsKey(b) || existeixAresta(a,b) || Pes < 0) return false;
		llista.set(Diccionari.get(a),Diccionari.get(b),Pes);
		llista.set(Diccionari.get(b),Diccionari.get(a),Pes);
		return true;
	}
	
	/**
	 * Elimina una aresta entre dos Nodes del Graf.
	 * @param a Un dels Nodes que connecta l'aresta.
	 * @param b L'altre dels Nodes que connecta l'aresta.
	 * @return false si l'aresta ja no existia, true altrament. 
	 */
	public Boolean removeAresta(String a, String b) {
		if (!existeixAresta(a,b)) return false;
		llista.remove(Diccionari.get(a),Diccionari.get(b));
		return true;
	}
	
	/**
	 * Consulta si existeix una aresta entre dos Nodes del Graf.
	 * @param a Un dels Nodes que connecta l'aresta.
	 * @param b L'altre dels Nodes que connecta l'aresta.
	 * @return true si existeix, false si no existeix.
	 */
	public Boolean existeixAresta(String a, String b) {
		if (!Diccionari.containsKey(a) || !Diccionari.containsKey(b)) return false;
		if (llista.get(Diccionari.get(a), Diccionari.get(b)) <= 0.0) return false;
		return true;
	}
	
	/**
	 * Donada una aresta existent modifica el seu pes.
	 * @param a Un dels Nodes que connecta l'aresta.
	 * @param b L'altre dels Nodes que connecta l'aresta.
	 * @param Pes Pes de l'aresta entre a i b. Ha de ser >= 0.
	 * @return false si l'aresta no existia o si el Pes �s incorrecte, true altrament.
	 */
	public Boolean setPes(String a, String b, Double Pes) {
		if (!existeixAresta(a,b) || Pes < 0) return false;
		llista.set(Diccionari.get(a),Diccionari.get(b),Pes);
		return true;
	}
	
	/**
	 * Donada una aresta existent, retorna el seu pes.
	 * @param a Un dels Nodes que connecta l'aresta.
	 * @param b L'altre dels Nodes que connecta l'aresta.
	 * @return Pes de l'aresta si existeix, -1 si no existeix.
	 */
	public Double getPes(String a, String b) {
		if (!existeixAresta(a,b)) return -1.0;
		return llista.get(Diccionari.get(a),Diccionari.get(b));
	}
	
	/**
	 * Donat un Node, retorna els Nodes adjacents a aquest.
	 * @param id Node
	 * @return Conjunt de nodes adjacent a id. Si id no existeix, el conjunt és buit.
	 */
	public HashSet<String> getAdjacents(String id) {
		HashSet<String> Cjt = new HashSet<String>();
		if(!existeixNode(id)) return Cjt;
		Integer Posicio = Diccionari.get(id);
		for(Integer index : llista.adjacents(Posicio).keySet()) {
			Cjt.add(DiccionariInvers.get(index));
		}
		return Cjt;
	}
		
	public HashSet<Aresta> getArestes() {
		HashSet<Aresta> ret = new HashSet<Aresta>();
		for (int i = 0; i < llista.size(); ++i) {
			String node1 = DiccionariInvers.get(i);
			for (Integer j : llista.adjacents(i).keySet()) {
				if (i <= j) ret.add(new Aresta(node1, DiccionariInvers.get(j), llista.get(i, j)));
			}
		}
		
		return ret;
		
	}
}
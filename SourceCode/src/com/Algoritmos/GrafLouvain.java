package com.Algoritmos;

import java.util.HashSet;
import java.util.Iterator;

public class GrafLouvain extends Graf {
	
	private Double sumaFila(Integer i) {
		Double sum = 0.0;
		for (Double pes : llista.adjacents(i).values()) {
			sum += pes;
		}
		return sum;
	}
	
	/*private Double sumaColumna(Integer j) {
		Double sum = 0.0;
		for (Integer i = 0; i < Matriu.get(0).size(); ++i) {
			sum += Matriu.get(i).get(j);
		}
		return sum;
	}*/

	/**
	 * Creadora per defecte.
	 */
	public GrafLouvain() {
		super();
	}
	
	/**
	 * Creadora per còpia a partir d'un Graf.
	 * @param g Graf que es copiarà.
	 */
	public GrafLouvain(Graf g) {
		super(g);
	}
	
	/**
	 * Creadora a partir de HashSet. Crea un GrafLouvain que t� per nodes el contingut del HashSet.
	 * @param NodesInicials Nodes del Graf que es crea.
	 */
	public GrafLouvain(HashSet<String> NodesInicials) {
		super(NodesInicials);
	}
	
	/**
	 * 
	 * @return Suma dels Pesos de totes les arestes del Graf.
	 */
	public Double sumaPesos() {
		Double sum = 0.0;
		for (Integer i = 0; i < llista.size(); ++i) {
			sum += sumaFila(i);
		}
		if (llista.size() == 0) return -1.0;
		return sum/2;
	}
	
	/**
	 * 
	 * @param Comunitat Conjunt de Nodes del Graf.
	 * @return Suma dels pesos interns de la Comunitat.
	 */
	public Double sumaPesos(HashSet<String> Comunitat) {
		if (Comunitat.isEmpty()) return -1.0;
		Double sum = 0.0;
		for(String node: Comunitat) {
			Double sumi = sumaPesosAdjacents(node, Comunitat);
			if (sumi > 0) sum += sumi;
		}
		return sum;
	}
	
	/**
	 * 	
	 * @param id Node del Graf
	 * @return Suma dels pesos de les Arestes adjacents al Node. -1 si no existeix.
	 */
	public Double sumaPesosAdjacents(String id) {
		if (!existeixNode(id)) return -1.0;
		Integer Posicio = Diccionari.get(id);
		return sumaFila(Posicio);
	}
	
	/**
	 * 
	 * @param id Node
	 * @param Comunitat Conjunt de Nodes.
	 * @return Suma dels pesos de les Arestes entre el Node i la Comunitat. -1 si el node no existeix o la comunitat és buida.
	 */
	public Double sumaPesosAdjacents(String id,HashSet<String> Comunitat) {
		if (!existeixNode(id)) return -1.0;
		if (Comunitat.isEmpty()) return -1.0;
		Double sum = 0.0;
		Integer Posicio = Diccionari.get(id);
		for (String nodeC : Comunitat) {
			sum += llista.get(Posicio,Diccionari.get(nodeC));
		}
		return sum;
	}
	
	/**
	 * 
	 * @param C1 Comunitat 1
	 * @param C2 Comunitat 1
	 * @return Suma dels pesos de les Arestes entre les Comunitats, que han de ser disjuntes. -1 si el node no existeix o la comunitat és buida.
	 */
	public Double sumaPesosAdjacents(HashSet<String> C1, HashSet<String> C2) {
		if (C1.isEmpty() || C2.isEmpty()) return -1.0;
		if (C1 == C2) return sumaPesos(C1);
		Double sum = 0.0;

		for (String nodeC1 : C1) {
			Double sumi = sumaPesosAdjacents(nodeC1, C2);
			if (sumi > 0) sum += sumi;
		}
		return sum;
	}
	
	/**
	 * 
	 * @param Comunitat
	 * @return Suma dels pesos de les arestes adjacents a Nodes de la Comunitat. -1 si és buida.
	 */
	public Double sumaPesosAdjacentsInclusiva(HashSet<String> Comunitat) {
		if (Comunitat.isEmpty()) return -1.0;
		Double sum = 0.0;
		Iterator<String> it = Comunitat.iterator();
		while(it.hasNext()) {
			Double sumi = sumaPesosAdjacents(it.next());
			if (sumi > 0) sum += sumi;
		}
		return sum;
	}
	/**
	 * 
	 * @param Comunitat
	 * @return Suma dels pesos de les arestes adjacents a Nodes de la Comunitat, excloent els pesos de les arestes internes.
	 */
	public Double sumaPesosAdjacentsExclusiva(HashSet<String> Comunitat) { 
		if (Comunitat.isEmpty()) return -1.0;
		return sumaPesosAdjacentsInclusiva(Comunitat) - sumaPesos(Comunitat);
	}

	/*public void print(Sortida sC) {
		sC.Write("Nodes");
		for (String Node : Diccionari.keySet()) sC.Write(Node);
		for (Integer i = 0; i < Matriu.size();++i) {
			for (Integer j = i; j < Matriu.size(); ++j) {
				sC.Write(DiccionariInvers.get(i)+ " " + DiccionariInvers.get(j) + " " + Matriu.get(i).get(j).toString());

			}
		}
	}*/
	
	
	
	
	
	
}
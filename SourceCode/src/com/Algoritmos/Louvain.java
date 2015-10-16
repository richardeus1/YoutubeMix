package com.Algoritmos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class Louvain {
	private static GrafLouvain G;
	private static HashSet<HashSet<String> > Comunidades;
	private static HashMap<String,HashSet<String>> Pertenencia;
	//Potser cal un diccionari<Node, Punter a Comunitat>. Per quan volem saber a quina comunitat pertany un cert node en un cert moment.
	//O potser no :)
	private static Vector<HashMap<String,HashSet<String> > > Historia;
	
	
	
	private static void init(GrafLouvain G)  {
		Historia = new Vector<HashMap<String,HashSet<String> > >();
		Comunidades = new HashSet< HashSet<String> >();
		HashSet<String> Nodes = G.getNodes();
		HashMap<String,HashSet<String> > Mapa = new HashMap<String, HashSet<String>>();
		for (String node : Nodes) {
			HashSet<String> primera = new HashSet<String>();
			String act = new String(node);
			primera.add(act);
			Comunidades.add(primera);
			Mapa.put(act, primera);
			
		}
		Historia.addElement(Mapa);
		calcPertenencia();
	}
	
	private static void calcPertenencia() {
		Pertenencia = new HashMap<String,HashSet<String>>();
		for (HashSet<String> Comunidad : Comunidades) {
			for (String Node : Comunidad) {
				Pertenencia.put(Node, Comunidad);
			}
		}
		
	}

	private static void agregaGraf() {
		//System.out.println("Agregando Grafo "+(System.currentTimeMillis()-t)+"ms");
		GrafLouvain NouGraf = new GrafLouvain();
		Iterator<HashSet<String> >  iHS = Comunidades.iterator();
		HashMap<String, HashSet<String>> Mapa = new HashMap<String, HashSet<String>>();
		HashMap<HashSet<String>, String> auxMap = new HashMap<HashSet<String>, String>();
		for (Integer i = 0; iHS.hasNext(); ++i) {
			HashSet<String> Comunidad = iHS.next(); 
			if (Comunidad.size() > 0) {
				NouGraf.addNode(i.toString());
				Mapa.put(i.toString(), Comunidad);
				auxMap.put(Comunidad, i.toString());
			}
		}
		//System.out.println("Nodos decididos, son "+NouGraf.getNodes().size()+" "+(System.currentTimeMillis()-t)+"ms");
		Historia.addElement(Mapa); //Actualitzem la Història de l'algorisme amb un nou pas
		HashSet<String> Nodes = new HashSet<String> (Historia.get(Historia.size()-1).keySet()); //Agafa els noms tots els noms que se li ha donat als diversos nodes agregats de comunitats.
		Comunidades = HSStoHSHSS(Nodes); //Reiniciem les comunitats a comunitats individuals
		//System.out.println("CalculandoPesos... "+(System.currentTimeMillis()-t)+"ms");
		//int i = 0;
		//int j = 0;
		for (Graf.Aresta a :  G.getArestes()) {
			String c1 = auxMap.get(getComunitat(a.getNode1()));
			String c2 = auxMap.get(getComunitat(a.getNode2()));
			if (!NouGraf.existeixAresta(c1, c2)) NouGraf.addAresta(c1, c2, a.getPes());
			else NouGraf.setPes(c1, c2, NouGraf.getPes(c1, c2)+a.getPes());
			if(c1.equals(c2) && !a.getNode1().equals(a.getNode2())) NouGraf.setPes(c1, c2, NouGraf.getPes(c1, c2)+a.getPes());
		}
		G = NouGraf; //Graf actualitzat
		//System.out.println("Calculando Pertenencias... "+(System.currentTimeMillis()-t)+"ms");
		calcPertenencia();
	}
	

	private static boolean IncrementModularity() {
		HashSet<String> Nodes = G.getNodes();
		Double m = G.sumaPesos();
		Boolean optimitzada = true;
		for (String Node : Nodes) {
			
			Boolean sehaincrementado = false;
			HashSet<String> actual = getComunitat(Node);
			HashSet<String> maxCom = actual;
			Double max = 0.0;
			
			Double pesReflexiu = 0.0;
			if(G.existeixAresta(Node, Node)) {
				pesReflexiu = G.getPes(Node, Node);
			}
			Double grauNode = G.sumaPesosAdjacents(Node);
			Double pesosAdjacentsComunitat = G.sumaPesosAdjacentsInclusiva(actual);
			Double pesosNodeComunitat = G.sumaPesosAdjacents(Node, actual);
			for(String nodeAdjacent : G.getAdjacents(Node)) {
				HashSet<String> aTractar = getComunitat(nodeAdjacent);
				if (actual == aTractar) continue;
				Double Inc = ModularityInc(Node, m, pesReflexiu, pesosAdjacentsComunitat, pesosNodeComunitat, grauNode, aTractar);
				//sC.Write(Inc);
				if (Inc > max) {
					//sC.Write(Inc);
					max = Inc;
					maxCom = aTractar;
					sehaincrementado = true;
					
				}
			}
			if (sehaincrementado) {  
				actual.remove(Node);
				maxCom.add(Node);
				Pertenencia.put(Node, maxCom);
				optimitzada = false;
				if (actual.size() == 0) Comunidades.remove(actual);
			}
		}
		return !optimitzada;
	}
	
	

private static Double ModularityInc(String node, Double m,
		Double pesReflexiu, Double sumapesosAdjacentsOrigen, Double sumapesosNodeOrigen, Double grauNode, HashSet<String> destino) {
		
		Double res = (G.sumaPesosAdjacents(node, destino) - sumapesosNodeOrigen+pesReflexiu)*2; 
		res -= (G.sumaPesosAdjacentsInclusiva(destino)-sumapesosAdjacentsOrigen + grauNode)*grauNode/m;
		res /= 2*m;	
		return res;
	}

	private static HashSet<String> getComunitat(String node) {
		return Pertenencia.get(node); 
		
	}

	private static HashSet<HashSet<String>> retorna(Integer percentatge) {
		Integer Total = Historia.size();
		Integer Interesante = (100-percentatge)*Total/100;
		if(Interesante >= Total) Interesante = Total-1;
		if (Interesante < 0 ) Interesante = 0;
		HashSet<String> Generacion = new HashSet<String>(Historia.get(Interesante).keySet());
		HashSet<HashSet<String> >  hs = HSStoHSHSS(Generacion);
		HashSet<HashSet<String>> ret = new HashSet<HashSet<String>>();
		for (HashSet<String> comunitat : hs) {
			ret.add(historiador(Interesante, comunitat));
		}
		return ret;
	}
	
	
	
	private static HashSet<String> historiador(Integer Posicion, HashSet<String> Com) {
		HashSet<String> Merged = new HashSet<String>();
		if (Posicion == 0) {
		Merged.addAll(Com);	
		}
		else {
			Iterator<String> It = Com.iterator();
			while (It.hasNext()) {
				Merged.addAll(historiador(Posicion-1, Historia.get(Posicion).get(It.next())));
			}
		}
		return Merged;	
	}

	private static HashSet<HashSet<String>> HSStoHSHSS(HashSet<String> seed) {
		HashSet< HashSet<String> > Plant = new HashSet< HashSet<String> >();
		Iterator<String> iS = seed.iterator();
		while(iS.hasNext()) {
			HashSet<String> unit = new HashSet<String>();
			String act = new String(iS.next());
			unit.add(act);
			Plant.add(unit);
		}
		return Plant;
	}
	/**
	 * Executa l'algorisme Louvain fent el percentatge% dels passos que faria l'algorisme si no se l'aturés.
	 * @param Gr Graf sobre el que s'executarà l'algorisme.
	 * @param percentatge 
	 * @return Conjunt de Comunitats resultant de l'execució.
	 */
	public static HashSet< HashSet<String> > executa(Graf Gr, Integer percentatge) {
		G = new GrafLouvain(Gr);
		init(G); 
		boolean modificacion = true;
		Integer i =0;
		long t = System.currentTimeMillis();
		while(Comunidades.size() > 1 && modificacion) {
			modificacion = false;
			++i;
			//System.out.println("Iteracion "+i.toString()+", "+Comunidades.size()+" comunidades. "+(System.currentTimeMillis()-t)+"ms");
			//Integer j = 0;
			while(IncrementModularity()) {
				modificacion = true; 
				//++j;
				//System.out.println("Iteracion "+i.toString()+" Incrementro "+j.toString()+". "+Comunidades.size()+" comunidades. "+(System.currentTimeMillis()-t)+"ms");
				
			}
			//System.out.println("Iteracion "+i.toString()+" finalizada. "+(System.currentTimeMillis()-t)+"ms");
			agregaGraf();
			
		}
		//System.out.println("Final, "+Comunidades.size()+" comunidades. "+(System.currentTimeMillis()-t)+"ms");
		//G.print(sC);
		return retorna(percentatge);
		
	}

	
}
package it.univr.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Un grafo i cui nodi contengono valori di tipo E.
 * 
 * @param <E> il tipo degli elementi del grafo
 */
public final class Graph<E> implements Iterable<Graph<E>.Node> {

	/**
	 * I nodi di questo grafo.
	 */
    private final List<Node> nodes = new ArrayList<>();
    

	/**
	 * La definizione di un nodo di questo grafo.
	 */
	public class Node {

		/**
		 * Il valore dentro il nodo.
		 */
		protected final E e;

		/**
		 * La stella uscente del nodo, cioe' i nodi a cui e' legato.
		 */
		private final List<Node> star = new ArrayList<>();

		/**
		 * Crea un nodo che contiene un valore.
		 * 
		 * @param e il valore del nodo
		 */
		private Node(E e) {
			this.e = e;
		}

		/**
		 * Restituisce il grafo a cui appartiene questo nodo.
		 * 
		 * @return il grafo
		 */
		private Graph<E> graph() {
			return Graph.this;
		}

		/**
		 * Lega questo nodo a other. Se erano gia' legati, non fa nulla.
		 * 
		 * @param other il nodo a cui va legato questo nodo
		 * @throws IllegalArgumentException se other appartiene a un grafo diverso da quello di this
		 */
		public final void linkTo(Node other) {
			if(this.star.contains(other)) {return;}
			if(other.graph() != this.graph()) {
				throw new IllegalArgumentException();
			}
		    this.star.add(other);
		}

		/**
		 * Restituisce una stringa che descrive questo nodo, del tipo:
		 * name [label="LLL"]
		 * dove LLL e' il toString() del valore di questo nodo.
		 * 
		 * @param names i nomi da usare per i nodi. Serve per capire che name usare per questo nodo nel risultato
		 * @return la stringa risultante
		 */
		public String toString(Map<Node, String> names) {
			
		    return String.format("%s [label=\"%s\"]", names.get(this), this.e.toString());
		}
	}

	/**
	 * Un nodo rosso. E' uguale a un Node, ma il suo toString specifica di
	 * usare il colore rosso.
	 */
	private class RedNode extends Node {

		/**
		 * Costruisce un nodo rosso che contiene il valore indicato.
		 * 
		 * @param e il valore del nodo
		 */
		private RedNode(E e) {
			super(e);
		}

		/**
		 * Uguale a quello della superclasse, ma specifica in piu' le opzioni
		 * style="filled", fillcolor="red"
		 */
		@Override
		public String toString(Map<Node, String> names) {
		    return String.format("%s [label=\"%s\"]", names.get(this), this.e.toString()).substring(0, String.format("%s [label=\"%s\"]", names.get(this), this.e.toString()).length()-1) + ", style=\"filled\", fillcolor=\"red\"]";
		}
	}

	@Override
	public Iterator<Graph<E>.Node> iterator() {
	    return nodes.iterator(); 
	}

	/**
	 * Aggiunge a questo grafo un nodo che contiene il valore indicato.
	 * 
	 * @param e il valore del nodo da aggiungere
	 * @return il nodo che e' stato aggiunto
	 */
	public Node add(E e) {
		Node newNode = new Node(e);
		nodes.add(newNode);
	    return newNode;
	}

	/**
	 * Aggiunge a questo grafo un nodo rosso che contiene il valore indicato.
	 * 
	 * @param e il valore del nodo da aggiungere
	 * @return il nodo che e' stato aggiunto
	 */
	public Node addRed(E e) {
		RedNode newRedNode = new RedNode(e);
		nodes.add(newRedNode);
	    return newRedNode;
	}

	/**
	 * Restituisce una stringa che descrive questo grafo in formato dot.
	 */
	@Override
	public String toString() {
		String result = "diagraph{\n";
		Map<Node, String> names = new HashMap<>();
		
		int counter = 0;
		for (Node node: this)
			names.put(node, "node" + counter++);
		
		
	    for(Node node: this) {
	    	result += "  " + node.toString(names) + "\n";
	    	for(Node linkNode: node.star)
	    		result +=  "  " + String.format("%s -> %s\n",names.get(node), names.get(linkNode) );
	    	
	    }
	    return result;
	}
}

package it.univr.graph;

import it.univr.graph.Graph.Node;

public class Main {
	public static void main(String[] args) {
		// un grafo di stringhe

	    Graph<String> graphOfString = new Graph<>();
	    Node node0 = graphOfString.add("hello"); //hello
	    Node node1 = graphOfString.addRed("how"); //how
	    Node node2 = graphOfString.addRed("are"); //are
	    Node node3 =  graphOfString.add("you"); //you
	    Node node4 = graphOfString.add("hello"); //hello
	    
	    node0.linkTo(node0);
	    node0.linkTo(node2);
	    node0.linkTo(node4);
	    
	    node2.linkTo(node2);
	    node2.linkTo(node4);
	    
	    node4.linkTo(node0);
	    
	    System.out.println(graphOfString);

	    
	    
		// un grafo di interi

	    Graph<Integer> graphOfInteger = new Graph<>();
	     Node nodea = graphOfInteger.addRed(17);
	     Node nodeb = graphOfInteger.add(42);
	     nodea.linkTo(nodeb);
	     nodeb.linkTo(nodea);
	     
	     System.out.println(graphOfInteger);

		// un altro grafo di stringhe, con un unico nodo legato a un nodo del primo grafo

	     Graph<String> graphOfString2 = new Graph<>();
	     Node nodex = graphOfString2.add("hello");
	     nodex.linkTo(node3);
	}
}

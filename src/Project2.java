// IMPORT DELLE CLASSI CHE MI SERVONO IN QUESTA CLASSE
import java.util.ArrayList;
import java.util.List;

public class Project2 
{	
	/*
	 * Questo metodo è quello che richiede l'esrcizio2 dell'esame.
	 * 
	 * Il metodo statico (di classe e quindi puo' essere richiamato senza istanziare nessun oggetto di questa
	 * classe: ovvero senza fare new Project2()) 
	 * prende in input un grafo di tipo AdjMatrixEdgeWeightedDigraph e un numero reale k e 
	 * restituisce una lista di archi diretti (ovvero oggetti di tipo DirectedEdge) che
	 * corrispondono alle coppie di vertici il cui cammino nel grafo ha un peso complessivo maggiore di k.
	 * 
	 * Esempio:
	 * Supponiamo di avere un grafo con 3 vertici a,b,c,d.
	 * Supponiamo inoltre di avere i segunti archi tra i vertici:
	 * - a->b con peso 2.1
	 * - a->c con peso 4.8
	 * - c->c con peso 2.0
	 * - b->d con peso 2.7
	 * - d->c con peso 6.9
	 * - d->b con peso 1.0
	 * - d->a con peso 1.5
	 * 
	 * Applicando l'algoritmo di floydWarshal (alla riga #32)
	 * mi verrà restituito TUTTI I CAMMINI MINIMI PER OGNI COPPIA DI VERTICI:
	 * ovvero il cammino minimo all'interno del grafo dal vertice a se stesso, 
	 * il cammino minimo all'interno del grafo dal vertice al vertice b,
	 * il cammino minimo dal vertice a al vertice c,
	 * dal vertice a al vertice d,
	 * dal vertice b al vertice a,
	 * dal vertice b a ses stesso, 
	 * dal vertice b al vertice c,
	 * dal vertice b al vertice d, 
	 * dal vertice c al avertice a,
	 * .......e così via.
	 * 
	 * Ma io (o meglio l'esercizio 2 dell'esame non vuole questo, intendo TUTTI I CAMMINI MINIMI PER OGNI COPPIA DI VERTICI).
	 * Ms vuole TUTTI I CAMMINI MINIMI PER OGNI COPPIA DI VERTICI IL CUI CAMMINO COMPLESSIVO HA UN PESO MAGGIORE DI UN CERTO VALORE K DATO IN INPUT.
	 * */
	// ATTENZIONE QUESTO E' IL METODO CHE L'ESERCIZO 2 DELL'ESAME TI RICHIEDE DI IMPLEMENTARE
	public static List<DirectedEdge> pairVertexDistGreatK(AdjMatrixEdgeWeightedDigraph G, double K) // funzione prende in input un grafo e un numero reale
	{
		// struttura dati per la memorizzazione del risultato
		// ovvero TUTTI I CAMMINI MINIMI PER OGNI COPPIA DI VERTICI IL CUI CAMMINO COMPLESSIVO HA UN PESO MAGGIORE DI UN CERTO VALORE K DATO IN INPUT
		ArrayList<DirectedEdge> list = new ArrayList<>();
		
		// applico l'algoritmo di floydWarshal al grafo che passo come parametro di input
		// così ottengo TUTTI I CAMMINI MINIMI PER OGNI COPPIA DI VERTICI
		// QUESTO PRENDE TEMPO DI ESECUZIONE O(|V|^3)
		FloydWarshall fw = new FloydWarshall(G);	
		
		// PER OGNI VERTICE v DEL GRAFO (con v che varia per tutti i vertici)
		// QUESTO 2 CICLI ANNIDATI PRENDONO TEMPO DI ESECUZIONE O(|V|^2)
		for (int v = 0; v < G.V(); v++)
    	{
			// PER OGNI VERTICI w DEL GRAFO (con w che varia per tutti i vertici del grafo) 
        	for (int w = 0; w < G.V(); w++) 
        	{
        		// se peso complessivo da v a w e' più grande di questo k in input
        		// allora me lo salvo nella mia struttura dati che ho dichiarato all'inizio del metodo
        		// e che ho chiamato list
        		// altriment se così non fosse allora non lo salvo perche' vuol dire che il peso complessivo del cammino da e' minore del mio numero k
        		// e quindi non devo fare nulla (in quanto non lo devo memorizzare)
            	if (fw.dist(v, w) > K) 
            		list.add(new DirectedEdge(v, w, fw.dist(v, w)));
        	}
    	}
		
		// alla fine dei 2 cicli for ho esaminato tutte le coppie di veritici del grafo 
		// e avro' memorizzato solo quelli richiesti ( ovvero quelli che hanno cammino dall'arco v a w maggiore di k)
		// e quindi ritorno la lista al metodo chiamante.
		return list;
		
		// TEMPO DI ESECUZIONE DI QUESTO METODO NEL CASO PEGGIORE E' O(|V|^3) dato dall'applicazione di FLoydWarshall
	}
	
	// questo metodo main priva da linea di comando l'idea dell'algoritmo sopra
	// ma passando da linea di comando il numero di vertici del grafo, il numero di archi e il valore k.
	/*
	 * QUESTO NON E' NECESSARIO AI FINI DELL'ESAME MA E' UN QUALCOSA IN PIU' CHE HO FATTO IO PER VEDERE
	 * SE L'IDEA DEL METODO DI SOPRA ERA ESATTA.
	 * */
	public static void main(String[] args) 
	{
		double K = 0;
		int V= 0;
		int E=0;
		
		// se da linea di comando non ottengo i 3 valori che mi servono :
		//	- numero di vertici
		//	- numero di archi
		//	- valore k
		//	allora hai sbagliato a lanciare questo programma
		//
		if(args.length < 3)
		{
			System.out.println("missing  parameter of project2");
			System.out.println("insert number nodes, number edges, K parameter in command line");
			System.exit(0);;
		}
		
		// se arrivi ad eseguire questo codice qua sotto allora hai lanciato questo programma in modo corretto
		try
		{
			// PRENDO I 3 VALORI CHE MI SERVONO DA LINEA DI COMANDI e siccome sono stringhe li converto in intero e double
			V = Integer.parseInt(args[0]);  // prendo il numero di vertici e da stringa lo converto in intero
			E = Integer.parseInt(args[1]);	// prendo il numero di archi e da stringa lo converto in intero
			K = Double.parseDouble(args[2]); // prendo il numero real k e da stringa lo converto in double
		}
		catch(Exception e)
		{
			System.out.println("all parameters must be a number");
			System.exit(0);
		}
		
		// CREAZIONE DEL GRAFO TRAMITE L'INSTANZIAZIONE DELL'OGGETTO AdjMatrixEdgeWeightedDigraph
		// NOTA1: IL COSTRUTTORE DELLA CLASSE AdjMatrixEdgeWeightedDigraph VUOLE COME PARAMETRO PASSATI IN INPUT
		// IL NUMERO DI NODI (variabile V) E IL NUMERO DI ARCHI (variaile E).
		
		// PER MAGGIORE INFORMAZIONE SU COME FUNZIONA VAI ALLA CLASSE AdjMatrixEdgeWeightedDigraph
		// E VEDI IL COSTRUTTORE CON I 2 ARGOMENTI V ED E. 
		AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V, E);
        for (int i = 0; i < E; i++)
        {
        	// NOTA2: IL GRAFO COSTRUITO CONTERRA' V nodi ed E archi SCELTI IN MODO RANDOMICO E CON PESO
    		// DEGLI ARCHI ANC'ESSI RANDOMICI.
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * (StdRandom.uniform() - 0.15)) / 100.0;
            if (v == w) // loop must be non-negative
            	G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));	// INSERIMENTO ARCO v->w e peso |weight|
            else
            	G.addEdge(new DirectedEdge(v, w, weight));
        }

        // esecuzione dell'algoritmo di FloydWarshall
        FloydWarshall spt = new FloydWarshall(G);
        System.out.println("print Warshall\n_________________________________");
        
        printWarshal(G, spt); // chiamata meotodo statico che stampa info sull'esecuzione di FloydWarshall
        pairVertexpathDistGreatK(G, spt, K); // chiamata metodo statico
	}
	
	/*
	 * QUESTO NON E' NECESSARIO AI FINI DELL'ESAME MA E' UN QUALCOSA IN PIU' CHE HO FATTO IO PER VEDERE
	 * SE L'IDEA DEL METODO DI SOPRA ERA ESATTA.
	 * */
	private static void pairVertexpathDistGreatK(AdjMatrixEdgeWeightedDigraph G, FloydWarshall spt, double K)
	{
		for (int v = 0; v < G.V(); v++)
    	{
        	for (int w = 0; w < G.V(); w++) 
        	{
            	if (spt.dist(v, w) > K) 
            	StdOut.printf(v + "->" + w + "-_weight dist:" +  spt.dist(v, w)+ "\n");
        	}
    	}
	}
	
	/*
	 * QUESTO NON E' NECESSARIO AI FINI DELL'ESAME MA E' UN QUALCOSA IN PIU' CHE HO FATTO IO PER VEDERE
	 * SE L'IDEA DEL METODO DI SOPRA ERA ESATTA.
	 * */
	public static void  printWarshal(AdjMatrixEdgeWeightedDigraph G ,FloydWarshall spt)
	{
	 // print all-pairs shortest path distances
    StdOut.printf("  ");
    for (int v = 0; v < G.V(); v++)
    {
        StdOut.printf("%6d ", v);
    }
    StdOut.println();
    for (int v = 0; v < G.V(); v++) 
    {
        StdOut.printf("%3d: ", v);
        for (int w = 0; w < G.V(); w++) 
        {
            if (spt.hasPath(v, w)) StdOut.printf("%6.2f ", spt.dist(v, w));
            else StdOut.printf("  Inf ");
        }
        StdOut.println();
    }

    // print negative cycle
    if (spt.hasNegativeCycle()) 
    {
        StdOut.println("Negative cost cycle:");
        for (DirectedEdge e : spt.negativeCycle())
            StdOut.println(e);
        StdOut.println();
    }

    // print all-pairs shortest paths
    else 
    {
        for (int v = 0; v < G.V(); v++) 
        {
            for (int w = 0; w < G.V(); w++) 
            {
                if (spt.hasPath(v, w)) 
                {
                    StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                    for (DirectedEdge e : spt.path(v, w))
                        StdOut.print(e + "  ");
                    StdOut.println();
                }
                else 
                {
                    StdOut.printf("%d to %d no path\n", v, w);
                }
            }
        }
    	}
	}
}
import java.util.ArrayList;
import java.util.List;

public class Project3 
{
	public static List<List<DirectedEdge>> result()
	{
		List<List<DirectedEdge>> list = new ArrayList<>();
		
		AdjMatrixEdgeWeightedDigraph G = null;
		double K = 0;
		for(int V=25; V<=500; V=V+25)
		{
			G = new AdjMatrixEdgeWeightedDigraph(V, (int) Math.round(2*V*Math.log(Math.abs(V))));
			for(int v=0; v<G.V(); v++)
			{
				for(DirectedEdge e : G.adj(v))
					K = e.getWeight();
			}
			K = K/G.E();
			list.add(Project2.pairVertexDistGreatK(G, K));
		}
		return list;
	}
	
	
	public static void main(String args[])
	{
		AdjMatrixEdgeWeightedDigraph G = null;
		double K = 0;
		for(int V=25; V<500; V=V+25)
		{
			G = new AdjMatrixEdgeWeightedDigraph(V, (int) Math.round(2*V*Math.log(Math.abs(V))));
			for(int v=0; v<G.V(); v++)
			{
				for(DirectedEdge e : G.adj(v))
					K = e.getWeight();
			}
			K = K/G.E();
			List<DirectedEdge> list = Project2.pairVertexDistGreatK(G, K);
			System.out.println(list);
		}
	}
}

package aima.gui.demo.search;


import java.util.Iterator;

import java.util.List;
import java.util.Properties;


import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest2;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.util.math.*;


/**
 * @author Lucia Morales Rosa, 816906
 * 
 */
 
 
 public class EightPuzzlePrac2{
	
	public static void main(String[] args) {	
		//Inicializacion de strings
		String separacion = "----------------------------------------------------------------------------------";
		final String noHayRespuesta = "----";
		
		//Cabecera de la tabla
		cabeceraTabla(separacion);
		
		final int MIN_DEEP = 2;
		final int MAX_DEEP = 24;
		final int NUM_EXP = 100;
		final int MAX_IDS = 10;
		
		//int p_BFS, p_IDS, p_Ah1, p_Ah2;
		int n_BFS = 0, n_IDS = 0, n_Ah1 = 0, n_Ah2 = 0;
		double b_BFS, b_IDS, b_Ah1, b_Ah2;
		SearchAgent ag_BFS, ag_IDS, ag_Ah1, ag_Ah2;
		
		for(int i = MIN_DEEP; i <= MAX_DEEP; i++){ //Pasar por todas las profundidades
			for(int j = 0; j <NUM_EXP; j++){
				EightPuzzleBoard inicio = GenerateInitialEightPuzzleBoard.randomIni();
				EightPuzzleBoard objetivo = GenerateInitialEightPuzzleBoard.random(i, inicio);
				
				ag_BFS = getAgent(new BreadthFirstSearch(new GraphSearch()), inicio, objetivo);
				if(i == getPathcost(ag_BFS)){ //Comprueba que la profundidad sea la correcta
					n_BFS = n_BFS + getGeneratedNodes(ag_BFS);
					
					ag_Ah1 = getAgent(new AStarSearch(new GraphSearch(),new MisplacedTilleHeuristicFunction2()), inicio, objetivo);
					n_Ah1 = n_Ah1 + getGeneratedNodes(ag_Ah1);
					
					ag_Ah2 = getAgent(new AStarSearch(new GraphSearch(),new ManhattanHeuristicFunction2()), inicio, objetivo);
					n_Ah2 = n_Ah2 + getGeneratedNodes(ag_Ah2);
					
					if(i <= MAX_IDS){
						ag_IDS = getAgent(new IterativeDeepeningSearch(), inicio, objetivo);
						n_IDS = n_IDS + getGeneratedNodes(ag_IDS);
					}
				}
				else if(j > 0){
					j--;
				}	
			}
			
			Biseccion b = new Biseccion();
			b.setDepth(i);
			
			n_BFS /= 100;
			b.setGeneratedNodes(n_BFS);
			b_BFS = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10);
			
			n_Ah1 /= 100;
			b.setGeneratedNodes(n_Ah1);
			b_Ah1 = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10);
			
			n_Ah2 /= 100;
			b.setGeneratedNodes(n_Ah2);
			b_Ah2 = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10);
			
			if(i <= MAX_IDS) {
				n_IDS /= 100;
				b.setGeneratedNodes(n_IDS);
				b_IDS = b.metodoDeBiseccion(1.00000001, 4.0, 1E-10);
				/*mostrarResultados(i, n_BFS, n_IDS, n_Ah1, n_Ah2, b_BFS, b_IDS, b_Ah1, b_Ah2);
				 */
				System.out.format("||%3s|| %7s |%7s |%7s |%7s ||%7.2f |%7.2f |%7.2f |%7.2f ||\n",
						i, n_BFS, n_IDS, n_Ah1, n_Ah2, b_BFS, b_IDS, b_Ah1, b_Ah2);
			}
			else{
				System.out.format("||%3s|| %7s |%7s |%7s |%7s ||%7.2f |%7s |%7.2f |%7.2f ||\n",
						i, n_BFS, noHayRespuesta, n_Ah1, n_Ah2, b_BFS, noHayRespuesta, b_Ah1, b_Ah2);
			}
		}
	}	
	
	
	/*Devuelve el numero de nodos generados para el agente dado*/
	private static int getGeneratedNodes(SearchAgent agente){
		int ret;
		if (agente.getInstrumentation().getProperty("nodesGenerated")==null) {
			ret = 0;
		} else {
			ret = (int)Float.parseFloat(agente.getInstrumentation().getProperty("nodesGenerated"));
		}
		return ret;
	}

	/*Devuelve el pathcost para el agente dado*/
	private static int getPathcost(SearchAgent agente){
		int ret;
		String pathcost;
		pathcost = agente.getInstrumentation().getProperty("pathCost");
		if (pathcost != null) {
			ret = (int)Float.parseFloat(pathcost);
		} else {
			ret = 0;
		}
		return ret;
	}
	
	/*Devuelve el agente generado con los parametros introducidos*/
	private static SearchAgent getAgent(Search b, EightPuzzleBoard inicio, EightPuzzleBoard objetivo){
		Problem problema;
		SearchAgent agente = null;
		//Establecer estado objetivo
		EightPuzzleGoalTest2.setGoalState(objetivo);
		//Busqueda
		problema = new Problem(inicio, EightPuzzleFunctionFactory.getActionsFunction(),
								EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest2());
		try {
			agente = new SearchAgent(problema, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agente;
	}

	/*Imprime por pantalla la cabecera de la tabla*/
	private static void cabeceraTabla(String separacion) {
		System.out.format("%s\n",separacion);
		System.out.format("||   ||            Nodos Generados         ||                   b*              ||\n");
		System.out.format("%s\n",separacion);
		System.out.format("||  d||   BFS   |   IDS  | A*h(1) | A*h(2) ||   BFS  |   IDS  | A*h(1) | A*h(2) ||\n");
		System.out.format("%s\n",separacion);
		System.out.format("%s\n",separacion);
	}
	
}















package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @autor Morales Rosa, Lucia (816906)
 * 
 * Modificacion del fichero ManhattanHeuristicFunction original para la Practica_2
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard testObjetivo = (EightPuzzleBoard) EightPuzzleGoalTest2.goal;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locObjetivo = testObjetivo.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(locObjetivo, loc);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(XYLocation locObjetivo, XYLocation loc) {
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xposObjetivo = locObjetivo.getXCoOrdinate();
		int yposObjetivo = locObjetivo.getYCoOrdinate();
		
		return Math.abs(xpos - xposObjetivo) + Math.abs(ypos - yposObjetivo);
	}
}
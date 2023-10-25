package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * @author Ravi Mohan
 * @autor Morales Rosa, Lucia (816906)
 * 
 * Modificacion del fichero MisplacedTilleHeuristicFunction original para la Practica_2
 */
public class MisplacedTilleHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard testObjetivo = (EightPuzzleBoard) EightPuzzleGoalTest2.goal;
		return getNumberOfMisplacedTiles(board, testObjetivo);
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard board, EightPuzzleBoard testObjetivo) {
		int numberOfMisplacedTiles = 0;
		if (!(board.getLocationOf(0).equals(testObjetivo.getLocationOf(0)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(1).equals(testObjetivo.getLocationOf(1)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(2).equals(testObjetivo.getLocationOf(2)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(3).equals(testObjetivo.getLocationOf(3)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(4).equals(testObjetivo.getLocationOf(4)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(5).equals(testObjetivo.getLocationOf(5)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(6).equals(testObjetivo.getLocationOf(6)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(7).equals(testObjetivo.getLocationOf(7)))) {
			numberOfMisplacedTiles++;
		}
		if (!(board.getLocationOf(8).equals(testObjetivo.getLocationOf(8)))) {
			numberOfMisplacedTiles++;
		}
		
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}
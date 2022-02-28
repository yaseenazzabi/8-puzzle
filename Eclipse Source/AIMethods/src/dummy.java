import java.util.*;

public class dummy {
	
	public static void main(String[] args) {
		// Create two puzzles, s1 and s2 as specified in the coursework description.
		// These values are hard-coded, but can be changed manually.
		// The algorithm will work for any legal 8-puzzle.
		Puzzle newPuzzle = new Puzzle("0");
		Puzzle newPuzzle2 = new Puzzle("1");	

		// This function generates the reachable state space for a given 8-puzzle.
		ArrayList<ArrayList<List<String>>> r1 = Puzzle.evaluatePuzzle(newPuzzle);
		ArrayList<ArrayList<List<String>>> r2 = Puzzle.evaluatePuzzle(newPuzzle2);
		
		/*
		 * To get R1 \ R2:
		 * Either the heuristic solution:
		 * Because the state space of any single puzzle can be shown to be two disjoint graphs,
		 * we can use this fact to prove that exactly one of the following statements is true:
		 * R1 is equal to R2 OR R1 is disjoint from R2.
		 * We can then convert R1 and R2 to HashSets and test for equality, with a low time complexity.
		 * HashSet<...> HashR1, HashR2 <- new HashSet<...>(R1), new HashSet<...>(R2);
		 *
		 * Or the naive solutions:
		 * We can also remove all elements in R1 that appear in R2. 
		 * This is R1 \ R2.
		 * r1.removeAll(r2);
		*/		
	}
}
import java.util.*;
import java.io.*;
/*
 * Serializable is necessary to implement deepCopy. 
 * deepCopy is a method in the Puzzle Class (this Class).
 */
public class Puzzle implements Serializable {
	/*
	 * This is an automatically generated property that must be present for classes
	 * that implement Serializable.
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<List<String>> rows;
	int coordRow;
	int coordCol;
	
	/*
	 * Standard constructor for a Puzzle. 
	 * Manually input the parameters of the puzzle you'd like to evaluate.
	 */
	Puzzle(ArrayList<List<String>> rows, int coordRow, int coordCol) {
		this.rows = rows;
		this.coordRow = coordRow;
		this.coordCol = coordCol;
	}
	
	/*
	 * Alternate constructor for a Puzzle.
	 * Automatically generates the puzzles R1 and R2.
	 * To generate S1, use the argument "0".
	 * To generate S2, use the argument "1".
	 */
	Puzzle(String type) {
		if (type.contentEquals("0")) {
			List<String> firstRow = Arrays.asList("a", "b", "c");
			List<String> secondRow = Arrays.asList("d", "e", "f");
			List<String> thirdRow = Arrays.asList("g", "h", "0");
			
			ArrayList<List<String>> allRows = new ArrayList<List<String>>();
			allRows.add(firstRow);
			allRows.add(secondRow);
			allRows.add(thirdRow);
			
			this.rows = allRows;
			this.coordRow = 2;
			this.coordCol = 2;
		}
		if (type.contentEquals("1")) {
			List<String> firstRow = Arrays.asList("a", "e", "b");
			List<String> secondRow = Arrays.asList("0", "h", "c");
			List<String> thirdRow = Arrays.asList("d", "g", "f");
			
			ArrayList<List<String>> allRows = new ArrayList<List<String>>();
			allRows.add(firstRow);
			allRows.add(secondRow);
			allRows.add(thirdRow);
			
			this.rows = allRows;
			this.coordRow = 1;
			this.coordCol = 0;
		}
	}
	
	
	
	/*
	 * The state space for any n-puzzle is n!. 
	 * The state space for any state in any n-puzzle is (n! + 1) / 2.
	 * This means evaluating 181,440 states for this size of puzzle.
	 * 
	 * This program has been thoroughly tested, so please do not be alarmed by the incredibly long compute time.
	 * It took a Ryzen 7 2700x processor three and a half hours to fully compute (on high priority tasking and maximum affinity).
	 * 
	 * If you'd like a visualisation of the progress the program has made, 
	 * implementing a counter and printing it whenever the stack is pushed to is a very simple way of doing so.
	 */
	public static ArrayList<ArrayList<List<String>>> evaluatePuzzle(Puzzle puzzle) {
		// Create an empty stack and an empty list of visited nodes.
		System.out.println("Starting algorithm. This will take a long time.");
		Stack<Puzzle> stack = new Stack<Puzzle>();
		ArrayList<ArrayList<List<String>>> visited = new ArrayList<ArrayList<List<String>>>();
		// Copy the inputted puzzle to a variable. Add the puzzle to the list of visited nodes, and the stack.
		Puzzle current = (Puzzle) deepCopy(puzzle);
		visited.add(current.rows);
		stack.push(current);

		while(!stack.isEmpty()) {
			// The current puzzle we wish to expand.
			current = stack.pop();
			
			// Four deep copies of the current puzzle.
			// Each copy will have its blank moved up, left, down and right respectively.
			Puzzle upState = (Puzzle) deepCopy(current);
			Puzzle leftState = (Puzzle) deepCopy(current);
			Puzzle downState = (Puzzle) deepCopy(current);
			Puzzle rightState = (Puzzle) deepCopy(current);					
	
			Puzzle.moveUp(upState);				
			Puzzle.moveLeft(leftState);
			Puzzle.moveDown(downState);			
			Puzzle.moveRight(rightState);	
			
			// If all of the generated states are reached, then the state is impossible to progress from, so we terminate.
			if(visited.contains(leftState.rows) & visited.contains(rightState.rows) & 
					visited.contains(downState.rows) & visited.contains(upState.rows)) {
				continue;
			}
			
			// Add all states that are valid and that have not yet been visited to the stack.
			// We traverse the upwards tree, then the downwards tree, then the rightwards tree, and finally the leftwards tree.
			if ((leftState.coordCol != -1) & !visited.contains(leftState.rows)) {
				visited.add(leftState.rows);
				stack.push(leftState);
			}
			
			if ((rightState.coordCol != -1) & !visited.contains(rightState.rows)) {
				visited.add(rightState.rows);
				stack.push(rightState);
			}

			if ((downState.coordCol != -1) & !visited.contains(downState.rows)) {
				visited.add(downState.rows);
				stack.push(downState);
			}

			if ((upState.coordCol != -1) & !visited.contains(upState.rows)) {
				visited.add(upState.rows);
				stack.push(upState);
			}
			
	        /* Stopper so that we can easily see each line.
	        System.out.println("\n----------NEW STATE---------\n");
			System.out.print("Current: ");
	        Puzzle.outputPuzzle(current);
	        Scanner sc = new Scanner(System.in);
	        sc.nextLine();
			*/
		}
		
		// After all 181,440 possible states are evaluated, we have escaped the while loop.
		System.out.println("Done.");
		return visited;
	}
	
	/*
	 * We must define the swapping procedure of the blank space.
	 * First, we identify the value of the cell that we wish to swap the blank with.
	 * Let this cell be exemplified by 'a'.
	 * Then, we copy 'a' into the cell that contains the blank space.
	 * Then, we overwrite the original 'a' that we identified at the start with a blank space.
	 * 
	 * If the movement is not legal, for instance if we want to move a letter in the 0th row
	 * upwards, these functions will do nothing and set the coordinate pointers of the blank to -1.
	 * the -1 value will be used to indicate to other functions that they have arrived at an invalid state.
	 */
	public static Puzzle moveUp(Puzzle puzzle) {
		if ((puzzle.coordRow - 1) >= 0) {
			String selection;
			int currentRow = puzzle.coordRow;
			int currentCol = puzzle.coordCol;
			
			selection = puzzle.rows.get(currentRow - 1).get(currentCol);			
			puzzle.rows.get(currentRow).set(currentCol, selection);
			puzzle.rows.get(currentRow - 1).set(currentCol, "0");
			puzzle.coordRow--;
			
			return puzzle;
		}	
		else {
			puzzle.coordCol = -1;
			puzzle.coordRow = -1;
			return puzzle;		
		}
	}
	
	public static Puzzle moveLeft(Puzzle puzzle) {		
		if ((puzzle.coordCol - 1) >= 0) {
			String selection;
			int currentRow = puzzle.coordRow;
			int currentCol = puzzle.coordCol;
			
			selection = puzzle.rows.get(currentRow).get(currentCol - 1);
			puzzle.rows.get(currentRow).set(currentCol, selection);
			puzzle.rows.get(currentRow).set((currentCol - 1), "0");
			puzzle.coordCol--;
			
			return puzzle;
		}
		else {
			puzzle.coordCol = -1;
			puzzle.coordRow = -1;
			return puzzle;
		}
	}
	
	public static Puzzle moveDown(Puzzle puzzle) {		
		if ((puzzle.coordRow + 1) <= 2) {
			String selection;
			int currentRow = puzzle.coordRow;
			int currentCol = puzzle.coordCol;
			
			selection = puzzle.rows.get(currentRow + 1).get(currentCol);
			puzzle.rows.get(currentRow).set(currentCol, selection);
			puzzle.rows.get(currentRow + 1).set(currentCol, "0");
			puzzle.coordRow++;

			return puzzle;
		}
		else {
			puzzle.coordCol = -1;
			puzzle.coordRow = -1;
			return puzzle;
		}
	}
	
	public static Puzzle moveRight(Puzzle puzzle) {
		if ((puzzle.coordCol + 1) <= 2) {
			String selection;
			int currentRow = puzzle.coordRow;
			int currentCol = puzzle.coordCol;
			
			selection = puzzle.rows.get(currentRow).get(currentCol + 1);
			puzzle.rows.get(currentRow).set(currentCol, selection);
			puzzle.rows.get(currentRow).set((currentCol + 1), "0");
			puzzle.coordCol++;

			return puzzle;
		}
		else {
			puzzle.coordCol = -1;
			puzzle.coordRow = -1;
			return puzzle;
		}
	}
	
	/*
	 * This is a simple function that outputs a puzzle.
	 * A puzzle consists of a 3x3 matrix, with one element being a blank space.
	 * A puzzle also consists of a pair of coordinates that point to the location of the blank space.
	 */
	public static void outputPuzzle(Puzzle puzzle) {
		if (puzzle != null) {
			System.out.println(puzzle.rows);
			System.out.println(" - where 0 is at (" + puzzle.coordRow + ", " + puzzle.coordCol +").\n");
		}
		else {
			System.out.println("Puzzle is null.\n");
		}
	}
	
	/*
	 * This will take any object and copy it to another instance.
	 * Usually, Java copies by reference, whereas this function will copy by value.
	 * 
	 * Let Object1 = "1":
	 * 
	 * Usually if "Object object2 = object1" is employed:
	 * object2 += 1 gives the new values of object2 = 2 and object1 = 2. 
	 * This is because object2 is simply a reference to object1, and not a distinct object itself.
	 * 
	 * Using this function:
	 * Object object2 = (Object) deepCopy(object1)
	 * object2 += 1 gives the new values of object2 = 2 and object1 = 1.
	 * object1's data has not been corrupted by changing the value of object2's data.
	 * 
	 * This functionality is required to generate new, unique states without corrupting other states.
	 * If we move the blank space of a non-deepCopy puzzle left and then right, we output two references to the starting puzzle.
	 * This is because the left move and the right move have cancelled each other out.
	 * 
	 * Using a deep copy prevents that, instead outputting two completely distinct puzzles.
	 */
	public static Object deepCopy(Object puzzle) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
			objectStream.writeObject(puzzle);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return objInputStream.readObject();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}


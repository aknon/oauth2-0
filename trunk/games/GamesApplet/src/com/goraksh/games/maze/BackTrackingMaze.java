package com.goraksh.games.maze;

import java.util.Stack;

/**
 * 
 * @author Nitesh
 * 
 */
public class BackTrackingMaze {

	public static void main(String[] args) throws InterruptedException {
		BackTrackingMaze bm = new BackTrackingMaze();
		System.out.println("printing the co-ordinates of the right path");
		bm.move(0, 0, maze, -1, -1);

		System.out
				.println("-----------------Input maze, 0=way ahead, 1=blockage------------------");
		bm.printInputMaze();

		System.out
				.println("\n----------------- 0 denotes the path to exit --------------------- \n");
		bm.printExitPath();
	}

	public static int[][] maze = new int[][] { 
		{ 0, 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 1, 0, 1, 0, 1,  1 }, 
		{ 0, 1, 1, 0, 1, 0, 0, 1 },
		{ 0, 1, 0, 0, 1, 1, 0,  1 }, 
		{ 0, 1, 0, 0, 0, 1, 0, 1 },
		{ 0, 1, 0, 1, 0, 1, 0, 1 },
		{ 0, 1, 0, 0, 0, 1, 0,  1 },
		{ 1, 1, 1, 0, 1, 0, 0,  1 },
		{ 1, 1, 1, 0, 0, 0, 1, 1 }
		};

	static int startX = 0;
	static int startY = 0;
	static int endX = 0;
	static int endY = maze[0].length - 1;

	static int[][] pathtoexit = new int[maze.length][maze[0].length];

	static {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				// pathtoexit[i][j] = maze[i][j];
				pathtoexit[i][j] = 1;
			}
		}
		pathtoexit[startX][startY] = 0;
	}

	int[] exitIndex = new int[] { endX, endY };

	private boolean success = false;

	private int dead_end = 2;
	private int found_exit = 0;
	private int dead_end_maze = 3;

	private boolean isFreedom( int x, int y, int[][] maze) {
		//return (x == exitIndex[0] && y == exitIndex[1]); 
		return ( y == maze[0].length-1 ) && ( maze[x][y] == 0 );
	}
	/**
	 * 
	 * @param x, current x co-ordinate  into  this direction ( downward for the input matrix ) .... equivalent to lookSoth
	 *                                                  |
	 *                                                  |
	 *                                                  |
	 *                                                 \/
	 *                                                 
	 * @param y, current y ordinate, into -----------> this direction ( horizontal direction ) .... equivalen to lookEast
	 * @param maze
	 * @param px
	 * @param py
	 * @return
	 * @throws InterruptedException 
	 */
	public int move(int x, int y, int[][] maze, int px, int py) throws InterruptedException {

		//if (x == exitIndex[0] && y == exitIndex[1])
			//success = true;
		
		if ( isFreedom ( x, y, maze )) 
			success = true;

		if (success) {
			return found_exit;
		}

		// there are more valid moves
		@SuppressWarnings("rawtypes")
		Stack stk = new Stack();
		// x = row index
		// y= column index

		int[] north = lookNorth(x, y, maze, px, py);
		if (north != null)
			stk.push(north);

		int[] west = lookWest(x, y, maze, px, py);
		if (west != null)
			stk.push(west);

		int[] east = lookEast(x, y, maze, px, py);
		if (east != null)
			stk.push(east);

		int[] south = lookSouth(x, y, maze, px, py);
		if (south != null)
			stk.push(south);
			
		if (stk.isEmpty()) {
			//System.err.println("dead end : row=" + x + " col=" + y);
			return dead_end;
		}
		int[] out = null;
		while (!success && !stk.isEmpty()) {
			out = (int[]) stk.pop();
			int ret = move(out[0], out[1], maze, x, y);
			if (ret == dead_end) {
				System.out.println("dead end returned: row=" + x + " col=" + y);
			}
		}

		if (success) {
			System.out.println("Success :" + " x=" + out[0] + " y=" + out[1]);
			pathtoexit[out[0]][out[1]] = 0;
			return found_exit;
		}

		System.err.println("Dead end maze, last index: " + " x=" + out[0]
				+ " y=" + out[1]);
		return dead_end_maze;

	}

	public int[] lookEast(int x, int y, int[][] maze, int px, int py) {
		int k = ++y;
		return (k == py || k == maze[0].length || maze[x][k] == 1) ? null
				: new int[] { x, k };
	}

	public int[] lookSouth(int x, int y, int[][] maze, int px, int py) {
		int k = ++x;
		return (k == px || k == maze.length || maze[k][y] == 1) ? null
				: new int[] { k, y };
	}

	public int[] lookNorth(int x, int y, int[][] maze, int px, int py) {
		int k = --x;
		return (k == px || k == -1 || maze[k][y] == 1) ? null : new int[] { k,
				y };
	}

	public int[] lookWest(int x, int y, int[][] maze, int px, int py) {
		int k = --y;
		return (k == py || k == -1 || maze[x][k] == 1) ? null : new int[] { x,
				k };
	}

	private void printExitPath() {
		for (int i = 0; i < pathtoexit.length; i++) {
			for (int j = 0; j < pathtoexit[i].length; j++) {
				System.out.print(pathtoexit[i][j] + " ");
			}
			System.out.println("");
		}

	}

	private void printInputMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println("");
		}

	}

}
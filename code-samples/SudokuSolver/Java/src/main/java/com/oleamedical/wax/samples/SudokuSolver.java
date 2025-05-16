package com.oleamedical.wax.samples;

public class SudokuSolver {

    private static final int GRID_SIZE = 9;
    private static final int SQUARE_SIZE = 3;

    private static boolean canBePlaced(int [][] grid, int index, int value) {
        int y = index / GRID_SIZE;
        int x = index % GRID_SIZE;

        for (int i = 0; i < GRID_SIZE; i++)
            if (grid[y][i] == value) return false;

        for (int j = 0; j < GRID_SIZE; j++)
            if (grid[j][x] == value) return false;

        int squareY = y / SQUARE_SIZE;
        int squareX = x / SQUARE_SIZE;

        for (int offsetY = 0; offsetY < SQUARE_SIZE; offsetY++) {
            for (int offsetX = 0; offsetX < SQUARE_SIZE; offsetX++)
                if (grid[offsetY + squareY * SQUARE_SIZE][offsetX + squareX * SQUARE_SIZE] == value) return false;
        }

        return true;
    }

    private static boolean backtrack(int [][]grid, int index){
        int y = index / GRID_SIZE;
        int x = index % GRID_SIZE;

        if (index >= GRID_SIZE * GRID_SIZE) return true;

        if (grid[y][x] != 0) return backtrack(grid, index + 1);

        for (int val = 1; val <= GRID_SIZE; val++) {
            if (!canBePlaced(grid, index, val)) continue;

            grid[y][x] = val;
            if (backtrack(grid, index + 1)) return true;
            grid[y][x] = 0;
        }

        return false;
    }

    private static boolean solve(int [][]grid) {
        return backtrack(grid, 0);
    }

    public static void main(String[] args) {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        grid[0] = new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0};
        grid[1] = new int[]{0, 8, 0, 0, 7, 0, 0, 0, 0};
        grid[2] = new int[]{0, 0, 0, 0, 0, 0, 0, 4, 0};
        grid[3] = new int[]{0, 0, 0, 0, 8, 6, 0, 0, 0};
        grid[4] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        grid[5] = new int[]{0, 0, 0, 0, 0, 2, 0, 0, 0};
        grid[6] = new int[]{0, 0, 0, 2, 0, 0, 0, 0, 0};
        grid[7] = new int[]{6, 0, 0, 0, 0, 0, 0, 0, 0};
        grid[8] = new int[]{0, 7, 0, 0, 0, 0, 0, 0, 0};

        if (!solve(grid)) {
            System.out.printf("Sudoku is not solvable\n");
            return ;
        }

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.printf("%d ", grid[row][col]);
            }
            System.out.print("\n");
        }

    }

}

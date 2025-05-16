#ifndef SUDOKU_H
# define SUDOKU_H

# include <stdio.h>
# include <stdbool.h>

# define GRID_SIZE 9
# define SQUARE_SIZE 3


bool    solve(int grid[GRID_SIZE][GRID_SIZE]);
bool    backtrack(int grid[GRID_SIZE][GRID_SIZE], int index);
bool    can_be_placed(int grid[GRID_SIZE][GRID_SIZE], int index, int val);

#endif
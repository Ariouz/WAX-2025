#include "../include/sudoku.h"

bool    can_be_placed(int grid[GRID_SIZE][GRID_SIZE], int index, int val) {
    const int y = index / GRID_SIZE;
    const int x = index % GRID_SIZE;

    for (int i = 0; i < GRID_SIZE; i++)
        if (grid[y][i] == val) return false;

    for (int j = 0; j < GRID_SIZE; j++)
        if (grid[j][x] == val) return false;

    int square_y = y / SQUARE_SIZE;
    int square_x = x / SQUARE_SIZE;

    for (int offset_y = 0; offset_y < SQUARE_SIZE; offset_y++) {
        for (int offset_x = 0; offset_x < SQUARE_SIZE; offset_x++) {
            if (grid[offset_y + square_y * SQUARE_SIZE][offset_x + square_x * SQUARE_SIZE] == val) return false;
        }
    }
    return true;
}

bool    backtrack(int grid[GRID_SIZE][GRID_SIZE], int index)
{
    const int y = index / GRID_SIZE;
    const int x = index % GRID_SIZE;

    if (index >= GRID_SIZE*GRID_SIZE)
        return true;

    if (grid[y][x] != 0) return backtrack(grid, index + 1);

    for (int val = 1; val <= GRID_SIZE; val++) {
        if (!can_be_placed(grid, index, val)) continue;

        grid[y][x] = val;
        if (backtrack(grid, index + 1)) return true;
        grid[y][x] = 0;
    }

    return false;
}

bool    solve(int grid[GRID_SIZE][GRID_SIZE])
{
    return backtrack(grid, 0);
}

int main()
{
    int grid[GRID_SIZE][GRID_SIZE] = {
        {2, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 8, 0, 0, 7, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 4, 0},
        {0, 0, 0, 0, 8, 6, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 0, 0, 0},
        {6, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 7, 0, 0, 0, 0, 0, 0, 0}
    };

    solve(grid);

    for (int row = 0; row < GRID_SIZE; row++) {
        for (int col = 0; col < GRID_SIZE; col++)
            printf("%d ", grid[row][col]);
        printf("\n");
    }
    return 0;
}
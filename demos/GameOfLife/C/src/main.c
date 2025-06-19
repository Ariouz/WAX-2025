#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/time.h>

# define GRID_SIZE 250

bool grid[GRID_SIZE][GRID_SIZE];
int grid_len = GRID_SIZE;

void fillGrid() {
    for (int y = 0; y < GRID_SIZE; y++) {
        for (int x = 0; x < GRID_SIZE; x++)
            grid[y][x] = rand() % 2;
    }
}

int getAliveNeighboursCount(int x, int y, bool status) {
    int alive_neighbours = 0;
    
    for (int yOffset = -1; yOffset < 2; yOffset++) {
        for (int xOffset = -1; xOffset < 2; xOffset++) {
            if (!(y+yOffset >= 0 && y+yOffset < grid_len)
                || !(x+xOffset >= 0 && x+xOffset < grid_len)) continue;

            if (grid[y+yOffset][x+xOffset] && !(yOffset == 0 && xOffset == 0)) alive_neighbours++;
        }
    }

    return alive_neighbours;
}

bool getNewCellState(int x, int y, bool state)  {
    int neighbourCount = getAliveNeighboursCount(x, y, state);

    if (neighbourCount == 2) return state;
    return neighbourCount == 3;
}

void printGrid() {
    for (int y = 0; y < GRID_SIZE; y++) {
        for (int x = 0; x < GRID_SIZE; x++)
            printf("%c", (grid[y][x] == true ? '#' : ' '));
        printf("\n");
    }
    usleep(100*1000);
}

void iterate() {
    bool newGrid[GRID_SIZE][GRID_SIZE];

    //printGrid();
    for (int y = 0; y < GRID_SIZE; y++) {
        for (int x = 0; x < GRID_SIZE; x++)
            newGrid[y][x] = getNewCellState(x, y, grid[y][x]);
    }

    for (int y = 0; y < GRID_SIZE; y++) {
        for (int x = 0; x < GRID_SIZE; x++)
            grid[y][x] = newGrid[y][x];
    }
}



int main() {
    int iterations = 1000;

    struct timeval start;
    gettimeofday(&start, NULL);
    
    
    fillGrid();
    for (int i = 0; i < iterations; i++)
        iterate();

    
    struct timeval end;
    gettimeofday(&end, NULL);

    long elapsed = (end.tv_sec-start.tv_sec)*1000000 + end.tv_usec-start.tv_usec;

    printf("Simulation time: %ld ms", elapsed/1000);

    return 0;
}
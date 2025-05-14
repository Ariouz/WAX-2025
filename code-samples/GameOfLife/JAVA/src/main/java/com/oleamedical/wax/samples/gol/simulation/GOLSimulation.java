package com.oleamedical.wax.samples.gol.simulation;

import com.oleamedical.wax.samples.gol.Application;

import java.util.Random;

public class GOLSimulation {

    private final boolean[][] grid;

    public GOLSimulation(int width, int height) {
        this.grid = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                grid[x][y] = new Random().nextBoolean();
        }
    }

    public void simulate(int maxIterations) throws InterruptedException {
        for (int i = 0; i < maxIterations; i++)
            iterate();
    }

    private void iterate() throws InterruptedException {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        if (Application.DISPLAY) printGrid();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++)
                newGrid[y][x] = getNewCellState(x, y, grid[y][x]);
        }

        for (int y = 0; y < newGrid.length; y++)
            System.arraycopy(newGrid[y], 0, grid[y], 0, newGrid[y].length);
    }

    private void printGrid() throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (boolean[] row : grid) {
            for (boolean cellState : row)
                sb.append(cellState ? "#" : " ");
            sb.append("\n");
        }
        System.out.print(sb);
        Thread.sleep(100);
    }

    private boolean getNewCellState(int x, int y, boolean state) {
        int neighbourCount = getAliveNeighboursCount(x, y);

        if (neighbourCount == 2) return state;
        return neighbourCount == 3;
    }

    private int getAliveNeighboursCount(int x, int y) {
        int aliveNeighbours = 0;

        for (int yOffset = -1; yOffset < 2; yOffset++) {
            for (int xOffset = -1; xOffset < 2; xOffset++) {
                if (!(y+yOffset >= 0 && y+yOffset < grid.length) || !(x+xOffset >= 0 && x+xOffset < grid[0].length)) continue;

                if (grid[y+yOffset][x+xOffset] && !(yOffset == 0 && xOffset == 0)) aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

}

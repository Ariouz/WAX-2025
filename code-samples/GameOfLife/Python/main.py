import random
import time
import os

grid_size = int(os.getenv("GRID_SIZE"))
grid = [[random.randrange(0, 2) == 1 for _ in range(grid_size)] for _ in range(grid_size)]

iterations = int(os.getenv("ITERATIONS"))


def get_alive_neighbors(x, y):
    alive = 0

    for yOffset in range(-1, 2):
        for xOffset in range(-1, 2):
            if not (0 <= y + yOffset < grid_size) or not (0 <= x + xOffset < grid_size):
                continue

            if grid[y + yOffset][x + xOffset] and not (yOffset == 0 and xOffset == 0):
                alive += 1

    return alive

def get_new_cell_state(x, y, state):
    neighbors = get_alive_neighbors(x, y)

    if neighbors == 2: return state
    return neighbors == 3


def iterate():
    global grid

    new_grid = [[False for _ in range(grid_size)] for _ in range(grid_size)]
    for y in range(grid_size):
        for x in range(grid_size):
            state = grid[y][x]
            new_grid[y][x] = get_new_cell_state(x, y, state)

    grid = [[val for val in new_row] for new_row in new_grid]

def print_grid():
    for y in range(grid_size):
        for x in range(grid_size):
            print(f"{'#' if grid[y][x] else ' '}", end="")
        print("")


if __name__ == "__main__":
    start = time.time()
    for _ in range(iterations):
        iterate()

    end = time.time()
    print(f"Total time: {int((end - start) * 1000)} ms")
    print(f"Total iterations: {iterations}")
    print(f"Grid size: {grid_size}")



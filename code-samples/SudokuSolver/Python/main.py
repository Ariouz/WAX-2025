from typing import List

grid_size= 9
square_size= 3

def solve(grid: List[List[int]]):
    res = backtrack(grid)
    return res


def can_be_placed(grid, index, value):
    y = index // grid_size
    x = index % grid_size

    if value in grid[y]: return False
    for j in range(grid_size):
        if grid[j][x] == value: return False

    square_y = y // square_size
    square_x = x // square_size

    for offset_y in range(square_size):
        for offset_x in range(square_size):
            if grid[offset_y + square_y*square_size][offset_x + square_x*square_size] == value: return False

    return True


def backtrack(grid, index=0):
    y = index // grid_size
    x = index % grid_size

    if index >= grid_size*grid_size:
        return True

    if not grid[y][x] == 0: return backtrack(grid, index + 1)

    for val in range(1, 10):
        if not can_be_placed(grid, index, val): continue

        grid[y][x] = val
        if backtrack(grid, index + 1): return True
        grid[y][x] = 0

    return False

if __name__ == '__main__':
    grid = [[0] * 9 for _ in range(9)]
    grid[0] = [2, 0, 0, 0, 0, 0, 0, 0, 0]
    grid[1] = [0, 8, 0, 0, 7, 0, 0, 0, 0]
    grid[2] = [0, 0, 0, 0, 0, 0, 0, 4, 0]
    grid[3] = [0, 0, 0, 0, 8, 6, 0, 0, 0]
    grid[4] = [0, 0, 0, 0, 0, 0, 0, 0, 0]
    grid[5] = [0, 0, 0, 0, 0, 2, 0, 0, 0]
    grid[6] = [0, 0, 0, 2, 0, 0, 0, 0, 0]
    grid[7] = [6, 0, 0, 0, 0, 0, 0, 0, 0]
    grid[8] = [0, 7, 0, 0, 0, 0, 0, 0, 0]

    if not solve(grid):
        print("Grid in not solvable")
        exit(1)

    for row in grid:
        print(row)
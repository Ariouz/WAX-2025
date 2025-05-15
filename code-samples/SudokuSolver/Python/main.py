from typing import List
from math import sqrt


def solve(grid: List[List[int]]):
    index = 0
    res = backtrack(grid, index)
    return res


def can_be_placed(grid, index, value):
    y = index // len(grid)
    x = index % len(grid)

    if value in grid[y]: return False
    for _ in range(len(grid[y])):
        if grid[_][x] == value: return False

    square_y = y // int(sqrt(len(grid)))
    square_x = x // int(sqrt(len(grid)))

    for offset_y in range(3):
        for offset_x in range(3):
            if grid[offset_y + square_y*3][offset_x + square_x*3] == value: return False

    return True


def backtrack(grid, index):
    y = index // len(grid)
    x = index % len(grid)

    if index >= len(grid)*len(grid):
        return True

    if not grid[y][x] == 0: return backtrack(grid, index + 1)

    for val in range(1, 10):
        can_place = can_be_placed(grid, index, val)
        if not can_place: continue

        grid[y][x] = val
        if backtrack(grid, index + 1): return True
        grid[y][x] = 0

    return False

if __name__ == '__main__':
    grid = [[0] * 9 for _ in range(9)]
    grid[0] = [0, 8, 0, 7, 0, 3, 0, 9, 0]
    grid[1] = [0, 0, 6, 0, 1, 8, 0, 4, 0]
    grid[2] = [1, 5, 7, 0, 0, 6, 8, 0, 0]
    grid[3] = [0, 7, 0, 5, 0, 1, 0, 0, 4]
    grid[4] = [8, 3, 1, 0, 4, 0, 2, 0, 0]
    grid[5] = [5, 0, 0, 8, 0, 0, 0, 1, 0]
    grid[6] = [0, 0, 5, 9, 6, 2, 0, 7, 8]
    grid[7] = [0, 0, 0, 0, 0, 0, 4, 2, 9]
    grid[8] = [9, 2, 8, 0, 7, 0, 0, 0, 0]

    if not solve(grid):
        print("Grid in not solvable")
        exit(1)

    for row in grid:
        print(row)
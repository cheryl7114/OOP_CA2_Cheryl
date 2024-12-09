package org.example;

import java.util.Stack;

public class Question10 {
    // define movement directions
    public enum DIRECTION {
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1),
        UP(-1, 0);

        int dx, dy;

        DIRECTION(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    // display/ visualize the maze
    public static void display(char[][] maze) {
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    // solve the maze using a stack and backtracking
    public void solve(int startX, int startY, int exitX, int exitY, char[][] maze) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        // mark the starting point as visited
        maze[startX][startY] = '#';

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            // check if we've reached the exit
            if (x == exitX && y == exitY) {
                maze[x][y] = 'E'; // mark the exit
                System.out.println("Exit found at coordinates (" + exitX + ", " + exitY + ")" );
                System.out.println("Visited coordinates are marked by '#'");
                display(maze);
                return;
            }

            // explore all directions
            for (DIRECTION dir : DIRECTION.values()) {
                int newX = x + dir.dx;
                int newY = y + dir.dy;

                if (isValidMove(newX, newY, maze)) {
                    stack.push(new int[]{newX, newY});
                    maze[newX][newY] = '#'; // mark as visited
                }
            }
        }
    }

    // check if a move is valid
    public boolean isValidMove(int x, int y, char[][] maze) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == ' ';
    }

    public static void main(String[] args) {
        char[][] maze = {
                {'*', '*', '*', '*', '*', '*', '*', '*'},
                {'*', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
                {'*', '*', '*', '*', ' ', '*', '*', '*'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
                {'*', '*', '*', '*', ' ', '*', '*', '*'},
                {'*', ' ', ' ', ' ', ' ', '*', '*', '*'},
                {'*', '*', '*', '*', ' ', '*', '*', '*'},
                {'*', '*', '*', '*', '*', '*', '*', '*'}
        };

        Question10 solver = new Question10();
        System.out.println("Maze:");
        display(maze);
        System.out.println();

        int startX = 3, startY = 4; // starting coordinates
        int exitX = 3, exitY = 0;   // exit coordinates

        solver.solve(startX, startY, exitX, exitY, maze);
    }
}

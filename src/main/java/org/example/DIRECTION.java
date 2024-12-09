package org.example;

/**
 *  Name: Cheryl Kong
 *  Class Group: SD2B
 */

/*
Direction enum used to indicate direction.
 */
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


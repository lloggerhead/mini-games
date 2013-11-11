#ifndef _IAGNO_H
#define _IAGNO_H

typedef enum Player {
	kPlayer1,
	kPlayer2
} Player;

typedef enum Bool {
	kFalse,
	kTrue
} Bool;

typedef enum Chess {
	kNoneChess='-',
	kPlayer1Chess='X',
	kPlayer2Chess='O'
} Chess;

typedef enum GameStatus {
	kPlayer1Win,
	kPlayer2Win,
	kContinue
} GameStatus;

typedef struct point {
    int x;
    int y;
} Point;

char gBuffer[BUFSIZ];
Chess gBoard[9][9];
Player gPlayer;
const int gDirections[8][2] = {
	{1, 0}, {-1, 0}, // ↓ ↑
	{0, 1}, {0, -1}, // → ←
	{1, 1}, {-1, -1}, // ↘ ↖
	{-1, 1}, {1, -1} // ↗ ↙
};

void startGame();
void init();
void help();
void show();
void putChess(Point p);
Point input();
void changePlayer();
Bool haveAnyValidChess();
Bool isGameover();
Point findTurnPoint(Point p, const int dir[]);
void doTurnChess(Point p, Point target, const int dir[]);
Chess currentPlayerChess();
Bool isCurrentPlayerChess(Point p);
Bool isValid(Point p);
Bool isNoneChess(Point p);
Bool inBoard(Point p);

#endif /* _IAGNO_H */

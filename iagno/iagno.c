#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "iagno.h"

int main(int argc, char *argv[]) {
    // 重定向标准输入，用于调试
    //freopen("input.txt", "rb", stdin);
    while(1) {
        startGame();
    }
    return 0;
}

void startGame() {
    init();
    help();
    show();
    while(1) {
        putChess(input());
        show();
        changePlayer();
        // 无子可下时，让对手下
        if (!haveAnyValidChess())
            changePlayer();
        if (isGameover())
            break;
    }
}

void init() {
    int i, j;

    for (i = 0; i < 9; i++)
        for (j = 0; j < 9; j++)
            gBoard[i][j] = kNoneChess;
    gBoard[4][4] = kPlayer2Chess;
    gBoard[4][5] = kPlayer1Chess;
    gBoard[5][4] = kPlayer1Chess;
    gBoard[5][5] = kPlayer2Chess;

    gPlayer = kPlayer1;
}

void help() {
    printf("\t%s\n\t%s\n\t%s\n\n",
            "输入quit或exit退出游戏，输入坐标（如：1 1）落子。",
            "若输入无效落子，则重新输入；否无子可下，则交换玩家。",
            "若双方均无子可下，则结束游戏。");
}

void show() {
    int i, j;
    printf("\n  ");
    // 输出y轴
    for (i = 1; i < 9; i++)
        printf("%c ", i + '0');
    printf("\n");

    for (i = 1; i < 9; i++) {
        // 输出x轴
        printf("%c ", i + '0');
        // 输出棋子
        for (j = 1; j < 9; j++)
            printf("%c ", gBoard[i][j]);
        printf("\n");
    }
    printf("\n");
}

// 落子
void putChess(Point p) {
    int i;
    Point origin = p;

    for (i = 0; i < 8; i++) {
        const int *dir = gDirections[i];
        p.x = origin.x + dir[0];
        p.y = origin.y + dir[1];
        if (!inBoard(p) || isCurrentPlayerChess(p))
            continue;

        p = findTurnPoint(origin, dir);
        if (inBoard(p))
            doTurnChess(origin, p, dir);
    }
}

Point input() {
    Point p = {0, 0};

    do {
        if (gPlayer == kPlayer1)
            printf("1P %c : ", kPlayer1Chess);
        else
            printf("2P %c : ", kPlayer2Chess);
        if (gets(gBuffer) == NULL || !strcmp(gBuffer, "quit") || !strcmp(gBuffer, "exit"))
            exit(EXIT_SUCCESS);

        if (isdigit(gBuffer[0]) && isdigit(gBuffer[2])) {
            p.x = gBuffer[0] - '0';
            p.y = gBuffer[2] - '0';
        }
    } while (!isValid(p));
    return p;
}

void changePlayer() {
    gPlayer = (gPlayer == kPlayer1) ? kPlayer2 : kPlayer1;
}

// 存在任何能将对方棋子转变的棋子吗？
Bool haveAnyValidChess() {
    Point p;
    int i, j;

    for (i = 1; i < 9; i++)
        for (j = 1; j < 9; j++) {
            p.x = i;
            p.y = j;
            if (isValid(p))
                return kTrue;
        }
    return kFalse;
}

Bool isGameover() {
    int i, j;
    // 1P 棋子数
    int cnt1P = 0;
    int cnt2P = 0;
    GameStatus status = kContinue;

    for (i = 1; i < 9; i++)
        for (j = 1; j < 9; j++) {
            cnt1P += (gBoard[i][j] == kPlayer1Chess);
            cnt2P += (gBoard[i][j] == kPlayer2Chess);
        }

    if (0 == cnt1P) {
        status = kPlayer1Win;
    } else if (0 == cnt2P) {
        status = kPlayer2Win;
        // 如果棋盘已满
    } else if (8 * 8 == cnt1P + cnt2P) {
        status = (cnt1P > cnt2P) ? kPlayer1Win : kPlayer2Win;
    } else if (!haveAnyValidChess()) {
        changePlayer();
        // 双方皆无子可下时，结束游戏
        if (!haveAnyValidChess())
            status = (cnt1P > cnt2P) ? kPlayer1Win : kPlayer2Win;
        changePlayer();
    }

    switch(status) {
        case kPlayer1Win:
            printf("\t1P Win!\n\n");
            return kTrue;
        case kPlayer2Win:
            printf("\t2P Win!\n\n");
            return kTrue;
        default:
            return kFalse;
    }
}

// 找出p点和dir方向上导致对方棋子转变的棋子
// 若不存在，return (0, 0)
Point findTurnPoint(Point p, const int dir[]) {
    // p O O O ? | →
    p.x += dir[0];
    p.y += dir[1];

    // p O O ? | →
    while (1) {
        // p O O O | →
        // p O O - | →
        if (!inBoard(p) || isNoneChess(p)) {
            p.x = p.y = 0;
            break;
            // p O O X | →
        } else if (isCurrentPlayerChess(p)) {
            break;
        } else {
            p.x += dir[0];
            p.y += dir[1];
        }
    }
    return p;
}

// 转变夹住的棋子
void doTurnChess(Point p, Point target, const int dir[]) {
    int x = p.x;
    int y = p.y;

    while (!(x == target.x && y == target.y)) {
        gBoard[x][y] = currentPlayerChess();
        x += dir[0];
        y += dir[1];
    }
}

// 返回当前玩家的棋子类型
Chess currentPlayerChess() {
    return gPlayer == kPlayer1 ? kPlayer1Chess : kPlayer2Chess;
}

Bool isCurrentPlayerChess(Point p) {
    return gBoard[p.x][p.y] == currentPlayerChess();
}

// p点是有效的落子吗？
Bool isValid(Point p) {
    int i;
    Point origin = p;

    if (inBoard(p) && isNoneChess(p))
        for (i = 0; i < 8; i++) {
            const int *dir = gDirections[i];
            p.x = origin.x + dir[0];
            p.y = origin.y + dir[1];

            if (!inBoard(p) || isCurrentPlayerChess(p))
                continue;
            p = findTurnPoint(origin, dir);
            if (inBoard(p))
                return kTrue;
        }
    return kFalse;
}

Bool isNoneChess(Point p) {
    return kNoneChess == gBoard[p.x][p.y];
}

// p点在棋盘内吗？
Bool inBoard(Point p) {
    return (p.x > 0 && p.x < 9) && (p.y > 0 && p.y < 9);
}

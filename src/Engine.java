public class Engine {

    public int[][] gameField; // variable for game field
    public int direction = 1; // direction variable: 0-to west; 1 - to north; 2
    // - to east; 3 - to south
    public int newDirection = direction; // new prefered direction
    private int headX, headY; // head coordinates
    public int gameLevel = 0; // game level - describe snake speed
    public int gameRound = 0; // game Round - describe wall environment
    public int gameRoundMax = 5; // the highest round number
    public int gameLevelMax = 5; // the highest level number
    public boolean endGame; // Characterize end of the game
    public int bodyLength; // number of cell, forming snake body
    public int winLevel = 10; // the level of counter, when gamer goes to next
    // level
    public boolean gamerWin = false; // characterize win stage
    public int gameCounter = 0; // count gamer checked point
    public int gameCounterMax = gameLevelMax * gameRoundMax * winLevel;

    public Engine() {
        gameField = new int[30][30];
    }

    private void newAim() {
        while (true) {
            int x = (int) (Math.random() * 30);
            int y = (int) (Math.random() * 30);

            if (gameField[x][y] == 0) {
                gameField[x][y] = -1;
                break;
            }
        }
    }

    // Method start() will start new game: generate new start snake, new aim
    // cell,
    // start snake moving, set counter to zero
    public void start() {
        gameField = generateFieldWithWall(gameLevel);
        newAim();
        gameField[27][15] = 1;
        gameField[28][15] = 2;
        gameField[29][15] = 3;
        bodyLength = 3;
        direction = 1;
        newDirection = direction;
        headX = 27;
        headY = 15;
        endGame = false;
    }

    public int[][] generateFieldWithWall(int inLevel) {

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                gameField[i][j] = 0;
            }
        }

        switch (inLevel) {
            case 0:
                break;

            case 1:
                for (int i = 10; i < 21; i++) {
                    gameField[10][i] = -5;
                }
                break;

            case 2:
                for (int i = 10; i < 21; i++) {
                    gameField[i][10] = -5;
                    gameField[i][20] = -5;
                }
                break;

            case 3:
                for (int i = 10; i < 21; i++) {
                    gameField[10][i] = -5;
                }
                for (int j = 5; j < 16; j++) {
                    gameField[j][15] = -5;
                }
                break;

            case 5:
                for (int i = 10; i < 21; i++) { // copy case 3 and add case 4
                    gameField[10][i] = -5;
                }
                for (int j = 5; j < 16; j++) {
                    gameField[j][15] = -5;
                }

            case 4:
                for (int i = 0; i < 10; i++) {
                    gameField[0][i] = -5;
                    gameField[29][i] = -5;
                    gameField[i][0] = -5;
                    gameField[i][29] = -5;
                }
                for (int i = 20; i < 30; i++) {
                    gameField[0][i] = -5;
                    gameField[29][i] = -5;
                    gameField[i][0] = -5;
                    gameField[i][29] = -5;
                }
                break;
            default:
                break;
        }
        return gameField;
    }

    // this method analyze snake moving direction and
    // generate special number of the event coming next step
    private int headMove() {
        // this block predict what the cell will be on the next snake head
        if (direction == 0) {
            if ((headY - 1) >= 0)
                headY--;
            else
                headY = 29; // variant for "no wall" game
            // else endGame=true; //variant for "inside wall" game
        } else if (direction == 1) {
            if ((headX - 1) >= 0) {
                headX--;
            } else
                headX = 29; // variant for "no wall" game
            // else endGame=true; //variant for "inside wall" game
        } else if (direction == 2) {
            if ((headY + 1) <= 29)
                headY++;
            else
                headY = 0; // variant for "no wall" game
            // else endGame=true; //variant for "inside wall" game
        } else if (direction == 3) {
            if ((headX + 1) <= 29)
                headX++;
            else
                headX = 0; // variant for "no wall" game
            // else endGame=true; //variant for "inside wall" game
        }
        if (gameField[headX][headY] == -1) { // have caught current aim
            newAim();
        }

        // next block analyze next cell for head and generate
        // special number (int) of such operation
        int result = 0; // special number for next step
        if (gameField[headX][headY] == 1)
            result = 0; // general case -> put head into this cell
        if (gameField[headX][headY] == -1)
            result = 1; // caught aim
        else if (gameField[headX][headY] == 0)
            result = 2; // move on free cell
        else if (gameField[headX][headY] > 1)
            result = 3; // will catch snake tail
        else if (gameField[headX][headY] == -5)
            result = 4; // will catch snake tail

        gameField[headX][headY] = -2;
        return result;
    }

    // method reverse() forbid changing of snake moving direction to the
    // opposite
    private void reverse() {
        if ((Math.abs(newDirection - direction)) != 2) {
            direction = newDirection;
        }
    }

    // method moveSnakeBody() analyze next snake step and
    // assign new value for all of the cell, or generate end-icon
    public void moveSnakeBody() {
        if (direction != newDirection)
            reverse();
        int flag = headMove();
        if (flag == 3)
            endGame = true; // if we hit own tail -> stop the game
        if (flag == 4)
            endGame = true; // if we hit the wall
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                if (gameField[x][y] > 0)
                    gameField[x][y]++; // put body here
                else if (gameField[x][y] == -2)
                    gameField[x][y] = 1; // put head here
                if (flag != 1) {
                    if (gameField[x][y] == (bodyLength + 1))
                        gameField[x][y] = 0;
                }
            }
        }
        if (flag == 1) {
            bodyLength++;
            gameCounter++;
            if (gameCounter > 0 && ((gameCounter % winLevel) == 0)) {
                gamerWin = true;
            }
        }
    }
}
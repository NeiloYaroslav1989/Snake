import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class SnakePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Engine snakeEngine;
    private javax.swing.Timer timerRepaint;
    private javax.swing.Timer timerOfSteps;
    private Image aim, background, body, head, endg, win0, win1, win3, wall, winner, rules;
    private JLabel levelBox, roundBox;
    private JButton start, exit, nextLevelButton, nextRoundButton, winnerButton, rulesButton;
    private SnakePanel linkToSnakePanel;

    // this class organize listening for keys,
    // which turn snake into different direction
    public class Keys implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT)
                snakeEngine.newDirection = 0;
            else if (key == KeyEvent.VK_UP)
                snakeEngine.newDirection = 1;
            if (key == KeyEvent.VK_RIGHT)
                snakeEngine.newDirection = 2;
            if (key == KeyEvent.VK_DOWN)
                snakeEngine.newDirection = 3;
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    public SnakePanel() {
        linkToSnakePanel = this;
        this.addKeyListener(new Keys());
        this.setFocusable(true);
        try {
            aim = ImageIO.read(new File("C:\\aim.png"));
            background = ImageIO.read(new File("C:\\background.png"));
            body = ImageIO.read(new File("C:\\body.png"));
            head = ImageIO.read(new File("C:\\head.png"));
            endg = ImageIO.read(new File("C:\\endg.png"));
            win0 = ImageIO.read(new File("C:\\win0.png"));
            win1 = ImageIO.read(new File("C:\\win1.png"));
            win3 = ImageIO.read(new File("C:\\win3.png"));
            wall = ImageIO.read(new File("C:\\wall.png"));
            winner = ImageIO.read(new File("C:\\winner.png"));
            rules = ImageIO.read(new File("C:\\rules.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        snakeEngine = new Engine();

        // This timer describe velocity of snake moving
        timerOfSteps = new javax.swing.Timer(setTimerOfSteps(snakeEngine.gameRound), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!snakeEngine.endGame && !snakeEngine.gamerWin) {
                    snakeEngine.moveSnakeBody();
                }
            }
        });
        timerOfSteps.start();

        // this timer describe frequency of repainting of game field
        timerRepaint = new javax.swing.Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                levelBox.setText("Level: " + snakeEngine.gameLevel + "/" + snakeEngine.gameLevelMax);
                roundBox.setText("Round: " + snakeEngine.gameRound + "/" + snakeEngine.gameRoundMax);
                repaint();
            }
        });
        timerRepaint.start();

        setLayout(null);

        rulesButton = new JButton("", new ImageIcon(rules));
        // rulesButton.setForeground(Color.BLUE);
        rulesButton.setBounds(200, 175, 299, 274);
        rulesButton.setVisible(true);
        rulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                rulesButton.setVisible(false);
                nextLevelButton.setVisible(false);
                start.setFocusable(false);
                exit.setFocusable(false);
                linkToSnakePanel.setFocusable(true);
                snakeEngine.endGame = false;
                snakeEngine.gamerWin = false;
                snakeEngine.start();
            }
        });
        add(rulesButton);

        levelBox = new JLabel("Level: ");
        levelBox.setBounds(630, 270, 150, 50);
        levelBox.setForeground(Color.BLACK);
        levelBox.setFont(new Font("serif", 0, 30));
        add(levelBox);

        // Next block describe button "Count", which
        // looking for gamer result and accelerate snake moving
        roundBox = new JLabel("Round: ");
        roundBox.setBounds(630, 340, 150, 50);
        roundBox.setForeground(Color.BLACK);
        roundBox.setFont(new Font("serif", 0, 30));
        add(roundBox);

        // Next block describe button "Start", which start the game
        start = new JButton();
        start.setText("New game");
        start.setForeground(Color.BLUE);
        start.setFont(new Font("serif", 0, 20));
        start.setBounds(630, 30, 150, 50);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                rulesButton.setVisible(false);
                nextLevelButton.setVisible(false);
                start.setFocusable(false);
                exit.setFocusable(false);
                linkToSnakePanel.setFocusable(true);
                snakeEngine.endGame = false;
                snakeEngine.gamerWin = false;
                snakeEngine.gameRound = 0;
                snakeEngine.start();
            }
        });
        add(start);

        exit = new JButton("EXIT");
        exit.setForeground(Color.BLUE);
        exit.setFont(new Font("serif", 0, 20));
        exit.setBounds(630, 120, 150, 50);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        add(exit);

        nextLevelButton = new JButton("Next level");
        nextLevelButton.setForeground(Color.BLUE);
        nextLevelButton.setFont(new Font("serif", 0, 20));
        nextLevelButton.setBounds(150, 500, 150, 50);
        nextLevelButton.setVisible(false);
        nextLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                snakeEngine.gamerWin = false;
                if (snakeEngine.gameLevel < snakeEngine.gameLevelMax) {
                    snakeEngine.gameLevel++;
                } else {
                    snakeEngine.gameLevel = 0;
                }
                snakeEngine.gameRound = 0;
                timerOfSteps.setDelay(setTimerOfSteps(snakeEngine.gameRound));
                snakeEngine.start();
                nextLevelButton.setVisible(false);
                nextRoundButton.setVisible(false);
                start.setFocusable(false);
                exit.setFocusable(false);

            }
        });
        add(nextLevelButton);

        nextRoundButton = new JButton("Next round");
        nextRoundButton.setForeground(Color.BLUE);
        nextRoundButton.setFont(new Font("serif", 0, 20));
        nextRoundButton.setBounds(400, 500, 150, 50);
        nextRoundButton.setVisible(false);
        nextRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                snakeEngine.gamerWin = false;
                snakeEngine.gameRound++;
                timerOfSteps.setDelay(setTimerOfSteps(snakeEngine.gameRound));
                snakeEngine.start();
                nextRoundButton.setVisible(false);
                nextLevelButton.setVisible(false);
                start.setFocusable(false);
                exit.setFocusable(false);

            }
        });
        add(nextRoundButton);

        winnerButton = new JButton("", new ImageIcon(winner));
        winnerButton.setForeground(Color.BLUE);
        winnerButton.setBounds(130, 75, 373, 476);
        winnerButton.setVisible(false);
        winnerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                snakeEngine.gamerWin = false;
                snakeEngine.gameRound = 0;
                snakeEngine.gameLevel = 0;
                snakeEngine.gameCounter = 0;
                winnerButton.setVisible(false);
                timerOfSteps.setDelay(setTimerOfSteps(snakeEngine.gameRound));
                snakeEngine.start();
                start.setFocusable(false);
                exit.setFocusable(false);
            }
        });
        add(winnerButton);
    }

    // method setTimerOfSteps(int gameRound) - determine the snake speed,
    // according to game round
    private int setTimerOfSteps(int gameRound) {
        int timerOfSnakeSteps = (200 - gameRound * 22);
        return timerOfSnakeSteps;
    }

    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(background, 0, 0, 800, 650, null);
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                if (snakeEngine.gameField[x][y] != 0) {
                    if (snakeEngine.gameField[x][y] == 1) {
                        gr.drawImage(head, 10 + y * 20, 10 + x * 20, 20, 20, null);
                    } else if (snakeEngine.gameField[x][y] == -1) {
                        gr.drawImage(aim, 10 + y * 20, 10 + x * 20, 20, 20, null);
                    } else if (snakeEngine.gameField[x][y] >= 2) {
                        gr.drawImage(body, 10 + y * 20, 10 + x * 20, 20, 20, null);
                    } else if (snakeEngine.gameField[x][y] >= -5) {
                        gr.drawImage(wall, 10 + y * 20, 10 + x * 20, 20, 20, null);
                    }
                }
            }
            gr.setColor(Color.GRAY);
            for (int i = 0; i < 31; i++) {
                gr.drawLine(10 + i * 20, 10, 10 + i * 20, 610);
                gr.drawLine(10, 10 + i * 20, 610, 10 + i * 20);
            }
            if (snakeEngine.gamerWin == true) {
                switch (snakeEngine.gameCounter % 3) {
                    case (1):
                        gr.drawImage(win0, 250, 100, 200, 100, null);
                        break;
                    case (2):
                        gr.drawImage(win1, 250, 100, 200, 100, null);
                        break;
                    default:
                        gr.drawImage(win3, 250, 100, 200, 100, null);
                }
                start.setFocusable(true);
                exit.setFocusable(true);
                if (snakeEngine.gameRound != snakeEngine.gameRoundMax) {
                    nextRoundButton.setVisible(true);
                }
                if (snakeEngine.gameLevel != snakeEngine.gameLevelMax) {
                    nextLevelButton.setVisible(true);
                }

                if (snakeEngine.gameLevel == snakeEngine.gameLevelMax
                        && snakeEngine.gameRound == snakeEngine.gameRoundMax) {
                    winnerButton.setVisible(true);
                }
                ;
            }

            if (snakeEngine.endGame == true) {
                gr.drawImage(endg, 250, 200, 200, 100, null);
            }
        }
    }
}
package ForMy;

import java.util.Random;
import java.util.Scanner;

public class Game1 {
    public static void main(String[] args) {
        String castle = "\uD83C\uDFF0";
        String heart = "\u2764\uFE0F";

        boolean isBossDefeated = false;

        int sizeBoard = 5;
        int step = 0;

        Person person = new Person(sizeBoard);
        Boss boss = new Boss(sizeBoard);

        Random random = new Random();
        int castleX = 1 + random.nextInt(sizeBoard);
        int castleY = 1;

        int heartX = 1 + random.nextInt(sizeBoard);
        int heartY = 2 + random.nextInt(sizeBoard - 1);

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = "  ";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 1;
        Monster[] monsters = new Monster[countMonster];

        for (int i = 0; i < countMonster; i++) {
            Monster monster = new Monster(sizeBoard);
            monsters[i] = monster;
            board[monster.getY() - 1][monster.getX() - 1] = monster.getImage();
        }

        board[castleY - 1][castleX - 1] = castle;
        board[heartY - 1][heartX - 1] = heart;
        board[boss.getY() - 1][boss.getX() - 1] = boss.getImage();

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        System.out.println("Ваш ответ:\t" + answer);

        switch (answer) {
            case "ДА":
                System.out.println("Выбери сложность игры (от 1 до 5):");
                int difficultGame = scanner.nextInt();
                System.out.println("Выбранная сложность:\t" + difficultGame);

                while (true) {
                    board[person.getY() - 1][person.getX() - 1] = person.getImage();
                    outputBoard(board, person.getLive());

                    System.out.println("Введите, куда будет ходить персонаж "
                            + "(ход возможен только по вертикали или горизонтали на одну клетку)"
                            + "\nКоординаты персонажа — (x: " + person.getX()
                            + ", y: " + person.getY() + ")");

                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    System.out.println(x + ", " + y);

                    if (person.isMoveCorrect(x, y)) {
                        if (board[y - 1][x - 1].equals("  ")) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            step++;

                            System.out.println("Ход корректный; новые координаты: "
                                    + person.getX() + ", " + person.getY()
                                    + "\nХод номер: " + step);
                        } else if (board[y - 1][x - 1].equals(castle)) {
                            if (isBossDefeated) {
                                System.out.println("Вы прошли игру!");
                                break;
                            } else {
                                System.out.println("Cначало нужно победить главного БОССА-КОРОЛЬ ДРАКОНОВ");
                            }
                        } else if (board[y - 1][x - 1].equals(boss.getImage())) {
                            if (boss.taskMonster(difficultGame)) {
                                board[person.getY() - 1][person.getX() - 1] = "  ";
                                person.move(x, y);
                                step++;
                                isBossDefeated = true;
                            } else {
                                person.downLive();
                            }
                        } else if (board[y - 1][x - 1].equals(heart)) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            step++;
                            person.upLive();

                        } else {
                            for (Monster monster : monsters) {
                                if (monster.conflictPerson(x, y)) {
                                    if (monster.taskMonster(difficultGame)) {
                                        board[person.getY() - 1][person.getX() - 1] = "  ";
                                        person.move(x, y);
                                        step++;
                                    } else {
                                        person.downLive();
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Некорректный ход");
                    }

                    if (person.getLive() <= 0) {
                        break;
                    }
                }

                if (person.getLive() <= 0) {
                    System.out.println("Закончились жизни. Итог: ...");
                }
                break;

            case "НЕТ":
                System.out.println("Жаль, приходи ещё!");
                break;

            default:
                System.out.println("Данные введены некорректно");
                break;
        }
    }

    static void outputBoard(String[][] board, int live) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] row : board) {
            System.out.println(wall);
            for (String col : row) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.println("Количество жизней:\t" + live + "\n");
    }
}

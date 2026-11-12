package ForMy;

import java.util.Random;
import java.util.Scanner;

public class Boss extends Monster{
    Boss(int sizeBoard) {
        super(sizeBoard);
        setImage("\uD83D\uDC09");
    }

    @Override
    public boolean taskMonster(int difficultGame){
        System.out.println("--- БОСС ФАААААЙТ ---");
        Random random = new Random();
        int a = random.nextInt(20 * difficultGame);
        int b = random.nextInt(20 * difficultGame);
        int trueAnswer = a * b;

        System.out.println("Реши пример: " + a + " * " + b + " = ?");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        if (trueAnswer == answer) {
            System.out.println("И.. Поздравляю ты победил главного босса игры!!!");
            return true;
        } else {
            System.out.println("GAME OVER. Ничего. В следующий раз у тебя точно получиться.");
            return false;
        }
    }
}

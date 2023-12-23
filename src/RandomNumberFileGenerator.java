import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomNumberFileGenerator {
    public static void main(String[] args) {
        int numFiles = 13;
        for (int i = 0; i < numFiles; i++) {
            String filename = "files/file" + (i + 1) + ".txt";
            try {
                FileWriter fileWriter = new FileWriter(filename);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                Random rand = new Random();
                int numNumbers = rand.nextInt(50);

                for (int j = 0; j < numNumbers; j++) {
                    int randomNumber = rand.nextInt(-10, 10);
                    bufferedWriter.write(Integer.toString(randomNumber));
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();
                System.out.println(filename + " успешно создан.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
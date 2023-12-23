import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class SummationThread extends Thread{

    private final SummationCallback callback;
    private int a, b;  // промежуток суммирования


    public SummationThread(int a, int b, SummationCallback callback) {
        this.a = a;
        this.b = b;
        this.callback = callback;
    }

    @Override
    public void run() {
        // считаем сумму в промежутке
        long partOfSum = 0;
        for (int i = a; i < b; i++) {
            String filename = "files/file" + (i + 1) + ".txt";
            try {
//                System.out.println(filename);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    partOfSum += Integer.parseInt(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // через обратный вызов, кидаем результат
        callback.call(partOfSum);
    }
}
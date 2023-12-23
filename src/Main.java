import java.io.*;
import java.util.Random;

public class Main {
    static int FILE_COUNT = 13;
    static int THREAD_COUNT = 3;
    static int sumMany = 0;

    public static void main(String[] args) throws InterruptedException {
        oneWorker();
        manyWorkers();
    }
    private static void oneWorker(){
        long time = System.currentTimeMillis();
        long sumOne = 0;
        for (int i = 0; i < FILE_COUNT; i++) {
            String filename = "files/file" + (i + 1) + ".txt";
            try {
//                System.out.println(filename);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sumOne += Integer.parseInt(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("sumOne =  " + sumOne);
        time = System.currentTimeMillis() - time;
        System.out.println("Main thread worked " + time + "ms and exited");
    }


    private static void manyWorkers() throws InterruptedException {
        long time = System.currentTimeMillis();



        Thread[] threads = new Thread[THREAD_COUNT];
        SummationCallback callback = new SummationCallback() {
            @Override
            public void call(long part) {
                sumMany += part;
            }
        };

        // Создать и запустить
        for (int i = 0; i < THREAD_COUNT; i++){
            int count_files_for_thread = FILE_COUNT / THREAD_COUNT;

            // Определяем, должен ли текущий поток обработать дополнительный файл
            boolean hasExtraFile = i < FILE_COUNT % THREAD_COUNT;

            // Вычисляем индексы файлов, которые должен обработать текущий поток
            int startIndex = i * count_files_for_thread + Math.min(i, FILE_COUNT % THREAD_COUNT);
            int endIndex = startIndex + count_files_for_thread + (hasExtraFile ? 1 : 0);
            threads[i] = new SummationThread(startIndex, endIndex, callback);
            threads[i].start();
        }

        // Подождать последнего или любого из выживших
        for (int i = THREAD_COUNT - 1; i >= 0; i--){
            if (threads[i].isAlive()) {
                System.out.println("Wait thread-" + i );
                threads[i].join();
            }
        }

        System.out.println("sumMany = " + sumMany);
        System.out.println("All threads worked " + (time = System.currentTimeMillis() - time) + "ms and exited");
    }
}

interface SummationCallback {
    void call(long part);
}
package av5.binary;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class BinaryNumbers {
    private static final String FILE_PATH = "C:\\Users\\krist\\IdeaProjects\\napredno\\src\\av5\\binary\\test.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        try {
            writeToFile(n);
            System.out.println(average());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(int n) throws IOException {
        Random random = new Random();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        for (int i = 0; i < n; i++) {
            int num = random.nextInt(1000);
            objectOutputStream.writeInt(num);
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private static double average() throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));

        double count = 0;
        double sum = 0;
        try{
            while(true){
                int num = objectInputStream.readInt();
                sum += num;
                count++;
            }
        }catch (EOFException e){
            System.out.println("END OF FILE WAS REACHED");
        }catch (Exception e){
            e.printStackTrace();
        }


        objectInputStream.close();

        return sum / count;
    }
}

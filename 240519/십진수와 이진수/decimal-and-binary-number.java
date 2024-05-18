import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String N = br.readLine();
        long num = Long.parseLong(N, 2) * 17;

        String answer = Long.toBinaryString(num);
        System.out.println(answer);
    }
}
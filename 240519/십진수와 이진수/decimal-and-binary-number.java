import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String N = br.readLine();
        int num = Integer.parseInt(N, 2) * 17;

        String answer = Integer.toBinaryString(num);
        System.out.println(answer);
    }
}
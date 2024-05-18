import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] square = new int[n][n];

        for (int i = 0; i < n; i++) {
            int num = i + 1;
            for (int j = 0; j < n; j++) {
                square[i][j] = num;
                num *= 2;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(square[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
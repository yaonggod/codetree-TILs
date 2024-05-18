import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] N = br.readLine().toCharArray();

        char[] N02 = new char[N.length + 4];
        Arrays.fill(N02, '0');
        for (int i = 4; i < N.length + 4; i++) {
            N02[i] = N[i - 4];
        }

        // 16배하고 N을 더한다
        char[] N17 = new char[N.length + 4];
        Arrays.fill(N17, '0');
        for (int i = 0; i < N.length; i++) {
            N17[i] = N[i];
        }

        int add = 0;
        int idx = N17.length - 1;

        while (true) {
            if (idx == -1) break;

            if (add == 0) {
                if (N02[idx] == '0' && N17[idx] == '1') {
                    N17[idx] = '1';
                } else if (N02[idx] == '1' && N17[idx] == '0') {
                    N17[idx] = '1';
                } else if (N02[idx] == '1' && N17[idx] == '1') {
                    N17[idx] = '0';
                    add = 1;
                }
            } else {
                if (N02[idx] == '0' && N17[idx] == '0') {
                    N17[idx] = '1';
                    add = 0;
                } else if (N02[idx] == '0' && N17[idx] == '1') {
                    N17[idx] = '0';
                    add = 1;
                } else if (N02[idx] == '1' && N17[idx] == '0') {
                    N17[idx] = '0';
                    add = 1;
                } else {
                    N17[idx] = '1';
                    add = 1;
                }
            }
            
            idx--;
        }

        StringBuilder sb = new StringBuilder();
        if (add == 1) sb.append(1);

        for (char n : N17) {
            sb.append(n);
        }

        System.out.println(sb);
    }
}
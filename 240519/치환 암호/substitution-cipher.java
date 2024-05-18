import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] original = br.readLine().toCharArray();
        char[] solved = new char[original.length];

        char[] rule = br.readLine().toCharArray();
        Map<Character, Integer> idx = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            idx.put(rule[i], i);
        }

        for (int i = 0; i < solved.length; i++) {
            if (original[i] != ' ') {
                solved[i] = (char) ((int) 'a' + idx.get(original[i]));
            } else {
                solved[i] = ' ';
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < solved.length; i++) {
            sb.append(solved[i]);
        }

        System.out.println(sb);
    }
}
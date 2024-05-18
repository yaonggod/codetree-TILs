import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String output = "Hello\n#@#@#@#@#@\nCodeTree\n@#@#@#@#@#\nStudents!";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n - 1; i++) {
            sb.append(output).append("\n\n");
        }
        sb.append(output);
        System.out.println(sb);
    }
}
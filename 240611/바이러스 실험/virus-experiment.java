import java.util.*;
import java.io.*;

public class Main {
    static class Virus implements Comparable<Virus> {
        int x;
        int y;
        int age;

        public Virus(int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }

        @Override
        public int compareTo(Virus v) {
            return this.age - v.age;
        }
    }

    static int n, m, k;
    static PriorityQueue<Virus> pq = new PriorityQueue<>();

    static Queue<Virus> dead = new LinkedList<>();
    static Queue<Virus> parent = new LinkedList<>();

    static int[][] nut;
    static int[][] add;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        nut = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(nut[i], 5);
        }

        add = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                add[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken());
            pq.offer(new Virus(r, c, a));
        }

        for (int i = 0; i < k; i++) {
            step1();
            step2();
            step3();
            step4();
        }

        System.out.println(pq.size());
    }

    static void step1() {
        // 양분 섭취
        PriorityQueue<Virus> newpq = new PriorityQueue<>();
        while (!pq.isEmpty()) {
            Virus v = pq.poll();

            // 내 칸의 양분이 날 먹일 수 있다
            if (nut[v.x][v.y] >= v.age) {
                // 먹인다
                nut[v.x][v.y] -= v.age;
                v.age++;
                newpq.offer(v);
                if (v.age % 5 == 0) {
                    parent.offer(v);
                }
            } else {
                dead.offer(v);
            }
        }

        pq = newpq;
    }

    static void step2() {
        // 죽은게 양분으로 바뀐다
        while (!dead.isEmpty()) {
            Virus v = dead.poll();
            nut[v.x][v.y] += v.age / 2;
        }
    }

    static void step3() {
        // 번식한다
        while (!parent.isEmpty()) {
            Virus v = parent.poll();

            int[] dx = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int d = 0; d < 8; d++) {
                int nx = v.x + dx[d];
                int ny = v.y + dy[d];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                    pq.offer(new Virus(nx, ny, 1));
                }
            }
        }
    }

    static void step4() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nut[i][j] += add[i][j];
            }
        }
    }
}
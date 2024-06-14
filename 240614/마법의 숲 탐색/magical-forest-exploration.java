import java.io.*;
import java.util.*;

public class Main {

    // 숲
    static int r, c, k;
    static int[][] forest;

    // 북 동 남 서
    // 시계 +1 반시계 -1
    static int[] dx = new int[] {-1, 0, 1, 0};
    static int[] dy = new int[] {0, 1, 0, -1};

    // 행 번호에 +1 해야함
    static int answer = 0;

    // 골렘 위치
    static int gx, gy;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        forest = new int[r][c];

        for (int i = 1; i <= k; i++) {
            st = new StringTokenizer(br.readLine());

            // 골렘 정보
            int gc = Integer.parseInt(st.nextToken()) - 1;
            int gd = Integer.parseInt(st.nextToken());
            int gn = i;

            tryGolem(gc, gd, gn);

//            for (int j = 0; j < r; j++) {
//                System.out.println(Arrays.toString(forest[j]));
//            }
        }

        System.out.println(answer);
    }

    static void tryGolem(int gc, int gd, int gn) {

        // 초기 위치 == 골렘의 센터
        gx = -2;
        gy = gc;

        while (true) {
            boolean success = false;
            if (goDown(gx, gy)) {
                success = true;
            }
            if (!success) {
                int newd = spinWest(gx, gy, gd);
                if (newd >= 0) {
                    gd = newd;
                    success = true;
                }

            }
            if (!success) {
                int newd = spinEast(gx, gy, gd);
                if (newd >= 0) {
                    gd = newd;
                    success = true;
                }
            }
            if (!success) break;

            // 다내려왔음
            if (gx == r - 2) break;
        }

        // gx가 1보다 작으면 골렘이 숲에 들어온 게 아님
        if (gx < 1) {
            forest = new int[r][c];
        } else {
            setGolem(gx, gy, gn, gd);
            moveFairy(gx, gy, gn);
        }
    }

    static boolean goDown(int x, int y) {
        // 남쪽으로 한 칸 내려가서
        x++;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            // 좌표 벗어나는 건 상관없음
            // 다른 골렘이 있는게 문제

            if (nx >= 0 && nx < r && ny >= 0 && ny < c) {
                if (forest[nx][ny] != 0) return false;
            }
        }

        // 남쪽으로 한 칸 가기 가능
        gx++;
        return true;
    }

    static int spinWest(int x, int y, int gd) {
        // 왼쪽으로 한 칸 가기
        y--;

        if (y == 0) return -1;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < r && ny >= 0 && ny < c) {
                if (forest[nx][ny] != 0) return -1;
            }
        }

        if (goDown(x, y)) {
            // 성공했음
            gy--;
            int ad = gd - 1;
            if (ad < 0) ad += 4;
            return ad;
        }
        return -1;
    }

    static int spinEast(int x, int y, int gd) {
        // 오른쪽으로 한 칸 가기
        y++;

        if (y == c - 1) return -1;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < r && ny >= 0 && ny < c) {
                if (forest[nx][ny] != 0) return -1;
            }
        }

        if (goDown(x, y)) {
            // 성공했음
            gy++;
            int ad = gd + 1;
            if (ad >= 4) ad -= 4;
            return ad;
        }
        return -1;
    }

    static void setGolem(int x, int y, int gn, int gd) {
        forest[x][y] = gn;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (d == gd) {
                forest[nx][ny] = -gn;
            } else {
                forest[nx][ny] = gn;
            }
        }
    }

    static void moveFairy(int x, int y, int gn) {
        // n == 골렘 요정은 n에서 n이나 -n로만 이동 가능
        // -n == 탈출구 -n에서는 아무 n이나 이동 가능

        int maxr = -1;

        Queue<Integer[]> queue = new LinkedList<>();
        queue.offer(new Integer[] {x, y});

        boolean[][] visited = new boolean[r][c];
        visited[x][y] = true;

        while(!queue.isEmpty()) {
            Integer[] from = queue.poll();
            if (from[0] > maxr) maxr = from[0];

            if (forest[from[0]][from[1]] < 0) {
                for (int d = 0; d < 4; d++) {
                    int nx = from[0] + dx[d];
                    int ny = from[1] + dy[d];

                    if (nx >= 0 && nx < r && ny >= 0 && ny < c) {
                        if (!visited[nx][ny] && forest[nx][ny] != 0) {
                            visited[nx][ny] = true;
                            queue.offer(new Integer[] {nx, ny});
                        }
                    }
                }
            } else if (forest[from[0]][from[1]] > 0) {
                for (int d = 0; d < 4; d++) {
                    int nx = from[0] + dx[d];
                    int ny = from[1] + dy[d];

                    if (nx >= 0 && nx < r && ny >= 0 && ny < c) {
                        if (!visited[nx][ny] && (forest[nx][ny] == forest[from[0]][from[1]] || forest[nx][ny] == -forest[from[0]][from[1]])) {
                            visited[nx][ny] = true;
                            queue.offer(new Integer[] {nx, ny});
                        }
                    }
                }
            }
        }
        if (maxr >= 0) answer += (maxr + 1);
    }
}
import java.util.*;
import java.io.*;


public class Main {
    static class Node {
        int myId;
        int parentId;
        int color;
        int depth;
        List<Integer> children;

        public Node(int myId, int parentId, int color, int depth, List<Integer> children) {
            this.myId = myId;
            this.parentId = parentId;
            this.color = color;
            this.depth = depth;
            this.children = children;
        }
    }

    // 노드
    static Node[] nodes = new Node[100001];

    // 루트
    static List<Integer> root = new ArrayList<>();

    static long value;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 0; t < q; t++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());

            if (command == 100) {
                int m = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                Node newNode = new Node(m, p, c, d, new ArrayList<>());
                addNode(newNode);
            } else if (command == 200) {
                int m = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                changeColor(m, c);
            } else if (command == 300) {
                int m = Integer.parseInt(st.nextToken());
                sb.append(viewColor(m)).append("\n");
            } else if (command == 400) {
                value = 0;
                for (Integer r : root) {
                    subvalue(r);
                }
                sb.append(value).append("\n");
            }
        }

        System.out.println(sb);
    }


    static void addNode(Node newNode) {
        // 내가 루트다
        if (newNode.parentId == -1) {
            root.add(newNode.myId);
            nodes[newNode.myId] = newNode;
            return;
        }


        boolean possible = true;
        // 부모한테 찾아가서 이 노드 넣어도 되는지 허락받기
        // 2부터 시작
        Node parentNode = nodes[newNode.parentId];
        int d = 2;
        while (true) {
            // 지금 부모
            if (parentNode.depth < d) {
                possible = false;
                break;
            }

            d++;

            if (parentNode.parentId == -1) break;

            // 다음 부모 가서 허락받기
            parentNode = nodes[parentNode.parentId];
        }

        if (possible) {
            nodes[newNode.myId] = newNode;
            nodes[newNode.parentId].children.add(newNode.myId);
        }
    }

    static void changeColor(int myId, int color) {
        nodes[myId].color = color;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(myId);

        while (!queue.isEmpty()) {
            Integer from = queue.poll();
            for (Integer to : nodes[from].children) {
                nodes[to].color = color;
                queue.offer(to);
            }
        }

    }

    static int viewColor(int myId) {
        return nodes[myId].color;
    }

    static boolean[] subvalue(int myId) {
        boolean[] v;

        // 내가 자식이면 내꺼 칠해서
        if (nodes[myId].children.size() == 0) {
            v = new boolean[6];
            v[nodes[myId].color] = true;
            value += 1;
            return v;
        }

        v = new boolean[6];
        v[nodes[myId].color] = true;
        for (Integer c : nodes[myId].children) {
            boolean[] cColor = subvalue(c);
            for (int i = 1; i <= 5; i++) {
                if (cColor[i]) v[i] = true;
            }
        }

        int count = 0;
        for (int i = 1; i <= 5; i++) {
            if (v[i]) count++;
        }

        value += count * count;
        return v;
    }
}
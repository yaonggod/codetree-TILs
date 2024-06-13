import java.util.*;
import java.io.*;


public class Main {
    static class Node {
        int myId;
        int parentId;
        int color;
        int depth;
        List<Integer> children;
        boolean[] subColor;

        public Node(int myId, int parentId, int color, int depth, List<Integer> children, boolean[] subColor) {
            this.myId = myId;
            this.parentId = parentId;
            this.color = color;
            this.depth = depth;
            this.children = children;
            this.subColor = subColor;
        }
    }

    // 추가된 노드 
    static Map<Integer, Node> nodeMap = new HashMap<>();

    // 루트
    static List<Integer> root = new ArrayList<>();

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
                Node newNode = new Node(m, p, c, d, new ArrayList<>(), new boolean[6]);
                addNode(newNode);
            } else if (command == 200) {
                int m = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                changeColor(m, c);
            } else if (command == 300) {
                int m = Integer.parseInt(st.nextToken());
                sb.append(viewColor(m)).append("\n");
            } else if (command == 400) {
                long value = calcValue();
                sb.append(value).append("\n");
            }
        }

        System.out.println(sb);
    }


    static void addNode(Node newNode) {
        // 내가 루트다 
        if (newNode.parentId == -1) {
            root.add(newNode.myId);
            nodeMap.put(newNode.myId, newNode);
            paintColor(newNode.myId);
            return;
        }


        boolean possible = true;
        // 부모한테 찾아가서 이 노드 넣어도 되는지 허락받기
        // 2부터 시작
        Node parentNode = nodeMap.get(newNode.parentId);
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
            parentNode = nodeMap.get(parentNode.parentId);
        }

        if (possible) {
            nodeMap.put(newNode.myId, newNode);
            nodeMap.get(newNode.parentId).children.add(newNode.myId);
            paintColor(newNode.myId);
        }
    }

    static void changeColor(int myId, int color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(myId);

        // 하향식으로 칠하고 
        while (!queue.isEmpty()) {
            Integer from = queue.poll();
            Node currentNode = nodeMap.get(from);
            currentNode.color = color;

            // 색 단일화
            boolean[] sc = new boolean[6];
            sc[color] = true;
            currentNode.subColor = sc;
            nodeMap.put(currentNode.myId, currentNode);

            for (Integer to : currentNode.children) {
                queue.offer(to);
            }
        }

        // 내 부모를 찾아가서 나의 색이 바뀌었다고 보고 
        int pid = nodeMap.get(myId).parentId;

        if (pid == -1) return;

        while (true) {
            Node parent = nodeMap.get(pid);
            boolean[] newSub = new boolean[6];
            newSub[parent.color] = true;
            for (Integer c : parent.children) {
                Node child = nodeMap.get(c);

                for (int i = 1; i <= 5; i++) {
                    if (child.subColor[i]) {
                        newSub[i] = true;
                    }
                }
            }
            parent.subColor = newSub;
            nodeMap.put(pid, parent);

            pid = parent.parentId;
            if (pid == -1) break;
        }
        
        calcValue();
        
    }

    static int viewColor(int myId) {
        return nodeMap.get(myId).color;
    }

    static void paintColor(int myId) {
        // 새 노드에서 타고올라가서 부모노드도 칠하기 

        Node currentNode = nodeMap.get(myId);

        // 일단 나부터 칠한다
        currentNode.subColor[currentNode.color] = true;
        nodeMap.put(myId, currentNode);

        boolean[] colored = new boolean[6];
        colored[currentNode.color] = true;

        if (currentNode.parentId == -1) return;

        while (true) {
            currentNode = nodeMap.get(currentNode.parentId);
            colored[currentNode.color] = true;
            for (int i = 1; i <= 5; i++) {
                if (colored[i]) {
                    currentNode.subColor[i] = true;
                }
            }    
            nodeMap.put(currentNode.myId, currentNode);
            if (currentNode.parentId == -1) break;
        }
    }

    static long calcValue() {
        long answer = 0;
        for (Integer r : root) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(r);

            while (!queue.isEmpty()) {
                Integer fromId = queue.poll();
                Node from = nodeMap.get(fromId);

                int count = 0;
                for (int i = 1; i <= 5; i++) {
                    count += from.subColor[i] ? 1 : 0;
                }

                answer += count * count;

                for (Integer to : from.children) {
                    queue.offer(to);
                }
            }
        }
        
        return answer;
    }
}
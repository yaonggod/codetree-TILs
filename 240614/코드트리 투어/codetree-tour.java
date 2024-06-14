import java.util.*;
import java.io.*;

public class Main {
	static class Trip implements Comparable<Trip> {
		int id;
		int revenue;
		int dest;
		int distance;
		
		public Trip(int id, int revenue, int dest, int distance) {
			this.id = id;
			this.revenue = revenue;
			this.dest = dest;
			this.distance = distance;
		}
		
		@Override
		public int compareTo(Trip t) {
			if (this.revenue - this.distance == t.revenue - t.distance) {
				return Integer.compare(this.id, t.id);
			}
			return Integer.compare(t.revenue - t.distance, this.revenue - this.distance);
		}
		
		public String toString() {
			return this.id + " " + this.revenue + " " + this.dest + " " + this.distance;
		}
	}
	
	// 땅
	static List<List<Integer[]>> land = new ArrayList<>();
	
	// 시작점
	static int start = 0;
	
	// 관리 
	static PriorityQueue<Trip> manage = new PriorityQueue<>();
	
	// 취소
	static boolean[] exist = new boolean[30001];
	
	static int q, n, m;
	
	static int[] route;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		q = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			 
			if (command == 100) {
				n = Integer.parseInt(st.nextToken());
				m = Integer.parseInt(st.nextToken());
				
				// 땅 건설 
				for (int j = 0; j < n; j++) {
					land.add(new ArrayList<>());
				}
				
				// 간선
				for (int e = 0; e < m; e++) {
					int v = Integer.parseInt(st.nextToken());
					int u = Integer.parseInt(st.nextToken());
					int w = Integer.parseInt(st.nextToken());
					
					if (v == u) continue;
					
					land.get(v).add(new Integer[] {u, w});
					land.get(u).add(new Integer[] {v, w});
				}
				
				dijkstra();
			}
			
			else if (command == 200) {
				int id = Integer.parseInt(st.nextToken());
				int rev = Integer.parseInt(st.nextToken());
				int dest = Integer.parseInt(st.nextToken());
				int dist = route[dest];
				manage.offer(new Trip(id, rev, dest, dist));
				exist[id] = true;
			}
			
			else if (command == 300) {
				int id = Integer.parseInt(st.nextToken());
				if (exist[id]) {
					exist[id] = false;
				}
				
			}
			
			else if (command == 400) {
				
				Trip bestTrip = null;
				
				// 지워진 게 아닐 경우에만
				while (!manage.isEmpty()) {
					bestTrip = manage.poll();
					if (exist[bestTrip.id]) {
						break;
					} else {
						bestTrip = null;
					}
				}
				
				if (bestTrip == null) {
					sb.append(-1).append("\n");
				} else if (bestTrip.distance == Integer.MAX_VALUE) {
					manage.offer(bestTrip);
					sb.append(-1).append("\n");
				} else if (bestTrip.revenue - bestTrip.distance < 0) {
					manage.offer(bestTrip);
					sb.append(-1).append("\n");
				} else {
					sb.append(bestTrip.id).append("\n");
					exist[bestTrip.id] = false;
				}
			}
			
			else if (command == 500) {
				int newStart = Integer.parseInt(st.nextToken());
				start = newStart;
				
				dijkstra();
				
				PriorityQueue<Trip> newManage = new PriorityQueue<Trip>();
				int size = manage.size();
				
				for (int s = 0; s < size; s++) {
					Trip t = manage.poll();
					Trip newT = new Trip(t.id, t.revenue, t.dest, route[t.dest]);
					
					newManage.offer(newT);
				}
				manage = newManage;
			}
		}
		System.out.println(sb);
	}
	
	static void dijkstra() {
		route = new int[n];
		Arrays.fill(route, Integer.MAX_VALUE);
		
		PriorityQueue<Integer[]> pq = new PriorityQueue<>(new Comparator<Integer[]>() {
			@Override
			public int compare(Integer[] a, Integer[] b) {
				return a[1] - b[1];
			}
		});
		pq.offer(new Integer[] {start, 0});
		route[start] = 0;
		
		
		while (!pq.isEmpty()) {
			Integer[] data = pq.poll();
			
			int from = data[0];
			int d = data[1];
			
			for (Integer[] to : land.get(from)) {
				int arrive = to[0];
				int adddist = to[1];
				
				if (route[arrive] > d + adddist) {
					route[arrive] = d + adddist;
					pq.offer(new Integer[] {arrive, route[arrive]});
				}
			}
		}
	}
	
	
}
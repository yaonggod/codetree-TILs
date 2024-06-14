import java.util.*;
import java.io.*;

public class Main {
	static int[][] land = new int[5][5];
	
	static int k, m;
	
	// 사용하는 유물 정보 
	static int[] pieces;
	
	static int pidx;
	
	static int value = 0;
	static int answer = 0;
	
	static int[] dx = new int[] {-1, 0, 1, 0};
	static int[] dy = new int[] {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		k = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		for (int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				land[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		pieces = new int[m];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++) {
			pieces[i] = Integer.parseInt(st.nextToken());
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < k; i++) {
			value = 0;
			
			// 탐사 진행 
			tryTurn();
			// 최고 가치가 갱신되지 않아서 못찾음 
			if (value == 0) break;
			
			answer = 0;
			
			// 유물 연쇄 획득
			tryTreasure();
			
			// value가 0이 아니므로 필연적으로 0보다 큼 
			if (answer > 0) {
				sb.append(answer).append(" ");
			}
		}
		System.out.println(sb);
	}
	
	static void tryTurn() {
		
		int fx = -1;
		int fy = -1;
		int fd = 0;
		
		for (int i = 0; i <= 2; i++) {
			for (int j = 2; j >= 0; j--) {
				int result1 = turn90(j, i, false);
				int result2 = turn180(j, i, false);
				int result3 = turn270(j, i, false);
				if (result1 > value) {
					fx = j;
					fy = i;
					fd = 1;
					value = result1;
				}
				if (result2 > value) {
					fx = j;
					fy = i;
					fd = 2;
					value = result2;
				}
				if (result3 > value) {
					fx = j;
					fy = i;
					fd = 1;
					value = result3;
				}
			}
		}
		
		// 가치없음 
		if (fx == -1 && fy == -1) {
			return;
		}
		
		if (fd == 1) {
			turn90(fx, fy, true);
		} else if (fd == 2) {
			turn180(fx, fy, true);
		} else if (fd == 3) {
			turn270(fx, fy, true);
		}
	}
	
	// 진짜로 할거냐 말거냐
	static int turn90(int x, int y, boolean forReal) {
		int[][] newLand = new int[5][5];
		
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				int ox = x + i;
				int oy = y + j;
				int nx = x + j;
				int ny = y + 2 - i;
				newLand[nx][ny] = land[ox][oy];
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (newLand[i][j] == 0) {
					newLand[i][j] = land[i][j];
				}
			}
		}
		
		if (!forReal) {
			return treasure(newLand);
		}
		
		copyLand(newLand);
		return 0;
	}
	
	static int turn180(int x, int y, boolean forReal) {
		int[][] newLand = new int[5][5];
		
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				int ox = x + i;
				int oy = y + j;
				int nx = x + 2 - i;
				int ny = y + 2 - j;
				newLand[nx][ny] = land[ox][oy];
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (newLand[i][j] == 0) {
					newLand[i][j] = land[i][j];
				}
			}
		}
		
		if (!forReal) {
			return treasure(newLand);
		}
		
		copyLand(newLand);
		return 0;
	}
	
	static int turn270(int x, int y, boolean forReal) {
		int[][] newLand = new int[5][5];
		
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				int ox = x + i;
				int oy = y + j;
				int nx = x + 2 - j;
				int ny = y + 2 - i;
				newLand[nx][ny] = land[ox][oy];
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (newLand[i][j] == 0) {
					newLand[i][j] = land[i][j];
				}
			}
		}
		
		if (!forReal) {
			return treasure(newLand);
		}
		
		copyLand(newLand);
		return 0;
	}
	
	static void copyLand(int[][] newLand) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				land[i][j] = newLand[i][j];
			}
		}
	}
	
	static int treasure(int[][] newLand) {
		boolean[][] visited = new boolean[5][5];
		
		int total = 0;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!visited[i][j]) {
					visited[i][j] = true;
					int sub = 1;
					int no = newLand[i][j];
					Queue<Integer[]> queue = new LinkedList<>();
					queue.offer(new Integer[] {i, j});
					
					while (!queue.isEmpty()) {
						Integer[] from = queue.poll();
						
						for (int d = 0; d < 4; d++) {
							int nx = from[0] + dx[d];
							int ny = from[1] + dy[d];
							
							if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
								if (!visited[nx][ny] && newLand[nx][ny] == no) {
									visited[nx][ny] = true;
									sub++;
									queue.offer(new Integer[] {nx, ny});
								}
							}
						}
					}
					if (sub >= 3) {
						total += sub;
					}
				}
			}
		}
		return total;
	}
	
	static void tryTreasure() {
		while (true) {
			// 지금 땅에서 유물 찾기
			int total = treasure(land);
			
			if (total == 0) return;
			
			answer += total;
			
			// 유물 지우기
			deleteTreasure();
		}
	}
	
	static void deleteTreasure() {
		boolean[][] visited = new boolean[5][5];
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!visited[i][j]) {
					visited[i][j] = true;
					List<Integer[]> deleteList = new ArrayList<>();
					deleteList.add(new Integer[] {i, j});
					int sub = 1;
					int no = land[i][j];
					Queue<Integer[]> queue = new LinkedList<>();
					queue.offer(new Integer[] {i, j});
					
					while (!queue.isEmpty()) {
						Integer[] from = queue.poll();
						
						for (int d = 0; d < 4; d++) {
							int nx = from[0] + dx[d];
							int ny = from[1] + dy[d];
							
							if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
								if (!visited[nx][ny] && land[nx][ny] == no) {
									visited[nx][ny] = true;
									sub++;
									queue.offer(new Integer[] {nx, ny});
									deleteList.add(new Integer[] {nx, ny});
								}
							}
						}
					}
					if (sub >= 3) {
						for (Integer[] del : deleteList) {
							land[del[0]][del[1]] = 0;
						}
					}
				}
			}
		}
		for (int j = 0; j < 5; j++) {
			for (int i = 4; i >= 0; i--) {
				if (land[i][j] == 0) {
					land[i][j] = pieces[pidx++];
				}
			}
		}
	}
}
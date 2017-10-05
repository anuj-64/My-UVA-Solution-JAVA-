import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Position;

import org.xml.sax.HandlerBase;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Arrays.fill;
import static java.util.Collections.*;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Main {
	public static void main(String[] args) throws Exception {
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		TaskA solver = new TaskA();
		solver.solve(1, in, out);
		out.close();
	}

	static class TaskA {
		public int[] dx = { 1, 0, -1, 0, -1, -1, 1, 1 }, dy = { 0, 1, 0, -1, -1, 1, -1, 1 };
		int MAX = (int) (1e9 + 7);

		// GCD && LCM
		long gcd(long a, long b) {
			return b == 0 ? a : gcd(b, a % b);
		}

		long lcm(long a, long b) {
			return a * (b / gcd(a, b));
		}

		// REverse a String
		String rev(String s) {
			return new StringBuilder(s).reverse().toString();
		}

		boolean isprime[] = new boolean[1000015];
		ArrayList<Integer> al = new ArrayList<>();

		void sieve() {
			Arrays.fill(isprime, true);
			boolean visited[] = new boolean[1000015];
			isprime[0] = false;
			isprime[1] = false;
			for (int i = 2; i * i <= 1000015; i++) {
				visited[i] = true;
				for (int j = i * i; j < 1000015; j += i) {
					if (!visited[j]) {
						visited[j] = true;
						isprime[j] = false;
					}
				}
			}
			for (int i = 2; i < 1000012; i++)
				if (isprime[i])
					al.add(i);
		}

		public void solve(int testNumber, InputReader in, PrintWriter out) throws IOException {
			Scanner sc = new Scanner(System.in);
			int t = sc.nextInt();
			sc.nextLine();
			while (t-- > 0) {
				int p = sc.nextInt();
				sc.nextLine();
				dsu ds = new dsu(p);
				String str = sc.nextLine();
				int res1 = 0, res2 = 0;
				while (str.trim().length() > 0) {
					String[] g = str.split(" ");
					char ch = g[0].charAt(0);
					//out.println(str);
					int a = Integer.parseInt(g[1]), b = Integer.parseInt(g[2]);
					if (ch == 'c')
						ds.union(a, b);
					else {
						if (ds.connected(a, b)) {
							res1++;
						} else {
							res2++;
						}
					}
					if(sc.hasNext())
					str = sc.nextLine();
					else
						break;
				}
				out.println(res1+","+res2);
				if(t>0)
				out.println();

			}
		}
	}

	static class dsu {
		int[] union, size;

		public dsu(int n) {
			union = new int[n + 1];
			size = new int[n + 1];
			for (int i = 0; i <= n; i++) {
				union[i] = i;
				size[i] = 1;
			}
		}

		public int root(int s) {
			while (s != union[s])
				s = union[s];
			return s;
		}

		public boolean connected(int a, int b) {
			return root(a) == root(b);
		}

		public void union(int a, int b) {
			int ea = root(a);
			int eb = root(b);
			if (a == b)
				;
			else if (size[ea] > size[eb]) {
				union[eb] = ea;
				size[ea] += size[eb];
			} else {
				union[ea] = eb;
				size[eb] += size[ea];
			}
		}

	}

	static class pair implements Comparable<pair> {
		public Integer x, y, total;

		public pair(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.total = z;
		}

		public pair() {
			x = 0;
			y = 0;
			total = x + y;
		}

		public int compareTo(pair that) {
			if (this.x != that.x)
				return this.x.compareTo(that.x);
			else
				return this.y.compareTo(that.y);
		}
	}

	static class InputReader {
		private byte[] buf = new byte[8000];
		private int index;
		private int total;
		private InputStream in;
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));

		public InputReader(InputStream stream) {
			in = stream;
		}

		public int scan() {
			if (total == -1)
				throw new InputMismatchException();
			if (index >= total) {
				index = 0;
				try {
					total = in.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (total <= 0)
					return -1;
			}
			return buf[index++];
		}

		public long nextLong() {
			long integer = 0;
			int n = scan();
			while (isWhiteSpace(n))
				n = scan();
			int neg = 1;
			if (n == '-') {
				neg = -1;
				n = scan();
			}
			while (!isWhiteSpace(n)) {
				if (n >= '0' && n <= '9') {
					integer *= 10;
					integer += n - '0';
					n = scan();
				} else
					throw new InputMismatchException();
			}
			return neg * integer;
		}

		private int nextInt() {
			int integer = 0;
			int n = scan();
			while (isWhiteSpace(n))
				n = scan();
			int neg = 1;
			if (n == '-') {
				neg = -1;
				n = scan();
			}
			while (!isWhiteSpace(n)) {
				if (n >= '0' && n <= '9') {
					integer *= 10;
					integer += n - '0';
					n = scan();
				} else
					throw new InputMismatchException();
			}
			return neg * integer;
		}

		public double nextDouble() {
			double doubll = 0;
			int n = scan();
			int neg = 1;
			while (isWhiteSpace(n))
				n = scan();
			if (n == '-') {
				neg = -1;
				n = scan();
			}
			while (!isWhiteSpace(n) && n != '.') {
				if (n >= '0' && n <= '9') {
					doubll *= 10;
					doubll += n - '0';
					n = scan();
				}
			}
			if (n == '.') {
				n = scan();
				double temp = 1;
				while (!isWhiteSpace(n)) {
					if (n >= '0' && n <= '9') {
						temp /= 10;
						doubll += (n - '0') * temp;
						n = scan();
					}
				}
			}
			return neg * doubll;
		}

		private float nextFloat() {
			float doubll = 0;
			int n = scan();
			int neg = 1;
			while (isWhiteSpace(n))
				n = scan();
			if (n == '-') {
				neg = -1;
				n = scan();
			}
			while (!isWhiteSpace(n) && n != '.') {
				if (n >= '0' && n <= '9') {
					doubll *= 10;
					doubll += n - '0';
					n = scan();
				}
			}
			if (n == '.') {
				n = scan();
				double temp = 1;
				while (!isWhiteSpace(n)) {
					if (n >= '0' && n <= '9') {
						temp /= 10;
						doubll += (n - '0') * temp;
						n = scan();
					}
				}
			}
			return neg * doubll;
		}

		public String next() {
			StringBuilder sb = new StringBuilder();
			int n = scan();
			while (isWhiteSpace(n))
				n = scan();
			while (!isWhiteSpace(n)) {
				sb.append((char) n);
				n = scan();
			}
			return sb.toString();
		}

		public String nextLine() {
			int n = scan();
			while (isWhiteSpace(n))
				n = scan();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(n);
				n = scan();
			} while (!isEndOfLine(n));
			return res.toString();
		}

		public boolean isWhiteSpace(int n) {
			if (n == ' ' || n == '\n' || n == '\r' || n == '\t' || n == -1)
				return true;
			return false;
		}

		private boolean isEndOfLine(int c) {
			return c == '\n' || c == '\r' || c == -1;
		}

		/*
		 * /// Input module
		 * 
		 * public int nextInt() { return scanInt(); }
		 * 
		 * public long nextLong() { return scanlong(); }
		 * 
		 * public double nextDouble() { return scandouble(); }
		 * 
		 * public float nextFloat() { return scanfloat(); }
		 * 
		 * public String next() { return scanstring(); }
		 * 
		 * public String nextLine() throws IOException { return scan_nextLine();
		 * }
		 */
		public int[] readintArray(int size) {
			int[] array = new int[size];
			for (int i = 0; i < size; i++) {
				array[i] = nextInt();
			}
			return array;
		}

		public Integer[] readIntegerArray(int size) {
			Integer[] array = new Integer[size];
			for (int i = 0; i < size; i++) {
				array[i] = nextInt();
			}
			return array;
		}
	}

}

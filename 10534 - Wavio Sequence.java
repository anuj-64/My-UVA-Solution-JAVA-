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
			while(sc.hasNext()){
				int n = sc.nextInt();
				int[] a = new int[n];
				int[] b = new int[n];
				int tmp=0;
				for(int i = 0; i < n; i++){
					tmp = sc.nextInt();
					a[i] = tmp;
					b[n-1-i] = tmp;
				}
				int[] temp = new int[n],temp2 = new int[n];
				int[] length = new int[n],length2 = new int[n];
				temp[0] = a[0];
				temp2[0] = b[0];
				length[0] =1;
				length2[0] = 1;
				int pointer = 0,pointer2=0;
				for(int i = 1; i < n; i++){
					if(temp[pointer] < a[i]){
						pointer++;
						temp[pointer] = a[i];
					}else{
						int x =binarySearch(temp, 0,pointer,a[i]);
						if(x<0)x=-x-1;
						temp[x] = a[i];
					}
					length[i] = pointer+1;
					/*for(int ii = 0; ii <= pointer; ii++)
						out.print(temp[ii]+" ");
					out.println();*/
				}
				//out.println("asd ");
				for(int i = 1; i < n; i++){
					if(temp2[pointer2] < b[i]){
						pointer2++;
						temp2[pointer2] = b[i];
					}else{
						int x =binarySearch(temp2, 0,pointer2,b[i]);
						if(x<0)x=-x-1;
						temp2[x] = b[i];
					}
					length2[i] = pointer2+1;
					/*for(int ii = 0; ii <= pointer2; ii++)
						out.print(temp2[ii]+" ");
					out.println();*/
				}
				int res = Integer.MIN_VALUE;
				for(int i = 0; i < n;i++){
					//out.println(temp[i]+" "+length[i]+" "+temp2[n-1-i]+" "+length2[n-1-i]);
					res = max(res,min(length[i],length2[n-1-i]));
				}
				out.println(res*2-1);
			}
		}
		
		public int res(int q,int pos,int pet){
			if(pos==q)
				return pet;
			if(pos>q)
				return Integer.MAX_VALUE;
			return min(res(q, pos+1, pet+1),res(q, 2*pos, pet+2));
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

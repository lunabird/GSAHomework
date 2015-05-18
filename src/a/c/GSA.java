package a.c;

import java.util.ArrayList;

public class GSA {
	int[][] opt;
	String[][] flag;
	String s1;
	String s2;

	public GSA(String s1, String s2) {
		this.s1 = s1;
		this.s2 = s2;
		opt = new int[s2.length() + 1][s1.length() + 1];
		flag = new String[s2.length() + 1][s1.length() + 1];
	}

	public void init() {
		for (int i = 0; i < s2.length() + 1; i++) {
			for (int j = 0; j < s1.length() + 1; j++) {
				opt[i][j] = 0;
				flag[i][j] = " ";
				if (i == s2.length()) {
					opt[i][j] = 2 * (s1.length() - j);
					flag[i][j] = "-";
				}
				if (j == s1.length()) {
					opt[i][j] = 2 * (s2.length() - i);
					flag[i][j] = "|";
				}
				if (i == s2.length() && j == s1.length()) {
					flag[i][j] = ".";
				}
			}
		}
	}

	public void fillTable() {
		for (int i = s2.length() - 1; i >= 0; i--) {
			for (int j = s1.length() - 1; j >= 0; j--) {
				int right = opt[i][j + 1];
				int down = opt[i + 1][j];
				int rightDown = opt[i + 1][j + 1];
				if (s2.charAt(i) == s1.charAt(j)) {
					rightDown += 0;
				} else {
					rightDown += 1;
				}
				if (right <= down) {
					if (rightDown <= right + 2) {
						opt[i][j] = rightDown;
						flag[i][j] = "\\";
					} else {
						opt[i][j] = right + 2;
						flag[i][j] = "-";
					}
				} else {
					if (rightDown <= down + 2) {
						opt[i][j] = rightDown;
						flag[i][j] = "\\";
					} else {
						opt[i][j] = down + 2;
						flag[i][j] = "|";
					}
				}
			}
		}
	}

	public void printTable1() {
		for (int i = 0; i < s2.length() + 2; i++) {
			for (int j = 0; j < s1.length() + 2; j++) {
				if (i == 0) {
					if (j == 0) {
						System.out.print("    ");
					} else {
						if (j == s1.length() + 1) {
							System.out.print("   ");
						} else {
							System.out.print(s1.charAt(j - 1));
							System.out.print("   ");
						}
					}
				} else if (j == 0) {
					if (i == 0) {
						System.out.print("  ");
					} else {
						if (i == s2.length() + 1) {
							System.out.print("  ");
						} else {
							System.out.print(" " + s2.charAt(i - 1));
						}
					}
				} else {
					String s = String.format("%1$4s", opt[i - 1][j - 1]
							+ flag[i - 1][j - 1]);
					System.out.print(s);
				}
			}
			System.out.println();
		}
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		int i = 0;
		int j = 0;
		while (i != s2.length() || j != s1.length()) {
			if (flag[i][j].equals("\\")) {
				list1.add(s1.charAt(j) + "");
				list2.add(s2.charAt(i) + "");
				i++;
				j++;
			} else if (flag[i][j].equals("-")) {
				list2.add("_");
				list1.add(s1.charAt(j) + "");
				j++;
			} else if (flag[i][j].equals("|")) {
				list2.add(s2.charAt(i) + "");
				list1.add("_");
				i++;
			}
		}
		System.out.println("s1:" + list1.toString() + "\ns2:"
				+ list2.toString());
		System.out.println("编辑距离 = " + opt[0][0]);
	}

	public static void main(String[] args) {
		String s1 = "GGGAATCACGAGAGCAGACAGATCACACAGGTTTATGGGTTCTACGACGAGTGTTTA";
		String s2 = "GGGAATCATGAGAGCAGACGATCACACAAGTTTATGGTTTCTATGATGAATGTTTA";
		GSA gsa = new GSA(s1, s2);
		long startTime=System.currentTimeMillis();
		gsa.init();
		gsa.fillTable();
		gsa.printTable1();
		long endTime=System.currentTimeMillis(); 
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	}
}

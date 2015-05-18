package a.c;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class GSA_ex {
	int[] opt1;
	int[] opt2;
	String s1;
	String s2;
	
	public GSA_ex(String s1,String s2){
		this.s1 = s1;
		this.s2 = s2;
		opt1 = new int[s2.length()+1];
		opt2 = new int[s1.length()];
	}
	public void init(){
		for(int i=0;i<s2.length()+1;i++){
			opt1[i] = 2*(s2.length()-i);
		}
		for(int j=0;j<s1.length();j++){
			opt2[j] = 2*(s1.length()-j);
		}		
	}
	public void fillTable(){
		for(int i=s2.length()-1;i>=0;i--){
			int[] opt = new int[s1.length()];
			for(int j=s1.length()-1;j>=0;j--){
				int right ;
				int down ;
				int rightDown ;
				if(j==s1.length()-1){
					right = opt1[i];
					down = opt2[j];
					rightDown = opt1[i+1];
				}else{
					right = opt[j+1];
					down = opt2[j];
					rightDown = opt2[j+1];
				}				
				if(s2.charAt(i)==s1.charAt(j)){
					rightDown += 0;
				}else{
					rightDown += 1;
				}
				if(right<=down){
					if(rightDown<=right+2){
						opt[j] = rightDown;
					}else{
						opt[j] = right+2;
					}
				}else{
					if(rightDown<=down+2){
						opt[j] = rightDown;
					}else{
						opt[j] = down+2;
					}
				}//else
			}//for j
			opt2 = opt;
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException{
		String[] str = readFile("E:\\ecoli10000.txt");
		String s1 = str[0];
		String s2 = str[1];
		GSA_ex gsa = new GSA_ex(s1, s2);	
		long startTime=System.currentTimeMillis();
		gsa.init();
		gsa.fillTable();
		long endTime=System.currentTimeMillis(); 
		System.out.println("编辑距离 = "+gsa.opt2[0]+"\n程序运行时间： "+(endTime-startTime)+"ms");
	}
	
	public static String[] readFile(String path) throws UnsupportedEncodingException, FileNotFoundException{
		File file = new File(path);
		InputStreamReader read = new InputStreamReader (new FileInputStream(file),"UTF-8");
		BufferedReader reader = null;
		String[] str = new String[2];
		
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(read);
			String tempString = null;
			int line = 0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
//				System.out.println("line "+ line +": " +tempString);
				str[line] = tempString;
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return str;
	}
}

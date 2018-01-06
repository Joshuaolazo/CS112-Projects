package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This class is the driver for little search engine
 * 
 * @author Justin May
 *
 */

public class theLittleEngineThatCould {
	static Scanner stdin = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("master document name?:");
		String master = stdin.nextLine(); //getting the document name
		
		LittleSearchEngine tester = new LittleSearchEngine();
		tester.makeIndex(master, "noisewords.txt");
		
		Set<Entry<String, ArrayList<Occurrence>>> x = tester.keywordsIndex.entrySet();
		String y = x.toString();
		StringTokenizer z = new StringTokenizer(y," =");
		System.out.print(z.nextToken());
		while (z.hasMoreTokens()){
			String a = z.nextToken();
			if(a.charAt(0)=='['||a.charAt(0)=='(') {
				String[] holder = a.split("[|(|)|]|,");
				System.out.print(" " +holder[1]+ " " + holder[2]);
			} else {
				System.out.print("\n");
				System.out.print(a+"--");
			}
		}
		
		System.out.print("\n\nenter quit to quit");
		while(true) {
			System.out.print("\n\n");
			System.out.println("keyword 1:");
			String kw1 = stdin.nextLine(); //getting the document name
			if(kw1.equals("quit")) {
				break;
			}
			System.out.println("keyword 2:");
			String kw2 = stdin.nextLine(); //getting the document name
			try {
				ArrayList<String> bob = tester.top5search(kw1, kw2);
				System.out.print("\nFinal Answer: ");
				for(int i=0;i<bob.size();i++) {
					System.out.print(bob.get(i)+" ");
				}
			} catch (java.lang.NullPointerException e) {
				if(tester.top5search(kw1, kw2)==null) {
					System.out.println("is null");
				}
			}
			
			
		}
	}
}

package friends;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class FriendsTest {
	@Rule
	public  ErrorCollector collector = new ErrorCollector();

	@Test
	public void test() throws Exception{
		
		
		Graph g = new Graph(new Scanner(new File("sampleTest.txt")));
	
		
		
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("subtest1_2.txt")))));
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("clqtest4.txt")))));
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("subtest3.txt")))));
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("subtest4.txt")))));
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("assnsample.txt")))));
		System.out.println(Friends.connectors(g = new Graph(new Scanner(new File("conntest6.txt")))));
		
		
		System.out.println(Friends.cliques(g = new Graph(new Scanner(new File("assnsample.txt"))),"rutgers"));
		System.out.println(Friends.cliques(g = new Graph(new Scanner(new File("subtest5.txt"))),"rutgers"));
		

	}

}

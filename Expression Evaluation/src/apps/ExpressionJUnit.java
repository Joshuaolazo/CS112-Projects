package apps;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class ExpressionJUnit {

	@Test
	public void test() {
		Expression test = new Expression("3");
		test.buildSymbols();
		try{
			Scanner scfile = new Scanner(new File("etest1.txt"));
			test.loadSymbolValues(scfile);
		}catch(FileNotFoundException x){
		}catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(3,testEval("3"), 0.0f);
		assertEquals((3.0/2.0)*3.0,testEval("(3/2)*3"), 0.0f);
		assertEquals(16.0+342.0,testEval("16+342"), 0.0f);
		assertEquals(23.0-413.0,testEval("23-413"), 0.0f);
		assertEquals(234+234+4-2+45,testEval("234+234+4-2+45"), 0.0f);
		assertEquals(3.0*234.0/234.0+54.0*3.0,testEval("3*234/234+54*3"), 0.0f);
		assertEquals((23.0+43.0)*(212.0-3.0),testEval("(23+43)*(212-3)"), 0.0f);
		assertEquals((1.0/3.0)*3.0,testEval("(1/3)*3"), 0.0f);
		assertEquals((75.0+(98.0-43.0*(234.0-4.0))),testEval("(75+(98-43*(234-4)))"), 0.0f);
		assertEquals((75.0+(98.0-43.0*(234.0-4.0)))*98.0+(34.0-3.0*9.0),testEval("(75+(98-43*(234-4)))*98+(34-3*9)"), 0.0f);

		assertEquals(1,testEval("a"), 0.0f);
		assertEquals(1+2,testEval("a+b"), 0.0f);
		assertEquals(1+2*3,testEval("a+b*c"), 0.0f);
		assertEquals(1+2*(3+7)*26,testEval("a+b*(c+g)*z"), 0.0f);
		assertEquals(1+2*(3+(7+(4*25)))*26,testEval("a+b*(c+(g+(d*y)))*z"), 0.0f);
		assertEquals(10,testEval("A[6]"), 0.0f);
		assertEquals(0,testEval("A[0]"), 0.0f);
		assertEquals(10,testEval("A[3+3]"), 0.0f);
		assertEquals(10,testEval("A[a+e]"), 0.0f);
		assertEquals(10,testEval("A[z - 20*a]"), 0.0f);
		assertEquals(0,testEval("A    [  1    /    2     ]    "), 0.0f);
		assertEquals(10*10*10+1+2*(3+(7+(4*25)))*26,testEval("A[z - 20*a]*A[z - 20*a]*A[z - 20*a]+a+b*(c+(g+(d*y)))*z"), 0.0f);

		assertEquals(5,testEval("A[A[5]]"), 0.0f);
		assertEquals(5,testEval("A[2+A[3]]"), 0.0f);
		assertEquals(5,testEval("A[b+A[(1+1)+1]]"), 0.0f);
		assertEquals(5,testEval("A[varx-vary+d]"), 0.0f);
		assertEquals(1,testEval("(aa-bb)*(aa-bb)*(aa-bb)*(aa-bb)"),0.0f);
		assertEquals(1-1.5,testEval("aa+(aa-bb)/bb*3"),0.0f);


	}

	
	public Float testEval(String s){
		Expression test = new Expression(s);
		test.buildSymbols();
		try{
			Scanner scfile = new Scanner(new File("etest1.txt"));
			test.loadSymbolValues(scfile);
		}catch(FileNotFoundException x){
		}catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		return test.evaluate();
	}

}

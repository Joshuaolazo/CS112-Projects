package apps;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;

	/**
	 * Scalar symbols in the expression
	 */
	ArrayList<ScalarSymbol> scalars;

	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;

	/**
	 * String containing all delimiters (characters other than variables and
	 * constants), to be used with StringTokenizer
	 */
	public static final String delims = " \t*+-/()[]";

	/**
	 * Initializes this Expression object with an input expression. Sets all
	 * other fields to null.
	 * 
	 * @param expr
	 *            Expression
	 */
	public Expression(String expr) {
		this.expr = expr;
	}

	/**
	 * Populates the scalars and arrays lists with symbols for scalar and array
	 * variables in the expression. For every variable, a SINGLE symbol is
	 * created and stored, even if it appears more than once in the expression.
	 * At this time, values for all variables are set to zero - they will be
	 * loaded from a file in the loadSymbolValues method.
	 */
	public void buildSymbols() {
		String s = this.expr;
		s = s.replaceAll("\\s+", "");
		s = s.trim();
		StringTokenizer st = new StringTokenizer(s, delims);

		StringTokenizer withdelims = new StringTokenizer(s, delims, true);
		ArrayList<ArraySymbol> jim = new ArrayList<ArraySymbol>();
		ArrayList<ScalarSymbol> stefan = new ArrayList<ScalarSymbol>();

		while (st.hasMoreTokens() == true) {
			String tok1 = st.nextToken();
			if (Character.isAlphabetic(tok1.charAt(0))) {
				String tok2 = withdelims.nextToken();
				while (tok1.equals(tok2) != true) {
					tok2 = withdelims.nextToken();
				}
				if (withdelims.hasMoreTokens()) {
					tok2 = withdelims.nextToken();
				}
				if (tok2.equals("[") == true) {
					ArraySymbol seide = new ArraySymbol(tok1);
					if (jim.contains(seide) == false) {
						jim.add(seide);
					}
				}
				if (tok2.equals("[") == false) {
					ScalarSymbol salman = new ScalarSymbol(tok1);
					if (stefan.contains(salman) == false) {
						stefan.add(salman);
					}
				}
			}
		}
		this.arrays = jim;
		this.scalars = stefan;
	}

	private static Stack<String> intoStack(String og) {
		StringTokenizer withdelims = new StringTokenizer(og, delims, true);
		Stack<String> pringles = new Stack<String>();
		while (withdelims.hasMoreTokens()) {
			String tok2 = withdelims.nextToken();
			pringles.push(tok2);
		}
		return pringles;
	}

	/**
	 * Loads values for symbols in the expression
	 * 
	 * @param sc
	 *            Scanner for values input
	 * @throws IOException
	 *             If there is a problem with the input
	 */
	public void loadSymbolValues(Scanner sc) throws IOException {
		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String sym = st.nextToken();
			ScalarSymbol ssymbol = new ScalarSymbol(sym);
			ArraySymbol asymbol = new ArraySymbol(sym);
			int ssi = scalars.indexOf(ssymbol);
			int asi = arrays.indexOf(asymbol);
			if (ssi == -1 && asi == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				scalars.get(ssi).value = num;
			} else { // array symbol
				asymbol = arrays.get(asi);
				asymbol.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					String tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok, " (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					asymbol.values[index] = val;
				}
			}
		}
	}

	/**
	 * Evaluates the expression, using RECURSION to evaluate subexpressions and
	 * to evaluate array subscript expressions.
	 * 
	 * @return Result of evaluation
	 */
	public float evaluate() {
		return recurseeval(this, this.expr);
	}

	private static float adder(Expression ex, String function) {
		String exprs = function;
		Stack<String> Lays = intoStack(exprs);
		float ali = 0;
		String juan = Lays.pop();
		ali = replacer(juan, ex);
		if (Lays.isEmpty()) {
			return ali;
		}
		String oper = Lays.pop();
		String leftovers = "";
		while (Lays.isEmpty() == false) {
			leftovers = Lays.pop() + leftovers;
		}
		function = leftovers;
		if (oper.equals("+")) {
			ali = ali + adder(ex, function);
		}
		if (oper.equals("-")) {
			ali = adder(ex, function) - ali;
		}
		return ali;
	}

	private static float replacer(String var, Expression ex) {
		boolean contains = false;
		int i = 0;
		float dragon;
		int l = ex.scalars.size();
		for (i = 0; i < l; i++) {
			if (ex.scalars.get(i).name.equals(var)) {
				contains = true;
				break;
			}
		}
		if (contains) {
			dragon = ex.scalars.get(i).value;
		} else {
			dragon = Float.parseFloat(var);
		}
		return dragon;
	}

	private static String multi(Expression ex, String function) {
		function = function.replaceAll("\\+\\-", "-");
		function = function.replaceAll("\\-\\-", "+");
		Stack<String> Doritos = intoStack(function);
		float amy = 0;
		float su = 0;
		float ans = 0;
		String sans = "";
		String juan = "";
		String oper = "";
		String dos = "";
		String pek = "";
		String multi = "";
		Boolean neg = false;
		Boolean pos = false;

		while (!Doritos.isEmpty()) {
			juan = Doritos.pop();
			if (Doritos.isEmpty()) {
				return function;
			}
			oper = Doritos.pop();
			if (oper.equals("*")) {
				dos = Doritos.pop();
				break;
			}
			if (oper.equals("-")) {
				if (Doritos.isEmpty() == false) {
					pek = Doritos.peek();
					if (pek.equals("*")) {
						oper = Doritos.pop();
						dos = Doritos.pop();
						neg = true;
						break;
					}
				} else {
					String uni = "\\" + oper + juan;
					String cero = "0-" + juan;
					function = function.replaceAll(uni, cero);
					return function;
				}
			}
			if (oper.equals("+")) {
				if (Doritos.isEmpty() == false) {
					pek = Doritos.peek();
					if (pek.equals("*")) {
						oper = Doritos.pop();
						dos = Doritos.pop();
						pos = true;
						break;
					}
				} else {
					String uni = "\\" + oper + juan;
					String cero = "0+" + juan;
					function = function.replaceAll(uni, cero);
					return function;
				}
			}
		}
		if (neg) {
			multi = dos + "\\" + oper + "\\-" + juan;
		} else if (pos) {
			multi = dos + "\\" + oper + "\\+" + juan;
		} else {
			multi = dos + "\\" + oper + juan;
		}
		amy = replacer(juan, ex);
		su = replacer(dos, ex);
		if (oper.equals("*")) {
			ans = su * amy;
			if (neg) {
				ans = ans * -1;
			}
			sans = Float.toString(ans);
		}
		function = function.replaceAll(multi, sans);
		function = multi(ex, function);
		return function;
	}

	private static String divide(Expression ex, String function) {
		function = function.replaceAll("\\+\\-", "-");
		function = function.replaceAll("\\-\\-", "+");
		Stack<String> Doritos = intoStack(function);
		float amy = 0;
		float su = 0;
		float ans = 0;
		String sans = "";
		String juan = "";
		String oper = "";
		String dos = "";
		String pek = "";
		String multi = "";
		Boolean neg = false;
		Boolean pos = false;

		while (!Doritos.isEmpty()) {
			juan = Doritos.pop();
			if (Doritos.isEmpty()) {
				return function;
			}
			oper = Doritos.pop();
			if (oper.equals("/")) {
				dos = Doritos.pop();
				break;
			}
			if (oper.equals("-")) {
				if (Doritos.isEmpty() == false) {
					pek = Doritos.peek();
					if (pek.equals("/")) {
						oper = Doritos.pop();
						dos = Doritos.pop();
						neg = true;
						break;
					}
				} else {
					String uni = "\\" + oper + juan;
					String cero = "0-" + juan;
					function = function.replaceAll(uni, cero);
					return function;
				}
			}
			if (oper.equals("+")) {
				if (Doritos.isEmpty() == false) {
					pek = Doritos.peek();
					if (pek.equals("/")) {
						oper = Doritos.pop();
						dos = Doritos.pop();
						pos = true;
						break;
					}
				} else {
					String uni = "\\" + oper + juan;
					String cero = "0+" + juan;
					function = function.replaceAll(uni, cero);
					return function;
				}
			}
		}
		if (neg) {
			multi = dos + "\\" + oper + "\\-" + juan;
		} else if (pos) {
			multi = dos + "\\" + oper + "\\+" + juan;
		} else {
			multi = dos + "\\" + oper + juan;
		}
		amy = replacer(juan, ex);
		su = replacer(dos, ex);
		if (oper.equals("/")) {
			ans = su / amy;
			if (neg) {
				ans = ans * -1;
			}
			sans = Float.toString(ans);
		}
		function = function.replaceAll(multi, sans);
		function = multi(ex, function);
		return function;
	}

	private static String parensgod(Expression ex, String function) {
		Stack<String> Wise = intoStack(function);
		String close = "";
		String innards = "";
		String opens = "";
		String full = "";
		String sans = "";
		int count = 0;
		if (Wise.isEmpty()) {
			return function;
		}
		while (!Wise.isEmpty()) {
			close = Wise.pop();
			if (close.equals(")")) {
				count++;
				opens = Wise.pop();
				while (count != 0) {
					if (opens.equals("("))
						count--;
					if (count == 0)
						break;
					innards = opens + innards;
					if (opens.equals(")"))
						count++;
					opens = Wise.pop();
				}
				break;
			}
			if (Wise.isEmpty()) {
				return function;
			}
		}
		full = opens + innards + close;
		String reg = toReg(full);
		Float Phuru = recurseeval(ex, innards);
		sans = Float.toString(Phuru);
		function = function.replaceAll(reg, sans);
		function = parensgod(ex, function);
		return function;
	}

	private static Float recurseeval(Expression ex, String function) {
		String s = function;
		s = s.replaceAll("\\s+", "");
		s = s.trim();
		String arrays = arraysgod(ex, s);
		String parens = parensgod(ex, arrays);
		String divider = divide(ex, parens);
		String multi = multi(ex, divider);
		Float add = adder(ex, multi);
		return add;
	}

	private static String toReg(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/'
					|| s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == ']' || s.charAt(i) == '[') {
				s = s.substring(0, i) + "\\" + s.substring(i);
				i++;
			}
		}
		return s;
	}

	private static String arraysgod(Expression ex, String function) {
		Stack<String> Wise = intoStack(function);
		String close = "";
		String innards = "";
		String opens = "";
		String full = "";
		String sans = "";
		int scount = 0;
		if (Wise.isEmpty()) {
			return function;
		}
		while (!Wise.isEmpty()) {
			close = Wise.pop();
			if (Wise.isEmpty()) {
				return function;
			}
			if (close.equals("]")) {
				scount++;
				opens = Wise.pop();
				while (scount != 0) {
					if (opens.equals("["))
						scount--;
					if (scount == 0)
						break;
					innards = opens + innards;
					if (opens.equals("]"))
						scount++;
					opens = Wise.pop();
				}
				break;
			}
		}
		String reg = toReg(innards);
		Float Phuru = recurseeval(ex, innards);
		sans = Float.toString(Phuru);
		function = function.replaceAll(reg, sans);
		String var = Wise.pop();
		int index = Phuru.intValue();
		float value = arrayreplacer(var, ex, index);
		full = var + opens + innards + close;
		String reg2 = toReg(full);
		String sans2 = Float.toString(value);
		function = function.replaceAll(reg2, sans2);
		function = arraysgod(ex, function);
		return function;
	}

	private static float arrayreplacer(String var, Expression ex, int index) {
		boolean contains = false;
		int i = 0;
		float dragon;
		int l = ex.arrays.size();
		for (i = 0; i < l; i++) {
			if (ex.arrays.get(i).name.equals(var)) {
				contains = true;
				break;
			}
		}
		if (contains) {
			dragon = ex.arrays.get(i).values[index];
		} else {
			dragon = Float.parseFloat(var);
		}
		return dragon;
	}

	/**
	 * Utility method, prints the symbols in the scalars list
	 */
	public void printScalars() {
		for (ScalarSymbol ss : scalars) {
			System.out.println(ss);
		}
	}

	/**
	 * Utility method, prints the symbols in the arrays list
	 */
	public void printArrays() {
		for (ArraySymbol as : arrays) {
			System.out.println(as);
		}
	}

}
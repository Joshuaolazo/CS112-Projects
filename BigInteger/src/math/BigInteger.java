package math;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer
 * with any number of digits, which overcomes the computer storage length
 * limitation of an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;

	/**
	 * Number of digits in this integer
	 */
	int numDigits;

	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST
	 * node. For instance, the integer 235 would be stored as: 5 --> 3 --> 2
	 */
	DigitNode front;

	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}

	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first
	 * character (no sign means positive), and at least one digit character
	 * (including zero). Examples of correct format, with corresponding values
	 * Format Value +0 0 -0 0 +123 123 1023 1023 0012 12 0 0 -123 -123 -001 -1
	 * +000 0
	 * 
	 * 
	 * @param integer
	 *            Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer
	 * @throws IllegalArgumentException
	 *             If input is incorrectly formatted
	 */

	// add front to save on typing later
	private static DigitNode addFront(int i, DigitNode front) {
		return new DigitNode(i, front);
	}

	public static BigInteger parse(String integer) throws IllegalArgumentException {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger

		BigInteger Parse = new BigInteger();
		integer = integer.trim();
		int NumZeros = 0, length = integer.length();

		// Checks the first char.
		if (integer.charAt(0) == '-' || integer.charAt(0) == '+') {
			// If the first character is a negative sign, set the boolean to
			// true.
			if (integer.charAt(0) == '-') {
				Parse.negative = true;
			}
			// takes off the sign from the string.
			integer = integer.substring(1);
			length = integer.length();
		}

		// after taking off the sign, now check if all characters are numbers.
		for (int i = 0; i < length; i++) {
			// throws an exception if digit is not a number.
			if (!Character.isDigit(integer.charAt(i))) {
				throw new IllegalArgumentException();
			}
		}

		// counts the leading zeros to be parsed
		while (NumZeros != length - 1 && Character.getNumericValue(integer.charAt(NumZeros)) == 0) {
			NumZeros++;
		}

		// starts the DigitNode at the first non-zero number
		DigitNode Build = new DigitNode(Character.getNumericValue(integer.charAt(NumZeros)), null);
		Parse.numDigits++;

		// puts each character into a DigitNode, starting at the second
		// character after the zeros
		for (int j = NumZeros + 1; j < length; j++) {
			// Adds to the front using a private addFront method
			Build = addFront(Character.getNumericValue(integer.charAt(j)), Build);
			Parse.numDigits++;
		}
		Parse.front = Build;
		// if the answer is 0, then take away the negative sign
		if (Parse.front.digit == 0 && Parse.numDigits == 1)
			Parse.negative = false;
		return Parse;
	}

	/**
	 * Adds an integer to this integer, and returns the result in a NEW
	 * BigInteger object. DOES NOT MODIFY this integer. NOTE that either or both
	 * of the integers involved could be negative. (Which means this method can
	 * effectively subtract as well.)
	 * 
	 * @param other
	 *            Other integer to be added to this integer
	 * @return Result integer
	 */

	// Modified deleteFront to save typing
	private static DigitNode deleteFront(DigitNode front) {
		// if delete front deletes the only node, then a new node with a value
		// of 0 is returned as a place holder.
		if (front.next == null) {
			return new DigitNode(0, null);
		}
		return front.next;
	}

	// adds to the back using a temp node as a pointer
	private static DigitNode addBack(int i, DigitNode front) {
		DigitNode temp = front;
		while (temp.next != null)
			temp = temp.next;
		temp.next = new DigitNode(i, null);
		return front;
	}

	// Converts the DigitNode to a string in the correct order, used for reference
	private static String fullPrint(DigitNode front) {
		String s = "";
		while (front.next != null) {
			s = front.toString() + s;
			front = front.next;
		}
		if (front.next == null)
			s = front.toString() + s;
		return s;
	}
	
	//parses the zeros
	private static DigitNode noZeros(DigitNode front) {
		DigitNode temp = front;
		//ends if there is only one digit left
		if( temp.next== null){
			return front;
		}
		//uses 2 place holders to go through
		DigitNode pointer = temp.next;
		while (pointer.next != null){
			temp = temp.next;
			pointer = pointer.next;
		}
		// deletes the last node if it is a zero
		if(pointer.digit == 0){
			temp.next = null;
			return noZeros(front);
		}else{	
			return front;
		}
	}
	
	// only use when same number of digits between nodes
	private static Boolean isBigger2(DigitNode front, DigitNode other) {
		boolean big = false;
		int numdigfront= digitCounter(front);	
		int numdigother= digitCounter(other);
		if(numdigother != numdigfront)
			return null;
		// for loop compares the individual digits of the linked list, the 2 digitnodes are the same length.
		for(int i =numdigfront;i>0;i--){
			if( front.digit>other.digit){
				big = true;
			}
			front = deleteFront(front);
			other = deleteFront(other);
		}
		return big;
	}
	
	// goes through entire linked list to count number of digits
	private static int digitCounter(DigitNode front) {
		int i = 1;
		while (front.next != null){
			front = front.next;
			i++;
		}
		return i;
	}


	public BigInteger add(BigInteger other) {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger

		// Initialize variables needed
		BigInteger Final = new BigInteger();
		BigInteger top;
		BigInteger bot;
		int Toplength = 0;
		int sum = 0;
		int carry = 0;

		// Order Matters!

		// if the first number is shorter than the second number
		// or if the number of digits are the same
		// and the only the second number is negative
		// the top of the add equation is the second number
		if ((this.numDigits < other.numDigits)
				|| ((this.numDigits == other.numDigits) && isBigger2(this.front, other.front)==false)) {
			Toplength += other.numDigits;
			top = other;
			bot = this;
		} else {
			Toplength += this.numDigits;
			top = this;
			bot = other;
		}

		// Made new digit node objects so I wouldn't have to type top.front all
		// the time
		DigitNode TopNode = top.front;
		DigitNode BotNode = bot.front;
		DigitNode adder = new DigitNode(0,null);

		// Adding & Subtracting

		// the loop will go the length of the top number
		while (Toplength != 0) {
			// if the numbers are the same sign, add them
			if (top.negative == bot.negative) {
				// if adding two negative numbers, the final answer will be
				// negative
				if (top.negative)
					Final.negative = true;
				// reset sum and add the carry and the new sum
				sum = 0;
				sum += carry;
				carry = 0;
				sum += TopNode.digit + BotNode.digit;
				// to carry
				if (sum >= 10) {
					// if there is no node to carry, append to the back of the
					// top node
					if (TopNode.next == null) {
						TopNode = addBack(0, TopNode);
						Toplength++;
					}
					carry++;
					sum -= 10;
				}
			} else {
				// if adding a bigger negative number, the final answer will be
				// negative
				if (top.negative)
					Final.negative = true;
				sum = TopNode.digit - BotNode.digit;
				// to borrow
				if (sum < 0) {
					// if there is no node to borrow, append to the back of the
					// top node
					if (TopNode.next == null) {
						TopNode = addBack(0, TopNode);
						Toplength++;
					}
					TopNode.next.digit--;
					sum += 10;
				}
			}

			// Adds the sum to back of the node and deletes the front, so the
			// size stays the same
			// the while loop will end in such a way that the sum of the first
			// node will become the first node again
			adder= addBack(sum,adder);
			TopNode = deleteFront(TopNode);
			BotNode = deleteFront(BotNode);
			// Decrement the counter
			Toplength--;
		}
		// If there is a carry left over, adds to the back
		if (carry != 0) {
			adder= addBack(carry,adder);
		}

		// Setting the Final Answer

		adder = deleteFront(adder);
		// Printing for my reference, doesn't do anything
		System.out.println(fullPrint(adder) + " sum");
		// sets the final DigitNode as a Parsed version of itself
		Final.front = noZeros(adder);
		// Corrects the number of Digits
		Final.numDigits = digitCounter(adder);
		// if the answer is 0, then take away the negative sign
		if (Final.front.digit == 0 && Final.numDigits == 1)
			Final.negative = false;
		return Final;
	}

	/**
	 * Returns the BigInteger obtained by multiplying the given BigInteger with
	 * this BigInteger - DOES NOT MODIFY this BigInteger
	 * 
	 * @param other
	 *            BigInteger to be multiplied
	 * @return A new BigInteger which is the product of this BigInteger and
	 *         other.
	 */
	public BigInteger multiply(BigInteger other) {
		// THE FOLLOWING LINE IS A PLACEHOLDER SO THE PROGRAM COMPILES
		// YOU WILL NEED TO CHANGE IT TO RETURN THE APPROPRIATE BigInteger

		// Initialize variables needed
		BigInteger Final = new BigInteger();
		BigInteger top;
		BigInteger bot;
		int Toplength = 0;
		int multiple = 0;
		int Botlength = 0;

		// if only one of the numbers are negative, the final answer is negative
		if (this.negative != other.negative)
			Final.negative = true;

		// this time, it only matters to put the longer number on top. the
		// values inside don't matter
		if (this.numDigits < other.numDigits) {
			Toplength += other.numDigits;
			Botlength += this.numDigits;
			top = other;
			bot = this;
		} else {
			Toplength += this.numDigits;
			Botlength += other.numDigits;
			top = this;
			bot = other;
		}

		// Made new digit node objects so I wouldn't have to type something like
		// top.front all the time
		DigitNode TopNode = top.front;
		DigitNode BotNode = bot.front;
		DigitNode S = new DigitNode(0, null);
		int carry = 0;

		// double for loops for each number
		for (int j = 0; j < Botlength; j++) {
			DigitNode multi = new DigitNode(0, null);
			TopNode = top.front;
			for (int i = Toplength; i > 0; i--) {
				multiple = 0;
				multiple += carry;
				carry = 0;
				multiple += TopNode.digit * BotNode.digit;
				// to carry
				while (multiple >= 10) {
					multiple -= 10;
					carry++;
				}
				multi = addBack(multiple, multi);
				TopNode = deleteFront(TopNode);
			}
			// carry if the last digit needs it
			if (carry != 0) {
				multi = addBack(carry, multi);
				carry = 0;
			}
			// deleting the initial zero
			multi = deleteFront(multi);
			// adding zeros for the next row of multiply
			int zeros = j;
			while (zeros != 0) {
				multi = addFront(0, multi);
				zeros--;
			}

			// converting to big int to add
			BigInteger Multiple = new BigInteger();
			Multiple.front=noZeros(multi);	
			Multiple.numDigits = digitCounter(multi);
			BigInteger Sum = new BigInteger();
			Sum.front=noZeros(S);	
			Sum.numDigits = digitCounter(S);

			System.out.println(fullPrint(S) + " + " + fullPrint(multi) +" =");
			BigInteger C = Sum.add(Multiple);
			S = C.front;
			// to move on to the next number
			BotNode = deleteFront(BotNode);
		}
		// Setting the Final Answer

		// Printing for my reference, doesn't do anything
		System.out.println(fullPrint(S) + " multiple");
		// sets the final DigitNode as a Parsed version of itself
		Final.front = noZeros(S);
		// Corrects the number of Digits
		Final.numDigits = digitCounter(S);
		// if the answer is 0, then take away the negative sign
		if (Final.front.digit == 0 && Final.numDigits == 1)
			Final.negative = false;

		return Final;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}

		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
			retval = curr.digit + retval;
		}

		if (negative) {
			retval = '-' + retval;
		}

		return retval;
	}

}

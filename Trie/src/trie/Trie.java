package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie.
 *
 * @author Sesh Venugopal
 *
 */
public class Trie {

	// prevent instantiation
	private Trie() {
	}

	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!) The words in
	 * the input array are all lower case.
	 *
	 * @param allWords
	 *            Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/

		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION

		String begin = allWords[0];
		int lel = begin.length();
		Indexes beginner = new Indexes(0, (short) 0, (short) (lel - 1));
		TrieNode seide = new TrieNode(beginner, null, null);

		int l = allWords.length;
		for (int i = 1; i < l; i++) {
			builder( seide, allWords, i,0);
		}
		TrieNode root = new TrieNode(null, seide, null);
		return root;
	}

	private static boolean builder(TrieNode root, String[] allWords, int searchindex, int matchcounter ){
		String comparingsubstring = allWords[root.substr.wordIndex].substring(0,root.substr.endIndex +1);
		String search = allWords[searchindex];
		int i = 0;
		// Loop compares each character for match, breaks if there is a mismatch
		while( ( i< search.length())  && ( i< comparingsubstring.length())  ){
			char compchar = comparingsubstring.charAt(i);
			char searchchar = search.charAt(i);
			if( compchar == searchchar){
			}else{
				break;
			}
			i++;
		}
		int end = i;
		boolean fullmatch = (end==comparingsubstring.length() );
		if(end<matchcounter){
			// returns false if no action is needed
			return false;
		}
		if(end==matchcounter){
			// no additional matches
			boolean insib= false;
			if(root.sibling!=null){
				insib = builder( root.sibling, allWords, searchindex,matchcounter);
			}
			if(!insib){
				boolean inchild = false;
				if(root.firstChild != null){
					inchild = builder(root.firstChild, allWords, searchindex,matchcounter+1);
				}
				if(!inchild){
					// for no matches
					Indexes addednode =  new Indexes(searchindex, (short) matchcounter,  (short) (search.length()-1) );
					TrieNode nomatches =new TrieNode(addednode,null,null);
					root.sibling= nomatches;
				}
			}
		}
		if(end>matchcounter){
			//checks for full matches
			boolean inchild = false;
			boolean insib= false;
			if( fullmatch){
				if(root.firstChild != null){
					// siblings will have the same match number 
					inchild = builder(root.firstChild, allWords, searchindex,end);	
				}
			}else{
				if(insib==false && inchild == false){
					//for partial matches
					//makes a new node for the old root
					TrieNode oldroot = new TrieNode (root.substr,root.firstChild,root.sibling);
					
					Indexes adjustedroot = new Indexes(root.substr.wordIndex, (short)  end, root.substr.endIndex);
					oldroot.substr= adjustedroot;
		
					//changes the root to the smaller index
					Indexes newroot = new Indexes(root.substr.wordIndex, (short) (matchcounter) , (short) (end-1));
					root.substr = newroot;
		
					//adds new node as the first child and old root as its sibling.
					Indexes addednode =  new Indexes(searchindex, (short) (end), (short) (search.length()-1) );
					TrieNode newnode =new TrieNode(addednode,null,null);
					oldroot.sibling = newnode;
					root.firstChild= oldroot;
				}
			}
		}
		// returns true at the end of an action
		return true;
	}

	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the
	 * leaf nodes in the trie whose words start with this prefix. For instance,
	 * if the trie had the words "bear", "bull", "stock", and "bell", the
	 * completion list for prefix "b" would be the leaf nodes that hold "bear",
	 * "bull", and "bell"; for prefix "be", the completion would be the leaf
	 * nodes that hold "bear" and "bell", and for prefix "bell", completion
	 * would be the leaf node that holds "bell". (The last example shows that an
	 * input prefix can be an entire word.) The order of returned leaf nodes
	 * DOES NOT MATTER. So, for prefix "be", the returned list of leaf nodes can
	 * be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root
	 *            Root of Trie that stores all words to search on for completion
	 *            lists
	 * @param allWords
	 *            Array of words that have been inserted into the trie
	 * @param prefix
	 *            Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with
	 *         the prefix, order of leaf nodes does not matter. If there is no
	 *         word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/

		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		ArrayList<TrieNode> seide = new ArrayList<TrieNode>();
		TrieNode trunk= root.firstChild;
		// breaks since root has no string so check first child
		search( trunk, allWords,prefix,seide);
		if(seide.isEmpty())
			return null;
		return seide;
	}
	private static void search( TrieNode root, String[] allWords, String prefix,ArrayList<TrieNode> list ){
		// root string
		String comparingsubstring = allWords[root.substr.wordIndex].substring(0,root.substr.endIndex +1);
		int i = 0;
		// Compares the prefix to the current root
		while( ( i< prefix.length())  && ( i< comparingsubstring.length())  ){
			char compchar = comparingsubstring.charAt(i);
			char searchchar = prefix.charAt(i);
			if( compchar == searchchar){
			}else{
				break;
			}
			i++;
		}
		// checks all siblings for match
		if(root.sibling!=null){
			search( root.sibling, allWords, prefix,list);
		}
		// only checks children if parent matches
		if( i >= 0 ){
			if(root.firstChild != null){
				search( root.firstChild, allWords, prefix,list);
			}
			if(root.firstChild == null && i== prefix.length()){
				list.add(root);	
			}
		}		
		return;
	}

	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}

	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i = 0; i < indent - 1; i++) {
			System.out.print("    ");
		}

		if (root.substr != null) {
			String pre = words[root.substr.wordIndex].substring(0, root.substr.endIndex + 1);
			System.out.println("      " + pre);
		}

		for (int i = 0; i < indent - 1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}

		for (TrieNode ptr = root.firstChild; ptr != null; ptr = ptr.sibling) {
			for (int i = 0; i < indent - 1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent + 1, words);
		}
	}
}

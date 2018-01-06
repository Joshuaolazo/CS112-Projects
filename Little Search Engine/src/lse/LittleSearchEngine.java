package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages
 * in which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and
	 * the associated value is an array list of all occurrences of the keyword
	 * in documents. The array list is maintained in DESCENDING order of
	 * frequencies.
	 */
	HashMap<String, ArrayList<Occurrence>> keywordsIndex;

	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;

	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String, ArrayList<Occurrence>>(1000, 2.0f);
		noiseWords = new HashSet<String>(100, 2.0f);
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of
	 * keyword occurrences in the document. Uses the getKeyWord method to
	 * separate keywords from other words.
	 * 
	 * @param docFile
	 *            Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated
	 *         with an Occurrence object
	 * @throws FileNotFoundException
	 *             If the document file is not found on disk
	 */
	public HashMap<String, Occurrence> loadKeywordsFromDocument(String docFile) throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		HashMap<String, Occurrence> instance = new HashMap<String, Occurrence>();
		Scanner sc = new Scanner(new File(docFile));
		while (sc.hasNext()) {
			String word = sc.next();
			word = getKeyword(word);
			int counter = 0;
			if (instance.containsKey(word)) {
				counter = instance.get(word).frequency;
			}
			Occurrence number = new Occurrence(docFile, 1 + counter);
			if (word != null) {
				if (counter > 0) {
					instance.remove(word);
				}
				instance.put(word, number);
			}
		}
		sc.close();
		return instance;
	}

	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document must
	 * be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash
	 * table. This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws
	 *            Keywords hash table for a document
	 */ // keywordsIndex
	public void mergeKeywords(HashMap<String, Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		for (String key : kws.keySet()) {
			if (keywordsIndex.containsKey(key)) {
				ArrayList<Occurrence> copy = new ArrayList<Occurrence>();
				copy = keywordsIndex.get(key);
				copy.add(kws.get(key));
				insertLastOccurrence(copy);
				keywordsIndex.remove(key);
				keywordsIndex.put(key, copy);
			} else {
				ArrayList<Occurrence> knew = new ArrayList<Occurrence>();
				knew.add(kws.get(key));

				keywordsIndex.put(key, knew);
			}

		}
	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped
	 * of any trailing punctuation, consists only of alphabetic letters, and is
	 * not a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word
	 *            Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		// trims and makes word lowercase
		word = word.trim();
		word = word.toLowerCase();
		// Finds the trailing character
		int l = word.length();
		char lastletter = word.charAt(l - 1);
		// if trailing character is part of the list of punctuation characters,
		// delete it.
		while (Character.isLetter(lastletter) != true) {
			if (lastletter == '.' || lastletter == ',' || lastletter == '?' || lastletter == ':' || lastletter == ';'
					|| lastletter == '!') {
				word = word.substring(0, l - 1);
			} else {
				break;
			}
			l = word.length();
			if (l == 0) {
				return null;
			}
			lastletter = word.charAt(l - 1);
		}

		for (int i = 0; i < l; i++) {
			char letteritteration = word.charAt(l - 1 - i);
			if (Character.isLetter(letteritteration) != true) {
				return null;
			}
		}
		// returns word if it is not a noise word
		if (!noiseWords.contains(word)) {
			return word;
		}
		return null;
	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position
	 * in the list, based on ordering occurrences on descending frequencies. The
	 * elements 0..n-2 in the list are already in the correct order. Insertion
	 * is done by first finding the correct spot using binary search, then
	 * inserting at that spot.
	 * 
	 * @param occs
	 *            List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the
	 *         binary search process, null if the size of the input list is 1.
	 *         This returned array list is only used to test your code - it is
	 *         not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		int i = 0;
		ArrayList<Integer> mid = new ArrayList<Integer>();
		int size = occs.size() - 2;
		if (size == 0) {
			if (occs.get(0).frequency < occs.get(1).frequency) {
				occs.add(occs.get(0));
				occs.remove(0);
			}
			return null;
		}
		Occurrence last = occs.get(occs.size() - 1);
		while (i <= size) {
			int midpoint = (i + size) / 2;
			mid.add(midpoint);
			int compare = occs.get(midpoint).frequency;
			if (compare == last.frequency) {
				break;
			}
			if (compare > last.frequency)
				i = midpoint + 1;
			else
				size = midpoint - 1;
		}
		occs.remove(occs.size() - 1);
		int ins = mid.get(mid.size() - 1);
		if (occs.get(ins).frequency > last.frequency)
			ins++;
		occs.add(ins, last);
		return mid;
	}

	/**
	 * This method indexes all keywords found in all the input documents. When
	 * this method is done, the keywordsIndex hash table will be filled with all
	 * keywords, each of which is associated with an array list of Occurrence
	 * objects, arranged in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile
	 *            Name of file that has a list of all the document file names,
	 *            one name per line
	 * @param noiseWordsFile
	 *            Name of file that has a list of noise words, one noise word
	 *            per line
	 * @throws FileNotFoundException
	 *             If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String, Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or
	 * kw2 occurs in that document. Result set is arranged in descending order
	 * of document frequencies. (Note that a matching document will only appear
	 * once in the result.) Ties in frequency values are broken in favor of the
	 * first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is
	 * in doc2 also with the same frequency f1, then doc1 will take precedence
	 * over doc2 in the result. The result set is limited to 5 entries. If there
	 * are no matches at all, result is null.
	 * 
	 * @param kw1
	 *            First keyword
	 * @param kw1
	 *            Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in
	 *         descending order of frequencies. The result size is limited to 5
	 *         documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code

		ArrayList<Occurrence> wordjuan = new ArrayList<Occurrence>();
		ArrayList<Occurrence> worddos = new ArrayList<Occurrence>();


		if (keywordsIndex.containsKey(kw1)) {
			wordjuan = keywordsIndex.get(kw1);
		}
		if (keywordsIndex.containsKey(kw2)) {
			worddos = keywordsIndex.get(kw2);
		}
		if (wordjuan.isEmpty() && worddos.isEmpty())
			return null;
		ArrayList<Occurrence> both = new ArrayList<Occurrence>();
		both.addAll(wordjuan);
		both.addAll(worddos);

		for (int i = 0; i < both.size() - 1; i++) {
			for (int j = i + 1; j < both.size() ; j++) {
				if (both.get(i).frequency <= both.get(j).frequency) {
					// First precedence
					Occurrence larger = both.get(j);
					Occurrence smaller = both.get(i);
					both.set(j, smaller);
					both.set(i, larger);

				}

			}
		}


		ArrayList<String> fin = new ArrayList<String>();
		for (Occurrence occ : both) {
			fin.add(occ.document);
		}
		while(fin.size()>5){
			int l= fin.size()-1;
			fin.remove(l);
		}
		for (int i = 0; i < fin.size() - 1; i++) {
			for (int j = i + 1; j < fin.size() ; j++) {
				if (fin.get(i) == fin.get(j))
					// First precedence
					fin.remove(j);
			}
		}

		return fin;
	}
}

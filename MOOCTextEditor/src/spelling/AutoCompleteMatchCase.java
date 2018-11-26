package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Alena Ryzhkova
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size = 0;
    

    public AutoCompleteMatchCase()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    TrieNode current = root;
	    for(int i=0; i< word.length();i++) {
	    	char currChar =  word.charAt(i);
	    	current.insert(currChar);
	    	current = current.getChild(currChar);
	    }
	    if(current.endsWord())
	    	return false;
	    current.setEndsWord(true);
	    size++;
	    return true;

	}
	
	/** 
	 * Return the number of words in the dictionary.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/**
	 *  Returns whether the string is a word in the trie.
	 */
	@Override
	public boolean isWord(String s) 
	{
		if(root==null)
			return false;
		if(s.equals(""))
			return false;
		
		int len = s.length();
		char[] charArr = new char[len];
		String searchStr;
		
		// if first letter is Lower Case, we will check exactly typing word
		if(Character.isLowerCase(s.charAt(0))) {
			return isWordDirectly(s);
		}
		//if all letters in the word are capitalized we will check it in both capitalized and non-capitalized words
		else if(isCaps(s)) {
			charArr[0] = s.charAt(0);
			String sLowCase = s.toLowerCase();
			sLowCase.getChars(1, len, charArr, 1);
			searchStr = new String(charArr);	// first letter is capital other are not capital.
			if(isWordDirectly(searchStr))
				return true;
			else
				return isWordDirectly(sLowCase);		// all letters are non-capital
		}
		// if first letter is capitalized it may be capitalized or non-capitalized word in dictionary we will check both cases)
		else {
			charArr[0] = Character.toLowerCase(s.charAt(0));
			s.getChars(1, len, charArr, 1);
			searchStr = new String(charArr);
			if(isWordDirectly(searchStr))		// first latter is Low case, the rest of word as typed
				return true;
			else
				return isWordDirectly(s);		// all letters as typed
		}
		
	}
	
	// helper-methods for isWord(String s)
	private  boolean isWordDirectly(String word) {
		TrieNode current = root;
		for(int i=0;i<word.length();i++) {
			if((current = current.getChild(word.charAt(i)))==null)
				return false;
		}
		if(current.endsWord())
			return true;
		return false;
	}
	/**
	 * Check that all letters of the word are capitalized
	 */
	private boolean isCaps(String word) {
		int len = word.length();
		for(int i=0; i<len; i++) {
			if(Character.isLowerCase(word.charAt(i)))
				return false;
		}
		return true;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 
    	 List<String> complections = new LinkedList<>();
    	 if(root==null)
    		 return complections;
    	
    	 String prefixLc = prefix.toLowerCase();
    	 // TODO Change words in completions according case 
    	 
    	 // Find the stem in the trie.  If the stem does not appear in the trie, return an empty list
    	 TrieNode current = root;
    	 for(int i=0; i<prefixLc.length();i++) {
    		 char currChar = prefixLc.charAt(i);
    		 if((current = current.getChild(currChar))==null)
    			 return complections;
    	 }
    	 
    	 
    	 // Once the stem is found, perform a breadth first search to generate completions
    	 int cntCompls = 0;
    	 
 		 Queue<TrieNode> queue = new LinkedList<>();
 	     queue.add(current);
 	     while(!queue.isEmpty() && cntCompls<numCompletions) {
 	     	TrieNode curr = queue.remove();
 	     	if(curr.endsWord()) {
 	     		complections.add(curr.getText());
 	     		cntCompls++;
 	     	}
 	     	
 	     	Set<Character> childrenCh = curr.getValidNextCharacters();
 	     	for(Character childCh: childrenCh) {
 	     		queue.add(curr.getChild(childCh));
 	    	}
 	     }
    	 
         return complections;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public static void main(String[] args) {
 		AutoCompleteMatchCase dict = new AutoCompleteMatchCase();
 		dict.addWord("Alena");
 		dict.addWord("air");
 		dict.addWord("Anton");
 		dict.printTree();
 		System.out.println("Size:" + dict.size);
 		System.out.println();
 		System.out.println("Test #1 air is in the dictionary: " + dict.isWord("air"));
 		System.out.println("Test #2 Anton is in the dictionary: " + dict.isWord("Anton"));
 		System.out.println("Test #3 Alena is in the dictionary: " + dict.isWord("Alena"));
 		System.out.println("Test #4 AIR is in the dictionary: " + dict.isWord("AIR"));
 		System.out.println("Test #5 Air is in the dictionary: " + dict.isWord("Air"));
 	}
}
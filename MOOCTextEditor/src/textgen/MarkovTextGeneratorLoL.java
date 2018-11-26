package textgen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// If the generator don't get any text for training it will not do nothing
		if(sourceText.equals(""))
			return;
		List<String> words = new LinkedList<>(Arrays.asList(sourceText.split("[\\s]+")));
		ListIterator<String> it = words.listIterator();
		
		// set "starter" to be the first word in the text
		starter = it.next();	// now iterator in the second position
		// set "prevWord" to be a starter - remember the previous word, since we moved the iterator
		String prevWord = starter;
		String w;
		// Check all words in source text, starting at the second word if "prevWord" is already a node in the list
		while(it.hasNext()) {
			w = it.next();
			checkAndAdd(prevWord, w);
			prevWord = w;
		}
		checkAndAdd(prevWord, starter);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    
		if(numWords<1 || wordList.isEmpty())
	    	return "";
	    
		String currWord = starter;
	    StringBuilder output = new StringBuilder();
	    output.append(currWord);
	    int countWords = 1;
	    String w;
	    while(countWords<numWords) {
	      	// find the node corresponding to "currentWord" in the list
	    	ListNode node = wordList.get(wordInList(currWord));
	    	w = node.getRandomNextWord(rnGenerator);
	    	output.append(" ");
	    	output.append(w);
	    	currWord=w;
	    	countWords++;
	    }
	    
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList.clear();
		starter = "";
		train(sourceText);
	}
	
	// Private helper methods
	/**
	 * This method chick if previous word is already a node in the worldList.
	 * If it's true it'll add word in nextWords' list  else it'll add new ListNode
	 * (for prevWorld with world in nextWords' list) to the wordList 
	 * @param prevWord
	 * @param word
	 */
	private void checkAndAdd(String prevWord, String word) {
		int index;
		if((index = wordInList(prevWord))>=0) {
			wordList.get(index).addNextWord(word);;
		}else {
			ListNode node = new ListNode(prevWord,word);
			wordList.add(node);
		}
	}
	
	/**
	 * Check if exist node with word = checkedWord in wordList
	 * @param word
	 * @return index of ListNode in wordList or -1 if word isn't found
	 */
	private int wordInList(String checkedWord) {
		for(int i=0; i<wordList.size();i++) {
			if(wordList.get(i).getWord().equals(checkedWord))
				return i;
		}
		return -1;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
		String textString3 = "";
		gen.train(textString3);
		System.out.println(gen);
		String res = gen.generateText(500);
        System.out.println(res);
        String feedback = "\nGenerator produced: " + res + "\n";
        System.out.println(feedback);
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	ListNode(String word, String firstNextWord)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
		nextWords.add(firstNextWord);
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}



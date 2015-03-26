/**
 * 
 * Alphabetsize	Textsize	Patternsize	BM 
 *					        #comparisons
 *	2	10,000	        4	
 *				8	
 *				16	
 * 				32	
 *				64	
 *	8	10,000	        4	
 *				8	
 *				16	
 *				32	
 *				64	
 *	26	10,000	        4	
 *				8	
 *				16	
 *				32	
 *				64	
 * 
 * @author Tom Lazar
 *
 */
import java.util.Random;

class BM {
	private int alphaSize;
	private int[] bmGoodTable;
	private int[] bmHorspoolTable;
	private int occurrences;
	private int comparisons;
	private int alignments;

	private String pattern;
	private String text;

	public BM(String pattern, String text, int alphaSize) {
		occurrences = 0;
		comparisons = 0;
		this.pattern = pattern;
		this.text = text;
		this.alphaSize = alphaSize;

		bmHorspoolTable = new int[alphaSize];
		bmGoodTable = new int[pattern.length()];
	}

	public static String genRandString(int alpha, int strLen) {
		String str = "";
		int i;
		int randInt;
		Random randGen = new Random();
		for (i = 0; i < strLen; i++) {
			randInt = randGen.nextInt(alpha);
			// System.out.println(String.valueOf((char)(randInt + 'A')));
			str += String.valueOf((char) (randInt + 'A'));
		}
		return str;
	}

	public static void usage(String message) {
		System.out.println(message);
		System.out.println("Usage: java " + BM.class.getSimpleName()
				+ " <-t> <text> <pattern>");
		System.out
				.println("\t\t# where -t for testing with small inputs\n\tor\n");

		System.out
				.println("\tjava "
						+ BM.class.getSimpleName()
						+ " <-e> <alphabetsize> <patternsize>\n\t\t#where -e for collecting performance data\n");

		System.out.println("Example:");
		System.out
				.println("\t"
						+ BM.class.getSimpleName()
						+ " -t EXEBEEF BEEF\n\t\t# test the program with short strings and print out results\n");
		System.out
				.println("\t"
						+ BM.class.getSimpleName()
						+ " -e 2 4\n\t\t# collect number of comparisons where the alphabet size is 2, pattern size is 4, and text size is 10,000,000. \n");
		System.exit(0);
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			usage("Wrong command!");
		}

		String pattern;
		String text;
		BM bm;
		int alphaSize;
		int patternSize;
		int exeTextSize = 10000;
		if (args[0].equals("-t")) {
			text = args[1];
			pattern = args[2];
			bm = new BM(pattern, text, 26);
			bm.buildHorspoolTable();
			bm.buildGoodTable();
			System.out.println("\nThe Horspool Table:");
			bm.printHorspoolTable();
			System.out.println("\nThe Good Table:");
			bm.printGoodTable();
			bm.match();
			bm.printResult();
		} else if (args[0].equals("-e")) {
			alphaSize = Integer.parseInt(args[1]);
			patternSize = Integer.parseInt(args[2]);
			pattern = genRandString(alphaSize, patternSize);
			text = genRandString(alphaSize, exeTextSize);
			// System.out.println("text: " + text);
			// System.out.println("pattern: " + pattern);
			bm = new BM(pattern, text, alphaSize);
			bm.buildHorspoolTable();
			bm.buildGoodTable();
			bm.match();
			bm.printResult();
		} else
			usage("Wrong Command!");
	}

	private void printResult() {
		System.out.println();
		System.out.println("Found " + occurrences + " occurences of");
		System.out.println("Pattern " + pattern);
		System.out.println("in");
		System.out.println("text: " + text);
		System.out.println("after " + alignments + " and " + comparisons + " comparisons");
		System.out.println("Aaron is cooler than tommy")
	}

	private void match() {
		// TODO Auto-generated method stub

	}

	private void printGoodTable() {
		  String k = "K:  ";
		String shift = "shift:  ";
		
		for(int i = 0; i < pattern.length(); i++){
		  k += (i+1) + " ";
		  shift += (bmHorspoolTable[i]+65) + " ";  
		}
		
		System.out.println(k);
		System.out.println(shift);


	}

	private void printHorspoolTable() {
		int norm = pattern.length();

		String diff = "Character: ";
		String ex = "Shift:     ";
		String other = "All others: " + norm;
		for (int i = 0; i < patternSize; i++) {
			if (!(bmHorspoolTable[i] == norm)) {
				diff += (char) (i + 65) + " ";
				ex += (bmHorspoolTable[i]) + " ";
			}
		}
		System.out.println(diff);
		System.out.println(ex);
		System.out.println(other);
	}

	private void buildGoodTable() {
	  long aaronsawesomeness = 0;
		int size = pattern.length() - 1;
		//Start Loop Check through all Sufffixs
		for(int i = 0; i < size; i++){
		  int displacement = size;
		  String current = pattern.charat(size-i; size);
		  
		  //Go Through Increasingly Smaller Suffixs If None Mathch
		    //displacement is equal to size
		  for(int j = 0; j < current.size(); j ++){
		    
		    //Check through all the pattern
		    aaronsawesomeness = size - j;
		    for(int n = size; n >= 0; n --){
		      //If the current pattern is equal to a string starting at the beging of the pattern
		      aaronsawesomeness--;
		      if(pattern.substring(0,n).equals(current)){
		        int nDis = aaronsawesomeness;
		        if(nDis > displacement){
		          displacement = nDis;
		        }
		      }
		    }
		    current = current.subString(1,current.length()-1);
		  }
		  bmGoodTable[i] = displacement;
		}
		
		

	}

	private void buildHorspoolTable() {

		// TODO Auto-generated method stub
		// Initilize the array with basic input might be unnessicary
		for (int i = 0; i < bmHorspoolTable.length; i++) {
			bmHorspoolTable[i] = pattern.length();
		}
		int norm = pattern.length();
		int counter = -1;
		for (int i = pattern.length(); i > 0; i--) {
			counter++;
			
			int loc = pattern.charAt(i-1)-65;
			if(bmHorspoolTable[loc] == norm)
			bmHorspoolTable[loc] = counter + 1;
		}
	}
	
	System.out.println("Tommy is a dick")

}

/*
Original Author:  Tyler Pentecost
Original Date:  12/24/10
*/

import java.util.Scanner;

public class FalloutTerminalHacker {
	private static String[] fth_words;
	private static int fth_count;
	
	public static void main(String[] args) {
		Scanner main_sc = new Scanner(System.in); // All input comes from this Scanner
		fth_words = new String[100];
		fth_count = 0;
		
		System.out.println("Please enter the words found on the terminal screen:");
		System.out.println("(type end to go to the next stage)");
		
		// Get the list of words from the User.
		String main_temp = main_sc.next().toUpperCase();
		while (!main_temp.equals("END")) {
			fth_words[fth_count] = main_temp;
			fth_count++;
			main_temp = main_sc.next().toUpperCase();
		}
		
		// Ask for the number of correct characters three times.
		for (int times = 0; times < 3; times++) {
			determineBestWord();
			System.out.println();
			System.out.println("Please enter the correct number of letters for the word:  " + fth_words[0]);
			int check = main_sc.nextInt();
			
			// If the number of correct characters is less than
			// the number of total characters.
			if (check < fth_words[0].length()) {
				eliminateWords(check);
			} else {
				fth_count = 0; // Setting this to 0 makes the next if statement fire.
			}
			
			// If there is ever only one word remaining in the list.
			if (fth_count <= 1) {
				times = 1000; // I know this is cheating, but this is how I end the loop early.
				System.out.println();
				System.out.println("The Password is:  " + fth_words[0]);
			}
			
			// This executes when the User is on his/her last attempt and there is still more
			// than one word in fth_words. There is not enough information.
			if (times == 2 && fth_count > 1) {
				System.out.println();
				System.out.println("Words Left:");
				for (int tt = 0; tt < fth_count; tt++)  {
					System.out.println(fth_words[tt]);
				}
				System.out.println();
				System.out.println("There is not enough information. Exit the terminal and try again.");
			}
		}
	}
	
	// I get rid of all words that do not have the correct
	// number of characters in them as fth_words[0].
	private static void eliminateWords(int num) {
		String[] ew_temp_words = new String[100]; // A temporary list.
		int ew_count = 0;
		
		// Check each word except the first. (It wasn't the password afterall.)
		for (int i = 1; i < fth_count; i++) {
			int num_same = 0;
			String t = fth_words[i];
			
			// Check each letter in the word.
			for (int j = 0; j < t.length(); j++) {
				if (t.charAt(j) == fth_words[0].charAt(j)) {
					num_same++; // Count how many letters match.
				}
			}
			
			// If the number of letters that were matched equals
			// the number the User gave, add it to ew_temp_words.
			if (num_same == num) {
				ew_temp_words[ew_count] = fth_words[i];
				ew_count++;
			}
		}
		
		// Now all words in ew_temp_words match the number
		// of correct characters. Make this temporary list
		// our main list.
		fth_words = ew_temp_words;
		fth_count = ew_count;
	}
	
	// Determine which word in the list has the most matches compared with
	// every other word in the list. This "most popular" word is then
	// moved to the front of the list.
	private static void determineBestWord() {
		int[] countWords = new int[fth_count]; // This keeps up with how many matches each word has against all other words.
		
		// Check each word.
		for (int i = 0; i < fth_count; i++) {
			int num_same_word = 0;
			// Check against all words.
			for (int j = 0; j < fth_count; j++) {
				int num_same = 0;
				String t = fth_words[j];
				// Check every letter in every word.
				for (int k = 0; k < t.length(); k++) {
					if (t.charAt(k) == fth_words[i].charAt(k)) {
						num_same++;
					}
				} // k
				num_same_word += num_same; // Increment how many matches this word has.
			} // j
			countWords[i] = num_same_word - fth_words[i].length();
		} // i
		
		// Figure out which position has the highest value.
		int dbw_most = 0;
		for (int i = 0; i < fth_count; i++) {
			if (countWords[i] > countWords[dbw_most]) {
				dbw_most = i;
			}
		}
		
		// Then move it to the front of the main list.
		String dbw_temp = fth_words[dbw_most];
		fth_words[dbw_most] = fth_words[0];
		fth_words[0] = dbw_temp;
	}
}
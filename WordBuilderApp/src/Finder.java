//Made by Maxim Shinskiy

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Finder {
	//Russian dictionary
	private final String RUSSIAN_WORDS = "D:\\WordBuilderApp\\words\\russian.txt";
	//English dictionary
	private final String ENGLISH_WORDS = "D:\\WordBuilderApp\\words\\commonEnglishWords.txt";
	//ArrayList for dictionary
	private ArrayList<String> wordsArray = new ArrayList<>();
	//ArrayList for answers
	private ArrayList<String> answer = new ArrayList<>();
	//Frame reference
	private Frame frame;
	//Readers
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private Scanner scanner;

	Finder(Frame frame){
		this.frame = frame;
	}
	
	//opening a file to read 
	private void open(String input) {
		String fileName = "";
		String lang = langCheck(input);
		//decides which language
		if(lang.equals("russian")) fileName = RUSSIAN_WORDS;
		else if(lang.equals("english")) fileName = ENGLISH_WORDS;
		//else System.out.println("other");
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			scanner = new Scanner(bufferedReader);
			if(!wordsArray.isEmpty()) wordsArray.clear();
			while(scanner.hasNextLine()) {
				String word = scanner.nextLine();
				if(checkChar(word)) wordsArray.add(word.trim().toLowerCase());
			}
			scanner.close();
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Take a decimal to convert to binary of length of need eg length 6, decimal 3 then 000011
	private String DEtoBI(int decimal, int numOfDigits) {
		StringBuilder binaryA = new StringBuilder();
		int count = 0;
		
		while(decimal > 0) {
			int rem = decimal % 2;
			binaryA.append(rem);
			decimal = decimal / 2;
			count++;
		}
		//adds 0's to the beginning 
		//binaryA.append("0".repeat(Math.max(0, (numOfDigits - count))));
		for(int x = 0; x < numOfDigits - count; x++) {
			binaryA.append("0");
		}
		
		//turns the number around
		StringBuilder binary = new StringBuilder();
		for(int i = binaryA.length()-1; i >= 0; i--) {
			binary.append(binaryA.charAt(i));
		}
		return binary.toString();
		// returns string of binary number
	}
	
	//checks for special characters in the word in dictionary
	private boolean checkChar(String word) {
		return Pattern.matches("[а-яa-z]+", word) && word.length() > 2 && word.length() < frame.input.length() + 1;
	}
	
	//making an array out of string eg [s, t, r, i, n, g ]
	private String[] addToArr(String attempt) {
		String[] arr = new String[ attempt.length()];
		for(int i = 0; i < attempt.length(); i++) {
			arr[i] = attempt.substring(i, i+1);
		}
		return arr;
	}
	
	//making a string from an array
	private String makeString(String[] stringArray) {
		StringBuilder backTo = new StringBuilder();
		for (String s : stringArray) {
			backTo.append(s);
		}
		return backTo.toString();
	}
	
	//count 1's in a binary
	private int countOne(String binary) {
		int count = 0;
		String[] binArr;
		binArr = addToArr(binary);
		for (String s : binArr) {
			if (Integer.parseInt(s) == 1) {
				count++;
			}
		}
		return count;
	}

	//printing the results from heap
	private void heapPerm(String[] a, int size, int n) {
        // if size becomes 1 then checks the obtained
        // permutation
        if (size == 1)
            checking(wordsArray, a);

        for (int i = 0; i < size; i++) {
            heapPerm(a, size - 1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1) {
                String temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            }

            // If size is even, swap i'th and last
            // element
            else {
            	String temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }

	//creating an arrayList with answers
	private void compare(String word) {
		//trying combinations of counting from 1 to 2^word.length() 
		for(int x = 0; x < Math.pow(2, word.length()); x++){
			StringBuilder attempt = new StringBuilder();
			//converting to binary in the form we need
			String num = DEtoBI(x, word.length());
			//takes only words which are longer than 2
			if(countOne(num) > 2) {
				//applying combination onto a word
				for(int i = 0; i < word.length(); i++) {
					Integer biNum = Integer.valueOf(num.substring(i, i+1));
					if(biNum.equals(1)) {
						attempt.append(word.charAt(i));
					}
				}
				//convert attempt (first combination) into an array 
				//to put in Heap's algorithm
				//to check if the word is in the dictionary 
				String[] attemptArr = addToArr(attempt.toString());
				int size = attempt.length();
				int n = attempt.length();
				
				//for loop to check all combinations made by Heap's P.S. heaps checks the combs
				heapPerm(attemptArr, size, n);
			}
		}
	}
	
	//checks if the word is in dictionary, used in heap's
	private void checking(ArrayList<String> wordsArray, String[] attemptArr) {
		String newWord = makeString(attemptArr);
		for (String s : wordsArray) {
			if(newWord.length() == s.length()){
				if(newWord.equals(s) && !answer.contains(s)){
					answer.add(s);
				}
			}
		}
	}
	
	//printing the answer
	private void printAns(ArrayList<String> answer) {
		int count = 1;
		System.out.println("The answers are:");
		for (String s : answer) {
			System.out.println("|" + count++ + "| " + s);
			System.out.println("---------------");
		}
	}

	//Checks the language
	private String langCheck(String word) {
		String lang = "else";
		if (Pattern.matches("[A-Za-z]+", word)) {
			lang = "english";
		} else if(Pattern.matches("[А-Яа-я]+", word)) {
			lang = "russian";
		}
		return lang;
	}

	//Run all together from frame class
	void run(String input){
		final long startTime = System.currentTimeMillis();					//time the code (start)
		open(input);														//open the dictionary
		if(!answer.isEmpty()) answer.clear();
		compare(input);														//compare input for matches
		frame.modelOutput.removeAllElements();								//add all elements from answers to
		for (String s: answer) {
			frame.modelOutput.addElement(s);
		}
		final long endTime = System.currentTimeMillis();					//time the code (end)
		long time = endTime - startTime;									//time
		frame.labelMessage.setText(answer.size() + " Words found! Time taken: " + time/1000.0 + ".");
		//printAns(answer);													// printing answers
		//System.out.println(answer.size() + " Words found! Time taken: " + time/1000.0 + ".");
		//System.out.println("Words in dict:" + wordsArray.size());
	}
}
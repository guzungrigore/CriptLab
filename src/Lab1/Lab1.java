package Lab1;

import java.util.Arrays;
import java.util.Scanner;

public class Lab1 {
    static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    static char[] newAlphabet = new char[alphabet.length];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder newWord = new StringBuilder();
        int key; String wordKey;

        System.out.println("Enter your operation:" +
                "\ne - encryption" +
                "\nd - decryption");
        char choice = scanner.nextLine().charAt(0);

        while(choice != 'e' && choice != 'd') {
            System.out.println("There is no such operation");
            System.out.println("Enter your operation:" +
                    "\ne - encryption" +
                    "\nd - decryption");
            choice = scanner.nextLine().charAt(0);
        }

        System.out.println("Enter your word:");
        String word = scanner.nextLine().toUpperCase().replaceAll("\\s", "");
        while (!word.matches("[A-Z]+")) {
            System.out.println("Your word contains an invalid character! \nYou can only use the following characters:");
            System.out.println(Arrays.toString(alphabet));
            System.out.println("Enter your word:");
            word = scanner.nextLine().toUpperCase().replaceAll("\\s", "");
        }
        char[] wordToChar = word.toCharArray();

        keyOptionPrint();
        int keyOption = scanner.nextInt();
        while (keyOption != 1 && keyOption != 2){
            System.out.println("You entered an invalid option!");
            keyOptionPrint();
            keyOption = scanner.nextInt();
        }

        System.out.println("Enter your Number Key:");
        key = scanner.nextInt();
        while (key < 1 || key > 25) {
            System.out.println("You entered an invalid key! The key must be from 1 to 25");
            System.out.println("Enter your Number Key:");
            key = scanner.nextInt();
        }

        if(keyOption == 1) {
            if (choice == 'e') {
                System.out.println("Your encrypted word:");
                for (int i = 0; i < word.length(); i++) {
                    newWord.append(alphabet[(index(alphabet, wordToChar[i]) + key) % 26]);
                }
                System.out.println(newWord);
            } else if (choice == 'd'){
                for (int i = 0; i < word.length(); i++) {
                    newWord.append(alphabet[(index(alphabet, wordToChar[i]) - key + alphabet.length) % 26]);
                }
                System.out.println(newWord);
            }
        } else if(keyOption == 2) {
            System.out.println("Enter your Word Key:");
            wordKey = scanner.next().toUpperCase();
            while (wordKey.length() < 7){
                System.out.println("Your word key is too short. It needs to be at least 7 characters long!");
                System.out.println("Enter your Word Key:");
                wordKey = scanner.next().toUpperCase();
            }

            int alphIndex = 0;
            for (int i = 0; i < wordKey.length(); i++){

                if(index(newAlphabet, wordKey.charAt(i)) == -1) {
                    newAlphabet[alphIndex] = wordKey.charAt(i);
                    alphIndex++;
                }
            }
            for (int i = 0; i < alphabet.length; i++) {
                if(index(newAlphabet, alphabet[i]) == -1) {
                    newAlphabet[alphIndex] = alphabet[i];
                    alphIndex++;
                }
            }
//            System.out.println(newAlphabet);
            if (choice == 'e') {
                System.out.println("Your encrypted word:");
                for (int i = 0; i < word.length(); i++) {
                    newWord.append(newAlphabet[(index(newAlphabet, wordToChar[i]) + key) % 26]);
                }
                System.out.println(newWord);
            } else if (choice == 'd'){
                for (int i = 0; i < word.length(); i++) {
                    newWord.append(newAlphabet[(index(newAlphabet, wordToChar[i]) - key + newAlphabet.length) % 26]);
                }
                System.out.println(newWord);
            }
        }

    }

    public static int index(char[] arr, char target){
        target = Character.toUpperCase(target);
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == target)
                return i;
        }
        return -1;
    }

    public static void keyOptionPrint() {
        System.out.println("Enter which type of encryption:" +
                "\n1 - a number key which can be from 1 to 25" +
                "\n2 - a number key + a String key with length strictly greater than 6 ");
    }
}

package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lab3 {
    private static final List<Character> ALPHABET = "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ"
            .chars().mapToObj(i -> (char) i).collect(Collectors.toList());

    public static String encrypt(String plaintext, String key) {
        List<Character> plainList = prepareText(plaintext);
        List<Character> keyList = prepareText(key);
        List<Character> keyFullList = generateFullKeyList(plainList.size(), keyList);

        List<Integer> plainPositions = plainList.stream()
                .map(ALPHABET::indexOf)
                .collect(Collectors.toList());

        List<Integer> keyFullPositions = keyFullList.stream()
                .map(ALPHABET::indexOf)
                .collect(Collectors.toList());

        List<Integer> differences = calculateDifferences(plainPositions, keyFullPositions);

        return differences.stream()
                .map(ALPHABET::get)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (ALPHABET.contains(currentChar)) {
                int shift = ALPHABET.indexOf(key.charAt(j % key.length()));
                char decryptedChar = ALPHABET.get((ALPHABET.indexOf(currentChar) - shift + ALPHABET.size()) % ALPHABET.size());
                plaintext.append(decryptedChar);
                j++;
            } else {
                plaintext.append(currentChar);
            }
        }

        return plaintext.toString();
    }

    private static List<Character> prepareText(String text) {
        return text.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
    }

    private static List<Character> generateFullKeyList(int textSize, List<Character> keyList) {
        List<Character> keyFullList = new ArrayList<>();
        int i = 0, j, k = 1;
        while (i < textSize) {
            j = i - (keyList.size() * (k - 1));
            keyFullList.add(keyList.get(j));
            if ((i + 1) / k == keyList.size()) {
                k++;
            }
            i++;
        }
        return keyFullList;
    }

    private static List<Integer> calculateDifferences(List<Integer> positions1, List<Integer> positions2) {
        return IntStream.range(0, positions1.size())
                .mapToObj(i -> (positions1.get(i) + positions2.get(i)) % ALPHABET.size())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char operation;
        do {
            System.out.println("Enter 'E' for encryption or 'D' for decryption: ");
            operation = scanner.nextLine().toUpperCase().charAt(0);
        } while (operation != 'E' && operation != 'D');


        System.out.println("Enter the Word:");
        String plaintext = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        while (!plaintext.matches("[AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ]+")) {
            System.out.println("An illegal character was inserted");
            System.out.println("The word should contain only: " + ALPHABET.toString());
            System.out.println("Enter the Word:");
            plaintext = scanner.nextLine().toUpperCase().replaceAll(" ", "");
        }


        System.out.println("Enter the Key:");
        String key = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        while (!key.matches("[AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ]+") || key.length() < 7) {
            System.out.println("An illegal character was inserted or the length is too short");
            System.out.println("The word should contain only: " + ALPHABET.toString());
            System.out.println("Enter the Key:");
            key = scanner.nextLine().toUpperCase().replaceAll(" ", "");
        }

        if (operation == 'E') {
            String encryptedText = encrypt(plaintext, key);

            System.out.println("Original Text: " + plaintext);
            System.out.println("Encrypted Text: " + encryptedText);
        } else {
            String decryptedText = decrypt(plaintext, key);

            System.out.println("Original Text: " + plaintext);
            System.out.println("Decrypted Text: " + decryptedText);
        }
    }
}

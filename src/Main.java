import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.Thread;

import static java.util.Arrays.*;

public class Main {
    public static AtomicInteger beautifulNick3 = new AtomicInteger(0);
    public static AtomicInteger beautifulNick4 = new AtomicInteger(0);
    public static AtomicInteger beautifulNick5 = new AtomicInteger(0);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean palindrome(String str) {
        if (str.length() % 2 == 0) {
            for (int i = 0; i < str.length() / 2; i++) {
                if (!(str.charAt(i) == str.charAt(str.length() - 1 - i))) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < (str.length() - 1) / 2; i++) {
                if (!(str.charAt(i) == str.charAt(str.length() - 1 - i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean increase(String str) {
        char[] arr = str.toCharArray();
        char[] arrToSort = copyOf(arr, arr.length);
        sort(arrToSort);
        return Arrays.equals(arr, arrToSort);
    }

    public static boolean same(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (!(str.charAt(i) == str.charAt(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static synchronized void add(String str) {
        switch (str.length()) {
            case 3:
                beautifulNick3.addAndGet(1);
                break;
            case 4:
                beautifulNick4.addAndGet(1);
                break;
            case 5:
                beautifulNick5.addAndGet(1);
                break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String str : texts) {
                if (palindrome(str)) {
                    add(str);
                }
            }
        });
        palindromeThread.start();

        Thread increaseThread = new Thread(() -> {
            for (String str : texts) {
                if (increase(str)) {
                    add(str);
                }
            }
        });
        increaseThread.start();

        Thread sameThread = new Thread(() -> {
            for (String str : texts) {
                if (same(str)) {
                    add(str);
                }
            }
        });
        sameThread.start();

        palindromeThread.join();
        increaseThread.join();
        sameThread.join();
        palindromeThread.interrupt();
        increaseThread.interrupt();
        sameThread.interrupt();

        System.out.println("Красивые ники 3 " + beautifulNick3);
        System.out.println("Красивые ники 4 " + beautifulNick4);
        System.out.println("Красивые ники 5 " + beautifulNick5);
    }
}

import java.io.*;
import java.util.*;

public class Wordle {
    private static String[] words = readWords();
    private static Random random = new Random(System.currentTimeMillis());
    private String wordle = words[(random.nextInt() & Integer.MAX_VALUE) % words.length];

    public WordleChar[] checkWord(String word) {
        word = word.toLowerCase();
        Map<Character, Integer> charMap = new HashMap<>();

        for(char c: word.toCharArray()) {
            charMap.put(c, 0);
        }

        for(char c:wordle.toCharArray()) {
            int currentOccurences = charMap.getOrDefault(c, -1);
            if(charMap.containsKey(c) && charCount(wordle, c) > currentOccurences){
                charMap.put(c, currentOccurences+1);
            }
        }

        WordleChar[] ret = new WordleChar[5];
        for(int i=0; i<5; i++) {
            WordleChar wordleChar = new WordleChar(word.charAt(i));
            if(wordle.charAt(i) == wordleChar.getChr()){
                wordleChar.setExact(true);
                charMap.put(word.charAt(i), charMap.get(word.charAt(i))-1);
            }

            ret[i] = wordleChar;
        }

        for(int i=0; i<5; i++) {
            int charOccurencies = charMap.get(word.charAt(i));
            charMap.put(word.charAt(i), charOccurencies-1);

            ret[i].setPresent(charOccurencies > 0);
        }

        return ret;
    }
    public boolean isAnswerRight(WordleChar[] answer) {
        return answer != null && Arrays.stream(answer)
                .allMatch(WordleChar::isExact);
    }

    private int charCount(String str, char chr) {
        int count = 0;

        for(char c: str.toCharArray()){
            if(c == chr) count++;
        }

        return count;
    }

    public String getSolution() {
        return wordle;
    }

    public static boolean isValidWord(String currentWord) {
        return Arrays.stream(words)
                .anyMatch(str -> str.equals(currentWord.toLowerCase()));
    }

    private static String[] readWords(){
        List<String> words = new ArrayList<>();
        try (InputStream in = Wordle.class.getResourceAsStream("res/words.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str = br.readLine();

            while(str != null){
                words.add(str);
                str = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words.toArray(String[]::new);
    }
}
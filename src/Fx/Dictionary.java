package Fx;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Dictionary {
    /**
     * wordList: wordList with targetWord, definition & pronunciation.
     * words: words for functions in basicDictionary.
     */
    private List <Word> wordList = new ArrayList<>();
   protected List <String> words = new ArrayList<>(); // this one has no meaning at all!!!


//    int search(String s) {
//        Word toSearch = new Word(s.trim());
//       int i = Collections.binarySearch(words, toSearch);
//       return i;
//   }

    public List<Word> getWordList() {
        return wordList;
    }



    public void sortWords() {
        Collections.sort(wordList);
    }

    /**
     * Import from textFile.
     * Called in dicitonary constructor.
     */


   public void importFromFile() {
        try {
            FileReader fileReader = new FileReader("E:\\Javafx\\DictionaryApplication\\Batman.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String read = "";
            String word = "";
            String pronunciation = "";
            String definition = "";
            boolean isValid = true;
            while((read = bufferedReader.readLine()) != null) {
                if (read.startsWith("@")) {
                   read = read.replace("@", "");
                   String[] info = read.split(" /");
                    if (info.length > 2) {
                        isValid = false;
                    }
                    for (int i = 0; i < info.length - 1; i++) {
                        word += info[i];
                    }
                   pronunciation += "/" + info[info.length - 1];
                } else if (read.equals("")) {
                    if (isValid) {
                        words.add(word);
                       wordList.add(new Word(word, pronunciation, definition));
                    }
                    word = "";
                    pronunciation = "";
                    definition = "";
                    isValid = true;
                } else {
                    definition += read + "\n";
                }
            }
            words.add(word);
            wordList.add(new Word(word, pronunciation, definition));
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* public void loadFromFile() {
        try {
           // words = new Vector<>();
            Path path = Paths.get("E:\\Dictionary_JavaFx\\src\\sample\\dictionary.txt");
            List<String> dataList = Files.readAllLines(path);
            ListIterator<String> itr = dataList.listIterator();
            //code to read data from file to Vector
            Word word  = new Word();
            int count = 0;
            while (itr.hasNext()) {
                String p = itr.next();

                if (p.startsWith("@")) {
                    count++;
                    word = new Word();
                    String[] part = p.split("/", 2);

                    String s2 = part[0].substring(1).trim();
                    if (s2.startsWith("'") || s2.startsWith("-") || s2.startsWith("(")) {
                        s2 = s2.substring(1, s2.length());
                    }
                    word.setTargetWord(s2);
                    words.add(s2);

                    if (part.length < 2) {
                        word.setPronunciation("");
                    } else
                        word.setPronunciation("/" + part[1]);
                    while (itr.hasNext()) {
                        String p1 = itr.next();
                        //System.out.println(p1);
                        if (!p1.startsWith("@")) {
                            word.addToMeaning(p1);
                        } else {
                            wordList.add(word);
                            itr.previous();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


//    public Dictionary() {
//        importFromFile();
//    }


    /**
     * print all words.
     */
    public void printAllWords() {
        for (int i = 0; i < wordList.size(); i++) {
            System.out.println(wordList.get(i).toString());
        }
    }

    /**
     * Export wordList to file.
     */
    public void exportToFile() {
        try {
            FileWriter fileWriter = new FileWriter("E:\\Javafx\\DictionaryApplication\\Batman.txt");
            for (int i = 0 ; i < wordList.size() - 1; i++) {
                Word word = wordList.get(i);
                fileWriter.write("@" + word.getTargetWord() + " " + word.getPronunciation() + "\n");
                fileWriter.write(word.getDefinition() + "\n");
            }
            fileWriter.write("@" + wordList.get(wordList.size() - 1).getTargetWord() + " " + wordList.get(wordList.size() - 1).getPronunciation() + "\n");
            fileWriter.write(wordList.get(wordList.size() - 1).getDefinition());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get the amount of words in the dictionary.
     * @return the size of the list.
     */
    public int getListSize() {
        return wordList.size();
    }

    /**
     * check if a word is in the wordList or not.
     * @param word word to search for.
     * @return whether wordList contains the word or not.
     */
    public boolean containsWord(String word) {
        return words.contains(word);
    }


    /**
     * remove a word from dictionary.
     * @param toRemove
     * @return true if removed sucessful, false if not.
     */
    public int removeWord(String toRemove) {
        if (!containsWord(toRemove)) {
            //System.out.println("WORD NOT FOUND");
            return -1;
        }
        for (Word word : wordList) {
            if (word.compareTo(toRemove) == 0) {
                int tmp = words.indexOf(toRemove);
                wordList.remove(word);
                words.remove(toRemove);
                exportToFile();
                return tmp;
            }
        }
        return -1;
    }

    /**
     * adding a word to the dictionary.
     * @param word word to be added.
     * @return true if the word is added successfully, false if not.
     */
    public int addWord(Word word) {
        String target = word.getTargetWord();
        if (containsWord(target)) {
          //  System.out.println("WORD ALREADY IN THE DICTIONARY");
            return -1;
        }
        words.add(target);
        wordList.add(word);
        exportToFile();
        return words.indexOf(target);
    }


    /**
     * change definition of a word.
     * @param target target word in English.
     * @param newDefiniton new definition.
     * @return true if changed successfully, false otherwise.
     */
    public int changeDefinition(String target, String newDefiniton) {
        if (!containsWord(target)) {
            System.out.println("WORD NOT FOUND");
            return -1;
        }
        for (Word word : wordList) {
            if (word.getTargetWord().equals(target)) {
                word.setDefinition(newDefiniton);
                exportToFile();
                return words.indexOf(word);
            }
        }
        return -1;
    }

    /**
     * precise search for a word
     * @param targetWord word to be search
     * @return true if a word is found, false otherwise.
     */
    public int dictionaryLookUp(String targetWord) {
        for (Word word : wordList) {
            if (word.getTargetWord().equals(targetWord)) {
               // System.out.println(word.toStringWithDefiniton());
                int index = words.indexOf(targetWord);
                return index;
            }
        }
        return -1;
    }

    /**
     * look for words which starts with a specific phrase.
     * @param startsWith phrase that the word starts with.
     * @return true if results are found, false otherwise.
     */
    public boolean dictionarySearcher(String startsWith) {
        boolean found = false;
        ArrayList<Word> results = new ArrayList<>();
        for (Word word : wordList) {
            if (word.getTargetWord().startsWith(startsWith)) {
                System.out.println(word.getTargetWord());
                found = true;
            }
        }
        return found;
    }
}

package Fx;

 public class Word implements Comparable{
     String targetWord;
     String pronunciation;
     String definition;

    public Word(String targetWord, String pronunciation, String definition) {
        this.targetWord = targetWord;
        this.pronunciation = pronunciation;
        this.definition = definition;
    }

     public Word(Word word) {
         this.targetWord = word.getTargetWord();
         this.pronunciation = word.getPronunciation();
         this.definition = word.getDefinition();
     }


//     public Word() {
//         this.definition = new Meaning();
//     }

    /**
     * Return formatted target word & pronunciation
     * Ex: <targetWord>  <pronunciation>
     * @return formatted target word & pronunciation
     */
    @Override
    public String toString() {
        return String.format("%-40s%-40s", targetWord, pronunciation);
    }

    public String toStringWithDefiniton() {
        String result = toString();
        result += "\n" + definition;
        return result;
    }

    public String getTargetWord() {
        return targetWord;
    }

     public void setTargetWord(String targetWord) {
         this.targetWord = targetWord;
     }

     public String getPronunciation() {return pronunciation;}

     public void setPronunciation(String pronunciation) {
         this.pronunciation = pronunciation;
     }

     public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


//    public void addToMeaning(String s) {
//         this.definition.Add(s);
//     }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Word other) {
            return this.getTargetWord().compareTo(other.getTargetWord());
        }
        if (o instanceof String other) {
            return this.getTargetWord().compareTo(other);
        }
        return -999;
    }
}

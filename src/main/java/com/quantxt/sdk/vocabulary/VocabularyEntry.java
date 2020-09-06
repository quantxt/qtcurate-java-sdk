package com.quantxt.sdk.vocabulary;

public class VocabularyEntry {

    private String str;
    private String category;

    public VocabularyEntry(){

    }

    public VocabularyEntry(final String str) {
        this.str = str;
    }

    public VocabularyEntry(final String category,
                           final String str) {
        this.category = category;
        this.str = str;
    }

    /**
     * Returns category of the item
     *
     * @return key.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns look up string of the item.
     *
     * @return Entry search string.
     */
    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "category='" + category + '\'' +
                ",str='" + str + '\'' +
                '}';
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

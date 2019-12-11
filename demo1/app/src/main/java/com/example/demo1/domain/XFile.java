package com.example.demo1.domain;

public class XFile {
    private String filePath;
    private String fileName;
    private User poster;
    private String postTime;
    private String length;

    /**
     * @param filePath
     * @param fileName
     * @param poster
     * @param postTime
     * @param length
     */
    public XFile(String filePath, String fileName, User poster, String postTime, String length) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.poster = poster;
        this.postTime = postTime;
        this.length = length;
    }
    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @return the poster
     */
    public User getPoster() {
        return poster;
    }
    /**
     * @return the postTime
     */
    public String getPostTime() {
        return postTime;
    }

    /**
     *
     * @return
     */
    public String getLength(){
        return length;
    }
}

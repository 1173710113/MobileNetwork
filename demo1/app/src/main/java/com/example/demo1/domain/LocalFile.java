package com.example.demo1.domain;

import org.litepal.crud.DataSupport;

public class LocalFile extends DataSupport {
    private String fileId;
    private String filePath;
    private String fileName;

    public LocalFile(String fileId, String filePath, String fileName) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

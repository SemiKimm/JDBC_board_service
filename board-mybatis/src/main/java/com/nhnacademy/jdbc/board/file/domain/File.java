package com.nhnacademy.jdbc.board.file.domain;

import lombok.Data;


public class File {
    int fileNo;
    String fileName;
    String filePath;
    int postNo;

    public File(int fileNo, String fileName, String filePath, int postNo) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.filePath = filePath;
        this.postNo = postNo;
    }

    public File(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public int getFileNo() {
        return fileNo;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getPostNo() {
        return postNo;
    }
}

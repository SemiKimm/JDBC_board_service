package com.nhnacademy.jdbc.board.file.service;


import com.nhnacademy.jdbc.board.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    int insertFile(File file);
    File selectFile(int postNo);
    int deleteFile(int postNo);
    boolean saveFile(MultipartFile file);
}

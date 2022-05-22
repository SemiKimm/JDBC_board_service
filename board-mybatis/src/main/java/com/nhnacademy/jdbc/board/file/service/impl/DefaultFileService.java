package com.nhnacademy.jdbc.board.file.service.impl;

import com.nhnacademy.jdbc.board.file.domain.File;
import com.nhnacademy.jdbc.board.file.mapper.FileMapper;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.good.mapper.GoodMapper;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class DefaultFileService implements FileService {

    private String fileDir = "upload";

    private final FileMapper fileMapper;

    public DefaultFileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    @Override
    public File selectFile(int postNo) {
        return fileMapper.selectFile(postNo);
    }

    @Override
    public int deleteFile(int postNo) {
        return fileMapper.deleteFile(postNo);
    }

    @Override
    public boolean saveFile(MultipartFile file) {
        String path = "/Users/choijungwoo/nhngit/jdbc_team_pj/board-mybatis/src/main/resources/upload/" + file.getOriginalFilename();
        if (file.isEmpty()) {
            return false;
        }
        try(
                FileOutputStream fos = new FileOutputStream(path);
                InputStream is = file.getInputStream();
        ){
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }

        fileMapper.insertFile(new File(file.getOriginalFilename(),path));
        return true;

    }
}

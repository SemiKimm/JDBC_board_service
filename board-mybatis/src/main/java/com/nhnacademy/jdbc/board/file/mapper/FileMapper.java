package com.nhnacademy.jdbc.board.file.mapper;

import com.nhnacademy.jdbc.board.file.domain.File;
import com.nhnacademy.jdbc.board.good.domain.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface FileMapper {
    int insertFile(@Param("file") File file);
    File selectFile(@Param("postNo") int postNo);
    int deleteFile(@Param("postNo") int postNo);

}

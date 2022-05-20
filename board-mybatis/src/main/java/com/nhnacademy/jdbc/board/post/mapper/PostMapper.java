package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    Optional<Post> selectPost(@Param("no") int no);
    List<Post> selectPagePosts(@Param("pageLimit") int pageLimit,
                               @Param("pageOffset") int pageOffset);
    List<Post> selectPosts();
    int insertPost(Post post);
    int updatePostByNo(@Param("post") Post post);
    int deleteByNo(@Param("no") int no);
    List<Post> selectDeletedPosts();
    int restoreDeletedPost(@Param("deletedPostNo") int deletedPostNo);
    Optional<Post> selectDeletedPost(@Param("deletedPostNo") int deletedPostNo);
}

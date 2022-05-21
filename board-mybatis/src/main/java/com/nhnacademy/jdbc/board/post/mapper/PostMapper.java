package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    Optional<Post> selectPost(@Param("no") int no);
    List<PostListDTO> selectPostList(@Param("pageLimit") int pageLimit,
                                     @Param("pageOffset") int pageOffset);
    List<Integer> selectPostNumbers(); // 전체 posts 사이즈 구하기 위한 메서드
    int insertPost(Post post);
    int updatePostByNo(@Param("post") Post post);
    int deleteByNo(@Param("no") int no);
    List<PostListDTO> selectDeletedPosts();
    int restoreDeletedPost(@Param("deletedPostNo") int deletedPostNo);
    Optional<Post> selectDeletedPost(@Param("deletedPostNo") int deletedPostNo);

    List<PostListDTO> selectPostListByTitle(@Param("keywordCookie") String keywordCookie,
                                            @Param("pageLimit") int pageLimit,
                                            @Param("pageOffset") int pageOffset);

    List<PostListDTO> selectGoodPostList(@Param("loginUserNo") int loginUserNo,
                                         @Param("pageLimit") int pageLimit,
                                         @Param("pageOffset") int pageOffset);
}

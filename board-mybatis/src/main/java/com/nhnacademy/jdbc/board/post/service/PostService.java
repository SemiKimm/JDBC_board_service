package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public interface PostService {
    Optional<Post> getPost(int no);
    int registerPost(int writerNo, String title, String content);
    int deletePost(int postNo);
    int updatePost(Post post);
    int getLastPageSize(int postLimit, String searchKeyword);
    List<PostListDTO> getDeletedPosts();
    int restorePost(int deletedPostNo);
    Optional<Post> getDeletedPost(int deletedPostNo);
    List<PostListDTO> getPagePostList(String keywordCookie, Integer page, int pageLimit);

    List<PostListDTO> getGoodPostList(int loginUserNo, Integer page, int pageLimit);

    int getGoodPostsLastPageSize(int loginUserNo, int postLimit);

    boolean addViewCount(int loginUserNo, int postNo);
}

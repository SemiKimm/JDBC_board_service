package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Integer> getPostNumbers();
    Optional<Post> getPost(int no);
    int registerPost(int writerNo, String title, String content);
    int deletePost(int postNo);
    int updatePost(Post post);
    int getLastPageSize(int postLimit);
    List<PostListDTO> getDeletedPosts();
    int restorePost(int deletedPostNo);
    Optional<Post> getDeletedPost(int deletedPostNo);
    List<PostListDTO> getPagePostList(String keywordCookie, int page, int pageLimit);
}

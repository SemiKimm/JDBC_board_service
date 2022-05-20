package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getPagePosts(int page, int pageLimit);
    List<Post> getPosts();
    Optional<Post> getPost(int no);
    int registerPost(int writerNo, String title, String content);
    int deletePost(int postNo);
    int updatePost(Post post);
    int getLastPageSize(int postLimit);
    List<Post> getDeletedPosts();
    int restorePost(int deletedPostNo);
    Optional<Post> getDeletedPost(int deletedPostNo);
}

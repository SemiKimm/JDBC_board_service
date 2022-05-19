package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getPosts();
    Optional<Post> getPost(int no);
    int registerPost(int writerNo, String title, String content);
    int deletePost(int postNo);
}

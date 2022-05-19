package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;

public interface PostService {
    List<Post> getPosts();
    Post getPost(int no);
}

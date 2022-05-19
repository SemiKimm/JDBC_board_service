package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getPagePosts(int page);
    List<Post> getFirstPagePosts(); // 첫번째 페이지의 게시글 list 가져오기 위한 메서드
    List<Post> getPosts();
    Optional<Post> getPost(int no);
    int registerPost(int writerNo, String title, String content);
    int deletePost(int postNo);
    int updatePost(Post post);
    int getLastPageSize();
}

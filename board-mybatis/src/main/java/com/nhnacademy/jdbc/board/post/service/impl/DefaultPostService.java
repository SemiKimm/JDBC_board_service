package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultPostService implements PostService {
    private final PostMapper postMapper;

    public DefaultPostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public int updatePost(Post post) {
        return postMapper.updatePostByNo(post);
    }

    @Override
    public int getLastPageSize(int postLimit) {
        int postsSize = getPostNumbers().size();
        if (postsSize % postLimit > 0) {
            return postsSize / postLimit + 1;
        }
        return postsSize / postLimit;
    }

//    @Override
//    public List<Post> getPagePosts(int page, int postLimit) {
//        int offset = (page - 1) * postLimit;
//        return postMapper.selectPagePosts(postLimit, offset);
//    }

    @Override
    public List<PostListDTO> getPagePosts(int page, int postLimit) {
        int offset = (page - 1) * postLimit;
        return postMapper.selectPostList(postLimit, offset);
    }

    @Override
    public List<Integer> getPostNumbers() {
        return postMapper.selectPostNumbers();
    }

    @Override
    public Optional<Post> getPost(int no) {
        Optional<Post> post = postMapper.selectPost(no);
        if (post.isEmpty()) {
            throw new RuntimeException(); // todo
        }
        return post;
    }

    @Override
    public int registerPost(int writerNo, String title, String content) {
        Post post =
            new Post(null, title, content, new Timestamp(new Date().getTime()), 0, writerNo, 1,
                null, null);
        return postMapper.insertPost(post);
    }

    @Override
    public int deletePost(int postNo) {
        return postMapper.deleteByNo(postNo);
    }

    @Override
    public List<PostListDTO> getDeletedPosts() {
        return postMapper.selectDeletedPosts();
    }

    @Override
    public int restorePost(int deletedPostNo) {
        return postMapper.restoreDeletedPost(deletedPostNo);
    }

    @Override
    public Optional<Post> getDeletedPost(int deletedPostNo) {
        return postMapper.selectDeletedPost(deletedPostNo);
    }
}

package com.nhnacademy.jdbc.board.post.service.impl;

import com.nhnacademy.jdbc.board.post.domain.Post;
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
    public List<Post> getPosts() {
        return postMapper.selectPosts();
    }

    @Override
    public Optional<Post> getPost(int no) {
        Optional<Post> post = postMapper.selectPost(no);
        if(post.isEmpty()){
            throw new RuntimeException(); // todo
        }
        return post;
    }

    @Override
    public int registerPost(String title, String content) {
        // fixme : user 정보랑 엮어 야 됨 그러면 파라미터로 session 에 등록된 id 가져와서 여기서 ~~
        Post post = new Post(null, title, content, new Timestamp(new Date().getTime()),0,1,1,null,null);
        return postMapper.insertPost(post);
    }
}

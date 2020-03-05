package dev.astamur.javalin.repositories;

import dev.astamur.javalin.model.Post;

import java.util.List;

public interface PostRepository {
    void create(Post post);

    Post find(String id);

    List<Post> findAll();

    Post update(Post post);

    void delete(String id);
}

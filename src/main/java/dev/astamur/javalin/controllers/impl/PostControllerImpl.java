package dev.astamur.javalin.controllers.impl;

import dev.astamur.javalin.config.Paths;
import dev.astamur.javalin.controllers.PostController;
import dev.astamur.javalin.model.Post;
import dev.astamur.javalin.repositories.PostRepository;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class PostControllerImpl implements PostController {
    private static final String ID = "id";

    private PostRepository postRepository;

    public PostControllerImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void create(@NotNull Context context) {
        var post = context.bodyAsClass(Post.class);

        if (post.getId() != null) {
            throw new BadRequestResponse(String.format("Unable to create a new post with existing id: %s", post));
        }

        postRepository.create(post);
        context.status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), Paths.formatPostLocation(post.getId().toString()));
    }

    @Override
    public void find(@NotNull Context context) {
        String id = context.pathParam(ID);
        Post post = postRepository.find(id);

        if (post == null) {
            throw new NotFoundResponse(String.format("A post with id '%s' is not found", id));
        }

        context.json(post);
    }

    @Override
    public void findAll(@NotNull Context context) {
        context.json(postRepository.findAll());
    }

    @Override
    public void update(@NotNull Context context) {
        var post = context.bodyAsClass(Post.class);
        var id = context.pathParam(ID);

        if (post.getId() != null && !post.getId().toString().equals(id)) {
            throw new BadRequestResponse("Id update is not allowed");
        }

        postRepository.update(post, id);
    }

    @Override
    public void delete(@NotNull Context context) {
        postRepository.delete(context.pathParam(ID));
    }
}
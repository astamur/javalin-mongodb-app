package dev.astamur.javalin.controllers;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public interface PostController {
    void create(@NotNull Context context);

    void find(@NotNull Context context);

    void findAll(@NotNull Context context);

    void update(@NotNull Context context);

    void delete(@NotNull Context context);
}

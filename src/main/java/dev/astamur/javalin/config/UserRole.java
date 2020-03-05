package dev.astamur.javalin.config;

import io.javalin.core.security.Role;

public enum UserRole implements Role {
    PUBLIC, ADMIN
}

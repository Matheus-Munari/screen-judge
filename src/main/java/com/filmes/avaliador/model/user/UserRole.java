package com.filmes.avaliador.model.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    SUPER("super");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

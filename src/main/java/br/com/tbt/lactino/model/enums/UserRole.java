package br.com.tbt.lactino.model.enums;

public enum UserRole {
    ADMIN ("Admin"),
    PRODUTOR("Produtor");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}

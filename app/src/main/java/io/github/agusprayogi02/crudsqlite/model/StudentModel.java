package io.github.agusprayogi02.crudsqlite.model;

public class StudentModel {

    private int idField;
    private String nameField;
    private String emailField;


    public StudentModel(int idField, String nameField, String emailField) {
        this.idField = idField;
        this.nameField = nameField;
        this.emailField = emailField;
    }


    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getEmailField() {
        return emailField;
    }

    public void setEmailField(String emailField) {
        this.emailField = emailField;
    }
}

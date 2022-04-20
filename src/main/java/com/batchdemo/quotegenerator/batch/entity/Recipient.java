package com.batchdemo.quotegenerator.batch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity                 /* this annotation allows the JPA to manage this entity,
                        without it the init method of RecipientRepository cannot be invoked */
public class Recipient {
    @Id
    private int recipientId;
    private String firstName;
    private String lastName;
    private String email;

    public Recipient(int id, String firstName, String lastName, String email) {
        this.recipientId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Recipient() {}


    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }
    public int getRecipientId() {
        return recipientId;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder("{");
        sBuilder.append("id").append(":").append(this.getRecipientId());
        sBuilder.append("firstName").append(":").append(this.getFirstName());
        sBuilder.append("lastName").append(":").append(this.getLastName());
        sBuilder.append("email").append(":").append(this.getEmail());
        sBuilder.append("}");

        return sBuilder.toString();
    }
}

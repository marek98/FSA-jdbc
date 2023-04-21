package sk.posam.fsa.jdbc;

import java.util.Date;

public class Actor {
    private long id;
    private String firstName;
    private String lastName;


    public Actor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Actor(%d, %s %s)", id, firstName, lastName);
    }
}

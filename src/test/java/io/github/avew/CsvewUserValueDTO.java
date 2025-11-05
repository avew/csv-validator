package io.github.avew;

public class CsvewUserValueDTO extends CsvewValue {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String ref1;
    private String ref2;

    public CsvewUserValueDTO() {
    }

    public CsvewUserValueDTO(String username, String email, String firstname, String lastname) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", ref1='" + ref1 + '\'' +
                ", ref2='" + ref2 + '\'' +
                ", line='" + getLine() + '\'' +
                '}';
    }
}
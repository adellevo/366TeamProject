package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Owner {

    @Id
    private long owner_id;

    @Column(name="first_name")
    private String firstName;

    @NotNull
    @Column(name="last_name")
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Store> owned_stores;

    public Owner() {
    }

    public Owner(long owner_id) {
        this.owner_id = owner_id;
    }

    public Owner(long owner_id, String firstName, String lastName, String email) {
        this.owner_id = owner_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Store> getStores() {
        return owned_stores;
    }

    public void setRole(List<Store> stores) {
        this.owned_stores = stores;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "owner_id=" + owner_id +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '}';
    }

}
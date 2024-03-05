package csc366.jpademo;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
public class RegionalManager{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long manager_id;

    @NotNull
    @Column(name="first_name")
    private String first_name;

    @NotNull
    @Column(name="last_name")
    private String last_name;



    @Override
    public String toString() {
        return "RegionalManager{" +
                "manager_id=" + manager_id +
                ", first_name='" + first_name + '\'' +
                ", last_name=" + last_name +
                '}';
    }


    public long getManager_id() {
        return manager_id;
    }


    public void setManager_id(long manager_id) {
        this.manager_id = manager_id;
    }


    public String getFirst_name() {
        return first_name;
    }


    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }


    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


  
}


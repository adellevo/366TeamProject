package csc366.jpademo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long address_id;

    @NotNull
    private String country;

    private String state;

    @NotNull
    private String city;

    @NotNull
    private String street_address;

    @NotNull
    private String zip_code;

    public Address(String country, String state, String city, String street_address, String zip_code) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street_address = street_address;
        this.zip_code = zip_code;
    }

    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + address_id +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", street_address='" + street_address + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}

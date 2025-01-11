package modele;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    private String SIRENNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private String TVANumber;


    public Restaurant() {
    }

    public Restaurant(String name, String address, String phoneNumber, String TVANumber, String SIRENNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.TVANumber = TVANumber;
        this.SIRENNumber = SIRENNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTVANumber() {
        return TVANumber;
    }

    public void setTVANumber(String TVANumber) {
        this.TVANumber = TVANumber;
    }

    public String getSIRENNumber() {
        return SIRENNumber;
    }

    public void setSIRENNumber(String SIRENNumber) {
        this.SIRENNumber = SIRENNumber;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "SIRENNumber='" + SIRENNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", TVANumber='" + TVANumber + '\'' +
                '}';
    }
}

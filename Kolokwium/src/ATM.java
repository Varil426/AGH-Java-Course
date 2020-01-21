import java.time.LocalDate;

public class ATM {
    String id;
    String name;
    String city;
    String address;
    String postCode;
    String district;
    LocalDate liveDate;
    String accessHours;

    ATM(String id, String name, String city, String address, String postCode, String district, LocalDate liveDate, String accessHours) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.postCode = postCode;
        this.district = district;
        this.liveDate = liveDate;
        this.accessHours = accessHours;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(": ").append(name).append(" ").append(city).append(" ").append(address).append(" ").append(postCode).append(" ").append(district).append(" ").append(liveDate).append(" ").append(accessHours);
        return builder.toString();
    }
}

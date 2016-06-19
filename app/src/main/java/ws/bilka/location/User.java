package ws.bilka.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"mId"})
class User {

    private String mId;
    private String email;
    private String name;
    private Location location;

    public User() {
    }

    public User(String email, String name, Location location) {
        mId = "some_id";
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return mId;
    }

    @JsonIgnore
    public String getDisplayName() {
        return getName() + " (" + getEmail() + ")";
    }

    @Override
    public String toString() {
        return "User{email='" + email + "\', name='" + name + "\', location" + location + "\', displayName='" + getDisplayName() + "'}";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location locations) {
        this.location = location;

    }


}

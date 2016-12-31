package ch.drshit.domain.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author timo
 */
@Entity
@Table(name="LOCALE")
public class Locale implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    
    @Column(name="NAME", length=100)
    private String name;
    
    @Column(name="CITY", length=100)
    private String city;
    
    @Column(name="ADDRESS", length=100)
    private String address;
    
    @Column(name="LONGITUDE")
    private float longitude = 7.457614f;
    
    @Column(name="LATITUDE")
    private float latitude = 46.948856f;
    
    @Enumerated(EnumType.STRING)
    @Column(name="PICKUP_DAY")
    private DayOfWeek pickUpDay;
    
    @Enumerated(EnumType.STRING)
    @Column(name="RETURN_DAY")
    private DayOfWeek returnDay;
    
    @Column(name="PICKUP_TIME_START")
    private Time pickUpTimeStart;
    
    @Column(name="PICKUP_TIME_END")
    private Time pickUpTimeEnd;
    
    @Column(name="RETURN_TIME_START")
    private Time returnTimeStart;
    
    @Column(name="RETURN_TIME_END")
    private Time returnTimeEnd;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 61 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 61 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 61 * hash + Float.floatToIntBits(this.longitude);
        hash = 61 * hash + Float.floatToIntBits(this.latitude);
        hash = 61 * hash + (this.pickUpDay != null ? this.pickUpDay.hashCode() : 0);
        hash = 61 * hash + (this.returnDay != null ? this.returnDay.hashCode() : 0);
        hash = 61 * hash + (this.pickUpTimeStart != null ? this.pickUpTimeStart.hashCode() : 0);
        hash = 61 * hash + (this.pickUpTimeEnd != null ? this.pickUpTimeEnd.hashCode() : 0);
        hash = 61 * hash + (this.returnTimeStart != null ? this.returnTimeStart.hashCode() : 0);
        hash = 61 * hash + (this.returnTimeEnd != null ? this.returnTimeEnd.hashCode() : 0);
        return hash;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Locale other = (Locale) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        if (this.latitude != other.latitude) {
            return false;
        }
        if (this.longitude != other.longitude) {
            return false;
        }
        if ((this.pickUpDay == null) ? (other.pickUpDay != null) : !this.pickUpDay.equals(other.pickUpDay)) {
            return false;
        }
        if ((this.returnDay == null) ? (other.returnDay != null) : !this.returnDay.equals(other.returnDay)) {
            return false;
        }
        if ((this.pickUpTimeStart == null) ? (other.pickUpTimeStart != null) : !this.pickUpTimeStart.equals(other.pickUpTimeStart)) {
            return false;
        }
        if ((this.pickUpTimeEnd == null) ? (other.pickUpTimeEnd != null) : !this.pickUpTimeEnd.equals(other.pickUpTimeEnd)) {
            return false;
        }
        if ((this.returnTimeStart == null) ? (other.returnTimeStart != null) : !this.returnTimeStart.equals(other.returnTimeStart)) {
            return false;
        }
        if ((this.returnTimeEnd == null) ? (other.returnTimeEnd != null) : !this.returnTimeEnd.equals(other.returnTimeEnd)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public DayOfWeek getPickUpDay() {
        return pickUpDay;
    }

    public void setPickUpDay(DayOfWeek pickUpDay) {
        this.pickUpDay = pickUpDay;
    }

    public DayOfWeek getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(DayOfWeek returnDay) {
        this.returnDay = returnDay;
    }

    public Time getPickUpTimeStart() {
        return pickUpTimeStart;
    }

    public void setPickUpTimeStart(Time pickUpTimeStart) {
        this.pickUpTimeStart = pickUpTimeStart;
    }

    public Time getPickUpTimeEnd() {
        return pickUpTimeEnd;
    }

    public void setPickUpTimeEnd(Time pickUpTimeEnd) {
        this.pickUpTimeEnd = pickUpTimeEnd;
    }

    public Time getReturnTimeStart() {
        return returnTimeStart;
    }

    public void setReturnTimeStart(Time returnTimeStart) {
        this.returnTimeStart = returnTimeStart;
    }

    public Time getReturnTimeEnd() {
        return returnTimeEnd;
    }

    public void setReturnTimeEnd(Time returnTimeEnd) {
        this.returnTimeEnd = returnTimeEnd;
    }
}

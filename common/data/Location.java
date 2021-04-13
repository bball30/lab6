package common.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name="location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {
    private Long x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private double z;
    private String name; //Поле не может быть null

    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param z - Z coordinate
     * @param name - name of the location
     */
    public Location(Long x, Float y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location(){}

    public Long getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.z, z) == 0 &&
                Objects.equals(x, location.x) &&
                Objects.equals(y, location.y) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}

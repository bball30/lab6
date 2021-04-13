package common.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * X-Y coordinates.
 */
@XmlRootElement(name="coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Serializable {
    private double x;
    private Double y; //Поле не может быть null

    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(){}

    /**
     * @return X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * @return Y-coordinate.
     */
    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

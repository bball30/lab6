package common.utility;

import common.data.Coordinates;
import common.data.FormOfEducation;
import common.data.Person;
import common.xml.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name="StudyGroup")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudyGroupLite implements Serializable {
    private String name;
    private Coordinates coordinates;
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private java.time.LocalDateTime creationDate;
    private long studentsCount;
    private long shouldBeExpelled;
    private int averageMark;
    private FormOfEducation formOfEducation;
    private Person groupAdmin;

    public StudyGroupLite(String name, Coordinates coordinates, long studentsCount, long shouldBeExpelled, int averageMark, FormOfEducation formOfEducation, Person groupAdmin) {
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = groupAdmin;
    }

    public StudyGroupLite() {
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public long getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroupLite that = (StudyGroupLite) o;
        return studentsCount == that.studentsCount &&
                shouldBeExpelled == that.shouldBeExpelled &&
                averageMark == that.averageMark &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                formOfEducation == that.formOfEducation &&
                Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, studentsCount, shouldBeExpelled, averageMark, formOfEducation, groupAdmin);
    }

    @Override
    public String toString() {
        return "StudyGroupLite{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", averageMark=" + averageMark +
                ", formOfEducation=" + formOfEducation +
                ", groupAdmin=" + groupAdmin +
                '}';
    }
}

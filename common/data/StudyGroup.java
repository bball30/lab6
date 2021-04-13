package common.data;

import common.xml.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * StudyGroup common.data class
 */
@XmlRootElement(name="StudyGroup")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private long shouldBeExpelled; //Значение поля должно быть больше 0
    private int averageMark; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    /**
     * @param id - study group ID
     * @param name - study group name
     * @param coordinates - study group coordinates object
     * @param creationDate - LocalDateTime object of creation date
     * @param studentsCount - students count in the group
     * @param averageMark - average mark of students in the group
     * @param shouldBeExpelled - should be expelled students count in the group
     * @param formOfEducation - form of group education
     * @param groupAdmin - admin in the group
     */
    public StudyGroup(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, long studentsCount, long shouldBeExpelled, int averageMark, FormOfEducation formOfEducation, Person groupAdmin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = groupAdmin;
    }

    public StudyGroup() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setShouldBeExpelled(long shouldBeExpelled) {
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public void setAverageMark(int averageMark) {
        this.averageMark = averageMark;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public int getId() {
        return id;
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
        StudyGroup studyGroup = (StudyGroup) o;
        return Objects.equals(id, studyGroup.id) && similar(studyGroup);
    }

    /**
     * Like equals but id doesn't matter
     * @param studyGroup - StudyGroup object to compare
     * @return true if all fields of objects are equal
     */
    public boolean similar(StudyGroup studyGroup) {
        return Objects.equals(name, studyGroup.name) &&
                Objects.equals(coordinates, studyGroup.coordinates) &&
                Objects.equals(creationDate, studyGroup.creationDate) &&
                Objects.equals(studentsCount, studyGroup.studentsCount) &&
                Objects.equals(shouldBeExpelled, studyGroup.shouldBeExpelled) &&
                Objects.equals(averageMark, studyGroup.averageMark) &&
                formOfEducation == studyGroup.formOfEducation &&
                Objects.equals(groupAdmin, studyGroup.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, shouldBeExpelled, averageMark, formOfEducation, groupAdmin);
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", averageMark=" + averageMark +
                ", formOfEducation=" + formOfEducation +
                ", groupAdmin=" + groupAdmin +
                '}';
    }

    @Override
    public int compareTo(StudyGroup o) {
        return name.compareTo(o.name);
    }
}

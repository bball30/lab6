package common.xml;

import common.data.StudyGroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedHashSet;

/**
 * Root object of XML file. Contains collection of StudyGroup objects
 */
@XmlRootElement(name = "StudyGroups")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudyGroups {

    @XmlElement(name="StudyGroup")
    private LinkedHashSet<StudyGroup> studyGroups = null;

    public StudyGroups() {
    }

    /**
     * @return collection of StudyGroup objects
     */
    public LinkedHashSet<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(LinkedHashSet<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }
}

package common.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="hairColor")
@XmlEnum
public enum Color implements Serializable {
    GREEN,
    BLACK,
    ORANGE,
    BROWN;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */

    public static String nameList(){
        String nameList = "";
        for (Color hairColor : values()){
            nameList += hairColor.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}

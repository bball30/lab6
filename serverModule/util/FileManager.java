package serverModule.util;

import common.data.StudyGroup;
import common.exceptions.NoAccessToFileException;
import common.xml.StudyGroups;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public class FileManager {
    private final String envVariable;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    private InputStreamReader getInputStreamReader() throws FileNotFoundException, NoAccessToFileException {
        File file = new File(System.getenv(envVariable));
        if (file.exists() && !file.canRead()) throw new NoAccessToFileException();
        return new InputStreamReader(new FileInputStream(file));
    }

    private BufferedWriter getBufferedWriter() throws IOException, NoAccessToFileException {
        File file = new File(System.getenv(envVariable));
        if (file.exists() && !file.canWrite()) throw new NoAccessToFileException();
        return new BufferedWriter(new FileWriter(new File(System.getenv(envVariable))));
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(LinkedHashSet<StudyGroup> collection) {
        if (System.getenv().get(envVariable) != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(StudyGroups.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                StudyGroups result = new StudyGroups();
                result.setStudyGroups(collection);
                marshaller.marshal(result, getBufferedWriter());

                System.out.println("Коллекция успешно сохранена в файл!");
            } catch (JAXBException | IOException e) {
                System.out.println("Ошибка сохранения в файл!");
            } catch (NoAccessToFileException e) {
                System.out.println("Нет доступа к файлу!");
            }
        } else System.out.println("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Reads collection from a file.
     * @return Readed collection.
     */
    public LinkedHashSet<StudyGroup> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(StudyGroups.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                unmarshaller.unmarshal(getInputStreamReader());
                StudyGroups studyGroups = (StudyGroups) unmarshaller.unmarshal(new File(System.getenv(envVariable)));
                LinkedHashSet<StudyGroup> collection = studyGroups.getStudyGroups();
                ResponseOutputer.append("Коллекция успешно загружена!\n");
                return collection;
            } catch (NoSuchElementException exception) {
                ResponseOutputer.append("Загрузочный файл пуст!\n");
            } catch (NullPointerException exception) {
                ResponseOutputer.append("В загрузочном файле не обнаружена необходимая коллекция!\n");
            } catch (IllegalStateException exception) {
                ResponseOutputer.append("Непредвиденная ошибка!\n");
            } catch (JAXBException e) {
                ResponseOutputer.append("Ошибка прочтения XML-файла\n");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                ResponseOutputer.append("Загрузочный файл не найден!\n");
            } catch (NoAccessToFileException e) {
                ResponseOutputer.append("Нет доступа к файлу!\n");
            }
        } else ResponseOutputer.append("Системная переменная с загрузочным файлом не найдена!\n");
        ResponseOutputer.append("Проверьте переменную окружения " + envVariable + " и запустите заново.\n");
        System.exit(0);
        return new LinkedHashSet<>();
    }
}

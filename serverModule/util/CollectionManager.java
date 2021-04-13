package serverModule.util;

import java.time.LocalDateTime;
import java.util.*;

import common.data.Person;
import common.data.StudyGroup;

public class CollectionManager {
    private LinkedHashSet<StudyGroup> myCollection = new LinkedHashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
    }

    /**
     * @return The collection itself.
     */
    public LinkedHashSet<StudyGroup> getCollection() {
        return myCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return myCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return myCollection.size();
    }

    /**
     * @param collection myCollection
     * @param <StudyGroup> studyGroup
     * @return The last element of the collection or null if collection is empty
     */
    public <StudyGroup> StudyGroup getLast(Collection<StudyGroup> collection) {
        if (myCollection.isEmpty()) {return null;}
        else {StudyGroup last = null;
        for (StudyGroup s : collection) last = s;
        return last;
        }
    }

    /**
     * Remove marines greater than the selected one.
     * @param studyGroup A studyGroup to compare with.
     */
    public void removeGreater(StudyGroup studyGroup) {
        myCollection.removeIf(studyGroup1 -> studyGroup1.compareTo(studyGroup) > 0);
    }

    public void removeLower(StudyGroup studyGroup) {
        myCollection.removeIf(studyGroup1-> studyGroup1.compareTo(studyGroup) < 0);
    }

    /**
     * @param id ID of the studyGroup.
     * @return studyGroup by his ID or null if studyGroup isn't found.
     */
    public StudyGroup getById(Integer id) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.getId() == id) return studyGroup;
        }
        return null;
    }

    /**
     * @param studyGroupToFind A studyGroup who's value will be found.
     * @return A studyGroup by his value or null if studyGroup isn't found.
     */
    public StudyGroup getByValue(StudyGroup studyGroupToFind) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.similar(studyGroupToFind)) return studyGroup;
        }
        return null;
    }

    public StudyGroup getByFormOfEducation(String form) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.getFormOfEducation().name().equals(form)) return studyGroup;
        }
        return null;
    }

    /**
     * Adds a new studyGroup to collection.
     * @param studyGroup A studyGroup to add.
     */
    public void addToCollection(StudyGroup studyGroup) {
        myCollection.add(studyGroup);
    }

    /**
     * Removes a new studyGroup to collection.
     * @param studyGroup A studyGroup to remove.
     */
    public void removeFromCollection(StudyGroup studyGroup) {
        myCollection.remove(studyGroup);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        myCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Integer generateNextId() {
        if (myCollection.isEmpty()) return 1;
        return getLast(myCollection).getId() + 1;
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        fileManager.writeCollection(myCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection() {
        myCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    public ArrayList<Long> sortedByShouldBeExpelled(){
        ArrayList<Long> arrayList = new ArrayList<Long>();
        for(StudyGroup sg : myCollection){
            arrayList.add(sg.getShouldBeExpelled());
        }
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        return arrayList;
    }

    public Set<Person> uniqueGroupAdmin() {
        Set<Person> set = new HashSet<Person>();
        for(StudyGroup sg : myCollection){
            set.add(sg.getGroupAdmin());
        }
        return set;
    }

    @Override
    public String toString() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (StudyGroup studyGroup : myCollection) {
            info.append(studyGroup);
            info.append("\n\n");
        }
        return info.toString();
    }
}

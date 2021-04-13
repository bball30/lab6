package clientModule.util;

import clientModule.util.Console;
import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInBoundsException;



import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Asks a user a studyGroup's value.
 */
public class Interactor {
    private final int MIN_PASSPORT_ID_LENGTH = 7;
    private final int MAX_PASSPORT_ID_LENGTH = 39;
    private final int MIN_STUDENTS_COUNT = 0;
    private final int MIN_SHOULD_BE_EXPELLED = 0;
    private final int MIN_AVERAGE_MARK = 0;
    private Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

    private Scanner userScanner;
    private boolean fileMode;

    public Interactor(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     *
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets studyGroup asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets studyGroup asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the studyGroup's name.
     *
     * @param inputTitle title of input.
     * @param minLength  min length of string
     * @param maxLength  max length of string
     * @return name
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName(String inputTitle, int minLength, int maxLength) throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Console.println(inputTitle);
                Console.print(">");
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                if (name.length() <= minLength) throw new NotInBoundsException();
                if (name.length() >= maxLength) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror(String.format("Длина строки не входит в диапазон (%d; %d)", minLength, maxLength));
            }
        }
        return name;
    }

    /**
     * Asks a user the studyGroup's X coordinate.
     *
     * @return studyGroup's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askX() throws IncorrectInputInScriptException {
        String strX = "";
        double x;
        while (true) {
            try {
                Console.println("Введите координату X:");
                Console.print(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Double.parseDouble(strX);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strX).matches())
                    Console.printerror("Координата X должна быть в диапазоне (" + (Double.MIN_VALUE)
                            + ";" + Double.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the studyGroup's Y coordinate.
     *
     * @return StudyGroup's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Double askY() throws IncorrectInputInScriptException {
        String strY = "";
        Double y;
        while (true) {
            try {
                Console.println("Введите координату Y:");
                Console.print(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Double.parseDouble(strY);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strY).matches())
                    Console.printerror("Координата Y должна быть в диапазоне (" + Double.MIN_VALUE
                            + ";" + Double.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the studyGroup's Z coordinate.
     *
     * @return StudyGroup's Z coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Double askZ() throws IncorrectInputInScriptException {
        String strZ = "";
        double z;
        while (true) {
            try {
                Console.println("Введите координату Z:");
                Console.print(">");
                strZ = userScanner.nextLine().trim();
                if (fileMode) Console.println(strZ);
                z = Double.parseDouble(strZ);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Z не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strZ).matches())
                    Console.printerror("Координата Z должна быть в диапазоне (" + Double.MIN_VALUE
                            + ";" + Double.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата Z должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return z;
    }

    /**
     * Asks a user the studyGroup's coordinates.
     *
     * @return StudyGroup's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        double x = askX();
        Double y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the studyGroup's form of education
     *
     * @return StudyGroup's form of education
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public FormOfEducation askFormOfEducation() throws IncorrectInputInScriptException {
        String strFormOfEducation;
        FormOfEducation formOfEducation;
        while (true) {
            try {
                Console.println("Список форм обучения - " + FormOfEducation.nameList());
                Console.println("Введите форму обучения:");
                Console.print(">");
                strFormOfEducation = userScanner.nextLine().trim();
                if (fileMode) Console.println(strFormOfEducation);
                formOfEducation = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Форма обучения не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Формы обучения нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return formOfEducation;
    }

    /**
     * Asks a user the studyGroup's student count
     *
     * @return StudyGroup's students count
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public long askStudentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount = "";
        long studentsCount;
        while (true) {
            try {
                Console.println("Введите количество студентов:");
                Console.print(">");
                strStudentsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strStudentsCount);
                studentsCount = Integer.parseInt(strStudentsCount);
                if (studentsCount <= MIN_STUDENTS_COUNT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strStudentsCount).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_STUDENTS_COUNT + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_STUDENTS_COUNT);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user the studyGroup's average mark
     *
     * @return StudyGroup's average mark
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public int askAverageMark() throws IncorrectInputInScriptException {
        String strAverageMark = "";
        int averageMark;
        while (true) {
            try {
                Console.println("Введите средний балл студентов:");
                Console.print(">");
                strAverageMark = userScanner.nextLine().trim();
                if (fileMode) Console.println(strAverageMark);
                averageMark = Integer.parseInt(strAverageMark);
                if (averageMark <= MIN_AVERAGE_MARK) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strAverageMark).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_AVERAGE_MARK + ";" + Integer.MAX_VALUE + ")!");
                else
                    Console.printerror("Средний балл студентов должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_AVERAGE_MARK);
            }
        }
        return averageMark;
    }

    /**
     * Asks a user the studyGroup's admin
     *
     * @return Person [Admin]
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Person askGroupAdmin() throws IncorrectInputInScriptException {
        String name = askAdminName();
        String passportID = askPassportID();
        Color hairColor = askHairColor();
        Country country = askCountry();
        Location location = askLocation();
        return new Person(name, passportID, hairColor, country, location);
    }

    /**
     * Asks a user the studyGroup's count of should be expelled students
     *
     * @return StudyGroup's count of should be expelled students
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public long askShouldBeExpelled() throws IncorrectInputInScriptException {
        String strShouldBeExpelled = "";
        long shouldBeExpelled;
        while (true) {
            try {
                Console.println("Введите количество студентов, которые должны быть отчислены:");
                Console.print(">");
                strShouldBeExpelled = userScanner.nextLine().trim();
                if (fileMode) Console.println(strShouldBeExpelled);
                shouldBeExpelled = Integer.parseInt(strShouldBeExpelled);
                if (shouldBeExpelled <= MIN_SHOULD_BE_EXPELLED) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strShouldBeExpelled).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_SHOULD_BE_EXPELLED + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_SHOULD_BE_EXPELLED);
            }
        }
        return shouldBeExpelled;
    }

    /**
     * Asks a user the admin's name
     *
     * @return Person's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askAdminName() throws IncorrectInputInScriptException {
        return askName("Введите имя админа группы:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the location's name
     *
     * @return Location's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askLocationName() throws IncorrectInputInScriptException {
        return askName("Введите имя локации:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the studyGroup's name
     *
     * @return StudyGroup's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askGroupName() throws IncorrectInputInScriptException {
        return askName("Введите имя группы:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the admin's passport ID
     *
     * @return Person's passportID
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askPassportID() throws IncorrectInputInScriptException {
        return askName("Введите ID паспорта:", MIN_PASSPORT_ID_LENGTH, MAX_PASSPORT_ID_LENGTH);
    }

    /**
     * converts Double to Float
     *
     * @param f Double value
     * @return Float value
     */
    public static Float convertToFloat(Double f) {
        return f == null ? null : f.floatValue();
    }

    /**
     * Asks a user the admin's location
     *
     * @return Person's location
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Location askLocation() throws IncorrectInputInScriptException {
        Long x = (long) askX();
        Float y = convertToFloat(askY());
        double z = askZ();
        String name = askLocationName();
        return new Location(x, y, z, name);
    }

    /**
     * Asks a user the admin's hairColor
     *
     * @return Person's hairColor
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Color askHairColor() throws IncorrectInputInScriptException {
        String strColor;
        Color hairColor;
        while (true) {
            try {
                Console.println("Список цвета волос - " + Color.nameList());
                Console.println("Введите цвет волос:");
                Console.print(">");
                strColor = userScanner.nextLine().trim();
                if (fileMode) Console.println(strColor);
                hairColor = Color.valueOf(strColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("цвет волос не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("цвета волос нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    /**
     * Asks a user the admin's country
     *
     * @return Person's country
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Country askCountry() throws IncorrectInputInScriptException {
        String strCountry;
        Country country;
        while (true) {
            try {
                Console.println("Список стран - " + Country.nameList());
                Console.println("Введите страну:");
                Console.print(">");
                strCountry = userScanner.nextLine().trim();
                if (fileMode) Console.println(strCountry);
                country = Country.valueOf(strCountry.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("страна не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("страны нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return country;
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(">");
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                Console.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}
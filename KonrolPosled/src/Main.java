import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Создание объекта Scanner для ввода данных от пользователя
        Scanner scanner = new Scanner(System.in);

        // Валидация ФИО
        String[] parts;
        boolean is_good_input = false;
        do {
            System.out.print("Введите ФИО: ");
            String fullname_str = scanner.nextLine(); // Чтение строки ввода
            parts = fullname_str.split(" ");

            if (parts.length != 3) {
                System.out.println("Неверный формат ФИО, попробуйте ещё раз");
            } else {
                is_good_input = true;
            }
        } while (!is_good_input);

        // ФИО
        String surname = parts[0];
        String name = parts[1];
        String middleName = parts[2];

        // Валидация даты рождения
        LocalDate dateOfBirth =  LocalDate.now();
        is_good_input = false;
        do {
            System.out.print("Введите дату рождения, пример (01.01.2000): ");
            String dateOfBirth_str = scanner.nextLine(); // Чтение строки ввода
            try {
                dateOfBirth = parseDate(dateOfBirth_str);
                is_good_input = true;
            } catch (DateTimeParseException e) {
                // Обработка исключения для неверного формата даты
                System.err.println("Error: Неверный формат даты. Пожалуйста, используйте dd.MM.yyyy.");
            }
        } while (!is_good_input);


        long phoneNumber = 0;
        is_good_input = false;
        do {
            System.out.print("Введите номер телефона: ");
            String phoneNumberStr = scanner.nextLine(); // для чтения строки ввода в виде строки
            try {
                phoneNumber = parsePhoneNumber(phoneNumberStr);
                is_good_input = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный аргумент");
            }
        } while (!is_good_input);

        char gender = ' ';
        is_good_input = false;
        do {
            System.out.print("Введите ваш пол, f или m: ");
            String genderStr = scanner.nextLine();
            if (genderStr.length() != 1 || genderStr.equalsIgnoreCase("f") || genderStr.equalsIgnoreCase("m")) {
//                throw new IllegalArgumentException("Неверный гендер. Expected 'f' or 'm'.");// прерывает работу программу, выскакивает ошибка
                gender = parseGender(genderStr);
                is_good_input = true;
            } else {
                System.out.print("Неверный гендер. Expected 'f' or 'm'.");
            }
        } while (!is_good_input);

        try {
            writeToFile(surname, name, middleName, dateOfBirth, phoneNumber, gender);
        } catch (IOException e){
            e.printStackTrace();
        }


}


    // Метод для разбора и валидации даты
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // шаблон of Patter
        return LocalDate.parse(dateStr, formatter);
    }

    // Метод для разбора и валидации номера телефона
    private static long parsePhoneNumber(String phoneNumberStr) {
        try {
            return Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
    }

    // Метод для разбора и валидации пола
    private static char parseGender(String genderStr) {
        // Проверка длины строки и допустимых значений
        if (genderStr.length() != 1 || !(genderStr.equalsIgnoreCase("f") ||
        genderStr.equalsIgnoreCase("m"))) {
            throw new IllegalArgumentException("Неверный гендер. Expected 'f' or 'm'.");
        }
        return genderStr.toLowerCase().charAt(0);
    }

    // Метод для записи данных в файл
    private static void writeToFile(String surname, String name, String middleName, LocalDate dateOfBirth,
    long phoneNumber, char gender) throws IOException {
        String filename = surname + ".txt"; // Формирование имени файла
        // Формирование строки для записи
        String line = String.format("%s %s %s %s %d %c", surname,
        name, middleName, dateOfBirth, phoneNumber, gender);
        // Запись строки в файл
        try (BufferedWriter writer = new BufferedWriter(new
        FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine(); // Переход на новую строку
        }
    }
}
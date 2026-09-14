import java.util.Scanner; // Scanner читає введені користувачем рядки з консолі.
public final class Input { // Допоміжний клас об'єднує перевірене консольне введення.
    private static final Scanner IN = new Scanner(System.in); // Один Scanner запобігає конфліктам читання System.in.
    public static String text(String prompt) { // Метод повертає весь рядок, включно з пробілами всередині.
        System.out.print(prompt); // Виводимо запрошення без переходу на новий рядок.
        if (!IN.hasNextLine()) throw new IllegalStateException("Введення завершено."); // EOF припиняє читання, щоб не утворився нескінченний цикл.
        return IN.nextLine(); // Зчитуємо рядок разом із переходом, але повертаємо текст без переходу.
    }
    public static int integer(String prompt, int min, int max) { // Межі min і max включені в допустимий діапазон.
        while (true) { // Повторюємо запит доти, доки користувач не введе коректне число.
            try { // Відокремлюємо успішний розбір від помилки формату.
                int value = Integer.parseInt(text(prompt).trim()); // Видаляємо крайні пробіли й перетворюємо рядок на int.
                if (value < min || value > max) throw new NumberFormatException(); // Відхиляємо також числа поза потрібними межами.
                return value; // Коректне значення завершує метод.
            } catch (NumberFormatException e) { // Ловимо неправильне число та переповнення int.
                System.out.println("Потрібне ціле число від " + min + " до " + max); // Пояснюємо користувачу обмеження.
            }
        }
    }
    public static double real(String prompt) { // Метод читає скінченне дійсне число.
        while (true) { // Невдалий ввід не завершує програму.
            try { // Спробуємо перетворити поточний рядок.
                double value = Double.parseDouble(text(prompt).trim().replace(',', '.')); // Приймаємо і кому, і крапку як десятковий роздільник.
                if (!Double.isFinite(value)) throw new NumberFormatException(); // NaN та нескінченності не є допустимими вхідними даними.
                return value; // Повертаємо прочитане число.
            } catch (NumberFormatException e) { // Обробляємо невдале перетворення.
                System.out.println("Введіть скінченне число."); // Повідомляємо про спосіб виправлення.
            }
        }
    }
}

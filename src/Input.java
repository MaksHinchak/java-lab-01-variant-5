import java.util.Scanner;
// final class — від цього класу не можна успадковуватися.
public final class Input {
    // private — поле закрите ззовні; final забороняє переприсвоєння, але не зміну вмісту об’єкта.
    // new Клас(...) створює об’єкт і викликає його конструктор.
    private static final Scanner IN = new Scanner(System.in); // Один Scanner запобігає конфліктам читання System.in.
    // static — виклик без об’єкта; public — доступ ззовні, private — лише в класі; void — без результату.
    public static String text(String prompt) { // Метод повертає весь рядок, включно з пробілами всередині.
        System.out.print(prompt);
        // throw передає помилку в catch; new створює об’єкт винятку з повідомленням.
        if (!IN.hasNextLine()) throw new IllegalStateException("Введення завершено."); // EOF припиняє читання, щоб не утворився нескінченний цикл.
        return IN.nextLine();
    }
    public static int integer(String prompt, int min, int max) { // Межі min і max включені в допустимий діапазон.
        while (true) { // Повторюємо запит доти, доки користувач не введе коректне число.
            try { // Відокремлюємо успішний розбір від помилки формату.
                // parseInt/parseDouble перетворюють текст на число; помилка формату — NumberFormatException.
                int value = Integer.parseInt(text(prompt).trim());
                // && — «і», || — «або»; праву умову перевіряють лише за потреби.
                if (value < min || value > max) throw new NumberFormatException(); // Відхиляємо також числа поза потрібними межами.
                return value;
            // catch (Тип e) перехоплює виняток із try; e.getMessage() повертає його повідомлення.
            } catch (NumberFormatException e) { // Ловимо неправильне число та переповнення int.
                System.out.println("Потрібне ціле число від " + min + " до " + max);
            }
        }
    }
    public static double real(String prompt) { // Метод читає скінченне дійсне число.
        while (true) { // Невдалий ввід не завершує програму.
            try { // Спробуємо перетворити поточний рядок.
                double value = Double.parseDouble(text(prompt).trim().replace(',', '.'));
                // Double.isFinite відкидає NaN та ±Infinity; ! заперечує перевірку.
                if (!Double.isFinite(value)) throw new NumberFormatException(); // NaN та нескінченності не є допустимими вхідними даними.
                return value;
            } catch (NumberFormatException e) { // Обробляємо невдале перетворення.
                System.out.println("Введіть скінченне число.");
            }
        }
    }
}

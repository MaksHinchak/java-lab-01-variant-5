import java.util.Scanner;
// final class — від цього класу не можна успадковуватися.
public final class Input {
    // private — поле закрите ззовні; final забороняє переприсвоєння, але не зміну вмісту об’єкта.
    private static final Scanner IN = new Scanner(System.in);
    // static — виклик без об’єкта; public — доступ ззовні, private — лише в класі; void — без результату.
    public static String text(String prompt) {
        System.out.print(prompt);
        if (!IN.hasNextLine()) throw new IllegalStateException("Введення завершено.");
        return IN.nextLine();
    }
    public static int integer(String prompt, int min, int max) {
        while (true) {
            try {
                // parseInt/parseDouble перетворюють текст на число; помилка формату — NumberFormatException.
                int value = Integer.parseInt(text(prompt).trim());
                if (value < min || value > max) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Потрібне ціле число від " + min + " до " + max);
            }
        }
    }
    public static double real(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(text(prompt).trim().replace(',', '.'));
                // Double.isFinite відкидає NaN та ±Infinity; ! заперечує перевірку.
                if (!Double.isFinite(value)) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введіть скінченне число.");
            }
        }
    }
}

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    // static — виклик без об’єкта; public — доступ ззовні, private — лише в класі; void — без результату.
    public static double expression(double x, double y) {
        double denominator = x * x + y * y;
        if (denominator == 0) throw new ArithmeticException("x та y не можуть одночасно бути нулями.");
        double value = x * y + Math.pow(x + y, 3) / denominator * (x - y);
        // Double.isFinite відкидає NaN та ±Infinity; ! заперечує перевірку.
        if (!Double.isFinite(value)) throw new ArithmeticException("Числа завеликі для double.");
        return value;
    }
    public static double[] averages(double[] a) {
        // .length — довжина масиву без дужок; для String — length(), для колекції — size().
        if (a.length < 2 || a.length > 200) throw new IllegalArgumentException("Потрібно 2..200 чисел.");
        // new тип[n] — масив незмінної довжини; числові елементи спочатку 0, посилання — null.
        double[] b = new double[a.length];
        // for (початок; умова; крок); i++ збільшує лічильник після проходу.
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (j != i) b[i] += a[j] / (a.length - 1);
            }
        }
        return b;
    }
    public static int[] compareRows(double[][] a, double[][] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = 1;
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] <= b[i][j]) result[i] = 0;
            }
        }
        return result;
    }
    public static String transform(String text) {
        // Pattern — regex; matcher(text) створює пошук у тексті; \p{L}+ знаходить Unicode-літери.
        Matcher matcher = Pattern.compile("\\p{L}+").matcher(text);
        // StringBuilder — змінний буфер тексту; append додає частини, toString дає готовий String.
        StringBuilder result = new StringBuilder();
        int end = 0;
        // find переходить до наступного збігу; group дає текст, start/end — його межі.
        while (matcher.find()) {
            // append(text, від, до) копіює проміжок із невключною правою межею.
            result.append(text, end, matcher.start());
            // codePoints().toArray() дає int[] кодів Unicode, не окремі UTF-16 char; це не потік виконання.
            int[] letters = matcher.group().codePoints().toArray();
            int last = letters[letters.length - 1];
            for (int i = 0; i < letters.length - 1; i++) {
                // appendCodePoint додає символ за Unicode-кодом; append(int) додав би цифри числа.
                if (letters[i] != last) result.appendCodePoint(letters[i]);
            }
            result.appendCodePoint(last);
            end = matcher.end();
        }
        return result.append(text.substring(end)).toString();
    }
    private static double[][] matrix(String name, int n) {
        // double[][] — масив окремих рядків; індекси [рядок][стовпець].
        double[][] values = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) values[i][j] = Input.real(name + "[" + i + "][" + j + "]= ");
        }
        return values;
    }
    // main — точка входу; String[] args містить аргументи запуску без назви програми.
    public static void main(String[] args) {
        try {
            int task = Input.integer("Завдання (1 - вираз, 2 - масив, 3 - матриці, 4 - текст): ", 1, 4);
            // switch зі стрілками -> виконує лише вибрану гілку; break не потрібен.
            switch (task) {
                case 1 -> {
                    double x = Input.real("Дійсне x: ");
                    double y = Input.real("Дійсне y: ");
                    double result = expression(x, y);
                    System.out.println("double -> double: " + result);
                    int ix = Input.integer("Ціле x: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    int iy = Input.integer("Ціле y: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    System.out.println("int -> double: " + expression(ix, iy));
                    // умова ? a : b — вибір значення: a, якщо true, інакше b.
                    double truncated = result < 0 ? Math.ceil(result) : Math.floor(result);
                    if (truncated < Integer.MIN_VALUE || truncated > Integer.MAX_VALUE) throw new ArithmeticException("Результат не вміщується в int.");
                    // (int) відкидає дробову частину до нуля; int обмежений 32 бітами.
                    System.out.println("double -> int: " + (int) result);
                }
                case 2 -> {
                    int n = Input.integer("Кількість елементів (2..200): ", 2, 200);
                    double[] a = new double[n];
                    for (int i = 0; i < n; i++) a[i] = Input.real("a[" + i + "]= ");
                    // Arrays.toString друкує елементи; звичайний println масиву їх не показує.
                    System.out.println("B = " + Arrays.toString(averages(a)));
                }
                case 3 -> {
                    int n = Input.integer("Розмір матриць (1..15): ", 1, 15);
                    double[][] a = matrix("A", n);
                    double[][] b = matrix("B", n);
                    System.out.println("X = " + Arrays.toString(compareRows(a, b)));
                }
                case 4 -> System.out.println(transform(Input.text("Текст: ")));
            }
        // catch (Тип1 | Тип2 e) — один обробник для кількох типів винятків.
        } catch (IllegalArgumentException | ArithmeticException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}

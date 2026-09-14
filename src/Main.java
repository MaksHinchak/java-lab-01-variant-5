import java.util.Arrays; // Arrays.toString форматує одновимірні масиви для друку.
import java.util.regex.Matcher; // Matcher послідовно знаходить слова, зберігаючи їхні позиції.
import java.util.regex.Pattern; // Pattern описує правило пошуку літер Unicode.
public class Main { // Лабораторна 1: чотири незалежні задачі варіанта 5.
    public static double expression(double x, double y) { // Обчислюємо xy + (x+y)^3/(x^2+y^2)*(x-y).
        double denominator = x * x + y * y; // Знаменник дорівнює сумі квадратів.
        if (denominator == 0) throw new ArithmeticException("x та y не можуть одночасно бути нулями."); // Перевіряємо область визначення.
        double value = x * y + Math.pow(x + y, 3) / denominator * (x - y); // Дужки відтворюють формулу з PDF.
        if (!Double.isFinite(value)) throw new ArithmeticException("Числа завеликі для double."); // Виявляємо числове переповнення.
        return value; // Передаємо обчислений результат виклику.
    }
    public static double[] averages(double[] a) { // Кожен елемент результату є середнім усіх a, крім поточного.
        if (a.length < 2 || a.length > 200) throw new IllegalArgumentException("Потрібно 2..200 чисел."); // Для одного елемента середнє решти не визначене.
        double[] b = new double[a.length]; // Створюємо новий масив, не змінюючи вхідний.
        for (int i = 0; i < a.length; i++) { // Обчислюємо результат окремо для кожного індексу i.
            for (int j = 0; j < a.length; j++) { // Переглядаємо всі можливі доданки.
                if (j != i) b[i] += a[j] / (a.length - 1); // Пропускаємо a[i]; попереднє ділення зменшує ризик переповнення суми.
            }
        }
        return b; // Повертаємо всі середні значення; складність O(n^2), пам'ять O(n).
    }
    public static int[] compareRows(double[][] a, double[][] b) { // Для кожного рядка перевіряємо строгі попарні нерівності A > B.
        int[] result = new int[a.length]; // Початково вектор заповнений нулями.
        for (int i = 0; i < a.length; i++) { // Зовнішній цикл перебирає рядки.
            result[i] = 1; // Спочатку припускаємо, що всі нерівності істинні.
            for (int j = 0; j < a[i].length; j++) { // Внутрішній цикл перебирає стовпці поточного рядка.
                if (a[i][j] <= b[i][j]) result[i] = 0; // Навіть одна рівність або менше значення спростовує умову.
            }
        }
        return result; // Складність для квадратних матриць O(n^2).
    }
    public static String transform(String text) { // Вилучаємо попередні входження останньої літери кожного слова.
        Matcher matcher = Pattern.compile("\\p{L}+").matcher(text); // Слово тут є неперервною послідовністю літер Unicode.
        StringBuilder result = new StringBuilder(); // Збираємо змінений текст без багаторазового копіювання рядків.
        int end = 0; // Позиція після попереднього опрацьованого слова.
        while (matcher.find()) { // Шукаємо чергове слово в початковому тексті.
            result.append(text, end, matcher.start()); // Копіюємо розділові знаки й пробіли без змін.
            int[] letters = matcher.group().codePoints().toArray(); // Працюємо з Unicode-кодами, а не половинами сурогатних пар.
            int last = letters[letters.length - 1]; // Запам'ятовуємо останню літеру поточного слова.
            for (int i = 0; i < letters.length - 1; i++) { // Перевіряємо лише літери до останньої.
                if (letters[i] != last) result.appendCodePoint(letters[i]); // Зберігаємо літери, які точно не збігаються з останньою; регістр враховується.
            }
            result.appendCodePoint(last); // Остання літера завжди залишається.
            end = matcher.end(); // Оновлюємо межу вже опрацьованого тексту.
        }
        return result.append(text.substring(end)).toString(); // Додаємо хвіст після останнього слова.
    }
    private static double[][] matrix(String name, int n) { // Читаємо квадратну матрицю заданого розміру.
        double[][] values = new double[n][n]; // Виділяємо n рядків по n елементів.
        for (int i = 0; i < n; i++) { // Перебираємо рядки, індексація починається з нуля.
            for (int j = 0; j < n; j++) values[i][j] = Input.real(name + "[" + i + "][" + j + "]= "); // Читаємо одну клітинку.
        }
        return values; // Повертаємо заповнену матрицю.
    }
    public static void main(String[] args) { // JVM починає виконання з цього методу.
        try { // Помилки області визначення показуємо зрозумілим текстом.
            int task = Input.integer("Завдання (1 - вираз, 2 - масив, 3 - матриці, 4 - текст): ", 1, 4); // Вибираємо одну з чотирьох задач.
            switch (task) { // Виконуємо тільки вибрану гілку.
                case 1 -> { // Демонструємо всі три поєднання типів з умови.
                    double x = Input.real("Дійсне x: "); // Ввід double для першого і третього режимів.
                    double y = Input.real("Дійсне y: "); // Другий аргумент формули.
                    double result = expression(x, y); // Обчислюємо результат із дійсних аргументів.
                    System.out.println("double -> double: " + result); // Перший режим не змінює тип результату.
                    int ix = Input.integer("Ціле x: ", Integer.MIN_VALUE, Integer.MAX_VALUE); // Другий режим має окремі цілі вхідні дані.
                    int iy = Input.integer("Ціле y: ", Integer.MIN_VALUE, Integer.MAX_VALUE); // Читаємо друге ціле число.
                    System.out.println("int -> double: " + expression(ix, iy)); // Розширення int до double відбувається до арифметики.
                    double truncated = result < 0 ? Math.ceil(result) : Math.floor(result); // Відкидаємо дробову частину в напрямку нуля.
                    if (truncated < Integer.MIN_VALUE || truncated > Integer.MAX_VALUE) throw new ArithmeticException("Результат не вміщується в int."); // Не допускаємо мовчазного насичення під час cast.
                    System.out.println("double -> int: " + (int) result); // Явне звуження дає третій режим обчислення.
                }
                case 2 -> { // Задача про середні арифметичні без поточного елемента.
                    int n = Input.integer("Кількість елементів (2..200): ", 2, 200); // Обмежуємо довжину за умовою та областю визначення.
                    double[] a = new double[n]; // Виділяємо пам'ять для вхідного масиву.
                    for (int i = 0; i < n; i++) a[i] = Input.real("a[" + i + "]= "); // Заповнюємо всі елементи.
                    System.out.println("B = " + Arrays.toString(averages(a))); // Друкуємо побудований масив.
                }
                case 3 -> { // Задача про порівняння рядків двох матриць.
                    int n = Input.integer("Розмір матриць (1..15): ", 1, 15); // Перевіряємо межу з методички.
                    double[][] a = matrix("A", n); // Вводимо першу квадратну матрицю.
                    double[][] b = matrix("B", n); // Вводимо другу матрицю такого самого розміру.
                    System.out.println("X = " + Arrays.toString(compareRows(a, b))); // Друкуємо логічний результат числами 0 і 1.
                }
                case 4 -> System.out.println(transform(Input.text("Текст: "))); // Читаємо й перетворюємо цілий рядок.
            }
        } catch (IllegalArgumentException | ArithmeticException | IllegalStateException e) { // Обробляємо помилки даних та завершення вводу.
            System.out.println("Помилка: " + e.getMessage()); // Пояснюємо причину без аварійного стеку викликів.
        }
    }
}

public class LabChecks { // Автономні перевірки без зовнішніх бібліотек тестування.
    private static int count = 0; // Лічильник успішно перевірених умов.
    private static void check(boolean value) { // Допоміжний метод перетворює хибну умову на провал тесту.
        if (!value) throw new AssertionError("Перевірка " + (count + 1) + " не пройшла."); // Зупиняємо тест із ненульовим кодом процесу.
        count++; // Рахуємо лише успішні перевірки.
    }
    private static void near(double actual, double expected) { // Порівнюємо double з допуском на округлення.
        check(Math.abs(actual - expected) <= 1e-9 * Math.max(1, Math.abs(expected))); // Допуск враховує масштаб очікуваного числа.
    }
    private interface Action { void run() throws Exception; } // Лямбда тесту може породжувати і перевірювані винятки.
    private static void expect(Class<? extends Throwable> type, Action action) { // Перевіряємо, що помилкові дані дають саме потрібний вид помилки.
        try { action.run(); } // Виконуємо потенційно помилкову операцію.
        catch (Throwable error) { check(type.isInstance(error)); return; } // Неправильний тип винятку теж провалює тест.
        throw new AssertionError("Очікували " + type.getSimpleName()); // Відсутність потрібного винятку є помилкою реалізації.
    }
    public static void main(String[] args) throws Exception { // Метод запускає усі перевірки цієї лабораторної.
        near(Main.expression(2, 1), 7.4); // Формула дає 2 + 27/5 = 7.4.
        expect(ArithmeticException.class, () -> Main.expression(0, 0)); // Перевіряємо заборонений нульовий знаменник.
        double[] means = Main.averages(new double[]{1, 2, 6}); // Різні елементи виявляють помилкове включення поточного числа.
        near(means[0], 4); near(means[1], 3.5); near(means[2], 1.5); // Порівнюємо кожне середнє з незалежно обчисленим значенням.
        expect(IllegalArgumentException.class, () -> Main.averages(new double[]{1})); // Середнє порожньої решти не визначене.
        check(java.util.Arrays.equals(Main.compareRows(new double[][]{{3,5},{2,4}}, new double[][]{{1,4},{2,1}}), new int[]{1,0})); // Рівність у другому рядку має дати нуль.
        check(Main.transform("мама, ананас! а").equals("мма, ананас! а")); // Вилучаємо попередню останню літеру, зберігаючи пунктуацію.
        check(Main.transform("Аа ааа").equals("Аа а")); // Регістр розрізняється, повтори однакової літери видаляються.
        check(Main.transform("").isEmpty()); // Порожній текст не спричиняє звернення до неіснуючої літери.
        System.out.println("OK: " + count + " перевірок"); // Видимий підсумок після успішного виконання.
    }
}

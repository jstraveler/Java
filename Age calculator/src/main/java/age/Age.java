package age;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/** --- Задача ---
  *
  * Создать приложение, вычисляющее возраст, c 3 параметрами: день, месяц , год (дата рождения), где месяц задается
  * в виде строки: май или январь и т. п. Результатом работы приложения должен быть возраст, выраженный в количестве лет,
  * месяцев и дней на текущую дату. Обработайте ошибки при введении параметров.
  * фомат ввода: 25 мая 2013
  * формат вывода: 3 года, 5 месяцев и 5 дней
  *
  * --- Описание, состав - 3 модуля ---
  *
  * - модуль Age - содержит класс Age с методом determineAge в котором реализована логика вычисления возраста.
  * - модуль Format - содержит класс Format в котором описаны переменные для вывода возраста в разных форматах,
  *   в зависимости от числа(ед., мнж.) и склонения. Используемая библиотека эту сложность русского языка не учитывает.
  * - модуль Exec - содержит класс Exec (Execution) с методом main в котором производится считывание данных с консоли,
  *   создание необходимого объекта, вызов метода для получения возраста и вывод результата в консоль.
 **/

public class Age {
    private Period age;
    private String date;

    Age(String date) {
        this.date = date;
    }

    public Period getAge(){
        determineAge();
        return age;
    }

    public void determineAge(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("d MMMM yyyy");
        LocalDate birthday;
        LocalDate now = new LocalDate();

        if(date!=null && date.length() > 0) {
            try {
                birthday = LocalDate.parse(date, formatter);
            }
            catch (IllegalArgumentException e) {
                birthday = null;
                age = null;
                //e.printStackTrace();
            }
            if(birthday!=null) {
                if (birthday.isAfter(now)) {
                    System.out.println("Your date from future");
                    age = null;
                } else {
                    age = new Period(birthday, now, PeriodType.yearMonthDay());
                    //age = Period.parseTo(age, yearsMonthsDays);
                }
            }
        }
        else {
            System.out.println("Enter the correct date m");
            age = null;
        }
    }
}
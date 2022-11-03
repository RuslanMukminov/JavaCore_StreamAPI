import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(30),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Количество несовершеннолетних (т.е. людей младше 18 лет):
        long countAge = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();

        // Список фамилий призывников (т.е. мужчин от 18 и до 27 лет):
        List<String> recruit = persons.stream()
                .filter(p -> p.getAge() > 17 && p.getAge() < 28)
                .filter(p -> p.getSex().equals(Sex.MAN))
                .map(Person::getFamily)
                .collect(Collectors.toList());

        // Отсортированный по фамилии список потенциально работоспособных людей
        // с высшим образованием в выборке (т.е. людей с высшим образованием
        // от 18 до 60 лет для женщин и до 65 лет для мужчин):
        List<Person> workable = persons.stream()
                .filter(p -> p.getAge() > 17)
                .filter(p -> p.getEducation().equals(Education.HIGHER))
                .filter(p -> p.getSex().equals(Sex.WOMAN) ? p.getAge() < 61
                        : p.getAge() < 66)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}

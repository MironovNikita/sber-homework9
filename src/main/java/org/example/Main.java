package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Person> someCollection = new ArrayList<>();
        someCollection.add(new Person("Феде Вальверде", 25));
        someCollection.add(new Person("Лука Модрич", 38));
        someCollection.add(new Person("Тони Кроос", 34));
        someCollection.add(new Person("Винисиус Жуниор", 23));
        someCollection.add(new Person("Тибо Куртуа", 31));

        Map<?, Person> m = Streams.of(someCollection)
                .filter(p -> p.getAge() > 30)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(Person::getName, p -> p);

        System.out.println(m.toString());
    }
}
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class StreamsTest {

    List<Person> testList = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        testList.add(new Person("Феде Вальверде", 25));
        testList.add(new Person("Лука Модрич", 38));
        testList.add(new Person("Тони Кроос", 34));
        testList.add(new Person("Винисиус Жуниор", 23));
        testList.add(new Person("Тибо Куртуа", 31));
    }

    @DisplayName("Проверка метода \"Streams.of\"")
    @Test
    void shouldMakeStreamsOfGivenCollection() {
        Streams<Person> persons = Streams.of(testList);
        List<Person> checkList = testList;


        assertEquals("Streams", persons.getClass().getSimpleName());
        assertEquals(testList.size(), checkList.size());
        assertTrue(persons.toList().containsAll(testList));
    }

    @DisplayName("Проверка метода \"filter\"")
    @Test
    void shouldFilterGivenCollectionAndReturnIt() {
        Streams<Person> persons = Streams.of(testList);
        List<Person> filtered = persons.filter(p -> p.getAge() > 30).toList();

        assertEquals(3, filtered.size());
        assertTrue(filtered.containsAll(Arrays.asList(
                new Person("Лука Модрич", 38),
                new Person("Тони Кроос", 34),
                new Person("Тибо Куртуа", 31)
        )));
    }

    @DisplayName("Проверка метода \"transform\"")
    @Test
    void shouldTransformAllCollectionElementsAndReturnIt() {
        Streams<Person> persons = Streams.of(testList);
        List<Person> transformedAge = persons.transform(p -> new Person(p.getName(), p.getAge() + 10)).toList();

        assertEquals(testList.size(), transformedAge.size());
        assertTrue(transformedAge.containsAll(Arrays.asList(
                new Person("Феде Вальверде", 35),
                new Person("Лука Модрич", 48),
                new Person("Тони Кроос", 44),
                new Person("Винисиус Жуниор", 33),
                new Person("Тибо Куртуа", 41)
        )));
    }

    @DisplayName("Проверка метода \"toMap\"")
    @Test
    void shouldMakeMapFromGivenCollectionAndReturnIt() {
        Streams<Person> persons = Streams.of(testList);
        Map<String, Integer> ageMap = persons.toMap(Person::getName, Person::getAge);

        assertEquals(testList.size(), ageMap.size());
        assertEquals(ageMap.get("Феде Вальверде"), 25);
        assertEquals(ageMap.get("Лука Модрич"), 38);
        assertEquals(ageMap.get("Тони Кроос"), 34);
        assertEquals(ageMap.get("Винисиус Жуниор"), 23);
        assertEquals(ageMap.get("Тибо Куртуа"), 31);
    }

    @DisplayName("Проверка метода \"toList\"")
    @Test
    void shouldMakeListFromGivenCollectionAndReturnIt() {
        Streams<Person> stream = Streams.of(testList);
        List<Person> checkList = stream.toList();

        assertEquals(testList.size(), checkList.size());
        assertTrue(checkList.containsAll(testList));
    }

    @DisplayName("Проверка, что при использовании Streams исходная коллекция не меняется")
    @Test
    void shouldReturnNewCollectionFromOriginal() {
        Streams<Person> persons = Streams.of(testList);
        List<Person> notFiltered = persons.toList();
        List<Person> filtered = persons.filter(p -> p.getAge() > 30).toList();

        assertEquals(5, testList.size());
        assertEquals(3, filtered.size());
        assertEquals(notFiltered, testList);
    }

    @DisplayName("Проверка работы Streams с любыми ссылочными типами - Double")
    @Test
    void shouldWorkWithAnyReferenceTypesDouble() {
        Predicate<Double> predicate = new Predicate<Double>() {
            @Override
            public boolean test(Double aDouble) {
                return aDouble > 0;
            }
        };

        List<Double> collectionToCheck = List.of(1.0, 2.0, -12.2, 3.87, -9.64, -7.4);
        var resultCollection = Streams.of(collectionToCheck).filter(predicate).toList();

        assertEquals(3, resultCollection.size());
        assertTrue(resultCollection.containsAll(List.of(1.0, 3.87, 2.0)));
    }

    @DisplayName("Проверка работы Streams с любыми ссылочными типами - Integer")
    @Test
    void shouldWorkWithAnyReferenceTypesInteger() {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer aInteger) {
                return aInteger % 2 == 0;
            }
        };

        List<Integer> collectionToCheck = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var resultCollection = Streams.of(collectionToCheck).filter(predicate).toList();

        assertEquals(5, resultCollection.size());
        assertTrue(resultCollection.containsAll(List.of(2, 10, 6, 8, 4)));
    }

    @DisplayName("Проверка работы Streams с любыми ссылочными типами - String")
    @Test
    void shouldWorkWithAnyReferenceTypesString() {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("Т");
            }
        };

        List<String> collectionToCheck = List.of("Феде Вальверде", "Лука Модрич", "Тони Кроос", "Винисиус Жуниор",
                "Тибо Куртуа");
        var resultCollection = Streams.of(collectionToCheck).filter(predicate).toList();

        assertEquals(2, resultCollection.size());
        assertTrue(resultCollection.containsAll(List.of("Тони Кроос", "Тибо Куртуа")));
    }
}
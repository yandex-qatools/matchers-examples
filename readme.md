##Предположим, 
у нас есть набор неких фруктов.

```java
public class Fruit {
    ...
    
  public Color getColor() {...}

  public boolean isSweet() {...}

  public Shape getShape() {...}
}
```

Помимо фруктов, у нас есть и конвейер, через который можно пропустить такие фрукты.
Есть и задача для конвейера - отсеять фрукты, проделав серию тестов.
И вот удача - у нас как раз под рукой оказался аппарат, который умеет определять сладкий ли фрукт, его цвет, сравнивать форму с рядом известных и делать еще множество проверок.
Аппарат этот называется JUnit.
Перед началом теста, на конвейер вываливается новый фрукт.

```java
@Before
public void setUp() throws Exception {
  someFruit = getNextFruit();
}
```

Определим сперва, что фрукт круглый:

```java
@Test
public void orangeIsRoundWithMatcher() {
  assertThat(someFruit, is(round()));
}
```

Затем, что фрукт сладкий:

```java
@Test
public void orangeIsSweetWithMatcher() {
        assertThat(someFruit, is(sweet()));
}
```
	
 
И, наконец, посмотрим на его цвет.

```java 
@Test
public void orangeHasColorWithMatcher() {
  assertThat(someFruit, hasColor(Color.ORANGE));
}
```

Для такой красоты, существует специальная библиотека [Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial). Она содержит в себе и интерфейс для реализации и методы - assertThat и assumeThat (этот метод на самом деле внутри JUnit, но использует интерфейс из Hamcrest), которые и спрашивают матчер об объекте, принимая решение.
Начиная с версии 4.11 - в зависимостях JUnit библиотека Hamcrest имеет версию не ниже 1.3. Именно она ввела, интерфейс, в котором реализовано все что описано дальше. Поэтому, используя мавен - достаточно подключить JUnit 4.11 - и минимально необходимый набор инструментов готов к использованию. А для полного набора всех доступных матчеров из поставки Hamcrest, понадобится артифакт hamcrest-all, который можно подключить отдельно.

[Как может выглядеть ваш pom](https://github.com/lanwen/matchers-examples/blob/master/pom.xml)



###Как это работает?
В библиотеке есть абстрактный класс ``TypeSafeMatcher<Fruit>`` - параметризуемый по типу проверяемого объекта.
Класс предоставляет для переопределения три метода

``public boolean matchesSafely(Fruit fruit)`` - логика проверки,  
``public void describeTo(Description description)`` - описание ожидаемого значения,  
``protected void describeMismatchSafely(Fruit item, Description mismatchDescription)`` - описание полученного значения.

Экземпляр класса, расширяющего этот, перед выполнением собственного кода выполнит родительский - рутинные проверки поступающего объекта на
null
и соответствие указанному классу.
Например, матчер проверяющий форму фрукта выглядит так:

```java
public class ShapeMatcher extends TypeSafeMatcher<Fruit> {
    private Shape expected;

    public ShapeMatcher(Shape expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Fruit fruit) {
        return expected.equals(fruit.getShape());
    }

    @Override
    protected void describeMismatchSafely(Fruit item, Description mismatchDescription) {
        mismatchDescription.appendText("fruit has shape - ").appendValue(item.getShape());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("shape - ").appendValue(expected);
    }

    @Factory
    public static ShapeMatcher round() {
        return new ShapeMatcher(Shape.ROUND);
    }
}
```

Количество кода сперва пугает. Но если приглядеться, сразу заметно - каждое логическое действие выделено в отдельный метод. А в тесте вызов умещается в одно слово - использовать очень просто!
Но и это не все
Частая ситуация, как, например, выше - необходимость использовать для проверки только одно свойство объекта. Целый класс для этого - расточительство времени и сил. Тут на помощь приходят анонимные классы Java и абстрактный класс
``FeatureMatcher<WhatWeGet, WhatWeWannaCheck>``, параметризуемый двумя типами:  
- какой объект поступит на вход  
- и свойство какого типа нужно проверить.  

Конструктор у этого класса один и требует 3 атрибута:  
- матчер, который применим к ``WhatWeWannaCheck`` типу,  
- описание ожидания (оно добавится к описанию субматчера),  
- описание полученного значения (оно добавится к мисматч-описанию субматчера).  

Потомок этого класса, переопределив метод ``featureValueOf``позволит вытащить нужное свойство из объекта, после чего применить к нему существующий матчер, а их в поставке Hamcrest хватает для любых стандартных типов.
Перепишем наш матчер для формы, а заодно и остальные, используя этот класс:

```java
public class Matchers {

    public static Matcher<Fruit> hasShape(final Shape shape) {
        return new FeatureMatcher<Fruit, Shape>(equalTo(shape), "fruit has shape - ", "shape -") {
            @Override
            protected Shape featureValueOf(Fruit fruit) {
                return fruit.getShape();
            }
        };
    }

    public static Matcher<Fruit> round() {
        return hasShape(Shape.ROUND);
    }


    public static Matcher<Fruit> sweet() {
        return new FeatureMatcher<Fruit, Boolean>(is(true), "fruit should be sweet", "sweet -") {
            @Override
            protected Boolean featureValueOf(Fruit fruit) {
                return fruit.isSweet();
            }
        };
    }


    public static Matcher<Fruit> hasColor(Color color) {
        return new FeatureMatcher<Fruit, Color>(equalTo(color), "fruit have color - ", "color -") {
            @Override
            protected String featureValueOf(Fruit fruit) {
                return fruit.getColor();
            }
        };
    }

}
```

##Feel the POWER OF MATCHERS
Одним из главных преимуществ матчеров является возможность их объединения. Для таких целей в Hamcrest есть целый ряд специальных связующих - ``allOf``, ``anyOf``, ``both``, ``either``. Каждый из них заботливо соединит и описание ожидаемого значения и описание проваленных матчеров из цепочки.

```java
@Test
public void orangeBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
  assumeThat(someFruit, both(round()).and(sweet()).and(hasColor(Color.ORANGE)));
}
```
  
[Исходники всех тестов] (https://github.com/lanwen/matchers-examples/blob/master/src/test/java/ru/yandex/qatools/examples/FruitDegustationTest.java) 


###Матчеры и коллекции
Еще одна из замечательных возможностей - применение одного или ряда матчеров к коллекции. Предположим, вместо одного фрукта, за раз стал поступать целый набор. И все нужно проверить одновременно. Больше не нужно никаких циклов, все проще простого:

```java
@Test
public void orangeBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
  assertThat(someFruitList, everyItem(both(round()).and(sweet()).and(hasColor(Color.ORANGE))));
}
```
Как говорим, так и пишем - проверить каждый элемент нашим матчером. Возможны вариации - например, проверять что в пачке есть хотя бы один, удовлетворяющий условию -
``hasItem()``

[Примеры работы с коллекцией](https://github.com/lanwen/matchers-examples/blob/master/src/test/java/ru/yandex/qatools/examples/ProductionLineTest.java)

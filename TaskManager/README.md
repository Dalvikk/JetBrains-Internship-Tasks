# TaskManager

Тестовое задание для летней стажировки `JetBrains 2021`

**Задача**

Требуется реализовать простой планировщик задач. На вход данному планировщику передается набор задач, которые он должен выполнить.
Задача может обладать зависимостями, то есть набором задач, которые требуется выполнить до выполнения данной.

Задача представляется интерфейсом:
```java
interface Task { 
    // выполняет задачу
    void execute();
    // возвращает зависимости для данной задачи
    Collection<Task> dependencies();
}
```
Требуется написать исходный код класса, реализующего планировщик:
```java
class TaskExecutor {
    void execute(Collection<Task> tasks) {
    // реализация
    }
}
```
Вы можете делать дополнительные предположения, которые вам кажутся необходимыми. Большим плюсом будет, если планировщик будет многопоточным. Данный планировщик должен быть устойчив к некорректным входным данным.

Исходный код должен быть написан на `Java` или/и `Kotlin`. 

## Решение
Тестовая программа считывает задания и зависимости.

Как видно, программа выполняется асинхронно. В примере ниже, при старте задач в порядке `T3 T4 T2`, которые не зависимы друг от друга, завершает их в порядке `T2 T3 T4`
```
Enter tasks count:
4
Enter 4 tasks name
T1 T2 T3 T4
Enter dependencies (or empty line) for T1, separated by whitespaces
T2 T3 T4
Enter dependencies (or empty line) for T2, separated by whitespaces

Enter dependencies (or empty line) for T3, separated by whitespaces

Enter dependencies (or empty line) for T4, separated by whitespaces

[T3]: Task started.
[T4]: Task started.
[T2]: Task started.
[T2]: All done after 1152 ms sleep.
[T3]: All done after 1172 ms sleep.
[T4]: All done after 3600 ms sleep.
[T1]: Task started.
[T1]: All done after 1113 ms sleep.
```

```
Enter tasks count:
3
Enter 3 tasks name
T1
T2 T3
Enter dependencies (or empty line) for T1, separated by whitespaces
T2
Enter dependencies (or empty line) for T2, separated by whitespaces
T3
Enter dependencies (or empty line) for T3, separated by whitespaces
T1
Illegal dependencies. "T3" required previously completed "T1" task, which required completed "T3".
```

### Компиляция и запуск
```
git clone https://github.com/Dalvikk/TaskManager
cd ./TaskManager/
```

```
./gradlew.bat run --console plain
```
(просто `/gradlew`, если у вас `*nix`)
или
```
./gradlew.bat build
java -jar ./TaskManager.jar
```

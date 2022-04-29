# Домашняя работа 04 (HEAP, GC)

### Таблица экспериментов

|       | до изменений                      | доработка 1             | доработка 2
|-------|-----------------------------------|-------------------------|------------------------
| 256M  | OutOfMemoryError: Java heap space | spend msec:9717, sec:9  | spend msec:2121, sec:2
| 512M  | spend msec:17566, sec:17          | spend msec:8642, sec:8  | spend msec:1440, sec:1
| 1G    | spend msec:13213, sec:13          | spend msec:8142, sec:8  | spend msec:1516, sec:1
| 1250M | spend msec:11711, sec:11          | spend msec:7938, sec:7  | spend msec:1447, sec:1
| 1500M | spend msec:9115, sec:9            | spend msec:7892, sec:7  | не стал замерять
| 1750M | spend msec:8531, sec:8            | не стал замерять        | не стал замерять
| 2G    | spend msec:9521, sec:9            | не стал замерять        | не стал замерять
| 2500M | spend msec:8626, sec:8            | не стал замерять        | не стал замерять
| 3G    | spend msec:8598, sec:8            | не стал замерять        | не стал замерять

Для кода до изменений: порог ~1500M, дальше смысла увеличивать память не было.  
Для кода с доработками 1: порог ниже, составляет ~1G.
Для кода с доработками 2: порог еще ниже, составляет ~512M.

**Доработка кода 1:** смена типа поля "value" в классе Data с Integer на int.  
**Доработка кода 2:** доработка 1 + смена типов Integer на примитив int в классе Summator

**Выводы**:
1. После определенного порога не важно сколько выделено памяти для JVM. В моем случае, порог 1500M-1750M.
Как раз, демонстрация закона Амдала.
2. Работа с примитивами экономит используемую память. 
3. Работа с примитивами позволяет снизить необходимый объем для старта приложения (Xms). 
Это же подтверждается тем, что для первоначального кода при 256M памяти, приложение упало с ошибкой
```
[1.149s][info   ][gc     ] GC(41) Pause Full (G1 Compaction Pause) 213M->212M(256M) 134.734ms
java.lang.OutOfMemoryError: Java heap space
[1.149s][info   ][gc     ] GC(15) Concurrent Mark Cycle 505.188ms
Dumping heap to ./logs/dump ...
Heap dump file created [434182734 bytes in 1.215 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

не хватило памяти
```
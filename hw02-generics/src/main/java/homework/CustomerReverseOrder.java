package homework;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    //Пояснение: заюзал LL т.к. нет никакой сортировки и он имплементирует интерфейс Queue с удобным методом pollLast.
    //Deque<Customer> linkedList = new LinkedList<>();
    private final Deque<Customer> queue = new ArrayDeque<>();

    public void add(Customer customer) {
        //linkedList.add(customer);
        queue.push(customer);
    }

    public Customer take() {
        // return linkedList.pollLast();
        return queue.pop();
    }
}

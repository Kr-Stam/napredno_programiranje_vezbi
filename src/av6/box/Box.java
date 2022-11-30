package av6.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box<T> {
    private List<T> list;
    private final Random random = new Random();

    public Box(){
        this.list = new ArrayList<>();
    }

    public void add(T element){
        list.add(element);
    }

    public T drawItem(){
        if(isEmpty())
            return null;

        return list.remove(random.nextInt(list.size()));
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
}

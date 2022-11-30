package problems.komponenti;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.switchComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде

class Component {
    private String color;
    private int weight;
    Collection<Component> innerComponents;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.innerComponents = new ArrayList<>();
    }

    public void addComponent(Component component) {
        innerComponents.add(component);
        sortComponents();
    }

    private void sortComponents() {
        innerComponents = innerComponents.stream()
                .sorted(Comparator
                        .comparing(Component::getWeight)
                        .thenComparing(Component::getColor))
                .collect(Collectors.toList());
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public void changeColor(int weight, String color) {
        if (this.weight < weight)
            this.color = color;
        innerComponents.forEach(x -> x.changeColor(weight, color));
        sortComponents();
    }

    @Override
    public String toString() {
        return String.format("%d:%s", weight, color);
    }

    public String getWithIndent(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++)
            sb.append("---");
        sb.append(this).append("\n");
        innerComponents.forEach(x -> sb.append(x.getWithIndent(indent + 1)));
        return sb.toString();
    }
}

class Window {
    String name;
    Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        this.components = new TreeMap<Integer, Component>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {
        if (components.containsKey(position))
            throw new InvalidPositionException(position);
        components.put(position, component);
    }

    public void changeColor(int weight, String color) {
        components.values().forEach(x -> x.changeColor(weight, color));
    }

    public void switchComponents(int pos1, int pos2) {
        Component tmp1 = components.get(pos1);
        Component tmp2 = components.get(pos2);
        components.put(pos1, tmp2);
        components.put(pos2, tmp1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW ").append(name).append("\n");
        components.keySet().forEach(x -> {
            sb.append(x).append(":").append(components.get(x).getWithIndent(0));
        });
        return sb.toString();
    }
}

class InvalidPositionException extends Exception {
    public InvalidPositionException(Integer pos) {
        super(String.format("Invalid position %d, alredy taken!", pos));
    }
}
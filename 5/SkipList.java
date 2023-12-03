import java.util.concurrent.ThreadLocalRandom;

class Node<T> {
    T value;
    Node<T>[] next;

    public Node(T value, int level) {
        this.value = value;
        this.next = new Node[level + 1];
    }
}

public class SkipList<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 16;

    private Node<T> head = new Node<>(null, MAX_LEVEL);
    private int level = 0; 

    public boolean contains(T value) {
        Node<T> current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                current = current.next[i];
            }
        }

        current = current.next[0];
        return current != null && current.value.equals(value);
    }

    public void add(T value) {
        int newLevel = randomLevel();

        Node<T> newNode = new Node<>(value, newLevel);

        Node<T> current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                current = current.next[i];
            }

            if (i <= newLevel) {
                newNode.next[i] = current.next[i];
                current.next[i] = newNode;
            }
        }

        if (newLevel > level) {
            level = newLevel;
        }
    }

    public void remove(T value) {
        Node<T> current = head;
        Node<T>[] update = new Node[MAX_LEVEL + 1];

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value.compareTo(value) < 0) {
                current = current.next[i];
            }

            update[i] = current;
        }

        current = current.next[0];

        if (current != null && current.value.equals(value)) {
            for (int i = 0; i <= level; i++) {
                if (update[i].next[i] != current) {
                    break;
                }

                update[i].next[i] = current.next[i];
            }

            while (level > 0 && head.next[level] == null) {
                level--;
            }
        }
    }

    private int randomLevel() {
        int level = 0;
        while (ThreadLocalRandom.current().nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }


    public void printSkipList() {
        for (int i = level; i >= 0; i--) {
            Node<T> current = head.next[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next[i];
            }
            System.out.println();
        }
    }

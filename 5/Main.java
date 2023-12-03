public class Main {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();

        skipList.add(3);
        skipList.add(6);
        skipList.add(7);
        skipList.add(9);
        skipList.add(12);

        skipList.printSkipList();

        System.out.println("Contains 7: " + skipList.contains(7));
        System.out.println("Contains 8: " + skipList.contains(8));

        skipList.remove(7);
        System.out.println("After removing 7:");
        skipList.printSkipList();
    }
}

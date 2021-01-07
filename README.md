# FindDir.java
 2年ぐらい前に書いた物を発掘したので公開

# example
    Path p = Paths.get("E:\\bideo");
    FindDir.of(p)
        .stream()
        .map(path->path.toFile().getName())
        .forEach(System.out::println);


    ArrayList<Path> recursive  = FindDir.of(p).toList();

    ArrayList<Path> childrenOnly = FindDir.of(p).depth(1, 1).toList();

    ArrayList<Path> childrenAndGrandchild = FindDir.of(p).depth(1, 2).toList();

    ArrayList<Path> grandchildOnly = FindDir.of(p).depth(2, 2).toList();

    ArrayList<Path> toArrayList  = FindDir.of(p).toList();
    HashSet<Path> toHashSet  = FindDir.of(p).toList(HashSet::new);
    LinkedList<Path> toLinkedList  = FindDir.of(p).toList(LinkedList::new);
    Stream<Path> toStream  = FindDir.of(p).stream();

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindDir {
	private Path dir;
	private int min = 1;
	private int max = Integer.MAX_VALUE;
	//factory
	private FindDir(Path dir) {
		if(!Files.isDirectory(dir)) {
			throw new IllegalArgumentException("arg must be directory path");
		}
		this.dir = dir;
	};
	public static FindDir of(Path dir) { return new FindDir(dir); }
	//setting
	public FindDir depth(int min, int max) {
		this.min = min;
		this.max = max;
		return this;
	}
	//toList
	public <C extends Collection<Path>> Stream<Path> stream() { return toList().stream(); }
	public <C extends Collection<Path>> Stream<Path> parallelStream() { return toList().parallelStream(); }
	public <C extends Collection<Path>> ArrayList<Path> toList() { return toList(ArrayList::new); }

	public <C extends Collection<Path>> C toList(Supplier<C> collectionFactory) {
		C res = collectionFactory.get();
		finding(dir, res, min, max);
		return res;
	}


	//helper
	private static void finding(Path p, Collection<Path> res, int min_, int max_) {
		try {

			List<Path> list = Files.list(p).collect(Collectors.toList());

			if (2 > min_) res.addAll(list);
			if (2 > max_) return;

			list.stream()
				.filter(Files::isDirectory)
				.forEach(d -> finding(d, res, min_ - 1, max_ - 1));//再帰のコスト重いかも

		} catch (Exception e) {
			throw new RuntimeException(e);
		} ;
	}

	//example
	public static void main(String[] args) {
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

	}
}

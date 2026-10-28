public interface ComparableTree<T> {
    boolean isLessThan(T other);
    boolean match(T other);
}

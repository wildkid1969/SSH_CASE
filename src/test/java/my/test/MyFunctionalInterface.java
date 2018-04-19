package my.test;

@FunctionalInterface
public interface MyFunctionalInterface<T,R> {
    R apply(T t);
}

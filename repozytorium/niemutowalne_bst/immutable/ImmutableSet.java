package immutable;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interfejs niemutowalnego zbioru bez nulli.
 * Nie powinno być możliwe stworzenie obiektu tego typu, który zawierałby nulla.
 * W przypadku wykrycia takiej próby należy rzucić wyjątek NullPointerException.
 */
public interface ImmutableSet<T1> extends Iterable<T1> {
    /**
     * Zwraca sumę tego zbioru i singletonu {t}.
     */
    ImmutableSet<T1> insert(T1 t);

    /**
     * Zwraca różnicę tego zbioru i singletonu {t}.
     */
    ImmutableSet<T1> remove(T1 t);

    /**
     * Zwraca informację, czy ten zbiór zawiera element t.
     */
    boolean contains(T1 t);

    /**
     * Zwraca obraz funkcji mapper na tym zbiorze.
     */
    <T2 extends Comparable<T2>> ImmutableSet<T2> map(Function<T1, T2> mapper);

    /**
     * Zwraca zbiór tych elementów, dla których spełniony jest predykat predicate.
     */
    ImmutableSet<T1> filter(Predicate<T1> predicate);
}

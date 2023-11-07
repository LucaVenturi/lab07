package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

/**
 *  This class Contains a list of elements and iterates through them by useing a Predicate that filters the elements.
 */
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final List<T> elements;
    private Predicate<T> predicate;

    /**
     *  Constructor with 1 parameter. Sets the state of my class with the given array and a Predicate that always returns true;
     * @param array the array set as the state of my class.
     */
    public IterableWithPolicyImpl(final T[] array) {
        this(array, new Predicate<T>() {

            @Override
            public boolean test(T elem) {
                return true;
            }
        });
    }

    /**
     * Constructor with 2 parameters. Sets the state of the class with the given array of {@link T} and {@link Predicate};
     * @param array An array of {@link T} that populates the List of the class.
     * @param predicate A Predicate that checks if a given element should be considered when iterating.
     */
    public IterableWithPolicyImpl(final T[] array, Predicate<T> predicate) {
        this.elements = List.of(array);
        this.predicate = predicate;
    }

    /**
     * @return A new {@link IteratorWithPolicy}
     */
    @Override
    public Iterator<T> iterator() {
        return new IteratorWithPolicy();
    }

    /**
     * Sets a {@link Predicate} to filter the elements when iterating.
     * @param filter The predicate that is used to check if an elem should be considered when iterating.
     */
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }

    /**
     * Inner class Used as Iterator.
     */
    private class IteratorWithPolicy implements Iterator<T>{

        private int index = 0;

        @Override
        public boolean hasNext() {
            while (this.index < elements.size()) {
                if (predicate.test(elements.get(index))) {
                    return true;
                }
                this.index++;
            }
            return false;
        }

        @Override
        public T next() {
            return elements.get(this.index++);
        }

    }
}
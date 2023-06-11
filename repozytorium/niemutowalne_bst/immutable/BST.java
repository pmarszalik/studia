package immutable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Iterator;
import java.util.Stack;

public class BST<T extends Comparable<T> > implements ImmutableSet<T> {
    private BST<T> left;
    private BST<T> right;
    private T value;
    /*
        dwa różne konstruktory jakbym chciał stworzyć puste zbiór, albo normalnie
     */
    public BST(T value) throws NullPointerException{
        if(value == null)
            throw new NullPointerException();
        this.value = value;
        this.left = null;
        this.right = null;
    }
    public BST(){
        this.value = null;
        this.left = null;
        this.right = null;
    }
    /*
       gettery i settery
     */
    public T getValue() { return value; }

    public void setValue(T value) { this.value = value; }

    public BST<T> getLeft() { return left; }

    public void setLeft(BST<T> left) { this.left = left; }

    public BST<T> getRight() { return right; }

    public void setRight(BST<T> right) { this.right = right; }
    /*
        zakładam, że jak mam w wierzołku wartość rowną null to tak jakby go nie było
        z tegoż powodu jak mam pusty zbiór to jest to wierzchołek z nullową wartością
        to wtedy NIE oznacza, że zbiór zawiera nulla, także wszystko gra
     */
    public boolean isEmpty(){ return this.getValue() == null; }
    public boolean contains(T t){
        if(this.isEmpty())
            return false;
        int result = this.getValue().compareTo(t);
        if(result < 0) {
            if(this.getRight() != null && !this.getRight().isEmpty())
                return this.getRight().contains(t);
            else
                return false;
        }
        else if(result == 0)
            return true;
        else {
            if(this.getLeft() != null && !this.getLeft().isEmpty())
                return this.getLeft().contains(t);
            else
                return false;
        }
    }
    /*
        metoda pomocnicza, która
        zwraca minimum z poddrzewa
     */
    public BST<T> findMin(){
        if(this.getLeft() == null || this.getLeft().isEmpty())
            return this;
        return this.getLeft().findMin();
    }
    /*
        insert i remove dość standardowo robię - jak to w bst
        z takimi dodatkami, że tak jak pisałem wyżej:
        -traktuję bst z nullowa wartością jak nulla
        -robię tak, żeby było immutable
     */
    public BST<T> insert(T t){
        if(this.isEmpty())
            return new BST<>(t);

        BST<T> newNode = new BST<>(this.getValue());
        newNode.setLeft(this.getLeft());
        newNode.setRight(this.getRight());

        int result = this.getValue().compareTo(t);
        if(result < 0){
            if(this.getRight() != null && !this.getRight().isEmpty())
                newNode.setRight(this.getRight().insert(t));
            else
                newNode.setRight(new BST<>(t));

        }
        else if(result > 0){
            if(this.getLeft() != null && !this.getLeft().isEmpty())
                newNode.setLeft(this.getLeft().insert(t));
            else
                newNode.setLeft(new BST<>(t));
        }
        return newNode;
    }
    public BST<T> remove(T t) {
        if(this.isEmpty())
            return this;
        BST<T> newNode = new BST<>(this.getValue());
        newNode.setLeft(this.getLeft());
        newNode.setRight(this.getRight());

        int result = this.getValue().compareTo(t);
        if(result < 0){
            if(this.getRight() != null && !this.getRight().isEmpty())
                newNode.setRight(this.getRight().remove(t));

        }
        else if(result > 0){
            if(this.getLeft() != null && !this.getLeft().isEmpty())
                newNode.setLeft(this.getLeft().remove(t));
        }
        else{
            if(this.getRight() == null && this.getLeft() == null){
                newNode.setValue(null);
            }
            else if(this.getLeft() == null || this.getLeft().isEmpty()){
                return this.getRight();
            }
            else if(this.getRight() == null || this.getRight().isEmpty()){
                return this.getLeft();
            }
            else{
                T minValue = this.getRight().findMin().getValue();
                newNode.setValue(minValue);
                newNode.setRight(this.getRight().remove(minValue));
            }
        }
        return newNode;
    }
    public <T2 extends Comparable<T2>> BST<T2> map(Function<T, T2> mapper) {
        BST<T2> set = new BST<>();

        for (T value: this) {
            T2 mappedValue = mapper.apply(value);
            set = set.insert(mappedValue);
        }

        return set;
    }

    public BST<T> filter(Predicate<T> predicate) {
        BST<T> set = new BST<>();

        for (T value: this) {
            if (predicate.test(value)) {
                set = set.insert(value);
            }
        }

        return set;
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator(this);
    }

    private class BSTIterator implements Iterator<T> {
        private final Stack<BST<T>> stack;

        public BSTIterator(BST<T> x){
            this.stack = new Stack<>();
            stack.push(x);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            BST<T> current = stack.pop();
            if(current.getRight() != null && !current.getRight().isEmpty())
                stack.push(current.getRight());
            if(current.getLeft() != null && !current.getLeft().isEmpty())
                stack.push(current.getLeft());
            return current.getValue();
        }
    }
}
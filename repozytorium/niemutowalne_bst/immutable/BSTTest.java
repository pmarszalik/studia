package immutable;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    @Test
    void getValue() {
        BST<Integer> bstInteger1 = new BST<>();
        bstInteger1 = bstInteger1.insert(2);
        assertEquals(2, bstInteger1.getValue());

        BST<Integer> bstInteger2 = new BST<>();
        bstInteger2 = bstInteger2.insert(10);
        assertEquals(10, bstInteger2.getValue());

        BST<String> bstString1 = new BST<>();
        bstString1 = bstString1.insert("jajko");
        assertEquals("jajko", bstString1.getValue());

        BST<String> bstString2 = new BST<>();
        bstString2 = bstString2.insert("maciej");
        assertEquals("maciej", bstString2.getValue());
    }

    @Test
    void isEmpty() {
        BST<Integer> bst1 = new BST<>(3);
        BST<Integer> bst2 = new BST<>(4);
        BST<Integer> bst3 = new BST<>();
        BST<Integer> bst4 = new BST<>();
        BST<Integer> bst5 = new BST<>();

        assertFalse(bst1.isEmpty());
        assertFalse(bst2.isEmpty());
        assertTrue(bst3.isEmpty());
        assertTrue(bst4.isEmpty());
        assertTrue(bst5.isEmpty());

        bst1 = bst1.remove(3);
        assertTrue(bst1.isEmpty());
    }

    @Test
    void containsInsertRemove() {
        BST<Integer> bstInteger = new BST<>();
        int n = 100;
        n *= 2;
        for(int i = -n; i <= n; i++){
            bstInteger = bstInteger.insert(i);
        }

        for(int i = -n;i <= n; i+=2){
            bstInteger = bstInteger.remove(i);
        }

        for(int i = -n; i<= n; i += 2){
            assertFalse(bstInteger.contains(i));
        }
        for(int i = -n + 1; i<= n - 1; i += 2){
            assertTrue(bstInteger.contains(i));
        }
    }

    @Test
    void nullException(){
        BST<Integer> bst1 = new BST<>(2391);
        BST<String> bst2 = new BST<>("ABC");
        assertThrows(NullPointerException.class, () -> new BST<Integer>(null));
        assertThrows(NullPointerException.class, () -> new BST<String>(null));
        assertThrows(NullPointerException.class, () -> bst1.insert(null));
        assertThrows(NullPointerException.class, () -> bst2.insert(null));
    }

    @Test
    void containsInsertRemove2(){
        BST<Integer> bst = new BST<>();
        int n = 100;

        for(int i = 1; i <= n; i++){
            bst = bst.insert(i);
        }

        for(int i = 1; i<= n; i ++){
            assertTrue(bst.contains(i));
        }

        bst = bst.remove(7);

        for(int i = 1; i<= n; i ++){
            if(i == 7){
                assertFalse(bst.contains(i));
                continue;
            }
            assertTrue(bst.contains(i));
        }

        bst = bst.remove(10);

        for(int i = 1; i<= n; i ++){
            if(i == 7 || i == 10){
                assertFalse(bst.contains(i));
                continue;
            }
            assertTrue(bst.contains(i));
        }

        bst = bst.remove(33);

        for(int i = 1; i<= n; i ++){
            if(i == 7 || i == 10 || i == 33){
                assertFalse(bst.contains(i));
                continue;
            }
            assertTrue(bst.contains(i));
        }

        bst = bst.remove(40);

        for(int i = 1; i<= n; i ++){
            if(i == 7 || i == 10 || i == 33 || i == 40){
                assertFalse(bst.contains(i));
                continue;
            }
            assertTrue(bst.contains(i));
        }

        for(int i = 1; i <=n ; i++)
            bst = bst.remove(i);

//        for(int i = 1; i<= n; i ++){
//            assertFalse(bst.contains(i));
//        }

    }

    @Test
    void immutable() {
        BST<String> bst0 = new BST<>();
        BST<String> bst1 = bst0.insert("A");
        BST<String> bst2 = bst1.insert("B");
        BST<String> bst3 = bst2.insert("C");
        BST<String> bst4 = bst3.insert("D");

        assertFalse(bst0.contains("A"));
        assertTrue(bst1.contains("A"));
        assertTrue(bst2.contains("A"));
        assertTrue(bst3.contains("A"));
        assertTrue(bst4.contains("A"));

        assertFalse(bst0.contains("B"));
        assertFalse(bst1.contains("B"));
        assertTrue(bst2.contains("B"));
        assertTrue(bst3.contains("B"));
        assertTrue(bst4.contains("B"));

        assertFalse(bst0.contains("C"));
        assertFalse(bst1.contains("C"));
        assertFalse(bst2.contains("C"));
        assertTrue(bst3.contains("C"));
        assertTrue(bst4.contains("C"));

        assertFalse(bst0.contains("D"));
        assertFalse(bst1.contains("D"));
        assertFalse(bst2.contains("D"));
        assertFalse(bst3.contains("D"));
        assertTrue(bst4.contains("D"));

    }

    @Test
    void map() {
        Function<Integer, String> mapper = (x) -> (x.toString());
        BST<Integer> bst = new BST<>();
        int n = 100;
        for(int i = -n; i <= n; i++){
            bst = bst.insert(i);
        }
        BST<String> bstString = bst.map(mapper);
        assertTrue(bstString.contains("0"));
        assertTrue(bstString.contains("10"));
        assertTrue(bstString.contains("2"));
        assertTrue(bstString.contains("30"));
        assertTrue(bstString.contains("42"));
        assertTrue(bstString.contains("33"));
        assertTrue(bstString.contains("3"));

        assertFalse(bstString.contains("241992"));
        assertFalse(bstString.contains("af"));
        assertFalse(bstString.contains("abcde"));
        assertFalse(bstString.contains("1020"));
        assertFalse(bstString.contains("102"));
        assertFalse(bstString.contains("10c"));


    }

    @Test
    void filter() {
        Predicate<Integer> predicate = (x) -> (x % 10 == 0);
        BST<Integer> bst = new BST<>();
        int n = 100;
        for(int i = -n; i <= n; i++){
            bst = bst.insert(i);
        }
        bst = bst.filter(predicate);
        for(int i = -n; i <= n; i++){
            if(predicate.test(i))
                assertTrue(bst.contains(i));
            else
                assertFalse(bst.contains(i));
        }
    }
}
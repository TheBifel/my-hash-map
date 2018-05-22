import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    public void testOfPutting() {
        HashMap hashMap = new HashMap();
        hashMap.put(21, 10);
        hashMap.put(25, 11);
        hashMap.put(26, 12);
        hashMap.put(27, 13);
        assertEquals(10, hashMap.get(21));
        assertEquals(11, hashMap.get(25));
        assertEquals(12, hashMap.get(26));
        assertEquals(13, hashMap.get(27));
        assertEquals(HashMap.NULL_LONG, hashMap.get(1));
    }

    @Test
    public void testOfKeyDuplication() {
        HashMap hashMap = new HashMap(5);
        hashMap.put(1, 512);
        hashMap.put(2, 513);
        hashMap.put(3, 514);
        hashMap.put(2, 515);
        hashMap.put(5, 10);
        hashMap.put(5, 20);
        hashMap.put(6, 516);
        assertEquals(20, hashMap.get(5));
        assertEquals(515, hashMap.get(2));
    }

    @Test
    public void testOfHighLoad() {
        HashMap hashMap = new HashMap(6, 0.5f);
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i, i * 2);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i * 2, hashMap.get(i));
        }
    }


    @Test
    public void testOfCapacityAndKeyDuplication() {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i, i * 2);
        }
        for (int i = 0; i < 1000; i += 2) {
            hashMap.put(i, i * 4);
        }
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                assertEquals(i * 4, hashMap.get(i));
            } else {
                assertEquals(i * 2, hashMap.get(i));
            }
        }
    }

    @Test
    public void testOfSize() {
        HashMap hashMap = new HashMap();

        for (int i = 0; i < 200; i++) {
            hashMap.put(i, i);
        }
        assertEquals(200, hashMap.size());
        for (int i = 200; i < 400; i++) {
            hashMap.put(i, i);
        }
        assertEquals(400, hashMap.size());
    }
}

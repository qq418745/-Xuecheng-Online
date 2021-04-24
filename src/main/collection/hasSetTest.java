package main.collection;

import java.util.HashSet;
import java.util.Objects;

public class hasSetTest {
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < 12; i++) {
            hashSet.add(new Dog(i));
        }
        System.out.println(hashSet);
    }

    static class Dog{
        private int i  ;



        @Override
        public int hashCode() {
            return 100;
        }

        public Dog(int i) {
            this.i = i;
        }
    }



}

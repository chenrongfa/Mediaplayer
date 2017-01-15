// Itest.aidl
package yy.chen.ipc;

// Declare any non-default types here with import statements
import yy.chen.ipc.Person;
interface Itest {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
            String getName();
            void   setName(String name);
          void  setPerson(Person person);
          Person get();
}

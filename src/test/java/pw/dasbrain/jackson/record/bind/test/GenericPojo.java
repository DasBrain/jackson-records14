package pw.dasbrain.jackson.record.bind.test;

import java.util.Objects;

public class GenericPojo<T> {
    
    String foo;
    T bar;
    
    public String getFoo() {
        return foo;
    }
    
    public void setFoo(String foo) {
        this.foo = foo;
    }
    
    public T getBar() {
        return bar;
    }
    
    public void setBar(T bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "GenericPojo{foo=" + foo + ", bar=" + bar + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar, foo);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || 
                obj instanceof GenericPojo<?> other &&
                    Objects.deepEquals(foo, other.foo) &&
                    Objects.deepEquals(bar, other.bar);
    }
}

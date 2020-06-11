package pw.dasbrain.jackson.record.bind.test;

import java.util.Objects;

public class SimplePOJO {
    
    private String foo;
    
    public String getFoo() {
        return foo;
    }
    
    public void setFoo(String foo) {
        this.foo = foo;
    }
    
    @Override
    public String toString() {
        return "SimplePOJP{foo=" + foo + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimplePOJO other && Objects.equals(foo, other.foo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(foo);
    }
}

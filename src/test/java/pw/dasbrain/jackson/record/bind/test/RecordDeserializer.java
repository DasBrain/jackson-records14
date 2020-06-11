package pw.dasbrain.jackson.record.bind.test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;

@SuppressWarnings("serial")
public class RecordDeserializer<R extends Record> extends DelegatingDeserializer {
    
    private final JavaType recordType;
    
    public RecordDeserializer(JavaType recordType, JsonDeserializer<R> deser) {
        super(deser);
        this.recordType = recordType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected JsonDeserializer<?> newDelegatingInstance(
            JsonDeserializer<?> newDelegatee) {
        return new RecordDeserializer(recordType, newDelegatee);
    }
    
}

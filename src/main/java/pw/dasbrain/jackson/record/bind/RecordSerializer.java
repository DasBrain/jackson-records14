package pw.dasbrain.jackson.record.bind;

import java.io.IOException;
import java.lang.reflect.RecordComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class RecordSerializer<R extends Record> extends StdSerializer<R> {

    public RecordSerializer(JavaType type) {
        super(type);
    }

    @Override
    public void serialize(R value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        RecordComponent[] rcs = value.getClass().getRecordComponents();

        gen.writeStartObject(value, rcs.length);
        for (RecordComponent rc : rcs) {
            String ckey = rc.getName();
            try {
                Object cv = rc.getAccessor().invoke(value);
                gen.writeObjectField(ckey, cv);
            } catch (ReflectiveOperationException t) {
                wrapAndThrow(provider, t, value, ckey);
            }
        }
        gen.writeEndObject();
    }
    
}

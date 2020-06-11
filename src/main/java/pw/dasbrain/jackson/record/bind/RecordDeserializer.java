package pw.dasbrain.jackson.record.bind;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class RecordDeserializer extends StdDeserializer<Record> {
    
    public RecordDeserializer(JavaType valueType) {
        super(valueType);
    }
    
    private record RecordComponentWithIndex(RecordComponent rc, int idx) {}

    @Override
    public Record deserialize(JsonParser p, DeserializationContext ctxt)
            throws JsonProcessingException {
        RecordComponent[] rcs = handledType().getRecordComponents();
        Map<String, RecordComponentWithIndex> rcMap = new HashMap<>(rcs.length);
        for (int i = 0; i < rcs.length; i++) {
            // TODO: Deal with annotations?
            RecordComponent rc = rcs[i];
            rcMap.put(rcs[i].getName(), new RecordComponentWithIndex(rcs[i], i));
        }
        return null;
    }
    
}

package pw.dasbrain.jackson.record.bind;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;

public class RecordDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type,
            DeserializationConfig config, BeanDescription beanDesc)
            throws JsonMappingException {
        if (type.isTypeOrSubTypeOf(Record.class)) {
            return new RecordDeserializer(type);
        }
        return null;
    }
}

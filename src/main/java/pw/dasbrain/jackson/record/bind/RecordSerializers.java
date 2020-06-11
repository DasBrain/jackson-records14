package pw.dasbrain.jackson.record.bind;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;

public class RecordSerializers extends Serializers.Base {
    
    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type,
            BeanDescription beanDesc) {
        if (type.isTypeOrSubTypeOf(Record.class)) {
            return new RecordSerializer<>(type);
        }
        return null;
    }

}

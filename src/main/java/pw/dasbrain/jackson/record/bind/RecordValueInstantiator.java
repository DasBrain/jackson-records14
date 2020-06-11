package pw.dasbrain.jackson.record.bind;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;

@SuppressWarnings("serial")
public class RecordValueInstantiator extends StdValueInstantiator {
    
    private final JavaType recordType;
    
    public RecordValueInstantiator(DeserializationConfig cfg, JavaType recordType) {
        super(cfg, recordType);
        this.recordType = recordType; 
    }
    
    @Override
    public boolean canCreateFromObjectWith() {
        return true;
    }
    
    @Override
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
        RecordComponent[] rcs = getValueClass().getRecordComponents();
        SettableBeanProperty[] result = new SettableBeanProperty[rcs.length];
        for (int i = 0; i < rcs.length; i++) {
            RecordComponent rc = rcs[i];
            JavaType type = config.getTypeFactory().constructType(rc.getGenericType(),
                    recordType.getBindings());
            result[i] = creatorProp(rc.getName(), type, i);
        }
        return result;
    }
    
    @Override
    public Object createFromObjectWith(DeserializationContext ctxt, Object[] args)
            throws IOException {
        try {
            Class<?>[] ccArgTypes = Arrays.stream(getValueClass().getRecordComponents())
                .map(rc -> rc.getType())
                .toArray(Class<?>[]::new);
            Constructor<?> cons = getValueClass().getConstructor(ccArgTypes);
            return cons.newInstance(args);
        } catch (ReflectiveOperationException t) {
            throw wrapAsJsonMappingException(ctxt, t);
        }
    }
    
    private static CreatorProperty creatorProp(String name, JavaType type, int index) {
        return CreatorProperty.construct(PropertyName.construct(name), type, null,
                null, null, null, index, null, PropertyMetadata.STD_REQUIRED);
    }
}

package pw.dasbrain.jackson.record.bind;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;

public class RecordValueInstantiators implements ValueInstantiators {
    
    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig config,
            BeanDescription beanDesc, ValueInstantiator defaultInstantiator) {
        if (Record.class.isAssignableFrom(beanDesc.getBeanClass())) {
            return new RecordValueInstantiator(config, beanDesc.getType());
        }
        return defaultInstantiator;
    }
    
}

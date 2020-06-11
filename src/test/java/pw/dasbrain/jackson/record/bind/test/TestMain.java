package pw.dasbrain.jackson.record.bind.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMain {

    public static void main(String[] args) throws JsonParseException, IOException {
        

        
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false)
                .configure(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS, false);
//         System.out.println(mapper.readValue(TestJSONs.SIMPLE_JSON, SimplePOJO.class));
        System.out.println(mapper.readValue(TestJSONs.SIMPLE_JSON, SimpleRecord.class));
        
        System.out.println(mapper.readValue(TestJSONs.SIMPLE_JSON, RenamedFieldRecord.class));
        
        
        JavaType type = mapper.getTypeFactory().constructParametricType(GenericPojo.class, SimplePOJO.class);
        GenericPojo<SimplePOJO> genpojo = mapper.readValue(TestJSONs.GENERIC_JSON, type);
        System.out.println(genpojo);
        type = mapper.getTypeFactory().constructParametricType(GenericRecord.class, SimpleRecord.class);
        GenericRecord<SimpleRecord> rec = mapper.readValue(TestJSONs.GENERIC_JSON, type);
//        type = mapper.getTypeFactory().constructParametricType(GenericRecord.class, type);
//        GenericRecord<GenericRecord<SimpleRecord>> rec2 = mapper.readValue(TestJSONs.GENERIC_JSON, type);
        System.out.println(rec);
        
        
        
//        record LocalSimpleRecord(String foo) {}
//        LocalSimpleRecord lsr = mapper.readValue(TestJSONs.SIMPLE_JSON, LocalSimpleRecord.class);
//        System.out.println(lsr);
        
        System.out.println(mapper.writeValueAsString(genpojo));
        System.out.println(mapper.writeValueAsString(rec));
    }
    
}

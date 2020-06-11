package pw.dasbrain.jackson.record.bind.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
// TestJSONs contain the JSONs as constants - as string blocks.
// String blocks break my IDE (line numbers), so I put that into an interface
import static pw.dasbrain.jackson.record.bind.test.TestJSONs.GENERIC_JSON;
import static pw.dasbrain.jackson.record.bind.test.TestJSONs.SIMPLE_JSON;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecordsTest {
    
    static {
        RecordsTest.class.getModule().addOpens(RecordsTest.class.getPackageName(),
                ObjectMapper.class.getModule());
    }
    
    private static ObjectMapper mapper;
    
    @BeforeAll
    public static void setupObjectMapper() {
        mapper = new ObjectMapper().findAndRegisterModules();
    }
    
    @Test
    void simpleRecord() throws IOException {
        SimpleRecord rec = mapper.readValue(SIMPLE_JSON, SimpleRecord.class);
        assertEquals(new SimpleRecord("bar"), rec);
    }
    
    @Test
    void simpleRenamedRecord() throws IOException {
        RenamedFieldRecord rec = mapper.readValue(SIMPLE_JSON, RenamedFieldRecord.class);
        assertEquals(new RenamedFieldRecord("bar"), rec);
    }
    
    @Test
    void genericPOJO() throws IOException {
        SimplePOJO expected = new SimplePOJO();
        expected.setFoo("bar");
        SimplePOJO actual = mapper.readValue(SIMPLE_JSON, SimplePOJO.class);
        assertEquals(expected, actual);
    }
    
    @Test
    void genericRecord() throws IOException {
        JavaType type = mapper.getTypeFactory()
                .constructParametricType(GenericRecord.class, SimpleRecord.class);
        GenericRecord<SimpleRecord> rec = mapper.readValue(GENERIC_JSON, type);
        assertEquals(new GenericRecord<>("bar", new SimpleRecord("baz")), rec);
    }
    
    
    private record NestedRecord(String foo) {}
    @Test
    void nestedRecord() throws IOException {
        NestedRecord rec = mapper.readValue(SIMPLE_JSON, NestedRecord.class);
        assertEquals(new NestedRecord("bar"), rec);
    }
    
    @Test
    @Disabled("bug in jackson")
    void localRecord() throws IOException {
        record LocalRecord(String foo) {}
        LocalRecord rec = mapper.readValue(SIMPLE_JSON, LocalRecord.class);
        assertEquals(new LocalRecord("bar"), rec);
    }
    
}

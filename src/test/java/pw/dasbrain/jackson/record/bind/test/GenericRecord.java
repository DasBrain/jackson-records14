package pw.dasbrain.jackson.record.bind.test;

public record GenericRecord<T>(String foo, T bar) {
    
}

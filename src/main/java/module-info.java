import pw.dasbrain.jackson.record.bind.RecordModule;

module pw.dasbrain.jackson.record.bind {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    
    provides com.fasterxml.jackson.databind.Module with RecordModule;
}
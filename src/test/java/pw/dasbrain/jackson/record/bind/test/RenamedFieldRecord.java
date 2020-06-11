package pw.dasbrain.jackson.record.bind.test;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RenamedFieldRecord(@JsonProperty("foo") String bar) {
    
}

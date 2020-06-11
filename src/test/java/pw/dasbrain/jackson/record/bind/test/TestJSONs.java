package pw.dasbrain.jackson.record.bind.test;

public interface TestJSONs {
    String SIMPLE_JSON = """
            {"foo": "bar"}
            """;
    
    String GENERIC_JSON = """
            {"foo": "bar", "bar": {"foo": "baz"}}
            """;
}

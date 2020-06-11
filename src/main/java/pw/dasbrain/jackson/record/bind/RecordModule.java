package pw.dasbrain.jackson.record.bind;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class RecordModule extends Module {

    @Override
    public String getModuleName() {
        return "pw.dasbrain.jackson.record.bind.RecordModule";
    }

    @Override
    public Version version() {
        return new Version(0, 1, 0, "", "pw.dasbrain", "jackson-record-bind");
    }

    @Override
    public void setupModule(SetupContext context) {
//        context.addValueInstantiators(new RecordValueInstantiators());
//        context.addSerializers(new RecordSerializers());
//        context.addDeserializers(new RecordDeserializers());
        context.appendAnnotationIntrospector(new RecordAnnotationInspector());
    }
    
}

package pw.dasbrain.jackson.record.bind;

import java.lang.reflect.RecordComponent;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

@SuppressWarnings("serial")
public class RecordAnnotationInspector extends NopAnnotationIntrospector {
    
    @Override
    public Mode findCreatorAnnotation(MapperConfig<?> config, Annotated a) {
        if (a instanceof AnnotatedConstructor cons
                && a.getType().isTypeOrSubTypeOf(Record.class)) {
            if (isCanonicalConstructor(cons))
                return Mode.DEFAULT;
        }
        return null;
    }
    
    private static boolean isCanonicalConstructor(AnnotatedConstructor cons) {
        Class<?> clazz = cons.getRawType();
        RecordComponent[] rcs = clazz.getRecordComponents();
        if (cons.getParameterCount() != rcs.length) {
            return false;
        }
        for (int i = 0; i < rcs.length; i++) {
            if (cons.getRawParameterType(i) != rcs[i].getType()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public PropertyName findNameForDeserialization(Annotated a) {
        return findPropertyName(a);
    }
    
    @Override
    public PropertyName findNameForSerialization(Annotated a) {
        return findPropertyName(a);
    }
    
    private PropertyName findPropertyName(Annotated a) {
        if (a instanceof AnnotatedMember mem &&
                Record.class.isAssignableFrom(mem.getDeclaringClass())) { 
            String name = null;
            if (a instanceof AnnotatedMethod am) {
                name = getMethodName(am);
            } else if (a instanceof AnnotatedParameter ap) {
                name = getParameterName(ap);
            }
            if (name != null) {
                return PropertyName.construct(name);
            }
        }
        return null;
    }
    
    private String getMethodName(AnnotatedMethod method) {
        if (method.hasReturnType() && method.isPublic()
                && method.getParameterCount() == 0) {
            for (RecordComponent rc : method.getDeclaringClass().getRecordComponents()) {
                if (method.getRawReturnType() == rc.getType()
                        && method.getName().equals(rc.getName())) {
                    return rc.getName();
                }
            }
        }
        return null;
    }
    
    private String getParameterName(AnnotatedParameter ap) {
        if (ap.getOwner() instanceof AnnotatedConstructor ac &&
                isCanonicalConstructor(ac)) {
            return ac.getRawType().getRecordComponents()[ap.getIndex()].getName();
        }
        return null;
    }
    
}

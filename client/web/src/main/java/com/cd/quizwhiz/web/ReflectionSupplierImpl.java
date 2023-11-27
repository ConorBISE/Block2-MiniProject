package com.cd.quizwhiz.web;

import java.util.Collection;
import java.util.HashSet;
import org.teavm.classlib.ReflectionContext;
import org.teavm.classlib.ReflectionSupplier;
import org.teavm.model.MethodDescriptor;

import com.cd.quizwhiz.client.uiframework.UIEventListener;

public class ReflectionSupplierImpl implements ReflectionSupplier {
    @Override
    public Collection<String> getAccessibleFields(ReflectionContext context, String className) {
        return new HashSet<String>();
    }

    @Override
    public Collection<MethodDescriptor> getAccessibleMethods(ReflectionContext context, String className) {
        var cls = context.getClassSource().get(className);
        if (cls == null)
            return new HashSet<>();
        
        System.out.println(className);
            
        var methods = new HashSet<MethodDescriptor>();
        for (var method : cls.getMethods()) {
            if (method.getAnnotations().get(UIEventListener.class.getName()) != null) {
                methods.add(method.getDescriptor());
            }
        }
        return methods;
    }
}
package com.sulan.webshell.network;

import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.List;

public class ExtendedSoapObject extends SoapObject {
    public ExtendedSoapObject(String namespace, String name) {
        super(namespace, name);
    }

    public ExtendedSoapObject(SoapObject o) {
        super(o.getNamespace(), o.getName());
        for (int i = 0; i < o.getAttributeCount(); i++) {
            AttributeInfo ai = new AttributeInfo();
            o.getAttributeInfo(i, ai);
            ai.setValue(o.getAttribute(i));
            addAttribute(ai);
        }

        for (int i = 0; i < o.getPropertyCount(); i++) {
            PropertyInfo pi = new PropertyInfo();
            o.getPropertyInfo(i, pi);
            pi.setValue(o.getProperty(i));
            addProperty(pi);
        }
    }


    @Override
    public SoapObject addProperty(String name, Object value) {
        if (value instanceof Object[]) {
            Object[] subValues = (Object[]) value;
            for (int i = 0; i < subValues.length; i++) {
                super.addProperty(name, subValues[i]);
            }
        } else {
            super.addProperty(name, value);
        }

        return this;
    }


    @Override
    public Object getProperty(String name) {
        List<Object> result = new ArrayList<Object>();

        for (int i = 0; i < properties.size(); i++) {
            PropertyInfo prop = (PropertyInfo) properties.elementAt(i);
            if (prop.getName() != null && prop.getName().equals(name)) {
                result.add(unwrap(prop));
            }
        }

        if (result.size() == 1) {
            return result.get(0);
        } else if (result.size() > 1) {
            return result.toArray(new Object[0]);
        } else {
            return null;
        }
    }

    public Object[] getArrayProperty(String name) {
        Object o = getProperty(name);
        Object values[] = null;
        if (o != null) {
            if (o instanceof Object[]) {
                values = (Object[]) o;
            } else {
                values = new Object[1];
                values[0] = o;
            }
        }

        return values;
    }

    Object unwrap(Object o) {
        if (o instanceof PropertyInfo) {
            return unwrap(((PropertyInfo) o).getValue());
        } else if (o instanceof SoapPrimitive || o instanceof SoapObject) {
            return o;
        }

        return null;
    }
}
package stock.biz.utils;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {
	
	public static Class<?> resolveGenericType(Class<?> clz, Class<?> baseClz, String name){
		return resolveGenericType(clz, baseClz, name, new HashMap<TypeVariable<?>, Class<?>>());
	}
	
	private static Class<?> resolveGenericType(Class<?> clz, Class<?> baseClz, String name, Map<TypeVariable<?>, Class<?>> generaicMap){
        Type type = clz.getGenericSuperclass();
        if(type instanceof Class) {
        	Class<?> tmp = (Class<?>)type;
        	if(tmp.equals(baseClz)) {
        		throw new IllegalArgumentException("Class is not generatic : " + baseClz.getName());
        	} else {
        		return resolveGenericType(tmp, baseClz, name, generaicMap);
        	}
        }else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type rawType = pt.getRawType();
            if(rawType instanceof Class) {
                Class<?> rawClz = (Class<?>)rawType;
                TypeVariable<?>[] typeVariables = rawClz.getTypeParameters();
                Type[] actualTypes = pt.getActualTypeArguments();
                for(int i=0, n=actualTypes.length; i<n; i++) {
                    Type t = actualTypes[i];
                    TypeVariable<?> tv = typeVariables[i];
                    if(t instanceof Class) {
                        generaicMap.put(tv, (Class<?>)t);
                    }else if(t instanceof GenericArrayType) {
                        throw new RuntimeException("GenericArrayType Not supported");
                    }else if(t instanceof ParameterizedType) {
                        Type tmp = ((ParameterizedType)t).getRawType();
                        if(tmp instanceof  Class) {
                            generaicMap.put(tv, (Class<?>)tmp);
                        }else{
                        	throw new RuntimeException("unexpected rawType : " + tmp.getClass() + ", ParameterizedType : " + pt);
                        }
                    }else if(t instanceof TypeVariable) {
                        TypeVariable<?> typeVariable = (TypeVariable<?>)t;
                        Class<?> actualType = generaicMap.get(typeVariable);
                        if(actualType != null) {
                            generaicMap.put(tv, actualType);
                        } else {
                            throw new RuntimeException("Resolve Generic Type failed");
                        }
                    }else if(t instanceof WildcardType) {
                        throw new RuntimeException("WildcardType Not supported");
                    }
                }
                if(rawType.equals(baseClz)){
                	TypeVariable<?> found = null;
                	for(TypeVariable<?> tv : typeVariables) {
                		if(tv.getName().equals(name)) {
                			found = tv;
                			break;
                		}
                	}
                	if(found == null) {
                		throw new IllegalArgumentException(name);
                	}
                	return generaicMap.get(found);
                }else{
                	return resolveGenericType(rawClz, baseClz, name, generaicMap);
                }
            }else {
            	throw new RuntimeException("unexpected rawType : " + rawType.getClass() + ", ParameterizedType : " + pt);
            }
        }else {
        	throw new RuntimeException("Unsupported type : " + type.getClass());
        }

    }

}

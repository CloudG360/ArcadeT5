package net.cloudsbots.archseriest.archt5.extensions;

import net.cloudsbots.archseriest.archt5.exceptions.InvalidFormatException;

public class Validator {

    public static <T> T verifyType(Object object, Class<T> type, String info) {
        net.cloudsbots.archseriest.archt5.components.Validator.notNull(object, info);
        String t = type.getTypeName();
        if(type.isInstance(object)){
            return (T) object;
        } else {
            throw new InvalidFormatException("Type does not match required type: "+info+" ("+object.getClass().getTypeName()+" != "+t+")");
        }
    }

    public static boolean verifyTypeBool(Object object, Class type) {
        if(object == null){ return false; }
        String t = type.getTypeName();
        if(type.isInstance(object)){
            return true;
        } else {
            return false;
        }
    }
}

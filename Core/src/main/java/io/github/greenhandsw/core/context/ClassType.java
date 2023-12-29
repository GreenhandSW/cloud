package io.github.greenhandsw.core.context;

import java.lang.reflect.Modifier;

public enum ClassType {
    INTERFACE,
    ABSTRACT,
    CONCRETE,
    ALL;

    private ClassType() {
    }

    protected boolean support(int modifiers) {
        return switch (this) {
            case ALL -> true;
            case INTERFACE -> Modifier.isInterface(modifiers);
            case ABSTRACT -> Modifier.isInterface(modifiers);
            case CONCRETE -> !Modifier.isInterface(modifiers) && !Modifier.isAbstract(modifiers);
            default -> false;
        };
    }
}


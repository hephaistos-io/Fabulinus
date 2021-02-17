package ch.hephaistos.fabulinus.annotations;


import java.lang.annotation.*;

/**
 * This Annotation creates a way to access the annoted variable.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GET {

    /**
     * This variable holds the name for the target function. If you want
     * to use your own function, that is <b>not</b> called <b>getVariableName</b> then define it with
     * this variable
     * @return
     */
    public String function () default "";

}

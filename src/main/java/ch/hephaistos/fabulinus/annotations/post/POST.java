package ch.hephaistos.fabulinus.annotations.post;


import ch.hephaistos.fabulinus.annotations.post.DefaultPostValidatorStrategy;

import java.lang.annotation.*;

/**
 * This Annotation creates a way to post a value the annoted variable.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface POST {

    /**
     * This variable holds the name for the target function. If you want
     * to use your own function, that is <b>not</b> called <b>setVariableName</b> then define it with
     * this variable.
     * @return
     */
    public String function() default "";


    /**
     * This variable can hold one validator class. The class should check the incoming value and make sure that it
     * meets your required criteria, and if not return false.
     * @return
     */
    public Class validator() default DefaultPostValidatorStrategy.class;

}

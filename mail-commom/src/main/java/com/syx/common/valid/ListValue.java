package com.syx.common.valid;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * ClassName: ListValue <br/>
 * Description: <br/>
 * date: 2024/3/16 14:14 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Documented
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

    String message() default "{com.syx.common.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int [] vals() default {};
}

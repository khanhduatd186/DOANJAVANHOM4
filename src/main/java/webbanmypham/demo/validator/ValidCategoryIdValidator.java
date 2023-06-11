package webbanmypham.demo.validator;

import webbanmypham.demo.enity.Category;
import webbanmypham.demo.validator.annotation.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context){
        return (category != null && category.getId() != 0L);
    }
}

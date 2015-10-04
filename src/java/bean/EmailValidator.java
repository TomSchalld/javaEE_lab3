/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Thomas
 */
@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validateEmail(context, component, value);    
    }
    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) {
        String message = "";
        String email = (String) value;
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            ((UIInput) toValidate).setValid(false);
            message = "EMail Error: Contain too many @ sign";
            context.addMessage(toValidate.getClientId(context),
                    new FacesMessage(message));
        }
    }
}

package adapp.controle.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marculino
 */
@Target(ElementType.TYPE)//method
@Retention(RetentionPolicy.RUNTIME)

public @interface Menu {

    String Menu();

    String URL();

}

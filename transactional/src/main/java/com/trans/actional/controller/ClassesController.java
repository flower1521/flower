package com.trans.actional.controller;

import com.trans.actional.model.Classes;
import com.trans.actional.service.ClassesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2019/11/24.
 */
@RestController
public class ClassesController {
    private static Logger logger = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private ClassesService classesService;

    @RequestMapping(value = "/getClasses/{id}", method = RequestMethod.GET)
    public String get(@PathVariable("id") int id) {
//        Classes classes = classesService.getClass(id);
//        Classes classes = classesService.getClass2(id);
//        Classes classes = classesService.getClass3(id);
        Classes classes = classesService.getClass4(id);
        System.out.println(classes.toString());
        logger.info("classes => " + classes.toString());
        return "ok";
    }
}

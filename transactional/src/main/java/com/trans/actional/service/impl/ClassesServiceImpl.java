package com.trans.actional.service.impl;

import com.trans.actional.mapper.ClassesMapper;
import com.trans.actional.model.Classes;
import com.trans.actional.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2019/11/24.
 */
@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes getClass(int id) {
        return classesMapper.getClass(id);
    }

    @Override
    public Classes getClass2(int id) {
        return classesMapper.getClass2(id);
    }

    @Override
    public Classes getClass3(int id) {
        return classesMapper.getClass3(id);
    }

    @Override
    public Classes getClass4(int id) {
        return classesMapper.getClass4(id);
    }
}

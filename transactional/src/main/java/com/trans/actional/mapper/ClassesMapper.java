package com.trans.actional.mapper;

import com.trans.actional.model.Classes;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2019/11/24.
 */
@Repository
public interface ClassesMapper {
    Classes getClass(int id);
    Classes getClass2(int id);
    Classes getClass3(int id);
    Classes getClass4(int id);
}

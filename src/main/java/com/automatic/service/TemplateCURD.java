package com.automatic.service;

import com.cloud.Code;
import com.cloud.MatrixModel;
import com.automatic.entity.EntityCURD;
import com.automatic.exception.BusinessException;
import com.automatic.model.ModelCURD;

/**
 * 简单CURD模板
 * Created by LiaoKe on 2017/6/21.
 */
public  interface TemplateCURD {
    <M extends ModelCURD>void create(M m);
    void delete(String id) throws BusinessException;
    <M extends ModelCURD>void update(M m) throws BusinessException;
    <S extends EntityCURD>S get(String id) throws BusinessException;
    <S extends EntityCURD>Code.ViewPage<S> gets(MatrixModel model, Integer pageNum, Integer pageSize);
}

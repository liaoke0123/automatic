package ${packageName};

import com.cloud.Code;
import com.cloud.MatrixModel;
import com.note.dao.${entityPackageName}.${className}Repository;
import com.note.entity.EntityCURD;
import com.note.entity.${entityPackageName}.${className};
import com.note.exception.BusinessException;
import com.note.model.ModelCURD;
import com.note.model.${entityPackageName}.${className}Model;
import com.note.model.${entityPackageName}.${className}UpdateModel;
import com.note.service.${entityPackageName}.I${className}Service;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created by LiaoKe on ${date}
* From Automatic
*/
@Service
public class ${className}Service implements I${className}Service {

    @Resource
    Code code;

    @Resource
    ${className}Repository repository;

    @Override
    public <S extends EntityCURD> S valid(String id) throws BusinessException {
        ${className} data =  repository.findOne(id);
        if(data==null){
            throw new BusinessException(10000,code);//TODO Please initialize the code
        }
        return (S)data;
    }


    @Override
    public <M extends ModelCURD> void create(M m) {
        ${className}Model model = ( ${className}Model) m;
        ${className} data = new  ${className}();
        createBase(data,model);
        data.setCreateDate(new Date());
        repository.save(data);
     }


    @Override
    public void delete(String id) throws BusinessException {
        valid(id);
        repository.delete(id);
    }


    @Override
    public <M extends ModelCURD> void update(M m) throws BusinessException {
        ${className}UpdateModel model = (${className}UpdateModel)m;
        ${className} data = valid(model.getId());
        createBase(data,model);
        <#list attrs as attr>
            <#if attr.createFlag = false && attr.updateFlag = true>
        data.set${attr.fieldName?cap_first}(model.get${attr.fieldName?cap_first}());
            </#if>
        </#list>
        repository.saveAndFlush(data);
    }

    @Override
    public <S extends EntityCURD> S get(String id) throws BusinessException {
        return (S)valid(id);
    }

    @Override
    public <S extends EntityCURD> Code.ViewPage<S> gets(MatrixModel model, Integer pageNum, Integer pageSize) {
        Page<${className}> page = repository.findAll(MatrixModel.createSpecification(model),MatrixModel.createSort(pageNum, pageSize, model.getSorts()));
        return  (Code.ViewPage<S>)code.JpaPageToViewPage(page);
    }


    /////////////////////////////////////////////////////////////////////private/////////////////////////////////////////////////////////////////////
    private void createBase(${className} data,${className}Model model){
    <#list attrs as attr>
        <#if attr.createFlag>
        data.set${attr.fieldName?cap_first}(model.get${attr.fieldName?cap_first}());
        </#if>
    </#list>
    }




}
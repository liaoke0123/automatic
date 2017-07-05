package ${packageName};

import com.cloud.Code;
import com.cloud.MatrixModel;
import com.note.entity.${entityPackageName}.${className};
import com.note.exception.BusinessException;
import com.note.model.${entityPackageName}.${className}Model;
import com.note.model.${entityPackageName}.${className}UpdateModel;
import com.note.service.${entityPackageName}.I${className}Service;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.io.IOException;


/**
* Created by LiaoKe on ${date}
* From Automatic
*/
@RestController
@RequestMapping("/${entityPackageName}")
@Api(description="${className}接口")
public class ${className}Controller {



    @Resource
    I${className}Service iService;

    @ApiOperation(value = "创建${className}")
    @PostMapping
    public void create( @Validated @RequestBody ${className}Model model ) throws IOException {
        iService.create(model);
    }

    @ApiOperation(value = "删除${className}")
    @DeleteMapping("{${className?uncap_first}Id}")
    public void delete( @PathVariable String ${className?uncap_first}Id ) throws IOException, BusinessException {
        iService.delete(${className?uncap_first}Id);
    }

    @ApiOperation(value = "修改${className}")
    @PutMapping
    public void update( @Validated @RequestBody ${className}UpdateModel model ) throws IOException, BusinessException {
        iService.update(model);
    }


    @ApiOperation(value = "获取单个${className}")
    @GetMapping("{${className?uncap_first}Id}")
    public ${className} get( @PathVariable String ${className?uncap_first}Id ) throws IOException, BusinessException {
      return iService.get(${className?uncap_first}Id);
    }

    @ApiOperation(value = "通用查看${className}列表")
    @PostMapping("{pageNum}/{pageSize}")
    public Code.ViewPage<${className}> gets( @RequestBody MatrixModel model, @PathVariable Integer pageNum, @PathVariable Integer pageSize ) throws IOException {
        return iService.gets(model, pageNum, pageSize);
    }







}
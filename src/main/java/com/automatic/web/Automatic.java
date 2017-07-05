package com.automatic.web;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动代码生成
 * Created by LiaoKe on 2017/6/30.
 */
@Configuration
@Log4j
@Controller
@RequestMapping("/automatic")
@Api(description="自动化代码生成")
public class Automatic {

    @Resource
    freemarker.template.Configuration cfg;



    @ApiOperation(value = "生成器接口")
    @PostMapping("create")
    @ResponseBody
    public void create(@Validated @RequestBody Automatic.AutomaticModel model) throws IOException, TemplateException {
        automatic(model);
    }



    /**
     * 自动构建方法
     */
    public void automatic(AutomaticModel model) throws IOException, TemplateException {
        //过滤model，填入fieldType
        model.getEntityAttributes().stream().forEach(p->filtration(p));
        //获取项目物理地址路径
        String realPath = "";
        //用户自定义项目地址
        if(model.getRealPath()!=null){
            String lastChar =  model.getRealPath().substring(model.getRealPath().length()-1);
            if((!"/".equals(lastChar))&&(!"\\".equals(lastChar))){
                realPath = model.getRealPath()+"\\";
            }else{
                realPath = model.getRealPath();
            }
        }else {
            realPath= this.getClass().getResource("/").getPath().toString().replace("target/classes/","");
        }
        //找到模板地址
        cfg.setDirectoryForTemplateLoading(new File(filePathJoint(this.getClass().getResource("/").getPath().toString().replace("target/classes/",""),"\\src\\main\\resources\\templates")));
        //开始创建实体
        createEntity(model,realPath);
        //开始构建用于create方法的model
        createModel(model,realPath);
        //开始构建用于update方法的model
        createUpdateModel(model,realPath);
        //开始构建用于Repository
        createRepository(model,realPath);
        //开始构建IService
        createIService(model,realPath);
        //开始构建Service
        createService(model,realPath);
        //开始构建controller
        createController(model,realPath);

    }

    /**
     * 开始构建controller
     * @param model
     * @param realPath
     */
    private void createController(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("controllerTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".web.",model.getEntityPackageName()));
        root.put("entityPackageName", model.getEntityPackageName());
        root.put("className", model.getEntityName());
        root.put("date",new Date().toString());
        //输出目录
        out(model,"web",realPath,temp,root,model.getEntityName(),model.getEntityName()+"Controller");

    }

    /**
     * 开始构建Service
     * @param model
     * @param realPath
     */
    private void createService(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("serviceTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".service.",model.getEntityPackageName()));
        root.put("className", model.getEntityName());
        root.put("entityPackageName", model.getEntityPackageName());
        root.put("date",new Date().toString());
        root.put("attrs", model.getEntityAttributes());
        //输出目录
        out(model,"service",realPath,temp,root,model.getEntityName(),model.getEntityName()+"Service");
    }

    /**
     * 开始构建IService
     * @param model
     * @param realPath
     */
    private void createIService(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("iServiceTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".service.",model.getEntityPackageName()));
        root.put("className", model.getEntityName());
        root.put("date",new Date().toString());
        //输出目录
        out(model,"service",realPath,temp,root,model.getEntityName(),"I"+model.getEntityName()+"Service");
    }

    /**
     * 开始构建用于Repository
     * @param model
     * @param realPath
     */
    private void createRepository(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("repositoryTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".dao.",model.getEntityPackageName()));
        root.put("entityPackageName", model.getEntityPackageName());
        root.put("className", model.getEntityName());
        root.put("date",new Date().toString());
        //输出目录
        out(model,"dao",realPath,temp,root,model.getEntityName(),model.getEntityName()+"Repository");
    }

    /**
     * 开始构建用于update方法的model
     * @param model
     * @param realPath
     */
    private void createUpdateModel(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("updateModelTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".model.",model.getEntityPackageName()));
        root.put("className", model.getEntityName());
        root.put("date",new Date().toString());
        root.put("attrs", model.getEntityAttributes());
        //输出目录
        out(model,"model",realPath,temp,root,model.getEntityName(),model.getEntityName()+"UpdateModel");
    }

    /**
     * 开始构建用于create方法的model
     * @param model
     * @param realPath
     */
    private void createModel(AutomaticModel model, String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("createModelTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".model.",model.getEntityPackageName()));
        root.put("className", model.getEntityName());
        root.put("project", model.getProjectPackageName());
        root.put("date",new Date().toString());
        root.put("attrs", model.getEntityAttributes());
        //输出目录
        out(model,"model",realPath,temp,root,model.getEntityName(),model.getEntityName()+"Model");
    }




    /**
     * 创建实体
     */
    private void createEntity(AutomaticModel model,String realPath) throws IOException, TemplateException {
        //获取模板
        Template temp = cfg.getTemplate("entityTemplate.ftl");
        //设置模板参数
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("packageName",  filePathJoint(model.getProjectPackageName(),".entity.",model.getEntityPackageName()));
        root.put("project", model.getProjectPackageName());
        root.put("className", model.getEntityName());
        root.put("date",new Date().toString());
        root.put("defaultIdConfig",model.getDefaultIdConfig()); //选择id模型
        root.put("defaultMySqlConfig",model.getDefaultMySqlConfig()); //选择支持mysql模型
        root.put("tableName",model.getTableName());//id模型 及mysql模型都为真的情况下填写表明
        root.put("attrs", model.getEntityAttributes());
        //输出目录
        out(model,"entity",realPath,temp,root,model.getEntityName(),model.getEntityName());
    }

    /**
     * 文件输出
     * @param model
     * @param type
     * @param realPath
     * @param temp
     * @param root
     * @param entityName
     * @param fileName
     * @throws IOException
     * @throws TemplateException
     */
    private void out(AutomaticModel model, String type, String realPath, Template temp, Map<String, Object> root, String entityName, String fileName) throws IOException, TemplateException {
        String[] packageFileChunk = model.getProjectPackageName().split("\\.");
        StringBuffer sb  = new StringBuffer();
        for (String s : packageFileChunk) {
            sb.append(s);
            sb.append("/");
        }
        File dir = new File(filePathJoint(realPath,"src\\main\\java\\", sb.toString(),type,"\\",model.getEntityPackageName()));
        if(!dir.exists()){
            dir.mkdirs();
        }
        File target = new File(dir, filePathJoint(fileName,".java"));
        //如果未设置强行修改并且文件存在，则不修改新文件
        if(!model.getCompelFlag()&&target.exists()){
            log.warn(target.getName()+" is exists!");
            return;
        }
        OutputStream fos = new FileOutputStream(target); //java文件的生成目录
        try {
            Writer out = new OutputStreamWriter(fos);
            temp.process(root, out);
        }finally {
            fos.flush();
            fos.close();
        }
    }

    /**
     * 属性类型枚举类
     */
    public enum PropertyType {
        Byte,Short,Long,Boolean,Float,Double,String,Integer,Date
    }

    /**
     * 自动化创建model
     * 通过此model自动创建实体
     * 自动创建CreateModel
     * 自动创建UpdateModel
     * 自动创建Dao
     * 自动创建IService
     * 自动创建Service
     * 自动创建Controller（加入swagger注解）
     */
    @Data
    public static class AutomaticModel{
        @ApiModelProperty(value="真实地址")
        String realPath;
        @ApiModelProperty(value="项目包名",required=true)
        @NotBlank
        String projectPackageName;
        @ApiModelProperty(value="是否在其中任意文件存在的情况下强行修改",required=true)
        @NotNull
        Boolean compelFlag;
        @ApiModelProperty(value="字段",required=true)
        @NotNull
        @Valid //级联验证
        List<EntityAttribute> entityAttributes;
        @ApiModelProperty(value="实体名称",required=true)
        @NotBlank
        String entityName;
        @ApiModelProperty(value="实体所属包名",required=true)
        @NotBlank
        String entityPackageName;
        @ApiModelProperty(value="表名",required=true)
        @NotBlank
        String tableName;//id模型 及mysql模型都为真的情况下填写表明
        @ApiModelProperty(value="mysql，ID，UPDATE支持，默认开启",required=true,hidden = true)
        Boolean defaultIdConfig=true;
        @ApiModelProperty(value="mysql支持，默认开启",required=true,hidden = true)
        Boolean defaultMySqlConfig=true;
    }

    /**
     * 实体的字段模型
     */
    @Data
    public static class EntityAttribute{

        @ApiModelProperty(value="字段名称",required=true)
        @NotBlank
        private String fieldName;//字段名称
        @ApiModelProperty(value="字段描述",required=true)
        @NotBlank
        private String describe;//字段描述
        @ApiModelProperty(value="自定义字段类型，与基本字段类型必须二选其一")
        private String fieldType;//可选择基本类型，也可以选择自定义类型
        @ApiModelProperty(value="基本字段类型，与自定义字段类型必须二选其一")
        private PropertyType autoFieldType;//基本字段类型
        @ApiModelProperty(value="自定义类型包名")
        private String packageName;//自定义类型包名，若为项目entity包内的实体，则无需填写
        @ApiModelProperty(value="该字段是否在创建model中，若此字段为true，则必定会出现在updateModel中，updateFlag字段无效",required=true)
        @NotNull
        private Boolean createFlag;//是否出现在创建model中
        @ApiModelProperty(value="该字段是否在修改model中,若已经出现在createModel中则此字段无效，请填写任意Boolean",required=true)
        @NotNull
        private Boolean updateFlag;//是否出现在修改model中
        @ApiModelProperty(value="是否需要手动加入注解 源码中会生成/ / T O D O",required=true)
        @NotNull
        private Boolean annotationFlag;//是否需要手动加入注解 源码中会生成/ / T O D O


        /**
         * 传递基本类型枚举常量
         * @param propertyType
         */
        public void setFieldType(PropertyType propertyType) {
            this.fieldType = propertyType.toString();
        }

        public void setFieldType(String fieldType){this.fieldType = fieldType;}
    }


    /**
     * 文件地址拼接
     * @return
     */
    private static String filePathJoint(String... args){
        StringBuffer stringBuffer=new StringBuffer();
        for (String arg : args) {
            stringBuffer.append(arg);
        }
        return stringBuffer.toString();
    }


    /**
     * 过滤model填入fieldType类型
     * @param attribute
     */
    private void filtration(EntityAttribute attribute)  {
        if(attribute.getAutoFieldType()==null&&(attribute.getFieldType()==null||attribute.getFieldType().equals(""))){
            attribute.setFieldType("YOUR FIELD TYPE IS NULL");
            return;
        }
        //若选择了枚举的基本字段类型则按次类型定义字段类型
        if(attribute.getAutoFieldType()!=null){
            attribute.setFieldType(attribute.getAutoFieldType());
            return;
        }
    }
}

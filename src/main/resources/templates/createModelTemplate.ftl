package ${packageName};

import ${project}.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;


/**
* Created by LiaoKe on ${date}
* From Automatic
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ${className}Model extends  ModelCURD {

    <#list attrs as attr>
        <#if attr.createFlag>
    @ApiModelProperty(value="${attr.describe}",required=true)
    <#if attr.createFlag>
        <#if attr.fieldType='String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    private ${attr.fieldType} ${attr.fieldName};

        </#if>
    </#list>


}
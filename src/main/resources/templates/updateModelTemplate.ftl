package ${packageName};

import java.util.*;
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
public class ${className}UpdateModel extends ${className}Model {

    @ApiModelProperty(value="id",required=true)
    @NotBlank
    private String id;

    <#list attrs as attr>
    <#if attr.createFlag = false && attr.updateFlag = true>
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
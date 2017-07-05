package ${packageName};


import ${project}.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
<#if defaultIdConfig = true>
import org.hibernate.annotations.GenericGenerator;
</#if>
<#if defaultMySqlConfig = true>
import javax.persistence.*;
</#if>
import java.util.*;
import java.io.Serializable;
<#list attrs as attr>
    <#if attr.packageName??>
import ${attr.packageName};
    </#if>
</#list>
/**
* Created by LiaoKe on ${date}
* From Automatic
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
<#if defaultMySqlConfig = true>
@Entity
    <#if tableName??>
@Table(name=" _${tableName}")
    <#else>
@Table(name=" _${className}")
    </#if>
</#if>
public class ${className} extends EntityCURD implements Serializable{

    <#if defaultIdConfig = true>
        <#if defaultMySqlConfig = true>
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
        </#if>
    private String id;

        <#if defaultMySqlConfig = true>
    @Column(updatable = false) // 默认不修改此数据
        </#if>
    private Date createDate;
    </#if>

    <#list attrs as attr>
        <#if attr.annotationFlag = true>
    //TODO Miss Annotations This message come from Automatic
        </#if>
    private ${attr.fieldType} ${attr.fieldName}; //${attr.describe}

    </#list>


}
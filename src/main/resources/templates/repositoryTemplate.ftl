package ${packageName};

import ${projectPackageName}.entity.${entityPackageName}.${className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
* Created by LiaoKe on ${date}
* From Automatic
*/
public interface ${className}Repository extends JpaRepository<${className}, String>,JpaSpecificationExecutor<${className}> {

}

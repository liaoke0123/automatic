package com.automatic.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射
	 * 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试） 重写该方法需要 extends
	 * WebMvcConfigurerAdapter
	 * 
	 */
	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("swagger-ui.html")
	// .addResourceLocations("classpath:/META-INF/resources/");
	//
	// registry.addResourceHandler("/webjars/**")
	// .addResourceLocations("classpath:/META-INF/resources/webjars/");
	// }

	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
	 *
	 */
	@Bean
	public Docket cmsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("note")
				.genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.globalOperationParameters(new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
					{
						add(new ParameterBuilder().name("Authorization")
								.description("请求可能需要在HTTP header中加入token。\r\n请填写\"Bearer {token}\"").modelRef(new ModelRef("string"))
								.parameterType("header").required(false).build());
					}
				})
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.automatic.web"))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.build().apiInfo(cmsApiInfo());
	}

	private ApiInfo cmsApiInfo() {
		@SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo("代码生成器 API文档", // 大标题
				"本接口信息只做测试用，正式地址为：{api-gateway}/{接口地址}", // 小标题
				"0.1", // 版本
				"", "LK", // 作者
				"", // 链接显示文字
				""// 网站链接
		);

		return apiInfo;
	}

	
	
}
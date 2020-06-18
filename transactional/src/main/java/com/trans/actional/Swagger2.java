package com.trans.actional;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by lenovo on 2019/12/9.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 创建API应用
     * appinfo()增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制那些借口暴露给Swagger来展现
     * 本例采用置顶扫描的包路径来定义指定要建立API的目录
     * @return
     */
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.trans.actional.controller"))
                .apis(RequestHandlerSelectors.any()) //对所有api进行监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
//                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 创建该API的基本信息（这些基本信息会展示在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restful风格")
                .termsOfServiceUrl("http://www.baidu.com")
//                .contact(new Contact("", "", ""))
                .version("1.0")
                .build();
    }
}

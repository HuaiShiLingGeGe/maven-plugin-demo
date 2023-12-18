package org.example.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * 使用注解的方式自定义一个 Maven 插件
 * 1、集成AbstractMojo类，重写execute方法
 * 2、添加@Mojo注解，指定注解中的属性
 */
@Mojo(name = "my-maven-plugin", defaultPhase = LifecyclePhase.PACKAGE)
public class MyMavenPlugin extends AbstractMojo {

    /**
     * 获取目标 Maven 工程的整个 pom.xml 的配置文件信息
     * 用于注入插件的配置信息 configuration
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject mavenProject;

    @Parameter(required = true)
    private String name;

    @Parameter
    private String gender;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        log.info("==========> 开始执行 goal:my-maven-plugin 的 execute 方法");
        log.info("==========> packaging: " + mavenProject.getPackaging());
        log.info("==========> project name: " + mavenProject.getBuild().getFinalName());
        log.info("==========> name value: " + name);
        log.info("==========> gender value: " + gender);
        log.info("==========> 结束执行 goal:my-maven-plugin 的 execute 方法");
    }
}

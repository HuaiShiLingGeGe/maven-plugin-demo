package org.example.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

/**
 * 实现功能：把最终打好的 jar 包移动到指定的目录
 */
@Mojo(name = "move-jar-maven-plugin", defaultPhase = LifecyclePhase.PACKAGE)
public class MoveJarPlugin extends AbstractMojo {
    /**
     * 获取目标 Maven 工程的整个 pom.xml 的配置文件信息
     * 用于注入插件的配置信息 configuration
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject mavenProject;

    /**
     * jar 包最终移动的目标绝对路径
     */
    @Parameter(required = true)
    private String destAbsoluteDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        log.info("==========> 开始执行 goal:move-jar-plugin 的 execute 方法");

        // 存放 jar 包的绝对路径
        String jarFilePath = null;

        try {
            // 拼接 jar 包名称
            String jarName = mavenProject.getBuild().getFinalName() + "." + mavenProject.getPackaging();
            log.info("==========> jar包名称: " + jarName);

            // 获取 target 所在的目录路径
            String targetDir = mavenProject.getBuild().getDirectory();
            log.info("==========> target文件夹的路径: " + targetDir);

            // 拼接 jar 文件的绝对路径
            jarFilePath = targetDir + File.separator + jarName;

            // 移动 jar 包到指定的目录（绝对路径）
            FileUtils.copyFileToDirectory(new File(jarFilePath), new File(destAbsoluteDir));
        } catch (IOException e) {
            log.info("移动 jar 包失败，jar 包所在位置：" + jarFilePath + ", 目标目录：" + destAbsoluteDir);
            throw new RuntimeException(e);
        }

        log.info("==========> 结束执行 goal:move-jar-plugin 的 execute 方法");
    }
}

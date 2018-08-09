package com.yoogun.workflow.infrastructure;

import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 工作流部署器，每个流程定义单独部署
 */
public class WorkflowDeployer implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(WorkflowDeployer.class);

    @javax.annotation.Resource
    private RepositoryService repositoryService;

    private Resource[] deploymentResources;

    public void setDeploymentResources(Resource[] deploymentResources) {
        this.deploymentResources = deploymentResources;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (deploymentResources == null || deploymentResources.length == 0) {
            logger.debug("no processes will be deployed！");
            return;
        }
        for(Resource resource : deploymentResources) {
            if(!hasDeployed(resource)) {
                this.deployResource(resource);
            }
        }
    }

    private boolean hasDeployed(Resource resource) {
        String resourceName = resource.getFilename();   //资源名称
        //按照资源名称查询仓储中的已部署对象
        List<Deployment> deployments = repositoryService.createDeploymentQuery()
                .deploymentName(resourceName).orderByDeploymenTime().desc().list();
        if(deployments == null || deployments.isEmpty()) {  //仓储中无该资源
            return false;
        }
        Deployment deployed = deployments.get(0);
        //读取仓库中的资源内容流
        InputStream repositoryResourceStream = repositoryService.getResourceAsStream(deployed.getId(), resourceName);
        if(repositoryResourceStream == null) {
            logger.warn("读取仓储资源：'" + resourceName + "'为空，重新部署该资源！");
            return false;
        }
        InputStream newResourceStream;
        try {
            newResourceStream = new FileInputStream(resource.getFile());    //新资源文件流
        } catch (IOException e) {
            throw new BusinessException("读取新资源：'" + resourceName + "'的内容出错！", e);
        }

        try {
            return IOUtils.contentEquals(repositoryResourceStream, newResourceStream);  //比较流
        } catch (IOException e) {
            throw new BusinessException("比较资源：'" + resourceName + "'的新旧内容出错！", e);
        }
    }

    /**
     * 部署资源，一个资源独占一个部署
     * @param resource 资源对象
     */
    private void deployResource(Resource resource) {
        try {
            repositoryService.createDeployment()
                    .name(resource.getFilename())
                    .addInputStream(resource.getFilename(), resource.getInputStream())
                    .deploy();
            logger.debug("process: '" + resource.getFilename() + "' deployed!");
        } catch (IOException e) {
            throw new BusinessException("deploy process:'" + resource.getFilename() + "' failed!", e);
        }
    }

}

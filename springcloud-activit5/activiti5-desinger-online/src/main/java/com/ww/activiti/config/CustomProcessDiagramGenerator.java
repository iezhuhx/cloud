package com.ww.activiti.config;
import java.awt.Color;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年5月25日 下午12:08:45
 */
public interface CustomProcessDiagramGenerator {
	InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
            List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName,
            ClassLoader customClassLoader, double scaleFactor, Color[] colors, Set<String> currIds);
}

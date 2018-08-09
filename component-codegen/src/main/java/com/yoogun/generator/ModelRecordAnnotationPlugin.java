/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yoogun.generator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ModelRecordAnnotationPlugin extends PluginAdapter {
	
   private String annotationName;

   public ModelRecordAnnotationPlugin() { }

   @Override
   public boolean validate(List<String> warnings) {
	   annotationName = properties.getProperty("annotationName");
	   boolean isValid = stringHasValue(annotationName);
	   if (!isValid) {
		   warnings.add("ModelRecordAnnotationPlugin: annotationName property not found");
	   }
	   return isValid;
	}

   @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
	   if (annotationName != null) {
		   FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("org.codehaus.jackson.map.annotate.JsonSerialize");
		   FullyQualifiedJavaType superClassInclusion = new FullyQualifiedJavaType("org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion");
		   topLevelClass.addImportedType(superClass);
		   topLevelClass.addImportedType(superClassInclusion);
		   topLevelClass.addAnnotation("@Entity");//liulei-2016/01/13修改
		   //topLevelClass.addAnnotation("@Table(name = \""+properties.getProperty("tableName").toUpperCase()+"\")");
		   topLevelClass.addAnnotation(annotationName);
		   System.out.println("Setting class " + topLevelClass.getType().getFullyQualifiedName() + " Annotation : " + superClass.getFullyQualifiedName());
	   }
	   return true;
	}
}
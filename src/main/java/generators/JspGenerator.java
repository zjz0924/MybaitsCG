package main.java.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;

import main.java.model.DomainFiled;
import main.java.model.JspFormattedContent;

/**
 * jsp 生成器
 * @author zhenjunzhuo
 * 2016-12-29
 */
public class JspGenerator {
	// 配置文件
	private Configuration configuration;
	
	public JspGenerator(){}
	
	public JspGenerator(Configuration configuration){
		this.configuration = configuration;
	}
	
	
	/**
	 *  生成文件
	 */
	public void generate() {
		// 配置文件中的 context内容
		List<Context> contextsList = configuration.getContexts();
		
		for(Context context: contextsList){
			// 获取 model 的配置
			JavaModelGeneratorConfiguration modelConfiguration = context.getJavaModelGeneratorConfiguration();
			// model 的绝对路径目录
			String modelDirectory = modelConfiguration.getTargetProject() + "/" + modelConfiguration.getTargetPackage().replace(".", "/");
			// jsp 的绝对路径目录
			String jspDirctory = modelDirectory.substring(0, modelDirectory.lastIndexOf("/")) + "/jsp";
			// 获取 table 的配置
			List<TableConfiguration> tableConfigurations = context.getTableConfigurations();
			
			File fDir = new File(jspDirctory);
			if (!fDir.exists()) {
				fDir.mkdirs();
			}
			
			for(TableConfiguration tableConfiguration: tableConfigurations){
				// model 的名称
				String modelName = tableConfiguration.getDomainObjectName();
				
				try {
					// model的名称
					String className = modelConfiguration.getTargetPackage() + "." + modelName;
					// model的属性
					List<DomainFiled> filedList = getFiledsInfo(className);
					
					// list页面
					File listFile = new File(jspDirctory, modelName.toLowerCase() + "_list.jsp");
					writeFile(listFile, JspFormattedContent.getListContent(modelName, filedList), "utf-8");
				
					// detail页面
					File detailFile = new File(jspDirctory, modelName.toLowerCase() + "_detail.jsp");
					writeFile(detailFile, JspFormattedContent.getDetailContent(modelName, filedList), "utf-8");
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

	/**
	 * 导出文件
	 * @param file
	 * @param content
	 * @param fileEncoding
	 * @throws IOException
	 */
	private void writeFile(File file, String content, String fileEncoding) throws IOException {
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw;
		if (fileEncoding == null) {
			osw = new OutputStreamWriter(fos);
		} else {
			osw = new OutputStreamWriter(fos, fileEncoding);
		}

		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(content);
		bw.close();
	}
	
	/**
	 * 获取model的属性
	 */
	private List<DomainFiled> getFiledsInfo(String className) {
		try{
			// 获取类名反射获取类
			Class<?> clazz = Class.forName(className);
			Object obj = clazz.newInstance();
			
			Field[] fields = obj.getClass().getDeclaredFields();
			List<DomainFiled> list = new ArrayList<DomainFiled>();
			
			for (int i = 0; i < fields.length; i++) {
				DomainFiled filed = new DomainFiled();
				
				filed.setName(fields[i].getName());
				filed.setType(fields[i].getType().toString());
				list.add(filed);
			}
			return list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}

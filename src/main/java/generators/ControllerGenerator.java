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

import main.java.model.ControllerFormattedContent;
import main.java.model.DomainFiled;

/**
 * controller ������
 * @author zhenjunzhuo
 * 2016-12-28
 */
public class ControllerGenerator {
	
	// �����ļ�
	private Configuration configuration;
	
	public ControllerGenerator(){}
	
	public ControllerGenerator(Configuration configuration){
		this.configuration = configuration;
	}
	
	
	/**
	 *  �����ļ�
	 */
	public void generate() {
		// �����ļ��е� context����
		List<Context> contextsList = configuration.getContexts();
		
		for(Context context: contextsList){
			// ��ȡ model ������
			JavaModelGeneratorConfiguration modelConfiguration = context.getJavaModelGeneratorConfiguration();
			// model �ľ���·��Ŀ¼
			String modelDirectory = modelConfiguration.getTargetProject() + "/" + modelConfiguration.getTargetPackage().replace(".", "/");
			// ��ȡ table ������
			List<TableConfiguration> tableConfigurations = context.getTableConfigurations();
			
			for(TableConfiguration tableConfiguration: tableConfigurations){
				// model ������
				String modelName = tableConfiguration.getDomainObjectName();
				
				try {
					// model������
					String className = modelConfiguration.getTargetPackage() + "." + modelName;
					// model������
					List<DomainFiled> filedList = getFiledsInfo(className);
					
					File serviceFile = new File(modelDirectory, modelName + "Controller.java");
					writeFile(serviceFile, ControllerFormattedContent.getFormattedContent(modelConfiguration.getTargetPackage(), modelName, filedList), "utf-8");
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * �����ļ�
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
	 * ��ȡmodel������
	 */
	private List<DomainFiled> getFiledsInfo(String className) {
		try{
			// ��ȡ���������ȡ��
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

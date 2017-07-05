package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import main.java.generators.ControllerGenerator;
import main.java.generators.JspGenerator;
import main.java.generators.ServiceGenerator;


/**
 * ����������
 * @author samsung
 * zhenjunzhuo
 * ע�⣺��Ҫִ�����Σ�ִ�����һ�κ�ˢ����Ŀ����ִ�еڶ���
 */
public class Main {

	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		ConfigurationParser cp = new ConfigurationParser(warnings);

		boolean overwrite = true;
		File configFile = new File("src/main/resource/generatorConfig.xml");

		try {
			System.out.println("generate start...");
			Configuration config = cp.parseConfiguration(configFile);
			
			System.out.println("---- generate dao/domain/mapper ----");
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			
			System.out.println("---- generate service ----");
			ServiceGenerator serviceGenerator = new ServiceGenerator(config);
			serviceGenerator.generate();
			
			System.out.println("---- generate controller ----");
			ControllerGenerator controllerGenerator = new ControllerGenerator(config);
			controllerGenerator.generate();
			
			System.out.println("---- generate jsp ----");
			JspGenerator jspGenerator = new JspGenerator(config);
			jspGenerator.generate();
			
			System.out.println("generate success...");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}

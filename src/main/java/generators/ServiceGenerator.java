package main.java.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;

import main.java.model.ServiceFormattedContent;

/**
 * Service 生成器
 * 
 * @author zhenjunzhuo 2016-12-27
 */
public class ServiceGenerator {

	// 配置文件
	private Configuration configuration;

	public ServiceGenerator() {
	}

	public ServiceGenerator(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 生成文件
	 */
	public void generate() {
		// 配置文件中的 context内容
		List<Context> contextsList = configuration.getContexts();

		for (Context context : contextsList) {
			// 获取 model 的配置
			JavaModelGeneratorConfiguration modelConfiguration = context.getJavaModelGeneratorConfiguration();
			// 获取 dao 的配置
			JavaClientGeneratorConfiguration daoConfiguration = context.getJavaClientGeneratorConfiguration();
			// model 的绝对路径目录
			String modelDirectory = modelConfiguration.getTargetProject() + "/"
					+ modelConfiguration.getTargetPackage().replace(".", "/");
			// service 的绝对路径目录
			String serviceDirctory = modelDirectory.substring(0, modelDirectory.lastIndexOf("/")) + "/service";
			String serviceImplDirctory = modelDirectory.substring(0, modelDirectory.lastIndexOf("/")) + "/service/impl";

			File fDir = new File(serviceDirctory);
			File fiDir = new File(serviceImplDirctory);
			if (!fDir.exists()) {
				fDir.mkdirs();
			}
			if (!fiDir.exists()) {
				fiDir.mkdirs();
			}

			// 获取 table 的配置
			List<TableConfiguration> tableConfigurations = context.getTableConfigurations();

			for (TableConfiguration tableConfiguration : tableConfigurations) {
				// model 的名称
				String modelName = tableConfiguration.getDomainObjectName();

				try {
					// 生成 service 文件
					File serviceFile = new File(serviceDirctory, modelName + "Service.java");
					writeFile(serviceFile, ServiceFormattedContent
							.getInterfaceFormattedContent(modelConfiguration.getTargetPackage(), modelName), "utf-8");

					// 生成 serviceImpl 文件
					File serviceImplFile = new File(serviceImplDirctory, modelName + "ServiceImpl.java");
					writeFile(serviceImplFile,
							ServiceFormattedContent.getImplFormattedContent(modelConfiguration.getTargetPackage(),
									modelName, daoConfiguration.getTargetPackage()),
							"utf-8");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 导出文件
	 * 
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
}

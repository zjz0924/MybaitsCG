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
 * Service ������
 * 
 * @author zhenjunzhuo 2016-12-27
 */
public class ServiceGenerator {

	// �����ļ�
	private Configuration configuration;

	public ServiceGenerator() {
	}

	public ServiceGenerator(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * �����ļ�
	 */
	public void generate() {
		// �����ļ��е� context����
		List<Context> contextsList = configuration.getContexts();

		for (Context context : contextsList) {
			// ��ȡ model ������
			JavaModelGeneratorConfiguration modelConfiguration = context.getJavaModelGeneratorConfiguration();
			// ��ȡ dao ������
			JavaClientGeneratorConfiguration daoConfiguration = context.getJavaClientGeneratorConfiguration();
			// model �ľ���·��Ŀ¼
			String modelDirectory = modelConfiguration.getTargetProject() + "/"
					+ modelConfiguration.getTargetPackage().replace(".", "/");
			// service �ľ���·��Ŀ¼
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

			// ��ȡ table ������
			List<TableConfiguration> tableConfigurations = context.getTableConfigurations();

			for (TableConfiguration tableConfiguration : tableConfigurations) {
				// model ������
				String modelName = tableConfiguration.getDomainObjectName();

				try {
					// ���� service �ļ�
					File serviceFile = new File(serviceDirctory, modelName + "Service.java");
					writeFile(serviceFile, ServiceFormattedContent
							.getInterfaceFormattedContent(modelConfiguration.getTargetPackage(), modelName), "utf-8");

					// ���� serviceImpl �ļ�
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
	 * �����ļ�
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

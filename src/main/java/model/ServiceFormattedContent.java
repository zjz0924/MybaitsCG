package main.java.model;

/**
 * service 模板
 * @author zhenjunzhuo
 * 2016-12-27
 */
public class ServiceFormattedContent {
	
	/**
	 * 获取 Service 接口的模板内容
	 * @param modelPackagePath:  model 的package 路径 
	 * @param model的名称
	 */
	public static String getInterfaceFormattedContent(String modelPackagePath, String modelName){
		StringBuffer sb = new StringBuffer();
		
		// service 的 package 路径， 在model的打包路径前
		String servicePath = modelPackagePath.substring(0, modelPackagePath.lastIndexOf("."));
		// 变量名称（第一个字母小写）
		String varName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
		
		sb.append("package "+ servicePath +".service;\n");
		sb.append("\n");
		sb.append("import java.util.List;\n");
		sb.append("import java.util.Map;\n");
		sb.append("import "+ modelPackagePath + "." + modelName + ";\n");
		sb.append("\n");
		sb.append("public interface "+ modelName +"Service {\n");
		sb.append("    public " + modelName + " selectOne(Long id);\n\n");
		sb.append("    public int save(" + modelName + " " + varName + ");\n\n");
		sb.append("    public int update(" + modelName + " " + varName + ");\n\n");
		sb.append("    public int deleteByPrimaryKey(Long id);\n\n");
		sb.append("    public List<" + modelName + "> selectAllList(Map<String, Object> map);\n\n");
		sb.append("}\n");
		
		return sb.toString();
	}
	
	/**
	 * 获取 Service 实现类的模板内容
	 * @param modelPackagePath:  model的package路径 
	 * @param model的名称
	 * @param daoPackagePath: dao的package路径
	 */
	public static String getImplFormattedContent(String modelPackagePath, String modelName, String daoPackagePath){
		StringBuffer sb = new StringBuffer();
		
		// service 的 package 路径， 在model的打包路径前
		String servicePath = modelPackagePath.substring(0, modelPackagePath.lastIndexOf("."));
		// 变量名称（第一个字母小写）
		String varName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
		// Dao名称
		String daoName = modelName + "Dao";
		// Dao变量
		String daoVarName = varName + "Dao";
		
		sb.append("package "+ servicePath +".service.impl;\n");
		sb.append("\n");
		sb.append("import java.util.List;\n");
		sb.append("import java.util.Map;\n");
		sb.append("import org.slf4j.Logger;\n");
		sb.append("import org.slf4j.LoggerFactory;\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;\n");
		sb.append("import cn.wow.common.utils.pagination.PageHelperExt;\n");
		sb.append("import "+ daoPackagePath + "." + modelName + "Dao;\n");
		sb.append("import "+ modelPackagePath + "." + modelName + ";\n");
		sb.append("import "+ servicePath + ".service." + modelName + "Service;\n\n");
		sb.append("@Service\n");
		sb.append("@Transactional\n");
		sb.append("public class "+ modelName +"ServiceImpl implements "+ modelName +"Service{\n");
		sb.append("\n");
		sb.append("    private static Logger logger = LoggerFactory.getLogger("+ modelName +"ServiceImpl.class);\n");
		sb.append("\n");
		sb.append("    @Autowired\n");
		sb.append("    private "+ daoName + " " + daoVarName +";\n\n");
		sb.append("    public " + modelName + " selectOne(Long id){\n");
		sb.append("    	return "+ daoVarName + ".selectOne(id);\n");
		sb.append("    }\n\n");
		sb.append("    public int save(" + modelName + " " + varName + "){\n");
		sb.append("    	return "+ daoVarName + ".insert("+ varName +");\n");
		sb.append("    }\n\n");
		sb.append("    public int update(" + modelName + " " + varName + "){\n");
		sb.append("    	return "+ daoVarName + ".update("+ varName +");\n");
		sb.append("    }\n\n");
		sb.append("    public int deleteByPrimaryKey(Long id){\n");
		sb.append("    	return "+ daoVarName + ".deleteByPrimaryKey(id);\n");
		sb.append("    }\n\n");
		sb.append("    public List<" + modelName + "> selectAllList(Map<String, Object> map){\n");
		sb.append("    	PageHelperExt.startPage(map);\n");
		sb.append("    	return "+ daoVarName + ".selectAllList(map);\n");
		sb.append("    }\n\n");
		sb.append("}\n");
		
		return sb.toString();
	}
}

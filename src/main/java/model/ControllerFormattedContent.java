package main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * controller 模板
 * @author zhenjunzhuo
 * 2016-12-28
 */
public class ControllerFormattedContent {
	
	/**
	 * 获取 controller的模板内容
	 * @param modelPackagePath:  model 的package 路径 
	 * @param model的名称
	 */
	public static String getFormattedContent(String modelPackagePath, String modelName, List<DomainFiled> filedList){
		StringBuffer sb = new StringBuffer();
		
		// service 的 package 路径， 在model的打包路径前
		String servicePath = modelPackagePath.substring(0, modelPackagePath.lastIndexOf("."));
		// 变量名称（第一个字母小写）
		String varName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
		// service 变量名称
		String serviceVarName = varName + "Service";
		// model的属性
		String fileds = "";
		
		List<String> typeList = new ArrayList<String>();
		typeList.add("java.util.Map");
		typeList.add("java.util.List");
		
		for(DomainFiled filed: filedList){
			if("id".equals(filed.getName()) && "class java.lang.Long".equals(filed.getType())){
				fileds += " String" + " " + filed.getName() + ","; 
			}else{
				fileds += " " + filed.getType().substring(filed.getType().lastIndexOf(".") + 1)  + " " + filed.getName() + ","; 
			}
			
			if(!typeList.contains(filed.getType().substring(filed.getType().indexOf(" ")))){
				typeList.add(filed.getType().substring(filed.getType().indexOf(" ")));
			}
		}
		fileds = fileds.substring(0, fileds.length() - 1);
		
		sb.append("package cn.wow.support.web;\n\n");
		sb.append("import java.util.HashMap;\n");
		for(String type: typeList){
			sb.append("import " + type.trim() + ";\n");
		}
		sb.append("import javax.servlet.http.HttpServletRequest;\n");
		sb.append("import org.slf4j.Logger;\n");
		sb.append("import org.slf4j.LoggerFactory;\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Controller;\n");
		sb.append("import org.springframework.ui.Model;\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
		sb.append("import org.apache.commons.lang3.StringUtils;\n");
		sb.append("import cn.wow.common.utils.AjaxVO;\n");
		sb.append("import cn.wow.common.utils.pagination.PageMap;\n");
		sb.append("import cn.wow.support.utils.Contants;\n");
		sb.append("import "+ modelPackagePath + "." + modelName + ";\n");
		sb.append("import "+ servicePath + ".service." + modelName + "Service;\n\n");
		sb.append("@Controller\n");
		sb.append("@RequestMapping(value = \""+ varName + "Controller\")\n");
		sb.append("public class "+ modelName +"Controller{\n\n");
		sb.append("    private static Logger logger = LoggerFactory.getLogger("+ modelName +"Controller.class);\n\n");
		sb.append("    @Autowired\n");
		sb.append("    private "+ modelName + "Service " + serviceVarName +";\n");
		sb.append("\n");
		sb.append("    @RequestMapping(value = \"/list\")\n");
		sb.append("    public String list(HttpServletRequest httpServletRequest, Model model) {\n");
		sb.append("        Map<String, Object> map = new PageMap(httpServletRequest);\n\n");
		sb.append("        List<"+ modelName +"> dataList = "+ serviceVarName +".selectAllList(map);\n\n");
		sb.append("        model.addAttribute(\"dataList\", dataList);\n");
		sb.append("        return \""+ modelName.toLowerCase() + "_list\";\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @RequestMapping(value = \"/detail\")\n");
		sb.append("    public String detail(HttpServletRequest request, Model model, String id){\n");
		sb.append("        if(StringUtils.isNotBlank(id)){\n");
		sb.append("            "+ modelName + " " + varName +" = "+ serviceVarName +".selectOne(Long.parseLong(id));\n");
		sb.append("            model.addAttribute(\"facadeBean\", "+ varName +");\n");
		sb.append("        }\n\n");
		sb.append("        model.addAttribute(\"mode\", request.getParameter(\"mode\"));\n");
		sb.append("        return \""+ modelName.toLowerCase() +"_detail\";\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @RequestMapping(value = \"/save\")\n");
		sb.append("    public String save(HttpServletRequest request, Model model,"+ fileds +"){\n");
		sb.append("        String resultCode = \"\";\n");
		sb.append("        String resultMsg = \"\";\n");
		sb.append("        "+ modelName + " " + varName + " = null;\n\n");
		sb.append("        try{\n");
		sb.append("            if(StringUtils.isNotBlank(id)){\n");
		sb.append("                "+ varName +" = " + serviceVarName + ".selectOne(Long.parseLong(id));\n");
		for(DomainFiled filed: filedList){
			if(!"id".equals(filed.getName())){
				sb.append("                "+ varName +".set" + filed.getName().substring(0,1).toUpperCase() + filed.getName().substring(1) + "(" + filed.getName() + ");\n");
			}
		}
		sb.append("                " + serviceVarName + ".update(" + varName + ");\n\n");
		sb.append("                resultCode = Contants.EDIT_SUCCESS;\n");
		sb.append("                resultMsg = Contants.EDIT_SUCCESS_MSG;\n");
		sb.append("            }else{\n");
		sb.append("                "+ varName +" = new "+ modelName +"();\n");
		for(DomainFiled filed: filedList){
			if(!"id".equals(filed.getName())){
				sb.append("                "+ varName +".set" + filed.getName().substring(0,1).toUpperCase() + filed.getName().substring(1) + "(" + filed.getName() + ");\n");
			}
		}
		sb.append("                " + serviceVarName + ".save(" + varName + ");\n\n");
		sb.append("                resultCode = Contants.SAVE_SUCCESS;\n");
		sb.append("                resultMsg = Contants.SAVE_SUCCESS_MSG;\n");
		sb.append("            }\n");
		sb.append("        }catch(Exception ex){\n");
		sb.append("            ex.printStackTrace();\n");
		sb.append("            resultCode = Contants.EXCEPTION;\n");
		sb.append("            resultMsg = Contants.EXCEPTION_MSG;\n");
		sb.append("        }\n\n");
		sb.append("        model.addAttribute(\"resultCode\",  resultCode);\n");
		sb.append("        model.addAttribute(\"resultMsg\", resultMsg);\n");
		sb.append("        model.addAttribute(\"facadeBean\", "+ varName +");\n");
		sb.append("        return \""+ modelName.toLowerCase() +"_detail\";\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @ResponseBody\n");
		sb.append("    @RequestMapping(value = \"/delete\")\n");
		sb.append("    public AjaxVO delete(HttpServletRequest request, String id){\n");
		sb.append("        AjaxVO vo = new AjaxVO();\n\n");
		sb.append("        if(StringUtils.isNotBlank(id)){\n");
		sb.append("            int num = "+ serviceVarName +".deleteByPrimaryKey(Long.parseLong(id));\n\n");
		sb.append("            if(num > 0){\n");
		sb.append("                getResponse(vo, Contants.SUC_DELETE);\n");
		sb.append("            }else{\n");
		sb.append("                getResponse(vo, Contants.FAIL_DELETE);\n");
		sb.append("            }\n");
		sb.append("        }else{\n");
		sb.append("            getResponse(vo, Contants.FAIL_DELETE);\n");
		sb.append("        }\n\n");
		sb.append("        return vo;\n");
		sb.append("    }\n");
		sb.append("}");
		
		return sb.toString();
	}
	
}

package main.java.model;

import java.util.List;


/**
 * jsp模板内容
 * @author zhenjunzhuo
 * 2017-07-05
 */
public class JspFormattedContent {

	/**
	 * 获取列表模板内容
	 * @param modelPackagePath
	 * @param modelName
	 * @param filedList
	 * @return
	 */
	public static String getListContent(String modelName, List<DomainFiled> filedList){
		StringBuffer sb = new StringBuffer();
		
		// 变量名称（第一个字母小写）
		String varName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
		
		sb.append("<%@ page contentType=\"text/html;charset=utf-8\" pageEncoding=\"utf-8\"%>\n");
		sb.append("<%@include file=\"/page/taglibs.jsp\"%>\n");
		sb.append("<%@include file=\"/page/NavPageBar.jsp\"%>\n\n");
		sb.append("<!DOCTYPE html>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("    <meta charset=\"utf-8\">\n");
		sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n\n");
		sb.append("    <link href=\"${ctx}/resources/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/font-awesome.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/style.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/add-ons.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <script src=\"${ctx}/resources/js/jquery-2.1.1.min.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/artDialog.source.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/jquery.artDialog.source.js?skin=idialog\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/plugins/iframeTools.source.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/My97DatePicker/WdatePicker.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/tools.js\"></script>\n");
		sb.append("</head>\n\n");
		sb.append("<body>\n");
		sb.append("    <div class=\"row\">\n");
		sb.append("        <div class=\"col-lg-12\">\n");
		sb.append("            <ol class=\"breadcrumb\">\n");
		sb.append("                <li><i class=\"fa fa-home\"></i><a href=\"index.html\">首页</a></li>\n");
		sb.append("                <li><i class=\"fa fa-user\"></i>^管理</li>\n");
		sb.append("            </ol>\n");
		sb.append("        </div>\n");
		sb.append("    </div>\n\n");
		sb.append("    <div class=\"row\">\n");
		sb.append("        <div class=\"col-lg-12\">\n");
		sb.append("            <div class=\"panel panel-default\">\n");
		sb.append("                <div class=\"panel-heading\">\n");
		sb.append("                    <h2><i class=\"fa fa-user red\"></i><span class=\"break\"></span><strong>^管理</strong></h2>\n\n");
		sb.append("                    <span style=\"float:right;padding-top:5px;\">\n");
		sb.append("                        <button type=\"button\" class=\"btn btn-primary btn-xs\" onclick=\"goTo('detail')\">添加</button>\n");
		sb.append("                    </span>\n");
		sb.append("                </div>\n\n");
		sb.append("                <div class=\"panel-body\">\n");
		sb.append("                    <form id=\"queryForm\" name=\"queryForm\" action=\"${ctx}/" + varName + "Controller/list\" method=\"post\">\n");
		sb.append("                        <table class=\"queryTable\">\n\n\n");
		sb.append("                        </table>\n\n");
		sb.append("                        <div style=\"padding-top: 15px;\">\n");
		sb.append("                            <table class=\"table table-bordered table-striped table-condensed table-hover\">\n");
		sb.append("                                <thead>\n");
		sb.append("                                    <tr class=\"text_size_14\">\n");
		sb.append("                                        <th>序号</th>\n");
		for(DomainFiled filed: filedList){
			if(!"id".equals(filed.getName())){
				sb.append("                                        <th>"+ filed.getName() +"</th>\n");
			}
		}
		sb.append("                                    </tr>\n");
		sb.append("                                </thead>\n\n");
		sb.append("                                <tbody>\n");
		sb.append("                                    <c:forEach items=\"${dataList}\" var=\"vo\" varStatus=\"vst\">\n");
		sb.append("                                        <tr>\n");
		sb.append("                                            <td class=\"text-center text_size_14\" style=\"width:8%;\">${vst.index + 1}</td>\n");
		for(DomainFiled filed: filedList){
			if(!"id".equals(filed.getName())){
				if(filed.getType().indexOf("Date") == -1){
					if(filed.getName().startsWith("is")){
						sb.append("                                            <td class=\"text-center text_size_14\" style=\"width:10%;\">\n");
						sb.append("                                                <c:choose>\n");
						sb.append("                                                    <c:when test=\"${vo."+ filed.getName() +" == ''}\">\n");
						sb.append("                                                        <span class=\"label label-success\"></span>\n");
						sb.append("                                                    </c:when>\n");
						sb.append("                                                    <c:otherwise>\n");
						sb.append("                                                        <span class=\"label label-danger\"></span>\n");
						sb.append("                                                    </c:otherwise>\n");
						sb.append("                                                </c:choose>\n");
						sb.append("                                            </td>\n");
					}else{
						sb.append("                                            <td class=\"text-center text_size_14\" style=\"width:10%;\">${vo."+ filed.getName() +"}</td>\n");
					}
				}else{
					sb.append("                                            <td class=\"text-center text_size_14\" style=\"width:10%;\"><fmt:formatDate value='${vo."+ filed.getName() +"}' type=\"date\" pattern=\"yyyy-MM-dd hh:mm:ss\" /></td>\n");
				}
			}
		}
		sb.append("                                            <td style=\"width:16%;\">\n");
		sb.append("                                                <button type=\"button\" class=\"btn btn-default btn_padding\" onclick=\"goTo('detail?id=${vo.id}&mode=readonly')\">查看</button>\n");
		sb.append("                                                <button type=\"button\" class=\"btn btn-success btn_padding\" onclick=\"goTo('detail?id=${vo.id}')\">编辑</button>\n");
		sb.append("                                                <button type=\"button\" class=\"btn btn-danger btn_padding\" onclick=\"confirm('是否确定删除该记录？','delete?id=${vo.id}')\">删除</button>\n");
		sb.append("                                            </td>\n");
		sb.append("                                        </tr>\n");
		sb.append("                                    </c:forEach>\n");
		sb.append("                                </tbody>\n");
		sb.append("                            </table>\n");
		sb.append("                        </div>\n\n");
		sb.append("                        <c:if test=\"${not empty dataList}\">\n");
		sb.append("                            <pagination:pagebar startRow=\"${dataList.getStartRow()}\" id=\"queryForm\" pageSize=\"${dataList.getPageSize()}\" totalSize=\"${dataList.getTotal()}\" showbar=\"true\" showdetail=\"true\" />\n");
		sb.append("                        </c:if>\n");
		sb.append("                    </form>\n");
		sb.append("                </div>\n");
		sb.append("            </div>\n");
		sb.append("        </div>\n");
		sb.append("    </div>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");
		
		return sb.toString();
	}
	
	/**
	 * 获取详情页面模板内容
	 * @param modelPackagePath
	 * @param modelName
	 * @param filedList
	 * @return
	 */
	public static String getDetailContent(String modelName, List<DomainFiled> filedList){
		StringBuffer sb = new StringBuffer();
		
		// 变量名称（第一个字母小写）
		String varName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);
		
		sb.append("<%@ page contentType=\"text/html;charset=utf-8\" pageEncoding=\"utf-8\"%>\n");
		sb.append("<%@include file=\"/page/taglibs.jsp\"%>\n");
		sb.append("<%@include file=\"/page/NavPageBar.jsp\"%>\n\n");
		sb.append("<!DOCTYPE html>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("    <meta charset=\"utf-8\">\n");
		sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n\n");
		sb.append("    <link href=\"${ctx}/resources/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/font-awesome.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/style.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <link href=\"${ctx}/resources/css/add-ons.min.css\" rel=\"stylesheet\">\n");
		sb.append("    <script src=\"${ctx}/resources/js/jquery-2.1.1.min.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/artDialog.source.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/jquery.artDialog.source.js?skin=idialog\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/artDialog4.1.2/plugins/iframeTools.source.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/My97DatePicker/WdatePicker.js\"></script>\n");
		sb.append("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/tools.js\"></script>\n\n");
		sb.append("    <script type=\"text/javascript\">\n");
		sb.append("        $(function(){\n");
		sb.append("            var resultCode = \"${resultCode}\";\n");
		sb.append("            if(resultCode != null && resultCode != '' && resultCode != undefined){\n");
		sb.append("                var resultMsg = \"${resultMsg}\";\n");
		sb.append("                // 设置按钮不可点\n");
		sb.append("                $(\"#saveBtn\").attr(\"class\", \"btn btn-primary disabled\");\n\n");
		sb.append("                if(resultCode == \"01\" || resultCode == \"03\"){ \n");
		sb.append("                    window.location.href = \"${ctx}/"+ varName +"Controller/list\";\n");
		sb.append("                }else{\n");
		sb.append("                    // 设置按钮可点\n");
		sb.append("                    $(\"#saveBtn\").attr(\"class\", \"btn btn-primary\");\n");
		sb.append("                }\n");
		sb.append("            }\n\n");
		sb.append("            var mode = \"${mode}\";\n");
		sb.append("            if(mode == \"readonly\"){\n");
		sb.append("                $(\":input\").attr(\"disabled\",\"true\");\n");
		sb.append("            }\n\n");
		sb.append("            //自适应高度\n");
		sb.append("            window.parent.adapter(document.body.scrollHeight + 10);\n");
		sb.append("        });\n\n");
		sb.append("        function checkData(){\n\n\n");
		sb.append("        }\n");
		sb.append("    </script>\n");
		sb.append("</head>\n\n");
		sb.append("<body>\n");
		sb.append("    <div class=\"row\">\n");
		sb.append("        <div class=\"col-lg-12\">\n");
		sb.append("            <ol class=\"breadcrumb\">\n");
		sb.append("                <li><i class=\"fa fa-home\"></i><a href=\"index.html\">首页</a></li>\n");
		sb.append("                <li><i class=\"fa fa-user\"></i><a href=\"${ctx}/"+ varName +"Controller/list\">^管理</a></li>\n");
		sb.append("                <li><i class=\"fa fa-user\"></i>^信息</li>\n");
		sb.append("            </ol>\n");
		sb.append("        </div>\n");
		sb.append("    </div>\n\n");
		sb.append("    <div class=\"row\">\n");
		sb.append("        <div class=\"col-lg-12\">\n");
		sb.append("            <div class=\"panel panel-default\">\n");
		sb.append("                <div class=\"panel-heading\">\n");
		sb.append("                    <h2><i class=\"fa fa-user red\"></i><span class=\"break\"></span><strong>^信息</strong></h2>\n\n");
		sb.append("                </div>\n\n");
		sb.append("                <div class=\"panel-body\" style=\"padding-top:30px;padding-left:30px;\">\n");
		sb.append("                    <form action=\"${ctx}/" + varName + "Controller/save\" method=\"post\" onsubmit=\"return checkData();\">\n");
		sb.append("                        <input type=\"hidden\" id=\"id\" name=\"id\" value=\"${facadeBean.id}\"/>\n");
		for(DomainFiled filed: filedList){
			if(!"id".equals(filed.getName())){
				sb.append("                        <div class=\"form-group height_30\">\n");
				sb.append("                            <label class=\"col-md-2 control-label\">"+ filed.getName() +"</label>\n");
				sb.append("                            <div class=\"col-md-3\">\n");
				if(filed.getType().indexOf("Date") == -1){
					if(filed.getName().startsWith("is")){
						sb.append("                                <c:choose>\n");
						sb.append("                                    <c:when test=\"${vo."+ filed.getName() +" == ''}\">\n");
						sb.append("                                        <span class=\"label label-success\"></span>\n");
						sb.append("                                    </c:when>\n");
						sb.append("                                    <c:otherwise>\n");
						sb.append("                                        <span class=\"label label-danger\"></span>\n");
						sb.append("                                    </c:otherwise>\n");
						sb.append("                                </c:choose>\n");
					}else{
						sb.append("                                <input type=\"text\" id=\""+ filed.getName() + "\" name=\""+ filed.getName() + "\" class=\"form-control\" value=\"${facadeBean."+ filed.getName() +"}\">\n");
					}
				}else{
					sb.append("                                <p class=\"form-control-static\">\n");
					sb.append("                                    <fmt:formatDate value='${facadeBean."+ filed.getName() +"}' type=\"date\" pattern=\"yyyy-MM-dd hh:mm:ss\" />\n");
					sb.append("                                </p>\n");
				}
				sb.append("                            </div>\n");
				sb.append("                        </div>\n\n");
			}
		}
		sb.append("                        <c:if test=\"${mode != 'readonly'}\">\n");
		sb.append("                            <div class=\"form-group height_30 text-center\">\n");
		sb.append("                                <button id=\"saveBtn\" type=\"submit\" class=\"btn btn-primary\">保存</button>\n");
		sb.append("                                <button type=\"button\" class=\"btn btn-danger\" onclick=\"window.location.reload();\">取消</button>\n");
		sb.append("                            </div>\n");
		sb.append("                        </c:if>\n");
		sb.append("                    </form>\n");
		sb.append("                </div>\n");
		sb.append("            </div>\n");
		sb.append("        </div>\n");
		sb.append("    </div>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");
		
		return sb.toString();
	}
}

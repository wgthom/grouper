<%-- @annotation@ 
			Displays link to show naming privileges for given subject on given group
--%><%--
  @author Gary Brown.
  @version $Id: namingPrivilegesLinkView.jsp,v 1.2 2008-03-25 14:59:51 mchyzer Exp $
--%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<tiles:importAttribute ignore="true"/>
<c:set var="linkTitle"><grouper:message bundle="${nav}" key="browse.assign.title">
	<grouper:param value="${viewObject.subject.desc}"/>
	<grouper:param value="${browseParent.desc}"/>
</grouper:message></c:set>
<c:set var="linkText" value="stems.privilege.direct"/>
<c:if test="${!viewObject.isDirect}"><c:set var="linkText" value="stems.privilege.indirect"/></c:if>		

<html:link page="/populateStemMember.do" name="params" 
	title="${linkTitle}"><grouper:message bundle="${nav}" key="${linkText}"/></html:link> 
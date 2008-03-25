<%-- @annotation@
		  Dynamic tile used by genericList mechanism to 
		  render individual Access privileges for current subject
--%><%--
  @author Gary Brown.
  @version $Id: subjectAccessPrivView.jsp,v 1.4 2008-03-25 14:59:51 mchyzer Exp $
--%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<tiles:importAttribute ignore="true"/>
<div class="subjectAccessPriv">
<%-- Set up parameters for link to maintain context --%>
<c:set target="${pagerParams}" property="groupId" value="${viewObject.asMemberOf}"/>
<c:set target="${pagerParams}" property="contextGroup" value="${viewObject.asMemberOf}"/>
<c:set target="${pagerParams}" property="asMemberOf" value="${viewObject.asMemberOf}"/>
<c:set target="${pagerParams}" property="subjectId" value="${viewObject.id}"/>
<c:set target="${pagerParams}" property="subjectType" value="${viewObject.subjectType}"/>
<c:set target="${pagerParams}" property="contextSubject" value="true"/>
<c:set target="${pagerParams}" property="contextSubjectId" value="${viewObject.id}"/>
<c:set target="${pagerParams}" property="contextSubjectType" value="${viewObject.subjectType}"/> 
  
 <%--  Use params to make link title descriptive for accessibility --%>		
<c:set var="linkTitle"><grouper:message bundle="${nav}" key="groups.access.chain.title">
		 		<grouper:param value="${SubjectFormBean.map.accessPriv}"/>
				<grouper:param value="${viewObject.desc}"/>
				<grouper:param value="${viewObject.memberOfGroup.desc}"/>
</grouper:message></c:set>
<span class="groupMemberLink">		
		<html:link page="/populateGroupMember.do" name="pagerParams" title="${linkTitle}">
		 	<grouper:message bundle="${nav}" key="groups.privilege.has-for">
		 	<grouper:param value="${SubjectFormBean.map.accessPriv}"/>
			</grouper:message>
		</html:link></span> <c:out value="${linkSeparator}" escapeXml="false"/>
		
	
<c:set var="group" value="${viewObject.memberOfGroup}"/>
<c:set target="${group}" property="contextSubject" value="true"/>
<c:set target="${group}" property="contextSubjectId" value="${currentSubject.id}"/>
<c:set target="${group}" property="contextSubjectType" value="${currentSubject.subjectType}"/>
 <span class="groupSummaryLink">
  <tiles:insert definition="dynamicTileDef" flush="false">
	  <tiles:put name="viewObject" beanName="group"/>
	  <tiles:put name="view" value="subjectSummaryGroupLink"/>
  </tiles:insert>
  </span> 
</div>
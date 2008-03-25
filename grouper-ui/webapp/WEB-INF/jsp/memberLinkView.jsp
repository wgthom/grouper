<%-- @annotation@
		  Dynamic tile used by genericList mechanism to 
		  render individual group members
--%><%--
  @author Gary Brown.
  @version $Id: memberLinkView.jsp,v 1.8 2008-03-25 14:59:51 mchyzer Exp $
--%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<tiles:importAttribute ignore="true"/>

<c:set var="linkTitle"><grouper:message bundle="${nav}" key="browse.assign.title">
				<grouper:param value="${viewObject.subject.desc}"/>
				<grouper:param value="${viewObject.group.desc}"/>
</grouper:message></c:set>
  <span class="memberLink">

   <c:choose>
		<c:when test="${viewObject.noWays gt 1}">
		<html:link page="/populateChains.do" name="linkParams" title="${linkTitle}">
		 <grouper:message bundle="${nav}" key="groups.membership.chain.multiple">
		 	<grouper:param value="${viewObject.noWays}"/>
		 </grouper:message></html:link> 
		
	</c:when>
  	<c:when test="${!empty viewObject.viaGroup || isCompositeGroup}">
		<html:link page="/populateChains.do" name="linkParams" title="${linkTitle}">
		 <grouper:message bundle="${nav}" key="groups.membership.chain.indirect-member"/></html:link> 
	</c:when>
	<c:otherwise>
		<html:link page="/populateGroupMember.do" name="linkParams" title="${linkTitle}">
 		<grouper:message bundle="${nav}" key="groups.membership.chain.member"/></html:link> 
	</c:otherwise>
  </c:choose>	

</span>

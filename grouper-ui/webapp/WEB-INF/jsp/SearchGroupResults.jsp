<%-- @annotation@
		  Displays search results when searching for groups in all modes 
		  except 'Find' mode 
--%><%--
  @author Gary Brown.
  @version $Id: SearchGroupResults.jsp,v 1.5 2008-03-25 14:59:51 mchyzer Exp $
--%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<c:if test="${!empty subjectOfInterest}">
	<div class="groupSearchSubject"><grouper:message bundle="${nav}" key="subject.action.search-groups.info"/> 
	<tiles:insert definition="dynamicTileDef" flush="false">
	  			<tiles:put name="viewObject" beanName="subjectOfInterest"/>
	  			<tiles:put name="view" value="groupSearchForPrivileges"/>
  			</tiles:insert></div>
</c:if>
<c:set var="termMode" value="query"/>
<c:set var="termText" value=""/>
<c:forEach var="outTerm" items="${queryOutTerms}">
	<c:choose>
		<c:when test="${termMode == 'query'}">
			<c:set var="termText"><c:out value="${termText}" escapeXml="false"/> <span class="termQuery"><c:out value="${outTerm}" escapeXml="false"/></span></c:set>
			<c:set var="termMode" value="field"/>
		</c:when>
		<c:when test="${termMode == 'field'}">
			<c:set var="termText"><c:out value="${termText}" escapeXml="false"/> 
			<span class="termIn"><grouper:message bundle="${nav}" key="find.results.search-in"/></span> 
				<span class="termField"><c:out value="${outTerm}" escapeXml="false"/></span></c:set>
			<c:set var="termMode" value="andOrNot"/>
		</c:when>
		<c:otherwise>
			<c:set var="termAndOrNotKey" value="find.search.${outTerm}"/>
			<c:set var="termText"><c:out value="${termText}" escapeXml="false"/> 
				<span class="termAndOrNot"><grouper:message bundle="${nav}" key="${termAndOrNotKey}"/></span></c:set>	
			<c:set var="termMode" value="query"/>
		</c:otherwise>
	</c:choose>
</c:forEach>
<div class="searchedFor"><grouper:message bundle="${nav}" key="find.groups.searched-for">
	<grouper:param value="${termText}"/>
</grouper:message></div>
<c:if test="${!empty pager.params.searchFromDisplay}">
<div class="searchedFrom"><grouper:message bundle="${nav}" key="find.groups.searched-from">
	<grouper:param value="${pager.params.searchFromDisplay}"/>
</grouper:message></div>
</c:if>
<tiles:insert definition="dynamicTileDef">
	<tiles:put name="viewObject" beanName="pager" beanProperty="collection"/>
	<tiles:put name="view" value="searchResult"/>
	<tiles:put name="headerView" value="searchResultHeader"/>
	<c:choose>
		<c:when test="${browseMode=='SubjectSearch'}">
			<tiles:put name="itemView" value="groupSearchResultWithPrivs"/>
			<tiles:put name="useTable" value="true"/>
			<tiles:put name="tableClass" value="groupSearchResultsWithPrivsTable"/>
		</c:when>
		<c:otherwise>
			<tiles:put name="itemView" value="groupSearchResultLink"/>
		</c:otherwise>
	</c:choose>
	<tiles:put name="footerView" value="searchResultFooter"/>
	<tiles:put name="pager" beanName="pager"/>
	<tiles:put name="listInstruction" value="list.instructions.search-result-group"/>
</tiles:insert>

<c:if test="${pager.count==0}">
<div class="searchCountZero"><grouper:message bundle="${nav}" key="find.groups.no-results"/></div>
</c:if>
<div class="linkButton">
<tiles:insert definition="callerPageButtonDef">
	<tiles:put name="buttonTitle"><grouper:message bundle="${nav}" key="find.results.search-again"/></tiles:put>
	<tiles:put name="buttonText"><grouper:message bundle="${nav}" key="find.results.search-again"/></tiles:put>
</tiles:insert>
<c:if test="${grouperForm.map.advSearch=='Y'}">
<c:choose>
	<c:when test="${browseMode=='SubjectSearch'}">
		<a href="populateSubjectSummary.do?advancedSearch=false"><grouper:message bundle="${nav}" key="find.action.cancel-advanced-search"/></a>
	</c:when>
	<c:otherwise>
		<a href="populate<c:out value="${browseMode}"/>Groups.do?advancedSearch=false"><grouper:message bundle="${nav}" key="find.action.cancel-advanced-search"/></a>
	</c:otherwise>
	</c:choose>
</c:if>
</div>

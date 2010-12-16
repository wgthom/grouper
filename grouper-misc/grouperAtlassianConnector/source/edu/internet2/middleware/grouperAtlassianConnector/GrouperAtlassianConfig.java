/**
 * @author mchyzer
 * $Id$
 */
package edu.internet2.middleware.grouperAtlassianConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.internet2.middleware.grouperClient.util.ExpirableCache;
import edu.internet2.middleware.grouperClient.util.GrouperClientUtils;


/**
 * config for grouper atlassian plugin
 */
public class GrouperAtlassianConfig {
  
  /**
   * map of sourceId to source config
   */
  private Map<String, GrouperAtlassianSourceConfig> sourceConfigs = new HashMap<String, GrouperAtlassianSourceConfig>();
  
  /**
   * map of sourceId to source config
   * @return the sourceConfigs
   */
  public Map<String, GrouperAtlassianSourceConfig> getSourceConfigs() {
    return this.sourceConfigs;
  }
  
  /**
   * map of sourceId to source config
   * @param sourceConfigs1 the sourceConfigs to set
   */
  public void setSourceConfigs(Map<String, GrouperAtlassianSourceConfig> sourceConfigs1) {
    this.sourceConfigs = sourceConfigs1;
  }

  /**
   * <pre>
   * # list all sources here, and how to get the atlassian id
   * atlassian.source.jdbc.sourceId = jdbc
   * # should be "id" or an attribute name to get the identifier for atlassian
   * atlassian.source.jdbc.idOrAttribute = loginid
   * </pre>
   */
  public static class GrouperAtlassianSourceConfig {
   
    /**
     * <pre>
     * # list all sources here, and how to get the atlassian id
     * atlassian.source.jdbc.sourceId = jdbc
     * </pre>
     */
    private String sourceId;

    /**
     * <pre>
     * # should be "id" or an attribute name to get the identifier for atlassian
     * atlassian.source.jdbc.idOrAttribute = loginid
     * </pre>
     */
    private String idOrAttribute;

    
    /**
     * <pre>
     * # list all sources here, and how to get the atlassian id
     * atlassian.source.jdbc.sourceId = jdbc
     * </pre>
     * @return the sourceId
     */
    public String getSourceId() {
      return this.sourceId;
    }
    
    /**
     * <pre>
     * # list all sources here, and how to get the atlassian id
     * atlassian.source.jdbc.sourceId = jdbc
     * </pre>
     * @param sourceId1 the sourceId to set
     */
    public void setSourceId(String sourceId1) {
      this.sourceId = sourceId1;
    }

    /**
     * <pre>
     * # should be "id" or an attribute name to get the identifier for atlassian
     * atlassian.source.jdbc.idOrAttribute = loginid
     * </pre>
     * @return the idOrAttribute
     */
    public String getIdOrAttribute() {
      return this.idOrAttribute;
    }

    /**
     * <pre>
     * # should be "id" or an attribute name to get the identifier for atlassian
     * atlassian.source.jdbc.idOrAttribute = loginid
     * </pre>
     * @param idOrAttribute1 the idOrAttribute to set
     */
    public void setIdOrAttribute(String idOrAttribute1) {
      this.idOrAttribute = idOrAttribute1;
    }

    
    
  }
  
  /**
   * map of group names to users to autoadd
   */
  private Map<String, GrouperAtlassianAutoaddConfig> autoaddConfigGroupToUsers = new HashMap<String, GrouperAtlassianAutoaddConfig>();

  /**
   * map of users to groups to autoadd
   */
  private Map<String, List<String>> autoaddConfigUserToGroups = new HashMap<String, List<String>>();

  
  /**
   * map of group names to users to autoadd
   * @return the autoaddConfigGroupToUsers
   */
  public Map<String, GrouperAtlassianAutoaddConfig> getAutoaddConfigGroupToUsers() {
    return this.autoaddConfigGroupToUsers;
  }

  
  /**
   * map of group names to users to autoadd
   * @param autoaddConfigGroupToUsers1 the autoaddConfigGroupToUsers to set
   */
  public void setAutoaddConfigGroupToUsers(
      Map<String, GrouperAtlassianAutoaddConfig> autoaddConfigGroupToUsers1) {
    this.autoaddConfigGroupToUsers = autoaddConfigGroupToUsers1;
  }

  
  /**
   * map of users to groups to autoadd
   * @return the autoaddConfigUserToGroups
   */
  public Map<String, List<String>> getAutoaddConfigUserToGroups() {
    return this.autoaddConfigUserToGroups;
  }

  
  /**
   * map of users to groups to autoadd
   * @param autoaddConfigUserToGroups1 the autoaddConfigUserToGroups to set
   */
  public void setAutoaddConfigUserToGroups(
      Map<String, List<String>> autoaddConfigUserToGroups1) {
    this.autoaddConfigUserToGroups = autoaddConfigUserToGroups1;
  }

  /**
   * <pre>
   * # pretend these memberships exist (e.g. to bootstrap or for users not in grouper)
   * atlassian.autoadd.administrators.groupname = jira-administrators
   * atlassian.autoadd.administrators.usernames = admin
   *
   * atlassian.autoadd.users.groupname = jira-users
   * atlassian.autoadd.users.usernames = admin
   * </pre>
   */
  public static class GrouperAtlassianAutoaddConfig {
    
    /**
     * <pre>
     * group name to autoadd
     * # pretend these memberships exist (e.g. to bootstrap or for users not in grouper)
     * atlassian.autoadd.administrators.groupname = jira-administrators
     * </pre>
     */
    private String groupname;
    
    /**
     * <pre>
     * usernames to autoadd to the group
     * atlassian.autoadd.administrators.usernames = admin
     * </pre>
     */
    private List<String> usernames;

    
    /**
     * <pre>
     * group name to autoadd
     * # pretend these memberships exist (e.g. to bootstrap or for users not in grouper)
     * atlassian.autoadd.administrators.groupname = jira-administrators
     * </pre>
     * @return the groupname
     */
    public String getGroupname() {
      return this.groupname;
    }

    
    /**
     * <pre>
     * group name to autoadd
     * # pretend these memberships exist (e.g. to bootstrap or for users not in grouper)
     * atlassian.autoadd.administrators.groupname = jira-administrators
     * </pre>
     * @param groupname1 the groupname to set
     */
    public void setGroupname(String groupname1) {
      this.groupname = groupname1;
    }

    
    /**
     * <pre>
     * usernames to autoadd to the group
     * atlassian.autoadd.administrators.usernames = admin
     * </pre>
     * @return the usernames
     */
    public List<String> getUsernames() {
      return this.usernames;
    }

    
    /**
     * <pre>
     * usernames to autoadd to the group
     * atlassian.autoadd.administrators.usernames = admin
     * </pre>
     * @param usernames1 the usernames to set
     */
    public void setUsernames(List<String> usernames1) {
      this.usernames = usernames1;
    }
    
  }
  
  /**
   * <pre>
   * # put a folder name that is the root for atlassian groups
   * atlassian.root =  
   * </pre>
   */
  private String rootFolder = null;
  
  /** 
   * <pre>
   * # atlassian source to use (leave blank for all sources)
   * atlassian.subject.search.sourceId =
   * </pre>
   */ 
  private String subjectSearchSourceId;
  
  /**
   * <pre>
   * # atlassian search by id, identifier, or idOrIdentifer (idOrIdentifier is Grouper 2.0+)
   * atlassian.subject.search.subjectId = 
   * </pre>
   */
  private String subjectSearchSubjectId;
  
  /**
   * <pre>
   * # number of minutes to cache reads (-1 for none, though this isnt recommended since 
   * # atlassian makes a LOT of calls to the group service)
   * atlassian.cache.minutes = 
   * </pre>
   */
  private int cacheMinutes = -1;

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.updaters =
   * </pre>
   */ 
  private List<String> autoAddPrivilegeUpdaters = new ArrayList<String>();
  
  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.admins = 
   * </pre>
   */
  private List<String> autoAddPrivilegeAdmins = new ArrayList<String>();

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.readers = 
   * </pre>
   */
  private List<String> autoAddPrivilegeReaders = new ArrayList<String>();

  /**
   * <pre>
   * #ignore calls on this user to the web service
   * atlassian.ws.users.to.ignore = admin
   * </pre>
   */
  private List<String> wsUsersToIgnore = new ArrayList<String>();
  
  
  /**
   * <pre>
   * #ignore calls on this user to the web service
   * atlassian.ws.users.to.ignore = admin
   * </pre>
   * @return the wsUsersToIgnore
   */
  public List<String> getWsUsersToIgnore() {
    return this.wsUsersToIgnore;
  }

  /**
   * <pre>
   * #ignore calls on this user to the web service
   * atlassian.ws.users.to.ignore = admin
   * </pre>
   * @param wsUsersToIgnore1 the wsUsersToIgnore to set
   */
  public void setWsUsersToIgnore(List<String> wsUsersToIgnore1) {
    this.wsUsersToIgnore = wsUsersToIgnore1;
  }

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.updaters =
   * </pre>
   * @return the autoAddPrivilegeUpdaters
   */
  public List<String> getAutoAddPrivilegeUpdaters() {
    return this.autoAddPrivilegeUpdaters;
  }
  
  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.updaters =
   * </pre>
   * @param autoAddPrivilegeUpdaters1 the autoAddPrivilegeUpdaters to set
   */
  public void setAutoAddPrivilegeUpdaters(List<String> autoAddPrivilegeUpdaters1) {
    this.autoAddPrivilegeUpdaters = autoAddPrivilegeUpdaters1;
  }

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.admins = 
   * </pre>
   * @return the autoAddPrivilegeAdmins
   */
  public List<String> getAutoAddPrivilegeAdmins() {
    return this.autoAddPrivilegeAdmins;
  }

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.admins = 
   * </pre>
   * @param autoAddPrivilegeAdmins1 the autoAddPrivilegeAdmins to set
   */
  public void setAutoAddPrivilegeAdmins(List<String> autoAddPrivilegeAdmins1) {
    this.autoAddPrivilegeAdmins = autoAddPrivilegeAdmins1;
  }

  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.readers = 
   * </pre>
   * @return the autoAddPrivilegeReaders
   */
  public List<String> getAutoAddPrivilegeReaders() {
    return this.autoAddPrivilegeReaders;
  }
  
  /**
   * <pre>
   * # groups which should be assigned to various privileges for new groups created in confluence
   * atlassian.readers = 
   * </pre>
   * @param autoAddPrivilegeReaders1 the autoAddPrivilegeReaders to set
   */
  public void setAutoAddPrivilegeReaders(List<String> autoAddPrivilegeReaders1) {
    this.autoAddPrivilegeReaders = autoAddPrivilegeReaders1;
  }

  /**
   * <pre>
   * # number of minutes to cache reads (-1 for none, though this isnt recommended since 
   * # atlassian makes a LOT of calls to the group service)
   * atlassian.cache.minutes = 
   * </pre>
   * @return the cacheMinutes
   */
  public int getCacheMinutes() {
    return this.cacheMinutes;
  }
  
  /**
   * <pre>
   * # number of minutes to cache reads (-1 for none, though this isnt recommended since 
   * # atlassian makes a LOT of calls to the group service)
   * atlassian.cache.minutes = 
   * </pre>
   * @param cacheMinutes1 the cacheMinutes to set
   */
  public void setCacheMinutes(int cacheMinutes1) {
    this.cacheMinutes = cacheMinutes1;
  }

  /**
   * <pre>
   * # atlassian search by id, identifier, or idOrIdentifer (idOrIdentifier is Grouper 2.0+)
   * atlassian.subject.search.subjectId = 
   * </pre>
   * @return the subjectSearchSubjectId
   */
  public String getSubjectSearchSubjectId() {
    return this.subjectSearchSubjectId;
  }



  
  /**
   * <pre>
   * # atlassian search by id, identifier, or idOrIdentifer (idOrIdentifier is Grouper 2.0+)
   * atlassian.subject.search.subjectId = 
   * </pre>
   * @param subjectSearchSubjectId1 the subjectSearchSubjectId to set
   */
  public void setSubjectSearchSubjectId(String subjectSearchSubjectId1) {
    this.subjectSearchSubjectId = subjectSearchSubjectId1;
  }



  /**
   * <pre>
   * # atlassian source to use (leave blank for all sources)
   * atlassian.subject.search.sourceId =
   * </pre>
   * @return the subjectSearchSourceId
   */
  public String getSubjectSearchSourceId() {
    return this.subjectSearchSourceId;
  }


  
  /**
   * <pre>
   * # atlassian source to use (leave blank for all sources)
   * atlassian.subject.search.sourceId =
   * </pre>
   * @param subjectSearchSourceId1 the subjectSearchSourceId to set
   */
  public void setSubjectSearchSourceId(String subjectSearchSourceId1) {
    this.subjectSearchSourceId = subjectSearchSourceId1;
  }


  /**
   * <pre>
   * # put a folder name that is the root for atlassian groups
   * atlassian.root =  
   * </pre>
   * @return the rootFolder
   */
  public String getRootFolder() {
    return this.rootFolder;
  }

  
  /**
   * <pre>
   * # put a folder name that is the root for atlassian groups
   * atlassian.root =  
   * </pre>
   * @param rootFolder1 the rootFolder to set
   */
  public void setRootFolder(String rootFolder1) {
    this.rootFolder = rootFolder1;
  }

  /** cache the config for 2 minutes, then read it again */
  private static ExpirableCache<Boolean, GrouperAtlassianConfig> grouperAtlassianConfigCache = new ExpirableCache<Boolean, GrouperAtlassianConfig>(5);
  
  /**
   * cache the config for 2 minutes, then read it again
   * @return the config
   */
  @SuppressWarnings("unchecked")
  public static GrouperAtlassianConfig grouperAtlassianConfig() {
    GrouperAtlassianConfig grouperAtlassianConfig = grouperAtlassianConfigCache.get(Boolean.TRUE);
    if (grouperAtlassianConfig == null) {
      synchronized(GrouperAtlassianConfig.class) {
        grouperAtlassianConfig = grouperAtlassianConfigCache.get(Boolean.TRUE);
        if (grouperAtlassianConfig == null) {
          
          GrouperAtlassianConfig tempConfig = new GrouperAtlassianConfig();
          
          //# put a folder name that is the root for atlassian groups
          //atlassian.root = 
          tempConfig.setRootFolder(GrouperClientUtils.propertiesValue("atlassian.root", true));
          
          //# atlassian source to use (leave blank for all sources)
          //atlassian.subject.search.sourceId = 
          tempConfig.setSubjectSearchSourceId(GrouperClientUtils.propertiesValue("atlassian.subject.search.sourceId", false));

          //# atlassian search by id, identifier, or idOrIdentifer (idOrIdentifier is Grouper 2.0+)
          //atlassian.subject.search.subjectId = 
          tempConfig.setSubjectSearchSubjectId(GrouperClientUtils.propertiesValue("atlassian.subject.search.subjectId", true));

          //# number of minutes to cache reads (-1 for none, though this isnt recommended since 
          //# atlassian makes a LOT of calls to the group service)
          //atlassian.cache.minutes = 
          tempConfig.setCacheMinutes(GrouperClientUtils.propertiesValueInt("atlassian.cache.minutes", 2, true));
          
          //# list all sources here, and how to get the atlassian id
          //atlassian.source.jdbc.sourceId = jdbc
          //# should be "id" or an attribute name to get the identifier for atlassian
          //atlassian.source.jdbc.idOrAttribute = loginid
          Pattern pattern = Pattern.compile("^atlassian\\.source\\.(.+)\\.sourceId$");
          
          Properties properties = GrouperClientUtils.grouperClientProperties();
          
          for (String key : (Set<String>)((Object)properties.keySet())) {
            
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
              String sourceName = matcher.group(1);
              GrouperAtlassianSourceConfig grouperAtlassianSourceConfig = new GrouperAtlassianSourceConfig();
              grouperAtlassianSourceConfig.setSourceId(GrouperClientUtils.propertiesValue(key, true));
              grouperAtlassianSourceConfig.setIdOrAttribute(GrouperClientUtils.propertiesValue(
                  "atlassian.source." + sourceName + ".idOrAttribute", true));
              tempConfig.getSourceConfigs().put(grouperAtlassianSourceConfig.getSourceId(), grouperAtlassianSourceConfig);
            }
          }
          
          //# groups which should be assigned to various privileges for new groups created in confluence
          //atlassian.updaters = 
          tempConfig.setAutoAddPrivilegeUpdaters(
              GrouperClientUtils.splitTrimToList(
                  GrouperClientUtils.propertiesValue("atlassian.updaters", false), ","));
          
          //atlassian.admins = 
          tempConfig.setAutoAddPrivilegeAdmins(
              GrouperClientUtils.splitTrimToList(
                  GrouperClientUtils.propertiesValue("atlassian.admins", false), ","));
          
          //atlassian.readers = 
          tempConfig.setAutoAddPrivilegeReaders(
              GrouperClientUtils.splitTrimToList(
                  GrouperClientUtils.propertiesValue("atlassian.readers", false), ","));

          //#ignore calls on this user to the web service
          //atlassian.ws.users.to.ignore = admin
          tempConfig.setWsUsersToIgnore(
              GrouperClientUtils.nonNull(
                GrouperClientUtils.splitTrimToList(
                    GrouperClientUtils.propertiesValue("atlassian.ws.users.to.ignore", false), ",")));

          
          //# pretend these memberships exist (e.g. to bootstrap or for users not in grouper)
          //atlassian.autoadd.administrators.groupname = jira-administrators
          //atlassian.autoadd.administrators.usernames = admin
          //
          //atlassian.autoadd.users.groupname = jira-users
          //atlassian.autoadd.users.usernames = admin
          pattern = Pattern.compile("^atlassian\\.autoadd\\.(.+)\\.groupname$");
          
          for (String key : (Set<String>)((Object)properties.keySet())) {
            
            Matcher matcher = pattern.matcher(key);
            if (matcher.matches()) {
              String sourceName = matcher.group(1);
              GrouperAtlassianAutoaddConfig grouperAtlassianAutoaddConfig = new GrouperAtlassianAutoaddConfig();
              String groupname = GrouperClientUtils.propertiesValue(key, true);
              grouperAtlassianAutoaddConfig.setGroupname(groupname);
              grouperAtlassianAutoaddConfig.setUsernames(
                  GrouperClientUtils.splitTrimToList(
                  GrouperClientUtils.propertiesValue(
                  "atlassian.autoadd." + sourceName + ".usernames", true), ","));
              tempConfig.getAutoaddConfigGroupToUsers().put(
                  groupname, grouperAtlassianAutoaddConfig);

              for (String username : grouperAtlassianAutoaddConfig.getUsernames()) {

                List<String> groups = tempConfig.getAutoaddConfigUserToGroups().get(username);
                if (groups == null) {
                  groups = new ArrayList<String>();
                  tempConfig.getAutoaddConfigUserToGroups().put(username, groups);
                }

                groups.add(groupname);

              }

            }
          }

          grouperAtlassianConfig = tempConfig;
          grouperAtlassianConfigCache.put(Boolean.TRUE, grouperAtlassianConfig);
          
          
        }
      }
    }
    return grouperAtlassianConfig;
  }
  
}
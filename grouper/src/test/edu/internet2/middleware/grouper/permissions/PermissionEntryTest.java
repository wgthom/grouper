/**
 * @author Kate
 * $Id: PermissionEntryTest.java,v 1.5 2009-11-10 03:35:21 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.permissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.textui.TestRunner;
import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.Member;
import edu.internet2.middleware.grouper.MemberFinder;
import edu.internet2.middleware.grouper.Stem;
import edu.internet2.middleware.grouper.StemFinder;
import edu.internet2.middleware.grouper.SubjectFinder;
import edu.internet2.middleware.grouper.attr.AttributeDef;
import edu.internet2.middleware.grouper.attr.AttributeDefName;
import edu.internet2.middleware.grouper.attr.AttributeDefType;
import edu.internet2.middleware.grouper.attr.assign.AttributeAssignAction;
import edu.internet2.middleware.grouper.cfg.ApiConfig;
import edu.internet2.middleware.grouper.helper.GrouperTest;
import edu.internet2.middleware.grouper.helper.SubjectTestHelper;
import edu.internet2.middleware.grouper.misc.GrouperDAOFactory;
import edu.internet2.middleware.grouper.permissions.role.Role;
import edu.internet2.middleware.grouper.privs.AccessPrivilege;
import edu.internet2.middleware.grouper.privs.AttributeDefPrivilege;
import edu.internet2.middleware.grouper.util.GrouperUtil;


/**
 *
 */
public class PermissionEntryTest extends GrouperTest {
  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    TestRunner.run(new PermissionEntryTest("testHierarchies"));
  }

  /**
   * 
   */
  public PermissionEntryTest() {
    super();
  }

  /**
   * 
   * @param name
   */
  public PermissionEntryTest(String name) {
    super(name);
  }

  /** grouper session */
  private GrouperSession grouperSession;

  /** root stem */
  private Stem root;

  /** top stem */
  private Stem top;

  /**
   * 
   */
  public void setUp() {
    super.setUp();
    this.grouperSession = GrouperSession.start(SubjectFinder.findRootSubject());
    this.root = StemFinder.findRootStem(this.grouperSession);
    this.top = this.root.addChildStem("top", "top display name");

    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrAdmin", "false");
    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrOptin", "false");
    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrOptout", "false");
    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrRead", "false");
    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrUpdate", "false");
    ApiConfig.testConfig.put("attributeDefs.create.grant.all.attrView", "false");

    ApiConfig.testConfig.put("groups.create.grant.all.read", "false");
    ApiConfig.testConfig.put("groups.create.grant.all.view", "false");
  }

  /**
   * permission entry
   */
  public void testHibernate() {
    Member member = MemberFinder.findBySubject(this.grouperSession, SubjectTestHelper.SUBJ5, true); 
    Set<PermissionEntry> permissionEntries = GrouperDAOFactory.getFactory().getPermissionEntry().findByMemberId(member.getUuid());
    for (PermissionEntry permissionEntry : permissionEntries) {
      //System.out.println(permissionEntry);
      assertNotNull(permissionEntry);
    }
  }
  
  /**
   * 
   */
  public void testAddLookup() {
    Role role = this.top.addChildRole("test", "test");
    ((Group)role).addMember(SubjectTestHelper.SUBJ5);
    
    AttributeDef attributeDef = this.top.addChildAttributeDef("test", AttributeDefType.perm);
    attributeDef.setAssignToEffMembership(true);
    attributeDef.setAssignToGroup(true);
    attributeDef.store();
    AttributeDefName attributeDefName = this.top.addChildAttributeDefName(attributeDef, "testName", "test name");
    
    role.getPermissionRoleDelegate().assignRolePermission(attributeDefName);
    
    Member member = MemberFinder.findBySubject(this.grouperSession, SubjectTestHelper.SUBJ5, true); 
    Set<PermissionEntry> permissionEntries = GrouperDAOFactory.getFactory().getPermissionEntry().findByMemberId(member.getUuid());
    for (PermissionEntry permissionEntry : permissionEntries) {
      System.out.println(permissionEntry);
    }

    AttributeDefName attributeDefNameEff = this.top.addChildAttributeDefName(attributeDef, "testNameEff", "test name effective");
    role.getPermissionRoleDelegate().assignSubjectRolePermission(attributeDefNameEff, member);
    
    permissionEntries = GrouperDAOFactory.getFactory().getPermissionEntry().findByMemberId(member.getUuid());
    for (PermissionEntry permissionEntry : permissionEntries) {
      System.out.println(permissionEntry);
    }
    
    
  }
  
  /**
   * 
   */
  public void testHierarchies() {
    
    //parent group has child as member
    Group groupParent = this.top.addChildGroup("groupParent", "groupParent");
    Group groupChild = this.top.addChildGroup("groupChild", "groupChild");
    //if you are in parent, then you are in child
    groupChild.addMember(groupParent.toSubject());
    
    groupParent.addMember(SubjectTestHelper.SUBJ0);
    groupChild.addMember(SubjectTestHelper.SUBJ1);
    
    //parent implies child
    Role roleParent = this.top.addChildRole("roleParent", "roleParent");
    Role roleChild = this.top.addChildRole("roleChild", "roleChild");
    roleChild.getRoleInheritanceDelegate().addRoleToInheritFromThis(roleParent);
        
    ((Group)roleParent).addMember(SubjectTestHelper.SUBJ2);
    ((Group)roleChild).addMember(SubjectTestHelper.SUBJ3);
    
    Role roleParent2 = this.top.addChildRole("roleParent2", "roleParen2t");
    Role roleChild2 = this.top.addChildRole("roleChild2", "roleChild2");
    roleChild2.getRoleInheritanceDelegate().addRoleToInheritFromThis(roleParent2);
        
    roleParent2.addMember(SubjectTestHelper.SUBJ4, false);
    roleChild2.addMember(SubjectTestHelper.SUBJ5, false);
    
    
    AttributeDef attributeDef = this.top.addChildAttributeDef("attributeDef", AttributeDefType.perm);
    attributeDef.setAssignToEffMembership(true);
    attributeDef.setAssignToGroup(true);
    attributeDef.store();
    AttributeDefName attrDefNameParent = this.top.addChildAttributeDefName(attributeDef, "attrDefNameParent", "attrDefNameParent");
    AttributeDefName attrDefNameChild = this.top.addChildAttributeDefName(attributeDef, "attrDefNameChild", "attrDefNameChild");
    attrDefNameParent.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(attrDefNameChild);

    AttributeAssignAction actionParent = attributeDef.getAttributeDefActionDelegate().addAction("actionParent");
    AttributeAssignAction actionChild = attributeDef.getAttributeDefActionDelegate().addAction("actionChild");
    
    actionParent.getAttributeAssignActionSetDelegate().addToAttributeAssignActionSet(actionChild);

    roleParent.getPermissionRoleDelegate().assignRolePermission("actionParent", attrDefNameParent);
    roleParent2.getPermissionRoleDelegate().assignRolePermission("actionChild", attrDefNameChild);
    roleParent.addMember(SubjectTestHelper.SUBJA, false);
    roleParent2.addMember(groupParent.toSubject(), false);
    
    roleParent.addMember(SubjectTestHelper.SUBJ6, false);
    roleParent.getPermissionRoleDelegate().assignSubjectRolePermission("actionParent", attrDefNameChild, SubjectTestHelper.SUBJ6);
    roleParent.addMember(SubjectTestHelper.SUBJ7, false);
    roleParent.getPermissionRoleDelegate().assignSubjectRolePermission("actionChild", attrDefNameParent, SubjectTestHelper.SUBJ7);
    roleChild2.addMember(SubjectTestHelper.SUBJ8, false);
    roleChild2.getPermissionRoleDelegate().assignSubjectRolePermission("actionParent", attrDefNameParent, SubjectTestHelper.SUBJ8);
    roleChild2.addMember(SubjectTestHelper.SUBJ9, false);
    roleChild2.getPermissionRoleDelegate().assignSubjectRolePermission("actionChild", attrDefNameChild, SubjectTestHelper.SUBJ9);

    //test subject 0 can READ and read
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ0, AccessPrivilege.READ);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ0, AccessPrivilege.READ);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ0, AccessPrivilege.READ);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ0, AccessPrivilege.READ);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ0, AttributeDefPrivilege.ATTR_READ, false);

    //test subject 1 can READ not read
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ1, AccessPrivilege.READ);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ1, AccessPrivilege.READ);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ1, AccessPrivilege.READ);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ1, AccessPrivilege.READ);

    //test subject 2 can read not READ
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ2, AttributeDefPrivilege.ATTR_READ, false);

    //test subject 3 can not read or READ

    //test subject 4 can read and read
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ4, AccessPrivilege.READ);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ4, AccessPrivilege.READ);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ4, AccessPrivilege.READ);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ4, AccessPrivilege.READ);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ4, AttributeDefPrivilege.ATTR_READ, false);

    //test subject 5 can update and read
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ5, AccessPrivilege.UPDATE);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ5, AccessPrivilege.UPDATE);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ5, AccessPrivilege.UPDATE);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ5, AccessPrivilege.UPDATE);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ5, AttributeDefPrivilege.ATTR_READ, false);

    //test subject 6 can admin and read
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ6, AccessPrivilege.ADMIN);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ6, AccessPrivilege.ADMIN);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ6, AccessPrivilege.ADMIN);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ6, AccessPrivilege.ADMIN);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ6, AttributeDefPrivilege.ATTR_READ, false);

    //test subject 7 can view and update
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ7, AccessPrivilege.VIEW);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ7, AccessPrivilege.VIEW);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ7, AccessPrivilege.VIEW);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ7, AccessPrivilege.VIEW);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ7, AttributeDefPrivilege.ATTR_UPDATE, false);

    //test subject 8 can view and admin
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ8, AccessPrivilege.VIEW);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ8, AccessPrivilege.VIEW);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ8, AccessPrivilege.VIEW);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ8, AccessPrivilege.VIEW);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ8, AttributeDefPrivilege.ATTR_ADMIN, false);
    
    //test subject 9 can view and view
    ((Group)roleParent).grantPriv(SubjectTestHelper.SUBJ9, AccessPrivilege.VIEW);
    ((Group)roleParent2).grantPriv(SubjectTestHelper.SUBJ9, AccessPrivilege.VIEW);
    ((Group)roleChild).grantPriv(SubjectTestHelper.SUBJ9, AccessPrivilege.VIEW);
    ((Group)roleChild2).grantPriv(SubjectTestHelper.SUBJ9, AccessPrivilege.VIEW);
    attributeDef.getPrivilegeDelegate().grantPriv(SubjectTestHelper.SUBJ9, AttributeDefPrivilege.ATTR_VIEW, false);


    Set<PermissionEntry> permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);
    List<PermissionEntry> permissionEntriesList = new ArrayList<PermissionEntry>(permissionEntriesSet);
    Collections.sort(permissionEntriesList);
    for (PermissionEntry permissionEntry : permissionEntriesList) {
      System.out.println(permissionEntry);
    }
    System.out.println("\n\n");
    for (PermissionEntry permissionEntry : permissionEntriesList) {
      System.out.println("    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, \"" 
          + permissionEntry.getRoleName() + "\", \"" + permissionEntry.getAttributeDefNameName() + "\", \"" 
          + permissionEntry.getAction() + "\", \"" + permissionEntry.getSubjectSourceId() + "\", \""
          + permissionEntry.getSubjectId() + "\");");
      System.out.println("    assertPermission(permissionEntry, \"" 
          + permissionEntry.getPermissionTypeDb() + "\", " + permissionEntry.isImmediateMembership() + ", " 
          + permissionEntry.isImmediatePermission() + ", " + permissionEntry.getMembershipDepth() + ", " 
          + permissionEntry.getRoleSetDepth() + ", " + permissionEntry.getAttributeAssignActionSetDepth()
          + ", " + permissionEntry.getAttributeDefNameSetDepth() + ");\n");
      
    }
    
    assertEquals(29, GrouperUtil.length(permissionEntriesList));
    
    PermissionEntry permissionEntry = null;
    
    //NOTE, THIS WAS GENERATED
    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.3");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 1, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild", "top:attrDefNameChild", "actionParent", "jdbc", "test.subject.3");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild", "top:attrDefNameParent", "actionChild", "jdbc", "test.subject.3");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild", "top:attrDefNameParent", "actionParent", "jdbc", "test.subject.3");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.5");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.8");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.9");
    assertPermission(permissionEntry, "role", true, false, 0, 1, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameChild", "actionParent", "jdbc", "test.subject.8");
    assertPermission(permissionEntry, "role_subject", true, false, 0, -1, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameParent", "actionChild", "jdbc", "test.subject.8");
    assertPermission(permissionEntry, "role_subject", true, false, 0, -1, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleChild2", "top:attrDefNameParent", "actionParent", "jdbc", "test.subject.8");
    assertPermission(permissionEntry, "role_subject", true, true, 0, -1, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionChild", "g:isa", "GrouperAll");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.2");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.6");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.7");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionParent", "g:isa", "GrouperAll");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionParent", "jdbc", "test.subject.2");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionParent", "jdbc", "test.subject.6");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameChild", "actionParent", "jdbc", "test.subject.7");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 0, 1);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionChild", "g:isa", "GrouperAll");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionChild", "jdbc", "test.subject.2");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionChild", "jdbc", "test.subject.6");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionChild", "jdbc", "test.subject.7");
    assertPermission(permissionEntry, "role", true, false, 0, 0, 1, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionParent", "g:isa", "GrouperAll");
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionParent", "jdbc", "test.subject.2");
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionParent", "jdbc", "test.subject.6");
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent", "top:attrDefNameParent", "actionParent", "jdbc", "test.subject.7");
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent2", "top:attrDefNameChild", "actionChild", "g:gsa", groupParent.getUuid());
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent2", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.0");
    assertPermission(permissionEntry, "role", false, true, 1, 0, 0, 0);

    permissionEntry = PermissionEntry.collectionFindFirst(permissionEntriesList, "top:roleParent2", "top:attrDefNameChild", "actionChild", "jdbc", "test.subject.4");
    assertPermission(permissionEntry, "role", true, true, 0, 0, 0, 0);
    //NOTE THAT WAS GENERATED
    
    //test subject 0 can READ and read
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ0);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(29, GrouperUtil.length(permissionEntriesSet));

    //test subject 1 can READ not read
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ1);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    //test subject 2 can read not READ
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ2);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    //test subject 3 can not read or READ
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ3);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    //test subject 4 can read and read
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ4);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(29, GrouperUtil.length(permissionEntriesSet));

    //test subject 5 can update and read
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ5);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    //test subject 6 can admin and read
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ6);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(29, GrouperUtil.length(permissionEntriesSet));

    //test subject 7 can view and update
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ7);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    //test subject 8 can view and admin
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ8);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));
    
    //test subject 9 can view and view
    GrouperSession.stopQuietly(this.grouperSession);
    this.grouperSession = GrouperSession.start(SubjectTestHelper.SUBJ9);
    
    permissionEntriesSet = GrouperDAOFactory.getFactory().getPermissionEntry().findPermissions(
        GrouperUtil.toSet(attributeDef.getId()), null, null, null, null, null);

    assertEquals(0, GrouperUtil.length(permissionEntriesSet));

    
  }

  /**
   * make sure all this stuff is correct
   * @param permissionEntry
   * @param permissionType
   * @param immediateMembership
   * @param immediatePermission
   * @param membershipDepth
   * @param roleDepth
   * @param actionDepth
   * @param attrDefDepth
   */
  public static void assertPermission(PermissionEntry permissionEntry, String permissionType, 
      boolean immediateMembership, boolean immediatePermission, int membershipDepth,
      int roleDepth, int actionDepth, int attrDefDepth) {
    String permissionEntryString = permissionEntry.toString();
    assertEquals(permissionEntryString, permissionType, permissionEntry.getPermissionTypeDb());
    assertEquals(permissionEntryString, immediateMembership, permissionEntry.isImmediateMembership());
    assertEquals(permissionEntryString, immediatePermission, permissionEntry.isImmediatePermission());
    assertEquals(permissionEntryString, membershipDepth, permissionEntry.getMembershipDepth());
    assertEquals(permissionEntryString, roleDepth, permissionEntry.getRoleSetDepth());
    assertEquals(permissionEntryString, actionDepth, permissionEntry.getAttributeAssignActionSetDepth());
    assertEquals(permissionEntryString, attrDefDepth, permissionEntry.getAttributeDefNameSetDepth());
  }
  
}
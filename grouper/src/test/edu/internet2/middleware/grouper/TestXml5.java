/*
  Copyright (C) 2004-2007 University Corporation for Advanced Internet Development, Inc.
  Copyright (C) 2004-2007 The University Of Chicago

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package edu.internet2.middleware.grouper;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;

import junit.textui.TestRunner;

import org.apache.commons.logging.Log;

import edu.internet2.middleware.grouper.registry.RegistryReset;
import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.grouper.xml.XmlExporter;
import edu.internet2.middleware.grouper.xml.XmlImporter;
import edu.internet2.middleware.grouper.xml.XmlReader;
import edu.internet2.middleware.subject.Subject;

/**
 * @author  blair christensen.
 * @version $Id: TestXml5.java,v 1.8 2008-09-29 03:38:27 mchyzer Exp $
 * @since   1.1.0
 */
public class TestXml5 extends GrouperTest {

  public static void main(String[] args) {
    TestRunner.run(new TestXml5("testFullExportFullImportFullGroupInternational"));
  }
  
  private static final Log LOG = GrouperUtil.getLog(TestXml5.class);

  public TestXml5(String name) {
    super(name);
  }

  protected void setUp () {
    LOG.debug("setUp");
    RegistryReset.reset();
  }

  protected void tearDown () {
    LOG.debug("tearDown");
  }

  public void testFullExportFullImportFullGroup() {
    LOG.info("testFullExportFullImportFullGroup");
    try {
      // Populate Registry And Verify
      R     r   = R.populateRegistry(1, 1, 0);
      Group gA  = assertFindGroupByName( r.rs, "i2:a:a" );
      // For Later Validation
      boolean has_a   = gA.hasAdmin( SubjectFinder.findAllSubject() );
      boolean has_oi  = gA.hasOptin( SubjectFinder.findAllSubject() );
      boolean has_oo  = gA.hasOptout( SubjectFinder.findAllSubject() );
      boolean has_r   = gA.hasRead( SubjectFinder.findAllSubject() );
      boolean has_u   = gA.hasUpdate( SubjectFinder.findAllSubject() );
      boolean has_v   = gA.hasView( SubjectFinder.findAllSubject() );
      Subject val_c   = gA.getCreateSubject();
      Date    val_ct  = gA.getCreateTime();
      String  val_d   = gA.getDescription();
      String  val_de  = gA.getDisplayExtension();
      String  val_dn  = gA.getDisplayName();
      String  val_e   = gA.getExtension();
      String  val_n   = gA.getName();
      String  val_u   = gA.getUuid();
      r.rs.stop();

      // Export
      GrouperSession  s         = GrouperSession.start( SubjectFinder.findRootSubject() );
      Writer          w         = new StringWriter();
      XmlExporter     exporter  = new XmlExporter(s, new Properties());
      exporter.export(w);
      String          xml       = w.toString();
      s.stop();

      // Reset And Verify
      RegistryReset.reset();
      s = GrouperSession.start( SubjectFinder.findRootSubject() );
      assertDoNotFindGroupByName( s, "i2:a:a" );
      s.stop();

      // Import 
      s = GrouperSession.start( SubjectFinder.findRootSubject() );
      XmlImporter importer = new XmlImporter(s, new Properties());
      importer.load( XmlReader.getDocumentFromString(xml) );
      s.stop();

      // Verify
      s   = GrouperSession.start( SubjectFinder.findRootSubject() );
      gA  = assertFindGroupByName( s, "i2:a:a" );
      assertGroupHasAdmin( gA, SubjectFinder.findAllSubject(), has_a );
      assertGroupHasOptin( gA, SubjectFinder.findAllSubject(), has_oi );
      assertGroupHasOptout( gA, SubjectFinder.findAllSubject(), has_oo );
      assertGroupHasRead( gA, SubjectFinder.findAllSubject(), has_r );
      assertGroupHasUpdate( gA, SubjectFinder.findAllSubject(), has_u );
      assertGroupHasView( gA, SubjectFinder.findAllSubject(), has_v );
      assertGroupCreateSubject( gA, val_c );
      assertGroupCreateTime( gA, val_ct );
      assertGroupDescription( gA, val_d );
      assertGroupDisplayExtension( gA, val_de );
      assertGroupDisplayName( gA, val_dn );
      assertGroupExtension( gA, val_e );
      assertGroupName( gA, val_n );
      assertGroupUuid( gA, val_u );
      s.stop();
    }
    catch (Exception e) {
      T.e(e);
    }
  } // public void testFullExportFullImportFullGroup()

  public void testFullExportFullImportFullGroupInternational() {
    LOG.info("testFullExportFullImportFullGroupInternational");
    try {
      // Populate Registry And Verify
      R     r   = R.populateRegistry(1, 1, 0);
      GrouperSession grouperSession = GrouperSession.startRootSession();
      Group gA = new GroupSave(grouperSession).assignGroupNameToEdit("t�st:�Group").assignName("t�st:�Group")
        .assignDisplayName("t�st:�Group").assignDescription("t�st:�Group").assignCreateParentStemsIfNotExist(true).save();
      
      // For Later Validation
      boolean has_a   = gA.hasAdmin( SubjectFinder.findAllSubject() );
      boolean has_oi  = gA.hasOptin( SubjectFinder.findAllSubject() );
      boolean has_oo  = gA.hasOptout( SubjectFinder.findAllSubject() );
      boolean has_r   = gA.hasRead( SubjectFinder.findAllSubject() );
      boolean has_u   = gA.hasUpdate( SubjectFinder.findAllSubject() );
      boolean has_v   = gA.hasView( SubjectFinder.findAllSubject() );
      Subject val_c   = gA.getCreateSubject();
      Date    val_ct  = gA.getCreateTime();
      String  val_d   = gA.getDescription();
      String  val_de  = gA.getDisplayExtension();
      String  val_dn  = gA.getDisplayName();
      String  val_e   = gA.getExtension();
      String  val_n   = gA.getName();
      String  val_u   = gA.getUuid();
      r.rs.stop();
  
      // Export
      GrouperSession  s         = GrouperSession.start( SubjectFinder.findRootSubject() );
      Writer          w         = new StringWriter();
      XmlExporter     exporter  = new XmlExporter(s, new Properties());
      exporter.export(w);
      String          xml       = w.toString();
      s.stop();
  
      // Reset And Verify
      RegistryReset.reset();
      s = GrouperSession.start( SubjectFinder.findRootSubject() );
      assertDoNotFindGroupByName( s, "t�st:�Group" );
      s.stop();
  
      // Import 
      s = GrouperSession.start( SubjectFinder.findRootSubject() );
      XmlImporter importer = new XmlImporter(s, new Properties());
      importer.load( XmlReader.getDocumentFromString(xml) );
      s.stop();
  
      // Verify
      s   = GrouperSession.start( SubjectFinder.findRootSubject() );
      gA  = assertFindGroupByName( s, "t�st:�Group" );
      assertGroupHasAdmin( gA, SubjectFinder.findAllSubject(), has_a );
      assertGroupHasOptin( gA, SubjectFinder.findAllSubject(), has_oi );
      assertGroupHasOptout( gA, SubjectFinder.findAllSubject(), has_oo );
      assertGroupHasRead( gA, SubjectFinder.findAllSubject(), has_r );
      assertGroupHasUpdate( gA, SubjectFinder.findAllSubject(), has_u );
      assertGroupHasView( gA, SubjectFinder.findAllSubject(), has_v );
      assertGroupCreateSubject( gA, val_c );
      assertGroupCreateTime( gA, val_ct );
      //assertGroupDescription( gA, val_d );
      assertGroupDisplayExtension( gA, val_de );
      assertGroupDisplayName( gA, val_dn );
      assertGroupExtension( gA, val_e );
      assertGroupName( gA, val_n );
      assertGroupUuid( gA, val_u );
      s.stop();
    }
    catch (Exception e) {
      T.e(e);
    }
  } // public void testFullExportFullImportFullGroupInternational()
} // public class TestXml5

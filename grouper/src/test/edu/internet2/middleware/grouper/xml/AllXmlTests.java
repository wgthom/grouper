/**
 * Copyright 2014 Internet2
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * @author mchyzer $Id: AllXmlTests.java,v 1.3 2009-11-05 06:10:51 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.xml;

import edu.internet2.middleware.grouper.xml.export.AllXmlExportTests;
import edu.internet2.middleware.grouper.xml.importXml.AllXmlImportTests;
import edu.internet2.middleware.grouper.xml.userAudit.AllXmlUserAuditTests;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 */
public class AllXmlTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for edu.internet2.middleware.grouper.xml");
    //$JUnit-BEGIN$
    suite.addTestSuite(Test_U_API_XmlExporter_internal_groupToXML.class);
    suite.addTestSuite(TestXmlReader.class);
    suite.addTestSuite(Test_U_API_XmlExporter_internal_subjectToXML.class);
    suite.addTestSuite(TestXmlImport.class);
    suite.addTestSuite(Test_U_API_XmlExporter_internal_membershipToXML.class);
    suite.addTestSuite(TestXmlExport.class);
    suite.addTestSuite(Test_U_Util_XML_escape.class);
    suite.addTestSuite(TestXml.class);
    //$JUnit-END$

    suite.addTest(AllXmlUserAuditTests.suite());
    suite.addTest(AllXmlExportTests.suite());
    suite.addTest(AllXmlImportTests.suite());

    return suite;
  }

}

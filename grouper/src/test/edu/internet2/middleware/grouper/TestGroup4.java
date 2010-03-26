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
import junit.framework.Assert;

import org.apache.commons.logging.Log;

import edu.internet2.middleware.grouper.registry.RegistryReset;
import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.subject.Subject;

/**
 * @author  blair christensen.
 * @version $Id: TestGroup4.java,v 1.7 2008-09-29 03:38:27 mchyzer Exp $
 */
public class TestGroup4 extends GrouperTest {

  // Private Class Constants
  private static final Log LOG = GrouperUtil.getLog(TestGroup4.class);


  public TestGroup4(String name) {
    super(name);
  }

  protected void setUp () {
    LOG.debug("setUp");
    RegistryReset.reset();
  }

  protected void tearDown () {
    LOG.debug("tearDown");
  }

  public void testGroupDeleteWhenMemberAndHasMembers() {
    LOG.info("testGroupDeleteWhenMemberAndHasMembers");
    try {
      R       r     = R.populateRegistry(1, 2, 1);
      Group   a     = r.getGroup("a", "a");
      Subject aSubj = a.toSubject();
      Group   b     = r.getGroup("a", "b");
      Subject subjA = r.getSubject("a");
    
      a.addMember(subjA);
      b.addMember(aSubj);

      a.delete();
      Assert.assertTrue("group deleted", true);
      T.amount("no more members", 0, b.getMembers().size());
 
      r.rs.stop();
    }
    catch (Exception e) {
      T.e(e);
    }
  } // public void testGroupDeleteWhenMemberAndHasMembers()

}

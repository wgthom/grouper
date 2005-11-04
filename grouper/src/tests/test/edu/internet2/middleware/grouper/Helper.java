/*
  Copyright 2004-2005 University Corporation for Advanced Internet Development, Inc.
  Copyright 2004-2005 The University Of Chicago

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

package test.edu.internet2.middleware.grouper;

import  edu.internet2.middleware.grouper.*;
import  edu.internet2.middleware.subject.*;
import  edu.internet2.middleware.subject.provider.*;
import  junit.framework.*;

/**
 * Helper methods for testing the Grouper API.
 * <p />
 * @author  blair christensen.
 * @version $Id: Helper.java,v 1.1.2.1 2005-11-04 18:34:35 blair Exp $
 */
public class Helper {

  // Private Class Constants
  private static final String ERROR = "How did we reach this statement?";


  // Protected Class Methods

  // Add and test a child group
  // @return  Created {@link Group}
  protected static Group addChildGroup(Stem ns, String extn, String displayExtn) {
    try {
      Group child = ns.addChildGroup(extn, displayExtn);
      Assert.assertNotNull("child !null", child);
      Assert.assertTrue("added child group", true);
      Assert.assertTrue(
        "child group instanceof Group", 
        child instanceof Group
      );
      Assert.assertNotNull("child uuid !null", child.getUuid());
      Assert.assertTrue("child has uuid", !child.getUuid().equals(""));
      return child;
    }
    catch (GroupAddException e) {
      Assert.fail("failed to add group: " + e.getMessage());
    }
    throw new RuntimeException(ERROR);
  } // protected static Group addChildGroup(ns, extn, displayExtn)

  // Add and test a child stem
  // @return  Created {@link Stem}
  protected static Stem addChildStem(Stem ns, String extn, String displayExtn) {
    try {
      Stem child = ns.addChildStem("edu", "educational");
      Assert.assertNotNull("child !null", child);
      Assert.assertTrue("added child stem", true);
      Assert.assertTrue(
        "child stem instanceof Stem", 
        child instanceof Stem
      );
      Assert.assertNotNull("child uuid !null", child.getUuid());
      Assert.assertTrue("child has uuid", !child.getUuid().equals(""));
      return child;
    }
    catch (StemAddException e) {
      Assert.fail("failed to add stem: " + e.getMessage());
    }
    throw new RuntimeException(ERROR);
  } // protected static Stem addChildStem(ns, extn, displayExtn)

  // Fail to get a session by id
  protected static void getBadSession(String id) {
    try {
      GrouperSession s = GrouperSession.startSession(
        SubjectFinder.findById(id)
      );
      Assert.fail("started session with bad subject: " + s);
    }
    catch (SessionException e0) {
      Assert.fail("should have been a SubjectNotFoundException");
    }
    catch (SubjectNotFoundException e1) {
      Assert.assertTrue("could not start session with bad subject", true);
    }
  } // protected static void getBadSession(id)

  // Get a root session
  // @return  A root {@link GrouperSession}
  protected static GrouperSession getRootSession() {
    return Helper.getSession("GrouperSystem");
  } // protected static GrouperSession getRootSession()

  // Get a session by id
  // @return  A {@link GrouperSession}
  protected static GrouperSession getSession(String id) {
    try {
      GrouperSession s = GrouperSession.startSession(
        SubjectFinder.findById(id)
      );
      Assert.assertNotNull("s !null", s);
      Assert.assertNotNull("session_id !null", s.getSessionId());
      Assert.assertTrue("has sessionID", !s.getSessionId().equals(""));
      Assert.assertNotNull("subject !null", s.getSubject());
      Assert.assertTrue(
        "subject instanceof Subject", s.getSubject() instanceof Subject
      );
      Assert.assertNotNull("member !null", s.getMember());
      Assert.assertTrue(
        "member instanceof Member", s.getMember() instanceof Member
      );
/* TODO
      Assert.assertTrue(
        "subject id", s.getMember().getSubjectId().equals(id)
      );
*/
      return s;
    }
    catch (SessionException e0) {
      Assert.fail(
        "failed to start session: " + e0.getMessage()
      );
    }
    catch (SubjectNotFoundException e1) {
      Assert.fail(
        "failed to find subject " + id + ": " + e1.getMessage()
      );
    }
    throw new RuntimeException(ERROR);
  } // protected static GrouperSession getSession(id)

  // Get a session by id and type
  // @return  A {@link GrouperSession}
  protected static GrouperSession getSession(String id, String type) {
    try {
      GrouperSession s = GrouperSession.startSession(
        SubjectFinder.findById(id, type)
      );
      Assert.assertNotNull("s !null", s);
      Assert.assertNotNull("session_id !null", s.getSessionId());
      Assert.assertTrue("has sessionID", !s.getSessionId().equals(""));
      Assert.assertNotNull("subject !null", s.getSubject());
      Assert.assertTrue(
        "subject instanceof Subject", s.getSubject() instanceof Subject
      );
      Assert.assertNotNull("member !null", s.getMember());
      Assert.assertTrue(
        "member instanceof Member", s.getMember() instanceof Member
      );
/* TODO
      Assert.assertTrue(
        "subject id", s.getMember().getSubjectId().equals(id)
      );
      Assert.assertTrue(
        "subject type", s.getMember().getSubjectTypeId().equals(type)
      );
*/
      return s;
    }
    catch (SessionException e0) {
      Assert.fail(
        "failed to start session: " + e0.getMessage()
      );
    }
    catch (SubjectNotFoundException e1) {
      Assert.fail(
        "failed to find subject " + id + "/" + type + ": " + e1.getMessage()
      );
    }
    throw new RuntimeException(ERROR);
  } // protected static GrouperSession getSession(id, type)

  // Get the root stem
  // @return  The root {@link Stem}
  protected static Stem getRootStem(GrouperSession s) {
    try {
      Stem root = StemFinder.findRootStem(s);
      Assert.assertNotNull("root !null", root);
      Assert.assertTrue("found root stem", true);
      Assert.assertTrue(
        "root stem instanceof Stem", 
        root instanceof Stem
      );
      Assert.assertNotNull("root uuid !null", root.getUuid());
      Assert.assertTrue("root has uuid",      !root.getUuid().equals(""));
      Assert.assertTrue(
        "root extn", root.getExtension().equals("")
      );
      Assert.assertTrue(
        "root displayExtn", root.getDisplayExtension().equals("")
      );
      Assert.assertTrue(
        "root name", root.getName().equals("")
      );
      Assert.assertTrue(
        "root displayName", root.getDisplayName().equals("")
      );
      return root;
    }
    catch (StemNotFoundException e) {
      Assert.fail("root stem not found: " + e.getMessage());
    }  
    throw new RuntimeException(ERROR);
  } // protected static Stem getRootStem(s)

}


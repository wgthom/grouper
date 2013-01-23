/*******************************************************************************
 * Copyright 2012 Internet2
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
 ******************************************************************************/
/*
 * Copyright 2002-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.internet2.middleware.grouperClientExt.org.apache.commons.jexl;

import edu.internet2.middleware.grouperClientExt.org.apache.commons.jexl.context.HashMapContext;

/**
 *  Helper to create a context.  In the current implementation of JEXL, there
 *  is one implementation of JexlContext - {@link HashMapContext}, and there
 *  is no reason not to directly instantiate {@link HashMapContext} in your
 *  own application.
 *
 *  @since 1.0
 *  @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 *  @version $Id: JexlHelper.java,v 1.1 2008-11-30 10:57:20 mchyzer Exp $
 */
public class JexlHelper {
    /** singleton instance. */
    protected static JexlHelper helper = new JexlHelper();

    /** @return the single instance. */
    protected static JexlHelper getInstance() {
        return helper;
    }

    /**
     * Returns a new {@link JexlContext}.
     * @return a new JexlContext
     */
    public static JexlContext createContext() {
        return getInstance().newContext();
    }

    /**
     * Creates and returns a new {@link JexlContext}.  
     * The current implementation creates a new instance of 
     * {@link HashMapContext}.
     * @return a new JexlContext
     */
    protected JexlContext newContext() {
        return new HashMapContext();
    }
}
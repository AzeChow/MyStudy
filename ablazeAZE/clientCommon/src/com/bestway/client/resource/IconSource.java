/*
 * Copyright 2002-2004 the original author or authors.
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
package com.bestway.client.resource;

import javax.swing.Icon;

/**
 * A loader and cache for Swing <code>ImageIcons</code>.
 * 
 * @author Keith Donald
 * @see javax.swing.ImageIcon
 */
public interface IconSource {

    /**
     * Return an <code>ImageIcon</code> using its <code>String</code> key.
     * 
     * @param key
     *            a key for the icon.
     * @return The image icon.
     * @throws NoSuchImageResourceException,
     *             if no resource is found and no broken image indicator is set.
     */
    public Icon getIcon(String key);

}
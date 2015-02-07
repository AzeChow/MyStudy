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

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default implementation of ImageIconRegistry. This implementation caches
 * all icons using soft references. More specifically, cached icons will remain
 * in memory unless there is a shortage of resources in the system.
 * 
 * @author Keith Donald
 */
public class DefaultIconSource implements IconSource {
    protected static final Log logger = LogFactory.getLog(DefaultIconSource.class);

    private ImageSource images;
    /**
     * Constructs a icon registry that loads images from the provided source.
     * 
     * @param images
     *            the image source.
     */
    public DefaultIconSource(ImageSource images) {
        this.images = images;
    }

	public ImageSource getImages() {
		return images;
	}
    public Icon getIcon(String key) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Resolving icon with key '" + key + "'");
            }
            return (ImageIcon)new ImageIcon(images.getImage(key));
        }
        catch (NoSuchImageResourceException e) {
            if (logger.isInfoEnabled()) {
                logger.info("No image resource found for icon with key '" + key + "'; returning a <null> icon.");
            }
            return null;
        }
    }

    protected String doProcessImageKeyBeforeLookup(String key) {
        // subclasses can override
        return key;
    }

}
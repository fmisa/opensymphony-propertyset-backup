/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.module.propertyset;

import com.opensymphony.module.propertyset.config.PropertySetConfig;

import com.opensymphony.util.ClassLoaderUtil;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class PropertySetManager {
    //~ Methods ////////////////////////////////////////////////////////////////

    public static PropertySet getInstance(String name, Map args) {
        PropertySetConfig psc = PropertySetConfig.getConfig();
        String clazz = psc.getClassName(name);
        Map config = psc.getArgs(name);
        Class psClass = null;

        try {
            psClass = ClassLoaderUtil.loadClass(clazz, PropertySetManager.class);
        } catch (ClassNotFoundException ex) {
            return null;
        }

        try {
            PropertySet ps = (PropertySet) psClass.newInstance();
            ps.init(config, args);

            return ps;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void clone(PropertySet src, PropertySet dest) {
        PropertySetCloner cloner = new PropertySetCloner();
        cloner.setSource(src);
        cloner.setDestination(dest);
        cloner.cloneProperties();
    }
}
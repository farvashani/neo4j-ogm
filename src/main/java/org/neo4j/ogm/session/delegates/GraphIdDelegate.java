/*
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 */

package org.neo4j.ogm.session.delegates;

import org.neo4j.ogm.entityaccess.DefaultEntityAccessStrategy;
import org.neo4j.ogm.entityaccess.EntityAccessStrategy;
import org.neo4j.ogm.metadata.MappingException;
import org.neo4j.ogm.metadata.info.ClassInfo;
import org.neo4j.ogm.session.Capability;
import org.neo4j.ogm.session.Neo4jSession;

/**
 * @author Luanne Misquitta
 */
public class GraphIdDelegate implements Capability.GraphId {

	private final Neo4jSession session;

	public GraphIdDelegate(Neo4jSession session) {
		this.session = session;
	}

	@Override
	public Long resolveGraphIdFor(Object possibleEntity) {
		if (possibleEntity!=null) {
			ClassInfo classInfo = session.metaData().classInfo(possibleEntity);
			try {
				if (classInfo != null) {
					EntityAccessStrategy entityAccessStrategy = new DefaultEntityAccessStrategy();
					Object id = entityAccessStrategy.getIdentityPropertyReader(classInfo).read(possibleEntity);
					if (id != null) {
						return (long) id;
					}
				}
			} catch (MappingException me) {
				//Possibly no identity field on the entity. One example is an Enum- it won't have an identity field.
				return null;
			}
		}
		return null;
	}
}

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

package org.neo4j.ogm.session.result;

import java.util.Collection;

/**
 * Encapsulates {@link QueryStatistics} and row data returned by a query.
 * @author Luanne Misquitta
 */
public class RowQueryStatisticsResult {

	private Collection<Object> rows;
	private QueryStatistics stats;

	public QueryStatistics getStats() {
		return stats;
	}

	public void setRows(Collection<Object> rows) {
		this.rows = rows;
	}

	public void setStats(QueryStatistics stats) {
		this.stats = stats;
	}

	public Collection<Object> getRows() {
		return rows;
	}
}

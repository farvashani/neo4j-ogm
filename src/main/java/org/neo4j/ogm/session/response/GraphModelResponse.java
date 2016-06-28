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

package org.neo4j.ogm.session.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.ogm.model.GraphModel;
import org.neo4j.ogm.session.result.CypherException;
import org.neo4j.ogm.session.result.GraphModelResult;
import org.neo4j.ogm.session.result.ResultProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vince Bickers
 */
public class GraphModelResponse implements Neo4jResponse<GraphModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphModelResponse.class);

    private final ObjectMapper objectMapper;
    private final Neo4jResponse<String> response;

    public GraphModelResponse(Neo4jResponse<String> response, ObjectMapper mapper) {
        this.response = response;
        this.objectMapper = mapper;
        try {
            initialiseScan(ResponseRecord.GRAPH);
        }
        catch (CypherException ce) {
            close();
            throw ce;
        }
        catch (Exception e) {
            close();
            throw new ResultProcessingException("Could not initialise response", e);
        }
    }

    @Override
    public GraphModel next() {

        String json = response.next();

        if (json != null) {
            try {
                return objectMapper.readValue(json, GraphModelResult.class).getGraph();
            } catch (Exception e) {
                LOGGER.error("failed to parse: {}", json);
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public void initialiseScan(ResponseRecord record) {
        response.initialiseScan(record);
    }

    @Override
    public String[] columns() {
        return response.columns();
    }

    @Override
    public int rowId() {
        return response.rowId();
    }

}

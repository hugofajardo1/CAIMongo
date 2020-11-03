package com.example.mongo.repository.impl;

import com.example.mongo.model.Accident;
import com.example.mongo.model.CommonSeverity;
import com.example.mongo.model.Distance;
import com.example.mongo.model.Punto;
import com.example.mongo.repository.IAccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class AccidentRepository implements IAccidentRepository {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public AccidentRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Collection<Accident> findByDateBetween(String pDesde, String pHasta) {
        BasicQuery query = new BasicQuery("{ Start_Time: { $gte: " + pDesde + " , $lte: " + pHasta + "}}");
        return mongoTemplate.find(query, Accident.class);
    }

    @Override
    public Collection<Accident> findInRadius(Float pLatitud, Float pLongitud, Integer pRadius) {
        BasicQuery query = new BasicQuery("{start_location:{ $nearSphere: { $geometry: { type: 'Point',coordinates: ["+ pLatitud +","+ pLongitud +" ] }, $minDistance: 10, $maxDistance: "+ pRadius * 1000 +"}}}");
        return mongoTemplate.find(query, Accident.class);
    }

    @Override
    public Collection<Distance> getAverageDistance() {
        MatchOperation match0 = match(new Criteria("Distance(mi)").ne(null));
        GroupOperation group = group().avg("Distance(mi)").as("distance");
        ProjectionOperation project = project().andExpression("distance").multiply(1609).as("distance");
        Aggregation aggregation = Aggregation.newAggregation(match0, group, project);
        AggregationResults<Distance> result = mongoTemplate.aggregate(aggregation, "accidentes", Distance.class);
        return result.getMappedResults();
    }

    @Override
    public Collection<CommonSeverity> getCommonConditions() {
        MatchOperation match0 = match(new Criteria("Severity").ne(null));
        GroupOperation group = group("Severity").count().as("cont");
        MatchOperation match1 = match(new Criteria("cont").gt(0));
        SortOperation sort = sort(Sort.Direction.DESC, "cont");
        Aggregation aggregation = Aggregation.newAggregation(match0, group, match1, sort);
        AggregationResults<CommonSeverity> result = mongoTemplate.aggregate(aggregation, "accidentes", CommonSeverity.class);
        return result.getMappedResults();
    }

    @Override
    public Collection<Punto> getAccidentsDangerousPointsInRadius(Float pLatitud, Float pLongitud, Integer pRadius) {
        NearQuery nearQuery = NearQuery.near(new Point(pLatitud, pLongitud), Metrics.KILOMETERS)
                .maxDistance(pRadius)
                .spherical(true);

        GeoNearOperation geoNearOperation = geoNear(nearQuery, "dist.calculated");
        GroupOperation groupOperation = group("start_location").count().as("cont");
        MatchOperation matchOperation = match(new Criteria("cont").gt(0));
        SortOperation sortOperation = sort(Sort.Direction.DESC, "cont");
        LimitOperation limitOperation = limit(5);
        Aggregation aggregation = Aggregation.newAggregation(geoNearOperation, groupOperation, matchOperation, sortOperation, limitOperation);
        AggregationResults<Punto> result = mongoTemplate.aggregate(aggregation, "accidentes", Punto.class);
        return result.getMappedResults();
    }
}

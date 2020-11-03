package com.example.mongo.repository;

import com.example.mongo.model.Accident;
import com.example.mongo.model.CommonSeverity;
import com.example.mongo.model.Distance;
import com.example.mongo.model.Punto;

import java.util.Collection;

public interface IAccidentRepository {

    Collection<Accident> findByDateBetween(String pDesde, String pHasta);

    Collection<Accident> findInRadius(Float pLatitud, Float pLongitud, Integer pRadius);

    Collection<Distance> getAverageDistance();

    Collection<CommonSeverity> getCommonConditions();

    Collection<Punto> getAccidentsDangerousPointsInRadius(Float pLatitud, Float pLongitud, Integer pRadius);
}

package com.example.mongo.services;

import java.util.Collection;

import com.example.mongo.dto.AccidentDTO;
import com.example.mongo.dto.CommonSeverityDTO;
import com.example.mongo.dto.DistanceDTO;
import com.example.mongo.dto.PuntoDTO;

public interface IAccidentService {

	Collection<AccidentDTO> getAccidentsBetween(String pDesde, String pHasta);

	Collection<AccidentDTO> getAccidentsInRadius(Float pLatitud, Float pLongitud, Integer pRadius);

	Collection<DistanceDTO> getAverageDistance();

	Collection<CommonSeverityDTO>  getCommonConditions();

    Collection<PuntoDTO> getAccidentsDangerousPointsInRadius(Float pLatitud, Float pLongitud, Integer pRadius);
}

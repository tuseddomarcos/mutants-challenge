package com.mutant.recruter.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mutant.recruter.dto.RequestMutant;
import com.mutant.recruter.dto.Stats;
import com.mutant.recruter.exception.InvalidDNAException;
import com.mutant.recruter.service.DnaStatisticsService;
import com.mutant.recruter.service.MutantServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "MutantController")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class MutantController {

	protected static final String JSON_UTF8 = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";
	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	MutantServices mutantServices;

	@Autowired
	DnaStatisticsService dnaStatisticsService;

	@ApiOperation(value = "Mutant", notes = "Recibe como parámetro una secuencia de ADN y responde si la misma pertenece, o no, a un mutante.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response =Boolean.class), @ApiResponse(code = 403, message = "Forbidden", response =Boolean.class),
			@ApiResponse(code = 400, message = "Bad Request", response = InvalidDNAException.class), @ApiResponse(code = 500, message = "Interval Server Error", response = Exception.class) })
	@PostMapping(value = "/mutant/", consumes = JSON_UTF8, produces = JSON_UTF8)
	public ResponseEntity<Object> isMutant(@Validated @RequestBody RequestMutant requestBody) throws ServiceException {
		logger.info("Llamada al servicio /mutant/ con el request body : {}", requestBody.toString());
		boolean response;
		try {
			response = this.mutantServices.isMutant(requestBody);
			return new ResponseEntity<>(response, response ? HttpStatus.OK : HttpStatus.FORBIDDEN);
		}catch (InvalidDNAException e) {
			logger.error("Un expected error: {}", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}catch (Exception e) {
			logger.error("Un expected error: {}", e.getCause().getMessage());
			return new ResponseEntity<>("Server error ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Stats", notes = "Devuelve estadísticas en base a las secuencias de ADN que han sido consultadas.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = Stats.class),
			@ApiResponse(code = 500, message = "Interval Server Error", response = Exception.class) })
	@GetMapping(value = "/stats", produces = JSON_UTF8)
	public ResponseEntity<Object> getStats() throws ServiceException {
		logger.info("Llamada al servicio /stats");
		return new ResponseEntity<>(dnaStatisticsService.getStats(), HttpStatus.OK);
	}
}

package com.oath.micro.server.general.exception.mapper;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.EOFException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GeneralExceptionMapperTest {

	GeneralExceptionMapper mapper;
	Logger mockLogger;

	@Before
	public void setUp() throws Exception {
		mapper = new GeneralExceptionMapper();
		mockLogger = mock(Logger.class);
	}

	@Test
	public void whenEOFException_thenBadRequest() {
		assertThat(mapper.toResponse(new EOFException("test")).getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	public void whenJsonProcessingException_thenBadRequest() {
		assertThat(mapper.toResponse(mock(JsonProcessingException.class)).getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	public void whenNotFoundException_thenNotFound() {
		assertThat(mapper.toResponse(new NotFoundException()).getStatus(), is(Status.NOT_FOUND.getStatusCode()));
	}

	@Test
	public void whenWebApplicationException_thenUnsupportedMediaType() {
		
		assertThat(mapper.toResponse(new NotSupportedException(Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build()))
								.getStatus(), is(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode()));
	}

	@Test
	public void whenRuntimeException_thenInternalServerError() {
		assertThat(mapper.toResponse(mock(RuntimeException.class)).getStatus(), is(Status.INTERNAL_SERVER_ERROR.getStatusCode()));
	}

	

	@Test
	public void whenJacksonException_thenNotInternalServerError_NoErrorsLogged() {
		mapper=  new GeneralExceptionMapper(mockLogger);
		assertThat(mapper.toResponse(new MyLocalException()).getStatus(), is((Status.INTERNAL_SERVER_ERROR.getStatusCode())));
		verify(mockLogger, times(0)).error(any(String.class), any(Object[].class));
		verify(mockLogger, times(0)).error(any(String.class), any(Throwable.class), any(Object[].class));
	}

}

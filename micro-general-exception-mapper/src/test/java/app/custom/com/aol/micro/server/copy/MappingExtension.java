package app.custom.com.aol.micro.server.copy;

import java.util.LinkedHashMap;

import javax.ws.rs.core.Response.Status;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.stereotype.Component;

import com.aol.micro.server.general.exception.mapper.ExtensionMapOfExceptionsToErrorCodes;

@Component
public class MappingExtension implements ExtensionMapOfExceptionsToErrorCodes {

	@Override
	public LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> getErrorMappings() {
		LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> map = new LinkedHashMap<>();
		map.put(MyException.class, Tuple.tuple("my-error",Status.BAD_GATEWAY));
		return map;
	}

}

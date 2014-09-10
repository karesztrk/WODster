package util.http.type.parser;

import play.api.mvc.BodyParser;
import play.mvc.Http.RequestBody;
import util.http.request.SimpleForm;

public class SimpleFormParser extends play.mvc.BodyParser.FormUrlEncoded {

	@Override
	public BodyParser<RequestBody> parser(int maxlength) {
		FormUrlEncoded baseParser = new FormUrlEncoded();
		BodyParser<RequestBody> parser = baseParser.parser(maxlength).map(new akka.dispatch.Mapper<RequestBody, RequestBody>() {
			
            @Override
            public RequestBody apply(RequestBody requestBody) {
            	return new SimpleForm(requestBody.asFormUrlEncoded());
            }
        });
		
		return parser;
	}

	

}

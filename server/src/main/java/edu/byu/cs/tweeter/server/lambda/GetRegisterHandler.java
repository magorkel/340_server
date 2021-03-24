package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.service.RegisterServiceImpl;

public class GetRegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse>
{
    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context)
    {
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        return registerService.getRegister(registerRequest);
    }
}

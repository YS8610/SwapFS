package tfip.moddb.swap.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class JwtFilter implements Filter {
    
    @Autowired
    AuthenticateService authSvc;


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,ServletException{
        final HttpServletRequest httpReq = (HttpServletRequest)req;
        final HttpServletResponse httpResp = (HttpServletResponse)resp;

        // Look for Authorization: Bearer token
        String authHeader = httpReq.getHeader("Authorization");
        if ((null == authHeader) || !authSvc.validate(authHeader.trim().substring("Bearer ".length())) ){
            unauthorized(httpResp);
            return;
        }

        chain.doFilter(req, resp);
    }

    private void unauthorized(HttpServletResponse httpResp) throws IOException {
        httpResp.setStatus(401);
        httpResp.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter pw = httpResp.getWriter()) {
            JsonObject resp = new JsonObject();
            resp.addProperty("error", "Invalid token");
            pw.print(resp.toString());
        }
    }
}
